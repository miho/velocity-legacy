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

import java.io.IOException;
import java.io.Writer;

import eu.mihosoft.ext.velocity.legacy.context.InternalContextAdapter;
import eu.mihosoft.ext.velocity.legacy.exception.MethodInvocationException;
import eu.mihosoft.ext.velocity.legacy.exception.ParseErrorException;
import eu.mihosoft.ext.velocity.legacy.exception.ResourceNotFoundException;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.Parser;


/**
 *
 */
public class ASTBlock extends SimpleNode
{
    /**
     * @param id
     */
    public ASTBlock(int id)
    {
        super(id);
    }

    /**
     * @param p
     * @param id
     */
    public ASTBlock(Parser p, int id)
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
     * @see eu.mihosoft.ext.velocity.legacy.runtime.parser.node.SimpleNode#render(eu.mihosoft.ext.velocity.legacy.context.InternalContextAdapter, java.io.Writer)
     */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException, MethodInvocationException,
        	ResourceNotFoundException, ParseErrorException
    {
        int i, k = jjtGetNumChildren();

        for (i = 0; i < k; i++)
            jjtGetChild(i).render(context, writer);

        return true;
    }
}
