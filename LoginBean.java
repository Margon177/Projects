package com.nnbp.application.managedbeans;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.nnbp.application.dao.LoginDAO;
import com.nnbp.application.dao.UserMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.UserMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	
	private UserMst selectedItem;
	private boolean selView = false;

	private Connection connection = null;
	
	private String userId;
	
	private String password;
	private String newPass;

	MessageUtil message = new MessageUtil();
	
	String ModUid;
	String RegUid;
	
	@PreDestroy
	public void closeConnection(){
		DbConnect.close(connection);
	}

	public void login(){
		FacesMessage message = null;
		
	     boolean loggedIn = LoginDAO.login(userId,password);
	     if(loggedIn){
	    	 
	           // HttpSession session = Util.getSession();
	         //   session.setAttribute("username", userId);  
	            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Witaj ", userId);
	        } else {
	            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "B³¹d Logowania ", "Niepoprawne dane");
	        }
		
        FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String getModUid() {
		return ModUid;
	}

	public void setModUid(String modUid) {
		ModUid = modUid;
	}


	public String getRegUid() {
		return RegUid;
	}

	public void setRegUid(String regUid) {
		RegUid = regUid;
	}

	public UserMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(UserMst selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSelView() {
		return selView;
	}

	public void setSelView(boolean selView) {
		this.selView = selView;
	}
	
	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	
	
}
