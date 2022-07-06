package com.study.main.chapter07.config;

import com.study.main.chapter07.aspect2.CacheAspect2;
import com.study.main.chapter07.aspect2.ExeTimeAspect2;
import com.study.main.chapter07.chap07.Calculator;
import com.study.main.chapter07.chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppCtxWithCommonPointcut {

	@Bean
	public CacheAspect2 cacheAspect() {
		return new CacheAspect2();
	}

	@Bean
	public ExeTimeAspect2 exeTimeAspect() {
		return new ExeTimeAspect2();
	}

	@Bean
	public Calculator calculator() {
		return new RecCalculator();
	}

}
