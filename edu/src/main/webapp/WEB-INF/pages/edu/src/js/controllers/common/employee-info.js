/**
 * Created by Lyk on 2016/7/19.
 */
angular.module('ework-ui')
    .controller('EmployeeInfoCtrl', [
    		'$rootScope',
        '$scope',
        '$log',
        '$state',
        'PUBEmployeeService',
         EmployeeInfoCtrl]);


function EmployeeInfoCtrl(
    $rootScope,
    $scope,
    $log,
    $state,
    PUBEmployeeService
    ){
  $rootScope.currentEmployee = null;
	$scope.currentEmployee=null;
	$scope.all=true
	$scope.readonly="编辑";
	//显示当前账户信息
	function showCurrentEmployee(){
		PUBEmployeeService.query({},function(resp){
           /* if(resp.error ==  'false'){*/
            	$log.log(resp.data)
              $rootScope.currentEmployee = resp.data;
            	$scope.currentEmployee=resp.data;
            //}
        });
	}
	$scope.updateReadonly=function(){
		if($scope.all){
			$scope.all=false;
			$scope.readonly="返回";
		}else{
			$scope.all=true;
			$scope.readonly="编辑";
		}
	};
	
	$scope.updateCurrentEmployee=function(){
		updateCurrentEmployee();
		
	}
	//更新当前的账户信息
	function updateCurrentEmployee(){
        PUBEmployeeService.put($scope.currentEmployee,function(resp){
            if(resp.error ==  'false'||resp.error == false){
                alert("修改成功！");
            }else{
                alert("修改失败："+resp.message+",请截图反馈给ERP客服！");
            }
        });
	}
   
	showCurrentEmployee();
}