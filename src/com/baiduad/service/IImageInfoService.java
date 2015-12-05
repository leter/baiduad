package com.baiduad.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baiduad.model.CompanyInfo;
import com.baiduad.model.ImageInfo;
import com.baiduad.model.Page;

public interface IImageInfoService {
	
	boolean add(ImageInfo imageInfo);
	
	boolean update(ImageInfo imageInfo);
	
	boolean del(int pkId,HttpServletRequest servletRequest);
	
	List<ImageInfo> queryAll();
	
	List<ImageInfo> queryByComapny(String refId);
	
	ImageInfo getObjectById(int pkId);
	
	Page<ImageInfo> queryPage(String title,Integer pages);

}
