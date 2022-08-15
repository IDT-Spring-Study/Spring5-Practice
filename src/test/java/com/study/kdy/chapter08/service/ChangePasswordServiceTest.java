package com.study.kdy.chapter08.service;

import com.study.kdy.chapter08.config.AppCtx;
import com.study.kdy.chapter08.exception.WrongPasswordException;
import com.study.kdy.chapter08.model.Member;
import com.study.kdy.chapter08.model.MemberDao;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class ChangePasswordServiceTest {

    private ChangePasswordService changePasswordService;
    private MemberDao memberDao;

    private Member insertedMember;

    @BeforeAll
    public void setup() {
        var appCtx = new AnnotationConfigApplicationContext(AppCtx.class);
        changePasswordService = appCtx.getBean(ChangePasswordService.class);

        memberDao = appCtx.getBean(MemberDao.class);
        insertedMember = memberDao.insert(
                new Member("email", "password", "name", LocalDateTime.now())
        );
    }

    @AfterAll
    public void clear() {
        memberDao.delete(insertedMember.getId());
    }

    @Order(1)
    @Test
    public void 비밀번호변경() {
        // given
        var newPassword = "newPassword";

        // when
        changePasswordService.changePassword(insertedMember.getEmail(), insertedMember.getPassword(), newPassword);

        // then
        var member = memberDao.selectByEmail(insertedMember.getEmail());
        assertThat(member.getPassword()).isEqualTo(newPassword);
    }

    @Order(2)
    @Test
    public void 비밀번호변경_실패_기존번호다름() {
        // given
        var newPassword = "newPassword";

        // when
        assertThrows(WrongPasswordException.class,
                () -> changePasswordService.changePassword(insertedMember.getEmail(), "wrong", newPassword));

        // then
        var member = memberDao.selectByEmail(insertedMember.getEmail());
        assertThat(member.getPassword()).isNotEqualTo(newPassword);
    }

}
