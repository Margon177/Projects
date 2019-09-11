package com.nnbp.application.entities;

import java.io.Serializable;
import java.sql.Timestamp;


public class PackPalleteMst implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String modDate;
	private String modUid;
	private String regDate;
	private String regUid;
	private String palleteKey;
	private String type;
	private float height;
	private float maxWeight;
	private float width;
	private float length;
	private float tare;
	
	
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


	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getTare() {
		return tare;
	}

	public void setTare(float tare) {
		this.tare = tare;
	}

	public String getPalleteKey() {
		return palleteKey;
	}

	public void setPalleteKey(String palleteKey) {
		this.palleteKey = palleteKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((palleteKey == null) ? 0 : palleteKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PackPalleteMst other = (PackPalleteMst) obj;
		if (palleteKey == null) {
			if (other.palleteKey != null)
				return false;
		} else if (!palleteKey.equals(other.palleteKey))
			return false;
		return true;
	}

}
