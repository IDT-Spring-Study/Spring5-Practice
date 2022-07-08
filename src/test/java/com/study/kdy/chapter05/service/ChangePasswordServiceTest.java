package com.study.kdy.chapter05.service;

import com.study.kdy.chapter05.config.AppCtx;
import com.study.kdy.chapter05.exception.MemberNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangePasswordServiceTest {

    private static ApplicationContext ctx = null;

    @Test
    public void componentScan_Test() {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        var changePasswordService = ctx.getBean(ChangePasswordService.class);
        assertThat(changePasswordService).isNotNull();
        Assertions.assertThrows(MemberNotFoundException.class,
                () -> changePasswordService.changePassword("test.email", "password1!", "password2@"));
    }
}
