package com.study.kts.chapter03.di02.service;

import com.study.kts.chapter03.di02.dao.MemberDao;
import com.study.kts.chapter03.di02.dto.RegisterRequestDto;
import com.study.kts.chapter03.di02.model.Member;
import com.study.main.chapter03.spring.DuplicateMemberException;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private final MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long regist(RegisterRequestDto reqDto) {
        Member member = memberDao.selectByEmail(reqDto.getEmail());
        if (member != null) {
            throw new DuplicateMemberException("dup email " + reqDto.getEmail());
        }
        Member newMember = new Member(
                reqDto.getEmail(), reqDto.getPassword(), reqDto.getName(),
                LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
