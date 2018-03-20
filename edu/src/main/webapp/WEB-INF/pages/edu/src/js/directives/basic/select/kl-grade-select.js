/**
 * 年级下拉框
 * @author baiqb@klxuexi.org
 * @date 2017/5/25 19:58:51
 */
angular.module('ework-ui').directive('klGradeSelect', [
	'erp_gradeService',
	klGradeSelect
]);

function klGradeSelect(
	erp_gradeService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			gradeId: '=?',
			gradeName: '=?',
			grade: '=?'
		},
		template: '<select class="form-control" ng-model="grade"'
		+ 	'ng-options="grade.grade_name for grade in gradeList" ng-required="required" >'
		+ '</select>',
		link: function (scope, element, attrs) {
			scope.grade = scope.grade || {}
			scope.gradeId = Number(scope.gradeId || 0)
			scope.gradeName = scope.gradeName || ''
			scope.required = attrs.required
			scope.gradeList = []
			scope.$watch('grade', function (newValue, oldValue) {
				if (newValue && newValue.id) {
					scope.gradeId = newValue.id
					scope.gradeName = newValue.grade_name
				}
			})
			scope.$watch('gradeId', function (newValue, oldValue) {
				if (newValue) {
					scope.grade = _.find(scope.gradeList, {id: newValue})
				}
			})
			erp_gradeService.query({
				pageSize: 999
			}, function (resp) {
				if (!resp.error) {
					scope.gradeList = resp.data;
					if (scope.gradeId) {
						scope.grade = _.find(scope.gradeList, {id: scope.gradeId})
					}
				}
			})
		}
	}
}