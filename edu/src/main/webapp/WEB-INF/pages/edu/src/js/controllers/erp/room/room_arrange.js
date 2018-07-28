"use strict";
angular.module('ework-ui').controller('erp_roomArrangeController', [
    '$rootScope',
    '$scope',
    '$log',
    '$state',
    '$uibModal',
    '$uibMsgbox',
    'erp_roomService',
    'erp_studentBuOrgsService',
    'PUBORGSelectedService',
    erp_roomArrangeController
]);

function erp_roomArrangeController($rootScope,
    $scope,
    $log,
    $state,
    $uibModal,
    $uibMsgbox,
    erp_roomService,
    erp_studentBuOrgsService,
    PUBORGSelectedService) {
    
    // 自定义教室类型
    $scope.conditionList=[
        {code: "空闲",name: "空闲"},
        {code: "延课",name: "延课"},
        {code: "占用",name: "占用"},
        {code: "教室",name: "校区教室"}
    ]
    $scope.clearSearch = function(){
        $scope.searchParam.startTime ='';
        $scope.searchParam.endTime ='';
        $scope.searchParam.branchId ='';
        $scope.searchParam.roomName ='';
        $scope.searchParam.courseName ='';
        $scope.searchParam.teacherName ='';
        $scope.searchParams.startDate = moment().startOf('today').format('YYYY-MM-DD');
        $scope.searchParams.endDate = moment().endOf('today').add(6,'day').format('YYYY-MM-DD');        
    }

    // 初始化起止时间
    $scope.searchParams = {
        startDate: moment().startOf('today').format('YYYY-MM-DD'),
        endDate: moment().endOf('today').add(6,'day').format('YYYY-MM-DD'),
        range: 'today',
        p_btn_tag: 'hidden'
    };
    // 初始化搜索条件
    $scope.searchParam = {
        condition:null,
        startTime:null,
        endTime:null,
        branchId:null,
        roomName:null,
        courseName:null,
        teacherName:null
    }
    // 跳转页面
    $scope.viewDetail = function (room) {
        window.location.href = '?roomId=' + room.id + '&startDate='+ room.courseDate + '&endDate=' + room.courseDate + '#/orders/classesRoomMgr/classesRoomArrangerDetail'
    }
    
    /**
     * 查询校区
     */
    function queryBranchOrgs() {
        erp_studentBuOrgsService.query({}, function (resp) {
            if (!resp.error) {
                var data = resp.data;
                if (data && data.length) {
                    $scope.branchList = resp.data;
                }
                //查询选中的组织，并为校区设置默认值
                // querySelectedOrg();
            }
        })
    }
    

    // 查询方法
    $scope.arrangeList = [];
    $scope.totalList = [];
    $scope.query = function () {
        var params = _.cloneDeep($scope.searchParam);
        params.startDate = $scope.searchParams.startDate;
        params.endDate = $scope.searchParams.endDate; 
        erp_roomService.listroomArrange(params, function (resp) {
            // console.log(resp)
            if (!resp.error) {
                $scope.paginationConf.totalItems = resp.data.length; //设置总条数
                $scope.totalList = resp.data;
                $scope.arrangeList = resp.data.slice(0,$scope.paginationConf.itemsPerPage);
            } else {
                // console.log(resp.message)
                $uibMsgbox.error(resp.message)
            }
        });
    };

    $scope.handleExportExcel = function () {
        var params = _.cloneDeep($scope.searchParam);
        params.startDate = $scope.searchParams.startDate;
        params.endDate = $scope.searchParams.endDate;

        var _uibModalInstance = $uibMsgbox.waiting('正在为您导出数据，请稍候...');

        erp_roomService.outputExcel(params, function (resp) {
            _uibModalInstance.close();
            if (!resp.error) {
                //下载excel
                window.location.href = '../erp/coursemanagerment/downloadExcel?fileName=' + resp.data;
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }


    $scope.init = function () {
        $scope.searchParam = { // 搜索条件
            branchId: null,
            condition: '空闲',
        };
        // $scope.conditionList = []; // 课程列表
        $scope.branchList = [];//校区列表

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
            itemsPerPage: 20,
            onChange: function () {
                var currentPage = $scope.paginationConf.currentPage;
                var itemsPerPage = $scope.paginationConf.itemsPerPage;
                $scope.arrangeList = $scope.totalList.slice(itemsPerPage*(currentPage-1),itemsPerPage*currentPage);
            }
        };
        queryBranchOrgs();
        $scope.query();
    };
    // 服务用   erp_roomService.listroomArrange
    $scope.init();
}