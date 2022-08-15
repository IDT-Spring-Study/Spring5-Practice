package com.study.kdy.chapter08.model;

import com.study.kdy.chapter08.config.DbConfig;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class MemberDaoTest {

    private MemberDao memberDao;

    private Member insertedMember;

    @BeforeAll
    public void setup() {
        memberDao = new AnnotationConfigApplicationContext(DbConfig.class).getBean(MemberDao.class);
    }

    @AfterAll
    public void clearData() {
        memberDao.delete(insertedMember.getId());
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
    @Transactional
    @Test
    public void insertTest() {
        // given
        var member = new Member("email7", "pwd", "name", LocalDateTime.now());

        // when
        insertedMember = memberDao.insert(member);

        // then
        assertThat(insertedMember.getClass()).isEqualTo(Member.class);
        assertThat(insertedMember.getEmail()).isEqualTo(member.getEmail());
        System.out.printf("inserted memberId: %d\n", insertedMember.getId());
    }

}
