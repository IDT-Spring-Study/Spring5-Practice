package com.study.main.chapter10.config;

import com.study.main.chapter10.chap10.HelloController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

	@Bean
	public HelloController helloController() {
		return new HelloController();
	}

}
