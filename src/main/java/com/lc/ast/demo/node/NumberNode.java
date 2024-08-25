package com.lc.ast.demo.node;

public class NumberNode extends Node {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
