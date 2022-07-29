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

    @Order(1)
    @Test
    public void measureCalculateTime() {
        var calculator = aspectAppCtx.getBean(Calculator.class);

        var result = calculator.factorial(20);
        System.out.println("calc result: " + result);
    }

}
