package com.study.main.chapter05.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.study.main.chapter05.spring.MemberDao;
import com.study.main.chapter05.spring.MemberPrinter;
import com.study.main.chapter05.spring.MemberSummaryPrinter;
import com.study.main.chapter05.spring.VersionPrinter;

@Configuration
@ComponentScan(basePackages = {"com/study/main/chapter11/spring"})
public class AppCtxWithExplicit {

	@Bean
	public MemberDao memberDao2() {
		MemberDao memberDao = new MemberDao();
		System.out.println("explicit : " + memberDao);
		return memberDao;
	}
	
	@Bean
	@Qualifier("printer")
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}
	
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
}
