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
<title>厝边素高|教育</title>
<link rel="icon" href="/sso-server/favicon.ico" type="image/x-icon">
<link rel="dns-prefetch" href="//cdn.bootcss.com" />
<%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2y0piaocOl3cXXmgKkj25EGjSLr6XLH6"></script>--%>

<!-- STYLES -->
<link rel="stylesheet" type="text/css" href="components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="components/font-awesome/css/font-awesome.min.css">
<!-- build:libcss -->
<link rel="stylesheet" type="text/css" href="components/angular-bootstrap-calendar/dist/css/angular-bootstrap-calendar.min.css">
<link rel="stylesheet" type="text/css" href="components/angular/angular-csp.css">
<link rel="stylesheet" type="text/css" href="components/iconfont/iconfont.css">
<link rel="stylesheet" type="text/css" href="components/chosen/chosen.css">
<link rel="stylesheet" type="text/css" href="components/jquery-treetable/css/jquery.treetable.css" />
<link rel="stylesheet" type="text/css" href="components/jquery-treetable/css/jquery.treetable.theme.default.css" />
<link rel="stylesheet" type="text/css" href="components/jquery-treetable/css/screen.css" />
<link rel="stylesheet" type="text/css" href="components/mCustomScrollbar/jquery.mCustomScrollbar.min.css" />

<!-- endbuild:libcss -->


<!-- 自定义的部分，构建打包成压缩资源 -->
<!-- build:css -->
<link rel="stylesheet" type="text/css" href="less/block/content.css">
<link rel="stylesheet" type="text/css" href="less/block/left_nav.css">
<link rel="stylesheet" type="text/css" href="less/block/top.css">

<link rel="stylesheet" type="text/css" href="less/common/index.css">
<link rel="stylesheet" type="text/css" href="less/common/print.css">
<link rel="stylesheet" type="text/css" href="less/common/style.css">
<link rel="stylesheet" type="text/css" href="less/common/bootstrap-extends.css">
<link rel="stylesheet" type="text/css" href="less/common/bootstrap-overrides.css">
<link rel="stylesheet" type="text/css" href="less/common/star-rating-svg.css">

<link rel="stylesheet" type="text/css" href="less/crm/index.css">
<link rel="stylesheet" type="text/css" href="less/crm/distribute.css">
<link rel="stylesheet" type="text/css" href="less/crm/report.css">

<link rel="stylesheet" type="text/css" href="less/erp/student.css">
<link rel="stylesheet" type="text/css" href="less/erp/teacher.css">
<link rel="stylesheet" type="text/css" href="less/erp/workflow.css">
<link rel="stylesheet" type="text/css" href="less/erp/course.css">
<link rel="stylesheet" type="text/css" href="less/hrm/upload.css">
<!-- tr -->
<link rel="stylesheet" type="text/css" href="less/tr/tr-basic.css">
<!-- <link rel="stylesheet" type="text/css" href="less/tr/manualGenPaper.css"> -->
<link rel="stylesheet" type="text/css" href="less/tr/myCourse.css">
<link rel="stylesheet" type="text/css" href="less/tr/myCourseClass.css">

<!-- LESS资源 Create By baiqb@klxuexi.org -->
<link rel="stylesheet/less" href="less/variable.less">
<link rel="stylesheet/less" href="less/common/common.less">
<link rel="stylesheet/less" href="less/erp/student.less">
<link rel="stylesheet/less" href="less/erp/room.less">
<link rel="stylesheet/less" href="less/erp/order.less">
<link rel="stylesheet/less" href="less/erp/course.less">
<!-- endbuild:css -->

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
<!-- build:libjs -->
<script src="js/common/less.min.js"></script>
<script type="text/javascript" src="components/angular/angular.min.js"></script>
<script type="text/javascript" src="components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="components/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="components/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="components/jquery-treetable/jquery.treetable.js"></script>
<script type="text/javascript" src="js/common/jquery.star-rating-svg.js"></script>
<script type="text/javascript" src="components/angular-ui-router/release/angular-ui-router.min.js"></script>
<script type="text/javascript" src="components/angular-bootstrap/ui-bootstrap-tpls-2.5.0.min.js"></script>
<script type="text/javascript" src="components/angular-cookies/angular-cookies.min.js"></script>
<script type="text/javascript" src="components/angular-messages/angular-messages.min.js"></script>
<script type="text/javascript" src="components/angular-resource/angular-resource.min.js"></script>
<script type="text/javascript" src="components/angular-sanitize/angular-sanitize.min.js"></script>

