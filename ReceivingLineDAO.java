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
import com.nnbp.application.entities.ReceivingLine;
import com.nnbp.application.entities.ReceivingPallete;
import com.nnbp.application.entities.ReceivingLine;
import com.nnbp.application.entities.ReceivingLine;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;
import com.nnbp.application.util.StringUtil;


public class ReceivingLineDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	public boolean state = true;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public ReceivingLineDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<ReceivingLine> findAllItems(ReceivingPallete selectedPallete) {
		List<ReceivingLine> items = new ArrayList<ReceivingLine>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_KEY, DELIVERY_PALLETE_KEY, ALLOCATED_PALLETE_NO, ITEM_NAME, NO_OF_BOXES, QTY_IN_BOX, GROSS_WEIGHT FROM [DELIVERY_TBL] WHERE DELIVERY_PALLETE_KEY = '"+selectedPallete.getPalleteKey()+"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ReceivingLine i = new ReceivingLine();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("DELIVERY_PALLETE_KEY"));
				i.setDeliveryKey(rs.getString("DELIVERY_KEY"));
				i.setAllocatedPalleteNo(rs.getString("ALLOCATED_PALLETE_NO"));
				i.setItem(rs.getString("ITEM_NAME"));
				i.setQtyInBox(rs.getInt("QTY_IN_BOX"));
				i.setGrossWeight(rs.getFloat("GROSS_WEIGHT"));
				
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<ReceivingLine> findExistingItems(ReceivingPallete selectedPallete) {
		List<ReceivingLine> items = new ArrayList<ReceivingLine>();
		try {
			
			items.clear();
			
			String query;

			query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_KEY, DELIVERY_PALLETE_KEY, ALLOCATED_PALLETE_NO, ITEM_NAME, QTY_IN_BOX, GROSS_WEIGHT FROM [DELIVERY_TBL] WHERE DELETE_FLAG = 0 AND DELIVERY_PALLETE_KEY = '"+selectedPallete.getPalleteKey()+"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ReceivingLine i = new ReceivingLine();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("DELIVERY_PALLETE_KEY"));
				i.setAllocatedPalleteNo(rs.getString("ALLOCATED_PALLETE_NO"));
				i.setDeliveryKey(rs.getString("DELIVERY_KEY"));
				i.setItem(rs.getString("ITEM_NAME"));
				i.setQtyInBox(rs.getInt("QTY_IN_BOX"));
				i.setGrossWeight(rs.getFloat("GROSS_WEIGHT"));
				
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public ReceivingLine findOneById(String findParam){
		ReceivingLine i = new ReceivingLine();
		try{
			String query;
			
			query = "SELECT * FROM [DELIVERY_TBL] WHERE DELIVERY_KEY ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("DELIVERY_PALLETE_KEY"));
				i.setDeliveryKey(rs.getString("DELIVERY_KEY"));
				i.setItem(rs.getString("ITEM_NAME"));
				i.setQtyInBox(rs.getInt("QTY_IN_BOX"));
				i.setGrossWeight(rs.getFloat("GROSS_WEIGHT"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
public String setSeq(int size){
		
		String query;
		long seq = 0 ;

		String seqString = "";
		
		try{
		query = "SELECT count(1) from DELIVERY_TBL";

	PreparedStatement pstmt = connection.prepareStatement(query);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()){
	seq = rs.getLong(1)+1+size;
	}
	
	rs.close();
	pstmt.close();
	
	seqString = StringUtil.fillZeroLeft("DL" , ""+seq , 10);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seqString;
	}
	
	public void updateRecord(ReceivingLine updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [DELIVERY_TBL] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" ITEM_NAME = '");
		update.append(updateParams.getItem());
		update.append("',");
		update.append(" QTY_IN_BOX = '");
		update.append(updateParams.getQtyInBox());
		update.append("',");
		update.append(" GROSS_WEIGHT = '");
		update.append(updateParams.getGrossWeight());
		update.append("'");
		update.append(" WHERE ");
		update.append("DELIVERY_KEY = '");
		update.append(updateParams.getDeliveryKey());
		update.append("';");
		
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Success!", "Edycja zakoñczona");
		
	} catch (Exception e) {
		e.printStackTrace();
		message.error("B³¹d!", "Edycja nie mo¿liwa!");
	}
		
	}

	public void insertRecord(ReceivingLine insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [DELIVERY_TBL](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, DELIVERY_KEY, DELIVERY_PALLETE_KEY, ALLOCATED_PALLETE_NO, ITEM_NAME, QTY_IN_BOX, GROSS_WEIGHT ) VALUES (");
		insert.append("'");
		insert.append(insertParams.getDeleteFlag());
		insert.append("', '");
		insert.append(date.getCurrentDateObj());
		insert.append("', '");
		insert.append(insertParams.getRegUid());
		insert.append("', '");
		insert.append(date.getCurrentDateObj());
		insert.append("', '");
		insert.append(insertParams.getModUid());
		insert.append("', '");
		insert.append(insertParams.getDeliveryKey());
		insert.append("', '");
		insert.append(insertParams.getPalleteKey());
		insert.append("', '");
		insert.append(insertParams.getAllocatedPalleteNo());
		insert.append("', '");
		insert.append(insertParams.getItem());
		insert.append("', '");
		insert.append(insertParams.getQtyInBox());
		insert.append("', '");
		insert.append(insertParams.getGrossWeight());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT DELIVERY_KEY FROM [DELIVERY_TBL] WHERE DELIVERY_KEY ='"+insertParams.getDeliveryKey()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getDeliveryKey().equals(rs.getString("DELIVERY_KEY"))){
				message.warn("Uwaga!", "Dodawanie nie powiodï³o siê½, Rekord ju¿ istnieje");
				return;
			}
		}
		
		call=connection.prepareCall(insert.toString());
		call.executeUpdate();
		message.info("Sukces!", "Dodawanie zakoñczone rekord linii: "+insertParams.getDeliveryKey());

		} catch (Exception e) {
			state = false;
			e.printStackTrace();
			message.error("B³¹d!", "Dodawanie nie mo¿liwe, Naruszono wiêzy integralnoœci");
		}
	}
	
	public void deleteRecord(ReceivingLine deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [DELIVERY_TBL] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("DELIVERY_KEY =");
			delete.append("'");
			delete.append(deleteParams.getDeliveryKey());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, Usuniêto: ", deleteParams.getDeliveryKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(ReceivingLine restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [DELIVERY_TBL] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("DELIVERY_KEY =");
			restore.append("'");
			restore.append(restoreParams.getDeliveryKey());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, Przywrócono: ", restoreParams.getDeliveryKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

