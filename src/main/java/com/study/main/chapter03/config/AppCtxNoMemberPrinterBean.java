package com.study.main.chapter03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.main.chapter03.spring.ChangePasswordService;
import com.study.main.chapter03.spring.MemberDao;
import com.study.main.chapter03.spring.MemberInfoPrinter;
import com.study.main.chapter03.spring.MemberListPrinter;
import com.study.main.chapter03.spring.MemberPrinter;
import com.study.main.chapter03.spring.MemberRegisterService;
import com.study.main.chapter03.spring.VersionPrinter;

@Configuration
public class AppCtxNoMemberPrinterBean {
	private MemberPrinter printer = new MemberPrinter();
	
	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao());
	}
	
	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao());
		return pwdSvc;
	}
	
	@Bean
	public MemberListPrinter listPrinter() {
		return new MemberListPrinter(memberDao(), printer);
	}
	
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		infoPrinter.setMemberDao(memberDao());
		infoPrinter.setPrinter(printer);
		return infoPrinter;
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
}
