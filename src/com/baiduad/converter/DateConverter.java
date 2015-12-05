package com.baiduad.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class DateConverter implements WebBindingInitializer {

	private String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	
	
	public void initBinder(WebDataBinder binder, WebRequest request) {
		
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
		
	}

}
