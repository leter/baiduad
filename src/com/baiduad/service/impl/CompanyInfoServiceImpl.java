package com.baiduad.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.baiduad.model.CompanyInfo;
import com.baiduad.model.ImageInfo;
import com.baiduad.model.Page;
import com.baiduad.service.ICompanyInfoService;
import com.baiduad.service.IImageInfoService;
import com.baiduad.util.DateUtil;
import com.baiduad.util.Logger;
import com.baiduad.util.UUIDUtil;

@Service
public class CompanyInfoServiceImpl extends BaseServiceImpl<CompanyInfo> implements ICompanyInfoService {
	
	private static final Logger log = new Logger(CompanyInfoServiceImpl.class);
	@Autowired
	private IImageInfoService imageInfoService;
	

	@Override
	public boolean add(CompanyInfo companyInfo) {
		if(StringUtils.isEmpty(companyInfo.getCompanyName()) || StringUtils.isEmpty(companyInfo.getKeyWord())){
			log.error("添加公司信息失败！"+companyInfo.toString() ,null);
			return false;
		}
		companyInfo.setPkId(UUIDUtil.getUUID());
		companyInfo.setCreateDate(new Date());
		StringBuffer str = new StringBuffer();
		str.append("INSERT INTO baiduad.company_info                                ");
		str.append("(pk_id,company_name,key_word,create_date,remark,url,start_date,end_date,sales_person)            ");
		str.append("VALUES                                                          ");
		str.append("(?,?,?,?,?,?,?,?,?)");
		return super.add(str.toString(),
				companyInfo.getPkId(),
				companyInfo.getCompanyName(),
				companyInfo.getKeyWord(),
				companyInfo.getCreateDate(),
				companyInfo.getRemark(),
				companyInfo.getUrl(),
				companyInfo.getStartDate(),
				companyInfo.getEndDate(),
				companyInfo.getSalesPerson());
	}

	@Override
	public boolean update(CompanyInfo companyInfo) {
		if(StringUtils.isEmpty(companyInfo.getCompanyName()) 
				|| StringUtils.isEmpty(companyInfo.getKeyWord())
				|| StringUtils.isEmpty(companyInfo.getPkId())){
			log.error("更新公司信息失败！"+companyInfo.toString() ,null);
			return false;
		}		
		StringBuffer str = new StringBuffer();
		str.append("UPDATE baiduad.company_info                                                                                       ");
		str.append("SET                                                                                                               ");
		str.append("company_name=?,key_word=?,remark=?,url=?,start_date=?,end_date=?,sales_person=?");
		str.append(" WHERE                                                                                                             ");
		str.append("pk_id=?                                                                                                    ");
		return super.update(str.toString(), companyInfo.getCompanyName(),companyInfo.getKeyWord(),
				companyInfo.getRemark(),companyInfo.getUrl(),companyInfo.getStartDate(),companyInfo.getEndDate(),companyInfo.getSalesPerson(),companyInfo.getPkId());
	}

	@Override
	public boolean del(String pkId,HttpServletRequest servletRequest) {
		if(StringUtils.isEmpty(pkId) ){
			log.error("删除公司信息失败！pkId="+pkId ,null);
			return false;
		}	
		List<ImageInfo> list = imageInfoService.queryByComapny(pkId);
		if(list!=null && !list.isEmpty()){
			for (ImageInfo imageInfo : list) {
				imageInfoService.del(imageInfo.getPkId(),servletRequest);
			}
		}
		return super.del("DELETE FROM baiduad.company_info WHERE pk_id =  ? ", pkId);
	}

	@Override
	public List<CompanyInfo> queryAll() {
		return (List<CompanyInfo>) super.queryAll("SELECT pk_id,company_name,key_word,create_date,remark,url,start_date,end_date,sales_person FROM baiduad.company_info order by create_date desc");
	}

	@Override
	public RowMapper getRowMapper(){
		return new RowMapper() {
				public Object mapRow(ResultSet rs, int row)
						throws SQLException {
					CompanyInfo companyInfo = new CompanyInfo();
					companyInfo.setCompanyName(rs.getString("company_name"));
					companyInfo.setPkId(rs.getString("pk_id"));
					companyInfo.setCreateDate(rs.getDate("create_date"));
					companyInfo.setKeyWord(rs.getString("key_word"));
					companyInfo.setUrl(rs.getString("url"));
					companyInfo.setStartDate(rs.getDate("start_date"));
					companyInfo.setEndDate(rs.getDate("end_date"));
					companyInfo.setSalesPerson(rs.getString("sales_person"));
					if(companyInfo.getEndDate()!=null && DateUtil.compareDate(new Date(),companyInfo.getEndDate())){
						companyInfo.setExpired(CompanyInfo.EXPIRED);
					}else{
						companyInfo.setExpired(CompanyInfo.NO_EXPIRED);
					}
					companyInfo.setRemark(rs.getString("remark"));
					return companyInfo;
					}
				};
	}

	@Override
	public CompanyInfo getObjectById(String pkId) {
		if(StringUtils.isEmpty(pkId) ){
			log.error("获取单个公司信息失败！pkId="+pkId ,null);
			return null;
		}	
		return super.getObject("SELECT pk_id,company_name,key_word,create_date,remark,url,start_date,end_date,sales_person FROM baiduad.company_info where pk_Id = ? ", pkId);
	}

	@Override
	public Page<CompanyInfo> queryPage(CompanyInfo company,Integer pages) {
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT pk_id,company_name,key_word,create_date,remark,url,start_date,end_date,sales_person FROM baiduad.company_info where 1=1  ");
		if(StringUtils.isNotEmpty(company.getCompanyName())){
			sql.append(" and company_name like concat(?,'%') ");
			param.add(company.getCompanyName());
		}
		if(StringUtils.isNotEmpty(company.getSalesPerson())){
			sql.append(" and sales_person like concat(?,'%') ");
			param.add(company.getSalesPerson());
		}
		if(StringUtils.isNotEmpty(company.getExpired())){
			if(company.getExpired().equals(CompanyInfo.EXPIRED)){
				sql.append(" and end_date < now() ");
			}else{
				sql.append(" and (end_date >= now() or  end_date is null) ");
			}
		}
		if(company.getStartDate()!=null){
			sql.append(" and start_date >= ?  ");
			param.add(company.getStartDate());
		}
		if(company.getEndDate()!=null){
			sql.append(" and end_date <= ?  ");
			param.add(company.getEndDate());
		}
		
		sql.append(" order by create_date desc ");
		return super.pageBysql(sql.toString(), param.toArray(), pages);
	}
	


	@Override
	public String queryDefaultUuId() {
		try {
			return jt.queryForObject("select UUID from expired_uuid limit 1 ",
					String.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String updateDefaultUuId(String uuid) {
		if(!StringUtils.isEmpty(uuid)){
			boolean b = false;
			if(StringUtils.isEmpty(queryDefaultUuId())){
				 b = super.add("insert into expired_uuid (UUID) values (?) ", uuid);
			}else{
				 b = super.update("update  expired_uuid set UUID = ?  ", uuid);
			}
			return b ? "更新成功" : "更新失败";
		}
		return "更新失败 uuid为空";

	}

}
