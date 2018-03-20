/**
 * 选择业务团队和业务校区组件
 */
angular.module('ework-ui')
  .directive('rdBranch', ['PUBORGService', rdBranch]);

function rdBranch(
  PUBORGService
) {
  return {
    restrict: 'EA',
    replace: true,
    transclude: true,
    scope: {
      buId: '=?ngBuId',
      branchId: '=?ngBranchId',
      onChange: '&ngValueChange'
    },
    template: '<div class="form-inline">'
      + '<div class="form-group">'
      +   '团队：<kl-bu-select bu-id="buId"></kl-bu-select>'
      + '</div>'
      + '<div class="form-group">'
      +   '校区：<kl-branch-select bu-id="buId" branch-id="branchId">'
      + '</kl-branch-select>'
      + '</div>'
      + '<ng-transclude></ng-transclude>'
      + '</div>',
    link: function (scope, element, attrs) {
      scope.$watch('buId', function () {
        scope.onChange();
      })
      // 监听校区变化
      scope.$watch('branchId', function () {
        scope.onChange();
      })
    }
  }
}
