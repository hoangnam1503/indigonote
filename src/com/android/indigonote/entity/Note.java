package com.android.indigonote.entity;

public class Note {
	private int id;
	private String title;
	private String content;
	private boolean delFlg;
	private String dateCreated;
	private String dateUpdated;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isDelFlg() {
		return delFlg;
	}
	
	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getDateUpdated() {
		return dateUpdated;
	}
	
	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}
