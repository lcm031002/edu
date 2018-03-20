/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_cameraController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_cameraService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    'erp_roomService',
    erp_cameraController
]);

function erp_cameraController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_cameraService,
    erp_studentBuOrgsService,
    PUBORGSelectedService,
    erp_roomService) {


    /**
     * 查询校区
     */
    function queryBranchOrgs() {
        erp_studentBuOrgsService.query({}, function(resp) {
            if (!resp.error) {
                $scope.branchList = resp.data;
                querySelectedOrg();
            }
        })
    }

    $scope.roomList = [];

    /**
     * 查询教室
     */
    function queryRoom(branch_id) {
        erp_roomService.queryRoom({
            p_branch_id: branch_id
        }, function(res) {
            if (!res.error) {
                $scope.roomList = res.data;
            } else {
                $uibMsgbox.error(res.message);
            }
        });
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
                            $scope.searchParam.selectedBranch = b;
                        }
                    });
                    $scope.branchList = [$scope.searchParam.selectedBranch];
                } else {
                    $scope.searchParam.selectedBranch = $scope.branchList[0];
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
        $scope.cameraList = []; // 课程列表
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
        erp_cameraService.pageCamera({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_branch_id: $scope.searchParam.selectedBranch.branch_id,
            p_bu_id: $scope.searchParam.p_bu_id,
            p_queryKey: $scope.searchParam.p_queryKey
        }, function(resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.cameraList = resp.data;
            } else {
                alert(resp.message);
            }
        });
    };
    //
    // // 删除
    // $scope.delRoom = function (room) {
    //     $uibMsgbox.confirm('确定删除？', function (result) {
    //         if(result != 'yes') {
    //             return;
    //         }
    //         erp_roomService.del({
    //             id: room.id
    //         }, function (resp) {
    //             if (!resp.error) {
    //                 $scope.query();
    //                 $uibMsgbox.alert("删除成功");
    //             } else {
    //                 alert(resp.message);
    //             }
    //         });
    //     });
    // };
    // 添加
    $scope.addCamera = function() {
        var CameraDefault = {};
        if ($scope.selectedBranch) {
            CameraDefault.branch_id = $scope.selectedBranch;
        } else {
            CameraDefault.branch_id = $scope.searchParam.selectedBranch.id
        }
        CameraDefault.bu_id = $scope.searchParam.p_bu_id;
        queryRoom(CameraDefault.branch_id);
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'camera_modal.html',
            controller: 'erp_cameraModalController',
            scope: $scope,
            resolve: {
                params: function() {
                    return {
                        optype: "添加",
                        cameraDetail: CameraDefault,
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
    $scope.putCamera = function(camera) {
        var cameraDetail = {};
        angular.copy(camera, cameraDetail);
        queryRoom(cameraDetail.branch_id);
        var modalInstance = $uibModal.open({
            size: 'lg',
            backdrop: 'static',
            templateUrl: 'camera_modal.html',
            controller: 'erp_cameraModalController',
            scope: $scope,
            resolve: {
                params: function() {
                    return {
                        optype: "修改",
                        cameraDetail: cameraDetail,
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
    .controller('erp_cameraModalController', [
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        'erp_cameraService',
        'params',
        erp_cameraModalController
    ])

function erp_cameraModalController($scope,
    $uibModalInstance, $uibMsgbox, erp_cameraService, params) {
    $scope.optype = params.optype;
    $scope.cameraDetail = params.cameraDetail;
    $scope.branchList = params.branchList;
    if ($scope.optype == "添加" && $scope.branchList && $scope.branchList.length) {
        if (!$scope.cameraDetail.branch_id) {
            $scope.cameraDetail.branch_id = $scope.branchList[0].id;
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
        var _waitingModal = $uibMsgbox.waiting('操作中，请稍候...');
        if ($scope.optype == "修改") {
            //更新数据
            erp_cameraService.put($scope.cameraDetail, function(resp) {
                _waitingModal.close();
                if (!resp.error) {
                    $uibModalInstance.close("success")
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        } else {
            //添加数据
            erp_cameraService.post($scope.cameraDetail, function(resp) {
                _waitingModal.close();
                if (!resp.error) {
                    $uibModalInstance.close("success")
                } else {
                    $uibMsgbox.error(resp.message);
                }
            });
        }
    }
}