/**
 * 公司设备
 */
"use strict";
angular.module('ework-ui').controller('erp_CoopOrgController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox',
    'erp_CoopOrgService',
    erp_CoopOrgController
    ]);

function erp_CoopOrgController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_CoopOrgService
  ) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索的科目名称
    $scope.searchParam = {
        coopOrgName: ''
    };
    // 明细列表
    $scope.coopOrgList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.coopOrg = {
        id: 0,
        coopOrgName: '',
        percentage:0,
        description: ''
    };

    // 与表单绑定的数据，用于添加和修改
    $scope.coopOrgRel = {
        id: 0,
        coopOrgId: 0,
        start_date: '',
        end_date: ''
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
        onChange: function(){
            $scope.query()
        }
    };

    $scope.paginationBars = [];

    // 处理【添加】按钮点击事件
    $scope.handleAddCoopOrg = function () {
        $scope.optype = 'add';
        $scope.resetForm();
        $('#erpSystemCommonCoopOrgPanel').modal('show');
    };

    // 处理【删除】按钮点击事件
    $scope.handleDeleteCoopOrg = function (id) {
        $uibMsgbox.confirm('确定删除选中合作公司？', function (result) {
            if(result != 'yes') {
                return;
            }
            $scope.delete(id);
        });
    };
    
    // 处理【修改】按钮点击事件
    $scope.handlePutCoopOrg = function (coopOrg) {
        $scope.optype = 'put';
        $scope.coopOrg = {
            id: coopOrg.id,
            coopOrgName: coopOrg.coopOrgName,
            percentage: coopOrg.percentage,
            description: coopOrg.description
        };
        $("#erpSystemCommonCoopOrgPanel").modal('show');
    };

    // 处理【抽成】按钮点击事件
    $scope.handlePercentageCoopOrg = function (coopOrg) {
        $scope.coopOrgRel = {
            coopOrgId: coopOrg.id
        };

        $scope.queryPercentage(coopOrg.id);
        $("#erpSystemCommonPercentageCoopOrgPanel").modal('show');
    };

    // 处理【查询】按钮点击事件
    $scope.handleQueryCoopOrg = function () {
        $scope.query();
    };

    // 处理【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemCommonCoopOrgPanel').modal('hide');
    };

    // 处理【取消】按钮点击事件
    $scope.handleModalPercentageCancel = function () {
        $('#erpSystemCommonPercentageCoopOrgPanel').modal('hide');
    };

    // 处理【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
        if ($scope.optype == 'add') {
            // 添加的内容
            $scope.add();
        } else if ($scope.optype == 'put') {
            $scope.put();
        }
        $('#erpSystemCommonCoopOrgPanel').modal('hide');
    };

    // 处理【确认】按钮点击事件
    $scope.handleModalPercentageConfirm = function () {

       $scope.addPercentage();

        $('#erpSystemCommonPercentageCoopOrgPanel').modal('hide');
    };

    // 状态变化
    $scope.onStatusChange = function (coopOrg) {
        erp_CoopOrgService.changeStatus({
            ids: coopOrg.id,
            status: coopOrg.status
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
        erp_CoopOrgService.add($scope.coopOrg, function (resp) {
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
        erp_CoopOrgService.update($scope.coopOrg, function (resp) {
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
        erp_CoopOrgService.delete({
            id: id
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success('删除成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 添加
    $scope.addPercentage = function () {
        erp_CoopOrgService.addPercentage($scope.coopOrgRel, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success('添加成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询
    $scope.query = function () {
        erp_CoopOrgService.queryPage({
            pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            coopOrgName: $scope.searchParam.coopOrgName
        }, function (resp) {
            if (!resp.error) {
                $scope.coopOrgList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message)
            }
        });
    };

    // 查询抽成
    $scope.queryPercentage = function (id) {
        erp_CoopOrgService.queryPercentage({
            coopOrgId: id
        }, function (resp) {
            if (!resp.error) {
                $scope.coopOrgRelList = resp.data;
            } else {
                $uibMsgbox.error(resp.message)
            }
        });
    };

    // 重置表单
    $scope.resetForm = function () {
        $scope.coopOrg = {};
    };

    $scope.initialize = function() {
        $scope.query();
    }

    $scope.initialize();
}
