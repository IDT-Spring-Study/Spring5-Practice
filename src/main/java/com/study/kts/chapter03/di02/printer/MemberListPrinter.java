package com.study.kts.chapter03.di02.printer;

import com.study.kts.chapter03.di02.dao.MemberDao;
import com.study.kts.chapter03.di02.model.Member;

import java.util.Collection;

public class MemberListPrinter {
    private final MemberDao memberDao;
    private final MemberPrinter printer;

    public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
        this.memberDao = memberDao;
        this.printer = printer;
    }

    public void printAll() {
        Collection<Member> members = memberDao.selectAll();
        members.forEach(m -> printer.print(m));
    }
}
