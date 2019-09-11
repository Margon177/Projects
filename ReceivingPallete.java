package com.nnbp.application.entities;

import java.io.Serializable;
import java.sql.Timestamp;


public class ReceivingPallete implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String regDate;
	private String regUid;
	private String modDate;
	private String modUid;
	
	private String deliveryHdrKey;
	private String palleteKey;
	private String palleteNo;
	private String palleteNo2;
	private String type;
	private float height;
	private float length;
	private float widith;
	private float maxWeight;
	private float currentWeight;


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

	public String getDeliveryHdrKey() {
		return deliveryHdrKey;
	}

	public void setDeliveryHdrKey(String deliveryHdrKey) {
		this.deliveryHdrKey = deliveryHdrKey;
	}

	public String getPalleteKey() {
		return palleteKey;
	}

	public void setPalleteKey(String palleteKey) {
		this.palleteKey = palleteKey;
	}

	public String getPalleteNo() {
		return palleteNo;
	}

	public void setPalleteNo(String palleteNo) {
		this.palleteNo = palleteNo;
	}

	public String getPalleteNo2() {
		return palleteNo2;
	}

	public void setPalleteNo2(String palleteNo2) {
		this.palleteNo2 = palleteNo2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public float getWidith() {
		return widith;
	}

	public void setWidith(float widith) {
		this.widith = widith;
	}

	public float getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}

	public float getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(float currentWeight) {
		this.currentWeight = currentWeight;
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
		ReceivingPallete other = (ReceivingPallete) obj;
		if (palleteKey == null) {
			if (other.palleteKey != null)
				return false;
		} else if (!palleteKey.equals(other.palleteKey))
			return false;
		return true;
	}


}
