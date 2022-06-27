package com.study.main.chapter03.typehier;

public class ConsolePrinter implements Printer {

	@Override
	public void print(String msg) {
		System.out.println(msg);
	}

}
