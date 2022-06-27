package com.study.main.chapter05.main;

import com.study.main.chapter05.config.AppCtxWithExclude;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class MainForExclude {

	private static ApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException {
		ctx = new AnnotationConfigApplicationContext(AppCtxWithExclude.class);

	}

}