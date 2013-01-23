package com.erlanggatjhie.emotextcon.model;

public class Emoticon {
	private String description;
	private String content;
	
	
	public Emoticon(String description, String content) {
		this.description = description;
		this.content = content;
	}

	public String getDescription() {
		return description;
	}
	
	public void setName(String name) {
		this.description = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
