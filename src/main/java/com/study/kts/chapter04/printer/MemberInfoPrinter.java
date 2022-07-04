package com.study.kts.chapter04.printer;

import com.study.main.chapter04.spring.Member;
import com.study.main.chapter04.spring.MemberDao;
import com.study.main.chapter04.spring.MemberPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {

    private MemberDao memDao;
    private MemberPrinter printer;

    public void printMemberInfo(String email) {
        Member member = memDao.selectByEmail(email);
        if (member == null) {
            System.out.println("데이터 없음\n");
            return;
        }
        printer.print(member);
        System.out.println();
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memDao = memberDao;
    }

    @Autowired
    @Qualifier("printer")
    public void setPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
}
