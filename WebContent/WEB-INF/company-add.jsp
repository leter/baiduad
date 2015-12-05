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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,member-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>添加公司</title>
</head>
<body>
<div class="pd-20">
  <form action="${pageContext.request.contextPath}/company-info/add.do" method="post" class="form form-horizontal" id="form-member-add">
    <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>公司名称：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="" placeholder="" id="companyName" name="companyName" datatype="*2-20" nullmsg="公司名称不能为空">
      </div>
      <div class="col-4"> </div>
    </div>
        <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>关键词：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="" placeholder="" id="keyWord" name="keyWord" datatype="*2-16" nullmsg="关键词不能为空">
      </div>
      <div class="col-4"> </div>
    </div>
        <div class="row cl">
      <label class="form-label col-3">网址：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="" placeholder="" id="url" name="url" datatype="*0-200" >
      </div>
      <div class="col-4"> </div>
    </div>
            <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>业务员：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="" placeholder="" id="salesPerson" name="salesPerson" datatype="*2-200" >
      </div>
      <div class="col-4"> </div>
    </div>
            <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>开始使用日期：</label>
      <div class="formControls col-5">
				<input type="text"  autocomplete="off" name="startDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})" id="datemin" class="input-text Wdate">
      </div>
      <div class="col-4"> </div>
    </div>
    
                <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>停止使用日期：</label>
      <div class="formControls col-5">
				<input type="text" autocomplete="off" name="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datemin\')}'})" id="datemax" class="input-text Wdate">
      </div>
      <div class="col-4"> </div>
    </div>
    
    <div class="row cl">
      <label class="form-label col-3">备注：</label>
      <div class="formControls col-5">
        <textarea name="remark" cols="" rows="" class="textarea"   datatype="*0-200" dragonfly="true" onKeyUp="textarealength(this,200)"></textarea>
        <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <div class="col-9 col-offset-3">
        <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
      </div>
    </div>
  </form>
</div>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript">
$(function(){
	var msg = '${message}';
	if (msg) {
	 layer.confirm(msg+'您要关闭当前窗口吗？',function(index){
		parent.location.replace("company-info/queryAll.do");
	 });
	}
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-member-add").Validform({
		tiptype:2,
		callback:function(form){
			$("#form-member-add").submit();
		}
	});
});
</script>
</body>
</html>