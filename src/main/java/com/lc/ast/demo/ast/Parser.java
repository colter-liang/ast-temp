package com.lc.ast.demo.ast;

import com.lc.ast.demo.ast.node.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Node parse() {
        // Implement parsing logic to build an abstract syntax tree (AST).
        return parseExpression();
    }

    private Node parseExpression() {
        // Expression ::= Term { ('+' | '-') Term }
        Node node = parseTerm();
        while (current < tokens.size() && ("+".equals(tokens.get(current).getValue()) || "-".equals(tokens.get(current).getValue()))) {
            Token operation = tokens.get(current++);
            Node right = parseTerm();
            node = new OperationNode(operation.getValue(), node, right);
        }
        return node;
    }

    private Node parseTerm() {
        // Term ::= Factor { ('*' | '/') Factor }
        Node node = parseFactor();
        while (current < tokens.size() && ("*".equals(tokens.get(current).getValue()) || "/".equals(tokens.get(current).getValue()))) {
            Token op = tokens.get(current++);
            Node right = parseFactor();
            node = new OperationNode(op.getValue(), node, right);
        }
        return node;
    }

    private Node parseFactor() {
        // Factor ::= Number | FunctionCall | '(' Expression ')'
        Token token = tokens.get(current++);
        if (token.getType() == Token.TokenType.NUMBER) {
            return new NumberNode(Double.parseDouble(token.getValue()));
        } else if (token.getType() == Token.TokenType.FUNCTION) {
            return parseFunctionCall(token);
        } else if (token.getType() == Token.TokenType.LPAREN) {
            Node node = parseExpression();
            if (current >= tokens.size() || !")".equals(tokens.get(current).getValue())) {
                throw new IllegalArgumentException("Expected ')'");
            }
            current++; // Consume ')'
            return node;
        } else if (token.getType() == Token.TokenType.PARAMETER_VARIABLE) {
            return new ParamVariableNode(token.getValue());
        } else if (token.getType() == Token.TokenType.STR_PARAM) {
            return new StrParamNode(token.getValue());
        } else if(token.getType() == Token.TokenType.OPERATOR){
            Token negative = tokens.get(current++);
            if (current >= tokens.size() || !")".equals(tokens.get(current).getValue())) {
                throw new IllegalArgumentException("Expected ')'");
            }
            current++; // Consume ')'
            return new NumberNode(Double.parseDouble(negative.getValue()));
        }else {
            throw new IllegalArgumentException("Invalid factor");
        }
    }

    private Node parseFunctionCall(Token functionNameToken) {
        Token functionToken = functionNameToken;
        if (functionToken.getType() != Token.TokenType.FUNCTION) {
            throw new IllegalArgumentException("Expected function name");
        }
        String functionName = functionToken.getValue();

        if (current >= tokens.size() || !"(".equals(tokens.get(current).getValue())) {
            throw new IllegalArgumentException("Expected '('");
        }
        current++; // Consume '('

        List<Node> arguments = new ArrayList<>();
        while (current < tokens.size() && !")".equals(tokens.get(current).getValue())) {
            arguments.add(parseExpression());
            if (current < tokens.size() && ",".equals(tokens.get(current).getValue())) {
                current++; // Consume ','
            }
        }

        if (current >= tokens.size() || !")".equals(tokens.get(current).getValue())) {
            throw new IllegalArgumentException("Expected ')'");
        }
        current++; // Consume ')'

        return new FunctionCallNode(functionName, arguments);
    }
}