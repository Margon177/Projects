package com.nnbp.application.managedbeans;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;

import com.nnbp.application.dao.AssemblyBOMCompMstDAO;
import com.nnbp.application.dao.ItemMstDAO;
import com.nnbp.application.dao.PackPalleteMstDAO;
import com.nnbp.application.dao.PackUnitMstDAO;
import com.nnbp.application.dao.ReceivingDAO;
import com.nnbp.application.dao.ReceivingLineDAO;
import com.nnbp.application.dao.ReceivingPalleteDAO;
import com.nnbp.application.dao.SupplierMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.AssemblyBOMCompMst;
import com.nnbp.application.entities.AssemblyBOMMst;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.PackPalleteMst;
import com.nnbp.application.entities.PackUnitMst;
import com.nnbp.application.entities.Receiving;
import com.nnbp.application.entities.ReceivingLine;
import com.nnbp.application.entities.ReceivingPallete;
import com.nnbp.application.entities.SupplierMst;
import com.nnbp.application.util.MessageUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;



@ManagedBean
@ViewScoped
public class WarehouseBean {

	private List<Receiving> listOfApprovedShipments = new ArrayList<Receiving>();
	private Receiving selectedDelivery = new Receiving();
	
	private Connection connection = null;

	
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
			

			listOfApprovedShipments.clear();
			
			
			connection = DbConnect.getConnection();
			ReceivingDAO receivingDAO = new ReceivingDAO(connection);

				listOfApprovedShipments = receivingDAO.findApprovedItems();			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ReceivingPallete> listBySelectedHdr(Receiving selectedItem) {
		List<ReceivingPallete> listOfRecPallets = new ArrayList<ReceivingPallete>();
		try {
			if(selectedItem != null){
			ReceivingPalleteDAO receivingPalleteDAO = new ReceivingPalleteDAO(connection);
				listOfRecPallets = receivingPalleteDAO.findExistingItems(selectedItem);
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return listOfRecPallets;
	}
	
	
	public void deny(){
		try{
		ReceivingDAO receivingDAO = new ReceivingDAO(connection);
		Receiving paramObj = new Receiving();	

		paramObj.setModUid(testModUid);
		paramObj.setDeliveryHdrKey(selectedDelivery.getDeliveryHdrKey());
		paramObj.setSupCode(selectedDelivery.getSupCode());
		
		if(selectedDelivery.getStatus().equals("A"))
		{
			paramObj.setStatus("E");
			receivingDAO.denyRecord(paramObj);
			message.warn("Uwaga ","Odrzucono dostawê: " +selectedDelivery.getDeliveryHdrKey());
			searchRecords();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void acknowledge(){

		message.info("Przyjêto dostawê: ", selectedDelivery.getDeliveryHdrKey());
		
	}

	public Receiving getSelectedDelivery() {
		return selectedDelivery;
	}

	public void setSelectedDelivery(Receiving selectedDelivery) {
		this.selectedDelivery = selectedDelivery;
	}

	public List<Receiving> getListOfApprovedShipments() {
		return listOfApprovedShipments;
	}

	public void setListOfApprovedShipments(List<Receiving> listOfApprovedShipments) {
		this.listOfApprovedShipments = listOfApprovedShipments;
	}
	
}
