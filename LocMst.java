package com.nnbp.application.entities;

import java.io.Serializable;
import java.sql.Timestamp;


public class LocMst implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String type;
	private int bookstand;
	private int shelf;
	private String regDate;
	private String regUid;
	private String modDate;
	private String modUid;
	
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBookstand() {
		return bookstand;
	}
	public void setBookstand(int bookstand) {
		this.bookstand = bookstand;
	}
	public int getShelf() {
		return shelf;
	}
	public void setShelf(int shelf) {
		this.shelf = shelf;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUid() {
		return regUid;
	}
	public void setRegUid(String regUid) {
		this.regUid = regUid;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModUid() {
		return modUid;
	}
	public void setModUid(String modUid) {
		this.modUid = modUid;
	}
	
	
	
}
