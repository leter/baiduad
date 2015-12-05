<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<link rel="shortcut icon" href="<%=basePath%>images/${ico}">
<base href="<%=basePath%>">
<title>${title}</title>
</head>
<frameset rows="*,100" framespacing="0" border="0" frameborder="0">
  <frame name="content" src="${searchSrc }">
    <frame name="ad" src="<%=basePath%>image-info/get-imgs.do?refId=${company.pkId }" scrolling="no">
</frameset>
<noframes></noframes>
</html>