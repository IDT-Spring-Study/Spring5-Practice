package com.study.kts.chapter03.di02.config;

import com.study.kts.chapter03.di02.config.AppCtx;
import com.study.kts.chapter03.di02.service.MemberRegisterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppCtxTest {

    @Test
    @DisplayName("빈 객체 정상 조회")
    void findBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);

        MemberRegisterService memberRegisterService
                = ac.getBean("memberRegisterService", MemberRegisterService.class);

        assertThat(memberRegisterService).isInstanceOf(MemberRegisterService.class);
    }

    @Test
    @DisplayName("빈 객체 없는 이름(앞글자 대문자)으로 조회")
    void findBeanByNotValidName() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);

        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("MemberRegisterService", MemberRegisterService.class));
    }
}