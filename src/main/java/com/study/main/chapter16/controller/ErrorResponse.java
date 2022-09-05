package com.study.main.chapter16.controller;

public class ErrorResponse {
	private final String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
