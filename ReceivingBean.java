package com.nnbp.application.managedbeans;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.nnbp.application.dao.ItemMstDAO;
import com.nnbp.application.dao.PackPalleteMstDAO;
import com.nnbp.application.dao.PackUnitMstDAO;
import com.nnbp.application.dao.ReceivingDAO;
import com.nnbp.application.dao.ReceivingLineDAO;
import com.nnbp.application.dao.ReceivingPalleteDAO;
import com.nnbp.application.dao.SupplierMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.ItemMst;
import com.nnbp.application.entities.PackPalleteMst;
import com.nnbp.application.entities.PackUnitMst;
import com.nnbp.application.entities.Receiving;
import com.nnbp.application.entities.ReceivingLine;
import com.nnbp.application.entities.ReceivingPallete;
import com.nnbp.application.entities.SupplierMst;
import com.nnbp.application.util.MessageUtil;



@ManagedBean
@ViewScoped
public class ReceivingBean {

	private List<ItemMst> listItem = new ArrayList<ItemMst>();
	private List<SupplierMst> listSup = new ArrayList<SupplierMst>();
	private List<ItemMst> listItemFiltered = new ArrayList<ItemMst>();
	
	//PackUnitMst
	private List<PackUnitMst> listPackingMst = new ArrayList<PackUnitMst>();
	private List<PackPalleteMst> listPalleteMst = new ArrayList<PackPalleteMst>();

	private List<ReceivingLine> listOfLines = new ArrayList<ReceivingLine>();
	
	private List<ReceivingPallete> listOfPallets = new ArrayList<ReceivingPallete>();
	
	private Receiving selectedItem = new Receiving();
	private SupplierMst selectedSup = new SupplierMst();
	
	private ReceivingLine selectedLine = new ReceivingLine();
	private ReceivingLine receivingLineToDelete;
	
	private PackPalleteMst selectedPalleteMst;
	
	private ReceivingPallete selectedPallete = new ReceivingPallete();

	private ReceivingPallete palleteToDelete;
	
	private PackUnitMst selectedPackingMst;
	private ItemMst selectedItemMst;
	private boolean selView = false;

	private Connection connection = null;
	
	private String item;
	private String invNo;
	private String vesselName;
	private String purchaseNo;
	private String purchaseNo2;
	private int qtyInBox = 0;
	private int noOfBoxes = 0;
	private float grossWeight = 0;
	MessageUtil message = new MessageUtil();
	
	String testModUid = "TESTMODUID";
	String testRegUid = "TESTREGUID";
	private boolean skip;
	
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
			listSup.clear();
			listItemFiltered.clear();
			listPalleteMst.clear();
			
			
			
			connection = DbConnect.getConnection();
			ItemMstDAO itemDAO = new ItemMstDAO(connection);
			SupplierMstDAO supDAO = new SupplierMstDAO(connection);
			PackPalleteMstDAO palleteDAO = new PackPalleteMstDAO(connection);
			
			
				listSup = supDAO.findExistingItems();
				listItem = itemDAO.findExistingItems();
				listItemFiltered.addAll(listItem);
				listPalleteMst = palleteDAO.findExistingItems();
				
				initSelectedItem();
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void initSelectedItem() {
		if (listItem.size() > 0 ) {
			selectedItemMst = listItem.get(0);
		}
	}

	public void resetAfterItemChange(){
		grossWeight = 0;
		noOfBoxes = 0;
		qtyInBox = 0;
	}
	
