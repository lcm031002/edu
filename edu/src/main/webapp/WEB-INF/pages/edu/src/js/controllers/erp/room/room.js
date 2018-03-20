/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_roomController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_roomService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    erp_roomController
]);

function erp_roomController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_roomService,
    erp_studentBuOrgsService,
    PUBORGSelectedService) {


    /**
     * 查询校区
     */
    function queryBranchOrgs() {
        erp_studentBuOrgsService.query({}, function(resp) {
            if (!resp.error) {
                var data = resp.data;
                if (data && data.length) {
                    $scope.branchList = resp.data;
                }
                //查询选中的组织，并为校区设置默认值
                querySelectedOrg();
            }
        })
    }

    /**
     * 获取组织结构中选中的组织
     */
    function querySelectedOrg() {
        PUBORGSelectedService.query({}, function(resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
                    $.each($scope.branchList, function(i, b) {
                        if (b.id == $scope.selectedOrg.id) {
                            $scope.searchParam.p_branch_id = b.id;
                            $scope.selectedBranch = b.id;
                        }
                    });
                }
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.init = function() {

        $scope.searchParam = { // 搜索条件
            p_branch_id: null,
            p_queryKey: null
        };
        $scope.roomList = []; // 课程列表
        $scope.branchList = []; //校区列表

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
            itemsPerPage: 10,
            onChange: function() {
                $scope.query();
            }
        };
        queryBranchOrgs();
    };
    // 查询方法
    $scope.query = function() {
        $scope.loadStatues = true;
        erp_roomService.get({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_branch_id: $scope.searchParam.p_branch_id,
            p_bu_id: $scope.searchParam.p_bu_id,
            p_queryKey: $scope.searchParam.p_queryKey
        }, function(resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.roomList = resp.data;
            } else {
                alert(resp.message);
            }
        });
    };

    // 删除
    $scope.delRoom = function(room) {
        $uibMsgbox.confirm('确定删除？', function(result) {
            if (result != 'yes') {
                return;
            }
            erp_roomService.del({
                id: room.id
            }, function(resp) {
                if (!resp.error) {
                    $scope.query();
                    $uibMsgbox.alert("删除成功");
                } else {
                    alert(resp.message);
                }
            });
        });
    };

    // 添加
    $scope.addRoom = function() {
        var roomDefault = {};
        if ($scope.selectedBranch) {
            roomDefault.branch_id = $scope.selectedBranch;
        } else {
            roomDefault.branch_id = $scope.searchParam.p_branch_id
        }
        roomDefault.bu_id = $scope.searchParam.p_bu_id;

        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'room_modal.html',
            controller: 'erp_roomModalController',
            resolve: {
                params: function() {
                    return {
                        optype: "添加",
                        roomDetail: roomDefault,
                        branchList: $scope.branchList
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {
            $scope.query();
            $uibMsgbox.alert("操作成功");
        }, function(reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };
    // 修改
    $scope.putRoom = function(room) {
        var roomDetail = {};
        angular.copy(room, roomDetail);
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'room_modal.html',
            controller: 'erp_roomModalController',
            resolve: {
                params: function() {
                    return {
                        optype: "修改",
                        roomDetail: roomDetail,
                        branchList: $scope.branchList
                    }
                }
            }
        });
        modalInstance.result.then(function(result) {
            $scope.query();
            $uibMsgbox.alert("操作成功");
        }, function(reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };
    $scope.init();
}
angular.module('ework-ui')
    .controller('erp_roomModalController', [
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        'erp_roomService',
        'params',
        erp_roomModalController
    ])

function erp_roomModalController($scope,
    $uibModalInstance, $uibMsgbox, erp_roomService, params) {
    $scope.optype = params.optype;
    $scope.roomDetail = params.roomDetail;
    $scope.branchList = params.branchList;
    if ($scope.optype == "添加" && $scope.branchList && $scope.branchList.length) {
        if (!$scope.roomDetail.branch_id) {
            $scope.roomDetail.branch_id = $scope.branchList[0].id;
        }
    }
    /**
     * 模态框取消
     */
    $scope.handleModalCancel = function() {
            $uibModalInstance.dismiss('cancel')
        }
        /**
         * 模态框确认
         */
    $scope.handleModalConfirm = function() {
        if ($scope.optype == "修改") {
            //更新数据
            erp_roomService.put($scope.roomDetail, function(resp) {
                if (!resp.error) {
                    $uibModalInstance.close("success")
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        } else {
            //添加数据
            erp_roomService.post($scope.roomDetail, function(resp) {
                if (!resp.error) {
                    $uibModalInstance.close("success")
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    }
}