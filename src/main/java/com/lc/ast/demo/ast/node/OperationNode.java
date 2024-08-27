package com.lc.ast.demo.ast.node;

public class OperationNode extends Node {
    private final String operator;
    private final Node left;
    private final Node right;

    public OperationNode(String operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
