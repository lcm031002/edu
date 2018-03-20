angular.module('ework-ui')
    .controller('erp_wfdComboInputController', [
        '$rootScope',
        '$scope',
        '$log',
        '$state',
        '$uibMsgbox',
        '$uibModal',
        'erp_wfdComboService',
        erp_wfdComboInputController
    ]);

function erp_wfdComboInputController($rootScope,
                                     $scope,
                                     $log,
                                     $state,
                                     $uibMsgbox,
                                     $uibModal,
                                     erp_wfdComboService) {
    $scope.importSuccessProgress = 0;
    $scope.importFailureProgress = 0;
    $scope.importSuccessItems = 0;
    $scope.importFailureItems = 0;
    $scope.currentImportItems = 1;
    // 当前步骤
    $scope.currentStep = 1;

    // 所有导入数据
    $scope.allDataArray = [];

    // 过滤后的数据
    $scope.filteredData = [];

    // 检验成功的数据
    $scope.validateSuccess = [];

    // 校验失败
    $scope.validateFailure = [];

    // 未校验数据
    $scope.uncheckedData = [];

    // 显示的数据
    $scope.currentPageData = [];

    // 校验状态
    $scope.validateStatus = '0';

    // 是否可以开始校验
    $scope.validateDisabled = true;

    // 需要上传的文件
    $scope.fileUpload = null;

    // 等待模态框
    $scope.waitingModal = null;

    // 分页配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 10,
        showFirstAndLast: true,
        onChange: function () {
            $scope.getPageData();
        }
    };

    // 所有的步骤
    $scope.steps = [{
        title: '1. 上传文件'
    }, {
        title: '2. 数据校验'
    }, {
        title: '3. 数据导入'
    }];

    // 校验文件
    $scope.handleValidate = function () {
        $scope.waitingModal = $uibMsgbox.waiting('正在上传，请稍候...')
        uploadFiles();
    };

    // 修改
    $scope.handleModify = function (data) {
        $uibModal.open({
            size: 'lg',
            templateUrl: 'templates/block/modal/course-wfd-combo-input.modal.html',
            controller: 'erp_wfdComboInputModalController',
            scope: $scope,
            resolve: {
                data: function () {
                    return data;
                }
            }
        }).result.then(function (res) {
        }, function () {});
    };

    // 上传的文件改变事件
    $scope.onFileChange = function (files) {
        $scope.fileUpload = files[0];
        $scope.validateDisabled = !$scope.fileUpload;
        $scope.$apply();
    };

    // 关闭当前页
    $scope.closeCurrentPage = function (argument) {
        history.back();
    };

    // 导入数据
    $scope.importData = function () {
        //TODO 控制导入按钮状态
        if($scope.allDataArray.length != $scope.validateSuccess.length) {
            $uibMsgbox.warn("存在校验失败的数据，不能导入！");
            return;
        }
        $uibMsgbox.confirm('即将导入校验成功的数据，共'
            + $scope.allDataArray.length + '条，确认导入？',
            function (res) {
                if (res == 'yes') {
                    $('#progress-modal').modal('show');
                    $scope.importSuccessItems = 0;
                    $scope.importFailureItems = 0;
                    $scope.importSuccessProgress = 0;
                    $scope.importFailureProgress = 0;
                    inputData(0);
                }
            })
    };

    // 全部导出
    $scope.exportAll = function (step) {
        //步骤2 与 步骤3的全部导出应该可以相同
        outputExcel($scope.allDataArray);
    };

    // 错误导出
    $scope.exportError = function (step) {
        //步骤2
        if(step == 2) {
            outputExcel($scope.validateFailure);
        }
        //TODO 步骤2 与 步骤3的错误导出应该不一样
    };

    // 导出Excel
    function outputExcel(dataList) {
        var param = {
            dataList: dataList
        };
        erp_wfdComboService.outputExcel(param, function (resp) {
            if (!resp.error) {
                //下载
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
                // window.open('../erp/coursemanagerment/downloadExcel?fileName=' + resp.data);
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    function inputData(index) {
        index = index || 0;
        var array = $scope.allDataArray;
        if (index >= array.length) {
            $scope.currentStep = 3;
            $('#progress-modal').modal('hide');
            return;
        }
        $scope.currentImportItems = index + 1;
        erp_wfdComboService.inputData(array[index], function (resp) {
            if (!resp.error) {
                $scope.importSuccessItems++;
                $scope.importSuccessProgress = Number(($scope.importSuccessItems / array.length * 100)).toFixed(2);
            } else {
                $scope.importFailureItems++;
                $scope.importFailureProgress = Number(($scope.importFailureItems / array.length * 100)).toFixed(2)
            }
            inputData(index + 1);
        })
    }

    function uploadFiles() {
        $.ajaxFileUpload({
            url: '/erp/coursemanagerment/package/inputExcel',
            secureuri: false,
            fileElementId: 'fileUploadElementId',
            dataType: 'json',
            method: 'post',
            success: function (resp) {
                if ($scope.waitingModal) {
                    $scope.waitingModal.close();
                }
                if (!resp.error) {
                    $scope.currentStep = 2;
                    $scope.allDataArray.splice(0, $scope.allDataArray.length);
                    $scope.allDataArray = resp.data;
                    validateData();
                } else {
                    $uibMsgbox.warn(resp.message)
                }
            },
            error: function (html, status, e) {
                if ($scope.waitingModal && $scope.waitingModal.close) {
                    $scope.waitingModal.close();
                }
                $uibMsgbox.error('上传失败！' + status)
            }
        })
    }

    // 获取某一页的数据
    $scope.getPageData = function () {
        var currentPage = $scope.paginationConf.currentPage;
        var itemsPerPage = $scope.paginationConf.itemsPerPage;
        var end = itemsPerPage * currentPage;
        var start = end - itemsPerPage;
        var newArray = $scope.filteredData.slice(start, end);
        $scope.currentPageData.splice(0, $scope.currentPageData.length);
        for (var i = 0; i < newArray.length; i++) {
            $scope.currentPageData.push(newArray[i]);
        }
    };

    // 过滤校验数据
    $scope.filterCheckData = function () {
        if ($scope.validateStatus == 0) {
            $scope.filteredData = $scope.allDataArray;
        } else if ($scope.validateStatus == 1) {
            $scope.filteredData = $scope.uncheckedData;
        } else if ($scope.validateStatus == 2) {
            $scope.filteredData = $scope.validateSuccess;
        } else if ($scope.validateStatus == 3) {
            $scope.filteredData = $scope.validateFailure;
        }
        $scope.paginationConf.currentPage = 1;
        $scope.paginationConf.totalItems = $scope.filteredData.length;
        $scope.paginationConf.onChange();
    };
    $scope.goback=function() {
        window.history.go(-1)
    }
    // 数据校验
    function validateData() {
        var modalInstance = $uibMsgbox.waiting('正在校验数据，请稍候...');
        var param = {
            dataList: $scope.allDataArray
        };
        erp_wfdComboService.checkAll(param, function (resp) {
            modalInstance.close();
            if (!resp.error) {
                $scope.allDataArray = resp.data;
                $scope.validateSuccess.splice(0, $scope.validateSuccess.length);
                $scope.validateFailure.splice(0, $scope.validateFailure.length);

                for (var i = 0; i < $scope.allDataArray.length; i++) {
                    var item = $scope.allDataArray[i];
                    if (!item.errorList) {
                        item.validateStatus = 1;
                        $scope.uncheckedData.push(item)
                    } else if (item.errorList.length == 0) {
                        item.validateStatus = 2;
                        $scope.validateSuccess.push(item)
                    } else {
                        item.validateStatus = 3;
                        item.errorMsgObj = {};
                        for (var j = 0; j < item.errorList.length; j++) {
                            item.errorMsgObj[item.errorList[j].field_name] = item.errorList[j].err_msg;
                        }
                        $scope.validateFailure.push(item)
                    }
                }
                $scope.filterCheckData();
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    }
}
