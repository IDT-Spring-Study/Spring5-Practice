package com.study.kts.chapter06.config;


import com.study.kts.chapter06.Client;
import com.study.kts.chapter06.Client2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ClientCtx {

    @Bean
    public Client client() {
        Client client = new Client();
        client.setHost("host");
        return client;
    }

    @Bean
    @Scope("prototype")
    public Client2 client2() {
        return new Client2();
    }
}
