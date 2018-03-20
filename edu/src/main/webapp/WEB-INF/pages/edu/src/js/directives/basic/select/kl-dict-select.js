/**
 * 团队下拉框选择指令
 * <kl-bu-select bu-id="buId"></kl-bu-select>
 */
angular.module('ework-ui').directive('klDictSelect',[
	'erp_dictService',
	klDictSelect
]);

function klDictSelect(
    erp_dictService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			dictType: '=?',
			bindData: '=?'
		},
		template: '<select ng-model="bindData" class="form-control"'
		+ ' ng-options="dictData.code as dictData.name for dictData in dictDataList">'
    + '<option value="" ng-if="!hideOptionAll">全部</option>'
		+ '</select>',
		link: function (scope, element, attrs) {
			scope.dictDataList = [];
			if (scope.dictType) {
        erp_dictService.query({
					code: scope.dictType
				}, function(resp) {
					if (!resp.error) {
						scope.dictDataList = resp.data;
					} else  {
						console.log(resp.message)
					}
				});
			}
		}
	}
}
