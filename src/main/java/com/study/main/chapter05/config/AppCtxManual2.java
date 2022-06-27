package com.study.main.chapter05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.main.chapter05.spring2.MemberRegisterService;

@Configuration
public class AppCtxManual2 {

	@Bean
	public MemberRegisterService memberRegisterService() {
		return new MemberRegisterService();
	}
}
