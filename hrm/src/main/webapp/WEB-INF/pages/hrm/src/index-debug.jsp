<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/common/ref.jsp" %>
<!DOCTYPE html>
<html ng-app="ework-ui">
<head lang="en">
    <meta  http-equiv="Content-Type" content="text/html; charset=utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>快乐学习信息化平台</title>
    <!-- STYLES -->
    <!-- STYLES -->
    <!-- build:css lib/css/main.min.css -->
    <link rel="stylesheet" type="text/css" href="components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="components/angular/angular-csp.css">
    <link rel="stylesheet" type="text/css" href="components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="components/chosen/chosen.css">
    <link rel="stylesheet" type="text/css" href="components/jquery-treetable/css/jquery.treetable.css" />
    <link rel="stylesheet" type="text/css" href="components/jquery-treetable/css/jquery.treetable.theme.default.css" />
    <link rel="stylesheet" type="text/css" href="components/jquery-treetable/css/screen.css" />
    
    <!-- endbuild -->
    <!--<link rel="stylesheet" type="text/css" href="components/bootstrap/dist/css/bootstrap-theme.min.css">-->
    <!--<link rel="stylesheet" type="text/css" href="components/jstree/dist/themes/default/style.min.css">-->

    <!-- SCRIPTS -->
    <!-- build:js lib/js/main.min.js -->
    <script type="text/javascript" src="components/jquery/dist/jquery.min.js"></script> 
    <script type="text/javascript" src="components/angular/angular.min.js"></script>
    <script type="text/javascript" src="components/bootstrap/dist/js/bootstrap.js"></script>
    <script type="text/javascript" src="components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="components/bootstrap/dist/js/fileinput.js"></script>

    <script type="text/javascript" src="components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
    <script type="text/javascript" src="components/angular-resource/angular-resource.min.js"></script>
    <script type="text/javascript" src="components/angular-ui-router/release/angular-ui-router.min.js"></script>
    <script type="text/javascript" src="components/jstree/dist/jstree.min.js"></script>
    <script type="text/javascript" src="components/chosen/chosen.jquery.js"></script>
    <script type="text/javascript" src="components/jquery-treetable/jquery.treetable.js"></script> 
    <!-- endbuild -->
    <script type="text/javascript" src="components/bootstrap/js/transition.js"></script>
    <!-- 自定义的部分，构建打包成压缩资源 -->

    <link rel="stylesheet" type="text/css" href="less/block/content.css">
    <link rel="stylesheet" type="text/css" href="less/block/left_nav.css">
    <link rel="stylesheet" type="text/css" href="less/block/modal.css">
    <link rel="stylesheet" type="text/css" href="less/block/top.css">
    <link rel="stylesheet" type="text/css" href="less/common/index.css">
    <link rel="stylesheet" type="text/css" href="less/common/style.css">
    <link rel="stylesheet" type="text/css" href="less/pub/order.css">

    <script type="text/javascript" src="js/common/ajaxfileupload.js"></script> 
    <script type="text/javascript" src="js/app.js"></script>
    <script type="text/javascript" src="js/routes.js"></script>
    <script type="text/javascript" src="js/angular-locale_zh.js"></script>
    <script type="text/javascript" src="js/controllers/master-ctrl.js"></script>
    <script type="text/javascript" src="js/controllers/date-ctrl.js"></script>
    <script type="text/javascript" src="js/controllers/toolTip-ctrl.js" desc="toolTip"></script>
    <script type="text/javascript" src="js/controllers/page-ctrl.js" desc="分页组件"></script>
    <script type="text/javascript" src="js/controllers/studentlist-ctrl.js" desc="学员组件"></script>
    <!--Start Controller公共资源部分 -->
    <script type="text/javascript" src="js/blocks/cropbox.js"></script>
    <script type="text/javascript" src="js/blocks/left-nav.js"></script>
    <script type="text/javascript" src="js/blocks/top-ctrl.js"></script>
    <script type="text/javascript" src="data/services.js"></script>
    <!-- End Controller公共资源部分 -->

    
    <!--业务Controller资源 -->
    <script type="text/javascript" src="js/controllers/hrm/dict-ctrl.js"            desc="字典管理"></script>
    <script type="text/javascript" src="js/controllers/hrm/account-ctrl.js"      desc="账户管理"></script>      
    <script type="text/javascript" src="js/controllers/hrm/employeeExt-ctrl.js"     desc="员工档案定义"></script>
    <script type="text/javascript" src="js/controllers/hrm/employee-ctrl.js"     desc="员工档案管理"></script>
    <script type="text/javascript" src="js/controllers/hrm/post-ctrl.js"     desc="岗位管理"></script>
    <script type="text/javascript" src="js/controllers/hrm/role-ctrl.js"            desc="角色管理"></script> 
    <script type="text/javascript" src="js/controllers/common/account-security.js"  desc="账户安全"></script>   
    <script type="text/javascript" src="js/controllers/common/employee-info.js"     desc="个人信息"></script> 
    <script type="text/javascript" src="js/controllers/hrm/org-ctrl.js"      		desc="组织机构"></script>
    <script type="text/javascript" src="js/controllers/hrm/changeevent-ctrl.js"     desc="人事异动"></script>
    <script type="text/javascript" src="js/controllers/hrm/changeevent_ht-ctrl.js"     desc="人事处理历史"></script>
    <script type="text/javascript" src="js/controllers/hrm/employee_check-ctrl.js"     desc="审批流程-转正考核"></script>

    
     <script type="text/javascript" src="js/common/util.js"></script>
     
    
    
</head>
<body ng-controller="MasterCtrl">
<style>
.top_bar_item_active .active_arrow {
    top: -10px;
}
</style>
<div ng-include="'templates/block/top.html'"></div>
${casServerLogoutUrl}
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