<script type="text/javascript" src="components/lodash/dist/lodash.min.js"></script>
<script type="text/javascript" src="components/moment/min/moment.min.js"></script>
<script type="text/javascript" src="components/moment/locale/zh-cn.js"></script>
<script type="text/javascript" src="components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- <script type="text/javascript" src="components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script> -->
<script type="text/javascript" src="components/jstree/dist/jstree.min.js"></script>
<script type="text/javascript" src="components/Chart.js/Chart.min.js"></script>

<!-- <script type="text/javascript" src="components/mathjax/MathJax.js"></script> -->
<script type="text/javascript" src="components/angular-bootstrap-calendar/dist/js/angular-bootstrap-calendar-tpls.min.js"></script>
<!-- endbuild:libjs -->


<script type="text/javascript" src="js/common/qrcode.js"></script>


<!-- build:js -->
<script type="text/javascript" src="js/angular-locale_zh.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript" src="js/routes.js"></script>

<script type="text/javascript" src="js/common/screenfull.min.js"></script>
<script type="text/javascript" src="js/common/jbase64.js"></script>
<script type="text/javascript" src="js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/common/PageObj.js"></script>
<script type="text/javascript" src="js/common/agPageObject.js"></script>
<script type="text/javascript" src="js/common/timeLine.js"></script>
<script type="text/javascript" src="js/common/angular-file-upload.min.js"></script>
<!-- c-lodop打印-->
<script type="text/javascript" src="js/common/LodopFuncs.js"></script>
<script type="text/javascript" src="js/common/lodop_print.js"></script>
<script type="text/javascript" src="js/common/printUtil.js"></script>
<script type="text/javascript" src="js/common/msgbox.js"></script>
<script type="text/javascript" src="js/common/superClipBoard.min.js"></script>


<script type="text/javascript" src="data/services.js"></script>


<script type="text/javascript" src="js/services/erp_printService.js"></script>
<script type="text/javascript" src="js/services/erp_httpInterceptors.js"></script>


<!--Start Controller公共资源部分 -->
<script type="text/javascript" src="js/blocks/cropbox.js"></script>
<script type="text/javascript" src="js/blocks/left-nav.js"></script>
<script type="text/javascript" src="js/blocks/top-ctrl.js"></script>
<script type="text/javascript" src="js/blocks/avatar-upload.js"></script>
<!-- modal -->
<script type="text/javascript" src="js/blocks/modal/course-queue-detail.modal.js"></script>
<script type="text/javascript" src="js/blocks/modal/image-upload.modal.js"></script>
<script type="text/javascript" src="js/blocks/modal/course-multiple-select.modal.js"></script>
<script type="text/javascript" src="js/blocks/modal/order_workflow_modal.js"  ></script>


<!-- 后台Controller资源 -->
<script type="text/javascript" src="js/directives/widget.js"  ></script>
<script type="text/javascript" src="js/directives/ng-pagination.js"  ></script>
<script type="text/javascript" src="js/directives/ng-enter.js"  ></script>
<script type="text/javascript" src="js/directives/bs-switch.js"  ></script>
<script type="text/javascript" src="js/directives/kl-steps.js"  ></script>
<script type="text/javascript" src="js/directives/kl-timepicker.js"  ></script>
<script type="text/javascript" src="js/directives/kl-search-modal.js"  ></script>
<script type="text/javascript" src="js/directives/erp/kl-teacher-dropdown.js"  ></script>
<script type="text/javascript" src="js/directives/erp/kl-subject-dropdown.js"  ></script>
<script type="text/javascript" src="js/directives/erp/kl-course-dropdown.js"  ></script>
<script type="text/javascript" src="js/directives/erp/kl-coupon-dropdown.js"  ></script>
<script type="text/javascript" src="js/directives/erp/kl-multi-tags.js"  ></script>
<script type="text/javascript" src="js/directives/erp/kl-header-goback.js"  ></script>
 <script type="text/javascript" src="js/directives/crm/crm-bu-select.js"></script>
