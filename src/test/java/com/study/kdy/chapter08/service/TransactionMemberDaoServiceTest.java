package com.study.kdy.chapter08.service;

import com.study.kdy.chapter08.config.AppCtx;
import com.study.kdy.chapter08.dto.MemberInsertReqDto;
import com.study.kdy.chapter08.model.Member;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class TransactionMemberDaoServiceTest {

    private ApplicationContext appCtx;
    private TransactionMemberDaoService transactionMemberDaoService;

    private Member insertedMember;

    @BeforeAll
    public void setup() {
        appCtx = new AnnotationConfigApplicationContext(AppCtx.class);
        transactionMemberDaoService = appCtx.getBean(TransactionMemberDaoService.class);
    }

    @AfterAll
    public void clear() {
        Optional.ofNullable(insertedMember).ifPresent(
                member -> transactionMemberDaoService.delete(member.getId())
        );
    }

    @Order(1)
    @Test
    public void 트랜잭션_성공() {
        // given
        var reqDto = MemberInsertReqDto.builder()
                .email("email")
                .password("password")
                .name("name")
                .registerDateTime(LocalDateTime.now())
                .build();

        // when
        insertedMember = transactionMemberDaoService.insertOrThrow(reqDto, false);

        // then
        assertThat(insertedMember.getEmail()).isEqualTo(reqDto.getEmail());
        assertThat(insertedMember.getPassword()).isEqualTo(reqDto.getPassword());
        assertThat(insertedMember.getName()).isEqualTo(reqDto.getName());
    }

    @Order(2)
    @Test
    public void 트랜잭션_성공_어노테이션메서드_직접실행() {
        // given
        var reqDto = MemberInsertReqDto.builder()
                .email("email2")
                .password("password2")
                .name("name2")
                .registerDateTime(LocalDateTime.now())
                .build();

        // when
        assertThrows(RuntimeException.class,
                () -> transactionMemberDaoService.insertOrThrow(reqDto, true)
        );

        // then
        var selectedMember = transactionMemberDaoService.selectByEmail(reqDto.getEmail());
        assertThat(selectedMember).isNull();
    }

    @Order(3)
    @Test
    public void 트랜잭션_실패_일반메서드_직접호출() {
        // given
        var reqDto = MemberInsertReqDto.builder()
                .email("email3")
                .password("password3")
                .name("name3")
                .registerDateTime(LocalDateTime.now())
                .build();

        // when
        assertThrows(RuntimeException.class,
                () -> transactionMemberDaoService.nonTransactionalInsertWithThrow(reqDto)
        );

        // then
        var selectedMember = transactionMemberDaoService.selectByEmail(reqDto.getEmail());
        assertThat(selectedMember.getName()).isEqualTo(reqDto.getName());

        // delete member
        transactionMemberDaoService.delete(selectedMember.getId());
    }

    @Order(4)
    @Test
    public void 트랜잭션_성공_트랜잭션메서드_래핑호출() { // TODO: 실패... 다시 이해가 필요함.
        // given
        var reqDto = MemberInsertReqDto.builder()
                .email("email4")
                .password("password4")
                .name("name4")
                .registerDateTime(LocalDateTime.now())
                .build();

        // when
        var wrappingService = appCtx.getBean(TransactionWrappingService.class);
        assertThrows(RuntimeException.class,
                () -> wrappingService.insertAndThrow(reqDto)
        );

        // then
        var selectedMember = transactionMemberDaoService.selectByEmail(reqDto.getEmail());
        assertThat(selectedMember).isNull();
    }

}

