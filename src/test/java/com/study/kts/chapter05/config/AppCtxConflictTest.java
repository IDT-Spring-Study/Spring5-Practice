package com.study.kts.chapter05.config;

import com.study.kts.chapter05.dao.MemberDao;
import com.study.kts.support.PrintUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class AppCtxConflictTest {

    @Test
    @DisplayName("component 빈 이름 충돌")
    void conflict() {
        Assertions.assertThrows(BeanDefinitionStoreException.class,
                () -> new AnnotationConfigApplicationContext(AppConflictCxt.class));
    }

    @ComponentScan(basePackages = {"com.study.main.chapter05.spring", "com.study.main.chapter05.spring2"})
    static class AppConflictCxt {

    }


    @Test
    @DisplayName("수동 등록한 빈 충돌")
    void conflict2() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConflictCtx2.class);
        PrintUtils.printBeanInfo(ac);
    }

    @Configuration
    @ComponentScan(basePackages = {"com.study.kts.chapter05.dao"})
    static class AppConflictCtx2 {
        @Bean
        public MemberDao memberDao() {
            return new MemberDao();
        }
    }

}
