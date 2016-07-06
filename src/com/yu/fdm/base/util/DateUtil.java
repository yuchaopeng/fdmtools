package com.yu.fdm.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String FORMAT_SIMPLE = "yyyy-MM-dd";
	
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	
	public static Date getCurrentDate(){
		return new Date();
	}
	
	public static String getCurrentDateSimple(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SIMPLE);
		return sdf.format(getCurrentDate());
	}
	
	public static String getCurrentDateFull(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL);
		return sdf.format(getCurrentDate());
	}
	
	public static String getCurrentDateFullSimple(){
		String currentDate = getCurrentDateSimple();
		currentDate += " 00:00:00";
		return currentDate;
	}
}
