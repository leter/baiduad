package com.baiduad.service;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baiduad.model.CompanyInfo;
import com.baiduad.model.Page;

public interface ICompanyInfoService {

	boolean add(CompanyInfo companyInfo);
	
	boolean update(CompanyInfo companyInfo);
	
	boolean del(String pkId,HttpServletRequest servletRequest);
	
	List<CompanyInfo> queryAll();
	
	CompanyInfo getObjectById(String pkId);
	
	Page<CompanyInfo> queryPage(CompanyInfo companyInfo,Integer pages);
	
	String queryDefaultUuId();
	
	String updateDefaultUuId(String uuid);
}
