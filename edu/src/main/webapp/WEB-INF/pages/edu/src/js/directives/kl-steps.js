angular.module('ework-ui')
  .directive('klSteps', [klSteps])

function klSteps() {
  return {
    restrict: 'EA',
    scope: {
      current: '=klCurrent',
      steps: '=klSteps'
    },
    template: 
       '<div class="kl-progress-title flex-box">'
      + '<div class="flex_1 text-center" ng-repeat="step in steps">'
      +   '<i ng-if="step.iconCls" class="{{step.iconCls}}"></i>{{step.title}}</div>'
      +'</div>'
      +'<div class="progress kl-progress">'
      + '<div class="progress-bar progress-bar-info" '
        + 'role="progressbar" aria-valuenow="20" aria-valuemin="0" '
        + 'aria-valuemax="100" style="width: {{(current / steps.length) * 100}}%">'
      + '</div>'
    + '</div>'
  }
}