package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.Receiving;
import com.nnbp.application.entities.Receiving;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;
import com.nnbp.application.util.StringUtil;


public class ReceivingDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	boolean state = true;
	
	public ReceivingDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<Receiving> findAllItems() {
		List<Receiving> items = new ArrayList<Receiving>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_HDR_KEY, SUP_CODE, INVOICE_NUMBER, INVOICE_DATE, VESSEL_NAME, WAYBILL, WAYBILL2, CONTAINER_NO,"
				+" VOYAGE_NO, PURCHASE_NO, PURCHASE_NO2, DELIVERY_DATE, STATUS  FROM [DELIVERY_HDR_TBL] WHERE STATUS IN('N','E','Q') ORDER BY REG_DATE ASC";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Receiving i = new Receiving();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setSupCode(rs.getString("SUP_CODE"));
				i.setInvNo(rs.getString("INVOICE_NUMBER"));
				i.setInvDate(rs.getDate("INVOICE_DATE"));
				i.setVessel(rs.getString("VESSEL_NAME"));
				i.setWayBill(rs.getString("WAYBILL"));
				i.setWayBill2(rs.getString("WAYBILL2"));
				i.setContainerNo(rs.getString("CONTAINER_NO"));
				i.setShipmentNo(rs.getString("VOYAGE_NO"));
				i.setPurchaseNo(rs.getString("PURCHASE_NO"));
				i.setPurchaseNo2(rs.getString("PURCHASE_NO2"));
				i.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				i.setStatus(rs.getString("STATUS"));
				
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<Receiving> findExistingItems() {
		List<Receiving> items = new ArrayList<Receiving>();
		try {
			
			items.clear();
			
			String query;

			query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_HDR_KEY, SUP_CODE, INVOICE_NUMBER, INVOICE_DATE, VESSEL_NAME, WAYBILL, WAYBILL2, CONTAINER_NO,"+ 
			"VOYAGE_NO, PURCHASE_NO, PURCHASE_NO2, DELIVERY_DATE,STATUS  FROM [DELIVERY_HDR_TBL] WHERE DELETE_FLAG = 0 AND STATUS IN('N','E','Q') ORDER BY REG_DATE ASC";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Receiving i = new Receiving();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setSupCode(rs.getString("SUP_CODE"));
				i.setInvNo(rs.getString("INVOICE_NUMBER"));
				i.setInvDate(rs.getDate("INVOICE_DATE"));
				i.setVessel(rs.getString("VESSEL_NAME"));
				i.setWayBill(rs.getString("WAYBILL"));
				i.setWayBill2(rs.getString("WAYBILL2"));
				i.setContainerNo(rs.getString("CONTAINER_NO"));
				i.setShipmentNo(rs.getString("VOYAGE_NO"));
				i.setPurchaseNo(rs.getString("PURCHASE_NO"));
				i.setPurchaseNo2(rs.getString("PURCHASE_NO2"));
				i.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				i.setStatus(rs.getString("STATUS"));
				
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<Receiving> findItemsReadyToApprove() {
		List<Receiving> items = new ArrayList<Receiving>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_HDR_KEY, SUP_CODE, INVOICE_NUMBER, INVOICE_DATE, VESSEL_NAME, WAYBILL, WAYBILL2, CONTAINER_NO,"
				+" VOYAGE_NO, PURCHASE_NO, PURCHASE_NO2, DELIVERY_DATE, STATUS  FROM [DELIVERY_HDR_TBL] WHERE STATUS = 'N' AND DELETE_FLAG = 0 ORDER BY MOD_DATE ASC";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Receiving i = new Receiving();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setSupCode(rs.getString("SUP_CODE"));
				i.setInvNo(rs.getString("INVOICE_NUMBER"));
				i.setInvDate(rs.getDate("INVOICE_DATE"));
				i.setVessel(rs.getString("VESSEL_NAME"));
				i.setWayBill(rs.getString("WAYBILL"));
				i.setWayBill2(rs.getString("WAYBILL2"));
				i.setContainerNo(rs.getString("CONTAINER_NO"));
				i.setShipmentNo(rs.getString("VOYAGE_NO"));
				i.setPurchaseNo(rs.getString("PURCHASE_NO"));
				i.setPurchaseNo2(rs.getString("PURCHASE_NO2"));
				i.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				i.setStatus(rs.getString("STATUS"));
				
				items.add(i);
				
			}
		
			rs.close();
			pstmt.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public List<Receiving> findApprovedItems() {
		List<Receiving> items = new ArrayList<Receiving>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_HDR_KEY, SUP_CODE, INVOICE_NUMBER, INVOICE_DATE, VESSEL_NAME, WAYBILL, WAYBILL2, CONTAINER_NO,"
				+" VOYAGE_NO, PURCHASE_NO, PURCHASE_NO2, DELIVERY_DATE, STATUS  FROM [DELIVERY_HDR_TBL] WHERE STATUS = 'A' AND DELETE_FLAG = 0 ORDER BY MOD_DATE ASC";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Receiving i = new Receiving();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setSupCode(rs.getString("SUP_CODE"));
				i.setInvNo(rs.getString("INVOICE_NUMBER"));
				i.setInvDate(rs.getDate("INVOICE_DATE"));
				i.setVessel(rs.getString("VESSEL_NAME"));
				i.setWayBill(rs.getString("WAYBILL"));
				i.setWayBill2(rs.getString("WAYBILL2"));
				i.setContainerNo(rs.getString("CONTAINER_NO"));
				i.setShipmentNo(rs.getString("VOYAGE_NO"));
				i.setPurchaseNo(rs.getString("PURCHASE_NO"));
				i.setPurchaseNo2(rs.getString("PURCHASE_NO2"));
				i.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				i.setStatus(rs.getString("STATUS"));
				
				items.add(i);
			}

			rs.close();
			pstmt.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public Receiving findOneById(String findParam){
		Receiving i = new Receiving();
		try{
			String query;
			
			query = "SELECT * FROM [DELIVERY_HDR_TBL] WHERE DELIVERY_HDR_KEY ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setSupCode(rs.getString("SUP_CODE"));
				i.setInvNo(rs.getString("INVOICE_NUMBER"));
				i.setInvDate(rs.getDate("INVOICE_DATE"));
				i.setVessel(rs.getString("VESSEL_NAME"));
				i.setWayBill(rs.getString("WAYBILL"));
				i.setWayBill2(rs.getString("WAYBILL2"));
				i.setContainerNo(rs.getString("CONTAINER_NO"));
				i.setShipmentNo(rs.getString("VOYAGE_NO"));
				i.setPurchaseNo(rs.getString("PURCHASE_NO"));
				i.setPurchaseNo2(rs.getString("PURCHASE_NO2"));
				i.setInvDate(rs.getDate("DELIVERY_DATE"));
				i.setStatus(rs.getString("STATUS"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
public String setSeq(){
		
		String query;
		long seq = 0 ;

		String seqString = "";
		
		try{
		query = "SELECT count(1) FROM [DELIVERY_HDR_TBL]";

	PreparedStatement pstmt = connection.prepareStatement(query);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()){
	seq = rs.getLong(1)+1;
	}
	
	rs.close();
	pstmt.close();
	
	seqString = StringUtil.fillZeroLeft("DH" , ""+seq , 10);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seqString;
	}
	
	public void updateRecord(Receiving updateParams) throws SQLException
	{
		try {
			String update = new String();
			update ="UPDATE [DELIVERY_HDR_TBL] SET "
			+"MOD_DATE = ? , "					//1
			+"MOD_UID = ? , "					//2
			+"VESSEL_NAME = ? , "				//3
			+"SUP_CODE = ? , "					//4
			+"CONTAINER_NO = ? , "				//5
			+"INVOICE_NUMBER = ? , "			//6
			+"INVOICE_DATE = ? , "				//7
			+"PURCHASE_NO = ? , "				//8
			+"PURCHASE_NO2 = ? , "				//9
			+"WAYBILL = ?  ,"					//10
			+"WAYBILL2 = ? , "					//11
			+"VOYAGE_NO = ? , "					//12
			+"DELIVERY_DATE = ? , "				//13
			+"STATUS = ? "						//14
			+"WHERE DELIVERY_HDR_KEY = ? ";		//15
		
		PreparedStatement pstmt = connection.prepareStatement(update);
		pstmt.setTimestamp(1, date.getCurrentDateObj());
		pstmt.setString(2, updateParams.getModUid());
		pstmt.setString(3, updateParams.getVessel());
		pstmt.setString(4, updateParams.getSupCode());
		pstmt.setString(5, updateParams.getContainerNo());
		pstmt.setString(6, updateParams.getInvNo());
		pstmt.setTimestamp(7, date.convertToTimestamp(updateParams.getInvDate()));
		pstmt.setString(8, updateParams.getPurchaseNo());
		pstmt.setString(9, updateParams.getPurchaseNo2());
		pstmt.setString(10, updateParams.getWayBill());
		pstmt.setString(11, updateParams.getWayBill2());
		pstmt.setString(12, updateParams.getShipmentNo());
		pstmt.setTimestamp(13, date.convertToTimestamp(updateParams.getDeliveryDate()));
		pstmt.setString(14, updateParams.getStatus());
		pstmt.setString(15, updateParams.getDeliveryHdrKey());
		
		pstmt.executeUpdate();
		
		message.info("Sukces!", "Edycja zakoñczona");
		
	} catch (Exception e) {
		e.printStackTrace();
		message.error("B³¹d!", "Edycja nie mo¿liwa!");
		}
	}
		
		public void approveRecord(Receiving updateParams) throws SQLException
		{
			try {
				String update = new String();
				update ="UPDATE [DELIVERY_HDR_TBL] SET "
				+"MOD_DATE = ? , "					//1
				+"MOD_UID = ? , "					//2
				+"STATUS = ? "						//3
				+"WHERE DELIVERY_HDR_KEY = ? ";		//4
			
			PreparedStatement pstmt = connection.prepareStatement(update);
			pstmt.setTimestamp(1, date.getCurrentDateObj());
			pstmt.setString(2, updateParams.getModUid());
			pstmt.setString(3, updateParams.getStatus());
			pstmt.setString(4, updateParams.getDeliveryHdrKey());
			
			pstmt.executeUpdate();
			
			message.info("Sukces!", "Zmieniono status dostawy "+updateParams.getDeliveryHdrKey());
			
		} catch (Exception e) {
			e.printStackTrace();
			message.error("B³¹d!", "Zmiana statusu nie uda³a siê!");
		}
		
	}
		
		public void denyRecord(Receiving updateParams) throws SQLException
		{
			try {
				String update = new String();
				update ="UPDATE [DELIVERY_HDR_TBL] SET "
				+"MOD_DATE = ? , "					//1
				+"MOD_UID = ? , "					//2
				+"STATUS = ? "						//3
				+"WHERE DELIVERY_HDR_KEY = ? ";		//4
			
			PreparedStatement pstmt = connection.prepareStatement(update);
			pstmt.setTimestamp(1, date.getCurrentDateObj());
			pstmt.setString(2, updateParams.getModUid());
			pstmt.setString(3, updateParams.getStatus());
			pstmt.setString(4, updateParams.getDeliveryHdrKey());
			
			pstmt.executeUpdate();
			
			message.info("Sukces!", "Zmieniono status dostawy "+updateParams.getDeliveryHdrKey());
			
		} catch (Exception e) {
			e.printStackTrace();
			message.error("B³¹d!", "Zmiana statusu nie uda³a siê!");
		}
		
	}

	public void insertRecord(Receiving insertParams)
	{
		try{
		
		String insert = "INSERT INTO [DELIVERY_HDR_TBL] (DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, DELIVERY_HDR_KEY, SUP_CODE, INVOICE_NUMBER, INVOICE_DATE, VESSEL_NAME, WAYBILL, WAYBILL2, CONTAINER_NO, VOYAGE_NO, PURCHASE_NO, PURCHASE_NO2, DELIVERY_DATE, STATUS )"
		+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		
		String sqlValidateQuerry ="SELECT DELIVERY_HDR_KEY FROM [DELIVERY_HDR_TBL] WHERE DELIVERY_HDR_KEY ='"+insertParams.getDeliveryHdrKey()+"';";

		PreparedStatement vpstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = vpstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getDeliveryHdrKey().equals(rs.getString("DELIVERY_HDR_KEY"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê½, Rekord ju¿½ istnieje");
				return;
			}
		}
		
				PreparedStatement pstmt = connection.prepareStatement(insert);

				pstmt.setInt(1,insertParams.getDeleteFlag());
				pstmt.setTimestamp(2,date.getCurrentDateObj());
				pstmt.setString(3,insertParams.getRegUid());
				pstmt.setTimestamp(4,date.getCurrentDateObj());
				pstmt.setString(5,insertParams.getModUid());
				pstmt.setString(6,insertParams.getDeliveryHdrKey());
				pstmt.setString(7,insertParams.getSupCode());
				pstmt.setString(8,insertParams.getInvNo());
				pstmt.setTimestamp(9,date.convertToTimestamp(insertParams.getInvDate()));
				pstmt.setString(10,insertParams.getVessel());
				pstmt.setString(11,insertParams.getWayBill());
				pstmt.setString(12,insertParams.getWayBill2());
				pstmt.setString(13,insertParams.getContainerNo());
				pstmt.setString(14,insertParams.getShipmentNo());
				pstmt.setString(15,insertParams.getPurchaseNo());
				pstmt.setString(16,insertParams.getPurchaseNo2());
				pstmt.setTimestamp(17,date.convertToTimestamp(insertParams.getDeliveryDate()));
				pstmt.setString(18,insertParams.getStatus());

				pstmt.executeUpdate();
		
		message.info("Sukces!", "Dodawanie zakoñcone rekord nag³ówka: "+insertParams.getDeliveryHdrKey());

		} catch (Exception e) {
			state = false;
			e.printStackTrace();
			message.error("B³¹d!", "Dodawanie nie mo¿liwe, Naruszono wiêzy integralnoœci");
		}
	}
	
	public void deleteRecord(Receiving deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [DELIVERY_HDR_TBL] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("DELIVERY_HDR_KEY =");
			delete.append("'");
			delete.append(deleteParams.getDeliveryHdrKey());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, Usuniêto: ", deleteParams.getDeliveryHdrKey().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(Receiving restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [DELIVERY_HDR_TBL] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("DELIVERY_HDR_KEY =");
			restore.append("'");
			restore.append(restoreParams.getDeliveryHdrKey().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, Przywrócono: ", restoreParams.getDeliveryHdrKey().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

