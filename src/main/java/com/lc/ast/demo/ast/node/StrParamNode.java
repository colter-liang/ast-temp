package com.lc.ast.demo.ast.node;

public class StrParamNode extends Node {
    private final String unit;

    public StrParamNode(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}