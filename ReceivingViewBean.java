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
public class ReceivingViewBean {

	private List<Receiving> listItem = new ArrayList<Receiving>();
	private List<Receiving> listItemApproval = new ArrayList<Receiving>();
	private List<PackPalleteMst> listPalleteMst = new ArrayList<PackPalleteMst>();
	private List<SupplierMst> listSup = new ArrayList<SupplierMst>();
	private List<Receiving> listItemFiltered = new ArrayList<Receiving>();
	private List<PackUnitMst> listPackingMst = new ArrayList<PackUnitMst>();
	private List<ItemMst> listItemMst = new ArrayList<ItemMst>();
	private List<ReceivingLine> listOfRecLines;
	
	@ManagedProperty("#{selectedItem}")
	
	private Receiving selectedItem;
	public Receiving getSelectedItemToApprove() {
		return selectedItemToApprove;
	}

	public void setSelectedItemToApprove(Receiving selectedItemToApprove) {
		this.selectedItemToApprove = selectedItemToApprove;
	}

	private Receiving selectedItemToApprove = new Receiving();
	private ReceivingPallete selectedPallete;
	private ReceivingPallete selectedViewPallete;
	private ReceivingPallete selectedPalleteToDelete;

	private SupplierMst selectedSup;
	private ReceivingLine selectedLine;
	private ReceivingLine newBoxLine;
	private ReceivingLine receivingLineToDelete;
	private PackUnitMst selectedPackingMst;
	private ItemMst selectedItemMst;
	private PackPalleteMst selectedPalleteMst;
	private boolean selView = false;

	private Connection connection = null;
	private String item;
	private String invNo;
	private String vesselName;
	private String purchaseNo;
	private String purchaseNo2;
	private String nextPalleteSeq;
	private String nextPallete2Seq;
	private int qtyInBox = 0;
	private int noOfBoxes = 0;
	private float grossWeight = 0;
	
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
	
