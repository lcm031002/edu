   angular.module('ework-ui').controller('erp_delayRecordDetailController', [
        '$rootScope',
        '$scope',
        '$log',
        '$uibMsgbox', // 消息提示框服务，其他服务按需引入
        '$state',
        '$uibModal',
        'erp_delayCourseService',
        erp_delayRecordDetailController
    ]);

    function erp_delayRecordDetailController(
        $rootScope,
        $scope,
        $log,
        $uibMsgbox,
        $state,
        $uibModal,
        erp_delayCourseService
    ) {

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
            onChange: function(){
                $scope.listDelayRecordCourseDetail();
            }
        };

        $scope.delayRecordCourseDetailList = {};
    $scope.listDelayRecordCourseDetail = function() {
        erp_delayCourseService.listDelayRecordCourseDetail({
            pageSize: $scope.paginationConf.itemsPerPage, // 每页显示条数
            currentPage: $scope.paginationConf.currentPage,// 要获取的第几页的数据
            delayCourseRecordId : $("#rootIndex_recordId").val()
        },function(resp){
            if(!resp.error) {
                $scope.delayRecordCourseDetailList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0; //设置总条数
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.listDelayRecordCourseDetail();

    $scope.listDelayCourseSchedulingDetail = function(){

    }

    //查询课次变动
    $scope.listDelayCourseSchedulingChangeInfo = function (delayCourseId) {
        $scope.courseSchedullingChangeInfoList = {};
        erp_delayCourseService.listDelayCourseSchedulingChangeInfo({
            delayCourseId:delayCourseId
        }, function (resp) {
            if (!resp.error) {
                $scope.courseSchedullingChangeInfoList =resp.data;
                $('#delayRecordCourseSchedullingChangeInfo').modal('show');
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    };
}