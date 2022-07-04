package com.study.kts.chapter04.printer;

import com.study.main.chapter04.spring.MemberDao;
import com.study.main.chapter04.spring.MemberPrinter;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberListPrinterNotQualifier {
    private MemberDao memberDao;
    private MemberPrinter printer;

    public MemberListPrinterNotQualifier() {
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setMemberPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
}
