/**
 * Created by Liyong.zhu on 2016/6/23.
 */
angular.module('ework-ui')
    .controller('BStransferClassMgr2transferClassCtrl', [
        '$scope',
        '$log',
        '$state',
        'TransferClassService',
        'ProductService',
        'SeasonService',
        'BranchsService',
        'CompanyService',
        'GradeService',
        'SubjectService',
        'StudentQueryService',
        'OrdersPubService',
        'CourseService',
        'StudentCourseTimeStateService',
        BStransferClassMgr2transferClassCtrl]);


function BStransferClassMgr2transferClassCtrl(
    $scope,
    $log,
    $state,
    TransferClassService,
    ProductService,
    SeasonService,
    BranchsService,
    CompanyService,
    GradeService,
    SubjectService,
    StudentQueryService,
    OrdersPubService,
    CourseService,
    StudentCourseTimeStateService
    ){
    //步骤，默认第一步
    $scope.step = 1;
    //学员列表
    $scope.studentList = [];
    //当前的学员
    $scope.curStudent = null;
    //选择转入课程选择条件控制开关，默认关闭
    $scope.toSelectedCourseState = 'courseUp';
    //课程查询参数对象
    $scope.queryCourseParam = {};

    //学员待转出课程列表
    $scope.selectedCourseList = [];
    //转入课程
    $scope.transferInCourseList = [];
    //当前转出课程
    $scope.curCourse = null;
    //转入课程
    $scope.transferInCourse = null;

    //转出课程课次
    $scope.curCoursetime = [];
    //转入课程课次
    $scope.curTransferInCoursetime = [];

    //转出课次信息字符串
    $scope.transferOutCoursetimesStr = "";
    //转入课次信息字符串
    $scope.curTransferInCoursetimeStr = "";
    $scope.currentOrderCourse={
    	orderId:'',
        orderNo:''
    	
    }
    //转班备注
    $scope.remark = null;
    $scope.page = {
            searchInfo: null
        };
    $scope.shcId=null;
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
            queryStudentTransferInCourseTimeState();
        }
        if(next == 4){

        }
    }

    /**
     * 查询学员
     */
    $scope.queryStudent = function(){
        queryStudentList();
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
     * 选择学员
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
     * 打开搜课功能
     */
    $scope.toSelectedCourse = function(){
        if($scope.toSelectedCourseState == "courseUp"){
            $scope.toSelectedCourseState = "courseDown";
            queryCourse();
        }else{
            $scope.toSelectedCourseState = "courseUp";
        }
    }

    /**
     * 选择条件
     */
    $scope.checkCondition = function(key,value){
        $scope.queryCourseParam[key] = value;

        //查询已报课程信息
        if(key == 'productId'|| key == 'seasonId'){
            queryOrdersPubList();
        }else {
         if(key=='companyId')
           	{
   		  queryBranchs();
           	}
           //如果是校区联动
           else if(key=='branchId')
       	{
           	$scope.shcId=value;
           	queryWhenBranchChange();
       	}
            queryCourse();
        }
    }
    /**
     * 查询待转入课程，过滤掉已选择的课程
     */
    function queryCourse(){
        var param = {};
        $.extend(param,$scope.queryCourseParam);
        $scope.selectingCourseList = [];
        CourseService.get(param,function(resp){
            if(resp.error == 'false'){
                var selectingCourseList = resp.data;
                //过滤已经选择的课程
                if(selectingCourseList&&selectingCourseList.length){
                    $.each(selectingCourseList,function(i,cc){
                        cc.actual_price = cc.unit_price;
                        var exist = false;
                        $.each($scope.transferInCourseList,function(j,ci){
                            if(cc.id == ci.id){
                                exist = true;
                            }
                        });
                        if(!exist){
                            $scope.selectingCourseList.push(cc);
                        }
                    });
                }
            }
        });
    }
    /**
     * 查询产品线类型定义
     */
    function queryProduct(){
        ProductService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.productList = resp.data;
                if($scope.productList&&$scope.productList.length){
                    $scope.queryCourseParam['productId'] = $scope.productList[0].id;
                }
            }
        })
    }

    /**
     * 课程季参数
     */
    function queryGradeList(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
        	param.schId=$scope.shcId;
        }
        GradeService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.gradeList = resp.data;
            }
        })
    }

    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.companyList = resp.data;
            }
        })
    }

    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
    	var companyId=$scope.queryCourseParam['companyId'];
    	  var param = {};
          param.companyId = companyId;
        BranchsService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.branchsList = resp.data;
            }
        })
    }
    
    function queryWhenBranchChange(){
    	querySeason();
    	queryGradeList();
    	querySubject();
    }
    //查询课程季
    function querySeason(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
        	param.schId=$scope.shcId;
        }
        SeasonService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.seasonList = resp.data;
            }
        })
    }

    /**
     * 查询年级
     */
    function queryGradeList(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
        	param.schId=$scope.shcId;
        }
        GradeService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.gradeList = resp.data;
            }
        })
    }

    /**
     * 查询科目
     */
    function querySubject(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
        	param.schId=$scope.shcId;
        }
        SubjectService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.subjectList = resp.data;
            }
        })
    }

    /**
     * 对公查询订单课程信息服务
     */
    function queryOrdersPubList(){
        var param = {};
        param.p_studentId = $scope.curStudent.id;//学员编码
        if($scope.queryCourseParam['productId'])//产品类型
        param.p_productId =  $scope.queryCourseParam['productId'];
        if($scope.queryCourseParam['seasonId'])
        param.p_seasonId =  $scope.queryCourseParam['seasonId'];//课程季
        OrdersPubService.get(param,function(resp){
            //if(resp.error ==  'false'){
                var orders = resp.data;
                if( orders  &&  orders.length){
                    var courseList = [];
                    $.each(orders,function(j,order){
                        if(order.orderCourseList  && order.orderCourseList.length){
                            $.each(order.orderCourseList,function(i,model){
                                courseList.push({
                                    "orderId":order.id,
                                    "orderNo":order.order_no,
                                    "courseName":model.course.course_name,
                                    "courseId":model.course_id,
                                    "orderCourseId":model.id,
                                    "unitPrice":model.former_unit_price,
                                    "actualPrice":model.discount_sum_price,
                                    "totalPrice":model.former_sum_price,
                                    "forward":model.forward,
                                    "courseTimes":model.courseTimes,
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
           // }
        });
    }

    /**
     * 单选择课程
     * @param course
     */
    $scope.checkedCourse = function(course){
    	for(var i in   $scope.selectedCourseList){
    		 $scope.selectedCourseList[i].checked=false;
    	}
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

    /**
     * 选择课程
     * @param course
     */
    $scope.selectedCourse = function(course){
        if($scope.transferInCourseList && $scope.transferInCourseList.length == 0){
            $scope.transferInCourseList.push(course);
            $scope.toSelectedCourse();
            $scope.transferInCourse = course;
                $.extend($scope.transferInCourse, {'actualPrice':$scope.curCourse.unitPrice});
                queryCourse();
        }else{
            alert("已经选择了转入课程！");
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

    /**
     * 移除课程
     * @param course
     */
    $scope.removeCourse = function(course){
        var selectedCourse = [];
        $.each($scope.transferInCourseList,function(i,c){
            if(c.id != course.id){
                selectedCourse.push(c);
            }
        });
        $scope.transferInCourseList = selectedCourse;
        $scope.transferInCourse = null;
        queryCourse();
    }

    /**
     * 查询单个学生，选中的课程的课次状态信息
     */
    function queryStudentCourseTimeState(){
        var param = {};
        param.p_studentId = $scope.curStudent.id;
        param.courseId = $scope.curCourse.courseId;
        param.orderCourseId= $scope.curCourse.orderCourseId;
        StudentCourseTimeStateService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.selectedCourseTimesList = resp.data;
                $scope.currentOrderCourse.orderId= $scope.selectedCourseTimesList[0].orderId;
                $scope.currentOrderCourse.orderNo= $scope.selectedCourseTimesList[0].orderNo;
            }
        });
    }

    /**
     * 查询转入课程的课时状态信息
     */
    function queryStudentTransferInCourseTimeState(){
        var param = {};
        var courseId=$scope.transferInCourseList[0].id;
        param.courseId =courseId;
        CourseService.queryCourseTimes(param,function(resp){
            if(resp.error == 'false'){
                $scope.transferInCourseTimesList = resp.data;
            }
        });
    }



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
        });
        $scope.transferInCourse.courseTimes = $scope.curCoursetime.length;
        $scope.transferInCourse.totalPrice = $scope.curCoursetime.length * parseInt($scope.transferInCourse.actualPrice);
        genTransferOutCoursetimes();
    }

    /**
     * 选择转入课次
     * @param time
     */
    $scope.checkedTransferInTimes = function(time){
        if(time.checked){
            time.checked = false;
        }else{
            time.checked = true;
        }
        $scope.curTransferInCoursetime = [];
        $.each($scope.transferInCourseTimesList,function(i,courseTime){
            if(courseTime.checked){
                $scope.curTransferInCoursetime.push(courseTime);
            }
        });
        genTransferInCoursetimes();
    }

    /**
     * 获取转出课时课次信息字符串
     */
    function genTransferOutCoursetimes(){
        var str = "";
        $.each($scope.curCoursetime,function(i,m){
            if(i>0){
                str += ",";
            }
            str += m.courseTime;
        });
        $scope.transferOutCoursetimesStr =  str;
    }

    /**
     * 获取转入课时课次信息字符串
     */
    function genTransferInCoursetimes(){
        var str = "";
        $.each($scope.curTransferInCoursetime,function(i,m){
            if(i>0){
                str += ",";
            }
            str += m.courseTime;
        });
        $scope.curTransferInCoursetimeStr =  str;
    }
    
    function genPostTransferCourses(param){
    	 var bussiness={};
    	 bussiness.transferOutOrder=param.transferOut;
    	 bussiness.transferInOrder={}; 
    	 //转出单
    	 $.extend( bussiness.transferOutOrder, {'id': $scope.selectedCourseList[0]['orderCourseId'] });
    	 $.extend( bussiness.transferOutOrder, {'order_id': $scope.selectedCourseList[0]['orderId'] });
    	 var course_total_count= $scope.curCoursetime.length;
    	 $.extend( bussiness.transferOutOrder, {'course_total_count': course_total_count});//总课时
    	 var totalCount=$scope.transferInCourseTimesList.length;
    	 var unitPrice= $scope.selectedCourseList[0]['unitPrice'];
    	// var course_surplus_count=parseInt(totalCount)-parseInt($scope.curTransferInCoursetime.length);//剩余课程
    	 $.extend( bussiness.transferOutOrder, {'course_surplus_count': course_total_count });//剩余课时
    	 $.extend( bussiness.transferOutOrder, {'surplus_cost': course_total_count*unitPrice });//剩余费用
    	 $.extend( bussiness.transferOutOrder, {'former_sum_price': course_total_count*unitPrice });//原价
    	 $.extend( bussiness.transferOutOrder, {'discount_sum_price': bussiness.transferOutOrder['former_sum_price'] });//实际价格
    	 $.extend( bussiness.transferOutOrder, {'courseTimes': $scope.curTransferInCoursetime.length });//课时数量
    	 $.extend( bussiness.transferOutOrder, {'sch_id':$scope.shcId});
    	 
    	 //转入单
    	 var courseId=$scope.transferInCourseList[0]['id'];
    	 $.extend( bussiness.transferInOrder, {'course_id': courseId});
    	 $.extend( bussiness.transferInOrder, {'root_course_id': $scope.curCourse.orderCourseId });
    	 var course_total_count= $scope.curCoursetime.length;
    	 $.extend( bussiness.transferInOrder, {'course_total_count': course_total_count });
    	 var totalCount=$scope.selectedCourseList.length;
    	// var course_surplus_count=parseInt(totalCount)-$scope.curCoursetime.length;
    	 $.extend( bussiness.transferInOrder, {'course_surplus_count':course_total_count});//剩余课时
    	 $.extend( bussiness.transferInOrder, {'former_unit_price':  bussiness.transferOutOrder.unitPrice });
    	 $.extend( bussiness.transferInOrder, {'discount_sum_price':  bussiness.transferOutOrder.actualPrice });
    	 $.extend( bussiness.transferInOrder, {'former_sum_price':  bussiness.transferOutOrder.totalPrice });
    	 $.extend( bussiness.transferInOrder, {'courseTimes': $scope.curCoursetime.length});//课时数量
    	 $.extend( bussiness.transferInOrder, {'order_id':bussiness.transferOutOrder.orderId });
    	 $.extend( bussiness.transferInOrder, {'branch_id':$scope.queryCourseParam['branchId']});
    	 $.extend( bussiness.transferInOrder, {'sch_id':$scope.shcId});
    	 genTransferOutCourseTimes(bussiness);
    	 genTransferInCourseTimes(bussiness);
    	 genPubTransferOutOrderLog(bussiness);
    	 genPubTransferInOrderLog(bussiness);
     	 return bussiness;
    }

    //生成转出课程课次信息
    function genTransferOutCourseTimes(bussiness){
    	var orderCourseTimes=[];
    	angular.forEach($scope.curCoursetime, function(data){
    		orderCourseTimes.push({
                "times":data.courseTime,
                "isValid":1
            });
    		});
    	$.extend( bussiness.transferOutOrder, {'orderCourseTimes':  orderCourseTimes });
    }
    

    //生成转入课程课次信息
    function genTransferInCourseTimes(bussiness){
    	var orderCourseTimes=[];
    	angular.forEach($scope.curTransferInCoursetime, function(data){
    		orderCourseTimes.push({
                "times":data.courseTime,
                "isValid":1
            });
    		});
    	$.extend( bussiness.transferInOrder, {'orderCourseTimes':  orderCourseTimes });
    }
    
    
    //生成转入流水表
    function genPubTransferInOrderLog(bussiness){
    	bussiness.pubTransferInOrderLog={};
    	bussiness.pubTransferInOrderLog['order_id']=bussiness.transferInOrder['orderId'];
    	bussiness.pubTransferInOrderLog['ref_course_count']=$scope.curCoursetime.length;//报班数量或变更涉及课程数量
    	bussiness.pubTransferInOrderLog['fee_amount']=bussiness.transferInOrder['totalPrice'];
      
    }
    
    //生成转出流水表
    function genPubTransferOutOrderLog(bussiness){
    	bussiness.pubTransferOutOrderLog={};
    	bussiness.pubTransferOutOrderLog['order_id']=bussiness.transferOutOrder['orderId'];
    	bussiness.pubTransferOutOrderLog['ref_course_count']=$scope.curTransferInCoursetime.length;//报班数量或变更涉及课程数量
    	bussiness.pubTransferOutOrderLog['fee_amount']=bussiness.transferOutOrder['actualPrice'];
    }
    /**
     * 提交转班业务
     */
    $scope.submitTransferCourse = function(){
        var param = {};
        $scope.isLoading==true;
        param.student = $scope.curStudent;
        param.transferOut = $scope.curCourse;
        param.transferIn = $scope.transferInCourse;
        param.transferOutCourseTime = $scope.curCoursetime;
        param.transferInCourseTime = $scope.curTransferInCoursetime;
        param.remark = $scope.remark;
        TransferClassService.post(genPostTransferCourses(param),function(resp){
            if(resp.error == 'false'){
                alert("转班成功！");
                //转班成功之后跳转到转班详情页面
                location.href='#/businessSystem/BStransferClassMgr';
            }
            $scope.isLoading==false;
        })
    }

    //查询学员
   // queryStudentList();
    //查询产品线
    queryProduct();
    //查询课程季
    querySeason();
    //查询归属公司
    queryCompanys();
    //查询校区
    queryBranchs();
    //查询科目
    querySubject();
    //查询年级
    queryGradeList();
}