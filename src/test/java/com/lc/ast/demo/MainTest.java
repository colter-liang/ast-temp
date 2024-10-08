package com.lc.ast.demo;

import com.lc.ast.demo.ast.Interpreter;
import com.lc.ast.demo.ast.Lexer;
import com.lc.ast.demo.ast.Parser;
import com.lc.ast.demo.ast.Token;
import com.lc.ast.demo.ast.node.Node;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    public void test8() {
        List<Double> result = (List<Double>) parse("F.US.NT.BON.INX.CHYO.99LVUSD999Q@scenario+F.CN.NT.BON.INX.CHYO.99LVUSD999Q@base/F.US.NT.BON.INX.CHYO.99LVUSD999Q@scenario");
        List<Double> excepted = List.of(3.0, 3.0, 3.0);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(excepted.get(i), result.get(i), 0.01);
        }
    }

    @Test
    public void test7() {
        assertEquals(-5.0, (Double) parse("3.0+ 1.0*(2.0+(-3.0))*8.0"), 0.01);
        assertEquals(8.0, (Double) parse("3.0+ 1.0*(2.0+(-3.0))*(-8.0+3)"), 0.01);
    }

    @Test
    public void test6() {
        String expression = "3.0+ 1.0*(2.0+3.0)*8.0";
        Double result = (Double) parse(expression);
        assertEquals(43.0, result, 0.01);
    }

    @Test
    public void test5() {
        List<Double> result = (List<Double>) parse("6+22.0e-2*lag(F.CN.NT.BON.INX.CHYO.99LVUSD999Q@base,2,quarter)");
        List<Double> excepted = List.of(7.1, 7.1, 7.1);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(excepted.get(i), result.get(i), 0.01);
        }
    }

    @Test
    public void test4() {
        List<Double> result = (List<Double>) parse("diff( (-1) + lag(F.US.NT.BON.INX.CHYO.99LVUSD999Q@scenario ,(-3), quarter) ,month) / lag(F.CN.NT.BON.INX.CHYO.99LVUSD999Q@base,2,quarter)");
        List<Double> excepted = List.of(0.2, 0.2, 0.2);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(excepted.get(i), result.get(i), 0.01);
        }
    }

    @Test
    public void test3() {
        List<Double> result = (List<Double>) parse("diff( (-1) + lag(F.US.NT.BON.INX.CHYO.99LVUSD999Q@scenario ,(-3), quarter) ,month)");
        List<Double> excepted = List.of(1.0, 1.0, 1.0);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(excepted.get(i), result.get(i), 0.01);
        }
    }

    @Test
    public void test2() {
        List<Double> result = (List<Double>) parse("lag(F.US.NT.BON.INX.CHYO.99LVUSD999Q@scenario ,(-3), quarter)");
        List<Double> excepted = List.of(-1.0, -1.0, -1.0);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(excepted.get(i), result.get(i), 0.01);
        }
    }

    @Test
    public void test1() {
        assertEquals(2.0, (Double) parse("3.1+-1.1"), 0.01);
        assertEquals(2.0, (Double) parse("3.1+(-1.1)"), 0.01);
        assertEquals(5.1, (Double) parse("2*3.1+(-1.1)"), 0.01);
        assertEquals(1.1, (Double) parse("3.3+2*(-1.1)*1"), 0.01);
    }


    private Object parse(String expression) {
        Lexer lexer = new Lexer(expression);
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token.getType() + ": " + token.getValue());
        }
        Parser parser = new Parser(tokens);
        Node ast = parser.parse();
        Map<String, List<Double>> variables = new HashMap<>();
        variables.put("F.US.NT.BON.INX.CHYO.99LVUSD999Q@scenario", List.of(1.0, 1.0, 1.0));
        variables.put("F.CN.NT.BON.INX.CHYO.99LVUSD999Q@base", List.of(2.0, 2.0, 2.0));
        Interpreter interpreter = new Interpreter(variables);
        Object result = interpreter.interpret(ast);
        System.out.println(result);
        return result;
    }
}