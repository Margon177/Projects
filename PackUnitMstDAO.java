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
import com.nnbp.application.entities.PackUnitMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;
import com.nnbp.application.util.StringUtil;


public class PackUnitMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	private int MAX_HEIGHT = 5000;
	private int MAX_LENGTH = 5000;
	private int MAX_WIDTH  = 5000;
	private int MAX_TARE   = 25000; 
	private int MAX_QTY_IN_BOX = 30000;
	
	public PackUnitMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<PackUnitMst> findAllItems() {
		List<PackUnitMst> items = new ArrayList<PackUnitMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , PACKING_KEY, BOX_TYPE, DEFAULT_QTY_IN_BOX, HEIGHT, WIDTH, LENGTH, TARE, ITEM_NAME FROM [PACK_UNIT_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PackUnitMst i = new PackUnitMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPackingKey(rs.getString("PACKING_KEY"));
				i.setBoxType(rs.getString("BOX_TYPE"));
				i.setDefaultQtyInBox(rs.getInt("DEFAULT_QTY_IN_BOX"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setTare(rs.getFloat("TARE"));
				i.setItem(rs.getString("ITEM_NAME"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<PackUnitMst> findExistingItems() {
		List<PackUnitMst> items = new ArrayList<PackUnitMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , PACKING_KEY, BOX_TYPE, DEFAULT_QTY_IN_BOX, HEIGHT, WIDTH, LENGTH, TARE, ITEM_NAME FROM [PACK_UNIT_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PackUnitMst i = new PackUnitMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPackingKey(rs.getString("PACKING_KEY"));
				i.setBoxType(rs.getString("BOX_TYPE"));
				i.setDefaultQtyInBox(rs.getInt("DEFAULT_QTY_IN_BOX"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setTare(rs.getFloat("TARE"));
				i.setItem(rs.getString("ITEM_NAME"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public String setBoxSeq(){		
		String query;
		long seq = 0 ;

		String seqString = "";
		
		try{
		query = "SELECT count(1) from PACK_UNIT_MST WHERE PACKING_KEY LIKE 'B%'";

	PreparedStatement pstmt = connection.prepareStatement(query);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()){
	seq = rs.getLong(1)+1;
	}
	
	rs.close();
	pstmt.close();
	
	seqString = StringUtil.fillZeroLeft("B" , ""+seq , 10);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seqString;
	}
	
	
	public List<PackUnitMst> findByItem(String findParam){
		List<PackUnitMst> items = new ArrayList<PackUnitMst>();

		try{
			String query;
			
			items.clear();
			
			query = "SELECT * FROM [PACK_UNIT_MST] WHERE DELETE_FLAG = '0' AND ITEM_NAME ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {
				PackUnitMst i = new PackUnitMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPackingKey(rs.getString("PACKING_KEY"));
				i.setBoxType(rs.getString("BOX_TYPE"));
				i.setDefaultQtyInBox(rs.getInt("DEFAULT_QTY_IN_BOX"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setTare(rs.getFloat("TARE"));
				i.setItem(rs.getString("ITEM_NAME"));
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
	
	public PackUnitMst findOneById(String findParam){
		PackUnitMst i = new PackUnitMst();
		try{
			String query;
			
			query = "SELECT * FROM [PACK_UNIT_MST] WHERE PACKING_KEY ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setPackingKey(rs.getString("PACKING_KEY"));
				i.setBoxType(rs.getString("BOX_TYPE"));
				i.setDefaultQtyInBox(rs.getInt("DEFAULT_QTY_IN_BOX"));
				i.setHeight(rs.getFloat("HEIGHT"));
				i.setLength(rs.getFloat("LENGTH"));
				i.setWidth(rs.getFloat("WIDTH"));
				i.setTare(rs.getFloat("TARE"));
				i.setItem(rs.getString("ITEM_NAME"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public void updateRecord(PackUnitMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [PACK_UNIT_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" BOX_TYPE = '");
		update.append(updateParams.getBoxType());
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
		update.append(" ITEM_NAME = '");
		update.append(updateParams.getItem());
		update.append("',");
		update.append(" DEFAULT_QTY_IN_BOX = '");
		update.append(updateParams.getDefaultQtyInBox());
		update.append("'");
		update.append(" WHERE ");
		update.append("PACKING_KEY = '");
		update.append(updateParams.getPackingKey().toString());
		update.append("';");
		
		if(updateParams.getPackingKey().equals(null) || updateParams.getPackingKey().length()>10){
			message.error("B³¹d!", "Edycja nie powiod³a siê, Klucz pakowania nie mo¿e byæ pusty lub d³u¿szy ni¿ 10 znaków");
		}
		else if(updateParams.getBoxType().length()<=0 || updateParams.getBoxType().length() > 1){
			message.error("B³¹d!", "Edycja nie powiod³a siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 1 znak");
		}
		else if(updateParams.getHeight() <=0 || updateParams.getHeight() > MAX_HEIGHT)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Wysokoœæ nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_HEIGHT +" cm");
		}
		else if(updateParams.getLength() <=0 || updateParams.getLength() > MAX_LENGTH)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, D³ugoœæ nie mo¿e byæ mniejsza od 0 lub d³u¿sza ni¿ " +MAX_LENGTH +" cm");
		}
		else if(updateParams.getWidth() <=0 || updateParams.getWidth() > MAX_WIDTH)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Szerokoœæ nie mo¿e byæ mniejsza od 0 lub d³u¿sza ni¿ " +MAX_WIDTH +" cm");
		}
		else if(updateParams.getDefaultQtyInBox() > MAX_QTY_IN_BOX){
			message.error("B³¹d!", "Edycja nie powiod³a siê, Domyœlna iloœæ nie mo¿e przekraczaæ " +MAX_QTY_IN_BOX);
		}
		else if(updateParams.getTare() <=0 || updateParams.getTare() > MAX_TARE)
		{
			message.error("B³¹d!", "Edycja nie powiod³a siê, Waga nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_TARE  +" kg");
		}else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Sukces!", "Edycja zakoñczona");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

	public void insertRecord(PackUnitMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [PACK_UNIT_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, PACKING_KEY, BOX_TYPE, DEFAULT_QTY_IN_BOX, HEIGHT, LENGTH, TARE, ITEM_NAME) VALUES (");
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
		insert.append(insertParams.getPackingKey());
		insert.append("', '");
		insert.append(insertParams.getBoxType());;
		insert.append("', '");
		insert.append(insertParams.getDefaultQtyInBox());
		insert.append("', '");
		insert.append(insertParams.getHeight());
		insert.append("', '");
		insert.append(insertParams.getLength());
		insert.append("', '");
		insert.append(insertParams.getTare());
		insert.append("', '");
		insert.append(insertParams.getItem());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT PACKING_KEY FROM [PACK_UNIT_MST] WHERE PACKING_KEY ='"+insertParams.getPackingKey()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getPackingKey().equals(rs.getString("PACKING_KEY"))){
				message.warn("Uwaga!", "Dodawanie nie powiod³o siê, Rekord ju¿ istnieje");
				return;
			}
		}
		
		if(insertParams.getPackingKey().equals(null) || insertParams.getPackingKey().length()>10){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Klucz pakowania nie mo¿e byæ pusty lub d³u¿szy ni¿ 10 znaków");
		}
		else if(insertParams.getBoxType().length()<=0 || insertParams.getBoxType().length() > 1){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Typ nie mo¿e byæ pusty lub d³u¿szy ni¿ 1 znak");
		}
		else if(insertParams.getHeight() <=0 || insertParams.getHeight() > MAX_HEIGHT)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Wysokoœæ nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_HEIGHT +" cm");
		}
		else if(insertParams.getLength() <=0 || insertParams.getLength() > MAX_LENGTH)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, D³ugoœæ nie mo¿e byæ mniejsza od 0 lub d³u¿sza ni¿ " +MAX_LENGTH +" cm");
		}
		else if(insertParams.getDefaultQtyInBox() > MAX_QTY_IN_BOX){
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Domyœlna iloœæ nie mo¿e przekraczaæ " +MAX_QTY_IN_BOX);
		}
		else if(insertParams.getTare() <=0 || insertParams.getTare() > MAX_TARE)
		{
			message.error("B³¹d!", "Dodawanie nie powiod³o siê, Waga nie mo¿e byæ mniejsza od 0 lub wiêksza ni¿ " +MAX_TARE  +" kg");
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
	
	public void deleteRecord(PackUnitMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [PACK_UNIT_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("PACKING_KEY =");
			delete.append("'");
			delete.append(deleteParams.getPackingKey().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuniêto: ", deleteParams.getPackingKey().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(PackUnitMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [PACK_UNIT_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("PACKING_KEY =");
			restore.append("'");
			restore.append(restoreParams.getPackingKey().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywrócono: ", restoreParams.getPackingKey().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

