package com.baiduad.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.baiduad.util.FileUtil;
import com.baiduad.util.Logger;

@Service
public class ImageInfoServiceImpl extends BaseServiceImpl<ImageInfo>  implements IImageInfoService {
	
	private static final Logger log = new Logger(ImageInfoServiceImpl.class);
	
	@Autowired
	private ICompanyInfoService companyInfoService;
	
	public static final int MAX_IMAGE = 4;

	@Override
	public boolean add(ImageInfo imageInfo) {
		if(StringUtils.isEmpty(imageInfo.getUrl()) || StringUtils.isEmpty(imageInfo.getRefId()) ){
			log.error("添加图片信息失败！"+imageInfo.toString() ,null);
			return false;
		}
		List<ImageInfo> list = queryByComapny(imageInfo.getRefId());
		if(list!=null && list.size() >= MAX_IMAGE){
			log.error("添加图片信息失败！图片总数已经大于等于"+MAX_IMAGE ,null);
			return false;
		}
		CompanyInfo companyInfo = companyInfoService.getObjectById(imageInfo.getRefId());
		StringBuffer str = new StringBuffer();
		str.append("INSERT INTO baiduad.image_info                ");
		str.append("(title,url,order_num,link,create_date,remark,ref_id,company_name) ");
		str.append("VALUES                                        ");
		str.append("(?,?,?,?,?,?,?,?)                                 ");
		imageInfo.setCreateDate(new Date());
		return super.add(str.toString(), imageInfo.getTitle(),imageInfo.getUrl(),Integer.valueOf(imageInfo.getOrderNum()),imageInfo.getLink(),imageInfo.getCreateDate(),imageInfo.getRemark(),imageInfo.getRefId(),companyInfo.getCompanyName());
	}

	@Override
	public boolean update(ImageInfo imageInfo) {
		if(StringUtils.isEmpty(imageInfo.getUrl()) || StringUtils.isEmpty(imageInfo.getLink()) 
				|| imageInfo.getPkId() <= 0){
			log.error("更新图片信息失败！"+imageInfo.toString() ,null);
			return false;
		}
		StringBuffer str = new StringBuffer();
		str.append("UPDATE baiduad.image_info                  ");
			str.append("SET                                        ");
			str.append("title=?,url=?,order_num=?,link=?,remark=?  ");
			str.append("WHERE                                      ");
			str.append("pk_id=?                                    ");
		return super.update(str.toString(), imageInfo.getTitle(),imageInfo.getUrl(),Integer.valueOf(imageInfo.getOrderNum()),imageInfo.getLink(),imageInfo.getRemark(),Integer.valueOf(imageInfo.getPkId()));
	}

	@Override
	public boolean del(int pkId,HttpServletRequest servletRequest) {
		if(pkId <= 0){
			log.error("删除图片信息失败！"+pkId ,null);
			return false;
		}
		ImageInfo image = getObjectById(pkId);
		if(image!=null && super.del("DELETE FROM baiduad.image_info WHERE pk_id =  ? ", Integer.valueOf(pkId))){
			FileUtil.deleteFile(image.getUrl(), "imgs", servletRequest);
			return true;
		}
		return false;
	}

	@Override
	public List<ImageInfo> queryAll() {
		return (List<ImageInfo>) super.queryAll("SELECT company_name,pk_id,ref_id,title,url,order_num,link,create_date,remark FROM baiduad.image_info ORDER BY ref_id,order_num ASC,create_date DESC");
	}

	@Override
	public ImageInfo getObjectById(int pkId) {
		if(pkId <= 0){
			log.error("获取单个图片信息失败！pkId="+pkId ,null);
			return null;
		}	
		return super.getObject("SELECT company_name,pk_id,title,ref_id,url,order_num,link,create_date,remark FROM baiduad.image_info where pk_Id = ? ", Integer.valueOf(pkId));

	}

	@Override
	public RowMapper getRowMapper() {
		return new RowMapper() {
			public Object mapRow(ResultSet rs, int row)
					throws SQLException {
				ImageInfo companyInfo = new ImageInfo();
				companyInfo.setCreateDate(rs.getDate("create_date"));
				companyInfo.setPkId(rs.getInt("pk_id"));
				companyInfo.setLink(rs.getString("link"));
				companyInfo.setOrderNum(rs.getInt("order_num"));
				companyInfo.setUrl(rs.getString("url"));
				companyInfo.setRefId(rs.getString("ref_id"));
				companyInfo.setRemark(rs.getString("remark"));
				companyInfo.setTitle(rs.getString("title"));
				companyInfo.setCompanyName(rs.getString("company_name"));
				return companyInfo;
				}
			};
	}

	@Override
	public List<ImageInfo> queryByComapny(String refId) {
		return (List<ImageInfo>) super.queryAll("SELECT company_name,pk_id,ref_id,title,url,order_num,link,create_date,remark FROM baiduad.image_info where ref_id = ? ORDER BY order_num ASC,create_date DESC ",refId);
	}

	@Override
	public Page<ImageInfo> queryPage(String title, Integer pages) {
		Object[] param = null;
		StringBuffer sql = new StringBuffer("SELECT company_name,pk_id,ref_id,title,url,order_num,link,create_date,remark FROM baiduad.image_info  ");
		if(StringUtils.isNotEmpty(title)){
			sql.append(" where title like concat(?,'%') ");
			param = new Object[]{title}; 
		}
		sql.append(" ORDER BY ref_id,order_num ASC,create_date DESC ");
		return super.pageBysql(sql.toString(), param, pages);
	}

}
