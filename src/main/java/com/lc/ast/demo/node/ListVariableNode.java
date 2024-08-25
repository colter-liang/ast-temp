package com.lc.ast.demo.node;

public class ListVariableNode extends Node {
    private final String variableName;

    public ListVariableNode(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}