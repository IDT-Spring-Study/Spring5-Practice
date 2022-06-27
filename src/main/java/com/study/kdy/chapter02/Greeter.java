package com.study.kdy.chapter02;

public class Greeter {

    private String format;

    public String greet(String guest) {
        return String.format(format, guest);
    }

//    public void setFormat(String format) {
//        this.format = format;
//    }


    public void setFormat(String format) {
        this.format = format;
    }
}
