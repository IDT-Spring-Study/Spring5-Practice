package com.study.kdy.chapter04;

import com.study.kdy.chapter04.config.AppComponent;
import com.study.main.chapter04.spring.MemberRegisterService;
import com.study.main.chapter04.spring.RegisterRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MemberRegisterServiceTest {

    private final MemberRegisterService memberRegisterService;

    public MemberRegisterServiceTest() {
        var ctx = new AnnotationConfigApplicationContext(AppComponent.class);
        memberRegisterService = ctx.getBean("memberRegSvc", MemberRegisterService.class);
    }

    @Test
    void 최초_사용자_등록() {
        // given
        RegisterRequest req = new RegisterRequest();
        req.setEmail("idean3885@naver.com");
        req.setName("kdy");
        req.setPassword("password1!");
        req.setConfirmPassword("password1!");

        // when
        var memberId = memberRegisterService.regist(req);

        // then
        Assertions.assertThat(memberId).isEqualTo(1);
    }

}
