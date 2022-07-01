package com.study.kdy.chapter04.config;

import com.study.kdy.chapter04.service.MemberRegisterService;
import com.study.main.chapter04.spring.MemberDao;
import com.study.main.chapter04.spring.MemberPrinter;
import com.study.main.chapter04.spring.MemberSummaryPrinter;
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
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
    }

    @Bean
    public MemberSummaryPrinter memberPrinter2() {
        return new MemberSummaryPrinter();
    }

    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService();
    }
}
