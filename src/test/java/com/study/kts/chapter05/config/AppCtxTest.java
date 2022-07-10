package com.study.kts.chapter05.config;

import com.study.kts.support.PrintUtils;
import com.study.main.chapter05.config.AppCtx;
import com.study.main.chapter05.spring.MemberRegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppCtxTest {

    @Test
    @DisplayName("config 등록 빈 조회")
    void findBeanByConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppOldCtx.class);

        com.study.kts.chapter05.service.MemberRegisterService memberRegSvc
                = ac.getBean("memberRegSvc", com.study.kts.chapter05.service.MemberRegisterService.class);

        assertThat(memberRegSvc).isInstanceOf(com.study.kts.chapter05.service.MemberRegisterService.class);
    }

    @Test
    @DisplayName("component 빈 등록 조회")
    void getBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("memberRegSvc", MemberRegisterService.class));

        MemberRegisterService memberRegisterService
                = ac.getBean("memberRegisterService", MemberRegisterService.class);
        assertThat(memberRegisterService).isInstanceOf(MemberRegisterService.class);
    }

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);
        PrintUtils.printBeanInfo(ac);
    }
}
