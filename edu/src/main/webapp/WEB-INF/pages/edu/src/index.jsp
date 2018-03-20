<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html ng-app="ework-ui" lang="en">
<head>
<meta  http-equiv="Content-Type" content="text/html; charset=utf-8" >
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable = no">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-10">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Cache-Control" content="Max-stale=0">
<title>快乐学习|教育</title>
<link rel="icon" href="/sso-server/favicon.ico" type="image/x-icon">
<link rel="dns-prefetch" href="//cdn.bootcss.com" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2y0piaocOl3cXXmgKkj25EGjSLr6XLH6"></script>

<!-- STYLES -->
<link rel="stylesheet" type="text/css" href="components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="components/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="dist/css/main.min.css">


<!-- 自定义的部分，构建打包成压缩资源 -->
<link rel="stylesheet" type="text/css" href="dist/css/eWorkUI.min.1515571926783.css">

</head>
<body ng-controller="MasterCtrl" class="klxx">
<button class="btn btn-default" ng-click="onFullScreenToggle()"
    style="position:fixed; bottom: 45px; right: 45px; z-index: 9999;">
    <i class="glyphicon glyphicon-fullscreen"></i>
</button>
<div ng-include="topBodyURL"></div>
<!-- Main Content -->
<div class="content">
    <div class="left_nav"  ng-hide="curSystem.key=='index'">
        <div ng-include="leftNavURL"></div>
    </div>
    <div class="{{curSystem.key=='index' ? 'index_content':'right_content'}}"  >
        <div ui-view></div>
    </div>
</div>
<c:forEach items="${requestScope.paramList}" var="paramObj">
    <input type="hidden" value="${paramObj['value']}" id="rootIndex_${paramObj['name']}"/>
</c:forEach>
<!-- SCRIPTS -->

<script type="text/javascript" src="components/WdatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="dist/js/main.min.js"></script>


<script type="text/javascript" src="js/common/qrcode.js"></script>


<script type="text/javascript" src="dist/js/eWorkUI.min.1515571926783.js"></script>

<script type="text/x-mathjax-config">
    // var mathId = document.getElementById("tr-workMgt-view");
    MathJax.Hub.Config({
        showProcessingMessages: false,
        messageStyle: "none",
        extensions: ["tex2jax.js"],
        jax: ["input/TeX", "output/HTML-CSS"],
        tex2jax: {
            inlineMath: [ ['$','$'], ["\\(","\\)"] ],
            displayMath: [ ['$$','$$'], ["\\[","\\]"] ],
            // skipTags: ['script', 'noscript', 'style', 'textarea', 'pre','code','a'],
        },
        "showMathMenu": false,
        "HTML-CSS": {
            availableFonts: ["STIX","TeX"],
            showMathMenu: false
        },
        "fast-preview": {
            "disabled": true
        }
    });
    MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
</script>
<%-- <script src="//cdn.bootcss.com/mathjax/2.7.0/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script> --%>
<script type="text/javascript" async src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_HTML-full"></script>
</body>
</html>
