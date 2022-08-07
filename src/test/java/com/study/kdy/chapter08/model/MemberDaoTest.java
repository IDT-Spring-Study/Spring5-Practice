package com.study.kdy.chapter08.model;

import com.study.kdy.chapter08.config.DbConfig;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class MemberDaoTest {

    private MemberDao memberDao;

    @BeforeAll
    public void setup() {
        memberDao = new AnnotationConfigApplicationContext(DbConfig.class).getBean(MemberDao.class);
    }

    @Order(1)
    @Test
    public void selectTest() {
        // given
        var errorEmail = "eeeee";

        // when
        var result = memberDao.selectByEmail(errorEmail);

        // then
        assertThat(result).isNull();
    }

    @Order(2)
    @Test
    public void insertTest() {
        // given
        var member = new Member("email3", "pwd", "name", LocalDateTime.now());

        // when
        var result = memberDao.insert(member);

        // then
        assertThat(result.getClass()).isEqualTo(Member.class);
        assertThat(result.getEmail()).isEqualTo(member.getEmail());
        System.out.printf("inserted memberId: %d\n", result.getId());
    }

}
