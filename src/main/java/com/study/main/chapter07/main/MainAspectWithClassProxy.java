package com.study.main.chapter07.main;

import com.study.main.chapter07.chap07.RecCalculator;
import com.study.main.chapter07.config.AppCtxWithClassProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithClassProxy {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtxWithClassProxy.class);

		RecCalculator cal = ctx.getBean("calculator", RecCalculator.class);
		long fiveFact = cal.factorial(5);
		System.out.println("cal.factorial(5) = " + fiveFact);
		System.out.println(cal.getClass().getName());
		ctx.close();
	}

}
