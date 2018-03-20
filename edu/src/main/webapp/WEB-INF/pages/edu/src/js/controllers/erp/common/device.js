/**
 * 公司设备
 */
"use strict";
angular.module('ework-ui').controller('erp_deviceController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox',
    'erp_deviceService',
    'erp_organizationService',
    erp_deviceController
    ]);

function erp_deviceController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_deviceService,
    erp_organizationService
  ) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索的科目名称
    $scope.searchParam = {
        device_code: '',
        account_num: ''
    };
    // 明细列表
    $scope.deviceList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.deviceDetail = {
        id: 0,
        simple_name: '',
        device_code: '',
        account_id: '',
        bu_id:null,
        description: ''
    };

    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，每页显示数据条数的下拉框选项，默认为[10, 20, 30, 40, 50]]
     * @param  {Function} onChange      [必需，分页组件选择某一页后，触发事件，调用onChange方法，主要改变currentPage的值]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        // itemsPerPage: 10,
        // pagesLength: 9,
        // perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.query()
        }
    };

    $scope.paginationBars = [];

    //查询下拉框数据
    erp_deviceService.init({},function(resp) {
        if(!resp.error) {
            //公司账户列表
            $scope.accountList = resp.accountList;
        }
    });

    // 处理【添加】按钮点击事件
    $scope.handleAddDevice = function () {
        $scope.optype = 'add';
        $scope.resetForm();
        $('#erpSystemCommonDevicePanel').modal('show');
    };

    // 处理【删除】按钮点击事件
    $scope.handleDeleteDevice = function (id) {
        $uibMsgbox.confirm('确定删除选中公司设备？', function (result) {
            if(result != 'yes') {
                return;
            }
            $scope.delete(id);
        });
    };
    
    // 处理【修改】按钮点击事件
    $scope.handlePutDevice = function (device) {
        $scope.optype = 'put';
        $scope.deviceDetail = {
            id: device.id,
            simple_name: device.simple_name,
            device_code: device.device_code,
            account_id: device.account_id,
            bu_id:device.bu_id,
            description: device.description
        };
        $("#erpSystemCommonDevicePanel").modal('show');
    };

    // 处理【查询】按钮点击事件
    $scope.handleQueryDevice = function () {
        $scope.query();
    };

    // 处理【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemCommonDevicePanel').modal('hide');
    };

    // 处理【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
        if ($scope.optype == 'add') {
            // 添加的内容
            $scope.add();
        } else if ($scope.optype == 'put') {
            $scope.put();
        }
        $('#erpSystemCommonDevicePanel').modal('hide');
    };
    // 状态变化
    $scope.onStatusChange = function (device) {
        erp_deviceService.changeStatus({
            deviceId: device.id,
            status: device.status
        }, function (resp) {
            if (!resp.error) {
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };
    // 添加
    $scope.add = function () {
        erp_deviceService.add({
            simple_name: $scope.deviceDetail.simple_name,
            device_code: $scope.deviceDetail.device_code,
            account_id: $scope.deviceDetail.account_id,
            bu_id:$scope.deviceDetail.bu_id,
            description: $scope.deviceDetail.description
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success('添加成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 修改
    $scope.put = function () {
        erp_deviceService.update({
            id: $scope.deviceDetail.id,
            simple_name: $scope.deviceDetail.simple_name,
            device_code: $scope.deviceDetail.device_code,
            account_id: $scope.deviceDetail.account_id,
            bu_id:$scope.deviceDetail.bu_id,
            description: $scope.deviceDetail.description
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success('修改成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 删除
    $scope.delete = function (id) {
        erp_deviceService.delete({
            deviceId: id
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success('删除成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询
    $scope.query = function () {
        erp_deviceService.query({
            pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            device_code: $scope.searchParam.device_code,
            account_num: $scope.searchParam.account_num
        }, function (resp) {
            if (!resp.error) {
                $scope.deviceList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    };

    // 重置表单
    $scope.resetForm = function () {
        //$("#erpSystemCommonDevicePanel form")[0].reset()
        $scope.deviceDetail = {};
    };

    $scope.initialize = function() {
        $scope.query();
        erp_organizationService.teamList({}, function(resp) {
            if (!resp.error) {
                $scope.buList = resp.data;
                $scope.buList.unshift({id:null,org_name:'通用'})
            }
        });
    }

    $scope.initialize();
}
