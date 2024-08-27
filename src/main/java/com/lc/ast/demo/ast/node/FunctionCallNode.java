package com.lc.ast.demo.ast.node;

import java.util.List;

public class FunctionCallNode extends Node {
    private final String functionName;
    private final List<Node> arguments;

    public FunctionCallNode(String functionName, List<Node> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Node> getArguments() {
        return arguments;
    }
}