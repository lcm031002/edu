angular.module('ework-ui')
  .directive('rdModal', ['$uibModal', rdModal]);

function rdModal(
    $uibModal
  ) {
  return {
    restrict: 'AE',
    transclude: {
      title: '?rdModalTitle',
      body: 'rdModalBody',
      footer: '?rdModalFooter'
    },
    template: '<div class="klxx-modal-primary"><div class="modal-header">'
      + '  <button type="button" class="close" ng-click="$dismiss()">'
      + '    <span aria-hidden="true">&times;</span>'
      + '  </button>'
      + '  <h3 class="modal-title" ng-transclude="title">信息</h3>'
      + '</div>'
      + '<div class="modal-body" ng-transclude="body"></div>'
      + '<div class="modal-footer" ng-transclude="footer"></div></div>'
  }
}