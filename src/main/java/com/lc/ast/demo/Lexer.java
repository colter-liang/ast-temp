package com.lc.ast.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {
    private final String input;
    private final Pattern pattern;

    public Lexer(String input) {
        this.input = input;
        this.pattern = Pattern.compile(
                "(\\d+(\\.\\d+)?)|([+\\-*/()])|(diff|lag)|INPUT\\.(A|B)|\\d+|(\\b(quarter|month)\\b)"
        );
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String match = matcher.group();
            if (match.matches("\\d+(\\.\\d+)?")) {
                tokens.add(new Token(Token.TokenType.NUMBER, match));
            } else if (match.matches("[+\\-*/()]")) {
                tokens.add(new Token(Token.TokenType.OPERATOR, match));
            } else if (match.matches("diff|lag")) {
                tokens.add(new Token(Token.TokenType.FUNCTION, match));
            } else if (match.matches("INPUT\\.(A|B)")) {
                tokens.add(new Token(Token.TokenType.LIST_VARIABLE, match));
            } else if (match.matches("\\d+")) {
                tokens.add(new Token(Token.TokenType.NUMBER, match));
            } else if (match.matches("\\b(quarter|month)\\b")) {
                tokens.add(new Token(Token.TokenType.TIME_UNIT, match));
            }
        }
        return tokens;
    }
}