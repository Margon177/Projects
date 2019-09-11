package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.AssemblyBOMMst;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;
import com.nnbp.application.util.StringUtil;
import com.sun.xml.internal.ws.util.StringUtils;


public class AssemblyBOMMstDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public AssemblyBOMMstDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<AssemblyBOMMst> findAllItems() {
		List<AssemblyBOMMst> items = new ArrayList<AssemblyBOMMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , ASSEMBLY_BOM_MST_KEY, ITEM FROM [ASSEMBLY_BOM_MST]";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				AssemblyBOMMst i = new AssemblyBOMMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setAssemblyBOMMstKey(rs.getString("ASSEMBLY_BOM_MST_KEY"));
				i.setItem(rs.getString("ITEM"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public String setSeq(){
		
		String query;
		long seq = 0 ;

		String seqString = "";
		
		try{
			

		query = "SELECT count(1) from ASSEMBLY_BOM_MST";

	PreparedStatement pstmt = connection.prepareStatement(query);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()){
	seq = rs.getLong(1)+1;
	}
	
	rs.close();
	pstmt.close();
	
	seqString = StringUtil.fillZeroLeft("AS" , ""+seq , 10);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seqString;
	}
	


	
	public List<AssemblyBOMMst> findExistingItems() {
		List<AssemblyBOMMst> items = new ArrayList<AssemblyBOMMst>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , ASSEMBLY_BOM_MST_KEY, ITEM FROM [ASSEMBLY_BOM_MST] WHERE DELETE_FLAG = 0";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				AssemblyBOMMst i = new AssemblyBOMMst();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setAssemblyBOMMstKey(rs.getString("ASSEMBLY_BOM_MST_KEY"));
				i.setItem(rs.getString("ITEM"));
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public void updateRecord(AssemblyBOMMst updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [ASSEMBLY_BOM_MST] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" ITEM = '");
		update.append(updateParams.getItem());
		update.append("'");
		update.append(" WHERE ");
		update.append("ASSEMBLY_BOM_MST_KEY = '");
		update.append(updateParams.getAssemblyBOMMstKey().toString());
		update.append("';");
		
		if(updateParams.getItem() == null ||updateParams.getItem().length()>27 ){
			message.error("B��d!", "Edycja nie mo�liwa, Nazwa itemu d�u�sza ni� 27 znak�w");
			return;
		}else{
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Sukces!", "Edycja zako�czona");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}

	public void insertRecord(AssemblyBOMMst insertParams)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [ASSEMBLY_BOM_MST](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, ASSEMBLY_BOM_MST_KEY, ITEM) VALUES (");
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
		insert.append(insertParams.getAssemblyBOMMstKey());
		insert.append("', '");
		insert.append(insertParams.getItem());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT ASSEMBLY_BOM_MST_KEY FROM [ASSEMBLY_BOM_MST] WHERE ASSEMBLY_BOM_MST_KEY ='"+insertParams.getAssemblyBOMMstKey()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getAssemblyBOMMstKey().equals(rs.getString("ASSEMBLY_BOM_MST_KEY"))){
				message.warn("Uwaga!", "Dodawanie nie powiod�o si�, Rekord ju� istnieje");
				return;
			}
		}
		
		if(insertParams.getAssemblyBOMMstKey().equals(null) || insertParams.getAssemblyBOMMstKey().length()>27){
			message.error("B��d!", "Dodawanie nie powiod�o si�, Klucz nie mo�e by� pusty lub d�u�szy ni� 10 znak�w");
		}
		else if(insertParams.getItem().equals(null) || insertParams.getItem().length() > 27){
			message.error("B��d!", "Dodawanie nie powiod�o si�, Nazwa nie mo�e by� pusta lub d�u�sza ni� 27 znak�w");
		}
		else
		{
		call=connection.prepareCall(insert.toString());
		call.executeUpdate();
		message.info("Sukces!", "Dodawanie zako�czone");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRecord(AssemblyBOMMst deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [ASSEMBLY_BOM_MST] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("ASSEMBLY_BOM_MST_KEY =");
			delete.append("'");
			delete.append(deleteParams.getAssemblyBOMMstKey().toString());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, usuni�to: ", deleteParams.getAssemblyBOMMstKey().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(AssemblyBOMMst restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [ASSEMBLY_BOM_MST] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("ASSEMBLY_BOM_MST_KEY =");
			restore.append("'");
			restore.append(restoreParams.getAssemblyBOMMstKey().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, przywr�cono: ", restoreParams.getAssemblyBOMMstKey().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

