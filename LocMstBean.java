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

import com.nnbp.application.dao.LocMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.LocMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class LocMstBean {

	private List<LocMst> listItem = new ArrayList<LocMst>();
	private List<LocMst> listItemFiltered = new ArrayList<LocMst>();
	
	private LocMst selectedItem;
	private boolean selView = false;

	private Connection connection = null;
	
	private String type;
	private int bookstand;
	private int shelf;
	
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
			LocMstDAO locDAO = new LocMstDAO(connection);
			
			if(selView){
			listItem = locDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = locDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			LocMst paramObj = new LocMst();
			paramObj.setModUid(testModUid);
			paramObj.setType(selectedItem.getType());
			paramObj.setBookstand(selectedItem.getBookstand());
			paramObj.setShelf(selectedItem.getShelf());
			
			LocMstDAO locDAO = new LocMstDAO(connection);
			locDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{
			if(bookstand <= 0 && shelf <= 0){
				
				return;
			}
			
			LocMst paramObj = new LocMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setType(type);
			paramObj.setBookstand(bookstand);
			paramObj.setShelf(shelf);
			
			LocMstDAO locDAO = new LocMstDAO(connection);
			locDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			LocMst paramObj = new LocMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setBookstand(selectedItem.getBookstand());
			paramObj.setShelf(selectedItem.getShelf());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			paramObj.setType(selectedItem.getType());
			
			LocMstDAO locDAO = new LocMstDAO(connection);
			locDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			LocMst paramObj = new LocMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setBookstand(selectedItem.getBookstand());
			paramObj.setShelf(selectedItem.getShelf());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			paramObj.setType(selectedItem.getType());
			
			LocMstDAO locDAO = new LocMstDAO(connection);
			locDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<LocMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<LocMst> listItem) {
		this.listItem = listItem;
	}

	public List<LocMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<LocMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public LocMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(LocMst selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getBookstand() {
		return bookstand;
	}

	public void setBookstand(int bookstand) {
		this.bookstand = bookstand;
	}

	public int getShelf() {
		return shelf;
	}

	public void setShelf(int shelf) {
		this.shelf = shelf;
	}
	
	
}
