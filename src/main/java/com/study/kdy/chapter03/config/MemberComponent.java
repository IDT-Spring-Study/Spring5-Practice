package com.study.kdy.chapter03.config;

import com.study.kdy.chapter03.model.MemberDao;
import com.study.kdy.chapter03.service.ChangePasswordService;
import com.study.kdy.chapter03.service.MemberRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberComponent {

    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegisterService() {
        return new MemberRegisterService(memberDao());
    }

    @Bean
    public ChangePasswordService changePasswordService() {
        ChangePasswordService changePasswordService = new ChangePasswordService();
//        changePasswordService.setMemberDao(memberDao());
        return changePasswordService;
    }

}
