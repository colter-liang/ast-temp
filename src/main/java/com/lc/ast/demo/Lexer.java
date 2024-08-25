package com.lc.ast.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {
    //    public static final String NUMBER = "\\d+(\\.\\d+)?"; //default
//    public static final String NUMBER = "-?\\d+(\\.\\d+)?"; // () -
    public static final String NUMBER = "-?\\d+(\\.\\d+)?(e[-+]?\\d+)?";
    public static final String OPERATOR = "[+\\-*/]";
    public static final String METHOD = "diff|lag";
    public static final String PARAM = "INPUT\\.(A|B)";
    public static final String NUMBER2 = "\\d+";
    public static final String QM = "\\b(quarter|month)\\b";
    private final String input;
    private final Pattern pattern;
    private final Pattern patternNegative;

    public Lexer(String input) {
        this.input = input;
        this.pattern = Pattern.compile(
                //"(-?\\d+(\\.\\d+)?)|([+\\-*/()])|(diff|lag)|INPUT\\.(A|B)|\\d+|(\\b(quarter|month)\\b)"
                "(-?\\d+(\\.\\d+)?(e[-+]?\\d+)?)|([+\\-*/()])|([()])|(diff|lag)|INPUT\\.(A|B)|\\d+|(\\b(quarter|month)\\b)"

        );
        this.patternNegative = Pattern.compile("-");
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String match = matcher.group();
            if (match.matches(NUMBER)) {
                tokens.add(new Token(Token.TokenType.NUMBER, match));
            } else if (match.matches("[()]")) {
                if (match.equals("(")) {
                    tokens.add(new Token(Token.TokenType.LPAREN, match));
                } else {
                    tokens.add(new Token(Token.TokenType.RPAREN, match));
                }
            } else if (match.matches(OPERATOR)) {
                tokens.add(new Token(Token.TokenType.OPERATOR, match));
            } else if (match.matches(METHOD)) {
                tokens.add(new Token(Token.TokenType.FUNCTION, match));
            } else if (match.matches(PARAM)) {
                tokens.add(new Token(Token.TokenType.LIST_VARIABLE, match));
            } else if (match.matches(QM)) {
                tokens.add(new Token(Token.TokenType.TIME_UNIT, match));
            } else if ("-".equals(match)) {
                // Determine if '-' is a negative sign or a subtraction operator.
                Matcher matcherNegative = patternNegative.matcher(input.substring(matcher.end()));
                if (matcherNegative.find() && matcherNegative.start() == 0) {
                    // Check if the next character is a digit or '('.
                    if (Character.isDigit(input.charAt(matcher.end())) || input.charAt(matcher.end()) == '(') {
                        tokens.add(new Token(Token.TokenType.NEGATIVE_SIGN, match));
                    } else {
                        tokens.add(new Token(Token.TokenType.OPERATOR, match));
                    }
                } else {
                    tokens.add(new Token(Token.TokenType.OPERATOR, match));
                }
            }
        }
        return tokens;
    }
}