package com.nnbp.application.entities;

import java.io.Serializable;


public class AssemblyBOMMst implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String regDate;
	private String regUid;
	private String modDate;
	private String modUid;
	
	private String assBOMMstKey;
	private String item;
	
	
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

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getAssemblyBOMMstKey() {
		return assBOMMstKey;
	}

	public void setAssemblyBOMMstKey(String assBOMMstKey) {
		this.assBOMMstKey = assBOMMstKey;
	}
	
}
