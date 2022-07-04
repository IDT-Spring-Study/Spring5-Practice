package com.study.kts.chapter03.di02.printer;

import com.study.kts.chapter03.di02.config.AppCtx;
import com.study.kts.chapter03.di02.dao.MemberDao;
import com.study.kts.chapter03.di02.model.Member;
import com.study.kts.chapter03.di02.printer.MemberInfoPrinter;
import com.study.kts.chapter03.di02.printer.MemberListPrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

class MemberListPrinterTest {

    @Test
    @DisplayName("P83_리스트 3.19 생성자 주입 테스트")
    void mainFortSpringTest1() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);
        MemberDao memberDao = ac.getBean("memberDao", MemberDao.class);

        //member 등록
        memberDao.insert(new Member("kts1@naver.com", "123", "태수1", LocalDateTime.now()));
        memberDao.insert(new Member("kts2@naver.com", "456", "태수2", LocalDateTime.now()));

        //생성자 주입이 완료된 listPrinter 빈 객체 가져오기
        MemberListPrinter listPrinter = ac.getBean("listPrinter", MemberListPrinter.class);

        //생성자 주입이 제대로 되었는지, 내부 출력해보기
        listPrinter.printAll();
    }

    @Test
    @DisplayName("P86_리스트 3.22 세터 주입 테스트")
    void mainForSpringTest2() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppCtx.class);
        MemberDao memberDao = ac.getBean("memberDao", MemberDao.class);

        //member 등록
        memberDao.insert(new Member("kts3@naver.com", "789", "태수3", LocalDateTime.now()));

        //세터 주입이 완료된 listPrinter 빈 객체 가져오기
        MemberInfoPrinter infoPrinter = ac.getBean("infoPrinter", MemberInfoPrinter.class);

        //세터 주입이 제대로 되었는지, 내부 출력해보기
        infoPrinter.printMemberInfo("kts3@naver.com");
    }
}