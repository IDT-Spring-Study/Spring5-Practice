package com.study.kdy.chapter08.service;

import com.study.kdy.chapter05.exception.MemberNotFoundException;
import com.study.kdy.chapter08.model.Member;
import com.study.kdy.chapter08.model.MemberDao;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

public class ChangePasswordService {

	private MemberDao memberDao;

	@Transactional(rollbackFor= SQLException.class)
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberDao.update(member);
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
