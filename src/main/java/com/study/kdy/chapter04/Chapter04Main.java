package com.study.kdy.chapter04;

import com.study.kdy.chapter04.config.AppComponent;
import com.study.kdy.chapter04.service.MemberRegisterService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Chapter04Main {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AppComponent.class);

        var memberRegisterService = ctx.getBean(
                "memberRegSvc", MemberRegisterService.class
        );

        memberRegisterService.print("email");
    }

}
