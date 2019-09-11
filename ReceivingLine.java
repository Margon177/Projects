package com.nnbp.application.entities;

import java.io.Serializable;
import java.sql.Timestamp;


public class ReceivingLine implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String regDate;
	private String regUid;
	private String modDate;
	private String modUid;
	
	private String palleteKey;
	private String deliveryKey;
	private String item;
	private String allocatedPalleteNo;
	private int noOfBoxes;
	private int qtyInBox;
	private float grossWeight;
	
	public String getDeliveryKey() {
		return deliveryKey;
	}

	public void setDeliveryKey(String deliveryKey) {
		this.deliveryKey = deliveryKey;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getNoOfBoxes() {
		return noOfBoxes;
	}

	public void setNoOfBoxes(int noOfBoxes) {
		this.noOfBoxes = noOfBoxes;
	}

	public int getQtyInBox() {
		return qtyInBox;
	}

	public void setQtyInBox(int qtyInBox) {
		this.qtyInBox = qtyInBox;
	}

	public float getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(float grossWeight) {
		this.grossWeight = grossWeight;
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

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getPalleteKey() {
		return palleteKey;
	}

	public void setPalleteKey(String palleteKey) {
		this.palleteKey = palleteKey;
	}

	public String getAllocatedPalleteNo() {
		return allocatedPalleteNo;
	}

	public void setAllocatedPalleteNo(String allocatedPalleteNo) {
		this.allocatedPalleteNo = allocatedPalleteNo;
	}

}
