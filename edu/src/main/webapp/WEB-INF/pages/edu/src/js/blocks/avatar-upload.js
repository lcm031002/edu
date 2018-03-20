angular.module('ework-ui').controller('blocks_avatarUploadController', [
  '$rootScope',
  '$scope',
  '$uibMsgbox',
  '$uibModalInstance',
  'erp_studentsService',
  'onUploadImg',
  blocks_avatarUploadController
])

function blocks_avatarUploadController(
  $rootScope,
  $scope,
  $uibMsgbox,
  $uibModalInstance,
  erp_studentsService,
  onUploadImg
) {
  $scope.originImgSrc = null;
  $scope.imgSrc = null;
  $scope.result = null;
  $scope.resultBlob = null;
  $scope.initCrop = false;

  $scope.fileChanged = function(e) {
    var files = e.target.files;
    var fileReader = new FileReader();
    fileReader.readAsDataURL(files[0]);
    fileReader.onload = function(e) {
      $scope.originImgSrc = this.result;
      $scope.imgSrc = this.result;
      $scope.$apply();
    };
  }

  $scope.cropImage = function() {
    $scope.initCrop = true;
  }

  $scope.uploadImage = function(result) {
    if ($scope.result) {
            onUploadImg(result, $uibModalInstance)
    } else {
      $uibMsgbox.alert("请检查图像是否已经上传并裁切！");
    }
  }

  $scope.reCrop = function() {
    $scope.imageCropStep = 2;
    delete $scope.result;
    delete $scope.resultBlob;
  }
}
