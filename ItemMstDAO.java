package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class ItemMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public ItemMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<ItemMst> findAllItems() {
		List<ItemMst> items = new ArrayList<ItemMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , ITEM_NAME, ITEM_TYPE, DESCRIPTION FROM [ITEM_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ItemMst i = new ItemMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setName(rs.getString("ITEM_NAME"));
				i.setType(rs.getString("ITEM_TYPE"));
				i.setDescription(rs.getString("DESCRIPTION"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<ItemMst> findExistingItems() {
		List<ItemMst> items = new ArrayList<ItemMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , ITEM_NAME, ITEM_TYPE, DESCRIPTION FROM [ITEM_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ItemMst i = new ItemMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setName(rs.getString("ITEM_NAME"));
				i.setType(rs.getString("ITEM_TYPE"));
				i.setDescription(rs.getString("DESCRIPTION"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public ItemMst findOneById(String findParam){
		ItemMst i = new ItemMst();
		try{
			String query;
			
			query = "SELECT * FROM [ITEM_MST] WHERE ITEM_NAME ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setName(rs.getString("ITEM_NAME"));
				i.setType(rs.getString("ITEM_TYPE"));
				i.setDescription(rs.getString("DESCRIPTION"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	
	public void updateRecord(ItemMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [ITEM_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" ITEM_TYPE = '");
		update.append(updateParams.getType());
		update.append("',");
		update.append(" DESCRIPTION = '");
		update.append(updateParams.getDescription().toString());
		update.append("'");
		update.append(" WHERE ");
		update.append("ITEM_NAME = '");
		update.append(updateParams.getName().toString());
		update.append("';");
		
		if(updateParams.getType().length()>5 ){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Typ d³u¿szy ni¿ 5 znaków");
			return;
		}
		else if(updateParams.getDescription().length()>100){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Opis d³u¿szy ni¿ 100 znaków");
		}else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Sukces!", "Edycja zakoñczona");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

	public void insertRecord(ItemMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [ITEM_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, ITEM_NAME, ITEM_TYPE, DESCRIPTION) VALUES (");
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
		insert.append(insertParams.getName());
		insert.append("', '");
		insert.append(insertParams.getType());;
		insert.append("', '");
		insert.append(insertParams.getDescription());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT ITEM_NAME FROM [ITEM_MST] WHERE ITEM_NAME ='"+insertParams.getName()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getName().equals(rs.getString("ITEM_NAME"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getName().equals(null) || insertParams.getName().length()>27){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Nazwa nie mo¿e byæ pusta lub d³u¿sza ni¿ 27 znaków");
		}
		else if(insertParams.getType().length()<=0 || insertParams.getType().length() > 5){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 5 znaków");
		}
		else
		{
		call=connection.prepareCall(insert.toString());
		call.executeUpdate();
		message.info("Sukces!", "Dodawanie zakoñczone");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRecord(ItemMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [ITEM_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("ITEM_NAME =");
			delete.append("'");
			delete.append(deleteParams.getName().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", deleteParams.getName().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(ItemMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [ITEM_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("ITEM_NAME =");
			restore.append("'");
			restore.append(restoreParams.getName().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", restoreParams.getName().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

