package com.study.kts.chapter05.config;

import com.study.main.chapter05.config.ManualBean;
import com.study.main.chapter05.spring.MemberDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

public class AppCtxWithExcludeTest {

    @Test
    @DisplayName("config 등록 빈 조회")
    void findBeanByConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtxWithExclude.class);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("memberDao", MemberDao.class));

    }

    @ComponentScan(basePackages = {"com.study.kts.chapter05.dao"},
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ManualBean.class)
            })
    static class AppCtxWithExclude {
    }
}
