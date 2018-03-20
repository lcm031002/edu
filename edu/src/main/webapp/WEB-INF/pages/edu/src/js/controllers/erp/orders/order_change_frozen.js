/**
 * Created by Liyong.zhu on 2017/2/12.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_orderChangeFrozenController', [
        '$scope',
        '$log',
        '$uibMsgbox', // 消息提示框服务，其他服务按需引入
        'erp_studentsService',
        'erp_studentOrderCourseService',
        'erp_orderManagerService',
        'erp_courseService',
        'erp_studentBuOrgsService',
        'erp_gradeService',
        'erp_subjectService',
        'erp_timeSeasonService',
        'erp_courseTimesService',
        'erp_orderChangeService',
        erp_orderChangeFrozenController]);

function erp_orderChangeFrozenController(
    $scope,
    $log,
    $uibMsgbox,
    erp_studentsService,
    erp_studentOrderCourseService,
    erp_orderManagerService,
    erp_courseService,
    erp_studentBuOrgsService,
    erp_gradeService,
    erp_subjectService,
    erp_timeSeasonService,
    erp_courseTimesService,
    erp_orderChangeService) {

    //学员信息
    $scope.student = undefined;
    $scope.studentList = [];
    $scope.businessType = 1;
    $scope.productLine = 1; // 产品线 1-培英精品班 2-个性化 11-佳音
    
    $scope.course = {
    		wfdTimes:0
    }
    $scope.formulaTypeList = [
        {   id:1, name:'标准冻结', description:'折后总价 - 原单价 * （ 总课时 - 冻结课时 ） = 冻结金额'},
        {   id:2, name:'VIP冻结',  description:'当前单价 * 冻结课时 + 预结转 = 冻结金额'}
    ];

    $scope.selectedFormulaInfo = {
        selectedFormula: $scope.formulaTypeList[0]
    };
    
    $scope.premiumType = undefined;//校准退费;

    /**
     * 下一步、上一步
     * @param before
     * @param next
     */
    $scope.nextStep = function(before,next) {
        if (next == 2) {
        	$scope.refundTab='bjk';
            if ($scope.productLine == 2) {
                $scope.businessType = 2;
                $scope.refundTab = 'ydy';
            }
        	if ($scope.bizType) {
                $scope.businessType = $scope.bizType;
        		$scope.refundTab = ($scope.bizType == 3) ? 'wfd' : (($scope.bizType == 2) ? 'ydy' : 'bjk');
        	}
        	$scope.selectRefundCourseTab($scope.refundTab);
        }
        if (next == 3) {
            if($scope.premiumType == undefined){
            	$scope.premiumTypeSelect = false; 
            }else if($scope.premiumType == 1){
            	$scope.selectedFormulaInfo.selectedFormula = $scope.formulaTypeList[0];
                $scope.selectedFormulaInfo.selectedFormula.description = '当前单价 * 冻结课时 = 冻结金额';
            	$scope.premiumTypeSelect = true;
            }else if($scope.premiumType == 2){
            	$scope.selectedFormulaInfo.selectedFormula = $scope.formulaTypeList[1];
            	$scope.premiumTypeSelect = true;
            }
        	if ($scope.productLine == 2) {
                $scope.premiumTypeSelect = true
                $scope.selectedFormulaInfo.selectedFormula = $scope.formulaTypeList[1];
            }
            createConfirmInfo();
        }
        $scope.step = next;
    }

    $scope.changeFormula = function(){
        createConfirmInfo();
    }

    function createConfirmInfo(){
        if($scope.selectedFormulaInfo.selectedFormula){
            if($scope.selectedFormulaInfo.selectedFormula.id == 1){
                // 当前单价：0元订单，当前单价取折扣后的单价，否则取原单价
                var currentUnitPrice = ($scope.selectedRefundCourse.discount_sum_price > 0) ? $scope.selectedRefundCourse.former_unit_price : $scope.selectedRefundCourse.discount_unit_price;
                // 不是第一次，则算法不一样
                if($scope.premiumTypeSelect == true) {
                    $scope.selectedFormulaInfo.result =
                        (currentUnitPrice * $scope.refundCourseTimesList.length);

                    $scope.selectedFormulaInfo.formulaDetailInfo = ""
                        + currentUnitPrice
                        + " * "
                        + $scope.refundCourseTimesList.length
                        + " = "
                        + (currentUnitPrice * $scope.refundCourseTimesList.length);
                } else {
                	if($scope.selectedRefundCourse.root_course_id == undefined){
                        $scope.selectedFormulaInfo.result =
                            $scope.selectedRefundCourse.discount_sum_price - currentUnitPrice * ($scope.selectedRefundCourse.course_total_count - $scope.refundCourseTimesList.length);

                        $scope.selectedFormulaInfo.formulaDetailInfo = ""
                            + $scope.selectedRefundCourse.discount_sum_price
                            + " - "
                            + currentUnitPrice
                            + " * ( "
                            + $scope.selectedRefundCourse.course_total_count
                            + " - "
                            + $scope.refundCourseTimesList.length
                            + " ) "
                            + "="+ $scope.selectedFormulaInfo.result;
                	}else{
                        $scope.selectedFormulaInfo.result =
                            $scope.selectedRefundCourse.root_discount_sum_price - currentUnitPrice * ($scope.selectedRefundCourse.root_course_total_count - $scope.refundCourseTimesList.length);

                        $scope.selectedFormulaInfo.formulaDetailInfo = ""
                            + $scope.selectedRefundCourse.root_discount_sum_price
                            + " - "
                            + currentUnitPrice
                            + " * ( "
                            + $scope.selectedRefundCourse.root_course_total_count
                            + " - "
                            + $scope.refundCourseTimesList.length
                            + " )"
                            +"= "+ $scope.selectedFormulaInfo.result;
                	}
                		

                }
            } else if($scope.selectedFormulaInfo.selectedFormula.id == 2){
                $scope.selectedFormulaInfo.result =
                    ($scope.selectedRefundCourse.discount_unit_price * $scope.refundCourseTimesList.length)+ Math.floor(($scope.selectedRefundCourse.manage_fee/$scope.selectedRefundCourse.course_surplus_count) * $scope.refundCourseTimesList.length);

                $scope.selectedFormulaInfo.formulaDetailInfo = ""
                    + $scope.selectedRefundCourse.discount_unit_price
                    + " * "
                    + $scope.refundCourseTimesList.length
                    + " + "
                    + Math.floor(($scope.selectedRefundCourse.manage_fee/$scope.selectedRefundCourse.course_surplus_count) * $scope.refundCourseTimesList.length)
                    + " = "
                    + $scope.selectedFormulaInfo.result;
            }
        }
    }

    $scope.checkBuckleUp = function(){
        if($scope.selectedFormulaInfo.checkBuckleUp){
            $scope.selectedFormulaInfo.checkBuckleUp = false;
        }else{
            $scope.selectedFormulaInfo.checkBuckleUp = true;
        }
    }

    /**
     * 选择学员
     * @param student
     */
    $scope.checkedStudent = function(student){
        if(student.checked){
            student.checked = false;
            $scope.student = undefined;
            $scope.productLine = 1;
        }else{
            student.checked = true;
            $scope.student = student;
            $scope.productLine = student.product_line;

            if($scope.curStudent && $scope.curStudent.id != student.id){
                $scope.curStudent.checked = false;
            }
            $scope.curStudent = $scope.student;
        }
    }
    function initial(){
        $('title').text('冻结 | 快乐学习');
        $scope.studentId = $("#rootIndex_studentId").val();
        $scope.step = 1;

        $scope.queryStudents = queryStudents;
        $scope.studentId = $("#rootIndex_studentId").val();
        queryStudents();

        $scope.orderDetailId = $("#rootIndex_orderDetailId").val();
        $scope.bizType = $("#rootIndex_bizType").val();
    }

    $scope.studentQueryInfo = {};

    function queryStudents(){
        var param = {};
        if($scope.studentId){
            param.studentId = $scope.studentId;
        }
        param.pageSize = 10;
        param.searchInfo = $scope.studentQueryInfo.searchInfo;
        $scope.isQueryStudent = 'isQueryStudent';
        $scope.student = undefined;
        $scope.curStudent = $scope.student;
        $scope.studentList = [];
        erp_studentsService.query(
            param,
            function(resp){
                $scope.isQueryStudent = '';
                if(!resp.error){
                    $scope.studentList = [];
                    if(resp.data && resp.data.length > 0) {
                        $scope.student = resp.data[0];
                        $scope.productLine = $scope.student.product_line;
                        if ($scope.studentId) {
                            $scope.student.checked = true;
                            $scope.curStudent = $scope.student;
                            $scope.studentList.push($scope.student);
                        } else {
                            $scope.student = null;
                            $scope.studentList = resp.data;
                        }
                    }else{
                        $scope.studentList = resp.data;
                    }
                }else{
                    $uibMsgbox.alert(resp.message);
                }
            });
    }

    $scope.queryCourse =  function (){

        var param = {
            studentId : $scope.curStudent.id,
            businessType:$scope.businessType
        };

        erp_studentOrderCourseService.queryOrderCourse(param,function(resp){

            $scope.isQueryBjk = '';

            if(!resp.error){

                $scope.orderBJK = resp.data;
                if($scope.orderDetailId){
                    $.each($scope.orderBJK,function(i,r){
                        if(r.id == $scope.orderDetailId){
                            $scope.checkRefundCourse($scope.orderBJK,r);
                        }
                    })
                }
            }else{

                alert(resp.message);

            }
        });

    }
    
    $scope.queryWfdCourse =  function (){

        var param = {
            studentId : $scope.curStudent.id,
            businessType:$scope.businessType
        };

        erp_studentOrderCourseService.queryOrderCourse(param,function(resp){

            $scope.isQueryWfd = '';

            if(!resp.error){

                $scope.orderWFD = resp.data;
                if($scope.orderDetailId){
                    $.each($scope.orderWFD,function(i,r){
                        if(r.id == $scope.orderDetailId){
                            $scope.checkRefundCourse($scope.orderWFD,r);

                        }
                    })
                }
            }else{

                alert(resp.message);

            }
        });

    }
    
    $scope.queryYdyCourse =  function (){

        var param = {
            studentId : $scope.curStudent.id,
            businessType:$scope.businessType
        };

        erp_studentOrderCourseService.queryOrderCourse(param,function(resp){

            $scope.isQueryYdy = '';

            if(!resp.error){

                $scope.orderYDY = resp.data;
                if($scope.orderDetailId){
                    $.each($scope.orderYDY,function(i,r){
                        if(r.id == $scope.orderDetailId){
                            $scope.checkRefundCourse($scope.orderYDY,r);
                            
                        }
                    })
                }
            }else{

                alert(resp.message);

            }
        });

    }

    $scope.selectRefundCourseTab = function(key){
    	if($scope.selectedRefundCourse) {
            $uibMsgbox.warn("已选定冻结课程，不可切换页签！");
            return;
        }
        $scope.refundTab = key;
        if(key=='bjk'){
            $scope.businessType = 1;
            $scope.queryCourse();
        }else if(key=='ydy'){
            $scope.businessType = 2;
            if(!$scope.orderyDYD){
                $scope.queryYdyCourse();
            }
        }else if(key=='wfd'){
            $scope.businessType = 3;
            if(!$scope.orderWFD){
                $scope.queryWfdCourse();
            }
        }
    }
    $scope.selectedRefundCourse = undefined;

    $scope.checkRefundCourse = function(courseList,course){
        $.each(courseList,function(i,cs){
            if(cs.ID != course.ID){
                cs.checked = undefined;
                $scope.selectedRefundCourse = undefined;
            }
        });

        if(course.checked){
            course.checked = false;
            $scope.selectedRefundCourse = undefined;
        }else{
            $scope.premiumType = course.premium_type;
        
            if (course.invoice_status && course.invoice_status == 1) {
                $uibMsgbox.confirm("当前订单已经开出发票，是否继续冻结？", function (result) {
                    if (result != 'yes') {
                        return;
                    }
                    course.checked = true;
                    $scope.selectedRefundCourse = course;
                    queryOrderChangeTimesInfo();
                });
            } else {
                course.checked = true;
                $scope.selectedRefundCourse = course;
                queryOrderChangeTimesInfo();
            }
        }

    }

    function queryOrderChangeTimesInfo(){
        var param = {};
        param.orderDetailId = $scope.selectedRefundCourse.id;
        $scope.isLoadingCourseTimesPanel = 'isLoadingCourseTimesPanel';
        $scope.selectedRefundCourse.orderChangeCourseTimes = undefined;
        erp_orderManagerService.orderCourseSurplusCount(param,function(resp){
            $scope.isLoadingCourseTimesPanel = '';
            if(!resp.error){
                $scope.selectedRefundCourse.orderCourseSurplusCount = resp.data;
            }else{
                alert(resp.message);
            }
        });
    }
    $scope.refundCourseTimesList = [];
    $scope.checkRefundCourseTimes =  function(attendance){
        if(attendance.checked){
            attendance.checked = false;
        }else{
            attendance.checked = true;
        }
        $scope.refundCourseTimesList = [];
        $.each($scope.selectedRefundCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.checked){
                $scope.refundCourseTimesList.push(tfoCourse);
            }
        });
    }

    $scope.checkAllRefundCourseTimes = function(){
        $scope.refundCourseTimesList = [];
        $.each($scope.selectedRefundCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.ATTEND_TYPE==10||!tfoCourse.ATTEND_TYPE){
                tfoCourse.checked = true;
                $scope.refundCourseTimesList.push(tfoCourse);
            }
        });
    }
    
    $scope.checkTimes= function(times, type){
        $scope.course.wfdTimes = times;
      $scope.surplusTimes = (type == 'wfd') ? $scope.selectedRefundCourse.course_surplus_count : $scope.selectedRefundCourse.course_schedule_count;
    	if(Number(times)>$scope.surplusTimes){
    		$uibMsgbox.warn("输入的冻结课时必须为正整数且不能大于剩余课时！");
    		return;
    	}
    }
    
    $scope.checkWfdTimes = function(times, type){
        $scope.refundCourseTimesList = [];
    	$scope.checkTimes(times, type);
    	for(var i = 0;i < times; i++) {  $scope.refundCourseTimesList.push(i);  } 
    }

    $scope.uncheckAllRefundCourseTimes = function(){
        $.each($scope.selectedRefundCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.ATTEND_TYPE==10||!tfoCourse.ATTEND_TYPE){
                tfoCourse.checked = false;
            }
        });
        $scope.refundCourseTimesList = [];
    }


    $scope.remark = "";
    /**
     * 提交冻结
     */
    $scope.submitRefund = function(){
        if($scope.businessType == 1){
        	submitBJKFrozen();
        }else if($scope.businessType == 2){
        	submitWFDFrozen();
        }else if($scope.businessType == 3){
        	submitWFDFrozen();
        }
    }
    function submitBJKFrozen(){
    	var param = {};
        param.studentId = $scope.student.id;
        param.businessType=$scope.businessType;
        param.orderDetailId = $scope.selectedRefundCourse.id;
        param.courseCnt = $scope.refundCourseTimesList.length;
        var courseTimes = [];
        $.each( $scope.refundCourseTimesList,function(i,ct){
            courseTimes.push(ct.TIMES);
        });
        param.courseTimes = courseTimes.join(",");
        if($scope.selectedFormulaInfo.checkBuckleUp){
            param.premiumDeductionAmount = $scope.selectedFormulaInfo.buckleUp;
            param.premium_result_val = $scope.selectedFormulaInfo.result-param.premiumDeductionAmount;
            param.premium_result=""+param.premium_result_val+"";
        }else{
        	param.premiumDeductionAmount=0
            param.premium_result_val = $scope.selectedFormulaInfo.result;
            param.premium_result=""+param.premium_result_val+"";
        }

        param.premiumType           = $scope.selectedFormulaInfo.selectedFormula.id+"";
        param.orderId                = $scope.selectedRefundCourse.order_id;
        param.premium_formula       = $scope.selectedFormulaInfo.selectedFormula.description;
        param.premium_detail        = $scope.selectedFormulaInfo.formulaDetailInfo;
        param.remark                = $scope.selectedFormulaInfo.remark;

        // $scope.isSubmitRefund = 'isSubmitRefund';
        // erp_orderChangeService.changeFrozen(param,function(resp){
        //     if(!resp.error){
        //         $scope.isSubmitRefund = 'isSubmitRefundOk';
        //     }else{
        //         alert(resp.message);
        //         $scope.isSubmitRefund = 'isSubmitRefundFailed';
        //     }
        // });
        var waitingModal = $uibMsgbox.waiting('正在处理，请稍候');
        erp_orderChangeService.changeFrozen(param,function(resp){
            waitingModal.close();
            if(!resp.error) {
                $uibMsgbox.confirm({
                    content:'冻结成功，可以查看详情，或者继续冻结',
                    cancelText: '继续冻结',
                    okText: '查看详情',
                    callback: function (res) {
                        if (res == 'yes') {
                            //window.location.href = '?studentId=' + param.studentId + '#/studentMgr/studentMgrIndex';
                            window.location.href = '?studentId=' + param.studentId + '&orderId=' + $scope.selectedRefundCourse.order_id + '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
                        } else {
                            //$state.reload();
                            window.location.href="?_t=" + Math.random() + "#/orders/classesFrozen";
                        }
                    }
                });
                //$scope.isSubmitRefund = 'isSubmitRefundOk';
            } else {
                $uibMsgbox.error(resp.message);
                //$scope.isSubmitRefund = 'isSubmitRefundFailed';
            }
        });
    }
    function submitWFDFrozen(){
    	 var param = {};
    	 param.businessType=$scope.businessType;
         param.studentId = $scope.student.id;
         param.orderDetailId = $scope.selectedRefundCourse.id;
         param.courseCnt = $scope.refundCourseTimesList.length;
         var courseTimes = [];
         $.each( $scope.refundCourseTimesList,function(i,ct){
             courseTimes.push(i);
         });
         param.courseTimes = courseTimes.join(",");
         if($scope.selectedFormulaInfo.checkBuckleUp){
             param.premiumDeductionAmount = $scope.selectedFormulaInfo.buckleUp;
             param.premium_result_val = $scope.selectedFormulaInfo.result-param.premiumDeductionAmount;
             param.premium_result=""+param.premium_result_val+"";
         }else{
        	 param.premiumDeductionAmount=0;
             param.premium_result_val = $scope.selectedFormulaInfo.result;
             param.premium_result=""+param.premium_result_val+"";
         }

         param.premiumType           = $scope.selectedFormulaInfo.selectedFormula.id+"";
         param.orderId                = $scope.selectedRefundCourse.order_id;
         param.premium_formula       = $scope.selectedFormulaInfo.selectedFormula.description;
         param.premium_detail        = $scope.selectedFormulaInfo.formulaDetailInfo;
         param.remark                = $scope.selectedFormulaInfo.remark;

         //$scope.isSubmitRefund = 'isSubmitRefund';
        var waitingModal = $uibMsgbox.waiting('正在处理，请稍候');
         erp_orderChangeService.changeFrozen(param,function(resp){
             waitingModal.close();
             if(!resp.error) {
                 $uibMsgbox.confirm({
                     content:'冻结成功，可以查看详情，或者继续冻结',
                     cancelText: '继续冻结',
                     okText: '查看详情',
                     callback: function (res) {
                         if (res == 'yes') {
                             //window.location.href = '?studentId=' + param.studentId + '#/studentMgr/studentMgrIndex';
                             window.location.href = '?studentId=' + param.studentId + '&orderId=' + $scope.selectedRefundCourse.order_id + '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
                         } else {
                             //$state.reload();
                             window.location.href="?_t=" + Math.random() + "#/orders/classesFrozen";
                         }
                     }
                 });
                 //$scope.isSubmitRefund = 'isSubmitRefundOk';
             } else {
                 $uibMsgbox.error(resp.message);
                 //$scope.isSubmitRefund = 'isSubmitRefundFailed';
             }
         });
    }
    
    
    $scope.submitRefundColse = function(){
        $scope.isSubmitRefund = '';
    }



    initial();
}