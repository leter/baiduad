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
<title>添加图片</title>
</head>
<body>
<div class="pd-20">
  <form action="${pageContext.request.contextPath}/image-info/add.do" method="post" class="form form-horizontal" id="form-member-add" enctype="multipart/form-data">
    <div class="row cl">
      <label class="form-label col-3">图片标题：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="" placeholder="" id="title" name="title" datatype="*0-20">
                <input type="hidden" name="refId" id="pkId"  value="${refId }" >
      </div>
      <div class="col-4"> </div>
    </div>
           <div class="row cl">
      <label class="form-label col-3">图片链接：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="" placeholder="" id="link" name="link" datatype="*0-200" >
      </div>
      <div class="col-4"> </div>
    </div>
    		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>显示顺序（小的靠前）：</label>
			<div class="formControls col-5"> <span class="select-box" style="width:150px;">
				<select name="orderNum" class="select"  size="1">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select>
				</span> </div>
		</div>
		    <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>图片：</label>
      <div class="formControls col-5"> <span class="btn-upload form-group">
        <input class="input-text upload-url" type="text" name="uploadfile-2" id="uploadfile-2" readonly  datatype="*" nullmsg="请添加附件！" style="width:200px">
        <a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
        <input type="file" multiple name="myFile" class="input-file">
        </span> </div>
      <div class="col-4"> </div>
    </div>
    		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>压缩图片：</label>
			<div class="formControls col-5 skin-minimal">
				<div class="radio-box">
					<input type="radio" checked id="sex-1" value="0" name="compressImg" datatype="*" nullmsg="请选择是否压缩图片"/>
					<label for="sex-1">不压缩</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="sex-2" value="1" name="compressImg">
					<label for="sex-2">压缩</label>
				</div>
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
<script type="text/javascript">
$(function(){
	var msg = '${message}';
	if (msg) {
	 layer.confirm(msg+'您要关闭当前窗口吗？',function(index){
			parent.location.replace("image-info/queryByComapny.do?refId=${refId}");
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