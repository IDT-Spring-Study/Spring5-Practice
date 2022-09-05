package com.study.main.chapter17.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.main.chapter17.spring.Info;

@Configuration
public class InfoConfig {

	@Bean
	public Info info() {
		return new Info();
	}

}
