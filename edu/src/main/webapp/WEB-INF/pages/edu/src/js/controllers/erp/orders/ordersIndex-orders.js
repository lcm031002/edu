/**
 * Created by Liyong.zhu on 2016/10/27.
 *
 * Modify by baiqb@klxuexi.org
 * 2017-10-19：
 *    - 添加在线支付功能
 */
"use strict";
angular
  .module('ework-ui')
  .controller('erp_OrdersIndexOrdersController', [
    '$rootScope',
    '$scope',
    '$cookieStore',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'erp_wfdComboService',
    'erp_studentsService',
    'erp_timeSeasonService',
    'erp_subjectService',
    'erp_gradeService',
    'erp_studentBuOrgsService',
    'erp_courseService',
    'erp_courseTimesService',
    'erp_privilegeRuleService',
    'erp_couponInfoService',
    'erp_studentIndexAccountService',
    'erp_companyAccountService',
    'erp_deviceService',
    'erp_orderManagerService',
    'PUBORGSelectedService',
    'erp_courseLadderService',
    'erp_sortNumService',
    'erp_courseListeningService',
    erp_OrdersIndexOrdersController
  ]);

function erp_OrdersIndexOrdersController(
  $rootScope,
  $scope,
  $cookieStore,
  $log,
  $state,
  $uibModal,
  $uibMsgbox,
  erp_wfdComboService,
  erp_studentsService,
  erp_timeSeasonService,
  erp_subjectService,
  erp_gradeService,
  erp_studentBuOrgsService,
  erp_courseService,
  erp_courseTimesService,
  erp_privilegeRuleService,
  erp_couponInfoService,
  erp_studentIndexAccountService,
  erp_companyAccountService,
  erp_deviceService,
  erp_orderManagerService,
  PUBORGSelectedService,
  erp_courseLadderService,
  erp_sortNumService,
  erp_courseListeningService
) {
  //学员信息
  $scope.student = {};
  // 当前组织机构
  $scope.selectedOrg = {};
  queryBuOrgs();
  //学员账户信息
  $scope.studentIndexAccount = {};
  //学员咨询学管
  $scope.studentIndexCounselors = {};
  //学员报班记录查询类型
  $scope.queryHistoryType = 0;
  //暂存的订单
  $scope.temporaryOrder = null;
  // 业务类型
  $scope.business_type = 1;
  // 科目列表
  $scope.subjectList = [];
  // 已选择的科目
  $scope.selectedSubject = null;
  $scope.gradeList = [];
  $scope.selectedGrade = null;
  $scope.selectedTimeSeason = null;
  $scope.timeSeasonList = [];
  $scope.selectedBranch = null;
  $scope.selectedCourseLadder = null;
  $scope.selectedCourseList = [];
  $scope.selectedDXBCourseList = [];
  $scope.selectedWFDCourseList = [];
  $scope.selectedYDYCourseList = [];
  $scope.studentIndexAccount = {};
  $scope.searchInfo = '';
  $scope.studentQueryInfo = {};
  $scope.selectedCourseList = [];
  // 试听课程列表
  $scope.courseListeningList = [];
  //产品线 1-培英精品班 2-个性化 11-佳音
  $scope.productLine = 1;
  
  $scope.conf = {
    showAllBranch: false,
    showAllTimeSeason: false,
    showAllGrade: false,
    showAllSubject: false
  }
  $('title').text('报班 | 厝边素高');
  //整体优惠规则类型定义
  $scope.Preferential = {
    tabType: 'PreferentialRules',
    immediate_reduce: null,
    immediate_remark: null
  }
  $scope.studentList = [];
  $scope.Preferential.tabType = 'PreferentialRules';
  //获取传入的订单id
  $scope.studentId = $("#rootIndex_studentId").val() || $state.params.studentId;

  $scope.payment = {
    cash: 0,
    card: 0,
    card_detail: [],
    transfer: 0,
    transfer_detail: [],
    onlinePrice: 0, // 在线支付
    onlinePayEnable: true,
    storeAccount: 0, // 储值账户    
    frozenAccount: 0, // 冻结账户
    paymentAccount: 0
  };
  
  //暂存的订单
  var orderId = $("#rootIndex_temporaryOrderId").val();
  if (orderId) {
    $scope.openPanel = 'isLoadingTemporaryOrder';
    erp_orderManagerService.query({
      order_id: orderId,
      orderType: 'temporaryOrder'
    }, function(resp) {
      $scope.openPanel = '';
      if (!resp.error) {
        $scope.temporaryOrder = resp.data;
        initialTemporaryOrder();
        if ($scope.temporaryOrder.billNo && $scope.temporaryOrder.onlinepay_date) {
          $scope.payment.onlinePayEnable = false
          $scope.payment.onlinePayBtnLabel = '查询订单支付信息...'
          erp_orderManagerService.getPayOnlineResult({
            billDate: $scope.temporaryOrder.onlinepay_date,
            billNo: $scope.temporaryOrder.billNo
          }).$promise.then(function (resp) {
            $scope.payment.onlinePayBtnLabel = '缴费'
            var res = JSON.parse(resp.data)
            if (res.billStatus == 'PAID' && res.totalAmount) {
              // 银联在线支付金钱单位是分，所以需要除以100
              $scope.payment.onlinePrice = res.totalAmount / 100
              $scope.payment.onlinePayEnable = false
            } else {
              $scope.payment.onlinePrice = 0
              $scope.payment.onlinePayEnable = true
            }
          }, function (resp) {
            $uibMsgbox.error('查询报班单在线支付信息失败！请联系管理员！')
            console.log(resp)
            $scope.payment.onlinePayBtnLabel = '缴费'
          })
        }
        if ($scope.temporaryOrder.check_status == 3 && $scope.temporaryOrder.valid_status && !$scope.temporaryOrder.pay_status) {
          $scope.nextStep(3, 4);
        }
        if ($scope.temporaryOrder.check_status == 1 && $scope.temporaryOrder.valid_status && !$scope.temporaryOrder.pay_status) {
          $.each($scope.temporaryOrder.details, function(i, detail) {
            if ($scope.temporaryOrder.business_type == 1) {
              $scope.selectedDXBCourseList.push(detail.tCourseInfo);
              $scope.selectedCourseList = $scope.selectedDXBCourseList;
            }
            if ($scope.temporaryOrder.business_type == 2) {
              $scope.selectedYDYCourseList.push(detail.tCourseInfo);
              $scope.selectedCourseList = $scope.selectedYDYCourseList;
            }
            if ($scope.temporaryOrder.business_type == 3) {
              $scope.selectedWFDCourseList.push(detail.tCourseInfo);
              $scope.selectedCourseList = $scope.selectedWFDCourseList;
            }
          });
          $scope.selectedDXBCourseList.push();
          $scope.nextStep(1, 2);
        }
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  /**
   * 从暂存订单进行初始化
   */
  function initialTemporaryOrder() {
    //学员
    if ($scope.temporaryOrder.studentInfo) {
      $scope.studentList = [];
      $scope.studentList.push($scope.temporaryOrder.studentInfo);
      $scope.curStudent = $scope.temporaryOrder.studentInfo;
      $scope.student = $scope.temporaryOrder.studentInfo;
      $scope.temporaryOrder.studentInfo.checked = true;
    }


    //选课
    $scope.selectedCourseList = [];
    $.each($scope.temporaryOrder.details, function(i, detail) {

      if (detail.tCourseInfo) {
        $scope.selectedCourseList.push(detail.tCourseInfo);
        detail.tCourseInfo.selectedCourseTimesCount = detail.course_total_count;
        if (detail.orderCourseTimes && detail.tCourseInfo.courseSchedulingList) {

          $.each(detail.tCourseInfo.courseSchedulingList, function(j, courseScheduling) {
            var checked = false;
            $.each(detail.orderCourseTimes, function(h, courseTime) {
              if (courseScheduling.course_times == courseTime.course_times) {
                checked = true;
              }
            });
            courseScheduling.checked = true;
          })
        }
      }


    });
    //如果是立减
    if ($scope.temporaryOrder.immediate_reduce) {
      $scope.Preferential.tabType = 'PreferentialReduction';
      $scope.Preferential.immediate_reduce = $scope.temporaryOrder.immediate_reduce;
      $scope.Preferential.immediate_remark = $scope.temporaryOrder.extend_column;
    }
    //优惠券
    if ($scope.temporaryOrder.coupon_rels) {
      $.each($scope.temporaryOrder.coupon_rels, function(i, coupon) {
        var param = {};
        param.encoding = coupon.coupon_encoding;
        erp_couponInfoService.query(param, function(resp) {
          if (!resp.error) {
            if (resp.data) {
              var exist = false;
              if ($scope.foundedCouponInfos) {
                $.each($scope.foundedCouponInfos, function(j, couponInfo) {
                  if (couponInfo.id == resp.data.id) {
                    exist = true;
                  }
                });
              }

              if (!exist) {
                $scope.foundedCouponInfos.push(resp.data);
                resp.data.checked = true;
              } else {
                $uibMsgbox.alert("已添加！");
              }

            }
          } else {
            $uibMsgbox.alert(resp.message);
          }
        })
      });
    }

    $scope.selectedOrder = {};
    $.extend($scope.selectedOrder, $scope.temporaryOrder);
  }

  /**
   * 查询学员
   */
  $scope.queryStudents = function() {
    var param = {
      pageSize: 10,
      row_num: 10,
      need_contact: '1',
      searchInfo: $scope.studentQueryInfo.searchInfo,
      searchType: $scope.exactSearch ? 1 : 0
    };
    if($scope.studentId){
      param.studentId = $scope.studentId;
    }
    $scope.isQueryStudent = true;
    $scope.studentList = [];
    erp_studentsService.query(param,
      function(resp) {
        $scope.isQueryStudent = false;
        if(!resp.error){
          $scope.studentList = [];
          if(resp.data && resp.data.length > 0 && $scope.studentId) {
            $scope.student = resp.data[0];
            $scope.productLine = $scope.student.product_line;
            $scope.student.checked = true;
            $scope.curStudent = $scope.student;
            $scope.studentList.push($scope.student);
          }else{
            $scope.studentList = resp.data;
          }
          if (resp.data && resp.data.length == 0) {
            $uibMsgbox.confirm('查询不到对应的学员，是否立即创建学员？', function(res) {
              if (res == 'yes') {
                window.open('#/students/newStudent')
              }
            })
          }
        }else{
          $uibMsgbox.alert(resp.message);
        }

         if ($scope.studentId != null && !$("#rootIndex_temporaryOrderId").val()) {
            $scope.nextStep(1, 2);
         }
      });
  };

  /**
   * 选择学员
   * @param student
   */
  $scope.checkedStudent = function(student) {
    if (student.checked) {
      student.checked = false;
      $scope.studentId = null;
      $scope.student = undefined;
      $scope.productLine = 1;
    } else {
      student.checked = true;
      $scope.student = student;
      $scope.productLine = student.product_line;

      if ($scope.curStudent && $scope.curStudent.id != student.id) {
        $scope.curStudent.checked = false;
        $scope.selectedCourseList = [];
        $scope.selectedDXBCourseList = [];
        $scope.selectedWFDCourseList = [];
        $scope.selectedYDYCourseList = [];
      }
      $scope.curStudent = $scope.student;
      $scope.studentId = $scope.curStudent.id;
    }
  }


  /**
   * 下一步、上一步
   * @param before
   * @param next
   */
  $scope.nextStep = function(before, next) {
    if (next == 3) {
      if ($scope.business_type == 1) {
        $scope.selectedCourseList = $scope.selectedDXBCourseList;
      }
      if ($scope.business_type == 2) {
        $scope.selectedCourseList = $scope.selectedYDYCourseList;
      }
      if ($scope.business_type == 3) {
        $scope.selectedCourseList = $scope.selectedWFDCourseList;
      }
      console.log($scope.selectedCourseList)
      if (!checkSelectedCourse()) {
        return;
      }
    }
    if (next == 4 && before == 3) {
        $scope.isFushu = false;
        $.each($scope.selectedCourseList, function(i, course) {
           if(course.discount_sum_price < 0){
               $scope.isFushu = true;
           }
        });

        if($scope.isFushu){
            $uibMsgbox.alert("课程单价不允许为负数，请重新确认!");
            return;
        }

      if ($scope.temporaryOrder && ($scope.temporaryOrder.check_status == 1 || !$scope.temporaryOrder.check_status)) {
        //检查是否需要走审批，如果需要，则先走审批
        $scope.saveOrder();
        return;
      } else if ($scope.temporaryOrder && $scope.temporaryOrder.check_status == 2) {
        //审核中，走提示已经提交审核，流程结束
        $uibMsgbox.alert("订单审核中！");
        return;
      } else if ($scope.temporaryOrder && $scope.temporaryOrder.check_status == 3) {

      } else {
        //检查是否需要走审批，如果需要，则先走审批
        $scope.saveOrder();
        return;
      }

    }
    /**
     * Fixed Bug  648 勾选 学员，会过滤，把勾选去掉后，不会恢复了（操作返回上一步）
     * @baiqb 2017/5/24 16:02:39
     * 
     */
    // if ($scope.step == 1) {
    //   $scope.studentList = [];
    //   $scope.studentList.push($scope.curStudent);
    // }
    // END Fixed Bug
     
    /**
     * Modify baiqb@klxue.org
     * 如果产品线是个性化的，选择课程时默认打开个性化标签
     *  productLine: 1为培英,2为个性化，11为佳音，仅在选择个性化时，课程选择默认打开1对1标签
     */
    if (before == 1 && next == 2) {
      // if ($scope.student.active != 1) {
      //   $uibMsgbox.alert("非活跃学员，不能报班!");
      //   return;
      // }
      if ($scope.selectedOrg.productLine == 2) {
        $scope.business_type = 2
      } else {
        $scope.business_type = 1
      }
      
      /**
       * Add By baiqb@klxuexi.org
       * 添加试听课程列表
       */
      queryListeningCourseList();
    }

    $scope.step = next;

    if ($scope.step == 3) {
      if ($scope.business_type == 1) {
        $scope.selectedCourseList = $scope.selectedDXBCourseList;
      }
      if ($scope.business_type == 2) {
        $scope.selectedCourseList = $scope.selectedYDYCourseList;
      }
      if ($scope.business_type == 3) {
        $scope.selectedCourseList = $scope.selectedWFDCourseList;
      }
      queryPrivilegeRule();
      CalculationOrders();
      if ($scope.temporaryOrder) {
        $scope.selectedOrder.remark = $scope.temporaryOrder.remark;
      }
    }
    if ($scope.step == 4) {
      queryCompanyAccount();
      queryDeviceInfo();
      queryStudentAccount();
    }
  }

  /**
   * 查询试听课程列表
   * student_id: 学员id
   * business_type: 业务类型，1：班级课，2：一对一，3：晚辅导，目前只选择1
   * @return {[type]} [description]
   * 
   * @author: baiqb@klxuexi.org
   * @date: 2017/5/26 21:47:18
   */
  function queryListeningCourseList() {
    $scope.courseListeningList = []
    erp_courseListeningService.query({
      student_id: $scope.studentId,
      business_type: 1,
      isPage: 0 //不分页
    }, function (resp) {
      if (!resp.error) {
        $scope.courseListeningList = resp.data;
        _.forEach( function() {
          item.isSelected = false; //默认课程未选择
        });
      }
    })
  }

  function checkSelectedCourse() {
    if ((!$scope.selectedDXBCourseList || !$scope.selectedDXBCourseList.length) && (!$scope.selectedWFDCourseList || !$scope.selectedWFDCourseList.length) && (!$scope.selectedYDYCourseList || !$scope.selectedYDYCourseList.length)) {
      return false;
    }
    var selectedCourseTimes = true;

    $.each($scope.selectedCourseList, function(i, course) {
      if (!course.selectedCourseTimesCount) {
        selectedCourseTimes = false;
        var msg='';
        if($scope.business_type==1){
          msg="请选择课程课次！如果课程课次已满，且已排号，请到【排号业务】中查看排号详情";
        }
        if($scope.business_type==2){
          msg="请填写报班课时";
        }
        if($scope.business_type==3){
          msg="请填写报班课次";
        }
        $uibMsgbox.alert(msg);
      }
    });
    return selectedCourseTimes;
  }

  /**
   * 查询学员账户
   */
  function queryStudentAccount() {
    var param = {};
    param.studentId = $scope.student.id;
    erp_studentIndexAccountService.query(param, function(resp) {
      if (!resp.error) {
        $scope.studentIndexAccount = resp.data;
      }
    });
  }

  //默认走第一步
  $scope.step = 1;

  $scope.selectBusinessType = function(business_type) {
    // 页签未变动，不处理
    if($scope.business_type == business_type) {
      return;
    }
    if($scope.business_type == 1 && $scope.selectedDXBCourseList.length > 0 ||
       $scope.business_type == 2 && $scope.selectedYDYCourseList.length > 0 ||
       $scope.business_type == 3 && $scope.selectedWFDCourseList.length > 0) {
          $uibMsgbox.warn("已选定报班课程，不可切换页签！");
      return;
    }
    $scope.business_type = business_type;
    if (business_type == 3) {
      //            queryComboWfd();
    }
    return true;
  };

  /**
   * 展开和关闭课程
   */
  $scope.toSelectedCourse = function() {
    $scope.openPanel = 'toSelectedCourseState'
    $scope.querySelectingCourse();
  }


  /**
   * 查询课程季
   */
  function queryTimeSeason() {
    erp_timeSeasonService.list({}, function(resp) {
      if (!resp.error) {
        $scope.timeSeasonList = resp.data;
      }
    })
  }

  /**
   * 查询科目
   */
  function querySubject() {
    erp_subjectService.querySelectDatas({
      branch_id: $scope.selectedBranch ? $scope.selectedBranch.id : -1,
      season_id: $scope.selectedTimeSeason ? $scope.selectedTimeSeason.id : -1,
      grade_id: $scope.selectedGrade ? $scope.selectedGrade.id : -1
    }, function(resp) {
      if (!resp.error) {
        $scope.subjectList = resp.data;
      }
    })
  }

  /**
   * 查询年级
   */
  function queryGrade() {
    erp_gradeService.querySelectDatas({
      branch_id: $scope.selectedBranch ? $scope.selectedBranch.id : -1,
      season_id: $scope.selectedTimeSeason ? $scope.selectedTimeSeason.id : -1
    }, function(resp) {
      if (!resp.error) {
        $scope.gradeList = resp.data;
        if ($scope.student && $scope.student.grade_id) {
          $.each($scope.gradeList, function(i, grade) {
            if (grade.id == $scope.student.grade_id) {
              $scope.selectedGrade = grade;
            }
          })
        }
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  /**
   * 查询校区
   */
  function queryBuOrgs() {
    erp_studentBuOrgsService.queryAll({}, function(resp) {
      if (!resp.error) {
        $scope.branchList = resp.data;
        querySelectedOrg();
      }
    })
  }

  function querySelectedOrg(){
      PUBORGSelectedService.query({},function(resp){
          if(!resp.error){
              $scope.selectedOrg = resp.data;
              if($scope.selectedOrg&&$scope.selectedOrg.id&&$scope.selectedOrg.type=="4"){
                  $.each($scope.branchList,function(i,b){
                      if(b.id == $scope.selectedOrg.id){
                    	  $scope.selectedBranch = b;
                      }
                  });
                  queryTimeSeason();
                  querySubject();
                  queryGrade();
              }else{
              	$uibMsgbox.warn("请选择校区!");
              }
          }else{
          	$uibMsgbox.error(resp.message);
          }
      })
  }
  /**
   * 选择校区
   * @param branch
   */
  $scope.selectBranch = function(branch) {
    $scope.selectedBranch = branch;
    querySubject();
    queryGrade();
    $scope.querySelectingCourse();
  }

  /**
   * 选择课程季
   * @param season
   */
  $scope.selectSeason = function(season) {
    $scope.selectedTimeSeason = season;
    querySubject();
    queryGrade();
    $scope.querySelectingCourse();
  }

  /**
   * 选择年级
   * @param grade
   */
  $scope.selectGrade = function(grade) {
    $scope.selectedGrade = grade;
    querySubject();
    $scope.querySelectingCourse();
  }

  /**
   * 选择科目
   * @param subject
   */
  $scope.selectSubject = function(subject) {
    $scope.selectedSubject = subject;
    $scope.querySelectingCourse();
  }

  $scope.paginationConf = {
    currentPage: 1, //当前页
    totalItems: 0,
    itemsPerPage: 10,
    onChange: function () {
      $scope.querySelectingCourse();
    }
  };

  /**
   * 查询课程
   */
  $scope.querySelectingCourse = function() {
    if ($scope.business_type == 1) {
      $scope.selectedCourseList = $scope.selectedDXBCourseList;
    }
    if ($scope.business_type == 2) {
      $scope.selectedCourseList = $scope.selectedYDYCourseList;
    }
    if ($scope.business_type == 3) {
      $scope.selectedCourseList = $scope.selectedWFDCourseList;
    }
    
    var param = {
      pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
      currentPage: $scope.paginationConf.currentPage,// 要获取的第几页的数据
      branch_id: $scope.selectedBranch.id,
      season_id: $scope.selectedTimeSeason ? $scope.selectedTimeSeason.id : -1,
      grade_id: $scope.selectedGrade ? $scope.selectedGrade.id : -1,
      subject_id: $scope.selectedSubject ? $scope.selectedSubject.id : -1,
      business_type: $scope.business_type,
      status : 1, //只查询上架课程
      search_info: $("#courseSearchInfo").val()
    };
    $scope.isQuerySelectingCourse = 'isQuerySelectingCourse';
   
    erp_courseService.query(param, function(resp) {
      $scope.toSelectingCourseList = [];
      $scope.isQuerySelectingCourse = '';
      if (!resp.error) {
        var selectedCount = 0;
        var toSelectingCourseList = resp.data;
        $.each(toSelectingCourseList, function(i, course) {
          var selected = false;
          $.each($scope.selectedCourseList, function(j, selectedCourse) {
            if (selectedCourse.id == course.id) {
              selected = true;
              selectedCount ++;
            }
          });
          if (!selected) {
            $scope.toSelectingCourseList.push(course);
          }
        });
        $scope.paginationConf.totalItems = resp.total; //设置总条数
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  function queryComboWfd() {
    var branch_id = $scope.selectedBranch.id;
    erp_wfdComboService.query({
      pageSize: 999,
      p_grade: $scope.selectedGrade ? $scope.selectedGrade.id : $scope.selectedGrade,
      p_branch_id: branch_id
    }, function(response) {
      if (!response.error) {
        $scope.comboWfds = response.data;
        $scope.comboWfdsRec = [];
        $.each($scope.comboWfds, function(k, wfd) {
          $scope.comboWfdsRec.push(wfd);
        });
      }
    });
  }

  function queryLadder(course) {
	  
	  erp_courseLadderService.list({p_course_id :course.id
	    }, function(response) {
	      if (!response.error) {
	        $scope.ladders = response.data;
	      }
	    });
  }

  /**
   * 选择课程
   * @param course
   */
  $scope.selectedCourse = function(course) {
    //        $scope.selectedCourseList.push(course);
    if (course.branch_kind == 12 && !course.more_teacher_courseid) {
      $uibMsgbox.error("双师分场课程还未加入双师课程档案包，不能报班！");
      return;
    }
    course.selectedCourseTimesCount = 0;
    $scope.openPanel = '';
    if ($scope.business_type == 1) {
      $scope.selectedDXBCourseList.push(course);
      hideListeningCourseTimes(course);
      $scope.openCourseTimesPanel(course);
    }
    if ($scope.business_type == 2) {
      course.branch_name=$scope.selectedBranch.org_name;
      course.branch_id=$scope.selectedBranch.id;
      $scope.selectedYDYCourseList.push(course);
      queryLadder(course);
    }
    if ($scope.business_type == 3) {
      $scope.selectedWFDCourseList.push(course);
      queryComboWfd();
    }

  }
  function hideListeningCourseTimes (course) {
    setListeningCourseTimesSelected(course, true)
  }
  function showListeningCourseTimes (course) {
    setListeningCourseTimesSelected(course, false)
  }
  function setListeningCourseTimesSelected (course, action) {
    _.forEach($scope.courseListeningList, function(item) {
      if (item.COURSE_ID == course.id) {
        item.courseSelected = action;
      }
    })
  }

  /**
   * 移除课程
   * @param course
   */
  $scope.removeCourse = function(course) {
    if ($scope.business_type == 1) {
      removeDXBCourse(course);
    }
    if ($scope.business_type == 2) {
    	removeYDYCourse(course);
      }
    if ($scope.business_type == 3) {
      removeWFDCourse(course);
    }
    $scope.toSelectedCourse();
  }

  function removeDXBCourse(course) {
    var selectedDXBCourseList = [];
    $.each($scope.selectedDXBCourseList, function(i, model) {
      if (model.id != course.id) {
        selectedDXBCourseList.push(model);
      }
    });
    $scope.selectedDXBCourseList = selectedDXBCourseList;
    showListeningCourseTimes(course)
  }
  
  function removeYDYCourse(course) {
	    var selectedYDYCourseList = [];
	    $.each($scope.selectedYDYCourseList, function(i, model) {
	      if (model.id != course.id) {
	        selectedYDYCourseList.push(model);
	      }
	    });
	    $scope.selectedYDYCourseList = selectedYDYCourseList;
	  }

  function removeWFDCourse(course) {
    var selectedWFDCourseList = [];
    $.each($scope.selectedWFDCourseList, function(i, model) {
      if (model.id != course.id) {
        selectedWFDCourseList.push(model);
      }
    });
    $scope.selectedWFDCourseList = selectedWFDCourseList;
  }

  /**
   * 修改课时课次信息
   * @param course
   */
  $scope.toChangeCourseTimes = function(course) {
    $scope.currentCourse = course;
  }

  $scope.curCourse = null;

  /**
   * 试听报名
   * @param  {[type]} courseId [description]
   * @return {[type]}          [description]
   */
  $scope.handleListeningSignUp = function(courseId) {
    erp_courseService.query({
      course_id: courseId
    }, function (resp) {
      if (!resp.error && resp.data && resp.data.length > 0) {
        $scope.selectedCourse(resp.data[0])
      } else {
        $uibMsgbox.error(resp.message);
      }
    })
  }
  
  /**
   * 打开课次面板
   * @param course
   */
  $scope.openCourseTimesPanel = function(course) {
    $scope.curCourse = course || $scope.curCourse;
    $scope.openPanel = 'showCourseTimes';
    queryCourseTimes();

    /**
     * 查询课次明细
     * @return {[type]} [description]
     */
    function queryCourseTimes() {
      var param = {};
      param.courseId = course.id;
      param.studentId = $scope.student.id;
      $scope.isLoadingCourseTimesPanel = 'isLoadingCourseTimesPanel';
      erp_courseTimesService.query(param, function(resp) {
        $scope.isLoadingCourseTimesPanel = '';
        if (!resp.error) {
          course.courseSchedulingList = resp.data;

          if (course.checkedAllTimes == undefined) {
            var checkedAllTimes = true;
            _.forEach(course.courseSchedulingList, function(node) {
              
              if (!node.is_ordered) {
                node.checked = true;
              } else {
                checkedAllTimes = false;
              }
            })
            course.checkedAllTimes = checkedAllTimes;
          } else {
            course.courseTimeList = course.courseTimeList || [];
            _.forEach(course.courseSchedulingList, function(item) {
              if (_.some(course.courseTimeList, {id: item.id})) {
                item.checked = true;
              }
            })
          }
          course.canSelCourseList = []
          _.forEach(course.courseSchedulingList, function(node) {
              // 查询每个课次是否在试听列表中有存在，如果存在，则设置为已试听
              // 且禁用反选
              if (_.some($scope.courseListeningList, {
                COURSE_ID: node.course_id,
                COURSE_TIMES: node.course_times.toString()
              })) {
                node.is_listening = true;
              }
          	  if ((!node.is_ordered && !node.people_count )
                    ||(!node.is_ordered && node.people_count 
                        &&node.people_count > node.people_checkCount)) {
              course.canSelCourseList.push(node)
            }
          })
          countCourseTimesChecked(course);
        }
      })
    }
  }

  /**
   * 选择课次
   * @param courseTime
   */
  $scope.checkedCourseTimes = function(courseTime) {
    if (courseTime.checked) {
      courseTime.checked = false;
    } else {
      courseTime.checked = true;
    }
    countCourseTimesChecked($scope.curCourse);
  }

  /**
   * 选择所有的课次
   */
  $scope.checkedAllCourseTimes = function() {
    if ($scope.curCourse.checkedAllTimes) {
      if ($scope.curCourse.canSelCourseList) {
        $.each($scope.curCourse.canSelCourseList, function(i, node) {
          if(!node.is_listening) {
            node.checked = false;
          }
        });
      }
    } else {
      if ($scope.curCourse.canSelCourseList) {
        $.each($scope.curCourse.canSelCourseList, function(i, node) {
          if ((!node.is_ordered && !node.people_count )||
            (!node.is_ordered && node.people_count &&node.people_count > node.people_checkCount)) {
            node.checked = true;
          }
        });
      }
    }

    countCourseTimesChecked($scope.curCourse);
  }

  /**
   * 计算当前所有计算的课次
   * @param course
   */
  function countCourseTimesChecked(course) {
    if (course.canSelCourseList) {
      var count = 0;
      course.courseTimeList = []
      $.each(course.canSelCourseList, function(i, node) {
        if (node.checked) {
          count++;
          course.courseTimeList.push(node)
        }
      });
      course.selectedCourseTimesCount = count;
      course.SUM_PRICE = course.ACTUAL_PRICE * course.selectedCourseTimesCount;

      if (course.selectedCourseTimesCount == course.canSelCourseList.length) {
        course.checkedAllTimes = true;
      } else {
        course.checkedAllTimes = false;
      }
    }
  }

  /**
   * 关闭窗口
   */
  $scope.closePanel = function() {
    $scope.openPanel = '';
  }


  /**
   * 查询优惠规则
   */
  function queryPrivilegeRule() {
    $scope.privilegeRuleList = [];
    erp_privilegeRuleService.query({
        productLine: $scope.business_type,
        isOrder: 'true'
    }, function(resp) {
      if (!resp.error) {
        $scope.privilegeRuleList = resp.data;
        $scope.privilegeRuleList.unshift({
          id: -1,
          rule_name: ""
        });
        _.forEach($scope.selectedCourseList, function(course) {
          if (course.selectedRule) {
            course.selectedRule = _.find($scope.privilegeRuleList, {id: course.selectedRule.id})
          }
        })
        //暂存初始化优惠规则
        if ($scope.temporaryOrder) {
          //科目级
          $.each($scope.temporaryOrder.details, function(i, course) {
            if (course.rule_id) {
              $.each($scope.privilegeRuleList, function(j, rule) {
                if (rule.id == course.rule_id) {
                  if (course.tCourseInfo) {
                    course.tCourseInfo.selectedRule = rule;
                  } else {
                    $log("error found,course info not found,course_id is " + course.course_id);
                  }

                }
              })
            }
          });

          //订单级
          if ($scope.temporaryOrder.rule_id) {
            $.each($scope.privilegeRuleList, function(j, rule) {
              if (rule.id == $scope.temporaryOrder.rule_id) {
                $scope.theWholeRule = rule;
              }
            })
          }

          CalculationOrders();
        }
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  $scope.coupon = { '1': '折扣', '2': '金额', '3': '每课时优惠' };
  $scope.use_scope_types = { '1': '通用', '2': '老学员', '3': '新学员', '4': '推荐人', '5': '被推荐人' };
  $scope.theWholeRule = null;
  $scope.foundedCouponInfos = [];
  $scope.CouponInfo = {};

  /**
   * 选择整体优惠规则
   * @param rule
   */
  $scope.selectedWholeRule = function(rule) {
    if ($scope.theWholeRule && $scope.theWholeRule.id == rule.id) {
      $scope.theWholeRule = undefined;
    } else {
      $scope.theWholeRule = rule;
    }
    CalculationOrders();
  }

  /**
   * 获取优惠券
   */
  $scope.queryCouponInfo = function() {
    var param = {};
    param.encoding = $scope.CouponInfo.inputCouponInfoEncoding;
    erp_couponInfoService.query(param, function(resp) {
      if (!resp.error) {
        if (resp.data) {
          var exist = false;
          if ($scope.foundedCouponInfos) {
            $.each($scope.foundedCouponInfos, function(i, couponInfo) {
              if (couponInfo.id == resp.data.id) {
                exist = true;
              }
            });
          }

          if (!exist) {
            $scope.foundedCouponInfos.push(resp.data);
          } else {
            $uibMsgbox.alert("已添加！");
          }
        } else {
        	$uibMsgbox.alert("没有该优惠券信息！");
        }
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  /**
   * 选中优惠券
   * @param couponInfo
   */
  $scope.checkCouponInfo = function(couponInfo) {
    if (couponInfo.checked) {
      couponInfo.checked = false;
    } else {
      couponInfo.checked = true;
    }
    CalculationOrders();
  }

  //选择单科目优惠规则
  $scope.changeCourseSelectedRule = function() {
    CalculationOrders();
  }



  $scope.selectTab = function(tabName) {
    $scope.Preferential.tabType = tabName;
    CalculationOrders();
    return true;
  }


  /**
   * Add by baiqb@klxuexi.org
   * 如果是暂存订单，增加在线支付判断
   */
  var ids = 1;

  /**
   * 立减
   */
  $scope.changeImmediateReduce = function() {
    if ($scope.Preferential.tabType == 'PreferentialReduction' && $scope.Preferential.immediate_reduce && !genNumByString($scope.Preferential.immediate_reduce)) {
      $scope.Preferential.immediate_reduce = null;
      return;
    }
    CalculationOrders();
  }

  /**
   * 添加现金
   */
  $scope.addCash = function() {
    $scope.payment.cash = genNumByString($scope.payment.paymentCash);
    $scope.payment.cash = !$scope.payment.cash ? 0 : $scope.payment.cash;
    $scope.openPanel = '';
  }

  /**
   * 储值账户缴费
   */
  $scope.addStoreAccount = function() {
    var paymentAccount = genNumByString($scope.payment.paymentStoreAccount);
    paymentAccount = !paymentAccount ? 0 : paymentAccount;
    if (paymentAccount > $scope.studentIndexAccount.STORE_ACCOUNT) {
      $uibMsgbox.alert("账户余额不足！");
      return;
    }
    $scope.payment.storeAccount = genNumByString(paymentAccount);
    $scope.payment.storeAccount = !$scope.payment.storeAccount ? 0 : $scope.payment.storeAccount;
    $scope.openPanel = '';
  }

  /**
   * 冻结账户缴费
   */
  $scope.addFrozenAccount = function() {
    var paymentAccount = genNumByString($scope.payment.paymentFrozenAccount);
    paymentAccount = !paymentAccount ? 0 : paymentAccount;
    if (paymentAccount > $scope.studentIndexAccount.FROZEN_ACCOUNT) {
      $uibMsgbox.alert("账户余额不足！");
      return;
    }
    $scope.payment.frozenAccount = genNumByString(paymentAccount);
    $scope.payment.frozenAccount = !$scope.payment.frozenAccount ? 0 : $scope.payment.frozenAccount;
    $scope.openPanel = '';
  }

  /* 刷卡操作 */
  $scope.toCardPrice = function() {
      $scope.inputCradInfo = {};
      $scope.inputCradInfo.account = $scope.accountListPos[0].id;
      $scope.inputCradInfo.account_cmp = $scope.accountListPos[0].account_name;
      $scope.inputCradPrice = true;

      /** 添加刷卡记录 */
      $scope.addInputCradInfo = function() {
        var str = Format('yyyy-MM-dd hh:mm:ss', new Date());
        var staffappprem = genFloatByString($scope.inputCradInfo.staffappprem);
        if (!staffappprem) {
          $scope.inputCradInfo.staffappprem = 0;
          // 保存失败了
          $uibMsgbox.alert('请输入刷卡金额！');
          return;
        }
        $scope.inputCradInfo.staffappprem = staffappprem;

        if ($scope.inputCradInfo.staffappprem <= 0) {
          // 保存失败了
          $uibMsgbox.alert('请输入正确的刷卡金额！');
          return;
        }
        if (!$scope.inputCradInfo.client_card_no) {
          // 保存失败了
          $uibMsgbox.alert('请输入刷卡卡号！');
          return;
        }
        // 获取pos机信息
        genPosID($scope.inputCradInfo, $scope.accountListPos);
        var detailInfo = {
          client_card_no: $scope.inputCradInfo.client_card_no,
          staffappprem: parseFloat($scope.inputCradInfo.staffappprem),
          payment_way: 2,
          createTime: str,
          simple_cmp_name: $scope.inputCradInfo.account_cmp,
          pos_id: $scope.inputCradInfo.pos_id,
          company_card_id: $scope.inputCradInfo.company_card_id,
          account_name: $scope.inputCradInfo.simple_name,
          extend_column: $scope.inputCradInfo.extend_column
        };
        detailInfo.id = ++ids;
        if (submitCardPrice(detailInfo) == -1) {
          return;
        }
       detailInfo.simple_cmp_name = $scope.inputCradInfo.account_cmp;

        // 增加一条详情
        $scope.payment.card_detail[$scope.payment.card_detail.length] = detailInfo;
        // 点击确定，新增行清空金额和卡号信息 add by lincm 20170824
        $scope.inputCradInfo.client_card_no = null;
        $scope.inputCradInfo.staffappprem = null;

        // 获取单项总计
        $scope.payment.card = sumItemsPrice($scope.payment.card_detail);
      };
      /**
       * 移除刷卡记录
       */
      $scope.removeCardPrice = function(id) {
        removeCardPrice(id);
      };

    }
    /* 提交刷卡信息 */
  function submitCardPrice(cardDetail) {
    var param = {
      studentId: $scope.studentId,
      pay_mode: cardDetail.payment_way,
      money: cardDetail.staffappprem,
      stu_card: cardDetail.client_card_no,
      company_card_id: cardDetail.company_card_id,
      remark: cardDetail.extend_column,
      id: cardDetail.id
    };
    if (param.remark != null && param.remark.length > 100) {
      $uibMsgbox.alert("描述信息过长！");
      return -1;
    }
    if (!param.stu_card || param.stu_card.length > 29) {
      $uibMsgbox.alert("请输入正确的卡号！");
      return -1;
    }
    if (genNumByString(param.money) <= 0) {
      $uibMsgbox.alert("请输入正确的金额！");
      return -1;
    }
    return 1;
  }

  function sumAllCardMoney() {
    $.each($scope.payment.card_detail, function(i, card) {
      $scope.payment.card = $scope.payment.card + genNumByString(card.money);
    });
  }

  function removeCardPrice(id) {
    if ($scope.payment.card_detail) {
      var card_detail = [];
      $.each($scope.payment.card_detail, function(i, model) {
        if (model.id != id) {
          card_detail.push(model);
        }
      });
      $scope.payment.card_detail = card_detail;
      $scope.payment.card = sumItemsPrice($scope.payment.card_detail);

    }
  }

  /* 转账操作 */
  $scope.toTransferPrice = function() {
    $scope.transferPriceData = {};
    // 初始化公司账户信息
    $scope.transferPriceData.account = $scope.accountTransferList[0].ID;
    $scope.transferPriceData.account_cmp = $scope.accountTransferList[0].ACCOUNT_NAME;
    $scope.inputTransferPrice = true;


    // 添加操作
    $scope.addInputTransferPrice = function() {
      var staffappprem = genFloatByString($scope.transferPriceData.staffappprem);
      if (!staffappprem) {
        $scope.transferPriceData.staffappprem = 0;
        // 保存失败了
        $uibMsgbox.alert('请输入转账金额！');
        return;
      }
      $scope.transferPriceData.staffappprem = staffappprem;

      if ($scope.transferPriceData.staffappprem <= 0) {
        // 保存失败了
        $uibMsgbox.alert('请输入正确的转账金额！');
        return;
      }
      if (!$scope.transferPriceData.client_card_no) {
        // 保存失败了
        $uibMsgbox.alert('请输入转账卡号！');
        return;
      }

      var str = Format('yyyy-MM-dd hh:mm:ss', new Date());
      genPosID($scope.inputCradInfo, $scope.accountListPos);
      var detailObject = {
        client_card_no: $scope.transferPriceData.client_card_no,
        staffappprem: parseFloat($scope.transferPriceData.staffappprem),
        payment_way: 3,
        createTime: str,
        simple_cmp_name: $scope.transferPriceData.account.ACCOUNT_NAME,
        company_card_id: $scope.transferPriceData.account.ID,
        account_name: $scope.transferPriceData.simple_name,
        extend_column: $scope.transferPriceData.extend_column
      };
      detailObject.id = ++ids;
      if (submitCardPrice(detailObject) == -1) {
        return;
      }

      // 增加一条详情
      // detailObject.simple_cmp_name = $scope.transferPriceData.account_cmp;
      $scope.payment.transfer_detail[$scope.payment.transfer_detail.length] = detailObject;
      // 点击确定，新增行清空金额和卡号信息 add by lincm 20170824
      $scope.transferPriceData.client_card_no = null;
      $scope.transferPriceData.staffappprem = null;
      // 获取单项总计
      $scope.payment.transfer = sumItemsPrice($scope.payment.transfer_detail);

    }

    /* 移除转账信息 */
    $scope.removeTransferPrice = function(id) {
      removeTransferPrice(id);
    }

  }

  function removeTransferPrice(id) {
    if (id) {
      if ($scope.payment.transfer_detail) {
        var details = [];
        $.each($scope.payment.transfer_detail, function(i, model) {
          if (model.id != id) {
            details.push(model);
          }
        });
        $scope.payment.transfer_detail = details;
        $scope.payment.transfer = sumItemsPrice($scope.payment.transfer_detail);
      }
    }
  }

  function sumAllCardMoney() {
    $.each($scope.payment.transfer_detail, function(i, transfer) {
      $scope.payment.transfer = $scope.payment.transfer + genNumByString(transfer.money);
    });
  }


  /* 设定新的item序号，并统计该单项的综合 */
  function sumItemsPrice(card_detail) {
    var sum = 0;
    $.each(card_detail, function(i, model) {
      sum += model.staffappprem;
    });
    return sum;
  }

  /* 获取pos机的信息 */
  function genPosID(inputCradInfo, account) {
    if (inputCradInfo && account) {
      $.each(account, function(i, it) {
        if (it.id == inputCradInfo.account) {
          inputCradInfo.simple_name = it.simple_name;
          inputCradInfo.pos_id = it.id;
          inputCradInfo.company_card_id = it.company_card_id;
          return it;
        }
      });
    }
  }

  function queryCompanyAccount() {
    // 查询公司账户信息
	  erp_companyAccountService.query({p_status:1}, function(resp) {
		  if (!resp.error && resp.data && resp.data.length > 0) {
			  $scope.accountTransferList = resp.data;
		  } else {
		      $scope.accountTransferList = [];
		      $uibMsgbox.alert(resp.message)
		  } 
	  });
  }
  
  function queryDeviceInfo() {
	  erp_deviceService.queryBu({p_status:1}, function(resp) {
		  $scope.accountListPos = [];
	      if (!resp.error && resp.data && resp.data.length > 0) {
	        $.each(resp.data, function(i, model) {
	        	if (model.account_name) {
	        		$scope.accountListPos.push(model);
	        	}
	        });
	      } else {
	        $uibMsgbox.alert(resp.message)
	      }
	    });
  }

  $scope.selectedOrder = {};

  /**
   * 计算向下取整
   * @constructor
   */
  function CalculationOrders() {
    if (!$scope.student) {
      return;
    }
    if (!$scope.selectedCourseList || !$scope.selectedCourseList.length) {
      $uibMsgbox.alert("没有选择课程！");
      return;
    }

    if ($scope.Preferential.tabType == 'PreferentialReduction' && $scope.Preferential.immediate_reduce && !genNumByString($scope.Preferential.immediate_reduce)) {
      $uibMsgbox.alert("立减金额不正确!");
      $scope.Preferential.immediate_reduce = null;
      return;
    }

    $scope.selectedOrder.student_id = $scope.student.id;
    $scope.selectedOrder.business_type = $scope.business_type;
    $scope.selectedOrder.sum_price = 0;
    $scope.selectedOrder.actual_price = 0;
    $scope.selectedOrder.immediate_reduce = 0;

    //所有课时
    var all_count = 0;
    //计算科目级优惠
    $.each($scope.selectedCourseList, function(i, course) {
      course.former_unit_price = course.unit_price;
      course.discount_unit_price = course.unit_price;
      course.pre_forward = 0;
      course.former_sum_price = course.unit_price * course.selectedCourseTimesCount;
      course.discount_sum_price = course.unit_price * course.selectedCourseTimesCount;
      if (course.selectedRule && course.selectedRule.id > 0) {
        //优惠折扣
        if (course.selectedRule.coupon_type == 1) {
          var coupon_content = parseFloat(course.selectedRule.coupon_content);
          course.discount_sum_price = Math.floor(course.discount_sum_price * coupon_content);
          course.discount_unit_price = Math.floor(course.discount_sum_price / course.selectedCourseTimesCount);
          course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.discount_unit_price;
        }
        //优惠金额
        else if (course.selectedRule.coupon_type == 2) {
          var coupon_content = parseInt(course.selectedRule.coupon_content);
          course.discount_sum_price = Math.floor(course.discount_sum_price - coupon_content);
          course.discount_unit_price = Math.floor(course.discount_sum_price / course.selectedCourseTimesCount);
          course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.discount_unit_price;
        }
        //每课时优惠
        else if (course.selectedRule.coupon_type == 3) {
          var coupon_content = parseInt(course.selectedRule.coupon_content);
          course.discount_sum_price = Math.floor(course.discount_sum_price - course.selectedCourseTimesCount * coupon_content);
          course.discount_unit_price = Math.floor(course.discount_sum_price / course.selectedCourseTimesCount);
          course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.discount_unit_price;
        }
      }

      $scope.selectedOrder.sum_price = $scope.selectedOrder.sum_price + course.former_sum_price;
      $scope.selectedOrder.actual_price = $scope.selectedOrder.actual_price + course.discount_sum_price;
      all_count = all_count + course.selectedCourseTimesCount;
    });

    //选择了整体的折扣优惠
    if ($scope.Preferential.tabType == 'PreferentialRules' && $scope.theWholeRule) {
      var coupon_content = parseFloat($scope.theWholeRule.coupon_content);
      var coupon_content_left = coupon_content;
      //优惠折扣
      if ($scope.theWholeRule.coupon_type == 1) {
        var actual_price = Math.floor(Math.round($scope.selectedOrder.actual_price * coupon_content* Math.pow(10, 5)) / Math.pow(10, 5));
        coupon_content = $scope.selectedOrder.actual_price - actual_price;
        coupon_content_left = coupon_content;
        $scope.selectedOrder.actual_price = actual_price;
      }
      //优惠金额
      else if ($scope.theWholeRule.coupon_type == 2) {
        $scope.selectedOrder.actual_price = Math.floor($scope.selectedOrder.actual_price - coupon_content);
      }
      //每课时优惠
      else if ($scope.theWholeRule.coupon_type == 3) {
        coupon_content = Math.floor(all_count * coupon_content);
        coupon_content_left = coupon_content;
        $scope.selectedOrder.actual_price= Math.floor($scope.selectedOrder.actual_price - coupon_content);
      }
      //计算科目级分摊
      $.each($scope.selectedCourseList, function(i, course) {
        if (i == $scope.selectedCourseList.length - 1) {
          course.discount_sum_price = course.discount_sum_price - coupon_content_left;
        } else {
          var dalta = 0;
          if($scope.selectedOrder.sum_price != 0) {
            dalta = Math.floor((course.former_sum_price / $scope.selectedOrder.sum_price) * coupon_content);
          }
          coupon_content_left = coupon_content_left - dalta;
          course.discount_sum_price = course.discount_sum_price - dalta;
        }
        course.discount_unit_price = Math.floor(course.discount_sum_price / course.selectedCourseTimesCount);
        course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.discount_unit_price;
      });
    } else if ($scope.Preferential.tabType == 'PreferentialReduction' && $scope.Preferential.immediate_reduce) {
      var immediate_reduce = genNumByString($scope.Preferential.immediate_reduce);
      var coupon_content = immediate_reduce;
      var coupon_content_left = coupon_content;
      $scope.selectedOrder.immediate_reduce = immediate_reduce;
      $scope.selectedOrder.actual_price = $scope.selectedOrder.actual_price - immediate_reduce;
      //计算科目级分摊
      $.each($scope.selectedCourseList, function(i, course) {
        if (i == $scope.selectedCourseList.length - 1) {
          course.discount_sum_price = course.discount_sum_price - coupon_content_left;
        } else {
          var dalta = Math.floor((course.former_sum_price / $scope.selectedOrder.sum_price) * coupon_content);
          coupon_content_left = coupon_content_left - dalta;
          course.discount_sum_price = course.discount_sum_price - dalta;
        }
        course.discount_unit_price = Math.floor(course.discount_sum_price / course.selectedCourseTimesCount);
        course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.discount_unit_price;
      });
    }

    //选择多张优惠券
    if ($scope.foundedCouponInfos) {
      $.each($scope.foundedCouponInfos, function(i, CouponInfo) {
        if (CouponInfo.checked && CouponInfo.privilegeRule) {
          var coupon_content = parseFloat(CouponInfo.privilegeRule.coupon_content);
          var coupon_content_left = coupon_content;
          //优惠折扣
          if (CouponInfo.privilegeRule.coupon_type == 1) {
            var actual_price = Math.floor($scope.selectedOrder.actual_price * coupon_content);
            coupon_content = $scope.selectedOrder.actual_price - Math.floor($scope.selectedOrder.actual_price * coupon_content);
            coupon_content_left = coupon_content;
            $scope.selectedOrder.actual_price = actual_price;
          }
          //优惠金额
          else if (CouponInfo.privilegeRule.coupon_type == 2) {
            $scope.selectedOrder.actual_price = Math.floor($scope.selectedOrder.actual_price - coupon_content);
          }
          //每课时优惠
          else if (CouponInfo.privilegeRule.coupon_type == 3) {
            coupon_content = Math.floor(all_count * coupon_content);
            coupon_content_left = coupon_content;
          }
          //计算科目级分摊
          $.each($scope.selectedCourseList, function(i, course) {
            if (i == $scope.selectedCourseList.length - 1) {
              course.discount_sum_price = course.discount_sum_price - coupon_content_left;
            } else {
              var dalta = Math.floor((course.former_sum_price / $scope.selectedOrder.sum_price) * coupon_content);
              coupon_content_left = coupon_content_left - dalta;
              course.discount_sum_price = course.discount_sum_price - dalta;
            }
            course.discount_unit_price = Math.floor(course.discount_sum_price / course.selectedCourseTimesCount);
            course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.discount_unit_price;
          });
        }
      })
    }


  }

  /**
   * 打开现金的面板
   * @param type
   */
  $scope.openRechargePanel = function(type) {
    $scope.openPanel = type;
    if (type == 'storeAccount' || type == 'frozenAccount') {
      $scope.payment.paymentAccount = $scope.selectedOrder.actual_price - $scope.payment.cash - $scope.payment.card - $scope.payment.transfer - $scope.payment.storeAccount - $scope.payment.frozenAccount;
    } else if (type == 'transfer') {
      $scope.toTransferPrice();
    } else if (type == 'card') {
      $scope.toCardPrice();
    }
  }

  $scope.openPayOnline = function() {
    var sumpay = $scope.selectedOrder.actual_price - $scope.payment.cash -
    $scope.payment.card -
    $scope.payment.transfer -
    $scope.payment.storeAccount -
    $scope.payment.frozenAccount;
    if(sumpay > 0){
      $uibModal.open({
        templateUrl: 'payOnlineAccountInput.html',
        backdrop: 'static',
        resolve: {
          amount: function () {
            return sumpay
          },
          order: function () {
            return $scope.temporaryOrder
          }
        },
        controller: ['$scope', '$uibMsgbox', 'amount', 'order', function(
          $scope, $uibMsgbox, amount, order
        ){
          $scope.amount = amount;
          $scope.qrcodeEle = null;
          // $scope.pageView = 'genQrCode';
          $scope.pageView = 'getQrCodeFromServer';
          $scope.queryPayStatusTimer = null;
          $scope.queryPayStatusFlag = false;
          var qrCodeId = null;
          function getBillDesc(orderDetais){
            var courseNameList = []
            _.forEach(orderDetais, function(item) {
              courseNameList.push(item.course_name)
            })
            return courseNameList.join(',')
          }
          $scope.$on('modal.closing', function (target, reason, close) {
            if (!close && $scope.pageView != 'getQrCodeFromServer') {
              target.preventDefault()
              $uibMsgbox.confirm('关闭前请确认客户【还未进行扫码支付】，如果已支付，请点击【支付完成】，确认关闭？', function (res) {
                if (res == 'yes') {
                  $scope.queryPayStatusFlag = false;
                  $scope.$close('dismiss')
                }
              })
            }
          })
          $scope.onReGenQrcode = function () {
            $uibMsgbox.confirm('重新生成二维码，原有二维码将会失效，请先确认客户【还未进行扫码支付】，如果已支付，请点击【支付完成】，确认重新生成？', function (res) {
              if (res == 'yes') {
                $scope.onGenQrcode()
              }
            })
          }
          $scope.onGenQrcode = function () {
            qrCodeId = null
            if ($scope.qrcodeEle && typeof $scope.qrcodeEle.clear == 'function') {
              $scope.qrcodeEle.clear()
            }
            $scope.pageView = 'getQrCodeFromServer';
            erp_orderManagerService.getPayOnlineQrCode({
              orderId: order.id,
              billNo: order.encoding,
              billDesc: getBillDesc(order.details),
              // 银联在线支付金钱单位为分，需要乘以100
              totalAmount: amount * 100
            }).$promise.then(function(resp) {
              if(resp.error) {
                return $uibMsgbox.error(resp.message)
              }
              $scope.pageView = 'genQrCode';
              $scope.pageView = 'showQrCode'
              var result = JSON.parse(resp.data)
              var billQRCode = result.billQRCode
              qrCodeId = result.qrCodeId
              if (!qrCodeId) {
                return $uibMsgbox.alert('获取支付二维码信息失败，请尝试重新生成二维码或联系管理员!<br>失败原因：' + result.errMsg)
              }
              if (!$scope.qrcodeEle) {
                $scope.qrcodeEle = new QRCode(document.getElementById('order-pay-online-qrcode'),  {
                  text: billQRCode,
                  width: 256,
                  height: 256,
                  colorDark : "#000000",
                  colorLight : "#ffffff",
                  correctLevel : QRCode.CorrectLevel.H
                });
              } else {
                $scope.qrcodeEle.clear()
                $scope.qrcodeEle.makeCode(billQRCode)
              }
              startQueryPayStatus()
            })
          }
          $scope.onGenQrcode();
          function queryPayStaus(billNo, billDate) {
            erp_orderManagerService.getPayOnlineResult({
              billNo: billNo,
              billDate: billDate
            }).$promise.then(function(resp) {
              if (resp.error) {
                return false
              }
              var res = JSON.parse(resp.data)
              if (res.billStatus != 'PAID') {
                if ($scope.queryPayStatusFlag) {
                  setTimeout(function() {
                    queryPayStaus(billNo, billDate)
                  }, 1500)
                }
              } else {
                $uibMsgbox.success('支付成功!', function() {
                  $scope.$close()
                })
              }
            })
          }
          function startQueryPayStatus() {
            $scope.queryPayStatusFlag = true
            erp_orderManagerService.getPayOnlineQrCodeInfo({
              qrCodeId: qrCodeId
            }).$promise.then(function(resp) {
              var qrcodeInfo = JSON.parse(resp.data)
              queryPayStaus(qrcodeInfo.billNo, qrcodeInfo.billDate)
            })
          }
          function stopQueryPayStatus() {
            $scope.queryPayStatusFlag = false
          }
          $scope.onPayDone = function () {
            if (!qrCodeId) {
              return $uibMsgbox.alert('获取支付二维码信息失败，请尝试重新生成二维码!')
            }
            erp_orderManagerService.getPayOnlineQrCodeInfo({
              qrCodeId: qrCodeId
            }).$promise.then(function(resp) {
              var qrcodeInfo = JSON.parse(resp.data)
              return erp_orderManagerService.getPayOnlineResult({
                billNo: qrcodeInfo.billNo,
                billDate: qrcodeInfo.billDate
              }).$promise
              // $scope.$close()
            }, function (resp) {
              $uibMsgbox.error('请求失败！' + resp.message)
            }).then(function(resp) {
              var res = JSON.parse(resp.data)
              if (res.billStatus != 'PAID') {
                $uibMsgbox.warn('客户未支付未完成，请等待客户支付完成！');
              } else {
                $scope.$close();
              }
            }, function (resp) {
              console.log('getPayOnlineResult Error')
              console.log(resp)
            })
          }
        }]
      }).result.then(function (reason) {
        if (reason == 'dismiss') {
          return
        }
        $scope.payment.onlinePrice = $scope.selectedOrder.actual_price - $scope.payment.cash -
          $scope.payment.card -
          $scope.payment.transfer -
          $scope.payment.storeAccount -
          $scope.payment.frozenAccount;
        $scope.payOrder()
      })
    }
    else{
      $uibMsgbox.alert("支付金额需大于零!");
    }
  }
  /**
   * 关闭现金的面板
   */
  $scope.closeRechargePanel = function() {
    $scope.openPanel = '';
  }

  /**
   * 订单暂存:saveTemporary;订单付款:saveOrder
   */
  $scope.saveTemporary = function() {
    var param = {};
    // $scope.openPanel = 'saveTemporary';
    param.saveType = 'saveTemporary';
    genOrder(param);
    var _waitingModal = $uibMsgbox.waint('订单暂存中，请稍候...')
    erp_orderManagerService.post(param, function(resp) {
      _waitingModal.close();
      // $scope.openPanel = '';
      if (!resp.error) {
        $uibMsgbox.alert("暂存成功！");
        $scope.temporaryOrder = resp.data;
      } else {
        $uibMsgbox.alert(resp.message);
      }
    }, function(resp) {
      $scope.openPanel = '';
      $uibMsgbox.alert("暂存失败！");
    })
  }

  function genOrder(param) {
    if ($scope.temporaryOrder) {
      param.id = $scope.temporaryOrder.id;
    }

    param.student_id = $scope.selectedOrder.student_id;
    param.business_type = $scope.selectedOrder.business_type;
    param.sum_price = $scope.selectedOrder.sum_price;
    param.actual_price = $scope.selectedOrder.actual_price;
    param.remark = $scope.selectedOrder.remark;
    param.resource_rec_id = $state.params.resource_rec_id;
    param.details = [];
    //报班单详情
    $.each($scope.selectedCourseList, function(i, course) {
      var detailCourse = {};
      detailCourse.student_id = param.student_id;
      detailCourse.course_id = course.id;
      detailCourse.branch_id=course.branch_id;
      detailCourse.course_name =course.course_name;
      detailCourse.business_type = course.business_type;
      detailCourse.former_sum_price = course.former_sum_price;
      detailCourse.former_unit_price = course.former_unit_price;
      detailCourse.discount_sum_price = course.discount_sum_price;
      detailCourse.discount_unit_price = course.discount_unit_price;
      detailCourse.pre_forward = course.pre_forward;
      detailCourse.start_date = course.start_date;
      detailCourse.end_date = course.end_date;
      detailCourse.start_time = course.start_time;
      detailCourse.end_time = course.end_time;
      detailCourse.teacher_id = course.teacher_id;
      detailCourse.course_total_count = course.selectedCourseTimesCount;
      detailCourse.course_surplus_count = course.selectedCourseTimesCount;
      detailCourse.surplus_cost = course.selectedCourseTimesCount * detailCourse.discount_unit_price;

      //订单级的优惠规则
      if (course.selectedRule && course.selectedRule.id > 0) {
        detailCourse.rule_id = course.selectedRule.id;
      }
      detailCourse.orderCourseTimes = [];

      //课程报班课次信息
      if (course.courseSchedulingList) {
        $.each(course.courseSchedulingList, function(i, courseTime) {
          if (courseTime.checked) {
            var courseTimeChecked = {};
            courseTimeChecked.student_id = detailCourse.student_id;
            courseTimeChecked.course_times = courseTime.course_times;
            detailCourse.orderCourseTimes.push(courseTimeChecked);
          }
        });
      }
      param.details.push(detailCourse);
    });

    //报班单优惠
    if ($scope.Preferential.tabType == 'PreferentialRules' && $scope.theWholeRule) {
      param.rule_id = $scope.theWholeRule.id;
    } else if ($scope.Preferential.tabType == 'PreferentialReduction' && $scope.Preferential.immediate_reduce) {
      param.immediate_reduce = $scope.selectedOrder.immediate_reduce;
      param.extend_column = $scope.Preferential.immediate_remark;
    }

    param.coupon_rels = [];
    //优惠券信息
    if ($scope.foundedCouponInfos) {
      $.each($scope.foundedCouponInfos, function(i, couponInfo) {
        if (couponInfo.checked) {
          var couponRel = {};
          couponRel.coupon_id = couponInfo.id;
          couponRel.coupon_encoding = couponInfo.encoding;
          couponRel.coupon_name = couponInfo.name;
          param.coupon_rels.push(couponRel);
        }
      });
    }

    //付款信息
    if (param.saveType != 'saveTemporary' && param.actual_price == (
        $scope.payment.cash 
        + $scope.payment.card
        + $scope.payment.transfer
        + $scope.payment.storeAccount
        + $scope.payment.frozenAccount
        + $scope.payment.onlinePrice)) {

      param.payment = {};
      param.payment.studentId = param.student_id;
      param.payment.sumPrice = param.actual_price;
      param.payment.actualPrice = $scope.payment.cash + $scope.payment.card + $scope.payment.transfer + $scope.payment.storeAccount + $scope.payment.frozenAccount + $scope.payment.onlinePrice;
      param.payment.cashPrice = $scope.payment.cash;
      param.payment.cardPrice = $scope.payment.card;
      param.payment.transferPrice = $scope.payment.transfer;
      param.payment.accountPrice = $scope.payment.storeAccount;
      param.payment.frozenAccountPrice = $scope.payment.frozenAccount;
      param.payment.onlinePrice = $scope.payment.onlinePrice;

      param.payment.details = [];

      if (param.payment.cashPrice > 0) {
        var paramPP = {};
        paramPP.payment_way = 1;
        paramPP.staffappprem = param.payment.cashPrice;
        param.payment.details.push(paramPP);
      }

      if (param.payment.onlinePrice > 0) {
        var paramPP = {
          payment_way: 11,
          staffappprem: param.payment.onlinePrice
        }
        param.payment.details.push(paramPP)
      }

      if (param.payment.cardPrice > 0 && $scope.payment.card_detail) {
        $.each($scope.payment.card_detail, function(i, card) {
          if (card.simple_cmp_name) {
            delete card.simple_cmp_name
          }
          param.payment.details.push(card);
        });
      }
      if (param.payment.transferPrice > 0 && $scope.payment.transfer_detail) {
        $.each($scope.payment.transfer_detail, function(i, transfer) {
          if (transfer.simple_cmp_name) {
            delete transfer.simple_cmp_name
          }
          param.payment.details.push(transfer);
        });
      }

      if (param.payment.accountPrice > 0) {
        var paramPP = {};
        paramPP.payment_way = 4;
        paramPP.staffappprem = param.payment.accountPrice;
        param.payment.details.push(paramPP);
      }

      if (param.payment.frozenAccountPrice > 0) {
        var paramPP = {};
        paramPP.payment_way = 9; // 冻结账户付款
        paramPP.staffappprem = param.payment.frozenAccountPrice;
        param.payment.details.push(paramPP);
      }
    }
  }

  /**
   * 保存订单
   */
  $scope.saveOrder = function() {
    var param = {};
    // $scope.openPanel = 'saveOrder';
    param.saveType = 'saveOrder';
    genOrder(param);
    var _modalInstance = $uibMsgbox.waiting('订单保存中，请稍候...');
    erp_orderManagerService.post(param, function(resp) {
      _modalInstance.close();
      if (!resp.error) {

        $scope.temporaryOrder = resp.data;
        if ($scope.temporaryOrder.check_status == 2) {
          //审核中，走提示已经提交审核，流程结束
          $uibMsgbox.confirm({
            content:'订单提交成功，请等待审核通过后继续缴费！',
            title: '提示',
            okText: '查看详情',
            cancelText: '关闭',
            showCancelBtn: false,
            callback: function(res) {
              if (res == 'yes') {
                // window.location.href = '?studentId=' + $scope.temporaryOrder.student_id + '#/studentMgr/studentMgrIndex';
                window.location.href = '?studentId=' + $scope.temporaryOrder.student_id + '&orderId=' + $scope.temporaryOrder.id + '&orderType=temporaryOrder#/studentMgr/studentMgrCourse/studentMgrOrderDetail';
              } else {
                $state.reload();
              }
            }
          })
          // $scope.openPanel = 'saveOrderOK';
        } else if ($scope.temporaryOrder.check_status == 3) {
          $scope.openPanel = '';
          //审核通过，下一步去缴费
          $scope.nextStep(3, 4);
        }
      } else {
        $scope.openPanel = '';
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  $scope.payOrder = function() {
    var param = {};
    // $scope.openPanel = 'payOrder';
    param.saveType = 'payOrder';
    genOrder(param);
    var _modalInstance = $uibMsgbox.waiting('订单支付中，请稍候...');
    erp_orderManagerService.pay(param, function(resp) {
      _modalInstance.close();
      if (!resp.error) {
        $scope.temporaryOrder = resp.data;
        $uibMsgbox.confirm({
          content: '订单支付成功！', 
          okText: '查看详情',
          cancelText: '继续报班',
          callback: function (res) {
          if (res == 'yes') {
              window.open(
                // '?studentId=' + $scope.temporaryOrder.student_id +'#/studentMgr/studentMgrIndex'
                  '?studentId=' + $scope.temporaryOrder.student_id + '&orderId=' + $scope.temporaryOrder.id + '#/studentMgr/studentMgrCourse/studentMgrOrderDetail'
              );
              $scope.temporaryOrder=null;
              $state.reload();
            } else {
              window.location.href="?_t=" + Math.random() + "#/orders/ordersMgr/ordersMgrOrders";
              // $state.reload();
            }
          }
        })
        // $scope.openPanel = 'payOrderOK';
      } else {
        $scope.openPanel = '';
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  function genEndTime(start_date, monthNub) {
    var times = start_date.split("-");
    var year = times[0];
    var month = times[1];
    var day = times[2] - 1;
    var date = new Date();
    date.setFullYear(year, month - 1, day);
    date.setDate(date.getDate() + monthNub);

    return Format('yyyy-MM-dd', date);
  }

  $scope.changeTimes = function(course) {
    try {
      if (course.selectedCourseTimesCount == "" || course.selectedCourseTimesCount == null) {
        return;
      }
      course.selectedCourseTimesCount = parseInt(course.selectedCourseTimesCount);
    } catch (e) {
      course.selectedCourseTimesCount = 0;
      course.unit_price = 0;
    }
    if (course.selectedCourseTimesCount < 0) {
      course.selectedCourseTimesCount = 0;
    }
    //计算截止日期

    if (course.selectedCourseLadder) {
      //TODO按照年度累计课时算
      if (course.selectedCourseLadder.REACH_TYPE == 1) {
        if (course.selectedCourseLadder.TOTALCOUNT > course.selectedCourseLadder.REACH_COUNT) {
          //course.course_total_count = 0;
        } else {
          course.selectedCourseTimesCount = course.selectedCourseLadder.REACH_COUNT - course.selectedCourseLadder.TOTALCOUNT;
        }
      }
    }
    if (course.selectedComboWfd) {
      //套餐类型:1：月卡
      if (course.selectedComboWfd.combo_type == 1) {
        $scope.combo = 1;
        $scope.selectedComboWfd = course.selectedComboWfd;
        var month_count = course.selectedComboWfd.course_count;
        course.unit_price = Math.floor(course.selectedComboWfd.price / month_count);
        course.discount_sum_price = course.selectedComboWfd.price;
        course.selectedCourseTimesCount = month_count;
        //预结转
        course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.unit_price;
        //              var date = new Date();
        if (course.start_date) {
          course.end_date = genEndTime(course.start_date, month_count)
        }
        course.remark = '晚辅导报班，套餐信息：总价' + course.selectedComboWfd.price + '元' + (course.end_date ? '，截止时间：' + course.end_date : '');

        return;
      }
      //            //套餐类型:2：限时限次数
      //            else if(course.selectedComboWfd.COMBO_TYPE == 2){
      //                var MONTH_COUNT = course.selectedComboWfd.MONTH_COUNT;
      //                var COURSE_COUNT = course.selectedComboWfd.COURSE_COUNT;
      //                course.course_total_count = COURSE_COUNT;
      //                course.discount_unit_price = Math.floor(course.selectedComboWfd.PRICE / COURSE_COUNT);
      //                course.discount_sum_price = course.selectedComboWfd.PRICE;
      //                //预结转
      //                course.pre_forward = course.discount_sum_price - course.course_total_count * course.discount_unit_price;
      //                //截止日期
      //                if(course.start_date){
      //                    course.end_date = genEndTime(course.start_date,MONTH_COUNT);
      //                }
      //                course.remark='晚辅导报班，套餐信息：'+MONTH_COUNT+'个月(1个月30天)内，上完'+course.selectedComboWfd.COURSE_COUNT+'次课，总价'+course.selectedComboWfd.PRICE+'元'+(course.end_date?'，截止时间：'+course.end_date:'');
      //                
      //                return;
      //            }
      //套餐类型:2 次卡
      else if (course.selectedComboWfd.combo_type == 2) {
        $scope.combo = 2;
        $scope.selectedComboWfd = course.selectedComboWfd;
        var month_count = course.selectedComboWfd.course_count;
        course.remark = '晚辅导报班，套餐信息：上完' + month_count + '次课，总价' + course.selectedComboWfd.price + '元';
        course.selectedCourseTimesCount = month_count;
        course.unit_price = Math.floor(course.selectedComboWfd.price / month_count);
        course.discount_sum_price = course.selectedComboWfd.price;
        //预结转
        course.pre_forward = course.discount_sum_price - course.selectedCourseTimesCount * course.unit_price;
        return;
      }
    }

    course.discount_sum_price = course.unit_price * course.selectedCourseTimesCount;
    if($scope.selectedCourseLadder){
      $scope.calUnitPrice($scope.selectedCourseLadder);
    }
  };

  $scope.calUnitPrice= function(ladder) {
    var course=$scope.selectedYDYCourseList[0];
        erp_courseLadderService.adjustLadder({ 
          course_times: course.selectedCourseTimesCount ,
          student_id: $scope.studentId,
          ids:ladder.id,
          course_id:course.id
        }, function(resp) {
            if (!resp.error) {
              course.unit_price=resp.ladderPrice;
              course.discount_sum_price = course.unit_price * course.selectedCourseTimesCount;
            } else {
              $uibMsgbox.error(resp.message);
            }
       });
  }

  
  $scope.selectLadder = function(ladder,id) {
	    $scope.comboId = id;
      $scope.selectedCourseLadder = ladder;
	    var length = $scope.selectedYDYCourseList.length;
	    if (length == 0 || length >= 2) {
	    	$uibMsgbox.warn('个性化阶梯只支持选择一门课程！');
	      return;
	    } else {
	    	$scope.calUnitPrice(ladder);
      }
	};
  
  $scope.selectComboWfd = function(comboWfd) {
    if ($scope.comboId == comboWfd.id) {
      // 点击的是当前套餐时，则取消套餐选择
      $scope.comboId = undefined;
      // TODO 套餐取消，重新计算课程价格
      return;
    }
    $scope.comboId = comboWfd.id;
    var match = true;
    var length = $scope.selectedWFDCourseList.length;
    if (length == 0 || length >= 2) {
    	$uibMsgbox.warn('晚自习套餐只支持一门课程！');
    	return;
    } else {
      erp_wfdComboService.checkWfdCourse({ student_id: $scope.studentId }, function(resp) {
        if (!resp.error) {
          if (resp.code == 200 && match) {
            match = true;
          } else {
            match = false;
          }
          if (match) {
            erp_courseService.queryCourseByStudentId({
              studentId: $scope.studentId,
              business_type: $scope.business_type,
              course_id: $scope.selectedWFDCourseList[0].id
            }, function(resp) {
              var Course = null;
              if (!resp.error) {
                Course = resp.data;
              }
              if (Course && Course.length == 1) {
                var course = Course[0];
                course.selectedComboWfd = comboWfd;
                course.start_date = Format("yyyy-MM-dd", new Date());
                $scope.changeTimes(course);
                $scope.selectedWFDCourseList.splice(0, 1, course);

                var combo_index = -1;
                $.each($scope.comboWfds, function(k, combo) {
                  if (combo.id == comboWfd.id) {
                    combo_index = k;
                  }
                });
              }
            });
          } else {
            $uibMsgbox.warn("没有相应的套餐课程或者学生报完了课程未上完一半课时！");
          }
        } else {
          $uibMsgbox.error(resp.message);
        }
      });

    }
  };
  
  $scope.selectAccountPOS = function(posInfo) {
	  $.each($scope.accountListPos, function(idx, pos) {
		  if (pos.id == $scope.inputCradInfo.account) {
			  $scope.inputCradInfo.account_cmp = pos.account_name;
			  return;
		  }
	  });
  }  
  
  $scope.sortNum = function(courseScheduling) {
    delete courseScheduling.is_listening
    var _waitingModal = $uibMsgbox.waiting('排号中，请稍候...');
	  erp_sortNumService.sortNum(courseScheduling, function(resp) {
        _waitingModal.close();
          if (!resp.error) {
        	  $uibMsgbox.success('排号成功！');
            $scope.openCourseTimesPanel($scope.curCourse);
          }else{
        	  $uibMsgbox.error(resp.message);
          }
        });
  }
  
  $scope.queryTotalSortNumInfo= function(courseTimes) {
	  erp_sortNumService.query(courseTimes, function(resp) {
          if (!resp.error) {
      //  	  console.info(resp.data);
          }else{
        	  $uibMsgbox.error(resp.message);
          }
        });
  }
  
  $scope.cancelSortNum = function(courseTimes) {
    var _waitingModal = $uibMsgbox.waiting('取消排号中，请稍候...');
    var params = {
      courseId: courseTimes.course_id,
      studentId: courseTimes.student_id,
      seq: courseTimes.course_times
    }
	  erp_sortNumService.cancelSortNum(params, function(resp) {
        _waitingModal.close();
          if (!resp.error) {
        	  $uibMsgbox.success('取消排号成功！');
            $scope.openCourseTimesPanel($scope.curCourse);
          }else{
        	  $uibMsgbox.error(resp.message);
          }
        });
  }


    $scope.queryOrderQueueDetail = function (course, detail) {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/course-queue-detail.modal.html',
            controller: 'modal_courseQueueDetailController',
            resolve: {
                course: function () {
                    return course;
                },
                courseSeqDetail: function() {
                    return {
                      seq: detail.course_times
                    };
                }
            }
        }).result.then(function(){
          $scope.openCourseTimesPanel($scope.curCourse);
        }, function() {
        	$scope.openCourseTimesPanel($scope.curCourse);
        })
        
    };

    $scope.queryCheckPeopleList = function (course, detail, orderType) {
      $uibModal.open({
          size: 'lg',
          templateUrl: 'templates/block/modal/course-check-detail.modal.html',
          resolve: {
              course: function () {
                  return course;
              },
              courseSeqDetail: function() {
                  return {
                    seq: detail.course_times
                  };
              },
              type: function () {
                return orderType
              }
          },
          controller: ['$scope', 
            'erp_sortNumService',
            'course',
            'courseSeqDetail',
            'type',
            function (
              $scope,
              erp_sortNumService,
              course,
              courseSeqDetail,
              type
            ) {
              $scope.detailList = [];
              $scope.type = type;
              erp_sortNumService.queryCheckPeople({
                courseId: course.id,
                seq: courseSeqDetail.seq,
                type: type
              }, function (resp) {
                if (!resp.error) {
                  $scope.detailList = resp.data
                }
              })
            }]
      }).result.then(function(){
        $scope.openCourseTimesPanel($scope.curCourse);
      }, function() {
        $scope.openCourseTimesPanel($scope.curCourse);
      })
    }
  $scope.queryStudents();
}
