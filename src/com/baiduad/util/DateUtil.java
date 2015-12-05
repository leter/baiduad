package com.baiduad.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String Date2String(Date date){
		if(date!=null)
		 return new SimpleDateFormat("yyyy-MM-dd").format(date);
		return null;
	}
	
	/**
	 * 比较两个日期之间的大小
	 * 
	 * @param d1
	 * @param d2
	 * @return 前者大于后者返回true 反之false
	 */
	public static boolean compareDate(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int result = c1.compareTo(c2);
		if (result >= 0)
			return true;
		else
			return false;
	}

}
