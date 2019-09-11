package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.UserMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class UserMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public UserMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<UserMst> findAllItems() {
		List<UserMst> items = new ArrayList<UserMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , USER_ID, NAME, SURNAME, PASSWORD, ROLE, LAST_LOGIN_DATE FROM [USER_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				UserMst i = new UserMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setUserId(rs.getString("USER_ID"));
				i.setName(rs.getString("NAME"));
				i.setSurname(rs.getString("SURNAME"));
				i.setPassword(rs.getString("PASSWORD"));
				i.setRole(rs.getString("ROLE"));
				i.setLastLoginDate(rs.getString("LAST_LOGIN_DATE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<UserMst> findExistingItems() {
		List<UserMst> items = new ArrayList<UserMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , USER_ID, NAME, SURNAME, PASSWORD, ROLE, LAST_LOGIN_DATE FROM [USER_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				UserMst i = new UserMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setUserId(rs.getString("USER_ID"));
				i.setName(rs.getString("NAME"));
				i.setSurname(rs.getString("SURNAME"));
				i.setPassword(rs.getString("PASSWORD"));
				i.setRole(rs.getString("ROLE"));
				i.setLastLoginDate(rs.getString("LAST_LOGIN_DATE"));
				items.add(i);

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public UserMst findOneById(String findParam){
		UserMst i = new UserMst();
		try{
			String query;
			
			query = "SELECT * FROM [USER_MST] WHERE USER_ID ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setUserId(rs.getString("USER_ID"));
				i.setName(rs.getString("NAME"));
				i.setSurname(rs.getString("SURNAME"));
				i.setRole(rs.getString("ROLE"));
				i.setPassword(rs.getString("PASSWORD"));
				i.setLastLoginDate(rs.getString("LAST_LOGIN_DATE"));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	
	public void updateRecord(UserMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [USER_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" NAME = '");
		update.append(updateParams.getName());
		update.append("',");
		update.append(" SURNAME = '");
		update.append(updateParams.getSurname());
		update.append("',");
		update.append(" PASSWORD = '");
		update.append(updateParams.getPassword());
		update.append("',");
		update.append(" ROLE = '");
		update.append(updateParams.getRole());
		update.append("'");
		update.append(" WHERE ");
		update.append("USER_ID = '");
		update.append(updateParams.getUserId());
		update.append("';");
		
		if(updateParams.getName().length()>50 ){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Imiê d³u¿sze ni¿ 50 znaków");
			return;
		}
		else if(updateParams.getSurname().length()>50){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Nazwisko d³u¿sze ni¿ 50 znaków");
		}
		else if(updateParams.getPassword().length()>20){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Has³o d³u¿sze ni¿ 20 znaków");
		}
		else if(updateParams.getPassword().length() < 0){
			message.error("B³¹d!", "Edycja nie mo¿liwa, Has³o nie mo¿e byæ puste");
		}
		else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Sukces!", "Edycja zakoñczona");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

	public void insertRecord(UserMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [USER_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, USER_ID, NAME, SURNAME, ROLE, PASSWORD) VALUES (");
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
		insert.append(insertParams.getUserId());
		insert.append("', '");
		insert.append(insertParams.getName());;
		insert.append("', '");
		insert.append(insertParams.getSurname());;
		insert.append("', '");
		insert.append(insertParams.getRole());
		insert.append("', '");
		insert.append(insertParams.getUserId());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT USER_ID FROM [USER_MST] WHERE USER_ID ='"+insertParams.getUserId()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getUserId().equals(rs.getString("USER_ID"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getUserId().equals(null) || insertParams.getUserId().length()>12){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, ID U¿ytkownika nie mo¿e byæ puste lub d³u¿sze ni¿ 12 znaków");
		}
		else if(insertParams.getName().length()<=0 || insertParams.getName().length() > 50){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Imiê nie mo¿e byæ puste lub d³u¿sze ni¿ 50 znaków");
		}
		else if(insertParams.getRole().length()<=0){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Rola nie mo¿e byæ pusta");
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
	
	public void deleteRecord(UserMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [USER_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("USER_ID =");
			delete.append("'");
			delete.append(deleteParams.getUserId().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", deleteParams.getUserId().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(UserMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [USER_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("USER_ID =");
			restore.append("'");
			restore.append(restoreParams.getUserId());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", restoreParams.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetPassword(UserMst resetParams)
	{
		try{

					StringBuffer reset = new StringBuffer();
					reset.append("UPDATE [USER_MST] SET PASSWORD = '");
					reset.append(resetParams.getPassword());
					reset.append("', ");
					reset.append("MOD_DATE ='");
					reset.append(date.getCurrentDateObj());
					reset.append("'");
					reset.append(" WHERE ");
					reset.append("USER_ID =");
					reset.append("'");
					reset.append(resetParams.getUserId());
					reset.append("';");
					
					call=connection.prepareCall(reset.toString());
					call.executeUpdate();
					message.info("Sukces, Zresetowano has³o dla: " + resetParams.getUserId().toString() , " has³o pierwszego logowania to ID U¿ytkownika");	
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

