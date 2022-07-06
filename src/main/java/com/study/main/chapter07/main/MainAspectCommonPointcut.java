package com.study.main.chapter07.main;

import com.study.main.chapter07.chap07.Calculator;
import com.study.main.chapter07.config.AppCtxWithCommonPointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectCommonPointcut {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtxWithCommonPointcut.class);

		Calculator cal = ctx.getBean("calculator", Calculator.class);
		cal.factorial(7);
		cal.factorial(7);
		cal.factorial(5);
		cal.factorial(5);
		ctx.close();
	}

}
