package com.study.kdy.chapter03.service;

import com.study.kdy.chapter03.model.Member;
import com.study.kdy.chapter03.model.MemberDao;
import com.study.main.chapter03.exception.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ChangePasswordService {

    @Autowired
    private MemberDao memberDao;

    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email);
        Optional.ofNullable(member)
                .ifPresentOrElse(member1 -> {
                    member1.changePassword(oldPwd, newPwd);
                    memberDao.update(member1);
                }, () -> {
                    throw new MemberNotFoundException();
                });
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

}
