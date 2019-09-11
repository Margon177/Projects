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

import com.nnbp.application.dao.SupplierMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.SupplierMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class SupplierMstBean {

	private List<SupplierMst> listSupplierMst = new ArrayList<SupplierMst>();
	private List<SupplierMst> listSupplierMstFiltered = new ArrayList<SupplierMst>();
	
	private SupplierMst selectedItem;
	private boolean selView = false;

	private Connection connection = null;
	
	private String name;
	private String code;
	private String address;
	private String prefix;
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
			

			listSupplierMst.clear();
			listSupplierMstFiltered.clear();
			
			connection = DbConnect.getConnection();
			SupplierMstDAO supplierDAO = new SupplierMstDAO(connection);
			
			if(selView){
			listSupplierMst = supplierDAO.findAllItems();
			listSupplierMstFiltered.addAll(listSupplierMst);
			}
			else
			{
				listSupplierMst = supplierDAO.findExistingItems();
				listSupplierMstFiltered.addAll(listSupplierMst);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			SupplierMst paramObj = new SupplierMst();
			paramObj.setModUid(testModUid);
			paramObj.setName(selectedItem.getName());
			paramObj.setCode(selectedItem.getCode());
			paramObj.setAddress(selectedItem.getAddress());
			paramObj.setPrefix(selectedItem.getPrefix());
			
			SupplierMstDAO supplierDAO = new SupplierMstDAO(connection);
			supplierDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{
			
			SupplierMst paramObj = new SupplierMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setName(name);
			paramObj.setCode(code);
			paramObj.setAddress(address);
			paramObj.setPrefix(prefix);
			
			SupplierMstDAO supplierMstDAO = new SupplierMstDAO(connection);
			supplierMstDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			SupplierMst paramObj = new SupplierMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setCode(selectedItem.getCode());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			SupplierMstDAO supplierDAO = new SupplierMstDAO(connection);
			supplierDAO.deleteRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			SupplierMst paramObj = new SupplierMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setCode(selectedItem.getCode());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			SupplierMstDAO supplierDAO = new SupplierMstDAO(connection);
			supplierDAO.restoreRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Search All" : "Existing Only";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<SupplierMst> getlistSupplierMst() {
		return listSupplierMst;
	}

	public void setlistSupplierMst(List<SupplierMst> listSupplierMst) {
		this.listSupplierMst = listSupplierMst;
	}

	public List<SupplierMst> getlistSupplierMstFiltered() {
		return listSupplierMstFiltered;
	}

	public void setlistSupplierMstFiltered(List<SupplierMst> listSupplierMstFiltered) {
		this.listSupplierMstFiltered = listSupplierMstFiltered;
	}

	public SupplierMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(SupplierMst selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTestRegUid() {
		return testRegUid;
	}

	public void setTestRegUid(String testRegUid) {
		this.testRegUid = testRegUid;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isSelView() {
		return selView;
	}

	public void setSelView(boolean selView) {
		this.selView = selView;
	}
	
	
}
