package com.baiduad.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baiduad.action.LoginAction;

public class MyInterceptor
  extends HandlerInterceptorAdapter
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    HttpSession httpSession = request.getSession();
    if (httpSession.getAttribute(LoginAction.USER_KEY) != null) {
      return true;
    }
    response.setContentType("text/HTML;");
    response.getWriter().print("<script type='text/javascript'> window.location = '" + basePath + "login.jsp' ;</script>");
    response.getWriter().flush();
    response.getWriter().close();
    return false;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {}
}

