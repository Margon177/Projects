package com.nnbp.application.managedbeans.mst;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.nnbp.application.dao.AssemblyBOMCompMstDAO;
import com.nnbp.application.dao.AssemblyBOMMstDAO;
import com.nnbp.application.dao.ItemMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.AssemblyBOMCompMst;
import com.nnbp.application.entities.AssemblyBOMMst;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class AssemblyBOMMstBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AssemblyBOMMst> listItem = new ArrayList<AssemblyBOMMst>();
	private List<AssemblyBOMMst> listItemFiltered = new ArrayList<AssemblyBOMMst>();
	private List<ItemMst> itemList = new ArrayList<ItemMst>();

	
	private List<AssemblyBOMCompMst> listItemComp = new ArrayList<AssemblyBOMCompMst>();
	private List<AssemblyBOMCompMst> listItemCompFiltered = new ArrayList<AssemblyBOMCompMst>();
	
	@ManagedProperty("#{selectedItem}")
	
	private AssemblyBOMMst selectedItem;
	private AssemblyBOMCompMst selectedCompItem;
	private ItemMst selectedItemMstForUpdate; 
	private boolean selView = false;

	private Connection connection = null;
	
	private String AssemblyBOMMstKey;	
	private String AssemblyBOMCompMstKey;
	
	private String item;
	private String name;
	private ItemMst selectedItemMst;
	private ItemMst selectedItemCompMst;
	private int qty;
	
	private String recordSeq;
	private String recordCompSeq;
	
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
			itemList.clear();


			connection = DbConnect.getConnection();
			AssemblyBOMMstDAO AssemblyBOMMst = new AssemblyBOMMstDAO(connection);
			
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			
			
			itemList = itemDAO.findExistingItems();
			
			if(selView){
			listItem = AssemblyBOMMst.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = AssemblyBOMMst.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<AssemblyBOMCompMst> listBySelectComp(AssemblyBOMMst selectedRecord) {
		List<AssemblyBOMCompMst> listItemComp = new ArrayList<AssemblyBOMCompMst>();
		try {

			AssemblyBOMCompMstDAO AssemblyBOMCompMst = new AssemblyBOMCompMstDAO(connection);
			listItemComp = AssemblyBOMCompMst.findExistingItems(selectedRecord);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listItemComp;
	}
	
	
	// BOM ITEMS  CRUD-------------------------------------------------------------------------------
	
	
	
	public void updateRecord(){
		try{
			

			
			AssemblyBOMMst paramObj = new AssemblyBOMMst();
			paramObj.setModUid(testModUid);
			paramObj.setAssemblyBOMMstKey(selectedItem.getAssemblyBOMMstKey());
			paramObj.setItem(selectedItemMstForUpdate.getName());
			
			AssemblyBOMMstDAO itemDAO = new AssemblyBOMMstDAO(connection);
			itemDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{
			
			AssemblyBOMMstDAO itemDAO = new AssemblyBOMMstDAO(connection);
			recordSeq = itemDAO.setSeq();
			
			if(recordSeq == null || recordSeq.length() <= 0){
				
				return;
			}
			
			AssemblyBOMMst paramObj = new AssemblyBOMMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setAssemblyBOMMstKey(recordSeq);
			paramObj.setItem(selectedItemMst.getName());
			
	
			itemDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			AssemblyBOMMst paramObj = new AssemblyBOMMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setAssemblyBOMMstKey(selectedItem.getAssemblyBOMMstKey());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			AssemblyBOMMstDAO itemDAO = new AssemblyBOMMstDAO(connection);
			itemDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			AssemblyBOMMst paramObj = new AssemblyBOMMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setAssemblyBOMMstKey(selectedItem.getAssemblyBOMMstKey());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			AssemblyBOMMstDAO itemDAO = new AssemblyBOMMstDAO(connection);
			itemDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	
	// ASSEMBLY BOM COMPONENTS  CRUD-------------------------------------------------------------------------------
	
	
	public void updateCompRecord(){
		try{
			AssemblyBOMCompMst paramObj = new AssemblyBOMCompMst();
			paramObj.setModUid(testModUid);
			paramObj.setAssemblyBOMCompMstKey(selectedItem.getAssemblyBOMMstKey());
			paramObj.setItem(selectedCompItem.getItem());
			
			AssemblyBOMCompMstDAO itemDAO = new AssemblyBOMCompMstDAO(connection);
			itemDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertCompRecord(){
		try{
			
			AssemblyBOMCompMstDAO itemDAO = new AssemblyBOMCompMstDAO(connection);
			recordSeq = itemDAO.setSeq();
			
			if(recordSeq == null || recordSeq.length() <= 0){
				
				return;
			}
			
			AssemblyBOMCompMst paramObj = new AssemblyBOMCompMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setAssemblyBOMCompMstKey(recordSeq);
			paramObj.setItem(selectedItemCompMst.getName());
			paramObj.setQty(qty);
			paramObj.setAssemblyBOMmstKey(selectedItem.getAssemblyBOMMstKey());
			

			itemDAO.insertRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteCompRecord(){
		try{
			selectedCompItem.setDeleteFlag(1);
			AssemblyBOMCompMst paramObj = new AssemblyBOMCompMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setAssemblyBOMCompMstKey(selectedCompItem.getAssemblyBOMCompMstKey());
			paramObj.setDeleteFlag(selectedCompItem.getDeleteFlag());
			
			AssemblyBOMCompMstDAO itemDAO = new AssemblyBOMCompMstDAO(connection);
			itemDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreCompRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			AssemblyBOMCompMst paramObj = new AssemblyBOMCompMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setAssemblyBOMCompMstKey(selectedCompItem.getAssemblyBOMCompMstKey());
			paramObj.setDeleteFlag(selectedItemCompMst.getDeleteFlag());
			
			AssemblyBOMCompMstDAO itemDAO = new AssemblyBOMCompMstDAO(connection);
			itemDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
    public void onChangeSelectMenu() {
    	System.out.println("TEST");
    }
    
	
	public List<AssemblyBOMMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<AssemblyBOMMst> listItem) {
		this.listItem = listItem;
	}

	public List<AssemblyBOMMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<AssemblyBOMMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public AssemblyBOMMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(AssemblyBOMMst selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getAssemblyBOMMstKey() {
		return AssemblyBOMMstKey;
	}

	public void setAssemblyBOMMstKey(String AssemblyBOMMstKey) {
		this.AssemblyBOMMstKey = AssemblyBOMMstKey;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public List<ItemMst> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemMst> itemList) {
		this.itemList = itemList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemMst getSelectedItemMst() {
		return selectedItemMst;
	}

	public void setSelectedItemMst(ItemMst selectedItemMst) {
		this.selectedItemMst = selectedItemMst;
	}

	public String getRecordSeq() {
		return recordSeq;
	}

	public void setRecordSeq(String recordSeq) {
		this.recordSeq = recordSeq;
	}

	public List<AssemblyBOMCompMst> getListItemComp() {
		return listItemComp;
	}

	public void setListItemComp(List<AssemblyBOMCompMst> listItemComp) {
		this.listItemComp = listItemComp;
	}

	public List<AssemblyBOMCompMst> getListItemCompFiltered() {
		return listItemCompFiltered;
	}

	public void setListItemCompFiltered(List<AssemblyBOMCompMst> listItemCompFiltered) {
		this.listItemCompFiltered = listItemCompFiltered;
	}

	public ItemMst getSelectedItemCompMst() {
		return selectedItemCompMst;
	}

	public void setSelectedItemCompMst(ItemMst selectedItemCompMst) {
		this.selectedItemCompMst = selectedItemCompMst;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getRecordCompSeq() {
		return recordCompSeq;
	}

	public void setRecordCompSeq(String recordCompSeq) {
		this.recordCompSeq = recordCompSeq;
	}

	public String getAssemblyBOMCompMstKey() {
		return AssemblyBOMCompMstKey;
	}

	public void setAssemblyBOMCompMstKey(String assemblyBOMCompMstKey) {
		AssemblyBOMCompMstKey = assemblyBOMCompMstKey;
	}

	public AssemblyBOMCompMst getSelectedCompItem() {
		return selectedCompItem;
	}

	public void setSelectedCompItem(AssemblyBOMCompMst selectedCompItem) {
		this.selectedCompItem = selectedCompItem;
	}

	public ItemMst getSelectedItemMstForUpdate() {
		return selectedItemMstForUpdate;
	}

	public void setSelectedItemMstForUpdate(ItemMst selectedItemMstForUpdate) {
		this.selectedItemMstForUpdate = selectedItemMstForUpdate;
	}

}
