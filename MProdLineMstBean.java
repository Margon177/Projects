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

import com.nnbp.application.dao.MProdLineMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.MProdLineMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class MProdLineMstBean {

	private List<MProdLineMst> listItem = new ArrayList<MProdLineMst>();
	private List<MProdLineMst> listItemFiltered = new ArrayList<MProdLineMst>();
	
	private MProdLineMst selectedItem;
	private boolean selView = false;

	private Connection connection = null;
	
	private String name;
	private String type;
	private String description;
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
			MProdLineMstDAO MProdLineDAO = new MProdLineMstDAO(connection);
			
			if(selView){
			listItem = MProdLineDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = MProdLineDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			MProdLineMst paramObj = new MProdLineMst();
			paramObj.setModUid(testModUid);
			paramObj.setLineNo(selectedItem.getLineNo());
			paramObj.setDescription(selectedItem.getDescription());
			
			MProdLineMstDAO MProdLineDAO = new MProdLineMstDAO(connection);
			MProdLineDAO.updateRecord(paramObj);
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
			
			MProdLineMst paramObj = new MProdLineMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setLineNo(name);
			paramObj.setDescription(description);
			
			MProdLineMstDAO MProdLineDAO = new MProdLineMstDAO(connection);
			MProdLineDAO.insertRecord(paramObj);
			
			searchRecords();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			MProdLineMst paramObj = new MProdLineMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setLineNo(selectedItem.getLineNo());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			MProdLineMstDAO MProdLineDAO = new MProdLineMstDAO(connection);
			MProdLineDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			MProdLineMst paramObj = new MProdLineMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setLineNo(selectedItem.getLineNo());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			MProdLineMstDAO MProdLineDAO = new MProdLineMstDAO(connection);
			MProdLineDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<MProdLineMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<MProdLineMst> listItem) {
		this.listItem = listItem;
	}

	public List<MProdLineMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<MProdLineMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public MProdLineMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(MProdLineMst selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getLineNo() {
		return name;
	}

	public void setLineNo(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
}
