package com.nnbp.application.entities;

import java.io.Serializable;
import java.sql.Timestamp;


public class PackUnitMst implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String modDate;
	private String modUid;
	private String regDate;
	private String regUid;
	private String packingKey;
	private String boxType;
	private int defaultQtyInBox;
	private float height;
	private float length;
	private float width;
	private float tare;
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

	public String getPackingKey() {
		return packingKey;
	}

	public void setPackingKey(String packingKey) {
		this.packingKey = packingKey;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public int getDefaultQtyInBox() {
		return defaultQtyInBox;
	}

	public void setDefaultQtyInBox(int defaultQtyInBox) {
		this.defaultQtyInBox = defaultQtyInBox;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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
		result = prime * result + ((packingKey == null) ? 0 : packingKey.hashCode());
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
		PackUnitMst other = (PackUnitMst) obj;
		if (packingKey == null) {
			if (other.packingKey != null)
				return false;
		} else if (!packingKey.equals(other.packingKey))
			return false;
		return true;
	}

}
