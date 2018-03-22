/**
 * Created by Lyk on 2016/7/19.
 */
angular.module('ework-ui').controller(
		'AccountSecurityCtrl',
		[ '$scope', '$log', '$state', 'PUBAccountService',
				'JudgePasswordService', AccountSecurityCtrl ]);

function AccountSecurityCtrl($scope, $log, $state, PUBAccountService,
		JudgePasswordService) {
	$scope.currentAccount = null;
	$scope.nextJudgePassword = null;
	$scope.nowPassword = null;
	// 显示当前账户信息
	function showCurrentAccount() {
		PUBAccountService.query({}, function(resp) {
			//if (resp.error == 'false') {
				$log.log(resp.data);
				$scope.currentAccount = resp.data;
			//}
		});
		
	}
	// 更新当前的账户信息

	$scope.clickUpdateCurrentAccount = function() {
		JudgePassword();
	}
	function doConfirm(){
		var nextJudgePassword = $scope.nextJudgePassword;
		if (nextJudgePassword == "true") {
			var newAccountPassword = $scope.newAccountPassword;
			if (newAccountPassword != null && newAccountPassword != "") {
				var confirmPassword = $scope.confirmPassword;
				if (newAccountPassword == confirmPassword) {
					$scope.currentAccount.password=newAccountPassword;
					updateCurrentAccount();
				} else {
					alert("请输入相同的密码");
				}
			} else {
				alert("请输入新的密码");
			}
		}else{
			alert("当前登录密码输入错误");
		}
		
	}
	/**
	 * 判断当前用户输入的密码是否正确
	 */
	function JudgePassword() {
		var params = {};
		params.nowPassword = $scope.nowPassword;
		JudgePasswordService.get(params, function(resp) {
			$log.log(resp.data);
			$scope.nextJudgePassword = resp.data;
			if($scope.nextJudgePassword!='false'){doConfirm()}
		});
	}
	/**
	 * 修改密码
	 */
	function updateCurrentAccount() {
		//$scope.currentAccount.id=0;
		$log.log("new password is:"+$scope.currentAccount.password);
		PUBAccountService.put($scope.currentAccount, function(resp) {
				alert("修改成功！");
		}),function(err){
			alert("修改失败：" + resp.message + ",请截图反馈给ERP客服！");
		}
	}
	
	showCurrentAccount();

}