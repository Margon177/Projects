package com.nnbp.application.util;

import java.sql.Connection;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;



import com.nnbp.application.dao.ItemMstDAO;
import com.nnbp.application.database.DbConnect;
import com.nnbp.application.entities.ItemMst;


@FacesConverter("com.nnbp.application.util.ItemMstConverter")
public class ItemMstConverter implements Converter{
	
 	Connection connection = null;
	

	
	@PreDestroy
	public void closeConnection(){
		DbConnect.close(connection);
	}

	@Override
	 public Object getAsObject(FacesContext context, UIComponent component, String value) {


		
		connection = DbConnect.getConnection();
		
	     ItemMstDAO query = new ItemMstDAO(connection);

	            if (value == null || value.isEmpty()) {
	                return null;
	            }
	 
	            try {
	                  
	                ItemMst obj =  query.findOneById(value);
	                
	                return obj;
	            } catch (Exception e) {
	                e.printStackTrace();
	                throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to ItemMst", value)), e);
	            }
	}
	
	
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if(object != null){
			return String.valueOf(((ItemMst)object).getName());
		}
		else{
			return null;
		}
	}

}
