package com.study.kdy.chapter07.config;

import com.study.kdy.chapter07.service.Calculator;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class AspectAppCtxTest {

    private AnnotationConfigApplicationContext aspectAppCtx;

    @BeforeAll
    public void setup() {
        aspectAppCtx = new AnnotationConfigApplicationContext(AspectAppCtx.class);
    }

    @AfterAll
    public void close() {
        aspectAppCtx.close();
    }

    @Order(1)
    @Test
    public void measureCalculateTime() {
        var calculator = aspectAppCtx.getBean(Calculator.class);

        var result = calculator.factorial(20);
        System.out.println("calc result: " + result);
    }

//    @Order(2)
//    @Test
//    public void measureCalculateTimeProxy() {
//        var calculator = aspectAppCtx.getBean("calculator", RecCalculator.class);
//
//        var result = calculator.factorial(20);
//        System.out.println("calc result: " + result);
//    }

    @Order(3)
    @Test
    public void Advice_여러개적용() {
        var calculator = aspectAppCtx.getBean(Calculator.class);

        calculator.factorial(7);
        calculator.factorial(7);
        calculator.factorial(5);
        calculator.factorial(5);
    }

}
