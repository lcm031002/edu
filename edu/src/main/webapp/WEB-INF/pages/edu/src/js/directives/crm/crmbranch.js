/**
 * 选择业务团队和业务校区组件
 */
angular.module('ework-ui')
  .directive('crmBranch', ['PUBORGService', crmBranch]);

function crmBranch(
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
    template:'<div class="col-lg-4">'  
        + '<div class="form-group">'
        + '<label class="col-sm-4 label_name control-label">业务团队： </label>'
        + '<div class="col-sm-8">'
        + '<kl-bu-select bu-id="buId"></kl-bu-select>'
        + '</div>'
        + '<ng-transclude></ng-transclude>'
        + '</div>'
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
