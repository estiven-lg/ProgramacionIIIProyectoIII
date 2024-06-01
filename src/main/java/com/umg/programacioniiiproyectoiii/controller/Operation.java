package com.umg.programacioniiiproyectoiii.controller;

import com.umg.programacioniiiproyectoiii.model.SheetData;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Operation {

    private Operation left;
    private Operation right;
    private String label;
    private Double value;
    public static Map<String, Double> variables = new HashMap<>();

    public Operation(String label, SheetData data) {
        this.label = label.toUpperCase();

        if (Armectic.isNumeric(label)) {
            this.value = Double.valueOf(label);
            return;
        }

        if (Armectic.getHierarchy(label) != -1) {
            this.value = null;
            return;
        }

        int y = this.label.charAt(0) - 64;
        int x = Integer.parseInt(this.label.substring(1));


        this.value = Double.valueOf(data.resolve(x, y, true));
        variables.put(this.label, this.value);
    }

    public Operation getLeft() {
        return left;
    }

    public void setLeft(Operation left) {
        this.left = left;
    }

    public Operation getRight() {
        return right;
    }

    public void setRight(Operation right) {
        this.right = right;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double resolveOperation() {
        if (Armectic.getHierarchy(this.getLabel()) != -1) {
            return Armectic.calculate(this.getLeft().resolveOperation(), this.getRight().resolveOperation(), this.getLabel());
        }
        return this.getValue();
    }

    static public Operation createOperation(String operation, SheetData data) {
        Stack<Operation> tmpStack = new Stack<>();
        String[] listValue = Armectic.operationToPostfix(operation);
        for (String str : listValue) {
            if (Armectic.isAlpha(str.charAt(0)) || Armectic.isNumeric(str)) {
                Operation node = new Operation(str, data);
                tmpStack.push(node);
            } else {
                Operation node = new Operation(str, data);
                node.setRight(tmpStack.pop());
                node.setLeft(tmpStack.pop());

                tmpStack.push(node);
            }
        }
        return tmpStack.pop();
    }
}
