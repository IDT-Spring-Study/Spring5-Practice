package com.study.main.chapter08.main;

import com.study.main.chapter08.config.DbConfig;
import com.study.main.chapter08.config.DbQueryConfig;
import com.study.main.chapter08.dbquery.DbQuery;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainUsingDbQuery {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class,
				DbQueryConfig.class);

		DbQuery dbQuery = ctx.getBean(DbQuery.class);
		int count = dbQuery.count();
		System.out.println(count);
		ctx.close();
	}
}
