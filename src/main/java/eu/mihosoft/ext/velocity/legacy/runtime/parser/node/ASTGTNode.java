package eu.mihosoft.ext.velocity.legacy.runtime.parser.node;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import eu.mihosoft.ext.velocity.legacy.context.InternalContextAdapter;
import eu.mihosoft.ext.velocity.legacy.exception.MethodInvocationException;
import eu.mihosoft.ext.velocity.legacy.exception.VelocityException;
import eu.mihosoft.ext.velocity.legacy.runtime.RuntimeConstants;
import eu.mihosoft.ext.velocity.legacy.runtime.log.Log;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.Parser;
import eu.mihosoft.ext.velocity.legacy.util.TemplateNumber;

/**
 * Handles arg1 &gt; arg2<br><br>
 *
 * Only subclasses of Number can be compared.<br><br>
 *
 * Please look at the Parser.jjt file which is
 * what controls the generation of this class.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 * @author <a href="mailto:pero@antaramusic.de">Peter Romianowski</a>
 */

public class ASTGTNode extends SimpleNode
{
    /**
     * @param id
     */
    public ASTGTNode(int id)
    {
        super(id);
    }

    /**
     * @param p
     * @param id
     */
    public ASTGTNode(Parser p, int id)
    {
        super(p, id);
    }


    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.parser.node.SimpleNode#jjtAccept(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ParserVisitor, java.lang.Object)
     */
    public Object jjtAccept(ParserVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.parser.node.SimpleNode#evaluate(eu.mihosoft.ext.velocity.legacy.context.InternalContextAdapter)
     */
    public boolean evaluate(InternalContextAdapter context)
        throws MethodInvocationException
    {
        /*
         *  get the two args
         */

        Object left = jjtGetChild(0).value( context );
        Object right = jjtGetChild(1).value( context );

        /*
         *  if either is null, lets log and bail
         */

        if (left == null || right == null)
        {
            String msg = (left == null ? "Left" : "Right")
                           + " side ("
                           + jjtGetChild( (left == null? 0 : 1) ).literal()
                           + ") of '>' operation has null value at "
                           + Log.formatFileString(this);

            if (rsvc.getBoolean(RuntimeConstants.RUNTIME_REFERENCES_STRICT, false))
            {
              throw new VelocityException(msg);
            }

            log.error(msg);
            return false;
        }

        /*
         *  convert to Number if applicable
         */
        if (left instanceof TemplateNumber)
        {
           left = ( (TemplateNumber) left).getAsNumber();
        }
        if (right instanceof TemplateNumber)
        {
           right = ( (TemplateNumber) right).getAsNumber();
        }

        /*
         *  Only compare Numbers
         */

        if ( !( left instanceof Number )  || !( right instanceof Number ))
        {
            String msg = (!(left instanceof Number) ? "Left" : "Right")
                           + " side of '>=' operation is not a Number at "
                           + Log.formatFileString(this);

            if (rsvc.getBoolean(RuntimeConstants.RUNTIME_REFERENCES_STRICT, false))
            {
              throw new VelocityException(msg);
            }
            
            log.error(msg);
            return false;
        }

        return MathUtils.compare ( (Number)left,(Number)right) == 1;

    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.parser.node.SimpleNode#value(eu.mihosoft.ext.velocity.legacy.context.InternalContextAdapter)
     */
    public Object value(InternalContextAdapter context)
        throws MethodInvocationException
    {
        boolean val = evaluate(context);

        return val ? Boolean.TRUE : Boolean.FALSE;
    }

}
