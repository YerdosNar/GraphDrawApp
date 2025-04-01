package com.example.finalassignment.ui.examples;

public class Example {
    private String function;
    private String description;
    private boolean isPredefined;

    public Example(String function, String description, boolean isPredefined) {
        this.function = function;
        this.description = description;
        this.isPredefined = isPredefined;
    }

    public String getFunction() {
        return function;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPredefined() {
        return isPredefined;
    }
}