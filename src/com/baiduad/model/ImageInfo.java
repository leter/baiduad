package com.baiduad.model;

import java.io.Serializable;
import java.util.Date;

public class ImageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pkId;
	private String title;
	private String url;
	private String link;
	private String remark;
	private Date createDate;
	private Integer orderNum;
	private String refId;
	private String companyName;
	
	
	

	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	public Integer getPkId() {
		return pkId;
	}
	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "ImageInfo [pkId=" + pkId + ", title=" + title + ", url=" + url
				+ ", link=" + link + ", remark=" + remark + ", createDate="
				+ createDate + ", orderNum=" + orderNum + ", refId=" + refId
				+ ", companyName=" + companyName + "]";
	}

	
	
	
}
