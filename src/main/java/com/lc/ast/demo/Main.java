package com.lc.ast.demo;

import com.lc.ast.demo.node.Node;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        String expression = "1+2";
//        String expression = "3+1*2 + 7*8";
//        String expression = "diff( (-1) + lag(INPUT.A ,(-3), quarter) ,month)+lag(INPUT.B,2,quarter)";
//        String expression = "diff( 2e-2 + (-2)) + lag(INPUT.A ,(-3), quarter) ,month)+lag(INPUT.B,2,quarter)";
        String expression = " -1 + -1 * lag( INPUT.A,2e3 + -2 , quarter) + (-2e1) - 3e-5";
        Lexer lexer = new Lexer(expression);
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token.getType() + ": " + token.getValue());
        }
        Parser parser = new Parser(tokens);
        Node ast = parser.parse();
        Map<String, List<Double>> variables = new HashMap<>();
        variables.put("INPUT.A", List.of(1.0, 1.0, 1.0));
        variables.put("INPUT.B", List.of(2.0, 2.0, 2.0));
        Interpreter interpreter = new Interpreter(variables);
        Object result = interpreter.interpret(ast);
        System.out.println(result);
    }
}