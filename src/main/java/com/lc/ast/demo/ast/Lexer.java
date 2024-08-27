package com.lc.ast.demo.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {
    //    public static final String NUMBER = "\\d+(\\.\\d+)?"; //default
//    public static final String NUMBER = "-?\\d+(\\.\\d+)?"; // () -
    public static final String NUMBER = "-?\\d+(\\.\\d+)?(e[-+]?\\d+)?";
    public static final String OPERATOR = "[+\\-*/]";
    public static final String METHOD = "diff|lag|\\+rel|lndiff";
    public static final String PARAM = "[A-Za-z]\\.([A-Za-z]{2})\\.([A-Za-z0-9\\\\.]{27})@(scenario|base)";
    public static final String NUMBER2 = "\\d+";
    public static final String STR_PARAM = "\\b(quarter|month|year|q2m|m2q|linear)\\b";
    private final String input;
    private final Pattern pattern;
    private final Pattern patternNegative;

    public Lexer(String input) {
        this.input = input;
        String formatted = "(%s)|(%s)|(%s)|(%s)|(%s)|([()])".formatted(NUMBER, OPERATOR, METHOD, PARAM, STR_PARAM);
        this.pattern = Pattern.compile(formatted);
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
                tokens.add(new Token(Token.TokenType.PARAMETER_VARIABLE, match));
            } else if (match.matches(STR_PARAM)) {
                tokens.add(new Token(Token.TokenType.STR_PARAM, match));
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