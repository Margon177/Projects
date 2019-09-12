package com.nnbp.application.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean

public class SelectView {

	public boolean searchDeleted = true;  
 
    public boolean isSearchDeleted() {
        return searchDeleted;
    }
 
	public void setSearchDeleted(boolean searchDeleted) {
        this.searchDeleted = searchDeleted;
    }
 

     
    public void addMessage() {
        String summary = searchDeleted  ? "Search All" : "Existing Only";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
	
}
