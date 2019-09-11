package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.SupplierMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class SupplierMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public SupplierMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<SupplierMst> findAllItems() {
		List<SupplierMst> items = new ArrayList<SupplierMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , SUP_CODE, SUP_NAME, ADDRESS, PREFIX FROM [SUPPLIER_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				SupplierMst i = new SupplierMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setCode(rs.getString("SUP_CODE"));
				i.setName(rs.getString("SUP_NAME"));
				i.setAddress(rs.getString("ADDRESS"));
				i.setPrefix(rs.getString("PREFIX"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<SupplierMst> findExistingItems() {
		List<SupplierMst> items = new ArrayList<SupplierMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , SUP_CODE, SUP_NAME, ADDRESS, PREFIX FROM [SUPPLIER_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				SupplierMst i = new SupplierMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setCode(rs.getString("SUP_CODE"));
				i.setName(rs.getString("SUP_NAME"));
				i.setAddress(rs.getString("ADDRESS"));
				i.setPrefix(rs.getString("PREFIX"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public SupplierMst findOneById(String findParam){
		SupplierMst i = new SupplierMst();
		try{
			String query;
			
			query = "SELECT * FROM [SUPPLIER_MST] WHERE SUP_CODE ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setCode(rs.getString("SUP_CODE"));
				i.setName(rs.getString("SUP_NAME"));
				i.setAddress(rs.getString("ADDRESS"));
				i.setPrefix(rs.getString("PREFIX"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public void updateRecord(SupplierMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [SUPPLIER_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" SUP_NAME = '");
		update.append(updateParams.getName());
		update.append("',");
		update.append(" PREFIX = '");
		update.append(updateParams.getPrefix());
		update.append("',");
		update.append(" ADDRESS = '");
		update.append(updateParams.getAddress().toString());
		update.append("'");
		update.append(" WHERE ");
		update.append("SUP_CODE = '");
		update.append(updateParams.getCode().toString());
		update.append("';");
		
		if(updateParams.getName().length()>50 ){
			message.warn("Uwaga!", "Edycja nie mo¿liwa, Nazwa dostawcy d³u¿sza ni¿ 50 znaków");
			return;
		}
		else if(updateParams.getAddress().length()>50){
			message.warn("Uwaga!", "Edycja nie mo¿liwa, Adres d³u¿szy ni¿ 50 znaków");
		}
		else if(updateParams.getAddress().length()>1 || updateParams.getPrefix() == null){
			message.warn("Uwaga!", "Edycja nie mo¿liwa, Prefix nie mo¿e byæ pusty lub d³u¿szy ni¿ 1 znak");
		}else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Success!", "Edycja zakoñczona");
		}
	} catch (Exception e) {
		e.printStackTrace();
		message.error("B³¹d!", "Edycja nie mo¿liwa!");
	}
		
	}

	public void insertRecord(SupplierMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [SUPPLIER_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, SUP_NAME, SUP_CODE, ADDRESS, PREFIX) VALUES (");
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
		insert.append(insertParams.getCode());
		insert.append("', '");
		insert.append(insertParams.getAddress());
		insert.append("', '");
		insert.append(insertParams.getPrefix());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT SUP_CODE FROM [SUPPLIER_MST] WHERE SUP_CODE ='"+insertParams.getCode()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getCode().equals(rs.getString("SUP_CODE"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getName().equals(null) || insertParams.getName().length()>50){
			message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Nazwa Dostawcy nie mo¿e byæ pusta lub d³u¿sza ni¿ 50 znaków");
		}
		else if(insertParams.getCode().equals(null) || insertParams.getCode().length() > 10){
			message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Kod Dostawcy nie mo¿e byæ pusta lub d³u¿sza ni¿ 10 znaków");
		}
		else if(insertParams.getPrefix().equals(null) || insertParams.getPrefix().length() > 1){
			message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Prefix nie mo¿e byæ pusty lub d³u¿szy ni¿ 1 znak");
		}
		else
		{
		call=connection.prepareCall(insert.toString());
		call.executeUpdate();
		message.info("Sukces!", "Dodawanie zakoñczone");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			message.error("B³¹d!", "Dodawanie nie mo¿liwe, Naruszono wiêzy integralnoœci");
		}
	}
	
	public void deleteRecord(SupplierMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [SUPPLIER_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("SUP_CODE =");
			delete.append("'");
			delete.append(deleteParams.getCode());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, Usuniêto: ", deleteParams.getCode().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(SupplierMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [SUPPLIER_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("SUP_CODE =");
			restore.append("'");
			restore.append(restoreParams.getCode().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, Przywrócono: ", restoreParams.getCode().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

