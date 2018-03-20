/**
 * 课程季选择列表
 */
angular.module('ework-ui').directive('klTimeseasonSelect',[
	'erp_timeSeasonService',
	klTimeseasonSelect
])

function klTimeseasonSelect(
	erp_timeSeasonService
) {
	return {
		restrict: 'EA',
		replace: true,
		scope: {
			timeSeason: '=timeSeason'
		},
		template: '<select ng-model="timeSeason" class="form-control"'
		+		' ng-options="timeSeason.course_season_name for timeSeason in timeSeasonList">'
		+ '</select>',
		link: function(scope, element, attrs) {
			scope.timeSeasonList = []
			erp_timeSeasonService.query({}, function (resp) {
				if (!resp.error) {
					scope.timeSeasonList = resp.data;
					scope.timeSeasonList.unshift({id:null,course_season_name:'全部'});
					scope.timeSeason = scope.timeSeasonList[0];
				}
			})
		}
	}
}