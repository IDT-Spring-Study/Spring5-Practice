package com.study.kts.chapter04;

import com.study.kts.chapter04.printer.MemberInfoPrinter;
import com.study.kts.chapter04.printer.MemberListPrinter;
import com.study.kts.chapter04.printer.MemberListPrinterNotQualifier;
import com.study.main.chapter04.spring.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestMethodOrder(MethodOrderer.MethodName.class)
public class Chapter04Test {

    private ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtxUseQualifierConfig.class);
    private MemberDao memberDao;
    private RegisterRequest request;

    @BeforeEach
    void setUp() {
        memberDao = ac.getBean("memberDao", MemberDao.class);
        request = new RegisterRequest();
        request.setEmail("kts@test.com");
        request.setName("김태수");
        request.setPassword("123");
        request.setConfirmPassword("123");
    }

    @Test
    @DisplayName("autowired 주입 테스트")
    void autowiredTest() {
        MemberRegisterService memberRegisterService =
                ac.getBean("memberRegisterService", MemberRegisterService.class);

        long memberId = memberRegisterService.regist(request);

        assertThat(memberId).isEqualTo(1L);
        assertThat(memberDao.selectByEmail(request.getEmail())
                .getId()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("중복된 타입 빈 주입시 에러 발생 테스트")
    void duplicatedBeanTypeQualifierTest() {
        assertThrows(UnsatisfiedDependencyException.class,
                () -> new AnnotationConfigApplicationContext(AppCtxDuplicatedBeanTypeConfig.class));
    }

    @Test
    @DisplayName("@Qualifier 어노테이션 정상 동작 테스트")
    void qualifierTest() {
        memberDao.insert(new Member(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                LocalDateTime.now()));

        MemberListPrinter listPrinter =
                ac.getBean("listPrinter", MemberListPrinter.class);

        listPrinter.printAll();
    }

    @Configuration
    static class CommonConfig {
        @Bean
        public MemberDao memberDao() {
            return new MemberDao();
        }

        @Bean
        public MemberRegisterService memberRegisterService() {
            return new MemberRegisterService();
        }
    }

    @Configuration
    static class AppCtxDuplicatedBeanTypeConfig extends CommonConfig {
        @Bean
        public MemberPrinter memberPrinter() {
            return new MemberPrinter();
        }

        @Bean
        public MemberPrinter summaryPrinter() {
            return new MemberSummaryPrinter();
        }

        @Bean
        public MemberListPrinterNotQualifier listPrinter() {
            return new MemberListPrinterNotQualifier();
        }
    }

    @Configuration
    static class AppCtxUseQualifierConfig extends CommonConfig {
        @Bean
        @Qualifier("printer")
        public MemberPrinter memberPrinter() {
            return new MemberPrinter();
        }

        @Bean
        @Qualifier("summaryPrinter")
        public MemberPrinter summaryPrinter() {
            return new MemberSummaryPrinter();
        }

        @Bean
        public MemberListPrinter listPrinter() {
            return new MemberListPrinter();
        }

        @Bean
        public MemberInfoPrinter infoPrinter() {
            return new MemberInfoPrinter();
        }
    }
}
