/**
 * Created by Liyong.zhu on 2017/2/12.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_orderChangeRefundController', [
        '$scope',
        '$log',
        '$uibMsgbox',
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
        erp_orderChangeRefundController]);

function erp_orderChangeRefundController(
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
    $scope.refundTab='bjk';
    $scope.productLine = 1; // 产品线 1-培英精品班 2-个性化 11-佳音
    
    $scope.orderBJK = undefined;
    $scope.orderYDY = undefined;
    $scope.orderWFD = undefined;
    $scope.lastQueryCourseStudentId = undefined; //上次已查询课程的学生Id
    $scope.formulaTypeList = [
        {   id:1, name:'标准退费', description:'折后总价 - 原单价 * （ 总课时 - 退费课时 ）= 退费金额 ( 说明：退费金额为负值不允许编辑补扣金额,可直接提交)'},
        {   id:2, name:'VIP退费',  description:'当前单价 * 退费课时 + 预结转  = 退费金额'}
    ];

    $scope.selectedFormulaInfo = {
        selectedFormula: $scope.formulaTypeList[0]
    };
    
    $scope.premiumType = undefined;//校准退费;
    
    $scope.ladder = {};

    /**
     * 下一步、上一步
     * @param before
     * @param next
     */
    $scope.nextStep = function(before,next) {
        if (next == 2 && before == 1) {
            if ($scope.productLine == 2) {
                $scope.businessType = 2;
                $scope.refundTab = 'ydy';
            }
        	if ($scope.bizType) {
        		$scope.businessType = $scope.bizType;
        		$scope.refundTab = ($scope.bizType == 3) ? 'wfd' : (($scope.bizType == 2) ? 'ydy' : 'bjk');
            }
            $scope.queryCourse();
        }
        if (next == 3) {
            if ($scope.businessType == 2) { // 一对一
                if (!$scope.selectedRefundCourse.refundCourseTimes) {
                    $uibMsgbox.error("请输入退费课时");
                    return;
                }

                $scope.premiumType = 2; // 一对一退费暂时全部使用vip退费 add by lincm 20170828
            }

            if($scope.premiumType == undefined){
            	$scope.premiumTypeSelect = false; 
            }else if($scope.premiumType == 1){
            	$scope.selectedFormulaInfo.selectedFormula = $scope.formulaTypeList[0];
                $scope.selectedFormulaInfo.selectedFormula.description = '当前单价 * 退费课时 = 退费金额';
            	$scope.premiumTypeSelect = true;
            }else if($scope.premiumType == 2){
            	$scope.selectedFormulaInfo.selectedFormula = $scope.formulaTypeList[1];
            	$scope.premiumTypeSelect = true;
            }

            createConfirmInfo();
        }
        $scope.step = next;
    };

    $scope.changeFormula = function(){
        createConfirmInfo();
    };

    function createConfirmInfo(){
        if($scope.selectedFormulaInfo.selectedFormula){
        	if ($scope.businessType == 2) {
        		erp_orderChangeService.refundLadder({
        			p_student_id : $scope.selectedRefundCourse.student_id,
        			p_course_id : $scope.selectedRefundCourse.course_id,
        			p_refund_course_times : $scope.selectedRefundCourse.refundCourseTimes,
        			p_order_create_date : $scope.selectedRefundCourse.order_create_date
        		}, function(resp) {
        			if (!resp.error && resp.data) {
        				$scope.selectedRefundCourse.former_unit_price = resp.data.level_price;
        				$scope.ladder = resp.data;
        			}
        			genConfirmInfo();
        		});
        	} else {
        		$scope.selectedRefundCourse.refundCourseTimes = $scope.refundCourseTimesList.length;
        		genConfirmInfo();
        	}
        }
    }
    
    function genConfirmInfo() {
    	if($scope.selectedFormulaInfo.selectedFormula.id == 1) {
          // 当前单价：0元订单，当前单价取折扣后的单价，否则取原单价
    	    var currentUnitPrice = ($scope.selectedRefundCourse.discount_sum_price > 0) ? $scope.selectedRefundCourse.former_unit_price : $scope.selectedRefundCourse.discount_unit_price;
			      // 不是第一次，则算法不一样
            if($scope.premiumTypeSelect == true) {
                $scope.selectedFormulaInfo.result = currentUnitPrice * $scope.selectedRefundCourse.refundCourseTimes;

                $scope.selectedFormulaInfo.formulaDetailInfo = ""
                    + currentUnitPrice
                    + " * "
                    + $scope.selectedRefundCourse.refundCourseTimes
                    + " = "
                    + $scope.selectedFormulaInfo.result;
            } else {
            	if($scope.selectedRefundCourse.root_course_id == undefined){
                    $scope.selectedFormulaInfo.result = $scope.selectedRefundCourse.discount_sum_price
                    - currentUnitPrice
                    * ($scope.selectedRefundCourse.course_total_count - $scope.selectedRefundCourse.refundCourseTimes);

	                $scope.selectedFormulaInfo.formulaDetailInfo = ""
	                    + $scope.selectedRefundCourse.discount_sum_price
	                    + " - "
	                    + currentUnitPrice
	                    + " * ( "
	                    + $scope.selectedRefundCourse.course_total_count
	                    + " - "
	                    + $scope.selectedRefundCourse.refundCourseTimes
	                    + " ) = "
	                    + $scope.selectedFormulaInfo.result;
            	}else{
            		$scope.selectedFormulaInfo.result = $scope.selectedRefundCourse.root_discount_sum_price
                    - currentUnitPrice
                    * ($scope.selectedRefundCourse.root_course_total_count - $scope.selectedRefundCourse.refundCourseTimes);

	                $scope.selectedFormulaInfo.formulaDetailInfo = ""
	                    + $scope.selectedRefundCourse.root_discount_sum_price
	                    + " - "
	                    + currentUnitPrice
	                    + " * ( "
	                    + $scope.selectedRefundCourse.root_course_total_count
	                    + " - "
	                    + $scope.selectedRefundCourse.refundCourseTimes
	                    + " ) = "
	                    + $scope.selectedFormulaInfo.result;
            	}
            	
            }
    	} else if($scope.selectedFormulaInfo.selectedFormula.id == 2) {
    		$scope.selectedFormulaInfo.result = $scope.selectedRefundCourse.discount_unit_price 
				* $scope.selectedRefundCourse.refundCourseTimes + Math.floor(($scope.selectedRefundCourse.manage_fee/$scope.selectedRefundCourse.course_surplus_count) * $scope.refundCourseTimesList.length);
			
			$scope.selectedFormulaInfo.formulaDetailInfo = ""
	            + $scope.selectedRefundCourse.discount_unit_price
	            + " * "
	            + $scope.selectedRefundCourse.refundCourseTimes
                + " + "
                + Math.floor(($scope.selectedRefundCourse.manage_fee/$scope.selectedRefundCourse.course_surplus_count) * $scope.refundCourseTimesList.length)
	            + " = "
	            + $scope.selectedFormulaInfo.result;
    	}
    }

    $scope.checkBuckleUp = function(){
        if($scope.selectedFormulaInfo.checkBuckleUp){
            $scope.selectedFormulaInfo.checkBuckleUp = false;
        }else{
            $scope.selectedFormulaInfo.checkBuckleUp = true;
        }
        if($scope.selectedFormulaInfo.result<0){
            $scope.selectedFormulaInfo.checkBuckleUp = false;
        }
    };


    /**
     * 选择学员
     * @param student
     */
    $scope.checkedStudent = function(student){
        if(student.checked){
            student.checked = false;
            $scope.orderBJK = undefined;
            $scope.orderWFD = undefined;
            $scope.orderYDY = undefined;
            $scope.student = undefined;
            $scope.selectedRefundCourse = {};
            $scope.refundCourseTimesList = [];
            $scope.productLine = 1;
        }else{
            student.checked = true;
            $scope.student = student;
            $scope.productLine = student.product_line;

            if($scope.curStudent && $scope.curStudent.id != student.id){
                $scope.curStudent.checked = false;
            }
        }
        $scope.curStudent = $scope.student;
    };

    $scope.studentQueryInfo = {};

    function queryStudents() {
        var param = {};
        if($scope.studentId) {
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
            function(resp) {
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

    $scope.queryCourse =  function() {
        if(($scope.businessType == 1 && $scope.orderBJK && $scope.orderBJK.length > 0)
        		|| ($scope.businessType == 3 && $scope.orderWFD && $scope.orderWFD.length > 0)
        		|| ($scope.businessType == 2 && $scope.orderYDY && $scope.orderYDY.length > 0)) {
            return;
        }

        var param = {
            studentId : $scope.curStudent.id,
            businessType:$scope.businessType
        };
        if ($scope.businessType == 1) {
            $scope.isQueryBjk = 'isQueryBjk';
        } else if ($scope.businessType == 2) {
        	$scope.isQueryYdy = 'isQueryYdy';
        } else if ($scope.businessType == 3) {
            $scope.isQueryWfd = 'isQueryWfd';
        }

        erp_studentOrderCourseService.queryOrderCourse(param, function(resp){
            $scope.isQueryBjk = '';
            $scope.isQueryYdy = '';
            $scope.isQueryWfd = '';
            if(!resp.error){
                if ($scope.businessType == 1) {
                    $scope.orderBJK = resp.data;
                    if($scope.orderDetailId){
                        $.each($scope.orderBJK,function(i,r){
                            if(r.id == $scope.orderDetailId){
                                $scope.checkRefundCourse($scope.orderBJK,r);
                            }
                        });
                    }
                } else if ($scope.businessType == 2) {
                	$scope.orderYDY = resp.data;
                	if($scope.orderDetailId){
                        $.each($scope.orderYDY,function(i,r){
                            if(r.id == $scope.orderDetailId){
                                $scope.checkRefundCourse($scope.orderYDY,r);
                            }
                        });
                    }
                } else if ($scope.businessType == 3) {
                    $scope.orderWFD = resp.data;
                    if($scope.orderDetailId){
                        $.each($scope.orderWFD,function(i,r){
                            if(r.id == $scope.orderDetailId){
                                $scope.checkRefundCourse($scope.orderWFD,r);
                            }
                        });
                    }
                }
            }else{
            	$uibMsgbox.error(resp.message);
            }
        });
    };

    $scope.selectRefundCourseTab = function(key){
        if($scope.selectedRefundCourse && !$.isEmptyObject($scope.selectedRefundCourse)) {
            $uibMsgbox.warn("已选定退费课程，不可切换页签！");
            return;
        }
        $scope.refundTab = key;
        if(key=='bjk'){
            $scope.businessType = 1;
        } else if(key=='ydy'){
            $scope.businessType = 2;
        } else if(key=='wfd'){
            $scope.businessType = 3;
        }
        $scope.queryCourse();
    };

    $scope.selectedRefundCourse = {};

    $scope.checkRefundCourse = function(courseList,course){
        $scope.refundCourseTimesList = [];
        if(course.checked){
            course.checked = false;
            $scope.selectedRefundCourse = {};
            delete course.premiumNum;
        } else {
            $scope.premiumType = course.premium_type;
        	
            if (course.invoice_status && course.invoice_status == 1) {
                $uibMsgbox.confirm("当前订单已经开出发票，是否继续退费？", function (result) {
                    if(result != 'yes') {
                        return;
                    }
                    course.checked = true;
                    course.refundCourseTimes = '';
                    course.premiumNum = '';
                    $scope.selectedRefundCourse = course;
                    // 班级课，才查询
                    if($scope.businessType == 1) {
                        queryOrderChangeTimesInfo();
                    }
                });
            } else {
                course.checked = true;
                course.refundCourseTimes = '';
                course.premiumNum = '';
                $scope.selectedRefundCourse = course;
                // 班级课，才查询
                if($scope.businessType == 1) {
                    queryOrderChangeTimesInfo();
                }
            }
        }
    };

    function queryOrderChangeTimesInfo(){
        var param = {};
        param.orderDetailId = $scope.selectedRefundCourse.id;
        $scope.isLoadingCourseTimesPanel = 'isLoadingCourseTimesPanel';
        $scope.selectedRefundCourse.orderChangeCourseTimes = undefined;
        erp_orderManagerService.orderCourseSurplusCount(param,function(resp){
            $scope.isLoadingCourseTimesPanel = '';
            if(!resp.error) {
                $scope.selectedRefundCourse.orderCourseSurplusCount = resp.data;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    }
    $scope.refundCourseTimesList = [];
    $scope.checkRefundCourseTimes =  function(attendance){
        if(attendance.checked){
            attendance.checked = false;
        } else {
            attendance.checked = true;
        }

        $scope.refundCourseTimesList = [];
        $.each($scope.selectedRefundCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.checked){
                $scope.refundCourseTimesList.push(tfoCourse);
            }
        });
    };

    /**
     * 晚辅导退费，输入退费次数
     * @param course
     */
    $scope.inputPremiumNum = function(refundTimes, type){
        // 控制“下一步”的可点击
        if (!$scope.selectedRefundCourse) {
            $uibMsgbox.error('请选择要退费的课程！');
            return false;
        }

        var surplusCount = (type == 'wfd') ? $scope.selectedRefundCourse.course_surplus_count : $scope.selectedRefundCourse.course_schedule_count;
          if (!surplusCount) {
            $uibMsgbox.error('请选择要退费的课程！');
            return false;
          }

        try{
            refundTimes = parseInt(refundTimes);
        }catch(e){
            refundTimes = -1;
        }

        $scope.refundCourseTimesList = [];
        if(refundTimes && refundTimes > 0 && refundTimes <= surplusCount){
            for(var i = 0;i < refundTimes; i++) {
                $scope.refundCourseTimesList.push(i+1);
            }
        } else if (refundTimes <= 0 || refundTimes > surplusCount) {
            $uibMsgbox.error("输入的退费课时必须为正整数且不能大于剩余可排课时！");
        }
    };

    $scope.checkAllRefundCourseTimes = function(){
        $scope.refundCourseTimesList = [];
        $.each($scope.selectedRefundCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.ATTEND_TYPE==10||!tfoCourse.ATTEND_TYPE){
                tfoCourse.checked = true;
                $scope.refundCourseTimesList.push(tfoCourse);
            }
        });
    };

    $scope.uncheckAllRefundCourseTimes = function(){
        $.each($scope.selectedRefundCourse.orderCourseSurplusCount,function(i,tfoCourse){
            if(tfoCourse.ATTEND_TYPE==10||!tfoCourse.ATTEND_TYPE){
                tfoCourse.checked = false;
            }
        });
        $scope.refundCourseTimesList = [];
    };

    $scope.remark = "";

    /**
     * 提交退费
     */
    $scope.submitRefund = function(){
        var param = {};

        if($scope.businessType == 1){
            param.courseCnt = $scope.refundCourseTimesList.length;
            var courseTimes = [];
            $.each( $scope.refundCourseTimesList,function(i,ct){
                courseTimes.push(ct.TIMES);
            });
            param.courseTimes = courseTimes.join(",");
        } else if($scope.businessType == 2){
        	param.courseCnt = $scope.selectedRefundCourse.refundCourseTimes;
        	param.courseTimes = $scope.selectedRefundCourse.refundCourseTimes;
        } else if($scope.businessType == 3){ //晚辅导
            param.courseCnt = $scope.selectedRefundCourse.premiumNum;
            var courseTimes = [];
            $.each( $scope.refundCourseTimesList,function(i,ct){
                courseTimes.push(ct.TIMES);
            });
            param.courseTimes = courseTimes.join(",");
        }

        param.studentId = $scope.student.id;
        param.orderDetailId = $scope.selectedRefundCourse.id;

        if($scope.selectedFormulaInfo.checkBuckleUp){
            param.premiumDeductionAmount = $scope.selectedFormulaInfo.buckleUp;
            param.premium_result_val = $scope.selectedFormulaInfo.result-param.premiumDeductionAmount;
            param.premium_result=""+param.premium_result_val+"";
        }else{
            param.premiumDeductionAmount = 0;
            param.premium_result_val = $scope.selectedFormulaInfo.result;
            param.premium_result=""+param.premium_result_val+"";
        }

        param.premiumType           = $scope.selectedFormulaInfo.selectedFormula.id+"";
        param.orderId                = $scope.selectedRefundCourse.order_id;
        param.premium_formula       = $scope.selectedFormulaInfo.selectedFormula.description;
        param.premium_detail        = $scope.selectedFormulaInfo.formulaDetailInfo;
        param.remark                = $scope.selectedFormulaInfo.remark;
        if(!param.remark) {
            $uibMsgbox.error("备注信息必填！");
            return;
        }

        //$scope.isSubmitRefund = 'isSubmitRefund';
        var waitingModal = $uibMsgbox.waiting('正在处理，请稍候');
        erp_orderChangeService.changeRefund(param,function(resp){
            waitingModal.close();
            if(!resp.error) {
                $uibMsgbox.confirm({
                    content:'退费审批中，可以查看详情，或者继续退费',
                    cancelText: '继续退费',
                    okText: '查看详情',
                    callback: function (res) {
                        if (res == 'yes') {
                            //window.location.href = '?studentId=' + param.studentId + '#/studentMgr/studentMgrIndex';
                            window.location.href = '?studentId=' + param.studentId + '&orderId=' + $scope.selectedRefundCourse.order_id + '#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
                        } else {
                            //$state.reload();
                            window.location.href="?_t=" + Math.random() + "#/orders/classesRefund";
                        }
                    }
                });
                //$scope.isSubmitRefund = 'isSubmitRefundOk';
            } else {
            	$uibMsgbox.error(resp.message);
                //$scope.isSubmitRefund = 'isSubmitRefundFailed';
            }
        });
    };

    $scope.submitRefundColse = function(){
        $scope.isSubmitRefund = '';
    };

    function initial(){
        $('title').text('退费 | 快乐学习');
        $scope.studentId = $("#rootIndex_studentId").val();
        $scope.step = 1;

        $scope.queryStudents = queryStudents;
        $scope.studentId = $("#rootIndex_studentId").val();
        queryStudents();

        $scope.orderDetailId = $("#rootIndex_orderDetailId").val();
        $scope.bizType = $("#rootIndex_bizType").val();
    }

    initial();
}