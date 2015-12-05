package com.baiduad.action;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baiduad.model.CompanyInfo;
import com.baiduad.model.Page;
import com.baiduad.service.ICompanyInfoService;
import com.baiduad.util.DateUtil;
import com.baiduad.util.Logger;

@Controller
@RequestMapping({"company-info"})
public class CompanyInfoAction implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = new Logger(CompanyInfoAction.class);
	  
	@Autowired
	private ICompanyInfoService companyInfoService;
	
	@RequestMapping(value={"queryAll"})
	public ModelAndView queryAll(ModelAndView modelAndView,CompanyInfo companyInfo,Integer pages) {
		try {
			//List<CompanyInfo> list = companyInfoService.queryAll();
			Page<CompanyInfo> page = companyInfoService.queryPage(companyInfo, pages);
			modelAndView.setViewName("WEB-INF/company-list");
			modelAndView.getModelMap().addAttribute("page", page);
			modelAndView.getModelMap().addAttribute("companyName", companyInfo.getCompanyName());
			modelAndView.getModelMap().addAttribute("salesPerson", companyInfo.getSalesPerson());
			modelAndView.getModelMap().addAttribute("expired", companyInfo.getExpired());
			modelAndView.getModelMap().addAttribute("startDate", DateUtil.Date2String(companyInfo.getStartDate()));
			modelAndView.getModelMap().addAttribute("endDate", DateUtil.Date2String(companyInfo.getEndDate()));
			modelAndView.getModelMap().addAttribute("count", page.getTotalCount());
		} catch (Exception e) {
			log.error("获取公司列表出错", e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value={"queryAll-expired"})
	public ModelAndView queryAllExpired(ModelAndView modelAndView,Integer pages) {
		try {
			CompanyInfo companyInfo = new CompanyInfo();
			companyInfo.setExpired(CompanyInfo.EXPIRED);
			Page<CompanyInfo> page = companyInfoService.queryPage(companyInfo, pages);
			modelAndView.setViewName("WEB-INF/expired-company-list");
			modelAndView.getModelMap().addAttribute("page", page);
			modelAndView.getModelMap().addAttribute("count", page.getTotalCount());
		} catch (Exception e) {
			log.error("获取公司列表出错", e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value={"getObjectById"})
	public String getObjectById(HttpServletRequest request,@RequestParam String pkId) {
		try {
			CompanyInfo companyInfo = companyInfoService.getObjectById(pkId);
			request.setAttribute("companyInfo", companyInfo);
		} catch (Exception e) {
			log.error("获取单个公司信息出错", e);
		}
		return "WEB-INF/company-update";
	}
	
	@RequestMapping(value={"update"}, method={RequestMethod.POST})
	public String update(HttpServletRequest request,CompanyInfo companyInfo ) {
		try {
			boolean b =  companyInfoService.update(companyInfo);
			getObjectById(request,companyInfo.getPkId());
			request.setAttribute("message", b ? "更新成功！" : "更新失败！");
		} catch (Exception e) {
			log.error("更新公司信息出错", e);
		}
		return "WEB-INF/company-update";
	}
	
	@RequestMapping(value={"del"})
	public ModelAndView del(ModelAndView modelAndView,@RequestParam  String pkId,HttpServletRequest request) {
		String message  = null;
		try {
			boolean b =  companyInfoService.del(pkId,request);
		    message = b ? "删除成功！" : "删除失败！请您确认该公司下的图片信息已经完全删除。";
		    modelAndView.getModelMap().addAttribute("message", message);
		} catch (Exception e) {
			log.error("删除公司信息出错", e);
		}
		return queryAll(modelAndView,null,1);
	}
	
	@RequestMapping(value={"add"}, method={RequestMethod.POST})
	public String add(HttpServletRequest request,CompanyInfo companyInfo ) {
		try {
			boolean b =  companyInfoService.add(companyInfo);
			request.setAttribute("message", b ? "添加成功！" : "添加失败！");
		} catch (Exception e) {
			log.error("添加公司信息出错", e);
		}
		return "WEB-INF/company-add";
	}
	
	private static String[] searchArr = new String[]{
		"https://www.baidu.com/s?wd=",
		"http://www.haosou.com/s?q=",
		"https://www.sogou.com/web?query=",
		"http://www.youdao.com/search?q="
	};
	
	private static String[] faviconArr = new String[]{
		"baidu-favicon.ico",
		"haosou-favicon.ico",
		"sougou-favicon.ico",
		"youdao-favicon.ico"
	};
	
	private static String[] titleArr = new String[]{
		"_百度搜索",
		"_好搜",
		"_搜狗搜索",
		" - 有道搜索"
	};
	
	private static Map<String, Integer> urlMap = new HashMap<String, Integer>(){
		{
			this.put("baidu", 0);
			this.put("haosou", 1);
			this.put("sogou",2);
			this.put("youdao", 3);
		}
	};
	
	@RequestMapping(value={"get-ad"})
	public String getAd(HttpServletRequest request,@RequestParam String pkId,@RequestParam String  ref) {
		try {
			Integer index = urlMap.get(ref);
			if(index==null){
				index = 0;
			}
			CompanyInfo companyInfo = companyInfoService.getObjectById(pkId);
			//如果该公司已经过期则显示默认的公司信息
			if(companyInfo.getEndDate()!=null 
					&& DateUtil.compareDate(new Date(),companyInfo.getEndDate())){
				String defaultUuid = companyInfoService.queryDefaultUuId();
				if(defaultUuid!=null){
					CompanyInfo   _companyInfo = companyInfoService.getObjectById(defaultUuid);
					if(_companyInfo!=null){
						companyInfo = _companyInfo;
					}
				}
			}
			if(index==null || index < 0 || index >= searchArr.length){
				index = 0;
			}
			request.setAttribute("company", companyInfo);
			request.setAttribute("title", companyInfo.getKeyWord()+titleArr[index]);
			request.setAttribute("ico", faviconArr[index]);
			request.setAttribute("searchSrc", searchArr[index]+companyInfo.getKeyWord());
		} catch (Exception e) {
		}
		return "ad";
	}
	
	
	@RequestMapping(value={"show-default-uuid"})
	public String showDefaultUuid(HttpServletRequest request) {
		try {
			String uuid = companyInfoService.queryDefaultUuId();
			request.setAttribute("uuid", uuid);
		} catch (Exception e) {
			log.error("获取默认uuid信息出错", e);
		}
		return "WEB-INF/show-default-uuid";
	}
	
	@RequestMapping(value={"set-default-uuid"})
	public String setDefaultUuid(HttpServletRequest request,@RequestParam String uuid) {
		try {
			String message = companyInfoService.updateDefaultUuId(uuid);
			uuid = companyInfoService.queryDefaultUuId();
			request.setAttribute("uuid", uuid);
			request.setAttribute("message", message);
		} catch (Exception e) {
			log.error("更新默认uuid信息出错", e);
			e.printStackTrace();
		}
		return "WEB-INF/show-default-uuid";
	}
}
