package com.nnbp.application.entities;

import java.io.Serializable;
import java.util.Date;


public class Receiving implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int deleteFlag;
	private String regDate;
	private String regUid;
	private String modDate;
	private String modUid;
	
	private String deliveryHdrKey;
	private String supCode;
	private String status;
	private String invNo;
	private Date invDate;
	private String vessel;
	private String wayBill;
	private String wayBill2;
	private String containerNo;
	private String shipmentNo;
	private String purchaseNo;
	private String purchaseNo2;
	private Date deliveryDate;
	
	
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

	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getWayBill() {
		return wayBill;
	}

	public void setWayBill(String wayBill) {
		this.wayBill = wayBill;
	}

	public String getWayBill2() {
		return wayBill2;
	}

	public void setWayBill2(String wayBill2) {
		this.wayBill2 = wayBill2;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseNo2() {
		return purchaseNo2;
	}

	public void setPurchaseNo2(String purchaseNo2) {
		this.purchaseNo2 = purchaseNo2;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
