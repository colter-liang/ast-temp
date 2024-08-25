package com.lc.ast.demo;


import com.lc.ast.demo.node.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Interpreter {

    private Map<String, List<Double>> variables;

    public Interpreter(Map<String, List<Double>> variables) {
        this.variables = variables;
    }

    public Object interpret(Node node) {
        if (node instanceof NumberNode) {
            return ((NumberNode) node).getValue();
        } else if (node instanceof OperationNode) {
            OperationNode opNode = (OperationNode) node;
            Object leftValue = interpret(opNode.getLeft());
            Object rightValue = interpret(opNode.getRight());
            switch (opNode.getOperator()) {
                case "+":
                case "-":
                case "*":
                case "/":
                    return calc(leftValue, rightValue, opNode.getOperator());
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + opNode.getOperator());
            }
        } else if (node instanceof FunctionCallNode) {
            FunctionCallNode funcNode = (FunctionCallNode) node;
            Object[] argValues = new Object[funcNode.getArguments().size()];
            for (int i = 0; i < funcNode.getArguments().size(); i++) {
                argValues[i] = interpret(funcNode.getArguments().get(i));
            }
            if (funcNode.getFunctionName().equals("diff")) {
                return FunctionUtil.diff((List<Double>) argValues[0], (String) argValues[1]);
            } else if (funcNode.getFunctionName().equals("lag")) {
                return FunctionUtil.lag((List<Double>) argValues[0], (Double) argValues[1], (String) argValues[2]);
            } else {
                throw new RuntimeException("Unsupported Function:" + funcNode.getFunctionName());
            }
        } else if (node instanceof ListVariableNode) {
            ListVariableNode varNode = (ListVariableNode) node;
            Object varValue = variables.get(varNode.getVariableName());
            if (varValue == null) {
                throw new IllegalArgumentException("Undefined variable: " + varNode.getVariableName());
            }
            return varValue; // Assuming the variable holds a single value
        } else if (node instanceof TimeUnitNode) {
            TimeUnitNode timeUnitNode = (TimeUnitNode) node;
            return timeUnitNode.getUnit();
        }
        throw new IllegalArgumentException("Unsupported node type: " + node.getClass().getSimpleName());
    }

    private Object calc(Object leftValue, Object rightValue, String operator) {
        List<Double> result = new ArrayList<>();
        if (leftValue instanceof Double && rightValue instanceof List rValues) {
            for (Object rValue : rValues) {
                extracted(operator, (Double) leftValue, (Double) rValue, result);
            }
            return result;
        } else if (leftValue instanceof List lValues && rightValue instanceof Double) {
            for (Object lv : lValues) {
                extracted(operator, (Double) lv, (Double) rightValue, result);
            }
            return result;
        } else if (leftValue instanceof List lValues && rightValue instanceof List rValues) {
            for (int i = 0; i < rValues.size(); i++) {
                Object lValue = lValues.get(i);
                Object rValue = rValues.get(i);
                extracted(operator, (Double) lValue, (Double) rValue, result);
            }
            return result;
        }else if(leftValue instanceof Double lv && rightValue instanceof Double vValue){
            extracted(operator, lv, vValue, result);
            return result.get(0);
        }

        throw new RuntimeException("Unsupport operation:" + leftValue.getClass() + " " + rightValue.getClass());
    }

    private static void extracted(String operator, Double lValue, Double rValue, List<Double> result) {
        if (Objects.equals(operator, "+")) {
            Double temp = lValue + rValue;
            result.add(temp);
        } else if (Objects.equals(operator, "-")) {
            Double temp = lValue - rValue;
            result.add(temp);
        } else if (Objects.equals(operator, "*")) {
            Double temp = lValue * rValue;
            result.add(temp);
        } else if (Objects.equals(operator, "/")) {
            Double temp = lValue / rValue;
            result.add(temp);
        } else {
            throw new RuntimeException("Not Supported:" + operator);
        }
    }

}