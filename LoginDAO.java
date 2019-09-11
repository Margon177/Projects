package com.nnbp.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.nnbp.application.entities.UserMst;
import com.nnbp.application.util.DateUtil;
import com.nnbp.application.util.MessageUtil;


public class LoginDAO {
	
	static Connection connection;
	CallableStatement call = null;
	
	DateUtil date = new DateUtil();
	
	static MessageUtil message = new MessageUtil();
	
	public LoginDAO(Connection conn) {
		this.connection = conn;
	}
	
	public static boolean login(String userId, String password){
		try{
			String query = "SELECT USER_ID , PASSWORD FROM USER_MST WHERE USER_ID = '" + userId +"' AND PASSWORD = '" + password + "';";
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if(rs.getInt("DELETE_FLAG") == 0){
					message.info("Sukces!", "Zalogowano:" +userId);
					return true;
				}else{
				message.error("B³¹d!", "Podany u¿ytkownik jest usuniêty");
				return false;
			}
		}else{
			message.error("B³¹d!","Z³y login lub has³o");
			}
		}
		catch (Exception e) {
	         
			System.out.println("Error in login()" + e.getMessage());
		}
		return false;
	}
	
	
}
