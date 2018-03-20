/**
 * 团队下拉框选择指令 <crm-bu-select bu-id="buId"></crm-bu-select>
 */
angular.module('ework-ui').directive('crmBuSelect',
		[ 'PUBORGService', 'PUBORGSelectedService', crmBuSelect ]);

function crmBuSelect(PUBORGService, PUBORGSelectedService) {
	return {
		restrict : 'EA',
		replace : true,
		scope : {
			buId : '=?buId'
		},
		template : '<select ng-model="buId" class="form-control" id="bu_obj"'
				+ ' ng-options="bu.buId as bu.text for bu in buList">'
				+ '</select>',
		link : function(scope, element, attrs) {
			scope.buList = []

			if (!scope.buId) {
				PUBORGSelectedService.query({}, function(resp) {
					if (!resp.error) {
						scope.buId = resp.data.buId;
					} else {
						console.log(resp.message)
					}
				})
			}

			PUBORGService.queryBu({}, function(resp) {
				if (!resp.error) {
					scope.buList = resp.data;
				}
			})
		}
	}
}
