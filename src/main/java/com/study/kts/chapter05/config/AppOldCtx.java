package com.study.kts.chapter05.config;

import com.study.kts.chapter05.dao.MemberDao;
import com.study.kts.chapter05.service.MemberRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppOldCtx {
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDao());
    }
}
