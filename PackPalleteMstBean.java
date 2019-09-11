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
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.PackPalleteMst;
import com.nnbp.application.util.MessageUtil;



/**
 * @author N041832
 *
 */
@ManagedBean
@ViewScoped
public class PackPalleteMstBean {

	private List<PackPalleteMst> listItem = new ArrayList<PackPalleteMst>();
	private List<PackPalleteMst> listItemFiltered = new ArrayList<PackPalleteMst>();
	
	private List<ItemMst> listItemMst = new ArrayList<ItemMst>();
	
	private PackPalleteMst selectedItem;
	
	private boolean selView = false;

	private Connection connection = null;
	
	private String palleteKey;
	private String type;
	private float height;
	private float length;
	private float width;
	private float maxWeight;
	private float tare;
	
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
			PackPalleteMstDAO PackPalleteMstDAO = new PackPalleteMstDAO(connection);

			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			listItemMst = itemDAO.findExistingItems();
			
			if(selView){
			listItem = PackPalleteMstDAO.findAllItems();
			listItemFiltered.addAll(listItem);
			}
			else
			{
				listItem = PackPalleteMstDAO.findExistingItems();
				listItemFiltered.addAll(listItem);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateRecord(){
		try{
			PackPalleteMst paramObj = new PackPalleteMst();
			paramObj.setModUid(testModUid);
			paramObj.setPalleteKey(selectedItem.getPalleteKey());
			paramObj.setHeight(selectedItem.getHeight());
			paramObj.setLength(selectedItem.getLength());
			paramObj.setTare(selectedItem.getTare());
			paramObj.setWidth(selectedItem.getWidth());
			paramObj.setMaxWeight(selectedItem.getMaxWeight());
			
			PackPalleteMstDAO packPalleteMstDAO = new PackPalleteMstDAO(connection);
			packPalleteMstDAO.updateRecord(paramObj);
			searchRecords();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insertRecord(){
		try{

			PackPalleteMstDAO packPalleteMstDAO = new PackPalleteMstDAO(connection);
			
			PackPalleteMst paramObj = new PackPalleteMst();
			paramObj.setModUid(testModUid);
			paramObj.setRegUid(testRegUid);
			paramObj.setPalleteKey(packPalleteMstDAO.setPalleteSeq());
			paramObj.setType(type);
			paramObj.setHeight(height);
			paramObj.setLength(length);
			paramObj.setWidth(width);
			paramObj.setMaxWeight(maxWeight);
			paramObj.setTare(tare);

			
			packPalleteMstDAO.insertRecord(paramObj);
			searchRecords();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRecord(){
		try{
			selectedItem.setDeleteFlag(1);
			PackPalleteMst paramObj = new PackPalleteMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setPalleteKey(selectedItem.getPalleteKey());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			PackPalleteMstDAO packPalleteMstDAO = new PackPalleteMstDAO(connection);
			packPalleteMstDAO.deleteRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoreRecord(){
		try{
			selectedItem.setDeleteFlag(0);
			PackPalleteMst paramObj = new PackPalleteMst();
			paramObj.setRegUid(testRegUid);
			paramObj.setModUid(testModUid);
			paramObj.setPalleteKey(selectedItem.getPalleteKey());
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			
			PackPalleteMstDAO packPalleteMstDAO = new PackPalleteMstDAO(connection);
			packPalleteMstDAO.restoreRecord(paramObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
	
	public List<PackPalleteMst> getListItem() {
		return listItem;
	}

	public void setListItem(List<PackPalleteMst> listItem) {
		this.listItem = listItem;
	}

	public List<PackPalleteMst> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<PackPalleteMst> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public PackPalleteMst getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(PackPalleteMst selectedItem) {
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

	public String getPalleteKey() {
		return palleteKey;
	}

	public void setPalleteKey(String palleteKey) {
		this.palleteKey = palleteKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}

	public List<ItemMst> getListItemMst() {
		return listItemMst;
	}

	public void setListItemMst(List<ItemMst> listItemMst) {
		this.listItemMst = listItemMst;
	}
	
	
	
	
}
