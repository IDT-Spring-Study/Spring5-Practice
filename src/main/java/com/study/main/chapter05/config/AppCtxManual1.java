package com.study.main.chapter05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.main.chapter05.spring.MemberDao;
import com.study.main.chapter05.spring.MemberRegisterService;

@Configuration
public class AppCtxManual1 {
	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}

	@Bean
	public MemberRegisterService memberRegisterService() {
		return new MemberRegisterService();
	}
}
