/**
 * 教研组下拉框
 */
angular.module('ework-ui').directive('klTeacherGroupSelect', [
	'erp_teacherGroupService',
	klTeacherGroupSelect
])

function klTeacherGroupSelect(
	erp_teacherGroupService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			group: '=?',
			teachGroupId: '=?',
			teachGroupName: '=?'
		},
		template: '<select class="form-control" '
		+ ' ng-model="group" ng-options="group.teach_group_name for group in teachGroupList">'
		+ ' </select>',
		link: function (scope, element, attrs) {
			scope.group = scope.group || {}
			scope.teachGroupId = scope.teachGroupId || null
			scope.teachGroupName = scope.teachGroupName || ''
			scope.teachGroupList = [];
			scope.$watch('group', function (newVal, oldVal) {
				if (newVal && newVal.id && newVal.teach_group_name) {
					scope.teachGroupId = newVal.id
					scope.teachGroupName = newVal.teach_group_name
				}
			})
			scope.$watch('teachGroupId', function (newVal, oldVal) {
				if (newVal != scope.group.id) {
					scope.group = _.find(scope.teachGroupList, {id: newVal})
				}
			})
			 // 获取教研组
			erp_teacherGroupService.queryList({}, function (resp) {
				if (!resp.error) {
					scope.teachGroupList = resp.data;
					if (scope.teachGroupId) {
						scope.group = _.find(scope.teachGroupList, { id: scope.teachGroupId })
					}
				} else {
					$uibMsgbox.error(resp.message)
				}
			})
		}
	}
}