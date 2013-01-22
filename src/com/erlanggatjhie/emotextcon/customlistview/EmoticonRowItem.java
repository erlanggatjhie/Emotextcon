package com.erlanggatjhie.emotextcon.customlistview;

public class EmoticonRowItem {
	private String description;
	private String content;
	
	public EmoticonRowItem(String description, String content) {
		this.description = description;
		this.content = content;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
