package com.study.main.chapter17.main;

import java.util.List;

import com.study.main.chapter17.config.DsConfigWithProp;
import com.study.main.chapter17.config.InfoConfig;
import com.study.main.chapter17.config.MemberConfig;
import com.study.main.chapter17.config.PropertyConfig;
import com.study.main.chapter17.spring.MemberDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.main.chapter17.spring.Info;
import com.study.main.chapter17.spring.Member;

public class MainWithProp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(PropertyConfig.class, InfoConfig.class, MemberConfig.class, DsConfigWithProp.class);
		context.refresh();

		MemberDao dao = context.getBean(MemberDao.class);
		List<Member> members = dao.selectAll();
		members.forEach(m -> System.out.println(m.getEmail()));

		Info info = context.getBean(Info.class);
		info.printInfo();
		
		context.close();
	}
}
