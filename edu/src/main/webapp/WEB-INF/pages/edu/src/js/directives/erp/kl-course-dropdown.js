angular.module('ework-ui')
  .directive('klCourseDropdown', ['erp_courseService', '$uibMsgbox', klCourseDropdown])

function klCourseDropdown(
  erp_courseService,
  $uibMsgbox
) {
  return {
    restrict: 'EA',
    transclude: true,
    scope: {
      multi: '=',
      klSelected: '&'
    },
    template: '<div class="input-group kl-choose-teacher" '
        + 'auto-close="disabled" uib-dropdown is-open="isopen" on-toggle="onToggle(open)"> '
        + '<div uib-dropdown-menu class="dropdown-menu {{floatdir}}" style="width: 420px;"> '
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
              + 'ng-if="isLoading != \'isLoading\' && (!courseList || !courseList.length)"> '
              + '<span>暂无课程</span>'
            + '</div> '
            +'<table class="table table-hover" ng-if="isLoading != \'isLoading\' && courseList && courseList.length"> '
            + '<tr ng-repeat="course in courseList"> '
              +'<td class="cur" uib-popover="课程名称：{{course.course_name}}，课程编码：【{{course.course_no}}】，教师名称：【{{course.teacher_name}}】，年级：【{{course.grade_name}}】" '
              +' popover-trigger="\'mouseenter\'" ng-click="selectedCourse(course)">'
                + ' {{course.course_name}}'
              +'</td> '
            + '</tr> '
            + '<tr ng-if="isLoading == \'isLoading\'"> '
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
      scope.searchparam = scope.searchparam || {}
      if (attrs.coursetype) {
        scope.searchparam.business_type = attrs.coursetype
      }

      if (attrs.ydyladder) {
          scope.searchparam.ydyLadder = attrs.ydyladder
      }
      scope.floatdir = attrs.floatdir == 'pull-left' ? 'pull-left':'pull-right' 
      scope.isopen = false
      scope.search_info = ''
      scope.courseList = []
      scope.isLoading = ''
      scope.choosenCourseList = []

      // TODO 优化右边滚动条 baiqb@klxuexi.org
      
      scope.changeSearchInfo = function () {
        scope.searchparam.search_info = scope.search_info
        scope.searchparam.status=1;
        scope.searchparam.pageSize=100;
        scope.isLoading = 'isLoading';
        erp_courseService.query(scope.searchparam,function(resp){
          scope.isLoading = '';
          if(!resp.error){
            scope.courseList = resp.data;
          }else{
            $uibMsgbox.error(resp.message);
          }
        })
      },

      // 在指令中，调用父scope方法，传参需要以对象的形式传入
      // 对象传入的属性名称和controller中方法传入的参数名称一致
      scope.selectedCourse = function (course) {
        if (!scope.multi) {
          scope.klSelected({
            course: course
          })
          scope.isopen = false
          return
        }

        if (_.findIndex(scope.choosenCourseList, course) != -1) {
          scope.choosenCourseList.push(course)
        } else {
          _.remove(scope.choosenCourseList, course)
        }
      }
      
      scope.changeSearchInfo()
    }
  }
}