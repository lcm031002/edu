<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />
<title>图像上传</title>
<link rel="stylesheet" type="text/css" href="bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="style.css">
<script type="text/javascript" src="jquery-1.js"></script>
<script type="text/javascript" src="cropbox.js"></script>
</head>
<body>

<div class="container">
	<div class="action">
   	   <div class="new-contentarea tc"> 
   	   		<a href="javascript:void(0)" class="upload-img"><label for="upload-file" style="padding-top:3px;">上传图像</label></a>
           <input class="" name="upload-file" id="upload-file" type="file">
       </div>
	   <input id="upSave" class="Btnsty_peyton" value="上传" type="button">
    </div>
    <div style="background-image: url(&quot;&quot;); background-size: 658px 851px; background-position: -129px -225.5px; background-repeat: no-repeat;transform-origin: 0px 0px 0px; transition-timing-function: cubic-bezier(0.1, 0.57, 0.1, 1); transition-duration: 0ms; transform: translate(0px, 0px) scale(1) translateZ(0px);" class="imageBox">
        <div class="thumbBox"></div>
        <div class="spinner" style="display: none;">请上传头像.</div>
    </div>
	<div style=" float:left;margin-top:10px; margin-bottom:10px; text-align:center; width:100%;">
      	<input id="btnZoomIn" class="Btnsty_peyton" value="+" type="button" style="float:left;">
        <input id="btnZoomOut" class="Btnsty_peyton" value="-" type="button" style="float:left;">
	 	<input id="btnCrop" class="Btnsty_peyton" value="裁切" type="button" style="float:right;">
	 	
	</div>
    <div class="cropped">
        <img  style="width:50%; height:50%; float:left;margin-top:4px;border-radius:1000px;" ></p>
    </div>
</div>

<script type="text/javascript">
    $(window).load(function() {
        var options =
        {
            thumbBox: '.thumbBox',
            spinner: '.spinner',
            imgSrc: 'images/avatar.jpg'
        }
        var cropper = $('.imageBox').cropbox(options);
        $('#upload-file').on('change', function(){
            var reader = new FileReader();
            reader.onload = function(e) {
                options.imgSrc = e.target.result;
                cropper = $('.imageBox').cropbox(options);
            }
            reader.readAsDataURL(this.files[0]);
            this.files = [];
        })
        $('#btnCrop').on('click', function(){
            var img = cropper.getDataURL();
            $('.cropped').html('');
            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:15%;margin-top:4px;border-radius:1000px;box-shadow:0px 0px 12px #7E7E7E;float:left;margin-left:15px;" >');
            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:25%;margin-top:4px;border-radius:1000px;box-shadow:0px 0px 12px #7E7E7E;float:left; margin-left:20px;">');
            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:35%;margin-top:4px;border-radius:1000px;box-shadow:0px 0px 12px #7E7E7E;float:left; margin-left:20px;">');
        })
        $('#btnZoomIn').on('click', function(){
            cropper.zoomIn();
        })
        $('#btnZoomOut').on('click', function(){
            cropper.zoomOut();
        })
        
        /* var _redirect_uri = '${requestScope._redirect_uri}';    
     	  if(_redirect_uri.indexOf('?') > 0){
        	_redirect_uri += '&';
        }else{
        	redirect_uri += '?';
        } */ 
       var employeeId=${requestScope.employee_id};
       // var openWait = false;
        $('#upSave').bind('click', function(){
        	
        // if(openWait == true) return false;
			
        	var imageData = cropper.getDataURL();
        	if(imageData == null || $.trim(imageData.length) == 0){
        		return false;
        	}
        	//openWait = true;
        	$.ajax({
        		url: '/hrm/upload/base64Str',
        		cache : false,
        		data : {_imageData: imageData},
        		dataType: "json",
        		type: "POST",
        		success : function(Res) {
        			//openWait = false;
        			if(200 == Res.code){
        				alert("保存成功");
        			imageUrl=Res.data;
        			updateImage();
        			
        		   /* window.location.href = 'templates/hrm/employee/employee.html?_imageUrl=' + imageUrl; */      	
        			}else{
        				alert('上传失败咯！' + Res.msg);
        			}
        		},
        		error : function() {
        			//openWait = false;
        			alert("error");
        		}
        	});
        });
        
        function updateImage(){
        	var param={};
        	param.id=employeeId;
        	param.staff_head=imageUrl;
        	param=JSON.stringify(param);
        	$.ajax({
        		url:'/hrm/employee/employeeservice/employeeInfo/image',
        		cache:false,
        		contentType:"application/json",  
        		data:param,
        		dataType:"json",
        		type:"PUT",
        		success:function(Res){
        			if(Res.error==false){
        				alert("图片路径已保存到数据库"); 
        			}else{
        				alert("保存路径失败了!"+Res.msg);
        			}
        		},
        		error:function(){
        			alert("error");
        		}
        	})
        };
    })
</script>
</body>
</html>