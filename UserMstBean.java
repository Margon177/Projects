package com.nnbp.application.managedbeans.mst;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.nnbp.application.dao.UserMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.UserMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class UserMstBean {

	private List<UserMst> listItem = new ArrayList<UserMst>();
	private List<UserMst> listItemFiltered = new ArrayList<UserMst>();
	
	private UserMst selectedItem;
	private boolean selView = false;

	private Connection connection = null;
	
	private String userId;
	private String name;
	private String surname;
	private String role;
	private String password;
	private String newPass;

	MessageUtil message = new MessageUtil();
	
	String ModUid = "TESTMODUID";
	String RegUid = "TESTREGUID";
	
	
	@PostConstruct
	public void loadData() {
		searchRecords();
	}
	
	@PreDestroy
	public void closeConnection(){
		DbConnect.close(connection);
	}

	public void searchRecords() {
		try {

			listItem.clear();
			listItemFiltered.clear();
			
			connection = DbConnect.getConnection();
			UserMstDAO userDAO = new UserMstDAO(connection);
			
			if(selView){
			listItem = userDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = userDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			UserMst paramObj = new UserMst();
			paramObj.setModUid(ModUid);
			paramObj.setUserId(selectedItem.getUserId());
			paramObj.setName(selectedItem.getName());
			paramObj.setSurname(selectedItem.getSurname());
			paramObj.setRole(selectedItem.getRole());
			paramObj.setPassword(selectedItem.getPassword());
			
			UserMstDAO userDAO = new UserMstDAO(connection);
			userDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{
			if(name == null || name.length() <= 0){
				
				return;
			}
			
			UserMst paramObj = new UserMst();
			paramObj.setModUid(ModUid);
			paramObj.setRegUid(RegUid);
			paramObj.setUserId(userId);
			paramObj.setName(name);
			paramObj.setSurname(surname);
			paramObj.setRole(role);
			paramObj.setPassword(password);
			
			UserMstDAO userDAO = new UserMstDAO(connection);
			userDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			UserMst paramObj = new UserMst();
			paramObj.setRegUid(RegUid);
			paramObj.setModUid(ModUid);
			paramObj.setUserId(selectedItem.getUserId());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			UserMstDAO userDAO = new UserMstDAO(connection);
			userDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			UserMst paramObj = new UserMst();
			paramObj.setRegUid(RegUid);
			paramObj.setModUid(ModUid);
			paramObj.setUserId(selectedItem.getUserId());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			UserMstDAO userDAO = new UserMstDAO(connection);
			userDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetPassword(){
		try{
			selectedItem.setPassword(selectedItem.getUserId());
			UserMst paramObj = new UserMst();
			paramObj.setRegUid(RegUid);
			paramObj.setModUid(ModUid);
			paramObj.setUserId(selectedItem.getUserId());
			paramObj.setPassword(selectedItem.getPassword());
			
			UserMstDAO userDAO = new UserMstDAO(connection);
			userDAO.resetPassword(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<UserMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<UserMst> listItem) {
		this.listItem = listItem;
	}

	public List<UserMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<UserMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegUid() {
		return RegUid;
	}

	public void setRegUid(String RegUid) {
		this.RegUid = RegUid;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
