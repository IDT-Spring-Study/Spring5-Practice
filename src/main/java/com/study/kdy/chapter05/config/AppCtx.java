package com.study.kdy.chapter05.config;

import com.study.kdy.chapter05.service.ChangePasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.study.kdy.chapter05")
@Configuration
public class AppCtx {

    @Bean
    public ChangePasswordService changePasswordService() {
        return new ChangePasswordService();
    }

}
