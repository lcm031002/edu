/**
 * 科目下拉框
 */
angular.module('ework-ui').directive('klSubjectSelect', [
	'erp_subjectService',
	klSubjectSelect
])

function klSubjectSelect(
	erp_subjectService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			subject: '=?',
			subjectId: '=?',
			subjectName: '=?'
		},
		template: '<select class="form-control" '
		+ ' ng-model="subject" ng-options="subject.name for subject in subjectList">'
		+ ' </select>',
		link: function (scope, element, attrs) {
			scope.subject = scope.subject || {}
			scope.subjectId = scope.subjectId || null
			scope.subjectName = scope.subjectName || ''
			scope.subjectList = [];
			scope.$watch('subject', function (newVal, oldVal) {
				if (newVal && newVal.id && newVal.name) {
					scope.subjectId = newVal.id
					scope.subjectName = newVal.name
				}
			})
			scope.$watch('subjectId', function (newVal, oldVal) {
				if (newVal != scope.subject.id) {
					scope.subject = _.find(scope.subjectList, {id: newVal})
				}
			})
			erp_subjectService.query({
				pageSize: 999
			}, function(resp) {
				if (!resp.error) {
					scope.subjectList = resp.data;
					if (scope.subjectId) {
						scope.subject = _.find(scope.subjectList, {id: scope.subjectId})
					}
				}
			})
		}
	}
}