	public void findPacking(){
		try {
			//connection = DbConnect.getConnection();

			listPackingMst.clear();
			resetAfterItemChange();
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
	
	public void getPallets(){
		try {
			//connection = DbConnect.getConnection();

			listPalleteMst.clear();
			PackPalleteMstDAO palleteDAO = new PackPalleteMstDAO(connection);
				listPalleteMst = palleteDAO.findAllItems();

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
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Uwaga!", "Nie mo¿na obliczyæ, brakujaca konfiguracja w Pack Unit Mst dla itemu: " +selectedItemMst.getName()));
	}
		
	}
	
	public void calculateWeight(){
		float calculatedWeight = 0;
		if(selectedPackingMst != null){
			//calculatedWeight = noOfBoxes * selectedPackingMst.getTare();
			calculatedWeight = selectedPackingMst.getTare();
		grossWeight = calculatedWeight;		
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Uwaga!", "Nie mo¿na obliczyæ, brakujaca konfiguracja w Pack Unit Mst dla itemu: " +selectedItemMst.getName()));
		}
	}
	
	public void createHeader(){
		
		connection = DbConnect.getConnection();
		
		ReceivingDAO recDAO = new ReceivingDAO(connection);
		
		selectedItem.setDeliveryHdrKey(recDAO.setSeq());
		selectedItem.setRegUid(testRegUid);
		selectedItem.setModUid(testModUid);
		selectedItem.setSupCode(selectedSup.getCode());
		selectedItem.setStatus("N");
		
		if(selectedItem.getDeliveryHdrKey() != null){
			recDAO.insertRecord(selectedItem);
		}
	}
	
	public void createPallete(){
		
		connection = DbConnect.getConnection();
		ReceivingPalleteDAO recPalDAO = new ReceivingPalleteDAO(connection);
		ReceivingPallete newPallete = new ReceivingPallete();
		
		
		if(!selectedItem.equals(null)){
			selectedItem.setSupCode(selectedSup.getCode());
			newPallete.setPalleteKey(recPalDAO.setPalleteSeq(listOfPallets.size()));
			newPallete.setRegUid(testRegUid);
			newPallete.setModUid(testModUid);
			newPallete.setMaxWeight(selectedPalleteMst.getMaxWeight());
			newPallete.setType(selectedPalleteMst.getType());
			newPallete.setPalleteNo(recPalDAO.setPalleteNoSeq(newPallete, selectedItem,listOfPallets.size()));
			if(selectedItem.getSupCode().equals("TNSK")){
				newPallete.setPalleteNo2(recPalDAO.setPalleteNo2Seq(selectedPallete, selectedItem,listOfPallets.size()));
			}
		listOfPallets.add(newPallete);
			
	
		}
	}
	
	
	public void addLine(){
		ReceivingLineDAO recLineDAO = new ReceivingLineDAO(connection);
		
		if(qtyInBox > 0 && grossWeight > 0 && noOfBoxes > 0)
		{
			for(int i=0;i<noOfBoxes;i++)
			{
		ReceivingLine newLine = new ReceivingLine();
		newLine.setDeleteFlag(0);
		newLine.setRegUid(testRegUid);
		newLine.setModUid(testModUid);
		newLine.setItem(selectedItemMst.getName());
		newLine.setGrossWeight(grossWeight);
		//temp ustawione na 1 potem to pole bedzie numerem pudelka do druku
		newLine.setNoOfBoxes(1);
		newLine.setQtyInBox(qtyInBox);

		newLine.setAllocatedPalleteNo(selectedPallete.getPalleteNo());
		newLine.setPalleteKey(selectedPallete.getPalleteKey());
		
		if(selectedPallete.getCurrentWeight() < selectedPallete.getMaxWeight() && selectedPallete.getCurrentWeight()+newLine.getGrossWeight() <= selectedPallete.getMaxWeight()){
			selectedPallete.setCurrentWeight(selectedPallete.getCurrentWeight()+newLine.getGrossWeight());
			listOfLines.add(newLine);
			newLine.setDeliveryKey(recLineDAO.setSeq(listOfLines.size()));
			}
			else{
				message.warn("Uwaga!","nie mo¿na dodaæ pude³ka , aktualna waga przekroczy maksymaln¹ wagê");
			}
		
			}
		}
			else
		{
		message.error("B³¹d!", "waga, iloœæ pude³ek i iloœæ w pude³ku nie mog¹ byæ mniejsze lub równe 0");
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³¹d!", "waga, iloœæ pude³ek i iloœæ w pude³ku nie mog¹ byæ mniejsze lub równe 0"));
		}
	}
	
	public void removeLine(){
		if (receivingLineToDelete != null) {
			listOfLines.remove(receivingLineToDelete);
			selectedPallete.setCurrentWeight(selectedPallete.getCurrentWeight()-receivingLineToDelete.getGrossWeight());
		}
	}
	
	public void removePallete(){
		if (palleteToDelete != null) {
			listOfPallets.remove(palleteToDelete);
		}
	}
	
	
	
	// do zmiany na numer partii z batch
	// insert do bazy 
	public void save(ActionEvent actionEvent) {
	
		createHeader();
		
		ReceivingLineDAO recLineDAO = new ReceivingLineDAO(connection);
		ReceivingPalleteDAO recPalDAO = new ReceivingPalleteDAO(connection);
		
		
		Iterator<ReceivingLine> ib =listOfLines.iterator();
		Iterator<ReceivingPallete> ip = listOfPallets.iterator();
		
		// loop for pallete list
		while(ip.hasNext()){
			recPalDAO.insertRecord(ip.next() ,selectedItem);	
		}
			//loop for box list
				while(ib.hasNext()){
					recLineDAO.insertRecord(ib.next());
				}		
		
		if(recLineDAO.state == true){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces!","Stworzono dostawe:" +selectedItem.getDeliveryHdrKey()));
		resetAfterSubmit();
		}		

	}

	public void resetAfterSubmit(){
		  
		  listOfLines.clear();
		  listOfPallets.clear();
		  listItem.clear();
		  selectedSup = null;
		  selectedLine = null;
		  receivingLineToDelete = null;
		  selectedPackingMst = null;
		  selectedItemMst = null;
		  item ="";
		  invNo ="";
		  vesselName="";
		  purchaseNo ="";
		  purchaseNo2 ="";
		  qtyInBox = 0;
		  noOfBoxes = 0;
		  grossWeight = 0;
		  searchRecords();
		  
	}

	public String onFlowProcess(FlowEvent event) {
			return event.getNewStep();
	}
	


	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
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

	public List<ReceivingLine> getListOfLines() {
		return listOfLines;
	}

	public void setListOfLines(List<ReceivingLine> listOfLines) {
		this.listOfLines = listOfLines;
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
	
	public List<PackPalleteMst> getListPalleteMst() {
		return listPalleteMst;
	}

	public void setListPalleteMst(List<PackPalleteMst> listPalleteMst) {
		this.listPalleteMst = listPalleteMst;
	}

	public List<ReceivingPallete> getListOfPallets() {
		return listOfPallets;
	}

	public void setListOfPallets(List<ReceivingPallete> listOfPallets) {
		this.listOfPallets = listOfPallets;
	}

	public ReceivingPallete getSelectedPallete() {
		return selectedPallete;
	}

	public void setSelectedPallete(ReceivingPallete selectedPallete) {
		this.selectedPallete = selectedPallete;
	}

	public ReceivingPallete getPalleteToDelete() {
		return palleteToDelete;
	}

	public void setPalleteToDelete(ReceivingPallete palleteToDelete) {
		this.palleteToDelete = palleteToDelete;
	}

	public PackPalleteMst getSelectedPalleteMst() {
		return selectedPalleteMst;
	}

	public void setSelectedPalleteMst(PackPalleteMst selectedPalleteMst) {
		this.selectedPalleteMst = selectedPalleteMst;
	}
	
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Zaznaczono Paletê", ((ReceivingPallete) event.getObject()).getPalleteNo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Odznaczono Paletê", ((ReceivingPallete) event.getObject()).getPalleteNo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
