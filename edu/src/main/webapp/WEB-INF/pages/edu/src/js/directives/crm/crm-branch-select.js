/**
 * 团队下拉框选择指令 <crm-branch-select bu-id="buId" branch-id="branchId"></crm-branch-select>
 */
angular.module('ework-ui').directive('crmBranchSelect',
		[ 'crm_queryInputRightBranch', 'crm_querySelectedBranch',crmBranchSelect ]);

function crmBranchSelect(crm_queryInputRightBranch,crm_querySelectedBranch) {
	return {
		restrict : 'EA',
		replace : true,
		scope : {
			branchId : '=?branchId'
		},
		template : '<select ng-model="branchId" class="form-control"  id="branch_obj"'
				+ ' ng-options="branch.id as branch.text for branch in branchList">'
				+ '</select>',
		link : function(scope, element, attrs) {
			scope.branchList = []

			function getBranchList() {
				crm_queryInputRightBranch.query({}, function(resp) {
					if (!resp.error) {
						scope.branchList = resp.data;
						if(scope.branchList != null && scope.branchList.length >0){
							crm_querySelectedBranch.query({}, function(resp) {
								if (!resp.error) {
										scope.branchId = resp.branchs[0].id;
								}
							})
						}
					}
				})
			}
			
			getBranchList();
		}
	}
}
