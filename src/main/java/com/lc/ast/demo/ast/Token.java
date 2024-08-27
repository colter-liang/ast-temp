package com.lc.ast.demo.ast;

public class Token {
    public enum TokenType {NUMBER, OPERATOR, FUNCTION, PARAMETER_VARIABLE, STR_PARAM, LPAREN, RPAREN, NEGATIVE_SIGN}

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }


    // Getters and other methods...
}