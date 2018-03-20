angular.module('ework-ui').controller('modal_imageUploadController', [
  '$rootScope',
  '$scope',
  '$uibMsgbox',
  '$uibModalInstance',
  'erp_studentsService',
  'onUploadImg',
  modal_imageUploadController
])

function modal_imageUploadController(
  $rootScope,
  $scope,
  $uibMsgbox,
  $uibModalInstance,
  erp_studentsService,
  onUploadImg
) {
  $scope.result = null;
}
