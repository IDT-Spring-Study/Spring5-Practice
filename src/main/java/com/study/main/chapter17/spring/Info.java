package com.study.main.chapter17.spring;

import org.springframework.beans.factory.annotation.Value;

public class Info {

	private String version;

	public void printInfo() {
		System.out.println("version = " + version);
	}

	@Value("${info.version}")
	public void setVersion(String version) {
		this.version = version;
	}

}
