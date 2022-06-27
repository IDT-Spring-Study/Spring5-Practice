package com.study.kdy.chapter03.model;


import com.study.kdy.chapter03.exception.WrongIdPasswordException;

import java.time.LocalDateTime;

public class Member {

	private Long id;
	private String email;
	private String password;
	private String name;
	private LocalDateTime registerDateTime;

	public Member(String email, String password, 
			String name, LocalDateTime regDateTime) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.registerDateTime = regDateTime;
	}

	public void changePassword(String oldPassword, String newPassword) {
		if (!password.equals(oldPassword))
			throw new WrongIdPasswordException();
		this.password = newPassword;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getRegisterDateTime() {
		return registerDateTime;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
