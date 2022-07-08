package com.study.kdy.chapter05.config;

import com.study.kdy.chapter05.service.ChangePasswordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AssignableExcludeAppCtxTest {

    @Test
    public void _1_컴포넌트스캔_특정클래스제외() {
        var appCtx = new AnnotationConfigApplicationContext(AssignableExcludeAppCtx.class);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> appCtx.getBean(ChangePasswordService.class)
        );
    }
}
