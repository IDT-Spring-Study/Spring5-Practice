package com.study.main.chapter05.main;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.study.main.chapter05.config.AppCtxManual1;
import com.study.main.chapter05.config.AppCtxManual2;

public class MainForManual {

	private static AbstractApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException {
		ctx = new AnnotationConfigApplicationContext(
				AppCtxManual1.class, AppCtxManual2.class);
		
		ctx.close();
	}

}