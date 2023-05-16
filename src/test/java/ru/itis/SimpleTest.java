package ru.itis;

import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Expression;

public class SimpleTest {

    @Test
    public void a() {
        Expression expression = new Expression("-(-(-379.63930510803266+47)*sin(sqrt(abs((-281.24482840927567/2)+(-379.63930510803266+47))))--281.24482840927567*sin(sqrt(abs(-281.24482840927567-(-379.63930510803266+47)))))");
        System.out.println(expression.calculate());
    }
}
