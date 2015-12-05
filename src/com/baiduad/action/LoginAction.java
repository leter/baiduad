package com.baiduad.action;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baiduad.model.UserInfo;
import com.baiduad.service.IUserInfoService;
import com.baiduad.util.EncryptionUtil;
import com.baiduad.util.Logger;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class LoginAction
{
  private static Logger log = new Logger(LoginAction.class);
  
  public static final String USER_KEY = "userKey";
  
  @Autowired
  private IUserInfoService userInfoServiceImpl;
  
  @Autowired 
  private DefaultKaptcha captchaProducer;
  
  @RequestMapping({"login"})
  public String login(
		  HttpSession session,
		  HttpServletRequest request,
		  @RequestParam(value="userName", required=true) String userName, 
		  @RequestParam(value="vertifyCode",required=true) String vertifyCode,
		  @RequestParam(value="userPass", required=true) String password, 
		  ModelMap modelMap)
  {
    String message = null;
	userName = userName.trim();
	password = password.trim();
	vertifyCode = vertifyCode.trim();
    String vertifyCodeReal = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    if(!vertifyCode.equalsIgnoreCase(vertifyCodeReal)){
		message =  "验证码不正确！";
	}else{
		UserInfo userInfo = userInfoServiceImpl.getUserInfo(userName);
		if(userInfo==null){
			message = "用户名不存在！";
		}else{
			String realPass =EncryptionUtil.MD5Encode(EncryptionUtil.passWordDecode(userInfo.getUserPass(), userInfo.getUserName())+vertifyCode);
			if(realPass.equals(password)){
				log.info("登录成功[" + getRemortIP(request) + "]");
				session.setAttribute(USER_KEY, userInfo);
				return "WEB-INF/index";
			}else{
				message = "密码错误！";
			}
		}
	}
    log.info("登录失败，失败原因，"+message+" [" + getRemortIP(request) + "]");
    request.setAttribute("message", message);
	session.setAttribute("userName", userName);
	return "login";
  }
  
  @RequestMapping({"changePassWord"})
  public String chnagePass(		  
		  HttpSession session,
		  HttpServletRequest request,
		  @RequestParam(value="oldPassWord", required=true) String oldPassWord, 
		  @RequestParam(value="newPassWord",required=true) String newPassWord
		  ){
	  String userName = session.getAttribute("userName").toString(); 
	  boolean b = userInfoServiceImpl.changePass(userName, oldPassWord, newPassWord);
	  request.setAttribute("message", b ? "密码修改成功！" : "密码修改失败！请您检查一下你原来的密码是否输入正确。");
	  return "WEB-INF/change-pass";
   }
  
  @RequestMapping({"loginOut"})
  public String loginOut(		  
		  HttpSession session,
		  HttpServletRequest request
		  ){
	  session.removeAttribute(USER_KEY);
	  return "login";
   }
  
	@RequestMapping(value={"showJsp"})
	public String showJsp( HttpServletRequest request,String jsp,String refId) {
				request.setAttribute("refId", refId);
		return "WEB-INF/"+jsp;
	}
  
  public static String getRemortIP(HttpServletRequest request)
  {
    if (request.getHeader("x-forwarded-for") == null) {
      return request.getRemoteAddr();
    }
    return request.getHeader("x-forwarded-for");
  }
  
	@RequestMapping("captcha/image")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
}
