package eu.mihosoft.ext.velocity.legacy.runtime.visitor;

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

import eu.mihosoft.ext.velocity.legacy.runtime.parser.Token;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTAddNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTAndNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTAssignment;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTBlock;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTDirective;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTDivNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTEQNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTElseIfStatement;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTElseStatement;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTEscape;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTEscapedDirective;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTExpression;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTFalse;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTFloatingPointLiteral;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTGENode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTGTNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIdentifier;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIfStatement;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIntegerLiteral;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIntegerRange;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTLENode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTLTNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTMap;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTMethod;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTModNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTMulNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTNENode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTNotNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTObjectArray;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTOrNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTReference;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTSetDirective;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTStringLiteral;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTSubtractNode;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTText;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTTrue;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTWord;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTprocess;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.Node;
import eu.mihosoft.ext.velocity.legacy.runtime.parser.node.SimpleNode;

/**
 * This class is simply a visitor implementation
 * that traverses the AST, produced by the Velocity
 * parsing process, and creates a visual structure
 * of the AST. This is primarily used for
 * debugging, but it useful for documentation
 * as well.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @version $Id: NodeViewMode.java 747106 2009-02-23 19:25:14Z nbubna $
 */
public class NodeViewMode extends BaseVisitor
{
    private int indent = 0;
    private boolean showTokens = true;

    /** Indent child nodes to help visually identify
      *  the structure of the AST.
      */
    private String indentString()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indent; ++i)
        {
            sb.append("  ");
        }
        return sb.toString();
    }

    /**
      * Display the type of nodes and optionally the
      * first token.
      */
    private Object showNode(Node node, Object data)
    {
        String tokens = "";
        String special = "";
        Token t;

        if (showTokens)
        {
            t = node.getFirstToken();

            if (t.specialToken != null && ! t.specialToken.image.startsWith("##"))
                special = t.specialToken.image;

            tokens = " -> " + special + t.image;
        }

        System.out.println(indentString() + node + tokens);
        ++indent;
        data = node.childrenAccept(this, data);
        --indent;
        return data;
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.SimpleNode, java.lang.Object)
     */
    public Object visit(SimpleNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTprocess, java.lang.Object)
     */
    public Object visit(ASTprocess node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTExpression, java.lang.Object)
     */
    public Object visit(ASTExpression node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTAssignment, java.lang.Object)
     */
    public Object visit(ASTAssignment node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTOrNode, java.lang.Object)
     */
    public Object visit(ASTOrNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTAndNode, java.lang.Object)
     */
    public Object visit(ASTAndNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTEQNode, java.lang.Object)
     */
    public Object visit(ASTEQNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTNENode, java.lang.Object)
     */
    public Object visit(ASTNENode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTLTNode, java.lang.Object)
     */
    public Object visit(ASTLTNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTGTNode, java.lang.Object)
     */
    public Object visit(ASTGTNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTLENode, java.lang.Object)
     */
    public Object visit(ASTLENode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTGENode, java.lang.Object)
     */
    public Object visit(ASTGENode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTAddNode, java.lang.Object)
     */
    public Object visit(ASTAddNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTSubtractNode, java.lang.Object)
     */
    public Object visit(ASTSubtractNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTMulNode, java.lang.Object)
     */
    public Object visit(ASTMulNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTDivNode, java.lang.Object)
     */
    public Object visit(ASTDivNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTModNode, java.lang.Object)
     */
    public Object visit(ASTModNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTNotNode, java.lang.Object)
     */
    public Object visit(ASTNotNode node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTFloatingPointLiteral, java.lang.Object)
     */
    public Object visit(ASTFloatingPointLiteral node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIntegerLiteral, java.lang.Object)
     * @since 1.5
     */
    public Object visit(ASTIntegerLiteral node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTStringLiteral, java.lang.Object)
     */
    public Object visit(ASTStringLiteral node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIdentifier, java.lang.Object)
     */
    public Object visit(ASTIdentifier node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTMethod, java.lang.Object)
     */
    public Object visit(ASTMethod node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTReference, java.lang.Object)
     */
    public Object visit(ASTReference node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTTrue, java.lang.Object)
     */
    public Object visit(ASTTrue node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTFalse, java.lang.Object)
     */
    public Object visit(ASTFalse node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTBlock, java.lang.Object)
     */
    public Object visit(ASTBlock node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTText, java.lang.Object)
     */
    public Object visit(ASTText node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIfStatement, java.lang.Object)
     */
    public Object visit(ASTIfStatement node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTElseStatement, java.lang.Object)
     */
    public Object visit(ASTElseStatement node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTElseIfStatement, java.lang.Object)
     */
    public Object visit(ASTElseIfStatement node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTObjectArray, java.lang.Object)
     */
    public Object visit(ASTObjectArray node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTDirective, java.lang.Object)
     */
    public Object visit(ASTDirective node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTWord, java.lang.Object)
     */
    public Object visit(ASTWord node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTSetDirective, java.lang.Object)
     */
    public Object visit(ASTSetDirective node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTEscapedDirective, java.lang.Object)
     * @since 1.5
     */
    public Object visit(ASTEscapedDirective node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTEscape, java.lang.Object)
     * @since 1.5
     */
    public Object visit(ASTEscape node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTMap, java.lang.Object)
     * @since 1.5
     */
    public Object visit(ASTMap node, Object data)
    {
        return showNode(node,data);
    }

    /**
     * @see eu.mihosoft.ext.velocity.legacy.runtime.visitor.BaseVisitor#visit(eu.mihosoft.ext.velocity.legacy.runtime.parser.node.ASTIntegerRange, java.lang.Object)
     */
    public Object visit(ASTIntegerRange node, Object data)
    {
        return showNode(node,data);
    }
}
