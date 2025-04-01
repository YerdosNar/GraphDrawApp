package com.example.finalassignment.ui.graphdraw;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FunctionParser {
    public static double evaluate(String expression, double x) {
        try {
            // Build expression with x as variable
            Expression e = new ExpressionBuilder(expression)
                    .variables("x")
                    .build()
                    .setVariable("x", x);

            return e.evaluate();
        } catch (Exception e) {
            // Return 0 if there's any error in the expression
            return 0;
        }
    }
}