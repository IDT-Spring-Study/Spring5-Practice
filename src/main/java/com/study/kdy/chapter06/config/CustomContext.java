package com.study.kdy.chapter06.config;

import com.study.kdy.chapter06.bean.Client;
import com.study.kdy.chapter06.bean.Client2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomContext {

    @Bean
    @Scope
    public Client client() {
        return new Client();
    }

    @Bean(initMethod = "connect", destroyMethod = "close")
    public Client2 client2() {
        var client2 = new Client2();
        client2.setHost("manual set host");
        return client2;
    }

}
