<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:directive.include file="includes/top2.jsp" />
<!DOCTYPE html>
<html ng-app="ework-ui" style="height: 100%;">
<head lang="zh-cn">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>快乐学习</title>
    <jsp:directive.include file="includes/head.jsp" />
    
    <!-- STYLES -->
    <link rel="stylesheet" href="lib/css/main.min.css"/>

    <!-- SCRIPTS -->
    <script src="lib/js/main.min.js"></script>

    <!-- 自定义的部分，构建打包成压缩资源 -->
    <link rel="stylesheet" type="text/css" href="css/eWorkUI.min.css">
    <script type="text/javascript" src="js/eWorkUI.min.js"></script>
	<style>
        body {min-height: 720px;min-width: 460px;position: relative;}

        .logo {position: absolute;top: 20px; left: 20px;width: 242px;height: 30px;background: url('images/logo-sm.png') no-repeat center;}
        
        .wrapper {position: relative;top: 0;left: 0;right: 0;min-height: 600px;bottom: 180px;background: linear-gradient(39deg, #5c82ff,#57c4ff);vertical-align: middle;}
        .login-container{position: absolute;width: 100%;background: url('images/login-bg-sm.png') no-repeat 8% center;top: 0;left: 0;right: 0;bottom: 0;}
        
        .login-wrapper {background-color: #f2f7fd;width: 300px; height: 366px; float: right; border-radius: 6px;
            margin-top: -183px;padding: 30px 32px;position: absolute;right: 80px;top: 50%;box-shadow: 0px 10px 25px rgba(27,140,242,1);}
        .login-wrapper .title {font-size: 20px;line-height: 20px;color: #333;text-align: center;}
        .login-wrapper .form-group {margin-top: 10px;}
        .login-wrapper .form-group>label {font-size: 14px;color: #333;font-weight: normal;}
        .login-wrapper .submit-btn.form-group{margin-top: 30px;}
        .login-wrapper .submit-btn.form-group>button{padding: 6px 12px; font-size: 14px;}
        button.btn-primary:hover {background-color: #1b8cf2;border-color: #1b8cf2;}
        button.btn-primary {background-color: #3b9ef6;border-color: #3b9ef6;}

        #footer-wrapper {position: absolute;top: initial;bottom: 30px;left: 0px;right: 0px;}

        /* <= 600 */
         @media screen and (max-width: 460px) {
            body {min-height: 360px;min-width: 230px;}
            .wrapper {min-height: 300px;}
            .login-wrapper {right:50%;margin-right:-150px;}
            #footer-wrapper{
                margin-top: -50px;
                position: relative;
                z-index: 1;
                top:0;
                bottom:0;
             }
         }
        /* >= 1024 */
        @media screen and (min-width: 1024px) { 
        }
        /* >= 1280 */
        @media screen and (min-width: 1280px) {
            .logo {left: 25px;}
            .login-wrapper{right: 140px;}
        }
        /* >= 1366 */
        @media screen and (min-width: 1366px) {
            .login-wrapper{right: 140px;}
            .login-container{position: absolute;width: 100%;background: url(images/login-bg-sm.png) no-repeat 15% center;top: 0;left: 0;right: 0;bottom: 0;}
        }
        /* >= 1920 */
        @media screen and (min-width: 1600px) {
            .logo {top: 20px;left: 30px;width: 283px;height: 36px;background: url('images/logo.png') no-repeat center;}
            .login-container {background-image: url('images/login-bg.png')}
            .login-wrapper {background-color: #f2f7fd;width: 410px;height: 510px;float: right;border-radius: 6px;
                margin-top: -255px;padding: 40px 45px;position: absolute;right: 250px;top: 50%;box-shadow: 0px 10px 25px rgba(27,140,242,1);}
            .login-wrapper .title {font-size: 28px;line-height: 28px;color: #333;text-align: center;}
            .login-wrapper .form-group {margin-top: 30px;}
            .login-wrapper .form-group>label {font-size: 16px;color: #333;font-weight: normal;}
            .login-wrapper .submit-btn.form-group{margin-top: 60px;}
        }
        /* >= 2560 */
        @media screen and (min-width: 2560px) {
            .logo {top: 40px;left: 50px;width: 474px;height: 60px;background: url('images/logo-lg.png') no-repeat center;}
            .login-container {background-image: url('images/login-bg-lg.png')}
            .login-wrapper {width: 630px;height: 760px;border-radius: 12px;
                margin-top: -380px;padding: 60px 70px; right: 250px;top: 50%;box-shadow: 0px 10px 25px rgba(27,140,242,1);}
            .login-wrapper .title {font-size: 42px;line-height: 28px;color: #333;text-align: center;}
            .login-wrapper .form-group {margin-top: 60px;}
            .login-wrapper .form-group>label {font-size: 24px;color: #333;font-weight: normal;}
            .login-wrapper .form-group input {font-size: 20px;line-height: 58px;height: 58px;}
            .login-wrapper .vcode input{font-size: 20px; height: 58px; line-height: 58px;}
            .login-wrapper .vcode img#validcode {font-size: 20px;line-height: 0;height: 58px;}
            .login-wrapper .vcode a {font-size: 20px;line-height: 60px;}
            .login-wrapper .submit-btn.form-group{margin-top: 60px;line-height: 60px; font-size: 24px;height: 60px;}
             
            .login-wrapper .submit-btn.form-group>button{font-size: 24px;height: 58px;}
        }
	</style>

    <script type="text/javascript">
        function handleBeforeSubmit() {
          var username = $('#txtUsername').val();
          if (username) {
            $('#txtUsername').val(username.trim());
          }
        }
    </script>
</head>
<body ng-controller="LoginCtrl" style="height: 100%;">
    <div class="wrapper">
        <div class="login-container">
            <div class="logo"></div>    
            <div class="login-wrapper">
                <form:form  method="post" commandName="${commandName}" htmlEscape="true">
                    <div class="title">登 录</div>
                    
                    <div class="form-group">
                        <label>用户名</label>
                        <input placeholder="账户名/手机号/邮箱" class="form-control user" ng-model="user.name" required="" type="text" name="username" id="txtUsername">
                        <i class="input-icon user"></i>
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input placeholder="密码" class="form-control pwd" ng-model="user.password" required="" type="password"  name="password">
                        <i class="input-icon pwd"></i>
                    </div>
                    <div class="form-group">
                        <label>验证码</label>
                    </div>
                    <div class="text-right vcode" style="margin-top: -15px;">
                        <input placeholder="验证码" class="form-control pull-left" style="width:50%;" name="captcha" required="" type="text">
                        <img src = "captcha.htm" id="validcode" style="margin-right: 10px;">
                        <a href="#" onclick="javascript:changeValideCode();">刷新</a>
                    </div>
                    <div class="form-group submit-btn">
                        <input type="hidden" name="lt" value="${flowExecutionKey}" /> 
                        <input type="hidden" name="_eventId" value="submit" />
                        <button ng-disabled="disabled" type="submit" class="btn btn-lg btn-primary btn-submit btn-block" name="submit" onclick="handleBeforeSubmit()">确 定</button>
                    </div>
                    <div class="text-danger text-center ng-binding">
                        <form:errors path="*" cssClass="errors" id="status" element="div" />
                    </div>
                    <!-- <div class="text-center m-t m-b"><a href="#/access/forgotpwd" ui-sref="access.forgotpwd">忘记密码?</a></div> -->
                </form:form>
            </div>
        </div>
    </div>
    <div id="footer-wrapper" class="text-center" ng-include="'templates/block/bottom.html'" style="color: #999;font-size: 12px;"></div>
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