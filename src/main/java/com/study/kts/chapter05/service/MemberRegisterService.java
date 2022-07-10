package com.study.kts.chapter05.service;


import com.study.kts.chapter05.dao.MemberDao;

public class MemberRegisterService {
    private final MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
