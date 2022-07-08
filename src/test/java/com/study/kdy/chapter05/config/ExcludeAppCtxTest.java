package com.study.kdy.chapter05.config;

import com.study.kdy.chapter05.service.ChangePasswordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExcludeAppCtxTest {

    @Order(1)
    @Test
    public void _1_컴포넌트스캔_제외() {
        var applicationContext = new AnnotationConfigApplicationContext(ExcludeAppCtx.class);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> applicationContext.getBean(ChangePasswordService.class)
        );
    }

}
