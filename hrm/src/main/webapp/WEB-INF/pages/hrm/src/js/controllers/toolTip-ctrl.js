angular.module('ework-ui').controller('TooltipDemoCtrl', function ($scope, $sce) {
  $scope.dynamicTooltip = '此字段不能为空!';
  $scope.dynamicTooltipText = 'dynamic';
  $scope.htmlTooltip = $sce.trustAsHtml('I\'ve been made <b>bold</b>!');
  $scope.placement = {
    options: [
      'top',
      'top-left',
      'top-right',
      'bottom',
      'bottom-left',
      'bottom-right',
      'left',
      'left-top',
      'left-bottom',
      'right',
      'right-top',
      'right-bottom'
    ],
    selected: 'top'
  };
});