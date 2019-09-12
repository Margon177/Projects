package com.nnbp.application.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DateUtil {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public String getCurrentDate()
	{

		Calendar currentDate = Calendar.getInstance();
		String formatedDate = dateFormat.format(currentDate);
		
	return formatedDate;	
		
	}
	
	public Timestamp getCurrentDateObj()
	{	Calendar date = Calendar.getInstance();
		Timestamp currentDate = new Timestamp(date.getTimeInMillis());
	return currentDate;
	}
	
	public String convertToString(Date date){
		String convertedDate = new String();
		convertedDate = dateFormat.format(date);
		return convertedDate;
	}
	
	public Timestamp convertToTimestamp(Date date){
		if(date != null){
		Timestamp convertedTimestamp = new Timestamp(date.getTime());
		return convertedTimestamp;
		}
		else {
			return null;
		}
	}
}
