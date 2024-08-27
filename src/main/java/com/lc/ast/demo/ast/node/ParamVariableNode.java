package com.lc.ast.demo.ast.node;

public class ParamVariableNode extends Node {
    private final String variableName;

    public ParamVariableNode(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}