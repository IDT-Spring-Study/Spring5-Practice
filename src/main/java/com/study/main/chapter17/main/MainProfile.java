package com.study.main.chapter17.main;

import java.util.List;

import com.study.main.chapter17.config.DsDevConfig;
import com.study.main.chapter17.config.DsRealConfig;
import com.study.main.chapter17.config.MemberConfig;
import com.study.main.chapter17.spring.MemberDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.main.chapter17.spring.Member;

public class MainProfile {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("dev");
		context.register(MemberConfig.class, DsDevConfig.class, DsRealConfig.class);
		//context.register(MemberConfigWithProfile.class);
		context.refresh();
		
		MemberDao dao = context.getBean(MemberDao.class);
		List<Member> members = dao.selectAll();
		members.forEach(m -> System.out.println(m.getEmail()));
		
		context.close();
	}
}
