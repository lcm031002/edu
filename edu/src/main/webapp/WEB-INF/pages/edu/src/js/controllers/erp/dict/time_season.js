/**
 * Created by Liyong.zhu on 2017/2/14.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_timeSeasonController', [
        '$scope',
        '$log',
        '$uibMsgbox',
        'erp_timeSeasonService',
        erp_timeSeasonController]);

function erp_timeSeasonController(
    $scope,
    $log,
    $uibMsgbox,
    erp_timeSeasonService
    ) {
	// 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    
    // 搜索字段
    $scope.searchParam = {
    	course_season_name: ''
    };
    
    // 课程季列表
	$scope.timeSeasonList = [];

	//上一课程季列表
	$scope.lastTimeSeasonList = [];

    // 业务类型列表
	$scope.businessTypeList = [{"key" : 1, "value" : "班级课"},
	                   		   {"key" : 2, "value" : "一对一"},
	                   		   {"key" : 3, "value" :"晚辅导"}];
    // 产品线列表                               
	$scope.productLineList = [];

    // 季节列表
	$scope.seasonList = [{"key" : 1, "value" : "春季"},
	                     {"key" : 2, "value" : "夏季"},
	                     {"key" : 3, "value" : "秋季"},
	                     {"key" : 4, "value" : "冬季"}];

    // 与表单绑定的数据，用于添加和修改
    $scope.timeSeason = {
        business_type: 0,
        business_type_name: "",
        city_id: 0,
        city_name: "",
        course_season_name: "",
        create_time: null,
        create_user: 0,
        create_user_name: "",
        description: "",
        end_date: "",
        id: 0,
        last_course_season_name: "",
        last_season_id: 0,
        product_line: 0,
        product_line_name: "",
        season: 0,
        season_name: "",
        start_date: "",
        status: 0,
        update_time: null,
        update_user: 0,
        update_user_name: ""
    }
    
    /**
     * 分页配置
     * @param  {Number} currentPage     [当前页面，初始化时默认为1]
     * @param  {Number} totalItems      [数据总条数，每次查询时赋值]
     * @param  {Number} itemsPerPage    [每页显示条数]
     * @param  {Number} pagesLength     [可选，分页栏长度,默认为9]
     * @param  {Array}  perPageOptions  [可选，默认]
     * @param  {Function} perPageOptions [description]
     */
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        onChange: function() {
            $scope.handleQueryTimeSeason();
        }
    };

    $scope.paginationBars = [];

    // 处理【添加课程季】按钮点击事件
    $scope.handleAddTimeSeason = function () {
        $scope.optype = 'add';
        $scope.resetForm();
        $scope.timeSeason.season = null;
        $scope.timeSeason.last_season_id = null;
        $scope.timeSeason.product_line = null;
        $scope.timeSeason.business_type = null;
        $scope.querySelectDatas();
        $('#erpSystemDictTimeSeasonPanel').modal('show');
    }
    
    // 处理【修改课程季】按钮点击事件
    $scope.handlePutTimeSeason = function (rowData) {
        $scope.optype = 'put';
        $scope.timeSeason = rowData;
        $scope.querySelectDatas();
        $("#erpSystemDictTimeSeasonPanel").modal('show');
    }
    
    // 处理【删除课程季】按钮点击事件
    $scope.handleDeleteTimeSeason = function (id) {
        if (window.confirm('确定删除选中课程季？')) {
            $scope.remove(id);
        }
    }

    // 处理【查询课程季】按钮点击事件
    $scope.handleQueryTimeSeason = function () {
        $scope.query();
    }

    // 处理课程季表单【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemDictTimeSeasonPanel').modal('hide');
    }

    // 处理课程季表单【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
        if ($scope.optype == 'add') {
    		$scope.add();
    	} else {
    		$scope.put();
    	}
    }

    // 查询课程季
    $scope.query = function () {
    	erp_timeSeasonService.query({
    		pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            p_course_season_name: $scope.searchParam.course_season_name,
        },
        function(resp){
            if(!resp.error) {
           	 	$scope.timeSeasonList = resp.data;
           	 	$scope.paginationConf.totalItems = resp.total || 0;
            } else {
            	$uibMsgbox.error(resp.message);
            }
        });
    }

    // 添加课程季
    $scope.add = function () {
        var _modalInstance = $uibMsgbox.waiting('添加中，请稍后');
    	erp_timeSeasonService.post($scope.timeSeason, function (resp) {
            _modalInstance.close();
    		if(!resp.error) {
    			$uibMsgbox.success("添加成功");
    			$scope.query();
    			$('#erpSystemDictTimeSeasonPanel').modal('hide');
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    // 修改课程季
    $scope.put = function () {
    	erp_timeSeasonService.put($scope.timeSeason, function (resp) {
    		if(!resp.error) {
    			$uibMsgbox.success("修改成功");
    			$scope.query();
    			$('#erpSystemDictTimeSeasonPanel').modal('hide');
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    // 删除课程季
    $scope.remove = function (id) {
    	erp_timeSeasonService.remove({"id" : id},function (resp) {
    		if(!resp.error) {
    			$uibMsgbox.success("删除成功");
    			$scope.query();
    		} else {
    			$uibMsgbox.error(resp.message);
    		}
        });
    }

    // 重置课程季表单
    $scope.resetForm = function () {
        $("#erpSystemDictTimeSeasonPanel form")[0].reset();
    }

    /**
     * 查询下拉框数据
     */
    $scope.querySelectDatas = function () {
    	erp_timeSeasonService.querySelectDatas({
    	},function(resp) {
            if(!resp.error) {
                $scope.lastTimeSeasonList = resp.timeSeasonList;
                $scope.productLineList = resp.productLineList;
            }
        });
    };

    // 状态变化
    $scope.onStatusChange = function (data) {
        erp_timeSeasonService.changeStatus({
            id: data.id,
            status: data.status
        }, function (resp) {
            if (!resp.error) {
                $scope.query();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };
    
    $scope.query();
}