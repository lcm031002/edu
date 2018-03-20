/**
 * 团队下拉框选择指令
 * <kl-bu-select bu-id="buId"></kl-bu-select>
 */
angular.module('ework-ui').directive('klBuSelect',[
	'PUBORGService',
	'PUBORGSelectedService',
	klBuSelect
]);

function klBuSelect(
	PUBORGService,
	PUBORGSelectedService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			buId: '=?buId'
		},
		template: '<select ng-model="buId" class="form-control"'
		+ ' ng-options="bu.buId as bu.text for bu in buList">'
		+ '</select>',
		link: function (scope, element, attrs) {
			scope.buList = []

            scope.buId = isNaN(+scope.buId)|| scope.buId <= 0 ?null : scope.buId
			if (!scope.buId) {
				PUBORGSelectedService.query({}, function(resp) {
					if (!resp.error) {
						scope.buId = resp.data.buId;
					} else  {
						console.log(resp.message)
					}
				})
			}

			PUBORGService.queryBu({}, function(resp) {
				if (!resp.error) {
				  scope.buList = resp.data;
				}
		 	})

			scope.$watch('buId', function ( newValude, oldValue) {
                scope.buId = isNaN(+scope.buId)|| scope.buId <= 0 ? null : +scope.buId
            })
		}
	}
}
