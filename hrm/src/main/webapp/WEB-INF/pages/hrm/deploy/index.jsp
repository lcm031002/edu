<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="ework-ui">
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>快乐学习信息化平台</title>
    <!-- STYLES -->
    <link rel="stylesheet" href="lib/css/main.min.css"/>
    <!--<link rel="stylesheet" type="text/css" href="components/bootstrap/dist/css/bootstrap-theme.min.css">-->
    <!--<link rel="stylesheet" type="text/css" href="components/jstree/dist/themes/default/style.min.css">-->

    <!-- SCRIPTS -->
    <script src="lib/js/main.min.js"></script>
    <!--<script type="text/javascript" src="components/bootstrap/js/transition.js"></script>-->
    <!-- 自定义的部分，构建打包成压缩资源 -->

    <link rel="stylesheet" type="text/css" href="css/eWorkUI.min.css">
    <script type="text/javascript" src="js/eWorkUI.min.js"></script>
    <script type="text/javascript" src="data/services.js"></script>
</head>
<body ng-controller="MasterCtrl">
<div ng-include="'templates/block/top.html'"></div>

<!-- Main Content -->
<div class="content">
    <div class="col-sm-4 col-md-3 col-lg-2 left_nav"  ng-if="curSystem.key!='index'">
        <div ng-include="'templates/block/left_nav.html'"></div>
    </div>
    <div class="col-sm-8 col-md-9 col-lg-10 right_content"  ng-if="curSystem.key!='index'">
        <div ui-view></div>
    </div>

    <div ng-if="curSystem.key=='index'"  class="right_content" >
        <div ui-view></div>
    </div>
</div>


</body>
</html>