<script type="text/javascript" src="js/directives/crm/crm-branch-select.js"></script>
<script type="text/javascript" src="js/directives/crm/crmbranch.js"></script>
<script type="text/javascript" src="js/directives/image-crop.js"  ></script>
<script type="text/javascript" src="js/directives/date-picker-popup.js"  ></script>
<script type="text/javascript" src="js/directives/modal.js"  ></script>
<script type="text/javascript" src="js/directives/date-picker-range.js"  ></script>
<script type="text/javascript" src="js/directives/branch.js"  ></script>
<script type="text/javascript" src="js/directives/widget-body.js"    ></script>
<script type="text/javascript" src="js/directives/widget-footer.js"    ></script>
<script type="text/javascript" src="js/directives/widget-header.js"    ></script>
<script type="text/javascript" src="js/directives/loading.js"    ></script>
<script type="text/javascript" src="js/directives/latex.directive.js"     ></script>
<!-- 基础元素指令 -->
<script type="text/javascript" src="js/directives/basic/select/kl-bu-select.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-branch-select.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-timeseason-select.js"></script>
<script type="text/javascript" src="js/directives/basic/file/kl-image-upload.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-grade-select.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-subject-select.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-teacher-group-select.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-coupon-select.js"></script>
<script type="text/javascript" src="js/directives/basic/select/kl-dict-select.js"></script>
<script type="text/javascript" src="js/directives/kl-table.js"></script>


<script type="text/javascript" src="js/controllers/edu/edu_services.js"  ></script>
<script type="text/javascript" src="js/controllers/edu/index.js"  ></script>

<script type="text/javascript" src="js/controllers/master-ctrl.js"></script>
<script type="text/javascript" src="js/controllers/date-ctrl.js"></script>
<script type="text/javascript" src="js/controllers/toolTip-ctrl.js" desc="toolTip"></script>
<script type="text/javascript" src="js/controllers/page-ctrl.js" desc="分页组件"></script>
<%--<script type="text/javascript" src="js/controllers/common/account-ctrl.js"  ></script>--%>
<script type="text/javascript" src="js/controllers/common/account-security.js" ></script>
<script type="text/javascript" src="js/controllers/common/employee-info.js" ></script>

<!-- erp -->
<script type="text/javascript" src="js/controllers/erp/erp_service.js"     ></script>
<script type="text/javascript" src="js/controllers/erp/student/students.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/newStudent.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentsDistribution.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-order.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-wfd.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-schedule.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-center-info.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-center-contact.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-center-counselor.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-bjk.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-wfd.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-ydy.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-attendance.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-listening.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-account.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-accountRecharge.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-accountTransfer.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-accountClaim.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/team_acount_transfer.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-order-detail.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-order-detail-delModal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-order-detail-lockModal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-order-detail-unLockModal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-scheduling.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-scheduling-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-schedulingEdit-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-score.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentMine.js"  ></script>
<!-- <script type="text/javascript" src="js/controllers/erp/student/studentsMineYdy.js"  ></script> -->
<script type="text/javascript" src="js/controllers/erp/student/studentTraceInfo.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentTraceInfoAdd.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/studentIndex-integral.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/invoiceTrace.js"  ></script>

<!-- 学管师耗课分析 -->
<script type="text/javascript" src="js/controllers/erp/student/student-manager-bu.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/student-manager-branch.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/student-manager.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/student-manager-student.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/al_student_status.js"  desc="艾伦学生状态报表学生明细"></script>

<!-- 账户单据 -->
<script type="text/javascript" src="js/controllers/erp/student/accountChangeBills.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/student/withDrawal.js"  ></script>

<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-orders.js"  desc="报班业务"></script>
<script type="text/javascript" src="js/controllers/erp/orders/orders-queue.js"  desc="排号业务"></script>

<script type="text/javascript" src="js/controllers/erp/orders/arrearage_order.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-privilegerule.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-privilegecriteria.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-couponinfo.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-couponOnline.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-couponrulerel.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-activityinfo.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-activityBanner.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/ordersIndex-activityOnline.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/order_change_transfer.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/order_change_refund.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/order_change_frozen.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/torder.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/refund.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/orders/order_frozen_mgr.js"  desc="锁定订单管理"></script>
<script type="text/javascript" src="js/controllers/erp/orders/order_frozen_mgr-carryForwardModal.js"  desc="锁定订单结转"></script>
<script type="text/javascript" src="js/controllers/erp/course/course_listening.js"  desc="试听详情"></script>
<script type="text/javascript" src="js/controllers/erp/orders/order_unpay.js"  desc="欠费订单"></script>

<script type="text/javascript" src="js/controllers/erp/teacher/course_schedule.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/teacher-search.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/teacher-index.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/teacher-new.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/teacher-group.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/teacher-group-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/attendance_group.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/attendance_group-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/excelInput.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/teacher/excelInput-modal.js"  ></script>

