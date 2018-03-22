/**
 * Created by Liyong.zhu on 2016/6/17.
 */
angular.module('ework-ui')
    .controller('EBOrders2OrdersCtrl', [
        '$scope',
        '$log',
        '$state',
        'CompanyService',
        'BranchsService',
        'StudentQueryService',
        'GradeService',
        'SeasonService',
        'SubjectService',
        'CourseService',
        'StudentAccountService',
        'ProductService',
        'PosService',
        'CompanyAccountService',
        'OrdersPubService',
        EBOrders2OrdersCtrl]);
function EBOrders2OrdersCtrl(
        $scope,
        $log,
        $state,
        CompanyService,
        BranchsService,
        StudentQueryService,
        GradeService,
        SeasonService,
        SubjectService,
        CourseService,
        StudentAccountService,
        ProductService,
        PosService,
        CompanyAccountService,
        OrdersPubService
    ){
    //学员列表
    $scope.studentList = [];
    //已选课程列表
    $scope.selectedCourseList = [];
    //课程课程数;
    $scope.courseTimeList= [];
    //步骤
    $scope.step = 1;
    //年级
    $scope.gradeList = [];
    //课程季
    $scope.seasonList = [];
    //科目
    $scope.subjectList = [];
    //归属公司
    $scope.companyList = [];
    //所在校区
    $scope.branchsList= [];
    //产品线
    $scope.productList = [];
    //账户
    $scope.accountList = [];
    //现金,刷卡,转账
    $scope.cashList=[];
    $scope.cardList=[];
    $scope.transferList=[];
    
    $scope.shcId=null;
    $scope.page = {
            searchInfo: null
        };
    $scope.checkAll={
    	checkd:false	
    		
    };

    //支付信息
    $scope.pay = {
        cash:0,
        card:0,
        transfer:0,
        account:0,
        sum_price:0
    };
    //支付id
    $scope.payIds = 1;
    /**
     * 学员账户
     * @type {{account_1: {}}}
     */
    $scope.account = {
        "1" :{}
    };

    //查询课程的参数
    $scope.queryCourseParam = {
    };
    /**
     * 选择条件
     */
    $scope.checkCondition = function(key,value){
        $scope.queryCourseParam[key] = value;
        //如果是归属公司联动
        if(key=='companyId')
        	{
		  $log.log("start query branc.hs.........."+value);
		  queryBranchsWithCompany(value);
        	}
        //如果是校区联动
        else if(key=='branchId')
    	{
        	$scope.shcId=value;
        	 $log.log("start query branchs..........."+value);
        	queryWhenBranchChange();
    	}
         queryCourse();
    }
    
    //修改报班选择课次信息
    $scope.updateOrderCourseTimes=function(currentCourse){
     	$("#selectedCourseTimesPanel").modal("hide");
     
    	
    };
    
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
   
    
   //全选与反选
   $scope.toggleAll = function(currentCourse) {
	   $scope.checkAll.checked=$scope.checkAll.checked?false:true;
	   var count=0;
	   //如果反选
	   if( !$scope.checkAll.checked)
		   currentCourse.selectedCourseTimes=0;
       $.each(currentCourse.courseTimes,function(i,item){
    	   //排除不能选的课程课次
    	   if(!item.disabled){
       	   item.checked =$scope.checkAll.checked; 
       	   if( item.checked)
       	   count++;
    	   }
       });
       if(currentCourse.courseTimes){
    	   currentCourse.selectedCourseTimes=count;
       }
       backCoursePrice($scope.currentCourse);
       changeCourseLists($scope.currentCourse);
       genSumPrice();//计算报班总金额
    }
   /**
    * 选择行
    * @param row
    */
   $scope.checkedRow = function(row){
       if(row.checked){
           row.checked = false;
           var selectedTimes= $scope.currentCourse.selectedCourseTimes;
           $scope.currentCourse.selectedCourseTimes= parseInt(selectedTimes-1);
       }else{
           row.checked = true;
          var selectedTimes= $scope.currentCourse.selectedCourseTimes;
           $scope.currentCourse.selectedCourseTimes=(selectedTimes==0)?1:parseInt(selectedTimes+1);
       }
       backCoursePrice($scope.currentCourse);//还原课程价格
       changeCourseLists($scope.currentCourse);
       genSumPrice();
   }
   
   //还原现价和现总价
   function backCoursePrice(course){
	   course.actual_price=course.unit_price;
	   course.discount_sum_price=course.unit_price*course.selectedCourseTimes;
   }
   
   function changeCourseLists(course){
	   var index=0;
		angular.forEach($scope.selectedCourseList, function(data){
			if((data.id+"")==(course.id+"")){
				$.extend($scope.selectedCourseList[index],course);
			}
			parseInt(index++);
		});
	   
	   
   }
    /**
     * 查询归属校区
     */
    function queryBranchsWithCompany(value){
        var param = {};
        param.companyId = value;
            BranchsService.get(param,function(resp){
                $scope.isLoadingBranchListSelected = '';
                if(resp.error == 'false'){
                	$scope.branchsList = resp.data;
                }
            });
        
    }

    /**
     * 修改课时课次信息
     * @param course
     */
    $scope.toChangeCourseTimes = function(course){
        $scope.currentCourse = course;
        $("#selectedCourseTimesPanel").modal("show");
    }

    /**
     * 选择课程
     * @param course
     */
    $scope.selectedCourse = function(course){
    	var courseId=course.id;
    	$scope.initCourseTimes(course);
    	checkConfilctStudentsCourseTimes(course,courseId);
        $scope.selectedCourseList.push(course);
        queryCourse();
        $scope.onGoingQueryCourse=false;
    }

    /**
     * 移除课程
     * @param course
     */
    $scope.removeCourse = function(course){
        var selectedCourse = [];
        $.each($scope.selectedCourseList,function(i,c){
            if(c.id != course.id){
                selectedCourse.push(c);
            }
        });
        $scope.selectedCourseList = selectedCourse;
        queryCourse();
    }
    /**
     * 查询课程
     */
    $scope.queryCourse = function(){
        queryCourse();
    }

    /**
     * 查询学员
     */
    $scope.queryStudent = function(){
        queryStudentList();
    }
    
    $scope.queryStudentOnChange = function(){
    	$scope.onGoingQuery=true;
        queryStudentList();
    }
    //当前的学员
    $scope.curStudent = null;
    $scope.initCourseTimes=function(course){
    		var selectedCount=0;
    		var jsonArray= new Array();
        	for(var i=1;i<=course.courseTime;i++){
        		jsonArray.push({
                      "id":i,
                      "time":parseInt(i),
                      "checked":true,
                      "disabled":false		  
                  });
        	}
            $.extend(course, {'courseTimes':JSON.parse(JSON.stringify(jsonArray)),'selectedCourseTimes':selectedCount});
    }
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
     * 下一步操作
     * @param before
     * @param next
     */
    $scope.nextStep = function(before,next){
        $scope.step = next;
        if(next == 4){
            genSumPrice();
            queryStudentAccount();
        }
    }

    $scope.toSelectedCourseState = "courseUp";
    /**
     * 请选择课程
     */
    $scope.toSelectedCourse = function(){
        if($scope.toSelectedCourseState == "courseUp"){
            $scope.toSelectedCourseState = "courseDown";
        }else{
            $scope.toSelectedCourseState = "courseUp";
        }
    }
    /**
     * 查询课程课次信息
     * @param course
     */
    function queryCourseTimes(courseId){
        var param = {};
        param.courseId = courseId;
        CourseTimesService.get(param,function(resp){
            if(resp.error == 'false'){
                course.courseTimeList = resp.data;
            }
        })
    }
    /**
     * 打开现金支付面板
     * @param type
     */
    $scope.openPayPanel = function(type){
        if(type == 'cash'){
            $scope.selectedStudent = genSelectedStudent();
            $scope.cashPay = {
                total:0,
                cashList:[]
            };
            if($scope.pay.cashList){
                $scope.cashPay.cashList = $scope.pay.cashList;
                $scope.cashPay.total = $scope.pay.cash;
            }
            $("#studentAccountCashPanel").modal("show");

        }else if(type == 'card'){
            $scope.selectedStudent = genSelectedStudent();
            $scope.cardPay = {
                total:0,
                cardList:[],
                posList:$scope.posList
            };
            if($scope.pay.cardList){
                $scope.cardPay.cardList = $scope.pay.cardList;
                $scope.cardPay.total = $scope.pay.card;
            }
            $("#studentAccountCardPanel").modal("show");

        }
      
        //转账
        else if(type == 'transfer'){
            $scope.selectedStudent = genSelectedStudent();
            $scope.transferPay = {
                total:0,
                transferList:[],
                accountList:$scope.accountList
            };
            if($scope.pay.transferList){
                $scope.transferPay.transferList = $scope.pay.transferList;
                $scope.transferPay.total = $scope.pay.transfer;
            }
            $("#studentAccountTransferPanel").modal("show");
        }
        //储值账户
        else if(type == 'account'){
            $scope.selectedStudent = genSelectedStudent();
            $scope.accountPay = {
                total:0
            };
            if($scope.pay.account){
                $scope.accountPay.total = $scope.pay.account;
            }
            $("#studentAccountPanel").modal("show");
        }
        $scope.pay.currType = type;

    }

    /**
     * 添加现金
     */
    $scope.addPay = function(){
        $scope.payIds += 1;
debugger;
        //现金
        if($scope.pay.currType == 'cash'){
            var cash = {};
            cash.value = $scope.cashPay.value;
            cash.remark = $scope.cashPay.remark;
            cash.type = '1';
            cash.typeName = '现金';
            cash.id = $scope.payIds;
            $scope.cashPay.cashList.push(cash);
            $scope.cashPay.value = null;
            $scope.cashPay.remark = null;
            $scope.cashPay.total = 0;
            $.each($scope.cashPay.cashList,function(i,c){
                $scope.cashPay.total+= parseInt(c.value);
            });
        }
      
        //刷卡
        else if($scope.pay.currType == 'card'){
            var card = {};
            card.value = $scope.cardPay.value;
            card.card = $scope.cardPay.card;
            card.companyAccount = $scope.cardPay.companyAccount;
            card.posId = $scope.cardPay.pos.id;//pos机id
            card.posName = $scope.cardPay.pos.name;//pos机描述
            card.remark = $scope.cardPay.remark;
            card.id = $scope.payIds;
            card.type = '2';

            $scope.cardPay.cardList.push(card);
            $scope.cardPay.total = 0;
            $.each($scope.cardPay.cardList,function(i,c){
                $scope.cardPay.total  +=  parseInt( c.value );
            });
            $log.log("added cash.");
        }
        //转账
        else if($scope.pay.currType == 'transfer'){
            var transfer = {};
            $log.log($scope.transferPay.account);
            transfer.value = $scope.transferPay.value;
            transfer.card = $scope.transferPay.card;
            transfer.companyAccount = $scope.transferPay.account.name;
            transfer.accountId = $scope.transferPay.account.id;//账号编码
            transfer.remark = $scope.transferPay.remark;
            transfer.type = '3';
            transfer.id = $scope.payIds;
            $scope.transferPay.transferList.push(transfer);
            $scope.transferPay.total = 0;
            $.each($scope.transferPay.transferList,function(i,c){
                $scope.transferPay.total  +=  parseInt( c.value );
            });
        }
        
    }
    /**
     * 移除支付记录
     * @param pay
     */
    $scope.removePay = function(pay){
        //现金
        if($scope.pay.currType == 'cash'){
            var list = [];
            $scope.cashPay.total = 0;
            $.each($scope.cashPay.cashList,function(i,c){
                if(pay.id != c.id){
                    list.push(c);
                    $scope.cashPay.total +=  parseInt( c.value );
                }
            });
            $scope.cashPay.cashList = list;
        }
        //刷卡
        else if($scope.pay.currType == 'card'){
            var list = [];
            $scope.cardPay.total = 0;
            $.each($scope.cardPay.cardList,function(i,c){
                if(pay.id != c.id){
                    list.push(c);
                    $scope.cardPay.total +=  parseInt( c.value );
                }
            });
            $scope.cardPay.cardList = list;
        }
        //转账
        else if($scope.pay.currType == 'transfer'){
            var list = [];
            $scope.transferPay.total = 0;
            $.each($scope.transferPay.transferList,function(i,c){
                if(pay.id != c.id){
                    list.push(c);
                    $scope.transferPay.total +=  parseInt( c.value );
                }
            });
            $scope.transferPay.transferList = list;
        }
        if($scope.cashPay.cashList)
        $scope.cashList=$scope.cashPay.cashList;
        if($scope.cardPay.cardList)
        $scope.cardList=$scope.cardPay.cardList;
        if($scope.transferPay.transferList)
        $scope.transferList=$scope.transferPay.transferList;
    }

    /**
     * 选择pos机
     * @param pos
     */
    $scope.selectedPos = function(pos){
        $scope.cardPay.companyAccount = pos.accountName;
        $scope.cardPay.accountId = pos.accountId;
        $scope.cardPay.posName = pos.name;
        $scope.cardPay.posId = pos.id;
    }

    /**
     * 选择公司账户
     * @param account
     */
    $scope.selectedAccount = function(account){
        $scope.transferPay.companyAccount = account.name;
        $scope.transferPay.accountId = account.id;
    }

    /**
     * 现金支付确认
     */
    $scope.confirmPay = function(){
        if($scope.pay.currType == 'cash'){
            $scope.pay.cash = $scope.cashPay.total;
            $scope.pay.cashList = $scope.cashPay.cashList;
            $scope.cashPay = {};
            $scope.cashPay.total = 0;

            $("#studentAccountCashPanel").modal("hide");
        }
        else if($scope.pay.currType == 'card'){
            $scope.pay.card = $scope.cardPay.total;
            $scope.pay.cardList = $scope.cardPay.cardList;
            $scope.cardPay = {};
            $scope.cardPay.total = 0;
            $("#studentAccountCardPanel").modal("hide");
        }
        else if($scope.pay.currType == 'transfer'){
            $scope.pay.transfer = $scope.transferPay.total;
            $scope.pay.transferList = $scope.transferPay.transferList;
            $scope.transferPay = {};
            $scope.transferPay.total = 0;
            $("#studentAccountTransferPanel").modal("hide");
        }else if($scope.pay.currType == 'account'){
            $scope.pay.account = $scope.accountPay.total;
            $scope.accountPay = {};
            $scope.accountPay.total = 0;
            $("#studentAccountPanel").modal("hide");
        }
    }

    /**
     * 提交订单
     */
    $scope.submitOrder = function(){
    	//检查课次
    	var checkErroMsg=checkSelecetedCourseTimes();
    	if(checkErroMsg!=''){
    		alert(checkErroMsg+"未选择报班课次!");
    		return false;
    	}
       var param = {};
        param.student=$scope.selectedStudent = genSelectedStudent();
        param.courseList=$scope.selectedCourseList ;
        param.pay =$scope.pay;
        var bussiness={};
        //报班成功则跳转到报班详情页
        OrdersPubService.add( $scope.genOrderInfo(param,bussiness),function(resp){
            if(resp.error == 'false'){
                alert("提交成功！");
                //报班成功后跳转到报班详情页
                location.href='#/businessSystem/orderMgr';
            }
        });
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
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.branchsList = resp.data;
            }
        })
    }

    function queryStudentList(){
        var param = {};
        param.searchInfo = $scope.page.searchInfo;
        StudentQueryService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.studentList = resp.data;
            }
        });
    }

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
    
    function queryWhenBranchChange(){
    	queryCompanyAccount();
    	querySeason();
    	queryGradeList();
    	querySubject();
    	queryPos();
    }

     //动态查询课程
    $scope.dynamicQueryCourse=function(){
    	$scope.dynamicQuery=true;
    	$scope.queryCourseParam['searchInfo']=$("#courseSearchInfo").val();
    	$log.log($scope.queryCourseParam);
    	$scope.onGoingQueryCourse=true;
    	queryCourse();
    };
    
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
                        $.each($scope.selectedCourseList,function(j,ci){
                            if(cc.id == ci.id){
                                exist = true;
                            }
                        });
                        //报过的课次不能再续保
                        if(!exist){
                            $scope.selectingCourseList.push(cc);
                        }
                    });
                }
            }
        });
    }

    function genSumPrice(){
        var sum_price = 0;
        $scope.sum_price =0.0;
        $.each($scope.selectedCourseList,function(j,ci){
        	if(ci.discount_sum_price){
              //sum_price += parseFloat(ci.actual_price*ci['selectedCourseTimes']);//课程总价
        	  sum_price += parseFloat(ci.discount_sum_price);//现价之和
        	}
        });
        $scope.sum_price = sum_price.toFixed(2);
    }

    function queryStudentAccount(){
        var selectedStudent = genSelectedStudent();
        var param = {};
        param.studentId = selectedStudent.id;
        StudentAccountService.get(param,function(resp){
            if(resp.error == 'false'){
            	   $scope.account[$scope.queryCourseParam['productId']] = resp;
            }
        })
    }

    function genSelectedStudent(){
        var studentSelected = null;
        $.each($scope.studentList,function(i,student){
            if(student.checked){
                studentSelected = student;
            }
        });
        return studentSelected;
    }

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

    function queryPos(){
    	var param={};
    	param.branchId= $scope.queryCourseParam['branchId'];
        PosService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.posList = resp.data;
            }
        })
    }

    function queryCompanyAccount(){
    	  var param = {};
    	  param.branchId= $scope.queryCourseParam['branchId'];
        CompanyAccountService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.accountList = resp.data;
            }
        })
    }
   
    //生成报班单POST对象
    $scope.genOrderInfo=function(param,bussiness){
    	var order={};
    	order.student_id=param.student.id;
    	order.sch_id=$scope.shcId;//校区
    	order.fee_amount=$scope.sum_price;//报班金额
    	order.actural_amount=$scope.sum_price;//实际总价
    	order.branch_id=$scope.sch_id;
    	var orderCourses=[];
    	$.each(param.courseList,function(i,course){
    		var courseCount=course.selectedCourseTimes;
    		var orderCourseTimes=new Array();
    		genCourseTimes(course,orderCourseTimes);
    		orderCourses.push({
                   "course_id":course.id,
                   "course_no":course.courseNo,
                   "course_name":course.courseName,
                   "former_unit_price":course.unit_price,
                   "former_sum_price":(course.unit_price)*courseCount,//课程原总价
                   "discount_unit_price":course.actual_price,
                   "discount_sum_price":course.discount_sum_price,
                   "discount_rate":course.discount_rate,//折扣
                   "manage_fee":course.manage_fee,//预结转
                   "discount_amount":(course.unit_price)*courseCount,
                   "course_surplus_count":courseCount, //剩余课程总课次数
                   "course_total_count":courseCount,//课程总课次
                   "surplus_cost":courseCount*course.unit_price,//剩余费用
                   "branch_id":order.sch_id,
                   "status":1,//子订单状态为有效
                   "sch_id":order.sch_id,
                   "orderCourseTimes":JSON.parse(JSON.stringify(orderCourseTimes))
               });
    	});
    	bussiness.order=order;
    	//添加课次信息
    	bussiness.order.orderCourseList=orderCourses;
    	$scope.genPayInfo(bussiness);
    	return bussiness;
    
    	
    }
    //////////////////////////////////////生成支付信息/////////////////////////////////////////////////////////////
    $scope.genPayInfo=function(bussiness){
    	var payInfo={};
    	payInfo.pay_cash=$scope.pay.cash;//现金
    	payInfo.pay_card= $scope.pay.card;//刷卡
    	payInfo.pay_trans=$scope.pay.transfer;//转账
    	payInfo.pay_account=$scope.pay.account;//储值账户
    	var payDetails=new Array();
    	var companyId= $scope.queryCourseParam['companyId'];
        ////////////////////////////////现金////////////////////////////////
    	if($scope.pay.cashList){
    	 $.each($scope.pay.cashList,function(i,data){
 			payDetails.push({
             "company_id":companyId,
             "client_card_no":null,//客户账号
             "company_card_no":null,
             "fee_amount":data.value,//金额
             "pay_way":1
         });
         });
    	}
        ////////////////////////////////刷卡////////////////////////////////
    	if($scope.pay.cardList){
    	 $.each($scope.pay.cardList,function(i,data){
    		 payDetails.push({
                 "company_id":companyId,
                 "client_card_no":data.card,//客户账号
                 "company_card_no":data.posId,
                 "fee_amount":data.value,//金额
                 "pay_way":2
          });
          });
    	}
       	/////////////////////////转账////////////////////////////////
    	if($scope.pay.transferList){
       	angular.forEach($scope.pay.transferList, function(data){
    			payDetails.push({
                "company_id":companyId,
                "client_card_no":data.card,//客户账号
                "company_card_no":data.accountId,
                "fee_amount":data.value,//金额
                "pay_way":3
            });
    		});
    	}
       	payInfo.details=JSON.parse(JSON.stringify(payDetails));
       	bussiness.payInfo=payInfo;
    }
    
    function genCourseTimes(course,orderCourseTimes){
    	angular.forEach(course.courseTimes, function(data){
    		if(data.checked){
    		orderCourseTimes.push({
                "times":data.time,
                "isValid":1
            });
    		}
    		});
        }
    
    //检查没有选择课次的课程
    function checkSelecetedCourseTimes(){
    	var msg="";
    	for(var i in $scope.selectedCourseList){
    		if($scope.selectedCourseList[i].selectedCourseTimes==0){
    			msg=$scope.selectedCourseList[i].courseName;
    			break;
    		}
    	}
    	  return msg;
    }
    
      //检查学员冲突的课次
    function checkConfilctStudentsCourseTimes(course,courseId){
    	 var param = {};
    	 $scope.selectedStudent = genSelectedStudent();
    	 param.studentId= $scope.selectedStudent.id;
    	 param.courseId=courseId;
         OrdersPubService.query(param,function(resp){
                 $scope.isLoadingBranchListSelected = '';
                 if(resp.error == 'false'){
                 	var confilctTimes = resp.data;
                 	for(var i in confilctTimes){
                 		course.courseTimes[parseInt(confilctTimes[i].times)-1].disabled=true;//禁用掉某个课次
                 		course.courseTimes[parseInt(confilctTimes[i].times)-1].checked=false;
                 	}
                 	var selectedCount=course.courseTimes.length-confilctTimes.length;
                    $.extend(course,{'selectedCourseTimes':selectedCount});
                    course.discount_sum_price=course.unit_price*course.selectedCourseTimes;
                 }
             });
    }
	  //改变课程现价
	    $scope.changeCoursePrice=function(course){
	    	course.actual_price=Math.floor(course.discount_sum_price/course.selectedCourseTimes);//向下取整
	    	course.manage_fee=course.discount_sum_price%course.selectedCourseTimes;
	    	course.discount_rate=parseFloat(course.actual_price/course.unit_price)//折扣
	    	course.discount_rate= course.discount_rate.toFixed(2);
	    	genSumPrice();
	    }
   
    //学员搜索列表
    queryStudentList();

    //年级查询
    queryGradeList();

    //课程季
    querySeason();

    //科目查询
    querySubject();

    //查询公司
    queryCompanys();

    //查询校区
    queryBranchs();

  /*  //查询课程
    queryCourse();*/

    //查询产品线
    queryProduct();

    //查询
    queryPos();

    //账户
    queryCompanyAccount();
}