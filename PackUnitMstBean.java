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
import com.nnbp.application.dao.PackPalleteMstDAO;
import com.nnbp.application.dao.PackUnitMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.PackPalleteMst;
import com.nnbp.application.entities.PackUnitMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class PackUnitMstBean {

	private List<PackUnitMst> listItem = new ArrayList<PackUnitMst>();
	private List<PackUnitMst> listItemFiltered = new ArrayList<PackUnitMst>();
	
	private List<ItemMst> listItemMst = new ArrayList<ItemMst>();
	
	private PackUnitMst selectedItem;
	private ItemMst selectedItemMst;
	
	private boolean selView = false;

	private Connection connection = null;
	
	private String packingKey;
	private String boxType;
	private float height;
	private float length;
	private float tare;
	private float width;
	private int defaultQtyInBox;
	private String item;
	
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
			listItemMst.clear();
			
			connection = DbConnect.getConnection();
			PackUnitMstDAO packUnitMstDAO = new PackUnitMstDAO(connection);

			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			listItemMst = itemDAO.findExistingItems();
			
			if(selView){
			listItem = packUnitMstDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = packUnitMstDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			PackUnitMst paramObj = new PackUnitMst();
			paramObj.setModUid(testModUid);
			paramObj.setPackingKey(selectedItem.getPackingKey());
			paramObj.setBoxType(selectedItem.getBoxType());
			paramObj.setDefaultQtyInBox(selectedItem.getDefaultQtyInBox());
			paramObj.setHeight(selectedItem.getHeight());
			paramObj.setLength(selectedItem.getLength());
			paramObj.setWidth(selectedItem.getWidth());
			paramObj.setTare(selectedItem.getTare());
			paramObj.setItem(selectedItemMst.getName());
			
			PackUnitMstDAO PackUnitMstDAO = new PackUnitMstDAO(connection);
			PackUnitMstDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{

			PackUnitMstDAO packUnitMstDAO = new PackUnitMstDAO(connection);
			
			PackUnitMst paramObj = new PackUnitMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setPackingKey(packUnitMstDAO.setBoxSeq());			
			paramObj.setBoxType(boxType);
			paramObj.setDefaultQtyInBox(defaultQtyInBox);
			paramObj.setHeight(height);
			paramObj.setLength(length);
			paramObj.setWidth(width);
			paramObj.setTare(tare);
			paramObj.setItem(selectedItemMst.getName());
			
			packUnitMstDAO.insertRecord(paramObj);
			searchRecords();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			PackUnitMst paramObj = new PackUnitMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setPackingKey(selectedItem.getPackingKey());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			PackUnitMstDAO PackUnitMstDAO = new PackUnitMstDAO(connection);
			PackUnitMstDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			PackUnitMst paramObj = new PackUnitMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setPackingKey(selectedItem.getPackingKey());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			PackUnitMstDAO PackUnitMstDAO = new PackUnitMstDAO(connection);
			PackUnitMstDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<PackUnitMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<PackUnitMst> listItem) {
		this.listItem = listItem;
	}

	public List<PackUnitMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<PackUnitMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public PackUnitMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(PackUnitMst selectedItem) {
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

	public String getPackingKey() {
		return packingKey;
	}

	public void setPackingKey(String packingKey) {
		this.packingKey = packingKey;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getTare() {
		return tare;
	}

	public void setTare(float tare) {
		this.tare = tare;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public int getDefaultQtyInBox() {
		return defaultQtyInBox;
	}

	public void setDefaultQtyInBox(int defaultQtyInBox) {
		this.defaultQtyInBox = defaultQtyInBox;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<ItemMst> getListItemMst() {
		return listItemMst;
	}

	public void setListItemMst(List<ItemMst> listItemMst) {
		this.listItemMst = listItemMst;
	}

	public ItemMst getSelectedItemMst() {
		return selectedItemMst;
	}

	public void setSelectedItemMst(ItemMst selectedItemMst) {
		this.selectedItemMst = selectedItemMst;
	}
	
}
