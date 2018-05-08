<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:directive.include file="includes/top2.jsp" />
<!DOCTYPE html>
<html ng-app="ework-ui" style="width:100%;height:100%;position:fixed;">
<head lang="zh-cn">
    <title>厝边素高</title>
    <meta name="keywords" content="厝边素高" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <jsp:directive.include file="includes/head.jsp" />
    <script src="lib/js/main.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/eWorkUI.min.css">
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
<body ng-controller="LoginCtrl">
<!--header start here-->
<div class="login-form" style="margin-top:150px;right: 100px;position: absolute;">
    <div class="clear"> </div>
    <div class="avtar">
        <img src="images/logo.png" />
    </div>
    <form:form  method="post" commandName="${commandName}" htmlEscape="true">
        <input type="text" class="text" placeholder="用户名" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '用户名';} " ng-model="user.name" required=""  name="username" id="txtUsername">
        <input type="password" class="text" style="margin-bottom: 0px" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}" ng-model="user.password" required=""  name="password">

        <input placeholder="验证码" class="text" name="captcha" required="" type="text" style="width:42.5%;margin-top:0px;margin-bottom:40px">
        <img src = "captcha.htm" id="validcode" style="margin-right: 10px;">
        <a href="#" style="color:#000" onclick="javascript:changeValideCode();">刷新</a>

        <div class="signin">
            <input type="hidden" name="lt" value="${flowExecutionKey}" />
            <input type="hidden" name="_eventId" value="submit" />
            <input type="submit" value="登录" name="submit" onclick="handleBeforeSubmit()">
        </div>
    </form:form>

</div>
<div class="copy-rights" style="color:#ffffff;bottom:30px;width:100%;position: absolute;">
    <p>Copyright &copy; 2017-2018 厦门住总物业管理有限公司 版权所有 All Rights Reserved</p>
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