<script type="text/javascript" src="js/controllers/erp/course/course_scheduling.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/course.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/course-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/mtcourse.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/mtcourse-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/fileUpload_bjk.js"  ></script>
 <script type="text/javascript" src="js/controllers/erp/course/fileUpload_bjk-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/fileUpload_wfd.js"  ></script>
 <script type="text/javascript" src="js/controllers/erp/course/fileUpload_wfd-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/fileUpload_ydy.js"  ></script>
 <script type="text/javascript" src="js/controllers/erp/course/fileUpload_ydy-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/wfd.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/wfd_combo.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/wfd_combo-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/wfd_combo_input.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/wfd_combo_input-modal.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/ydy.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/ydy-ladder.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/course/ydy-ladder-modal.js"  ></script>

<script src="js/controllers/erp/course/course_scheduling_ydy_apply.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_apply_add.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_apply_detail.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_approval.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_match.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_match_edit.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_process.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_record.js"></script>
<script src="js/controllers/erp/course/course_scheduling_ydy_print.js"></script>

<script type="text/javascript" src="js/controllers/erp/course/course_scheduling_change.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_bjk.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_bjk_details.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_bjk_students.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_bjk_makeup.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_wfd.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_wfd_details.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/attendance/attendance_ydy.js"  ></script>

<!-- 财务 -->
<script type="text/javascript" src="js/controllers/erp/finance/financeIndex-order.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/finance/frozen.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/finance/invoice.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/finance/epay_wap.js"  ></script>
<!-- 文件上传 -->
<!-- 注释文件上传，无该JS文件， @modify baiqb@klxuexi.org -->
<!-- <script type="text/javascript" src="js/controllers/excels/FileUpload-Ctrl.js"  ></script> -->
<!-- 接口日志 -->
<script type="text/javascript" src="js/controllers/erp/eai/eaiLog.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/eai/double_teacher_sync.js"  ></script>


<!-- 工作流 -->
<script type="text/javascript" src="js/controllers/erp/workflow_service.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/workflow/workflow-mgrment.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/workflow/workflow-ProcessDefinition.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/workflow/workflow-ProcessNode.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/workflow/workflow-ProcessTask.js"  ></script>

<script type="text/javascript" src="js/controllers/erp/workflow/task_appication.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/workflow/task_branchappication.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/workflow/task_todo.js"  ></script>

<!-- 基础数据 -->
<script type="text/javascript" src="js/controllers/erp/dict/subject.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/dict/time_season.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/dict/grade.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/common/data_school.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/common/invoice_company.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/common/device.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/common/arranger.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/common/arranger_edit.js"  ></script>
<script type="text/javascript" src="js/controllers/erp/common/data_company_account.js"></script>
<script type="text/javascript" src="js/controllers/erp/dict/coopOrg.js"  ></script>

<!-- 系统 -->
<script type="text/javascript" src="js/controllers/erp/sysmgr/notice.js"     desc="通知管理"></script>
<script type="text/javascript" src="js/controllers/erp/sysmgr/noticeListModal.js"     desc="通知管理"></script>
<script type="text/javascript" src="js/controllers/erp/sysmgr/notice_detail.js"     desc="通知管理明细"></script>

<!-- 教室信息 -->
<script type="text/javascript" src="js/controllers/erp/room/room.js"     desc="教室信息"></script>
<script type="text/javascript" src="js/controllers/erp/room/course_room.js"     desc="教室信息"></script>
<script type="text/javascript" src="js/controllers/erp/room/room_arrange.js"     desc="教室信息"></script>
<script type="text/javascript" src="js/controllers/erp/room/room_arrange_details.js"     desc="教室信息"></script>
<!--教室摄像头信息-->
<script type="text/javascript" src="js/controllers/erp/camera/camera.js"     desc="摄像头信息"></script>
<script type="text/javascript" src="js/controllers/erp/camera/course_video.js"     desc="课次视频信息"></script>


<!-- 延课 -->
<script type="text/javascript" src="js/controllers/erp/delayCourse/delayCourse.js"     desc="延课信息"></script>
<script type="text/javascript" src="js/controllers/erp/delayCourse/mtDelayCourse.js"     desc="延课信息"></script>
<script type="text/javascript" src="js/controllers/erp/delayCourse/delayRecord.js"     desc="延课信息"></script>
<script type="text/javascript" src="js/controllers/erp/delayCourse/delayRecordDetail.js"     desc="延课信息"></script>

