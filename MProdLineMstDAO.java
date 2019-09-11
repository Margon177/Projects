package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.MProdLineMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class MProdLineMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public MProdLineMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<MProdLineMst> findAllItems() {
		List<MProdLineMst> items = new ArrayList<MProdLineMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , LINE_NO, DESCRIPTION FROM [M_PROD_LINE_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				MProdLineMst i = new MProdLineMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setLineNo(rs.getString("LINE_NO"));
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
	
	
	public List<MProdLineMst> findExistingItems() {
		List<MProdLineMst> items = new ArrayList<MProdLineMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , LINE_NO, DESCRIPTION FROM [M_PROD_LINE_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				MProdLineMst i = new MProdLineMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setLineNo(rs.getString("LINE_NO"));
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
	
	
	public void updateRecord(MProdLineMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [M_PROD_LINE_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" DESCRIPTION = '");
		update.append(updateParams.getDescription().toString());
		update.append("'");
		update.append(" WHERE ");
		update.append("LINE_NO = '");
		update.append(updateParams.getLineNo().toString());
		update.append("';");
		
		if(updateParams.getDescription().length()>100){
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

	public void insertRecord(MProdLineMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [M_PROD_LINE_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, LINE_NO, DESCRIPTION) VALUES (");
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
		insert.append(insertParams.getLineNo());
		insert.append("', '");
		insert.append(insertParams.getDescription());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT LINE_NO FROM [M_PROD_LINE_MST] WHERE LINE_NO ='"+insertParams.getLineNo()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getLineNo().equals(rs.getString("LINE_NO"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		if(insertParams.getLineNo().length()>10){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Numer Linii nie mo¿e byæ d³u¿szy ni¿ 10 znaków");
		}
		else if(insertParams.getDescription().length()>100){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Opis nie mo¿e byæ d³u¿szy ni¿ 100 znaków");
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
	
	public void deleteRecord(MProdLineMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [M_PROD_LINE_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("LINE_NO =");
			delete.append("'");
			delete.append(deleteParams.getLineNo().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", deleteParams.getLineNo().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(MProdLineMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [M_PROD_LINE_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("LINE_NO =");
			restore.append("'");
			restore.append(restoreParams.getLineNo().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", restoreParams.getLineNo().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

