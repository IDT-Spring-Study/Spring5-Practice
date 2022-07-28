package com.study.kdy.chapter07.service;

public class CalculatorImpl implements Calculator {

    @Override
    public long factorial(long num) {
        long result = 1;
        for (long i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }

}
