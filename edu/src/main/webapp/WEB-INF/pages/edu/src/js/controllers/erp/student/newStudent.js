angular.module('ework-ui').controller('erp_newStudentController', [
  '$scope',
  '$log',
  '$state',
  '$stateParams',
  '$uibMsgbox',
  '$uibModal',
  'erp_gradeService',
  'erp_studentsService',
  'erp_studentSchoolService',
  'erp_studentContactRelationService',
  'erp_dictService',
  erp_newStudentController
])

function erp_newStudentController(
  $scope,
  $log,
  $state,
  $stateParams,
  $uibMsgbox,
  $uibModal,
  erp_gradeService,
  erp_studentsService,
  erp_studentSchoolService,
  erp_studentContactRelationService,
  erp_dictService
) {
  //学员状态
  $scope.studentStatus = [];
  $scope.newContact = {
    link_phone: '',
    link_name: '母亲',
    relation: 2,
    remark: ''
  }
  $scope.gradeList = {}
  $scope.selectedInfo = {
    school: undefined,
    recommender: undefined,
    grade: undefined
  }
  $scope.selectedCounselor = null;
  $scope.selectedGovern = null;
  $scope.student = {
    head_pic: '',
    // 姓名 必填
    student_name: '',
    // 咨询师
    counselor: {
      start_date: moment().format('YYYY-MM-DD'),
      end_date: moment().endOf('year').format('YYYY-MM-DD'),
      encoding: '',
      counselor_id:null,
      name: '',
      remark: ''
    },
    // 学管师
    govern: {
      start_date: moment().format('YYYY-MM-DD'),
      end_date: moment().endOf('year').format('YYYY-MM-DD'),
      encoding: '',
      counselor_id:null,
      name: '',
      remark: ''
    },
    // 性别 必填
    sex: "1",
    // 生日
    birthday: '',
    // 学校 必填 （全日制学校）需要有学校列表
    attend_school_id: 100000680,
    // 年级 必填
    grade_id: 7,
    // 联系电话 必填（校验）
    studentContactList: [],
    // 邮箱
    email: '',
    // QQ
    qq: '',
    // 推荐人（学生id）
    student_id_old: '',
    //学员状态:1,正常；2,重复；3,在读；4,沉睡；5,停课；6,结课
    student_status: 1,
    // 家庭住址
    address: '',
  }

  $scope.getRelationList = function() {
    erp_studentContactRelationService.query({}, function(resp) {
      $scope.relationList = resp.data;
    })
  }

  $scope.getSchools = function(schoolName) {
    return erp_studentSchoolService.query({
      pageSize: 20,
      currentPage: 1,
      school_name: schoolName
    }).$promise.then(function(resp) {
      return resp.data
    })
  }
  $scope.queryStudentGrade = function() {
        erp_gradeService.querySelectDatas({
            date_filter: 1
        }, function(resp) {
            if (!resp.error) {
                $scope.gradeList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }
  $scope.getRecommender = function(searchInfo) {
    return erp_studentsService.query({
      pageSize: 20,
      currentPage: 1,
      searchInfo: searchInfo,
      searchType: searchInfo
    }).$promise.then(function(resp) {
      _.forEach(resp.data, function(item) {
        item.studentShortInfo = item.student_name + '【' + item.branch_name + ',' + (item.grade_name || '') + '】'
      });
      return resp.data;
    })
  }
  $scope.addContact = function(contact) {
    $scope.student.studentContactList.push(_.cloneDeep(contact));
    var link_name;
    var relation;
      switch ($scope.student.studentContactList.length) {
          case 0 :
              link_name = $scope.student.student_name ? ($scope.student.student_name + '的母亲') : '母亲';
              relation = 2;
              break;
          case 1 :
              link_name = $scope.student.student_name ? ($scope.student.student_name + '的父亲') : '父亲';
              relation = 1;
              break;
          case 2 :
              link_name =  '自己';
              relation = 5;
              break;
          case 3 :
              link_name = $scope.student.student_name ? ($scope.student.student_name + '的亲戚') : '亲戚';
              relation = 3;
              break;
          case 4 :
              link_name =  '其他';
              relation = 4;
              break;
      }
      $scope.newContact = {
          link_phone: '',
          link_name: link_name,
          relation: relation,
          remark: ''
      }

  };
//  输入的学生姓名自动填充到联系人姓名
  $scope.changeStudentName = function(){
        if($scope.student.student_name) {
            $.each($scope.student.studentContactList,function(i,n){
                if(n.relation !=4 && n.relation !=5 ) {
                    var realtionName = _.result(_.find($scope.relationList, function(relationOrigin) {
                        return n.relation == relationOrigin.value;
                    }), 'label');
                    if(n.link_name.indexOf(realtionName)!= -1) {
                        n.link_name=  $scope.student.student_name + (realtionName?('的'+ realtionName) :'')  ;
                    }
                }
            });
            if($scope.newContact.relation && $scope.newContact.relation !=4 && $scope.newContact.relation !=5 ) {
                $scope.newContact.link_name = $scope.student.student_name + '的' +
                    _.result(_.find($scope.relationList, function(relationOrigin) {
                        return $scope.newContact.relation == relationOrigin.value;
                    }), 'label');
            }
        } else {
            $.each($scope.student.studentContactList,function(i,n){
                if(n.relation !=4 && n.relation !=5) {
                    var realtionName = _.result(_.find($scope.relationList, function(relationOrigin) {
                        return n.relation == relationOrigin.value;
                    }), 'label');
                    if(n.link_name.indexOf(realtionName)!= -1) {
                        n.link_name= realtionName?realtionName:'' ;
                    }
                }
            });
            if($scope.newContact.relation && $scope.newContact.relation !=4 && $scope.newContact.relation !=5 ) {
                $scope.newContact.link_name =  _.result(_.find($scope.relationList, function(relationOrigin) {
                        return relationOrigin.value == $scope.newContact.relation;
                    }), 'label');
            }
        }

  }
    //  学管师的结束时间在开始时间上加两年
    $scope.$watch("student.counselor.start_date",function(){
        var date=new Date($scope.student.counselor.start_date);
        date.setFullYear(date.getFullYear() + 2);
        date.setDate(date.getDate()-1);
        $scope.student.counselor.end_date=Format("yyyy-MM-dd", date);
    })

    //  咨询师的结束时间在开始时间上加两年
    $scope.$watch("student.govern.start_date",function(){
        var date=new Date($scope.student.govern.start_date);
        date.setFullYear(date.getFullYear() + 2);
        date.setDate(date.getDate()-1);
        $scope.student.govern.end_date=Format("yyyy-MM-dd", date);
    })

  $scope.removeContact = function(contact) {
    _.remove($scope.student.studentContactList, contact);
  }

  $scope.selectCounselor = function() {
    $scope.openTeachersModal('counselor').result.then(function(teacher) {
      var counselor = $scope.student.counselor;
      counselor.counselor_id = teacher.id;
      counselor.name = teacher.employee_name;
      counselor.encoding = teacher.encoding;
    }, function() {})
  }

  $scope.selectGovern = function() {
    $scope.openTeachersModal('gover').result.then(function(teacher) {
      var govern = $scope.student.govern;
      govern.counselor_id = teacher.id;
      govern.name = teacher.employee_name;
      govern.encoding = teacher.encoding;
    }, function() {})
  }

  $scope.openTeachersModal = function(type) {
    var counselorType = type == 'counselor' ? 1 : 2;
    var modalTitle = (type == 'counselor' ? '咨询师' : '学管师') + '列表';
    return $uibModal.open({
      size: 'lg',
      templateUrl: 'templates/block/modal/employee-list.modal.html',
      controller: 'erp_employeeListModalController',
      resolve: {
        counselorType: function() {
          return counselorType;
        },
        modalTitle: function() {
          return modalTitle;
        }
      }
    })
  }

  // 弹出上传头像框
  $scope.showUploadModal = function(teacher) {
    var modalInstance = $uibModal
      .open({
        // templateUrl : 'uploadHeadModalInstance.html',
        templateUrl: 'templates/block/avatar-upload.html',
        controller: 'blocks_avatarUploadController',
        resolve: {
          onUploadImg: function() {
            return function(imageUrl, $uibModalInstance) {
              // TODO 保存上传的头像
              console.log(imageUrl)
              $scope.student.head_pic = imageUrl;
              $uibModalInstance.close();
            }
          }
        }
      });
    modalInstance.result.then(function(result) {

      if (result) {
        $scope.queryTeacher();
      }
    }, function() {
      $log.info('DrawModal dismissed at: ' + new Date());
    })
  };
  $scope.saveStudent = function() {
    var stu = $scope.student;
    if(!$.isEmptyObject($scope.selectedInfo.school)){
      stu.attend_school_id = $scope.selectedInfo.school.id;
    }

    stu.grade_id = $scope.selectedInfo.grade;
    if ($scope.selectedInfo.recommender && $scope.selectedInfo.recommender.id) {
      stu.student_id_old = $scope.selectedInfo.recommender.id;
    }
    var newStu = _.cloneDeep($scope.student);
    if (!$scope.studentForm.student_phone.$error.pattern 
        && $scope.newContact.link_phone && $scope.newContact.link_name) {
      newStu.studentContactList.push($scope.newContact)
    }

    if (newStu.studentContactList.length <= 0) {
      $uibMsgbox.warn('学员的联系电话未填写，请至少填写一个联系人信息！');
      return false;
    }

    if (!newStu.counselor.counselor_id) {
    	newStu.counselor=null;
    }
    if (!newStu.govern.counselor_id) {
    	newStu.govern=null;
    }

    var _waitingModal = $uibMsgbox.waiting('保存中，请稍候')
    erp_studentsService.post(newStu, function(resp) {
      _waitingModal.close()
      if (!resp.error) {
        //判断是否存在资源记录ID
        var resource_rec_id = $state.params.resource_rec_id;
        if(resource_rec_id) {
          $state.go('ordersMgrOrders', {
            studentId: resp.data.id,
            resource_rec_id:resource_rec_id,
            "path": "/orders/ordersMgr/ordersMgrOrders",
            "href": "templates/erp/orders/orders.html"
          });
          return;
        }

        $uibModal.open({
          animate: true,
          controllerAs: '$ctrl',
          template: '<div class="msgbox"><div class="modal-header">'
            + '<h3 class="modal-title" id="modal-title"><i class="iconfont icon-public_success i-text-success mr5"></i>添加成功</h3>'
            + '</div>'
            + '<div class="modal-body" id="modal-body">'
                + '<div class="content">添加学员成功，点击【继续新增】，继续添加学员，点击【立即充值】，立即为该学生充值，点击【立即报班】，立即为该学员报班</div>'
            + '</div>'
            + '<div class="modal-footer" style="text-align:center;">'
                + '<button class="btn btn-default" ng-click="$ctrl.cancel()">取消</button>'
                + '<button class="btn btn-primary" ng-click="$ctrl.ok()">继续新增</button>'
                + '<button class="btn btn-primary" ng-click="$ctrl.recharge()">立即充值</button>'
                + '<button class="btn btn-primary" ng-click="$ctrl.orders()">立即报班</button>'
            + '</div></div>',
          controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance) {
            var $ctrl = this;
            $ctrl.cancel = function () {
              $uibModalInstance.close()
              $state.goback()
            }
            $ctrl.ok = function () {
              $uibModalInstance.close()
              $state.reload()
            }
            $ctrl.recharge = function () {
                  var studentId = '';
                  $uibModalInstance.close();
                  window.open('?studentId=' + resp.data.id  + '#/studentMgr/studentMgrAccount/studentAccountRecharge');
              }
            $ctrl.orders = function () {
              var studentId = '';
              $uibModalInstance.close();
              window.open('?studentId=' + resp.data.id  + '#/orders/ordersMgr/ordersMgrOrders');
            }
          }],
        })
        // $uibMsgbox.confirm('保存成功，继续添加？', function(res) {
        //   if (res == 'yes') {
        //     $state.reload();
        //   } else {
        //     $scope.goback();
        //   }
        // })
      }
      else {
          //判断是否存在资源记录ID
          var stus=resp.data;
          $uibModal.open({
            animation: true,
            controllerAs: '$ctrl',
            template: '<div class="msgbox"><div class="modal-header">'
                  + '<h3 class="modal-title" id="modal-title"><i class="iconfont icon-public_reminder i-text-primary mr5"></i>提示</h3>'
              + '</div>'
              + '<div class="modal-body" id="modal-body" style="overflow:hidden;">'
                  + '<div class="content">该学员账号已存在，请勿重复创建。</div>'
                  +'<div class="content col-sm-12" ng-click="$ctrl.ok()">'
                    +'<img style="width:60px;height:60px;border-radius:50%;float:left;margin-right:10px;" src="img/erp/student-default.png">'
                    +'<a href="#" class="col-sm-10"><div>姓名:'+ (stu.student_name || '')
                      +'</div><div>学员编号:'+ (stus.encoding || '')
                      +',     校区:'+ (stus.branch_name || '')
                    +'</div></a>'
                  +'</div>'
              + '</div>'
              + '<div class="modal-footer" style="text-align:center;">'
                  + '<button class="btn btn-default" ng-click="$ctrl.cancel()">取消</button>'
                  + '<button class="btn btn-primary" ng-click="$ctrl.ok()">去看看</button>'
              + '</div></div>',
              controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance) {
                var $ctrl = this;
                $ctrl.cancel = function () {
                  $uibModalInstance.close()
                };
                $ctrl.ok = function () {
                  $uibModalInstance.close()
                  var studentId = '';
                  window.open('?studentId='+stus.id+'#/studentMgr/studentMgrIndex');
                }
              }],
          })
       }
    })
  }

  $scope.goback = function() {
    $state.go('studentsSearch');
  }

  $scope.defalutContactName = function(contact){
      if(_.some($scope.relationList,{label:contact.link_name.substr(contact.link_name.indexOf('的')+1)}) || !contact.link_name){
    	  if(contact.relation!=4&&contact.relation!=5){
                contact.link_name = ($scope.student.student_name?($scope.student.student_name + '的'):'') + _.result(_.find($scope.relationList,
                        function(relation) {
                          return relation.value == contact.relation;
                        }), 'label');
        }else  {
        	 contact.link_name= _.result(_.find($scope.relationList, function(relation) {
          return relation.value == contact.relation;
        }), 'label');
        }
      }
  }
  $scope.getRelationList();

  $scope.init = function(){
    if($state.params.resource_rec_id) {
       //通过资源记录id查询学员信息
      erp_studentsService.queryByResourceRecId({
          id:$state.params.resource_rec_id,
          page:1
      },function(resp){
          //console.log('-------------');
          console.log(resp.rows[0].resc);

          $scope.student.student_name = resp.rows[0].resc.name ? resp.rows[0].resc.name : '';
          $scope.newContact.link_name = $scope.student.student_name + $scope.newContact.link_name;
          $scope.student.sex = resp.rows[0].resc.sex.toString();
          if(resp.rows[0].resc.attend_sch_id) {
              $scope.selectedInfo.school = {
                  id:resp.rows[0].resc.attend_sch_id,
                  school_name :  resp.rows[0].resc.attend_sch_name
              };
          }
          $scope.student.counselor = {
                  start_date: $scope.student.counselor.start_date,
                  end_date: $scope.student.counselor.end_date,
                  encoding: resp.rows[0].cnselor_encoding,
                  counselor_id:resp.rows[0].cnselor_id,
                  name: resp.rows[0].cnselor_name
          };
         if(resp.rows[0].resc.rescLinks.length > 0) {
        	  $.each(resp.rows[0].resc.rescLinks,function(i,n) {
                  var crmContact = {
                      link_phone: n.mp,
                      link_name: n.name,
                      relation: n.rela_id,
                      remark: ''
                  }
                  $scope.student.studentContactList.push(_.cloneDeep(crmContact));
              });
         } else {
        	 console.log($scope.student.studentContactList.length);
        	 var crmContact = {
                     link_phone: resp.rows[0].resc.mp,
                     link_name: resp.rows[0].resc.name,
                     relation: "" + $scope.relationList[3].value
                 }
                 $scope.student.studentContactList.push(crmContact);
         };
         console.log($scope.student);
         erp_studentsService.querygradeBycrmGradeId({
             gradeId:resp.rows[0].grade_id
         },function(resp){
        	 $scope.selectedInfo.grade = {
                     id:resp.data.id,
                     grade_name : resp.data.grade_name
                 };
         });
      }) ;
    }

    erp_dictService.getDictData({"typeCode" : "studentStatus", "needProductLineCdtn" : "N"}, function(resp) {
      if (!resp.error) {
          $scope.studentStatus = resp.data;

          for (var i in $scope.studentStatus) {
              if($scope.studentStatus[i].name && $scope.studentStatus[i].name == '新生') {
                  $scope.student.student_status = $scope.studentStatus[i].code;
                  break
              }
          }
      }
    });
  }
  $scope.init();
  $scope.queryStudentGrade();
 }
