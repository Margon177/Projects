package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nnbp.application.entities.Receiving;
import com.nnbp.application.entities.ReceivingPallete;
import com.nnbp.application.entities.SupplierMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;
import com.nnbp.application.util.StringUtil;


public class ReceivingPalleteDAO {
	
	Connection connection;
	CallableStatement call = null;
	
	public boolean state = true;
	
	DateUtil date = new DateUtil();
	
	MessageUtil message = new MessageUtil();
	
	public ReceivingPalleteDAO(Connection conn) {
		this.connection = conn;
	}
	
	
	
	public List<ReceivingPallete> findAllItems(Receiving selectedLine) {
		List<ReceivingPallete> items = new ArrayList<ReceivingPallete>();
		try {
			
			items.clear();
			
			String query;

				query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_PALLETE_KEY, DELIVERY_HDR_KEY, PALLETE_NO, PALLETE_NO2, PALLETE_TYPE, PALLETE_MAX_WEIGHT, PALLETE_CURRENT_WEIGHT FROM [DELIVERY_PALLETE_TBL] WHERE DELIVERY_HDR_KEY = '"+selectedLine.getDeliveryHdrKey()+"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ReceivingPallete i = new ReceivingPallete();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setPalleteKey(rs.getString("DELIVERY_PALLETE_KEY"));
				i.setPalleteNo(rs.getString("PALLETE_NO"));
				i.setPalleteNo2(rs.getString("PALLETE_NO2"));
				i.setType(rs.getString("PALLETE_TYPE"));
				i.setMaxWeight(rs.getFloat("PALLETE_MAX_WEIGHT"));
				i.setCurrentWeight(rs.getFloat("PALLETE_CURRENT_WEIGHT"));
				
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<ReceivingPallete> findExistingItems(Receiving selectedLine) {
		List<ReceivingPallete> items = new ArrayList<ReceivingPallete>();
		try {
			
			items.clear();
			
			String query;

			query = "SELECT DELETE_FLAG , REG_DATE , REG_UID , MOD_DATE , MOD_UID , DELIVERY_PALLETE_KEY, DELIVERY_HDR_KEY, PALLETE_NO, PALLETE_NO2, PALLETE_TYPE, PALLETE_MAX_WEIGHT, PALLETE_CURRENT_WEIGHT FROM [DELIVERY_PALLETE_TBL] WHERE DELETE_FLAG = 0 AND DELIVERY_HDR_KEY = '"+selectedLine.getDeliveryHdrKey()+"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ReceivingPallete i = new ReceivingPallete();
				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setPalleteKey(rs.getString("DELIVERY_PALLETE_KEY"));
				i.setPalleteNo(rs.getString("PALLETE_NO"));
				i.setPalleteNo2(rs.getString("PALLETE_NO2"));
				i.setType(rs.getString("PALLETE_TYPE"));
				i.setMaxWeight(rs.getFloat("PALLETE_MAX_WEIGHT"));
				i.setCurrentWeight(rs.getFloat("PALLETE_CURRENT_WEIGHT"));
				
				items.add(i);
				

			}

		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public ReceivingPallete findOneById(String findParam){
		ReceivingPallete i = new ReceivingPallete();
		try{
			String query;
			
			query = "SELECT * FROM [DELIVERY_PALLETE_TBL] WHERE DELIVERY_PALLETE_KEY ='" +findParam +"';";

			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	
			
			while (rs.next()) {

				i.setDeleteFlag(rs.getInt("DELETE_FLAG"));
				i.setRegDate(rs.getString("REG_DATE"));
				i.setRegUid(rs.getString("REG_UID"));
				i.setModDate(rs.getString("MOD_DATE"));
				i.setModUid(rs.getString("MOD_UID"));
				i.setDeliveryHdrKey(rs.getString("DELIVERY_HDR_KEY"));
				i.setPalleteKey(rs.getString("DELIVERY_PALLETE_KEY"));
				i.setPalleteNo(rs.getString("PALLETE_NO"));
				i.setType(rs.getString("PALLETE_TYPE"));
				i.setMaxWeight(rs.getFloat("PALLETE_MAX_WEIGHT"));
				i.setCurrentWeight(rs.getFloat("PALLETE_CURRENT_WEIGHT"));

			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
public String setPalleteSeq(int size){
		
		String query;
		long seq = 0 ;

		String seqString = "";
		
		try{
		query = "SELECT count(1) from DELIVERY_PALLETE_TBL";

	PreparedStatement pstmt = connection.prepareStatement(query);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs.next()){
	seq = rs.getLong(1)+1+size;
	}
	
	rs.close();
	pstmt.close();
	
	seqString = StringUtil.fillZeroLeft("DP" , ""+seq , 10);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return seqString;
	}

	public String setPalleteNoSeq(ReceivingPallete params,Receiving hdr ,int size){
		String query,prefixquerry,prefix,countQuerry,strSeq;
		long seq=0;
		Integer convSeq = new Integer(0);
		
		String seqString ="";
		
		try{

			prefixquerry = "SELECT PREFIX FROM SUPPLIER_MST WHERE SUP_CODE ='" +hdr.getSupCode() +"';";
		
			PreparedStatement supPstmt = connection.prepareStatement(prefixquerry);
			ResultSet supRs = supPstmt.executeQuery();
			
			 if(supRs.next())
			 	{
			prefix = supRs.getString("PREFIX");
			 	

			 
			query="SELECT MAX(PALLETE_NO) FROM DELIVERY_PALLETE_TBL WHERE PALLETE_NO LIKE '"+ prefix +"%';";
			countQuerry="SELECT COUNT(1) FROM DELIVERY_PALLETE_TBL WHERE DELETE_FLAG='0' AND PALLETE_NO LIKE '"+ prefix +"%';";
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			PreparedStatement validatePstmt = connection.prepareStatement(countQuerry);
			
			ResultSet validate = validatePstmt.executeQuery();
			
			if(validate.next() && validate.getInt(1) == 0 ){
				seq =1 + size;
				seqString = StringUtil.fillZeroLeft(prefix , ""+seq , 5);
			}
			else
			{
			
			ResultSet rs = pstmt.executeQuery();
	
			 if(rs.next())
			 	{
			
			strSeq = rs.getString(1).substring(1); 
			convSeq = convSeq.parseInt(strSeq);
			seq = convSeq.longValue() + 1 + size;
			//seq = rs.getLong(1) + 1 + size;
			
			seqString = StringUtil.fillZeroLeft(prefix , ""+seq , 5);
			
			 	}
			}
		}
	}
			catch (Exception e) {
			e.printStackTrace();
		}
		return seqString;
	}
	
	public String setPalleteNo2Seq(ReceivingPallete params,Receiving hdr, int size){
		String query,countQuerry;
		long seq=0;
		
		String seqString ="";
		
		try{
			query="SELECT MAX(PALLETE_NO2) FROM DELIVERY_PALLETE_TBL WHERE PALLETE_NO2";
			countQuerry="SELECT COUNT(PALLETE_NO) FROM DELIVERY_PALLETE_TBL WHERE PALLETE_NO LIKE 'N%';";
			
			PreparedStatement validatePstmt = connection.prepareStatement(countQuerry);
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			ResultSet validate = validatePstmt.executeQuery();
			
			if(validate.next() && validate.getInt(1) == 0){
				seq =1 + size;
				seqString = StringUtil.fillZeroLeft("N" , ""+seq , 5);
			}
			else
			{
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
				{
			seq = rs.getLong(1)+1 + size;
			seqString = StringUtil.fillZeroLeft("N" , ""+seq , 5);
				}
			}
			}
			catch (Exception e) {
			e.printStackTrace();
		}
		return seqString;
	}
	
	public String getNextPalleteSeq(Receiving selectedItem){
		String palSeq,nextSeq = new String();
		Integer seq = new Integer(0);
		
		SupplierMst sup;
		SupplierMstDAO supDAO = new SupplierMstDAO(connection);
		
		sup = supDAO.findOneById(selectedItem.getSupCode());
		
		String query="SELECT MAX(PALLETE_NO) FROM DELIVERY_PALLETE_TBL WHERE PALLETE_NO LIKE'"+sup.getPrefix() +"%'";

		try {

		PreparedStatement pstmt = connection.prepareStatement(query);
		

			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				palSeq=rs.getString(1);
				seq = seq.parseInt(palSeq.substring(1));
				seq++;
				nextSeq = StringUtil.fillZeroLeft(palSeq.substring(0, 1) , ""+seq , 5);
			}

		} catch (SQLException e) {
			message.error("B³¹d!", "Brak sekwencji!");
			e.printStackTrace();
		}
		return nextSeq;
	}
	
	public String getNextPallete2Seq(Receiving selectedItem){

		String palSeq,nextSeq = new String();
		Integer seq = new Integer(0);
		
		SupplierMst sup;
		SupplierMstDAO supDAO = new SupplierMstDAO(connection);
		
		sup = supDAO.findOneById(selectedItem.getSupCode());
		
		
		String query="SELECT MAX(PALLETE_NO2) FROM DELIVERY_PALLETE_TBL WHERE PALLETE_NO LIKE 'A%';";
		
		try{
		
			PreparedStatement pstmt = connection.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				palSeq = rs.getString(1);
				if(palSeq == "null"){
					palSeq = "N0001";
				}

				seq = seq.parseInt(palSeq.substring(1));
				seq++;
				nextSeq = StringUtil.fillZeroLeft(palSeq.substring(0, 1) , ""+seq , 5);
			}

		} catch (SQLException e) {
			message.error("B³¹d!", "Brak sekwencji!");
			e.printStackTrace();
		}
		return nextSeq;
	}
	
	public void updateRecord(ReceivingPallete updateParams) throws SQLException
	{
		try {
		StringBuffer update = new StringBuffer();
		update.append("UPDATE [DELIVERY_PALLETE_TBL] SET ");
		update.append("MOD_DATE ='");
		update.append(date.getCurrentDateObj());
		update.append("',");
		update.append(" MOD_UID = ");
		update.append("'");
		update.append(updateParams.getModUid());
		update.append("',");
		update.append(" PALLETE_NO = '");
		update.append(updateParams.getPalleteNo());
		update.append("',");
		update.append(" PALLETE_TYPE = '");
		update.append(updateParams.getType());
		update.append("',");
		update.append(" PALLETE_MAX_WEIGHT = '");
		update.append(updateParams.getMaxWeight());
		update.append("',");
		update.append(" PALLETE_CURRENT_WEIGHT = '");
		update.append(updateParams.getCurrentWeight());
		update.append("'");
		update.append(" WHERE ");
		update.append("DELIVERY_PALLETE_KEY = '");
		update.append(updateParams.getPalleteKey());
		update.append("';");
		
		call=connection.prepareCall(update.toString());
		call.executeUpdate();
		message.info("Success!", "Edycja zakoñczona");
		
	} catch (Exception e) {
		e.printStackTrace();
		message.error("B³¹d!", "Edycja nie mo¿liwa!");
	}
		
	}

	public void insertRecord(ReceivingPallete insertParams , Receiving header)
	{
		try{
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO [DELIVERY_PALLETE_TBL](DELETE_FLAG, REG_DATE, REG_UID, MOD_DATE, MOD_UID, DELIVERY_PALLETE_KEY, DELIVERY_HDR_KEY, PALLETE_NO, PALLETE_NO2, PALLETE_TYPE, PALLETE_MAX_WEIGHT, PALLETE_CURRENT_WEIGHT ) VALUES (");
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
		insert.append(header.getDeliveryHdrKey());
		insert.append("', '");
		insert.append(insertParams.getPalleteNo());
		insert.append("', '");
		insert.append(insertParams.getPalleteNo2());
		insert.append("', '");
		insert.append(insertParams.getType());
		insert.append("', '");
		insert.append(insertParams.getMaxWeight());
		insert.append("', '");
		insert.append(insertParams.getCurrentWeight());
		insert.append("');");
		
		String sqlValidateQuerry ="SELECT DELIVERY_PALLETE_KEY FROM [DELIVERY_PALLETE_TBL] WHERE DELIVERY_PALLETE_KEY ='"+insertParams.getPalleteKey()+"';";

		PreparedStatement pstmt = connection.prepareStatement(sqlValidateQuerry);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if(insertParams.getPalleteKey().equals(rs.getString("DELIVERY_PALLETE_KEY"))){
				message.warn("Uwaga!", "Dodawanie nie powiodï¿½o siï¿½, Rekord juï¿½ istnieje");
				return;
			}
		}
		
		call=connection.prepareCall(insert.toString());
		call.executeUpdate();
		message.info("Sukces!", "Dodawanie zakoñczone rekord palety: "+insertParams.getPalleteKey());

		} catch (Exception e) {
			state = false;
			e.printStackTrace();
			message.error("B³¹d", "Dodawanie nie moï¿½liwe, Naruszono wiï¿½zy integralnoï¿½ci");
		}
	}
	
	public void deleteRecord(ReceivingPallete deleteParams)
	{
		try{

		StringBuffer delete = new StringBuffer();
			delete.append("UPDATE [DELIVERY_PALLETE_TBL] SET DELETE_FLAG = ");
			delete.append(deleteParams.getDeleteFlag());
			delete.append(",");
			delete.append("MOD_DATE ='");
			delete.append(date.getCurrentDateObj());
			delete.append("'");
			delete.append(" WHERE ");
			delete.append("DELIVERY_PALLETE_KEY =");
			delete.append("'");
			delete.append(deleteParams.getPalleteKey());
			delete.append("';");
			
			call=connection.prepareCall(delete.toString());
			call.executeUpdate();
			message.info("Sukces, Usuniêto: ", deleteParams.getPalleteNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreRecord(ReceivingPallete restoreParams)
	{
		try{

		StringBuffer restore = new StringBuffer();
			restore.append("UPDATE [DELIVERY_PALLETE_TBL] SET DELETE_FLAG = ");
			restore.append(restoreParams.getDeleteFlag());
			restore.append(",");
			restore.append("MOD_DATE ='");
			restore.append(date.getCurrentDateObj());
			restore.append("'");
			restore.append(" WHERE ");
			restore.append("DELIVERY_PALLETE_KEY =");
			restore.append("'");
			restore.append(restoreParams.getPalleteKey().toString());
			restore.append("';");
			
			call=connection.prepareCall(restore.toString());
			call.executeUpdate();
			message.info("Sukces, Przywrócono: ", restoreParams.getDeliveryHdrKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

