package com.study.kdy.chapter05.config;

import com.study.kdy.chapter05.service.ChangePasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.study.kdy.chapter05")
public class AppCtx {

    @Bean
    public ChangePasswordService changePasswordService() {
        return new ChangePasswordService();
    }

}
