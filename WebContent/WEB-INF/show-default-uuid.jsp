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
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title></title>
</head>
<body>
<div class="pd-20">
	<form action="company-info/set-default-uuid.do" method="post" id="form1">
				<div class="row cl">
			<label class="form-label col-4"><span class="c-red">*</span>默认标识：</label>
			<div class="formControls col-4">
        <input type="text" style="width:320px;"  autocomplete="off"   class="input-text" placeholder="" id="uuid" name="uuid"  value="${uuid}" datatype="*2-100" nullmsg="不能为空">
			</div>
			<div class="col-4"> </div>
		</div>
		<br/>
		<div class="row cl">
			<div class="col-8 col-offset-4">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/Validform/5.3.2/Validform.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script>
<script>
$(function(){
	var msg = '${message}';
	if (msg) {
	 layer.confirm(msg+'您要关闭当前窗口吗？',function(index){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index); 
	 });
	}
	
	$("#form1").Validform({
		tiptype:2,
		callback:function(form){
			$("#form1").submit();
		}
	}); 
});
</script>
</body>
</html>