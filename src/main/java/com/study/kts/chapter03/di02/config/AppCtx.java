package com.study.kts.chapter03.di02.config;

import com.study.kts.chapter03.di02.dao.MemberDao;
import com.study.kts.chapter03.di02.printer.MemberInfoPrinter;
import com.study.kts.chapter03.di02.printer.MemberListPrinter;
import com.study.kts.chapter03.di02.printer.MemberPrinter;
import com.study.kts.chapter03.di02.service.MemberRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCtx {

    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegisterService() {
        return new MemberRegisterService(memberDao());
    }

    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter() {
        return new MemberListPrinter(memberDao(), memberPrinter());
    }

    @Bean
    public MemberInfoPrinter infoPrinter() {
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setPrinter(memberPrinter());
        infoPrinter.setMemberDao(memberDao());
        return infoPrinter;
    }
}
