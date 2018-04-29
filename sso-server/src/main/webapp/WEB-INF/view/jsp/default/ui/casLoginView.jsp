<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:directive.include file="includes/top2.jsp" />
<!DOCTYPE html>
<html ng-app="ework-ui" style="height: 100%;">
<head lang="zh-cn">
    <title>厝边素高</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="厝边素高" />
    <jsp:directive.include file="includes/head.jsp" />
    <script src="lib/js/main.min.js"></script>
    <script type="text/javascript" src="js/eWorkUI.min.js"></script>

    <script type="text/javascript">
        function handleBeforeSubmit() {
            var username = $('#txtUsername').val();
            if (username) {
                $('#txtUsername').val(username.trim());
            }
        }
    </script>
</head>
<body>
<!--header start here-->
<div class="login-form">
    <div class="top-login">
        <span><img src="images/group.png" alt=""/></span>
    </div>
    <h1>登录</h1>
    <div class="login-top">
        <form:form  method="post"  htmlEscape="true">
            <div class="login-ic">
                <i></i>
                <input type="text"  value="用户" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = '用户';}" ng-model="user.name" required="" type="text" name="username" id="txtUsername"/>
                <div class="clear"> </div>
            </div>
            <div class="login-ic">
                <i class="icon"></i>
                <input type="password"  value="密码" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'password';}" ng-model="user.password" required="" type="password"  name="password"/>
                <div class="clear"> </div>
            </div>

            <div class="login-ic">
                <i class="icon"></i>
                <input placeholder="验证码" class="form-control pull-left" style="width:50%;" name="captcha" required="" type="text">
                <img src = "captcha.htm" id="validcode" style="margin-right: 10px;">
                <a href="#" onclick="javascript:changeValideCode();">刷新</a>
            </div>

            <div class="log-bwn">
                <input type="hidden" name="lt" value="${flowExecutionKey}" />
                <input type="hidden" name="_eventId" value="submit" />
                <input type="submit"  value="登录" onclick="handleBeforeSubmit()">
            </div>
        </form:form>
    </div>
    <p class="copy">Copyright 2017-2018 厦门住总物业管理有限公司 版权所有 All Rights Reserved</p>
</div>
</body>

<script type="text/javascript">
	function changeValideCode(){
		var _ = (new Date()).getTime();
		$("#validcode").attr('src','captcha.htm'+"?_="+_);
	}

    $(window).resize(function(){
    	setWrapperHeight()
    })
    
    function setWrapperHeight() {
    	var $wrapper = $('.wrapper');
    	var wH = $(window).height();
    	$wrapper.height(wH > 780 ? wH-180 : 600);
    }
    setWrapperHeight();
</script>
</html>