package com.study.kdy.chapter03;

public class ExceptionMain {

    private static class ExceptionClass {
        private void nestedException() {
            if (true) {
                throw new RuntimeException("nestedException!");
            }
        }

        public void outerException() {
            try {
                nestedException();
            } catch (RuntimeException e) {
                throw new RuntimeException("outerException!", e);
            }
        }
    }

    public static void main(String[] args) {
        var exceptionClass = new ExceptionClass();
        exceptionClass.outerException();
    }



}
