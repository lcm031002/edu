/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_schoolController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox', // 消息提示框服务，其他服务按需引入
    'erp_schoolService',
    erp_schoolController
]);

function erp_schoolController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_schoolService
) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索的学校名称
    $scope.searchParam = {
        schoolName: ''
    };
    // 学校列表
    $scope.schoolList = [];
    // 学校类型列表
    $scope.schoolTypeList = [];
    // 省份列表
    $scope.regionList = [];
    // 市区列表
    $scope.regionCityList = [];
    // 区域列表
    $scope.regionAreaList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.schoolDetail = {
        id: '',
        school_name: '',
        short_school_name: '',
        school_type: '',
        linkman: '',
        phone: '',
        description: '',
        province_id: 0,
        city_id: 0,
        area_id: 0,
        org_id: 0,
        address: ''
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
        itemsPerPage: 10,
        onChange: function() {
            $scope.query();
        }
    };

    // 处理【添加学校】按钮点击事件
    $scope.handleAddSchool = function() {
        $scope.optype = 'add';
        $scope.resetForm();
        $scope.querySelectDatas('');
        $scope.queryRegionProvince('');
        $('#erpSystemDictSchoolPanel').modal('show');
    };

    // 处理【删除学校】按钮点击事件
    $scope.handleDeleteSchool = function(id) {
        var r = window.confirm('确定删除选中学校？');
        if (r == true) {
            $scope.del(id);
        }
    };

    // 处理【修改学校】按钮点击事件
    $scope.handlePutSchool = function(school) {
        $scope.optype = 'put';
        $scope.schoolDetail = school;
        $scope.querySelectDatas('');
        $scope.queryRegionProvince('');
        if (school.province_id) {
            $scope.queryRegionCity(school.province_id);
        }
        if (school.city_id) {
            $scope.queryRegionArea(school.city_id);
        }
        $("#erpSystemDictSchoolPanel").modal('show');
    };

    // 处理【查询学校】按钮点击事件
    $scope.handleQuerySchool = function() {
        $scope.query();
    };

    // 处理学校表单【取消】按钮点击事件
    $scope.handleModalCancel = function() {
        $('#erpSystemDictSchoolPanel').modal('hide');
    };

    // 处理学校表单【确认】按钮点击事件
    $scope.handleModalConfirm = function() {
        if ($scope.optype == 'add') {
            $scope.add();
        } else {
            $scope.put();
        }
        $scope.query();
        $('#erpSystemDictSchoolPanel').modal('hide');
    };

    // 查询
    $scope.query = function() {
        $scope.loadStatues = true;
        erp_schoolService.query({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage, // 要获取的第几页的数据
            p_school_name: $scope.searchParam.schoolName
        }, function(resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
                $scope.schoolList = resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
    $scope.converToInt = function(value) {
        return parseInt(value, 10);
    };
    // 添加
    $scope.add = function() {
        erp_schoolService.addSchool($scope.schoolDetail, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success(resp.message);
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 修改
    $scope.put = function() {
        erp_schoolService.updateSchool($scope.schoolDetail, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success(resp.message);
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    // 删除
    $scope.del = function(id) {
        erp_schoolService.delSchool({ school_ids: id }, function(resp) {
            if (!resp.error) {
                $uibMsgbox.success(resp.message);
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };

    //查询下拉列表数据，目前主要是学校类型下拉数据
    $scope.querySelectDatas = function(id) {
        erp_schoolService.querySelectDatas({
            id: id
        }, function(resp) {
            if (!resp.error) {
                $scope.schoolTypeList = resp.schoolTypeList;
            }
        });
    };
    $scope.onProvinceChange = function(id) {
        id = parseInt(id);
        if (isNaN(id) || id < 1) {
            $scope.regionCityList = [];
            $scope.regionAreaList = [];
        } else {
            $scope.queryRegionCity(id);
        }
    }
    $scope.onCityChange = function(id) {
        id = parseInt(id);
        if (isNaN(id) || id < 1) {
            $scope.regionAreaList = [];
        } else {
            $scope.queryRegionArea(id)
        }
    }
    $scope.queryRegionProvince = function(id) {
        erp_schoolService.queryRegionDatas({
            id: id
        }, function(resp) {
            if (!resp.error) {
                $scope.regionList = resp.data;
            } else {
                console.log(resp.message);
                $scope.regionList = resp.data;
            }
        });
    }

    $scope.queryRegionCity = function(id) {
        erp_schoolService.queryRegionDatas({
            id: id
        }, function(resp) {
            if (!resp.error) {
                $scope.regionCityList = resp.data;
            } else {
                console.log(resp.message);
                $scope.regionCityList = [];
            }
        })
    }

    $scope.queryRegionArea = function(id) {
        erp_schoolService.queryRegionDatas({
            id: id
        }, function(resp) {
            if (!resp.error) {
                $scope.regionAreaList = resp.data;
            } else {
                console.log(resp.message);
                $scope.regionAreaList = [];
            }
        })
    }


    /*
     * 修改状态
     * @param flag true-生效 false-无效
     */
    $scope.changeStatus = function(id, flag) {
        erp_schoolService.changeStatus({ "id": id, "status": flag }, function(resp) {
            if (!resp.error) {} else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.onStatusChange = function(shcool) {
        $scope.changeStatus(shcool.id, shcool.status);
    }

    // 重置表单
    $scope.resetForm = function() {
        $("#erpSystemDictSchoolPanel form")[0].reset();
    };

    $scope.query();
}