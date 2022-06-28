package com.study.kts.chapter03.di02.printer;

import com.study.kts.chapter03.di02.dao.MemberDao;
import com.study.kts.chapter03.di02.model.Member;

public class MemberInfoPrinter {
    private MemberDao memberDao;
    private MemberPrinter printer;

    public void printMemberInfo(String email) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) {
            System.out.println("데이터 없음\n");
            return;
        }
        printer.print(member);
        System.out.println();
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
}
