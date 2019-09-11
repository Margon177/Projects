package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.WhMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class WhMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public WhMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<WhMst> findAllItems() {
		List<WhMst> items = new ArrayList<WhMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , WH_CODE, WH_TYPE FROM [WH_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				WhMst i = new WhMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setWhCode(rs.getString("WH_CODE"));
				i.setWhType(rs.getString("WH_TYPE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<WhMst> findExistingItems() {
		List<WhMst> items = new ArrayList<WhMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , WH_CODE, WH_TYPE FROM [WH_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				WhMst i = new WhMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setWhCode(rs.getString("WH_CODE"));
				i.setWhType(rs.getString("WH_TYPE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public void updateRecord(WhMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [WH_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" WH_TYPE = '");
		update.append(updateParams.getWhType());
		update.append("' ");
		update.append(" WHERE ");
		update.append("WH_CODE = '");
		update.append(updateParams.getWhCode().toString());
		update.append("';");
		
		if(updateParams.getWhType().length()>3 ){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Typ d³u¿szy ni¿ 3 znaków");
			return;
		}else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Sukces!", "Edycja zakoñczona");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

	public void insertRecord(WhMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [WH_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, WH_CODE, WH_TYPE) VALUES (");
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
		insert.append(insertParams.getWhCode());
		insert.append("', '");
		insert.append(insertParams.getWhType());;
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT WH_CODE FROM [WH_MST] WHERE WH_CODE ='"+insertParams.getWhCode()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getWhCode().equals(rs.getString("WH_CODE"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getWhCode().equals(null) || insertParams.getWhCode().length()>3){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Kod nie mo¿e byæ pusty lub d³u¿szy ni¿ 3 znaki");
		}
		else if(insertParams.getWhType().length()<=0 || insertParams.getWhType().length() > 3){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 3 znaki");
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
	
	public void deleteRecord(WhMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [WH_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("WH_CODE =");
			delete.append("'");
			delete.append(deleteParams.getWhCode().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", deleteParams.getWhCode().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(WhMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [WH_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("WH_CODE =");
			restore.append("'");
			restore.append(restoreParams.getWhCode().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", restoreParams.getWhCode().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

