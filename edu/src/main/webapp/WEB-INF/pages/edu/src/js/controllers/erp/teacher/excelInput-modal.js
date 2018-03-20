angular.module('ework-ui').controller('erp_teacherExcelInputModalController', [
  '$rootScope',
  '$scope',
  '$uibModalInstance',
  '$uibMsgbox',
  '$filter',
  'teacherDetail',
  'erp_tmpTeacherInfoService',
  function(
  	$rootScope,
  	$scope,
  	$uibModalInstance,
  	$uibMsgbox,
  	$filter,
  	teacherDetail,
  	erp_tmpTeacherInfoService
  ) {
	  	
  		$scope.teacherDetail = _.cloneDeep(teacherDetail);
  		
  		$scope.handleModalCancel = function () {
  			$uibModalInstance.dismiss();
  		}
  		
  		$scope.handleModalConfirm = function () {
  			delete $scope.teacherDetail.errorList;
  			delete $scope.teacherDetail.errorMsgObj;
        console.log($scope.teacherDetail)
  			erp_tmpTeacherInfoService.put($scope.teacherDetail, function(resp) {
  				if (!resp.error) {
		            if (_.isArray(resp.data.errorList) && resp.data.errorList.length > 0) {
		              $uibMsgbox.confirm('检验失败，是否继续修改？', function (res) {
		            	  if (res == 'yes') {
		            		  _.assign($scope.teacherDetail, resp.data);
		                	} else {
		                		_.assign(teacherDetail, resp.data);
		                		$uibModalInstance.close(resp.data);
		                	}
		              	});
		            } else {
		            	_.assign(teacherDetail, resp.data);
		            	$uibModalInstance.close(resp.data);
		            }
  				} else {
  					$uibMsgbox.error(resp.message);
  				}
  			});
  		}
  	
  		$scope.toManage = function() {
	  		erp_tmpTeacherInfoService.toManage({}, function(resp) {
	  			if (!resp.error) {
	  				$scope.statusList = resp.statusList;
	  			}
	  		});
  		}
  }
]);
