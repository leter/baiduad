package com.baiduad.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.baiduad.model.UserInfo;
import com.baiduad.service.IUserInfoService;
import com.baiduad.util.EncryptionUtil;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements IUserInfoService {

	@Override
	public UserInfo getUserInfo(String userName) {
		if(StringUtils.isNotBlank(userName)){
			return  super.getObject("select * from user_info where user_name = ? limit 1", userName);
		}
		return null;
	}
	

	@Override
	public RowMapper getRowMapper(){
		return new RowMapper() {
				public Object mapRow(ResultSet rs, int row)
						throws SQLException {
					UserInfo userInfo = new UserInfo();
					userInfo.setUserName(rs.getString("user_name"));
					userInfo.setUserPass(rs.getString("user_pass"));
					return userInfo;
					}
				};
	}

	@Override
	public boolean changePass(String userName, String oldPass, String newPass) {
		UserInfo info = getUserInfo(userName);
		if(info != null){
			String realPass =EncryptionUtil.MD5Encode(EncryptionUtil.passWordDecode(info.getUserPass(),info.getUserName()));
			if(realPass.equals(oldPass)){
				return super.update("update user_info set user_pass = ? where user_name = ? ", EncryptionUtil.passWordEncode(newPass, info.getUserName()),info.getUserName());
			}
		}
		return false;
	}

}
