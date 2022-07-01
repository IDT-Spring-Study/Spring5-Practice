package com.study.kdy.chapter04.service;

import com.study.main.chapter04.spring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;

public class MemberRegisterService {

	@Autowired
	@Qualifier("memberDao1")
	private MemberDao memberDao;

	@Autowired
	private MemberPrinter memberPrinter;

	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}

	public void print(String email) {
		if (email == null || "".equals(email)) {
			throw new NullPointerException("email is null.");
		}

		memberPrinter.print(memberDao.selectByEmail(email));
	}

}
