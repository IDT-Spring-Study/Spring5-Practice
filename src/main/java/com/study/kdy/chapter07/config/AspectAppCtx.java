package com.study.kdy.chapter07.config;

import com.study.kdy.chapter07.aspect.CacheAspect;
import com.study.kdy.chapter07.aspect.ExeTimeAspect;
import com.study.kdy.chapter07.service.Calculator;
import com.study.kdy.chapter07.service.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

@Configuration
@EnableAspectJAutoProxy
public class AspectAppCtx {

    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }

    @Bean
    public CacheAspect cacheAspect() {
        return new CacheAspect();
    }

    @Bean
    public Calculator calculator() {
        return new RecCalculator();
    }

}
