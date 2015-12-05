<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String myPath = request.getScheme()+"://"+request.getServerName()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<base href="<%=basePath%>">
<title>已过期公司列表</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>公司列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 已过期公司列表 <a class="btn btn-success radius r mr-20" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
		<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="150">唯一标识</th>
					<th width="180">公司名称</th>
					<th width="120">关键词</th>
					<th width="250">网址</th>
					<th width="100">业务员</th>
					<th width="100">开始使用日期</th>
					<th width="100">停止使用日期</th>
					<th width="90">是否过期</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="company">
				<tr class="text-c">
					<td>${company.pkId }</td>
					<td >${company.companyName }</td>
					<td><span class="label label-danger radius">${company.keyWord }</span></td>
					<td><a href="${company.url}" style="color:blue;" target="_blank">${company.url}</a></td>
					<td>${company.salesPerson }</td>
					<td>${company.startDate }</td>
					<td>${company.endDate }</td>
					<td>
					<c:if  test="${company.expired  eq  '已过期'}"><span style='color:red'>已过期 </span> </c:if>
					<c:if  test="${company.expired eq  '未过期'}"><span style='color:green'>未过期</span> </c:if>
					</td>
						</tr>
			</c:forEach>
			</tbody>
		</table>
</div>
<div id="page11" style="margin-top:5px; text-align:center;"></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script>
<script type="text/javascript" src="laypage-v1.3/laypage/laypage.js"></script>
<script>
$(function(){
	var msg = '${message}';
	if (msg) {
	  layer.alert(msg);
	}
	
	laypage({
	    cont: 'page11',
	    pages: '${page.totalPage}', //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
	    curr:'${page.page}',
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	            location.href = 'company-info/queryAll-expired.do?pages='+e.curr;
	        }
	    }
	});
});
</script> 
</body>
</html>