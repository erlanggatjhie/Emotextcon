package com.erlanggatjhie.emotextcon.model;

public class Emoticon {
	private Integer id;
	private String description;
	private String content;
	
	public Emoticon(String description, String content) {
		this.description = description;
		this.content = content;
	}
	
	public Emoticon(Integer id, String description, String content) {
		this.id = id;
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
	
	public Integer getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Emoticon) {
			Emoticon emoticon = (Emoticon) o;
			return this.id == emoticon.id && 
					this.description.equals(emoticon.description) && 
					this.content.equals(emoticon.content);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("Emoticon with id %s, description %s and content %s", this.id, this.description, this.content);
	}
}
