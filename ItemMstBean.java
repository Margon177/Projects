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

import com.nnbp.application.dao.ItemMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class ItemMstBean {

	private List<ItemMst> listItem = new ArrayList<ItemMst>();
	private List<ItemMst> listItemFiltered = new ArrayList<ItemMst>();
	
	private ItemMst selectedItem;
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
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			
			if(selView){
			listItem = itemDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = itemDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			ItemMst paramObj = new ItemMst();
			paramObj.setModUid(testModUid);
			paramObj.setName(selectedItem.getName());
			paramObj.setDescription(selectedItem.getDescription());
			paramObj.setType(selectedItem.getType());
			
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			itemDAO.updateRecord(paramObj);
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
			
			ItemMst paramObj = new ItemMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setName(name);
			paramObj.setDescription(description);
			paramObj.setType(type);
			
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			itemDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			ItemMst paramObj = new ItemMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setName(selectedItem.getName());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			itemDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			ItemMst paramObj = new ItemMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setName(selectedItem.getName());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			itemDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<ItemMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<ItemMst> listItem) {
		this.listItem = listItem;
	}

	public List<ItemMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<ItemMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public ItemMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemMst selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
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
