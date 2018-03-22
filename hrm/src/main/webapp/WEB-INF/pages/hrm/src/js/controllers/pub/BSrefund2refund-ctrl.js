/**
 * Created by Liyong.zhu on 2016/6/22.
 */
angular.module('ework-ui')
    .controller('BSrefund2refundCtrl', [
        '$scope',
        '$log',
        '$state',
        'RefundService',
        'BranchsService',
        'CompanyService',
        'StudentQueryService',
        'OrdersPubService',
        'StudentCourseTimeStateService',
        BSrefund2refundCtrl]);


function BSrefund2refundCtrl(
    $scope,
    $log,
    $state,
    RefundService,
    BranchsService,
    CompanyService,
    StudentQueryService,
    OrdersPubService,
    StudentCourseTimeStateService
    ){
    //步骤
    $scope.step = 1;

    //学员列表
    $scope.studentList = [];

    /**
     * 查询学员
     */
    $scope.queryStudent = function(){
        queryStudentList();
    }
    //当前的学员
    $scope.curStudent = null;

    //已选课程列表
    $scope.selectedCourseList = [];

    //已选择的课程的课时状态信息列表
    $scope.selectedCourseTimesList = [];

    $scope.refund={
    		premiumDeduction:null,    //退费补扣
            refundRemark:null//备注
    }
    $scope.page = {
            searchInfo: null
        };
    /**
     * 获取已经选择的学员
     */
    $scope.checkedStudent = function(student){
        if(student.checked){
            student.checked = false;
            if($scope.curStudent&&$scope.curStudent.id == student.id){
                $scope.curStudent = null;
            }
        }else{
            if($scope.curStudent&&$scope.curStudent.id != student.id){
                $scope.curStudent.checked = false;
            }
            student.checked = true;
            $scope.curStudent = student;
        }
    }
    /**
     * 单选择课程
     * @param course
     */
    $scope.checkedCourse = function(course){
        if(course.checked){
            course.checked = false;
            if($scope.curCourse&&$scope.curCourse.courseId == course.courseId){
                $scope.curCourse = null;
            }
        }else{

            if($scope.curCourse&&$scope.curCourse.courseId != course.courseId){
                $scope.curCourse.checked = false;
            }
            course.checked = true;
            $scope.curCourse = course;
        }
    }
 
    $scope.curCoursetime = [];
    /**
     * 选择课次信息
     * @param time
     */
    $scope.checkedTimes = function(time){
        if(time.checked){
            time.checked = false;
        }else{
            time.checked = true;

        }
        $scope.curCoursetime = [];
        $.each($scope.selectedCourseTimesList,function(i,courseTime){
            if(courseTime.checked){
                $scope.curCoursetime.push(courseTime);
            }
        })
    }

    /**
     * 下一步操作
     * @param before
     * @param next
     */
    $scope.nextStep = function(before,next){
        $scope.step = next;

        if(next == 2){
            queryOrdersPubList();
            $log.log("step 2");
        }
        if(next == 3){
            queryStudentCourseTimeState();
        }
        if(next == 4){
            var str = "";
            $.each($scope.curCoursetime,function(i,c){
                if(i>0){
                    str+= "，";
                }
                str+= c.courseTime;
            });
            $scope.curCoursetimeStr = str;
            $scope.allRefund = parseInt($scope.curCourse.totalPrice) 
                               - parseInt($scope.curCourse.unitPrice) * ( parseInt($scope.curCourse.totalCourseTimes)
                               -parseInt($scope.curCoursetime.length));
        }
    }
    
    
    $scope.queryStudentOnChange = function(){
    	$scope.onGoingQuery=true;
        queryStudentList();
    }
    
    
    //选择一个学生
    $scope.selectedStudent=function(student){
    	angular.forEach($scope.studentList, function(data){
    		  if(student.id==data.id){
    			  data.checked=true;
    			  $scope.curStudent=student;
    			  return;
    		  }
    	});
    	$scope.onGoingQuery=false;
    	}
    
    function checkAll(){}

    /**
     * 退费提交
     */
    $scope.postRefund = function(){
        var param = {};
        $scope.isLoading=true;
        param.curStudent = $scope.curStudent;
        param.curCourse = $scope.curCourse;
        param.curCoursetime = $scope.curCoursetime;
        param.premiumDeduction = $scope.refund.premiumDeduction;
        param.refundRemark = $scope.refund.refundRemark;
        RefundService.post(genPostData(),function(resp){
            if(resp.error == 'false'){
                alert("保存成功！");
                //退费成功后跳转到退费详情
                location.href="#/businessSystem/refundMgr";
            }
            else{
            	if(resp.message){
            		alert("退费提交发生错误:"+resp['message']+"....请截图给管理员!")
            	}
            	 $scope.isLoading=false;
            }
        })
    }

    /**
     * 查询单个学生，选中的课程的课次状态信息
     */
    function queryStudentCourseTimeState(){
        var param = {};
        param.studentId = $scope.curStudent.id;
        param.courseId = $scope.curCourse.courseId;
        param.orderCourseId= $scope.curCourse.orderCourseId;
        StudentCourseTimeStateService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.selectedCourseTimesList = resp.data;
            }
        });
    }

    /**
     * 查询学员列表
     */
    function queryStudentList(){
        var param = {};
        param.searchInfo = $scope.page.searchInfo;
        StudentQueryService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.studentList = resp.data;
            }
        });
    }

    /**
     * 对公查询服务
     */
    function queryOrdersPubList(){
        var param = {};
        param.p_studentId = $scope.curStudent.id;
        $log.log("cur studentId is "+$scope.curStudent.id);
        OrdersPubService.get(param,function(resp){
            if(resp.error ==  'false'){
                var orders = resp.data;
                if( orders  &&  orders.length){
                    var courseList = [];
                    $.each(orders,function(j,order){
                        if(order.orderCourseList  && order.orderCourseList.length){
                            $.each(order.orderCourseList,function(i,model){
                                courseList.push({
                                    "orderId":order.id,
                                    "orderNo":order.order_no,
                                    "schId":model.sch_id,
                                    "courseName":model.course.course_name,
                                    "courseId":model.course_id,
                                    "orderCourseId":model.id,
                                    "unitPrice":model.former_unit_price,
                                    "discountUnitPrice":model.discount_unit_price,
                                    "actualPrice":model.discount_sum_price,
                                    "totalPrice":model.former_sum_price,
                                    "forward":model.forward,
                                    "courseTimes":model.courseTimes,
                                    "rootCourseId":model.root_course_id,
                                    "teacherName":model.teacher['teacher_name'],
                                    "teacherCode":model.teacher['encoding'],
                                    "startTime":model.course['start_time'],
                                    "endTime":model.course['end_time'],
                                    "totalCourseTimes":model.course_total_count
                                })
                            });
                        }

                    });
                    $scope.selectedCourseList = courseList;
                }
            }
        });
    }
    
    
    //生成转入流水表
    function genBussinessOrderLog(bussiness){
    	bussiness.orderLog={};
    	bussiness.orderLog['order_id']=$scope.curCourse['orderId'];
    	bussiness.orderLog['ref_course_count']=$scope.curCoursetime.length;//报班数量或变更涉及课程数量
    	bussiness.orderLog['fee_amount']= $scope.allRefund ;
    }
    
    //生成转入流水表
    function genBussinessOrderCourseLog(bussiness){
    	bussiness.courseLog={};
    	bussiness.courseLog['order_id']=bussiness.orderLog['orderId'];
    	bussiness.courseLog['order_course_id']=$scope.curCourse['orderCourseId'];
    	bussiness.courseLog['root_course_id']=$scope.curCourse['rootCourseId'];
    	bussiness.courseLog['sch_id']=$scope.curCourse['schId'];
    	bussiness.courseLog['former_unit_price']=$scope.curCourse['unitPrice'];
    	bussiness.courseLog['discount_unit_price']=$scope.curCourse['discountUnitPrice'];
    	bussiness.courseLog['course_total_count']=$scope.curCourse['totalCourseTimes'];
    	bussiness.courseLog['course_surplus_count']=$scope.curCourse['totalCourseTimes']-$scope.curCoursetime.length;
     	bussiness.courseLog['fee_deduction_amount']=$scope.premiumDeduction;
     	genTransferInCourseTimes(bussiness.courseLog);
    }
    
    

    //生成转入课程课次信息
    function genTransferInCourseTimes(courseLog){
    	var orderCourseTimes=[];
    	angular.forEach($scope.curCoursetime, function(data){
    		orderCourseTimes.push({
                "course_times":data.courseTime,
                "order_course_id":courseLog['order_course_id']
            });
    		});
    	$log.log(orderCourseTimes);
    	courseLog.courseTimesLogs=JSON.parse(JSON.stringify(orderCourseTimes));
    }
    
    function genPostData(){
    	var bussiness={};
    	genBussinessOrderLog(bussiness);
    	genBussinessOrderCourseLog(bussiness);
    	return bussiness;
    	
    }
    
    
    

    queryStudentList();
}