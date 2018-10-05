/* 
onSearch: 输入搜索时调用的查询方法 
  <kl-teacher-dropdown on-search="onSearch(search_info)">
  $scope.onSearch = function (search_info) {
    var searchparam = {
      search_info: search_info
    }
    return erp_teacherService.page(scope.searchparam).$promise;
  }
*/
angular.module('ework-ui')
  .directive('klTeacherDropdown', ['erp_teacherService', '$uibMsgbox', klTeacherDropdown])

function klTeacherDropdown(
  erp_teacherService,
  $uibMsgbox
) {
  return {
    restrict: 'EA',
    transclude: true,
    scope: {
      searchparam: '=?',
      seachTag: '=?',
      multi: '=?',
      onSearch: '&?',
      klSelected: '&'
    },
    template: '<div class="input-group kl-choose-teacher" '
        + 'auto-close="disabled" uib-dropdown is-open="isopen" on-toggle="onToggle(open)"> '
        + '<div uib-dropdown-menu class="dropdown-menu floatdir"> '
          + '<form class="form" align="center" style="margin-bottom: 15px;"> '
            + '<div class="form-group"> '
              + '<div class="input-group">'
              + '<input type="text" class="form-control" ng-model="search_info" ng-change="changeSearchInfo()" /> '
              + '<span class="input-group-btn"><button class="btn btn-default"><i class="fa fa-search"></i></button></span>'
              + '</div>'
            + '</div> '
          + '</form> '
          + '<div class="list-wrapper" >'
           + '<div class="text-center" '
              + 'ng-if="isLoadingTeacherList != \'isLoadingTeacherList\' && (!teacherList || !teacherList.length)"> '
              + '<span>暂无老师</span>'
            + '</div> '
            +'<table class="table table-hover" ng-if="isLoadingTeacherList != \'isLoadingTeacherList\' && teacherList && teacherList.length"> '
            + '<tr ng-repeat="teacher in teacherList"> '
              +'<td class="cur" popover-class="uib-popover"   uib-popover="姓名：{{teacher.teacher_name}}，编码：{{teacher.encoding}}" '
                + ' popover-trigger="\'mouseenter\'" ng-click="selectedTeacher(teacher)">'
                + ' {{teacher.teacher_name}}，{{teacher.encoding}} '
              +'</td> '
            + '</tr> '
            + '<tr ng-if="isLoadingTeacherList == \'isLoadingTeacherList\'"> '
              + '<td align="center"><img src="img/erp/loading2.gif"></td> '
            + '</tr> '
          + '</table>'
          + '</div>'
          + '<div class="row" ng-show="multi == true">'
            +'<div class="col-sm-12 text-center">'
            +   '<button class="btn btn-danger btn-sm">取消</button> '
            +'<button class="btn btn-primary btn-sm">确定</button>'
            +'</div>'
          +'</div>'
        + '</div> '
        + '<span class="input-group-btn" uib-dropdown-toggle ng-transclude> '
        + '</span> '
      + '</div> ',
    link: function (scope, element, attrs) {
      scope.searchparam = scope.searchparam || {};
      scope.isopen = false;
      scope.search_info = '';
      scope.teacherList = []
      scope.isLoadingTeacherList = '';
      scope.choosenTeacherList = [];

      if (!_.isFunction(scope.onSearch)) {
        scope.onSearch = function (search_info) {
          var searchparam = {
            search_info: search_info
          }
          scope.searchparam.onlyValidTeacher = 'Y'
          return erp_teacherService.query(scope.searchparam).$promise;
        }
      }

      // TODO 优化右边滚动条 baiqb@klxuexi.org
      scope.floatdir = attrs.floatdir == 'pull-left' ? 'pull-left':'pull-right' 
      
      scope.changeSearchInfo = function () {
        scope.searchparam.search_info = scope.search_info;
        scope.isLoadingTeacherList = 'isLoadingTeacherList';
        // erp_teacherService.page(scope.searchparam,function(resp){
        //   scope.isLoadingTeacherList = '';
        //   if(!resp.error){
        //     scope.teacherList = resp.data;
        //   }else{
        //     $uibMsgbox.error(resp.message);
        //   }
        // })
        scope.onSearch({search_info: scope.search_info}).then(function (resp) {
          scope.isLoadingTeacherList = '';
          if(!resp.error){
            scope.teacherList = resp.data;
          }else{
            $uibMsgbox.error(resp.message);
          }
        })
      }
      // 在指令中，调用父scope方法，传参需要以对象的形式传入
      // 对象传入的属性名称和controller中方法传入的参数名称一致
      scope.selectedTeacher = function (teacher) {
        if (!scope.multi) {
          scope.klSelected({
            teacher: teacher
          })
          scope.isopen = false
          return
        }

        if (_.findIndex(scope.choosenTeacherList, teacher) != -1) {
          scope.choosenTeacherList.push(teacher)
        } else {
          _.remove(scope.choosenTeacherList, teacher)
        }
      }
      scope.$watch('seachTag', function ( newValude, oldValue) {
        scope.changeSearchInfo()
      })
      scope.changeSearchInfo()
    }
  }
}