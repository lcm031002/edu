"use strict";

angular.module('ework-ui').controller('crmRescRecTransferController', [
	'$rootScope',
	'$scope',
	'$log',
	'$uibMsgbox',
	'crm_tranfCnselorFromService',
	'crm_tranfCnselorToService',
	'crm_tranfRescQueryService',
	'crm_tranfSingleToService',
	'crm_tranfBatchService',
	crmRescRecTransferController
	]);

function crmRescRecTransferController(
		$rootScope, $scope, $log, $uibMsgbox,
		crm_tranfCnselorFromService,
		crm_tranfCnselorToService,
		crm_tranfRescQueryService,
		crm_tranfSingleToService,
		crm_tranfBatchService) {
	
	 $scope.resArray = [];
	  $scope.counselorFrom = [];
	  $scope.counselorTo = [];
	  $scope.students = [];
	  $scope.chosenCounselorToId = null;
	  $scope.chosenCounselorToName = '';
	  $scope.searchCounselorFrom = '';
	  $scope.searchCounselorTo = '';
	  $scope.cnselor_from = {};

	$scope.fromPageConf = {
		    currentPage: 1, //当前页
		    totalItems: 0,
		    itemsPerPage: 10,
		    showInfos: false,
		    onChange: function() {
		      $scope.getCounselorFrom()
		    }
		  };

		  $scope.toPageConf = {
		    currentPage: 1,
		    totalItems: 0,
		    itemsPerPage: 10,
		    showInfos: false,
		    onChange: function() {
		      $scope.getCounselorTo()
		    }
		  };
		  
		  // 初始化老师
		  function initResArray() {
		    $scope.resArray.splice(0, $scope.resArray.length)
		    for (var i = 0; i < $scope.counselorFrom.length; i++) {
		      $scope.counselorFrom[i].isStu = false;
		      $scope.counselorFrom[i].showStu = false;
		      $scope.resArray.push($scope.counselorFrom[i]);
		    }
		  };
		  
		  
		// 获取可转移出去的学管师
		  $scope.getCounselorFrom = function(counselor) {
		    var modalInstance = $uibMsgbox.waiting('数据加载中，请稍候...');
		    crm_tranfCnselorFromService.query({
		    	page: $scope.fromPageConf.currentPage,
		    	rows: $scope.fromPageConf.itemsPerPage,
		        p_counselor_name: $scope.searchCounselorFrom
		    }, function(resp) {
		      modalInstance.close();
		      if (!resp.error) {
			        $scope.fromPageConf.totalItems = resp.total;
			        $scope.counselorFrom = resp.rows;
			        initResArray();
			      } else {
			        $uibMsgbox.error(resp.message)
			      }
		    })
		  };
		  
		// 批量转移学员
		  $scope.transferAll = function (counselor) {
		    if (!$scope.chosenCounselorToId) {
		      $uibMsgbox.warn('请选择要转入的咨询师！')
		      return
		    }
		    $uibMsgbox.confirm('确定要将<span class="text-danger">【' + counselor.EMPLOYEE_NAME 
		      + '】</span>咨询师的所有资源转移给<span class="text-danger">【'+ $scope.chosenCounselorToName +'】</span>咨询师?', function (res) {
		      if (res == 'yes') {
		        var _modalInstance = $uibMsgbox.waiting('转移中，请稍候...')
		        crm_tranfBatchService.post({
		        	af_cnselor_id: $scope.chosenCounselorToId,
		        	bf_cnselor_id: $scope.cnselor_from.ID
		        }, function (resp) {
		          _modalInstance.close();
		          if (resp.result) {
		            $uibMsgbox.success('转移成功！')
		            $scope.getCounselorFrom();
		          } else {
		            $uibMsgbox.error(resp.msg)
		          }
		        }) 
		      }
		    })
		  }

		  // 转移单个学员
		  $scope.transfer = function (rec) {
		    if (!$scope.chosenCounselorToId) {
		      $uibMsgbox.warn('请选择要转入的咨询师！')
		      return
		    }
		    $uibMsgbox.confirm('确定要将线索<span class="text-danger">【称呼：' + rec.resc.call 
		      + '，学生姓名：' + rec.resc.name + '】</span>转移给<span class="text-danger">【'+ $scope.chosenCounselorToName +'】</span>咨询师?', function (res) {
		      if (res == 'yes') {
		        var _modalInstance = $uibMsgbox.waiting('转移中，请稍候...')
		        crm_tranfSingleToService.post({
		          af_cnselor_id:  $scope.chosenCounselorToId,
		          resc_rec_id:rec.id,
		          bf_cnselor_id:  $scope.cnselor_from.ID
		        }, function (resp) {
		          _modalInstance.close();
		          if (resp.result) {
		            $uibMsgbox.success('转移成功！');
		            $scope.getCounselorFrom();
		          } else {
		            $uibMsgbox.error(resp.msg)
		          }
		        }) 
		      }
		    })
		  }

		  
		  // 显示或隐藏老师底下的学生
		  $scope.toggleRes = function(item) {
		    if (item.isStu) {
		      return
		    }
		    item.showStu = !item.showStu
		    if (item.showStu) {
		      if (item.students) {
		        $scope.showStudents(item)
		      } else {
		        $scope.getStudents(item)
		      }
		    } else {
		      $scope.hideStudents()
		    }
		  };
		  
		  // 隐藏学生
		  $scope.hideStudents = function() {
		    for (var i = $scope.resArray.length - 1; i >= 0; i--) {
		      if ($scope.resArray[i].showStu) {
		        $scope.resArray[i].showStu = false
		      }
		      if($scope.resArray[i].isStu) {
		        console.log($scope.resArray[i])
		        $scope.resArray.splice(i, 1)
		      }
		    }
		  };

		  // 获取学生
		  $scope.getStudents = function (counselor, page) {
		    var modalInstance = $uibMsgbox.waiting('数据加载中，请稍候...');
		    $scope.cnselor_from = counselor;
		    page = page || 1
		    crm_tranfRescQueryService.query({
		      p_cnselor_id: counselor.ID
		    }, function(resp){
		      modalInstance.close();
		      if (!counselor.students || !counselor.students.concat) {
		        counselor.students = []
		      } 
		      if (!resp.error) {
		        for (var i = 0; i < resp.datas.length; i++) {
		          resp.datas[i].isStu = true
		          counselor.students.push(resp.datas[i])
		        }
		        $scope.showStudents(counselor)
		      } else {
		        $uibMsgbox.error(resp.message)
		      }
		    })
		  };

		  // 显示学生
		  $scope.showStudents =function(counselor) {
		    $scope.resArray.splice(0, $scope.resArray.length);
		    for (var i = 0; i < $scope.counselorFrom.length; i++) {
		      $scope.resArray.push($scope.counselorFrom[i]);
		      if ($scope.counselorFrom[i].ID == counselor.ID) {
		        $scope.resArray = $scope.resArray.concat(counselor.students);
		      } else {
		        $scope.counselorFrom[i].showStu = false;
		      }
		    }
		  };
		  
		  // 获取可转移进来的学管师
		  $scope.getCounselorTo = function(counselor) {
			  crm_tranfCnselorToService.query({
		      page: $scope.toPageConf.currentPage,
		      rows: $scope.toPageConf.itemsPerPage,
		      p_counselor_name: $scope.searchCounselorTo
		    }, function(resp) {
		      $scope.toPageConf.totalItems = resp.total;
		      $scope.counselorTo = resp.rows
		    })
		  };
		  
		  // 选择要转入的老师
		  $scope.chooseCounselorTo = function(counselor) {
		    $scope.chosenCounselorToId = counselor.id
		    $scope.chosenCounselorToName = counselor.employee_name
		  }
		  
		  $scope.getCounselorFrom();
		  $scope.getCounselorTo();
}