<!--HRM 业务Controller资源 -->
<script type="text/javascript" src="js/controllers/hrm/hrm_services.js"            desc="字典管理"></script>
<script type="text/javascript" src="js/controllers/hrm/dict-ctrl.js"            desc="字典管理"></script>
<script type="text/javascript" src="js/controllers/hrm/account-ctrl.js"      desc="账户管理"></script>
<script type="text/javascript" src="js/controllers/hrm/employeeExt-ctrl.js"     desc="员工档案定义"></script>
<script type="text/javascript" src="js/controllers/hrm/employee-list-modal.js"     desc="员工列表模态框"></script>
<script type="text/javascript" src="js/controllers/hrm/employee-ctrl.js"     desc="员工档案管理"></script>
<script type="text/javascript" src="js/controllers/hrm/post-ctrl.js"     desc="岗位职务管理"></script>
<script type="text/javascript" src="js/controllers/hrm/post-level-ctrl.js"     desc="岗位层级管理"></script>
<script type="text/javascript" src="js/controllers/hrm/post-rank-ctrl.js"     desc="岗位职级管理"></script>
<script type="text/javascript" src="js/controllers/hrm/post-duty-ctrl.js"     desc="岗位管理"></script>
<script type="text/javascript" src="js/controllers/hrm/role-ctrl.js"            desc="角色管理"></script>
<script type="text/javascript" src="js/controllers/hrm/org-ctrl.js"             desc="组织机构"></script>
<script type="text/javascript" src="js/controllers/hrm/changeevent-ctrl.js"     desc="人事异动"></script>
<script type="text/javascript" src="js/controllers/hrm/changeevent_ht-ctrl.js"     desc="人事处理历史"></script>
<script type="text/javascript" src="js/controllers/hrm/employee_check-ctrl.js"     desc="审批流程-转正考核"></script>
<script type="text/javascript" src="js/controllers/hrm/employeeInfo-ctrl.js"     desc="员工档案"></script>
<script type="text/javascript" src="js/controllers/hrm/employeeInfoAdd-ctrl.js"     desc="员工档案"></script>
<script type="text/javascript" src="js/controllers/hrm/employeeInfoSelf-ctrl.js"     desc="员工档案"></script>
<script type="text/javascript" src="js/controllers/hrm/employeeInfoUpdate-ctrl.js"     desc="员工档案"></script>
<script type="text/javascript" src="js/controllers/hrm/employee-uploadimg.js"     desc="员工档案"></script>
<script type="text/javascript" src="js/controllers/hrm/employee-uploadimg-self.js"     desc="员工档案"></script>

<!-- 教研 Ctrl resource -->
<!-- <script type="text/javascript" src="js/controllers/tr/manualGenPaper.js" desc="手动组卷"></script> -->
<!-- <script type="text/javascript" src="js/controllers/tr/paperLibrary.js" desc="试卷库"></script> -->
<!-- <script type="text/javascript" src="js/controllers/tr/myPaper.js" desc="我的试卷"></script> -->
<script type="text/javascript" src="js/controllers/tr/workManagement.js" desc="作业管理"></script>
<script type="text/javascript" src="js/controllers/tr/workManagementSetting.js" desc="我的课次"></script>
<script type="text/javascript" src="js/controllers/tr/modal/class-work-modal.js" desc="选择课次modal"></script>
<script type="text/javascript" src="js/controllers/tr/modal/mgt-work-modal.js" desc="作业管理试卷关联"></script>
<script type="text/javascript" src="js/controllers/tr/myCourse.js" desc="我的课程"></script>
<script type="text/javascript" src="js/controllers/tr/myCourseClass.js" desc="我的课次"></script>
<script type="text/javascript" src="js/controllers/tr/workManagement/add.js" desc="作业添加"></script>
<script type="text/javascript" src="js/controllers/tr/workManagement/edit.js" desc="作业编辑"></script>
<script type="text/javascript" src="js/controllers/tr/workManagement/view.js" desc="作业浏览"></script>
<!-- 暂时添加一类双师2.0, 完全迁移后去掉双师1.0 -->
<script type="text/javascript" src="js/controllers/tr/workManagement2.js" desc="作业管理2"></script>
<script type="text/javascript" src="js/controllers/tr/workManagement2/add.js" desc="作业添加2"></script>
<script type="text/javascript" src="js/controllers/tr/workManagement2/edit.js" desc="作业编辑2"></script>
<script type="text/javascript" src="js/controllers/tr/workManagement2/view.js" desc="作业浏览2"></script>
<!-- 报表Controller资源 -->
<script type="text/javascript" src="js/controllers/report/settings.js"  desc="报表任务设置"></script>
<script type="text/javascript" src="js/controllers/report/settings-modal.js"  desc="报表任务设置管理"></script>
<script type="text/javascript" src="js/controllers/report/result.js"  desc="报表任务运行状态"></script>
<script type="text/javascript" src="js/controllers/report/report_service.js"  desc="报表服务注册"></script>
<script type="text/javascript" src="js/controllers/report/common/account.js"  desc="账户剩余表"></script>
<script type="text/javascript" src="js/controllers/report/common/accountFlow.js"  desc="账户流水表"></script>

