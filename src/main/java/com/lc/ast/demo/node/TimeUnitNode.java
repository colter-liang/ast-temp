package com.lc.ast.demo.node;

public class TimeUnitNode extends Node {
    private final String unit;

    public TimeUnitNode(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}