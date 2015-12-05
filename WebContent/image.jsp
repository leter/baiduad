<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<base href="<%=basePath%>">
<title></title>
<style>
* { margin: 0px; padding: 0px; list-style: none }
.lists .bd li { height: 100px; background: top center no-repeat }
.lists .bd li a { display: block; max-width:100%;height:auto;}
.lists .hd { position: absolute; z-index: 100; right:10px;top:10px; }
.lists .hd li { cursor: pointer;  width: 11px; height: 11px; margin: 4px; background:#CCC; overflow: hidden; line-height: 9999px; filter: alpha(opacity=40); opacity: 0.4; }
.lists .hd .on { filter: alpha(opacity=100); opacity: 1; }
</style>
</head>
<body>
<div class="lists">
  <ul class="hd">
  </ul>
  <ul class="bd">
  <c:forEach items="${list}" var="img">
	<li><a href="${img.link}" title="${img.title}" target="_blank"><img width="100%" height="100" src="<%=basePath%>imgs/${img.url}"/></a></li>
  </c:forEach>
  </ul>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script> 
<script src="<%=basePath%>js/jquery.SuperSlide.2.1.1.js" type="text/jscript"></script> 
<script type="text/javascript">
jQuery(".lists").slide({ titCell:".hd", mainCell:".bd", effect:"topLoop",  autoPlay:true, autoPage:true, trigger:"click" });
</script>

</html>