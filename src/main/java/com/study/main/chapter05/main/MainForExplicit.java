package com.study.main.chapter05.main;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.study.main.chapter05.config.AppCtxWithExplicit;

public class MainForExplicit {

	private static AbstractApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException {
		ctx = new AnnotationConfigApplicationContext(
				AppCtxWithExplicit.class);
		
		ctx.close();
	}

}