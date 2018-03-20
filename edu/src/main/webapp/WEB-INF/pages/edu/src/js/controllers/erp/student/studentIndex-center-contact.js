"use strict";
angular.module('ework-ui').controller('erp_studentCenterContactController', [
  '$scope',
  '$log',
  '$uibMsgbox',
  'erp_studentContactRelationService',
  'erp_studentContactService',
  'erp_studentsService',
  erp_studentCenterContactController
]);

function erp_studentCenterContactController(
  $scope,
  $log,
  $uibMsgbox,
  erp_studentContactRelationService,
  erp_studentContactService,
  erp_studentsService
) {
  $scope.studentId = $("#rootIndex_studentId").val();

  $scope.optype = 'add';
  // 学员信息
  $scope.student = {};
  // 联系人信息
  $scope.studentContact = {};
  // 联系人列表
  $scope.RelationList = [];
  // 联系人关系列表
  $scope.contactRelList = [];

  $scope.toAddStudentContact = function() {
    $scope.optype = 'add';
    $scope.studentContact = {};
    showContactModal();
  }

  $scope.toUpdate = function(contact) {
  	$scope.optype = 'edit';
    $scope.studentContact = contact;
    if ($scope.studentContact && $scope.studentContact.relation) {
      $log.log("begin to query option,relation is " + $scope.studentContact.relation);
      $scope.studentContact.relationModel = getRelationOption($scope.studentContact.relation);
    }
  	showContactModal();
  };

  $scope.toDefault = function(contact) {
    erp_studentContactService.updateDefaultContact({
      id:contact.student_id,
      phone:contact.link_phone,
      contact_id:contact.id,
      relation_name:contact.relation_name
    },function(resp) {
      if (!resp.error) {
        $uibMsgbox.alert("设置成功！");
        queryContact();
      } else {
        $uibMsgbox.error(resp.message);
      }
    })
  };

  $scope.deleteConfirm = function(contact) {
    var contact_ids = [];
    contact_ids.push(contact.id);
    erp_studentContactService.del({
      contact_id: contact_ids,
      studentId:$scope.studentId
    }).$promise.then(function(resp) {
      if (!resp.error) {
        $uibMsgbox.alert("删除成功！");
        queryContact();
      } else {
        $uibMsgbox.error(resp.message);
      }
    });
  };


  $scope.onSaveContact = function() {
    if (!isValid()) {
      return false;
    }

    saveStudentContact().then(function(resp) {
      if (!resp.error) {
        $uibMsgbox.alert($scope.optype == 'add' ? '添加成功' : '修改成功！');
        $scope.studentContact = {};
        hideContactModal();
        queryContact();
      } else {
        $uibMsgbox.alert(resp.message);
      }
    })
  }

  $scope.onCloseModal = function() {
    hideContactModal();
  }

  function showContactModal() {
    $('#studentContactModal').modal('show');
  }

  function hideContactModal() {
    $('#studentContactModal').modal('hide');  	
  }

  function isValid() {
    if (!$scope.studentContact.link_phone || $scope.studentContact.link_phone == '输入联系人号码') {
      $uibMsgbox.alert("请输入联系人号码！");
      return false;
    }
    if (isEmpty($scope.studentContact.relationModel)) {
      $uibMsgbox.alert('关系不能为空');
      return false;
    }
    if (!Validator.mobile.test($scope.studentContact.link_phone)) {
      $uibMsgbox.alert('电话格式不正确');
      return false;
    }
    if ($scope.studentContact.link_name == '输入联系人姓名') {
      $scope.studentContact.link_name = '';
      return false;
    }
    return true;
  }

  function saveStudentContact() {
    $scope.studentContact.student_id = $scope.studentId;
    var contact = {};
    contact.student_id = $scope.studentId;
    contact.id = $scope.studentContact.id;
    contact.link_name = $scope.studentContact.link_name;
    contact.relation = $scope.studentContact.relationModel.value;
    contact.link_phone = $scope.studentContact.link_phone;
    contact.remark = $scope.studentContact.remark;

    return $scope.optype == 'add' ?
    		erp_studentContactService.post(contact).$promise
    	: erp_studentContactService.put(contact).$promise
  }
  /*
   * 查询联系人关系类型
   */
  function queryRelationList() {
    erp_studentContactRelationService.query({}, function(resp) {
      if (!resp.error) {
        $scope.RelationList = resp.data;
        if ($scope.RelationList && $scope.RelationList[0]) {
          $scope.studentContact.relationModel = $scope.RelationList[0].value;
        }
      }
    });
  }

  function getRelationOption(relation) {
    var Option = null;
    $.each($scope.RelationList, function(i, row) {
      if (row.value + '' == relation) {
        Option = row;
        $log.log("found option,see: option.value is " + row.value + ",label is " + row.label);
      }
    });
    return Option;
  }

  /*
   * 查询联系人关系列表
   */
  function queryContact() {
    erp_studentContactService.query({student_id: $scope.studentId}, function(resp) {
      if (!resp.error) {
        $scope.contactRelList = resp.data;
      }
    });
  }

  /**
   * 查询学生信息
   * @return {[type]} [description]
   */
  function queryStudentInfo() {
    erp_studentsService.query({
        row_num: 20,
        studentId: $scope.studentId
      },
      function(resp) {
        if (!resp.error && resp.data.length) {
          $scope.student = resp.data[0];
    			$('title').text('学员|' + $scope.student.student_name);
    			queryContact();
        } else {
          alert(resp.message);
        }
      });
  }
  $scope.defalutContactName = function(contact){
    if(_.some($scope.RelationList,{label:contact.link_name })|| !contact.link_name){
      contact.link_name = _.result(_.find($scope.RelationList, function(relation) {
        return relation.value == contact.relationModel.value;
      }), 'label');
    }
  }
  function init() {
    queryRelationList();
    queryStudentInfo();
  }

  init();
}
