package com.study.kdy.chapter04.config;

import com.study.main.chapter04.spring.MemberDao;
import com.study.main.chapter04.spring.MemberRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppComponent {

    @Bean
    public MemberDao memberDao1() {
        return new MemberDao();
    }

    @Bean
    public MemberDao memberDao2() {
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService();
    }
}
