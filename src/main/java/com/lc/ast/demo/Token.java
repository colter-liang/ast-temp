package com.lc.ast.demo;

public class Token {
    public enum TokenType {NUMBER, OPERATOR, FUNCTION, LIST_VARIABLE, TIME_UNIT, LPAREN, RPAREN, NEGATIVE_SIGN}

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


    // Getters and other methods...
}