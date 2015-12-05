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
<title>公司详情</title>
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 公司列表  <span class="c-gray en">&gt;</span> 详情 [<b>${company.companyName }</b>] <a class="btn btn-success radius r mr-20" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> <a class="btn btn-primary radius"
				onclick="img_addOrUpdate('添加图片','showJsp.do?jsp=image-add&refId=${company.pkId}','','480')" href="javascript:;"><i
					class="Hui-iconfont">&#xe600;</i> 添加广告图片</a></span> <span class="r">共有数据：<strong>${count }</strong>
				条
			</span>
		</div>
		<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="50">ID</th>
					<th width="150">图片标题</th>
					<th width="380">缩略图</th>
					<th width="120">显示顺序</th>
					<th width="200">图片链接</th>
					<th >备注</th>
					<th width="100">创建日期</th>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="img">
				<tr class="text-c">
					<td>${img.pkId }</td>
					<td >${img.title }</td>
					<td ><a title="点击打开该图片" href="imgs/${img.url}" target=“_blank"> <img width=350 height= 80 src="imgs/${img.url}" /></a></td>
					<td >${img.orderNum }</td>
					<td><a href="${img.link}" style="color:blue;" target="_blank">${img.link}</a></td>
					<td>${img.remark }</td>
					<td>${img.createDate }</td>
							<td class="f-14 td-manage"> <a
								style="text-decoration: none" class="ml-5"
								onClick="img_addOrUpdate('编辑图片','image-info/getObjectById.do?pkId=${img.pkId}','','480')"
								href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>
								<a style="text-decoration: none" class="ml-5"
								onClick="img_del('${img.title }','${img.pkId}','${img.refId}')" href="javascript:;"
								title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
						</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script>
<script type="text/javascript">

function img_addOrUpdate(title,url,w,h){
	layer_show(title,url,w,h);
}
function img_del(title,pkId,refId){
	layer.confirm('您确认要删除图片['+title+']吗？',function(index){
		window.location.href = 'image-info/del.do?pkId='+pkId+'&refId='+refId;
	});
}
$(function(){
	var msg = '${message}';
	if (msg) {
	  layer.alert(msg);
	}
});
</script> 
</body>
</html>