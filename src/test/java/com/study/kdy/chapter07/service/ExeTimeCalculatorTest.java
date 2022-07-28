package com.study.kdy.chapter07.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExeTimeCalculatorTest {

    @Order(1)
    @Test
    public void CalculatorImplTest() {
        var calculator = new ExeTimeCalculator(new CalculatorImpl());
        var result = calculator.factorial(20);

        System.out.println(result);
    }

    @Order(2)
    @Test
    public void RecCalculatorTest() {
        var calculator = new ExeTimeCalculator(new RecCalculator());
        var result = calculator.factorial(20);

        System.out.println(result);
    }

}
