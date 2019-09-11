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

import com.nnbp.application.dao.WhMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.WhMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class WhMstBean {

	private List<WhMst> listItem = new ArrayList<WhMst>();
	private List<WhMst> listItemFiltered = new ArrayList<WhMst>();
	
	private WhMst selectedItem;
	private boolean selView = false;

	private Connection connection = null;
	
	private String whCode;
	private String whType;

	MessageUtil message = new MessageUtil();
	
	String testModUid = "TESTMODUID";
	String testRegUid = "TESTREGUID";
	
	
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
			WhMstDAO whDAO = new WhMstDAO(connection);
			
			if(selView){
			listItem = whDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = whDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			WhMst paramObj = new WhMst();
			paramObj.setModUid(testModUid);
			paramObj.setWhCode(selectedItem.getWhCode());
			paramObj.setWhType(selectedItem.getWhType());

			WhMstDAO whDAO = new WhMstDAO(connection);
			whDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{
			if(whCode == null || whCode.length() <= 0){
				
				return;
			}
			
			WhMst paramObj = new WhMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setWhCode(whCode);
			paramObj.setWhType(whType);
			
			WhMstDAO whDAO = new WhMstDAO(connection);
			whDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			WhMst paramObj = new WhMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setWhCode(selectedItem.getWhCode());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			WhMstDAO whDAO = new WhMstDAO(connection);
			whDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			WhMst paramObj = new WhMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setWhCode(selectedItem.getWhCode());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			WhMstDAO whDAO = new WhMstDAO(connection);
			whDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<WhMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<WhMst> listItem) {
		this.listItem = listItem;
	}

	public List<WhMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<WhMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public WhMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(WhMst selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getTestRegUid() {
		return testRegUid;
	}

	public void setTestRegUid(String testRegUid) {
		this.testRegUid = testRegUid;
	}

	public boolean isSelView() {
		return selView;
	}

	public void setSelView(boolean selView) {
		this.selView = selView;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getWhType() {
		return whType;
	}

	public void setWhType(String whType) {
		this.whType = whType;
	}
	
	
}
