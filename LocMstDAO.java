package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.LocMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class LocMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	int MAX_BOOKSTAND_NO =100;
	int MAX_SHELF_NO =100;
	
	public LocMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<LocMst> findAllItems() {
		List<LocMst> items = new ArrayList<LocMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , BOOKSTAND, SHELF, LOC_TYPE FROM [LOC_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				LocMst i = new LocMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setBookstand(rs.getInt("BOOKSTAND"));
				i.setShelf(rs.getInt("SHELF"));
				i.setType(rs.getString("LOC_TYPE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<LocMst> findExistingItems() {
		List<LocMst> items = new ArrayList<LocMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , BOOKSTAND, SHELF, LOC_TYPE FROM [LOC_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				LocMst i = new LocMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setBookstand(rs.getInt("BOOKSTAND"));
				i.setShelf(rs.getInt("SHELF"));
				i.setType(rs.getString("LOC_TYPE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public void updateRecord(LocMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [LOC_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" LOC_TYPE = '");
		update.append(updateParams.getType());
		update.append("'");
		update.append(" WHERE ");
		update.append("BOOKSTAND = '");
		update.append(updateParams.getBookstand());
		update.append("' AND ");
		update.append("SHELF = ");
		update.append("'");
		update.append(updateParams.getShelf());
		update.append("';");
		
		if(updateParams.getType().length()>1 ){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Typ d³u¿szy ni¿ 1 znak");
			return;
		}
		else if(updateParams.getBookstand() >= MAX_BOOKSTAND_NO || updateParams.getShelf() >= MAX_SHELF_NO || updateParams.getBookstand() <= 1 || updateParams.getShelf() <= 1){
			message.error("B³¹d!", "Edycja nie mo¿liwa, bookstand = " +updateParams.getBookstand() +" | shelf = " +updateParams.getShelf() +" poza zakresem, <1,"+MAX_BOOKSTAND_NO+">");
		}else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Sukces!", "Edycja zakoñczona");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

	public void insertRecord(LocMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [LOC_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, BOOKSTAND, SHELF, LOC_TYPE) VALUES (");
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
		insert.append(insertParams.getBookstand());
		insert.append("', '");
		insert.append(insertParams.getShelf());
		insert.append("', '");
		insert.append(insertParams.getType());
		insert.append("');");
		

		String sqlValidateQuerry ="SELECT BOOKSTAND , SHELF FROM [LOC_MST] WHERE BOOKSTAND ='"+insertParams.getBookstand()+"' AND SHELF = '" +insertParams.getShelf() +"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getBookstand() == (rs.getInt("BOOKSTAND")) && insertParams.getShelf() == (rs.getInt("SHELF"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getBookstand() <= 0  || insertParams.getBookstand() > MAX_BOOKSTAND_NO){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Bookstand poza zakresem <1,"+MAX_BOOKSTAND_NO+">");
		}
		else if(insertParams.getShelf() <= 0  || insertParams.getBookstand() > MAX_BOOKSTAND_NO){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Shelf poza zakresem <1,"+MAX_SHELF_NO+">");
		}
		else if(insertParams.getType().length()<=0 || insertParams.getType().length() > 1){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 1 znak");
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
	
	public void deleteRecord(LocMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [LOC_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(" ,");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("BOOKSTAND =");
			delete.append("'");
			delete.append(deleteParams.getBookstand());
			delete.append("' AND SHELF ='");
			delete.append(deleteParams.getShelf());
			delete.append("'");
			delete.append(";");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", "B: " +deleteParams.getBookstand() +" / S: " +deleteParams.getShelf() +" typ: " +deleteParams.getType());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(LocMst restoreParams)
	{
		try{

			StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [LOC_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(" ,");
			restore.append(" MOD_DATE = '");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("BOOKSTAND =");
			restore.append("'");
			restore.append(restoreParams.getBookstand());
			restore.append("' AND SHELF ='");
			restore.append(restoreParams.getShelf());
			restore.append("'");
			restore.append(";");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", "B: " +restoreParams.getBookstand() +" / S: " +restoreParams.getShelf() +" typ: " +restoreParams.getType());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

