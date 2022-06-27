package com.study.kdy.chapter03.service;


import com.study.kdy.chapter03.dto.RegisterRequestDto;
import com.study.kdy.chapter03.exception.DuplicateMemberException;
import com.study.kdy.chapter03.model.Member;
import com.study.kdy.chapter03.model.MemberDao;

import java.time.LocalDateTime;

public class MemberRegisterService {
	public final MemberDao memberDao;

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
