package com.study.main.chapter11.survey;

import java.util.Collections;
import java.util.List;

public class Question {

	private final String title;
	private final List<String> options;

	public Question(String title, List<String> options) {
		this.title = title;
		this.options = options;
	}

	public Question(String title) {
		this(title, Collections.emptyList());
	}

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return options;
	}

	public boolean isChoice() {
		return options != null && !options.isEmpty();
	}

}
