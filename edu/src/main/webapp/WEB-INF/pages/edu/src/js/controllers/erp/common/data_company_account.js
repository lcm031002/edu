/**
 * 公司设备
 */
"use strict";
angular.module('ework-ui').controller('erp_companyAccountController', [
    '$rootScope',
    '$scope',
    '$log',
    'erp_companyAccountService',
    erp_companyAccountController
    ]);

function erp_companyAccountController(
    $rootScope,
    $scope,
    $log,
    erp_companyAccountService
  ) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索的科目名称
    $scope.searchParam = {
        device_code: '',
        account_num: ''
    };
    // 明细列表
    $scope.companyAccountList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.companyAccountDetail = {
        id: 0,
        account_name: '',
        account_num: '',
        description: '',
        status:1
    };

    $scope.statusList = [
        {key: 1, value: '有效'},
        {key: 2, value: '无效'}
    ];

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
        onChange: function(){
            $scope.query()
        }
    };

    $scope.paginationBars = [];

    // 处理【添加】按钮点击事件
    $scope.handleAddAccount = function () {
        $scope.optype = 'add';
        $scope.resetForm();
        $('#erpSystemDictAccountPanel').modal('show');
    };

    // 处理【删除】按钮点击事件
    $scope.handleDeleteAccount = function (id) {
        var r = window.confirm('确定删除选中公司账户？')
        if (r == true) {
            $scope.delete(id)
        }
    };
    
    // 处理【修改】按钮点击事件
    $scope.handlePutAccount = function (account) {
        $scope.optype = 'put';
        $scope.companyAccountDetail = {
            id: account.id,
            account_name: account.account_name,
            account_num: account.account_num,
            description: account.description,
            status : account.status
        };
        $("#erpSystemDictAccountPanel").modal('show');
    };

    // 处理【查询】按钮点击事件
    $scope.handleQueryAccount = function () {
        $scope.query();
    };

    // 处理【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemDictAccountPanel').modal('hide');
    };

    // 处理【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
        if ($scope.optype == 'add') {
            // 添加的内容
            $scope.add();
        } else if ($scope.optype == 'put') {
            $scope.put();
        }
        $('#erpSystemDictAccountPanel').modal('hide');
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
                alert(resp.message)
            }
        })
    }
    // 添加
    $scope.add = function () {
    	erp_companyAccountService.add({
    		 account_name: $scope.companyAccountDetail.account_name,
             account_num: $scope.companyAccountDetail.account_num,
             description: $scope.companyAccountDetail.description,
             status: $scope.companyAccountDetail.status
        }, function (resp) {
            if (!resp.error) {
                window.alert('添加成功！');
                $scope.query();
            } else {
                window.alert(resp.message);
            }
        })
    };

    // 修改
    $scope.put = function () {
    	erp_companyAccountService.update({
            id: $scope.companyAccountDetail.id,
            account_name: $scope.companyAccountDetail.account_name,
            account_num: $scope.companyAccountDetail.account_num,
            description: $scope.companyAccountDetail.description,
            status: $scope.companyAccountDetail.status
        }, function (resp) {
            if (!resp.error) {
                window.alert('修改成功！');
                $scope.query();
            } else {
                window.alert(resp.message);
            }
        })
    };

    // 删除
    $scope.delete = function (id) {
    	erp_companyAccountService.delete({
    		id: id
        }, function (resp) {
            if (!resp.error) {
                window.alert('删除成功！');
                $scope.query();
            } else {
                window.alert(resp.message);
            }
        })
    };

    // 查询
    $scope.query = function () {
    	erp_companyAccountService.query({
            pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            p_account_name: $scope.searchParam.account_name,
            p_account_num: $scope.searchParam.account_num
        }, function (resp) {
            if (!resp.error) {
                $scope.companyAccountList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                alert(resp.message)
            }
        })
    };

    // 重置表单
    $scope.resetForm = function () {
        //$("#erpSystemCommonDevicePanel form")[0].reset()
        $scope.companyAccountDetail = {
            status : 1
        };
    };

    $scope.query();
}
