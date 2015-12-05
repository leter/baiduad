package com.baiduad.action;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.baiduad.model.ImageInfo;
import com.baiduad.model.Page;
import com.baiduad.service.ICompanyInfoService;
import com.baiduad.service.IImageInfoService;
import com.baiduad.service.impl.ImageInfoServiceImpl;
import com.baiduad.util.FileUtil;
import com.baiduad.util.Logger;

@Controller
@RequestMapping({"image-info"})
public class ImageInfoAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = new Logger(ImageInfoAction.class);
	  
	@Autowired
	private IImageInfoService imageInfoService;
	
	@Autowired
	private ICompanyInfoService companyInfoService;
	
	
	
	@RequestMapping(value={"queryAll"})
	public String queryAll(HttpServletRequest request,String title,Integer pages) {
		try {
			Page<ImageInfo> page = imageInfoService.queryPage(title, pages);
			request.setAttribute("page", page);
			request.setAttribute("count", page.getTotalCount());
			request.setAttribute("title", title);
		} catch (Exception e) {
			log.error("获取图片列表出错", e);
		}
		return "WEB-INF/image-list";
	}
	
	
	@RequestMapping(value={"queryByComapny"})
	public String queryByComapny(HttpServletRequest request,@RequestParam String refId) {
		try {
			List<ImageInfo> list = imageInfoService.queryByComapny(refId);
			request.setAttribute("list", list);
			request.setAttribute("count", list!=null ? list.size() : 0);
			request.setAttribute("company", companyInfoService.getObjectById(refId));
		} catch (Exception e) {
			log.error("获取图片列表出错 refId="+refId, e);
		}
		return "WEB-INF/company-detail";
	}
	
	
	@RequestMapping(value={"getObjectById"})
	public String getObjectById(HttpServletRequest request,@RequestParam int pkId) {
		try {
			ImageInfo imageInfo = imageInfoService.getObjectById(pkId);
			request.setAttribute("imageInfo", imageInfo);
		} catch (Exception e) {
			log.error("获取单个图片信息出错", e);
		}
		return "WEB-INF/image-update";
	}
	
	@RequestMapping(value={"update"}, method={RequestMethod.POST})
	public String update(HttpServletRequest request,ImageInfo imageInfo,@RequestParam(value="myFile",required=false) MultipartFile myfile,@RequestParam int compressImg) {
	    String imageName = null;
	    if(imageInfo!=null){
			try {
				boolean b  = false;
				ImageInfo oldPojo = imageInfoService.getObjectById(imageInfo.getPkId());
				if(imageInfo!=null){
				    imageName = FileUtil.uploadFile(myfile, "imgs", request);
				    if(compressImg==1 && imageName!=null){
			        	String smallImage = null;
				        smallImage = FileUtil.compressPic(imageName, "imgs", request, 1920, 100);
					    FileUtil.deleteFile(imageName, "imgs", request);
					    imageName = smallImage;
				    }
				    if(imageName==null){
					    imageInfo.setUrl(oldPojo.getUrl());
				    }else{
					    imageInfo.setUrl(imageName);
				    }
					b =  imageInfoService.update(imageInfo);
					if(b){
						 if(imageName!=null && !imageName.equals(oldPojo.getUrl())){
							 FileUtil.deleteFile(oldPojo.getUrl(), "imgs", request);
						 }
					}else{
						 FileUtil.deleteFile(imageName, "imgs", request);
					}
				}
				getObjectById(request,imageInfo.getPkId());
				request.setAttribute("message", b ? "更新成功！" : "更新失败！");
				request.setAttribute("refId", oldPojo.getRefId());
			} catch (Exception e) {
				log.error("更新图片信息出错", e);
			    FileUtil.deleteFile(imageName, "imgs", request);
			}
	    }

		return "WEB-INF/image-update";
	}
	
	@RequestMapping(value={"del"})
	public String del(HttpServletRequest request,@RequestParam  int pkId,@RequestParam  String  refId) {
		try {
			boolean b = false;
			ImageInfo imageInfo = imageInfoService.getObjectById(pkId);
			if(imageInfo!=null){
			    b =  imageInfoService.del(pkId,request);
				if(b){
					FileUtil.deleteFile(imageInfo.getUrl(), "imgs", request);
				}
			}
			request.setAttribute("message", b ? "删除成功！" : "删除失败！");
			request.setAttribute("refId", refId);
		} catch (Exception e) {
			log.error("删除图片信息出错", e);
		}
		return queryByComapny(request,refId);
	}
	
	@RequestMapping(value={"add"}, method={RequestMethod.POST})
	public String add(HttpServletRequest request,ImageInfo imageInfo, @RequestParam("myFile") MultipartFile myfile,@RequestParam int compressImg) {
	    String imageName = null;
		try {
	        imageName = FileUtil.uploadFile(myfile, "imgs", request);
	        if(compressImg==1 && imageName!=null){
	        	String smallImage = null;
		        smallImage = FileUtil.compressPic(imageName, "imgs", request, 1920, 100);
			    FileUtil.deleteFile(imageName, "imgs", request);
			    imageName = smallImage;
	        }
	        imageInfo.setUrl(imageName);
			boolean b =  imageInfoService.add(imageInfo);
			request.setAttribute("message", b ? "添加成功！" : "添加失败！可能是因为当前最多只能添加"+ImageInfoServiceImpl.MAX_IMAGE+"张图片。");
		} catch (Exception e) {
			log.error("添加图片信息出错", e);
		    FileUtil.deleteFile(imageName, "imgs", request);
		}
		request.setAttribute("refId", imageInfo.getRefId());
		return "WEB-INF/image-add";
	}
	
	
	@RequestMapping(value={"get-imgs"})
	public String getImgs(HttpServletRequest request,@RequestParam String refId) {
		try {
			List<ImageInfo> list = imageInfoService.queryByComapny(refId);
			request.setAttribute("list", list);
		} catch (Exception e) {
		}
		return "image";
	}
}
