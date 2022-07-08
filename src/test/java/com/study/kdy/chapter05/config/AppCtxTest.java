package com.study.kdy.chapter05.config;

import com.study.kdy.chapter05.aop.AspectClass;
import com.study.kdy.chapter05.service.ChangePasswordService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class AppCtxTest {

    private AnnotationConfigApplicationContext appCtx;

    @BeforeAll
    public void setup() {
        this.appCtx = new AnnotationConfigApplicationContext(AppCtx.class);
    }

    @Order(1)
    @Test
    public void _1_Aspect_컴포넌트스캔_제외_확인() {
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> appCtx.getBean(AspectClass.class));
    }

    @Order(2)
    @Test
    public void _2_Bean_Component_하나만등록_확인() {
        var changePasswordService = appCtx.getBean(ChangePasswordService.class);

        assertThat(changePasswordService).isNotNull();
    }

}
