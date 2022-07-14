package com.study.kdy.chapter06.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {

	public Client() {
		System.out.println("Construct called!");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("Client.afterPropertiesSet() 실행");
	}

	@Override
	public void destroy() {
		System.out.println("Client.destroy() 실행");
	}

}
