package ru.itis.service;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class ExpressionUtils {

    public static String createExpressionString(String expression, Comparable<?>[] args) {
        for (int i = 0; i < args.length; i++) {
            expression = expression.replaceAll(String.format("(x%d)", i), args[i].toString());
        }
        return expression;
    }

    public static Expression createExpression(String expression, Argument ... arguments) {
        return new Expression(expression, arguments);
    }

    public static double calculateExpression(String expression, Comparable<?>[] args) {
        return new Expression(ExpressionUtils.createExpressionString(expression, args)).calculate();
    }
}
