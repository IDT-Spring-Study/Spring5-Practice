package com.study.main.chapter03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.study.main.chapter03.spring.MemberDao;
import com.study.main.chapter03.spring.MemberPrinter;

@Configuration
@Import({AppConf2.class})
public class AppConfImport {

	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}
}
