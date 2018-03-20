/**
 * 团队下拉框选择指令
 * <kl-branch-select bu-id="buId" branch-id="branchId"></kl-branch-select>
 */
angular.module('ework-ui').directive('klBranchSelect',[
	'PUBORGService',
	'erp_organizationService',
	klBranchSelect
]);

function klBranchSelect(
	PUBORGService,
	erp_organizationService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			buId: '=buId',
			branchId: '=?branchId',
			hideOptionAll: '=?',
      queryAll: '=?',
			onBranchChange: '&?'
		},
		template: '<select ng-model="branchId" class="form-control" ng-change="handleBranchChange(branchId)"'
		+ ' ng-options="branch.id as branch.org_name for branch in branchList">'
    + '<option value="" ng-if="!hideOptionAll">全部</option>'
		+ '</select>',
		link: function (scope, element, attrs) {
			scope.branchList = []

			if (scope.buId) {
				getBranchList();
			}
			
			scope.handleBranchChange = function (branchId) {
				var branch = _.find(scope.branchList, {id: branchId})
				if (!!branch && _.isFunction(scope.onBranchChange)) {
					scope.onBranchChange({
						branch: branch
					})
				}
			}
			scope.$watch('branchId', function (newValue, oldValue) {
				scope.branchId = isNaN(+scope.branchId) || scope.branchId <= 0 ? null : +scope.branchId
				if (!!scope.branchId) {
					scope.handleBranchChange(scope.branchId)
				}
			})
			scope.$watch('buId', function (newValue, oldValue) {
				scope.buId = isNaN(+scope.buId) || scope.buId <= 0 ? null : +scope.buId
				if (newValue && newValue != oldValue) {
					getBranchList();
				}
			})

			function getBranchList() {
				return erp_organizationService.branchList({
					p_buId: scope.buId,
					p_queryAll: scope.queryAll
				}, function(resp) {
	        if (!resp.error) {
	          scope.branchList = resp.data;
	          _.remove(scope.branchList, function(n) {
	          	return n.parent_id != scope.buId;
						})
						if (scope.branchId) {
							scope.handleBranchChange(scope.branchId)
						}
	        }
	      })
			}
		}
	}
}