<script type="text/javascript" src="js/controllers/report/common/performance_details.js"  desc="业绩明细表"></script>
<script type="text/javascript" src="js/controllers/report/common/account_recharge_cash.js"  desc="充值取现表"></script>
<script type="text/javascript" src="js/controllers/report/common/account_cashier.js"  desc="出纳信息表"></script>
<script type="text/javascript" src="js/controllers/report/common/order_change_report.js"  desc="报冻退转报表"></script>
<script type="text/javascript" src="js/controllers/report/common/performance_sum.js"  desc="业绩汇总报表"></script>
<script type="text/javascript" src="js/controllers/report/common/surplus_amount_fee.js"  desc="学员剩余课时费用表"></script>
<script type="text/javascript" src="js/controllers/report/common/attendance_report.js"  desc="考勤消耗表"></script>
<script type="text/javascript" src="js/controllers/report/common/settlement_performance_details.js"  desc="业绩结算表"></script>
<script type="text/javascript" src="js/controllers/report/common/settlement_attendance_report.js"  desc="考勤结算表"></script>
<script type="text/javascript" src="js/controllers/report/bjk/teacher_workload_report.js"  desc="培英班教师工作量表"></script>
<script type="text/javascript" src="js/controllers/report/wfd/teacher_attend_report.js"  desc="晚辅导教师考勤消耗表"></script>

<!-- Components 组件 -->
<!-- 公共组件 -->
<script type="text/javascript" src="js/components/base/datetime/multiDatePicker.js"></script>

<!-- 教务平台 1对1学员排课 -->
<script type="text/javascript" src="js/components/erp/course/stuOrderList.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedApplyBrief.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedApplyDetail.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedBaseInfoMatch.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedBaseInfoAnalysis.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedBaseInfoPlan.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedBaseInfoResult.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedByPeriod.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedByTime.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedByMatch.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedPlanList.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedProcess.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedResult.js"></script>
<script type="text/javascript" src="js/components/erp/course/stuSchedTimeTable.js"></script>

<script type="text/javascript" src="js/components/erp/teacher/typeahead.js"></script>
<script type="text/javascript" src="js/components/erp/student/typeahead.js"></script>
<script type="text/javascript" src="js/components/erp/teacher/teacherSchedule.js"></script>

<!-- endbuild:js -->

<%--<script type="text/x-mathjax-config">--%>
    <%--// var mathId = document.getElementById("tr-workMgt-view");--%>
    <%--MathJax.Hub.Config({--%>
        <%--showProcessingMessages: false,--%>
        <%--messageStyle: "none",--%>
        <%--extensions: ["tex2jax.js"],--%>
        <%--jax: ["input/TeX", "output/HTML-CSS"],--%>
        <%--tex2jax: {--%>
            <%--inlineMath: [ ['$','$'], ["\\(","\\)"] ],--%>
            <%--displayMath: [ ['$$','$$'], ["\\[","\\]"] ],--%>
            <%--// skipTags: ['script', 'noscript', 'style', 'textarea', 'pre','code','a'],--%>
        <%--},--%>
        <%--"showMathMenu": false,--%>
        <%--"HTML-CSS": {--%>
            <%--availableFonts: ["STIX","TeX"],--%>
            <%--showMathMenu: false--%>
        <%--},--%>
        <%--"fast-preview": {--%>
            <%--"disabled": true--%>
        <%--}--%>
    <%--});--%>
    <%--MathJax.Hub.Queue(["Typeset", MathJax.Hub]);--%>
<%--</script>--%>
<%-- <script src="//cdn.bootcss.com/mathjax/2.7.0/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script> --%>
<%--<script type="text/javascript" async src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_HTML-full"></script>--%>
</body>
</html>
