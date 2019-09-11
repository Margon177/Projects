package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.PackPalleteMst;
import com.nnbp.application.entities.PackPalleteMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;
import com.nnbp.application.util.StringUtil;


public class PackPalleteMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	private int MAX_HEIGHT = 5000;
	private int MAX_LENGTH = 5000;
	private int MAX_TARE   = 25000; 
	private int MAX_WEIGHT = 99999;
	private int MAX_WIDTH  = 5000;
	
	public PackPalleteMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<PackPalleteMst> findAllItems() {
		List<PackPalleteMst> items = new ArrayList<PackPalleteMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , PALLETE_KEY, TYPE, HEIGHT, LENGTH, WIDTH, MAX_WEIGHT, TARE  FROM [PACK_PALLETE_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PackPalleteMst i = new PackPalleteMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("PALLETE_KEY"));
				i.setType(rs.getString("TYPE"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setMaxWeight(rs.getFloat("MAX_WEIGHT"));
				i.setTare(rs.getFloat("TARE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<PackPalleteMst> findExistingItems() {
		List<PackPalleteMst> items = new ArrayList<PackPalleteMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , PALLETE_KEY, TYPE, HEIGHT, WIDTH, MAX_WEIGHT, LENGTH, TARE  FROM [PACK_PALLETE_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PackPalleteMst i = new PackPalleteMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("PALLETE_KEY"));
				i.setType(rs.getString("TYPE"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setMaxWeight(rs.getFloat("MAX_WEIGHT"));
				i.setTare(rs.getFloat("TARE"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public String setPalleteSeq(){		
		String query;
		long seq = 0 ;

		String seqString = "";
		
		try{
		query = "SELECT count(1) from PACK_PALLETE_MST";

	PreparedStatement pstmt = connection.prepareStatement(query);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()){
	seq = rs.getLong(1)+1;
	}
	
	rs.close();
	pstmt.close();
	
	seqString = StringUtil.fillZeroLeft("P" , ""+seq , 10);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seqString;
	}
	
	
	public List<PackPalleteMst> findByItem(String findParam){
		List<PackPalleteMst> items = new ArrayList<PackPalleteMst>();

		try{
			String query;
			
			items.clear();
			
			query = "SELECT * FROM [PACK_PALLETE_MST] WHERE DELETE_FLAG = '0' AND  ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {
				PackPalleteMst i = new PackPalleteMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("PALLETE_KEY"));
				i.setType(rs.getString("TYPE"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setMaxWeight(rs.getFloat("MAX_WEIGHT"));
				i.setTare(rs.getFloat("TARE"));
				items.add(i);
			}
			
			rs.close();
			pstmt.close();	
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public PackPalleteMst findOneById(String findParam){
		PackPalleteMst i = new PackPalleteMst();
		try{
			String query;
			
			query = "SELECT * FROM [PACK_PALLETE_MST] WHERE PALLETE_KEY ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPalleteKey(rs.getString("PALLETE_KEY"));
				i.setType(rs.getString("TYPE"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setMaxWeight(rs.getFloat("MAX_WEIGHT"));
				i.setTare(rs.getFloat("TARE"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public void updateRecord(PackPalleteMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [PACK_PALLETE_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" TYPE = '");
		update.append(updateParams.getType());
		update.append("',");
		update.append(" HEIGHT = '");
		update.append(updateParams.getHeight());
		update.append("',");
		update.append(" LENGTH = '");
		update.append(updateParams.getLength());
		update.append("',");
		update.append(" TARE = '");
		update.append(updateParams.getTare());
		update.append("',");
		update.append(" WIDTH = '");
		update.append(updateParams.getWidth());
		update.append("',");
		update.append(" MAX_WEIGHT = '");
		update.append(updateParams.getMaxWeight());
		update.append("'");
		update.append(" WHERE ");
		update.append("PALLETE_KEY = '");
		update.append(updateParams.getPalleteKey().toString());
		update.append("';");
		
		if(updateParams.getPalleteKey().equals(null) || updateParams.getPalleteKey().length()>10){
			message.error("B³¹d!", "Edycja nie powiod³a siê, Klucz pakowania nie mo¿e byæ pusty lub d³u¿szy ni¿ 10 znaków");
		}
		else if(updateParams.getType().length()<=0 || updateParams.getType().length() > 8){
			message.error("B³¹d!", "Edycja nie powiod³a siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 8 znaków");
		}
		else if(updateParams.getHeight() <=0 || updateParams.getHeight() > MAX_HEIGHT)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Wysokoœæ nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_HEIGHT +" cm");
		}
		else if(updateParams.getLength() <=0 || updateParams.getLength() > MAX_LENGTH)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, D³ugoœæ nie mo¿e byæ mniejsza od 0 lub d³u¿sza ni¿ " +MAX_LENGTH +" cm");
		}
		else if(updateParams.getTare() <=0 || updateParams.getTare() > MAX_TARE)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Waga nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_TARE  +" kg");
		}
		else if(updateParams.getMaxWeight() <=0 || updateParams.getTare() > MAX_WEIGHT)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Maksymalna Waga nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_TARE  +" kg");
		}
		else if(updateParams.getWidth() <=0 || updateParams.getWidth() > MAX_WIDTH)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Szerokoœæ nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_WIDTH  +" kg");
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

	public void insertRecord(PackPalleteMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [PACK_PALLETE_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, PALLETE_KEY, TYPE, HEIGHT, LENGTH, WIDTH, MAX_WEIGHT, TARE ) VALUES (");
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
		insert.append(insertParams.getPalleteKey());
		insert.append("', '");
		insert.append(insertParams.getType());;
		insert.append("', '");
		insert.append(insertParams.getHeight());
		insert.append("', '");
		insert.append(insertParams.getLength());
		insert.append("', '");
		insert.append(insertParams.getWidth());
		insert.append("', '");
		insert.append(insertParams.getMaxWeight());
		insert.append("', '");
		insert.append(insertParams.getTare());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT PALLETE_KEY FROM [PACK_PALLETE_MST] WHERE PALLETE_KEY ='"+insertParams.getPalleteKey()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getPalleteKey().equals(rs.getString("PALLETE_KEY"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getPalleteKey().equals(null) || insertParams.getPalleteKey().length()>10){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Klucz pakowania nie mo¿e byæ pusty lub d³u¿szy ni¿ 10 znaków");
		}
		else if(insertParams.getType().length()<=0 || insertParams.getType().length() > 8){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 8 znaków");
		}
		else if(insertParams.getHeight() <=0 || insertParams.getHeight() > MAX_HEIGHT)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Wysokoœæ nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_HEIGHT +" cm");
		}
		else if(insertParams.getLength() <=0 || insertParams.getLength() > MAX_LENGTH)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, D³ugoœæ nie mo¿e byæ mniejsza od 0 lub d³u¿sza ni¿ " +MAX_LENGTH +" cm");
		}
		else if(insertParams.getTare() <=0 || insertParams.getTare() > MAX_TARE)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Waga nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_TARE  +" kg");
		} 
		else if(insertParams.getMaxWeight() <=0 || insertParams.getTare() > MAX_WEIGHT)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Maksymalna Waga nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_TARE  +" kg");
		}
		else if(insertParams.getWidth() <=0 || insertParams.getWidth() > MAX_WIDTH)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Szerokoœæ nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_WIDTH  +" kg");
		}
		
		else {
		call=connection.prepareCall(insert.toString());
		call.executeUpdate();
		message.info("Sukces!", "Dodawanie zakoñczone");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRecord(PackPalleteMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [PACK_PALLETE_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("PALLETE_KEY =");
			delete.append("'");
			delete.append(deleteParams.getPalleteKey().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", deleteParams.getPalleteKey().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(PackPalleteMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [PACK_PALLETE_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("PALLETE_KEY =");
			restore.append("'");
			restore.append(restoreParams.getPalleteKey().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", restoreParams.getPalleteKey().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