    public void addMessage() {
        String summary = selView  ? "Wszystkie" : "Tylko Istniej¹ce";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	

	public void searchRecords() {
		try {
			

			listItem.clear();
			listItemApproval.clear();
			listItemMst.clear();
			listSup.clear();
			listItemFiltered.clear();
			listPalleteMst.clear();
			
			
			
			connection = DbConnect.getConnection();
			ReceivingDAO receivingDAO = new ReceivingDAO(connection);
			SupplierMstDAO supDAO = new SupplierMstDAO(connection);
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			PackPalleteMstDAO palleteDAO = new PackPalleteMstDAO(connection);

				listItemMst = itemDAO.findExistingItems();
				listSup = supDAO.findExistingItems();
				listPalleteMst = palleteDAO.findExistingItems();
				listItemApproval = receivingDAO.findItemsReadyToApprove();
				
				if(selView){
					listItem = receivingDAO.findAllItems();
					listItemFiltered.addAll(listItem);		
				}else{
					listItem = receivingDAO.findExistingItems();
					listItemFiltered.addAll(listItem);			
				}
				initSelectedItem();
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//tylko zmiana statusu na approved
	
	/*TODO 
	Tworzenie batch no na podstawie dostawy
	*/
	public void approve(){
	try{
		ReceivingDAO receivingDAO = new ReceivingDAO(connection);
		Receiving paramObj = new Receiving();	

		
		paramObj.setModUid(testModUid);
		paramObj.setDeliveryHdrKey(selectedItemToApprove.getDeliveryHdrKey());
		paramObj.setSupCode(selectedItemToApprove.getSupCode());
		if(selectedItemToApprove.getStatus().equals("N")){
		paramObj.setStatus("A");
		receivingDAO.approveRecord(paramObj);
		}
		else{
			message.warn("Uwaga!", "Dostawa " +selectedItemToApprove.getDeliveryHdrKey() +" ze statusem "+ selectedItemToApprove.getStatus() +" nie jest gotowa do zatwiedzenia");
		}
		searchRecords();
	}catch (Exception e) {
		e.printStackTrace();
		}
		
	}
	
	public void updateHeader(){
		try{
		connection = DbConnect.getConnection();
		
		
		ReceivingDAO receivingDAO = new ReceivingDAO(connection);
		Receiving paramObj = new Receiving();
		
		paramObj.setModUid(testModUid);
		paramObj.setDeliveryHdrKey(selectedItem.getDeliveryHdrKey());
		paramObj.setSupCode(selectedItem.getSupCode());
		paramObj.setInvNo(selectedItem.getInvNo());
		paramObj.setInvDate(selectedItem.getInvDate());
		paramObj.setWayBill(selectedItem.getWayBill());
		paramObj.setWayBill2(selectedItem.getWayBill2());
		paramObj.setContainerNo(selectedItem.getContainerNo());
		paramObj.setPurchaseNo(selectedItem.getPurchaseNo());
		paramObj.setPurchaseNo2(selectedItem.getPurchaseNo2());
		paramObj.setDeliveryDate(selectedItem.getDeliveryDate());
		paramObj.setVessel(selectedItem.getVessel());
		paramObj.setShipmentNo(selectedItem.getShipmentNo());
		paramObj.setStatus(selectedItem.getStatus());
		
		
		receivingDAO.updateRecord(paramObj);
		searchRecords();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	
	public void removeHeader(){
		try{
		connection = DbConnect.getConnection();
		
		selectedItem.setDeleteFlag(1);
		Receiving paramObj = new Receiving();
		
		paramObj.setModUid(testModUid);
		paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
		paramObj.setDeliveryHdrKey(selectedItem.getDeliveryHdrKey());
		
		ReceivingDAO receivingDAO = new ReceivingDAO(connection);
		
		receivingDAO.deleteRecord(paramObj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void restoreHeader(){
		try{
			connection = DbConnect.getConnection();
			
			selectedItem.setDeleteFlag(0);
			Receiving paramObj = new Receiving();
			
			paramObj.setModUid(testModUid);
			paramObj.setDeleteFlag(selectedItem.getDeleteFlag());
			paramObj.setDeliveryHdrKey(selectedItem.getDeliveryHdrKey());
			
			ReceivingDAO receivingDAO = new ReceivingDAO(connection);
			
			receivingDAO.restoreRecord(paramObj);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public void insertPallete(){
		try{
		connection = DbConnect.getConnection();
		ReceivingPallete paramObj = new ReceivingPallete();
		ReceivingPalleteDAO palleteDAO = new ReceivingPalleteDAO(connection);
		paramObj.setModUid(testModUid);
		paramObj.setRegUid(testRegUid);
		paramObj.setDeleteFlag(0);
		paramObj.setDeliveryHdrKey(selectedItem.getDeliveryHdrKey());
		paramObj.setPalleteKey(palleteDAO.setPalleteSeq(0));
		paramObj.setType(selectedPalleteMst.getType());
		paramObj.setPalleteNo(nextPalleteSeq);
		paramObj.setMaxWeight(selectedPalleteMst.getMaxWeight());
		if(selectedItem.getSupCode().equals("TNSK")){
		paramObj.setPalleteNo2(nextPallete2Seq);
		}
		
		palleteDAO.insertRecord(paramObj, selectedItem);
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removePallete(){
		try{
		connection = DbConnect.getConnection();
		ReceivingPallete paramObj = new ReceivingPallete();
		ReceivingPalleteDAO palleteDAO = new ReceivingPalleteDAO(connection);
		paramObj.setModUid(testModUid);
		paramObj.setDeleteFlag(1);
		paramObj.setPalleteNo(selectedPallete.getPalleteKey());
		paramObj.setPalleteKey(selectedPallete.getPalleteKey());
		
		palleteDAO.deleteRecord(paramObj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void restorePallete(){
		try{
		connection = DbConnect.getConnection();
		ReceivingPallete paramObj = new ReceivingPallete();
		ReceivingPalleteDAO palleteDAO = new ReceivingPalleteDAO(connection);
		paramObj.setModUid(testModUid);
		paramObj.setDeleteFlag(0);
		paramObj.setPalleteNo(selectedPallete.getPalleteKey());
		paramObj.setPalleteKey(selectedPallete.getPalleteKey());
		palleteDAO.restoreRecord(paramObj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertLine(){
		try{
			connection = DbConnect.getConnection();
			int size = 1;
			ReceivingLineDAO receivingLineDAO = new ReceivingLineDAO(connection);
			ReceivingPalleteDAO receivingPalDAO = new ReceivingPalleteDAO(connection);
			
			ReceivingLine paramObj = new ReceivingLine();
			ReceivingPallete paramPalObj = new ReceivingPallete();
			
			paramObj.setModUid(testModUid);
			paramObj.setDeleteFlag(0);
			paramObj.setPalleteKey(selectedViewPallete.getPalleteKey());
			paramObj.setAllocatedPalleteNo(selectedViewPallete.getPalleteNo());
			paramObj.setDeliveryKey(receivingLineDAO.setSeq(size));
			paramObj.setItem(selectedItemMst.getName());
			paramObj.setGrossWeight(grossWeight);
			paramObj.setQtyInBox(qtyInBox);
			paramObj.setModUid(testModUid);
			
			paramPalObj.setPalleteKey(selectedViewPallete.getPalleteKey());
			paramPalObj.setCurrentWeight(selectedViewPallete.getCurrentWeight()+grossWeight);
			paramPalObj.setModUid(testModUid);
			paramPalObj.setPalleteNo(selectedViewPallete.getPalleteNo());
			paramPalObj.setPalleteNo2(selectedViewPallete.getPalleteNo2());
			paramPalObj.setType(selectedViewPallete.getType());
			paramPalObj.setMaxWeight(selectedViewPallete.getMaxWeight());
			
			receivingLineDAO.insertRecord(paramObj);
			receivingPalDAO.updateRecord(paramPalObj);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public void removeLine(){
		try{
			connection = DbConnect.getConnection();
			
			selectedLine.setDeleteFlag(1);
			ReceivingLine paramObj = new ReceivingLine();
			ReceivingPallete paramPalObj = new ReceivingPallete();
			
			ReceivingPalleteDAO receivingPalDAO = new ReceivingPalleteDAO(connection);
			
			paramObj.setModUid(testModUid);
			paramObj.setDeleteFlag(selectedLine.getDeleteFlag());
			paramObj.setPalleteKey(selectedViewPallete.getPalleteKey());
			paramObj.setDeliveryKey(selectedLine.getDeliveryKey());
			
			paramPalObj.setPalleteKey(selectedViewPallete.getPalleteKey());
			paramPalObj.setCurrentWeight(selectedViewPallete.getCurrentWeight()-selectedLine.getGrossWeight());
			paramPalObj.setModUid(testModUid);
			paramPalObj.setPalleteNo(selectedViewPallete.getPalleteNo());
			paramPalObj.setPalleteNo2(selectedViewPallete.getPalleteNo2());
			paramPalObj.setType(selectedViewPallete.getType());
			paramPalObj.setMaxWeight(selectedViewPallete.getMaxWeight());
			
			
			ReceivingLineDAO receivingLineDAO = new ReceivingLineDAO(connection);
			
			receivingLineDAO.deleteRecord(paramObj);
			receivingPalDAO.updateRecord(paramPalObj);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void restoreLine(){
		try{
			connection = DbConnect.getConnection();
			
			selectedLine.setDeleteFlag(0);
			ReceivingLine paramObj = new ReceivingLine();
			ReceivingPallete paramPalObj = new ReceivingPallete();
			
			paramObj.setModUid(testModUid);
			paramObj.setDeleteFlag(selectedLine.getDeleteFlag());
			paramObj.setDeliveryKey(selectedLine.getDeliveryKey());
			paramObj.setPalleteKey(selectedLine.getPalleteKey());
			
			
			paramPalObj.setPalleteKey(selectedViewPallete.getPalleteKey());
			paramPalObj.setCurrentWeight(selectedViewPallete.getCurrentWeight()-grossWeight);
			paramPalObj.setModUid(testModUid);
			paramPalObj.setPalleteNo(selectedViewPallete.getPalleteNo());
			paramPalObj.setPalleteNo2(selectedViewPallete.getPalleteNo2());
			paramPalObj.setType(selectedViewPallete.getType());
			paramPalObj.setMaxWeight(selectedViewPallete.getMaxWeight());
			
			
			ReceivingLineDAO receivingLineDAO = new ReceivingLineDAO(connection);
			ReceivingPalleteDAO receivingPalDAO = new ReceivingPalleteDAO(connection);
			
			receivingLineDAO.restoreRecord(paramObj);
			receivingPalDAO.updateRecord(paramPalObj);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void initSelectedItem() {
		if (listItem.size() > 0 ) {
			selectedItemMst = listItemMst.get(0);
		}
	}

	public void resetAfterItemChange(){
		grossWeight = 0;
		noOfBoxes = 0;
		qtyInBox = 0;
		message.warn(null,"Zresetowano wartoœci.");
	}
	
	public void findPacking(){
		try {
			connection = DbConnect.getConnection();

			listPackingMst.clear();
			PackUnitMstDAO packDAO = new PackUnitMstDAO(connection);
			if (selectedItemMst != null) {
				listPackingMst = packDAO.findByItem(selectedItemMst.getName());
				grossWeight = 0;
				qtyInBox = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calculateQtyInBox(){
		int calculatedQty = 0;
		if(selectedPackingMst != null){
		calculatedQty = selectedPackingMst.getDefaultQtyInBox();
		qtyInBox = calculatedQty;
	}
	else{
		message.warn("Uwaga!", "Nie mo¿na obliczyæ, brakujaca konfiguracja w Pack Unit Mst dla itemu: " +selectedItemMst.getName());
	}
		
	}
	
	public void calculateWeight(){
		float calculatedWeight = 0;
		if(selectedPackingMst != null){
			calculatedWeight = selectedPackingMst.getTare();
		grossWeight = calculatedWeight;		
		}
		else{
			message.warn("Uwaga!", "Nie mo¿na obliczyæ, brakujaca konfiguracja w Pack Unit Mst dla itemu: " +selectedItemMst.getName());
		}
	}
	
	public List<ReceivingPallete> listBySelectedHdr(Receiving selectedItem) {
		List<ReceivingPallete> listOfRecPallets = new ArrayList<ReceivingPallete>();
		try {
			if(selectedItem != null){
			ReceivingPalleteDAO receivingPalleteDAO = new ReceivingPalleteDAO(connection);
			if(selView){
				listOfRecPallets = receivingPalleteDAO.findAllItems(selectedItem);
			}
			else{
				listOfRecPallets = receivingPalleteDAO.findExistingItems(selectedItem);
				}
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return listOfRecPallets;
	}
	
	public List<ReceivingLine> listBySelectedPal() {
		List<ReceivingLine> listOfRecBoxes = new ArrayList<ReceivingLine>();
		try {
			if(selectedViewPallete != null){
			ReceivingLineDAO receivingLineDAO = new ReceivingLineDAO(connection);
				listOfRecBoxes = receivingLineDAO.findExistingItems(selectedViewPallete);
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return listOfRecBoxes;
	}
	
	public String getNextPalleteSeq(){
		ReceivingPalleteDAO receivingPalleteDAO = new ReceivingPalleteDAO(connection);
		nextPalleteSeq = receivingPalleteDAO.getNextPalleteSeq(selectedItem);
		return nextPalleteSeq;
	}
	
	public String getNextPallete2Seq(){
		ReceivingPalleteDAO receivingPalleteDAO = new ReceivingPalleteDAO(connection);
		if(selectedItem.getSupCode().equals("TNSK")){
		nextPallete2Seq = receivingPalleteDAO.getNextPallete2Seq(selectedItem);
		}
		else {
			nextPallete2Seq = null;
		}
		return nextPallete2Seq;
	}
	

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseNo2() {
		return purchaseNo2;
	}

	public void setPurchaseNo2(String purchaseNo2) {
		this.purchaseNo2 = purchaseNo2;
	}

	public int getQtyInBox() {
		return qtyInBox;
	}

	public void setQtyInBox(int qtyInBox) {
		this.qtyInBox = qtyInBox;
	}

	public int getNoOfBoxes() {
		return noOfBoxes;
	}

	public void setNoOfBoxes(int noOfBoxes) {
		this.noOfBoxes = noOfBoxes;
	}

	public float getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(float grossWeight) {
		this.grossWeight = grossWeight;
	}

	public List<Receiving> getListItem() {
		return listItem;
	}

	public void setListItem(List<Receiving> listItem) {
		this.listItem = listItem;
	}

	public List<ItemMst> getListItemMst() {
		return listItemMst;
	}

	public void setListItemMst(List<ItemMst> listItemMst) {
		this.listItemMst = listItemMst;
	}

	public List<ReceivingLine> getListOfRecLines() {
		return listOfRecLines;
	}

	public void setListOfRecLines(List<ReceivingLine> listOfRecLines) {
		this.listOfRecLines = listOfRecLines;
	}

	public List<Receiving> getListItemFiltered() {
		return listItemFiltered;
	}

	public void setListItemFiltered(List<Receiving> listItemFiltered) {
		this.listItemFiltered = listItemFiltered;
	}

	public Receiving getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Receiving selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getTestRegUid() {
		return testRegUid;
	}

	public void setTestRegUid(String testRegUid) {
		this.testRegUid = testRegUid;
	}

	public ItemMst getSelectedItemMst() {
		return selectedItemMst;
	}

	public void setSelectedItemMst(ItemMst selectedItemMst) {
		this.selectedItemMst = selectedItemMst;
	}

	public ReceivingLine getSelectedLine() {
		return selectedLine;
	}

	public void setSelectedLine(ReceivingLine selectedLine) {
		this.selectedLine = selectedLine;
	}

	public List<SupplierMst> getListSup() {
		return listSup;
	}

	public void setListSup(List<SupplierMst> listSup) {
		this.listSup = listSup;
	}

	public SupplierMst getSelectedSup() {
		return selectedSup;
	}

	public void setSelectedSup(SupplierMst selectedSup) {
		this.selectedSup = selectedSup;
	}

	public List<ReceivingLine> getlistOfRecLines() {
		return listOfRecLines;
	}

	public void setlistOfRecLines(List<ReceivingLine> listOfRecLines) {
		this.listOfRecLines = listOfRecLines;
	}

	public List<PackUnitMst> getListPackingMst() {
		findPacking();
		return listPackingMst;
	}

	public void setListPackingMst(List<PackUnitMst> listPackingMst) {
		this.listPackingMst = listPackingMst;
	}

	public PackUnitMst getSelectedPackingMst() {
		return selectedPackingMst;
	}

	public void setSelectedPackingMst(PackUnitMst selectedPackingMst) {
		this.selectedPackingMst = selectedPackingMst;
	}

	public boolean isSelView() {
		return selView;
	}

	public void setSelView(boolean selView) {
		this.selView = selView;
	}

	public ReceivingLine getReceivingLineToDelete() {
		return receivingLineToDelete;
	}

	public void setReceivingLineToDelete(ReceivingLine receivingLineToDelete) {
		this.receivingLineToDelete = receivingLineToDelete;
	}

	public void setNextPalleteSeq(String nextPalleteSeq) {
		if(nextPalleteSeq.equals(null)){
			getNextPalleteSeq();
		}
		this.nextPalleteSeq = nextPalleteSeq;
	}

	public void setNextPallete2Seq(String nextPallete2Seq) {
		if(nextPallete2Seq.equals(null)){
			getNextPallete2Seq();
		}
		this.nextPallete2Seq = nextPallete2Seq;
	}

	public PackPalleteMst getSelectedPalleteMst() {
		return selectedPalleteMst;
	}

	public void setSelectedPalleteMst(PackPalleteMst selectedPalleteMst) {
		this.selectedPalleteMst = selectedPalleteMst;
	}

	public List<PackPalleteMst> getListPalleteMst() {
		return listPalleteMst;
	}

	public void setListPalleteMst(List<PackPalleteMst> listPalleteMst) {
		this.listPalleteMst = listPalleteMst;
	}

	public ReceivingPallete getSelectedPallete() {
		return selectedPallete;
	}

	public void setSelectedPallete(ReceivingPallete selectedPallete) {
		this.selectedPallete = selectedPallete;
	}

	public ReceivingPallete getSelectedViewPallete() {
		return selectedViewPallete;
	}

	public void setSelectedViewPallete(ReceivingPallete selectedViewPallete) {
		this.selectedViewPallete = selectedViewPallete;
	}

	public ReceivingLine getNewBoxLine() {
		return newBoxLine;
	}

	public void setNewBoxLine(ReceivingLine newBoxLine) {
		this.newBoxLine = newBoxLine;
	}

	public List<Receiving> getListItemApproval() {
		return listItemApproval;
	}

	public void setListItemApproval(List<Receiving> listItemApproval) {
		this.listItemApproval = listItemApproval;
	}
	
	
}
