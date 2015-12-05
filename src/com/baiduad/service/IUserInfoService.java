package com.baiduad.service;

import com.baiduad.model.UserInfo;

public interface IUserInfoService {

	UserInfo getUserInfo(String userName);
	
	boolean changePass(String userName,String oldPass,String newPass);
}
