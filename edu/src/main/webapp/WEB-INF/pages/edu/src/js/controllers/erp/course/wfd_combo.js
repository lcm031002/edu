/**
 * 晚辅导套餐
 */
"use strict";
angular.module('ework-ui').controller('erp_wfdComboController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_wfdComboService',
    'erp_gradeService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    erp_wfdComboController
]);

function erp_wfdComboController(
    $rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_wfdComboService,
    erp_gradeService,
    erp_studentBuOrgsService,
    PUBORGSelectedService
) {
    // 表单操作类型，添加： add，修改：put
    $scope.opType = 'add';
    // 查询参数
    $scope.searchParam = {
        p_branch_id: '',
        p_combo_type: '',
        p_combo_name: '',
        p_grade_id: ''
    };

    // 列表信息
    $scope.dataList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.modalData = {};

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
        onChange: function() {
            $scope.query();
        }
    };

    $scope.paginationBars = [];

    $scope.img_src = ''; //图片预览

    // 处理【添加】按钮点击事件
    $scope.handleAdd = function() {
        $scope.opType = 'add';
        $scope.modalData = {};
        $scope.img_src = '';
        $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'templates/block/modal/course-wfd-combo.modal.html',
            controller: 'erp_wfdComboModalController',
            scope: $scope
        }).result.then(function() {
            $scope.add();
        }, function() {});
    };

    // 处理【修改】按钮点击事件
    $scope.handleUpdate = function(rowData) {
        $scope.opType = 'put';
        $scope.modalData = angular.copy(rowData);
        $scope.img_src = $scope.modalData.img_path;
        $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'templates/block/modal/course-wfd-combo.modal.html',
            controller: 'erp_wfdComboModalController',
            scope: $scope
        }).result.then(function() {
            $scope.update(rowData);
        }, function() {});
    };

    // 处理【删除】按钮点击事件
    $scope.handleDelete = function(data) {
        $uibMsgbox.confirm('确定删除选中数据？', function(result) {
            if (result != 'yes') {
                return;
            }
            $scope.remove(data.id);
        });
    };

    // 处理【查询】按钮点击事件
    $scope.handleQuery = function() {
        $scope.query();
    };

    // 添加
    $scope.add = function() {
        erp_wfdComboService.add($scope.modalData, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success('添加成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 修改
    $scope.update = function(rowData) {
        erp_wfdComboService.update($scope.modalData, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success('修改成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 删除
    $scope.remove = function(ids) {
        erp_wfdComboService.remove({
            ids: ids
        }, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success('删除成功！');
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 状态变化
    $scope.onStatusChange = function(data) {
        erp_wfdComboService.changeStatus({
            id: data.id,
            status: data.status
        }, function(resp) {
            if (!resp.error) {
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询
    $scope.query = function() {
        $scope.loadStatues = true;
        var queryParam = angular.copy($scope.searchParam);
        queryParam.pageSize = $scope.paginationConf.itemsPerPage;
        queryParam.currentPage = $scope.paginationConf.currentPage;
        erp_wfdComboService.query(queryParam, function(resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    };

    // 上传的文件改变事件
    $scope.onFileChange = function(files) {
        var file = files[0];
        if (!file.type.match(/jpg|jpeg|bmp|png/i)) {
            $uibMsgbox.error("图片格式无效！");
            return;
        }
        var fileReader = new FileReader();
        fileReader.onload = function() {
            $scope.modalData.img_src = this.result;
            $scope.img_src = this.result;
            $scope.$apply();
        };
        fileReader.readAsDataURL(file);
    };

    // 查询年级
    $scope.queryGrade = function() {
        erp_gradeService.querySelectDatas({}, function(resp) {
            if (!resp.error) {
                $scope.gradeList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询校区
    $scope.queryBuOrgs = function() {
        erp_studentBuOrgsService.query({}, function(resp) {
            if (!resp.error) {
                $scope.branchList = resp.data;
                $scope.querySelectedOrg();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    // 查询登录校区
    $scope.querySelectedOrg = function() {
        PUBORGSelectedService.query({}, function(resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
                    $.each($scope.branchList, function(i, b) {
                        if (b.id == $scope.selectedOrg.id) {
                            $scope.searchParam.p_branch_id = b.id;
                        }
                    });
                } else {
                  $uibMsgbox.warn('您还没选择校区，请选择校区！', function() {
                    setTimeout(function() {
                      $('.btn-group.sel-org.pull-left').addClass('open');
                    }, 300);
                  })
                }
                // 查询列表数据
                $scope.handleQuery();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    //导入
    $scope.handleImport = function() {
        // 跳转到导入页面
        $state.go('ordersMgrCourseWFDComboInput');
    };

    $scope.init = function() {
        // 套餐类型
        $scope.comboTypeList = [{ "id": 1, "name": "月卡" }, { "id": 2, "name": "次卡" }];
        // 查询年级
        $scope.queryGrade();
        // 查询校区
        $scope.queryBuOrgs();
    };
    $scope.init();
}