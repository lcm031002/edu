/**
 * @author baiqb@klxuexi.org 2017/03/06
 */
"use strict";
angular.module('ework-ui').controller('erp_subjectController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox',
    'erp_subjectService',
    erp_subjectController
    ]);

function erp_subjectController(
    $rootScope,
    $scope,
    $log,
    $uibMsgbox,
    erp_subjectService
  ) {
    // 表单操作类型，添加： add，修改：put
    $scope.optype = 'add'; //
    // 搜索的科目名称
    $scope.searchParam = {
        subjectName: ''
    };
    // 科目列表
    $scope.subjectList = [];

    // 与表单绑定的数据，用于添加和修改
    $scope.subjectDetail = {
        id: 0,
        name: '',
        encoding: '',
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
    
    // 处理【添加科目】按钮点击事件
    $scope.handleAddSubject = function () {
        $scope.optype = 'add';
        $scope.resetForm();
        $('#erpSystemDictSubjectPanel').modal('show');
    };

    // 处理【删除科目】按钮点击事件
    $scope.handleDeleteSubject = function (id) {
        $uibMsgbox.confirm('确定删除选中科目？', function (result) {
            if(result != 'yes') {
                return;
            }
            $scope.delete(id);
        });
    };
    
    // 处理【修改科目】按钮点击事件
    $scope.handlePutSubject = function (subject) {
        $scope.optype = 'put';
        $scope.subjectDetail = {
            id: subject.id,
            name: subject.name,
            encoding: subject.encoding,
            description: subject.description
        };
        $("#erpSystemDictSubjectPanel").modal('show');
    };

    // 处理【查询科目】按钮点击事件
    $scope.handleQuerySubject = function () {
        $scope.query();
    };

    // 处理科目表单【取消】按钮点击事件
    $scope.handleModalCancel = function () {
        $('#erpSystemDictSubjectPanel').modal('hide');
    };

    // 处理科目表单【确认】按钮点击事件
    $scope.handleModalConfirm = function () {
        if ($scope.optype == 'add') {
            // 添加的内容从 $scope.subjectDetail中获取
            $scope.add();
        } else if ($scope.optype == 'put') {
            $scope.put();
        }
        $('#erpSystemDictSubjectPanel').modal('hide');
    };

    // 添加
    $scope.add = function () {
        erp_subjectService.add({
            name: $scope.subjectDetail.name,
            encoding: $scope.subjectDetail.encoding,
            description: $scope.subjectDetail.description
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
        erp_subjectService.update({
            id: $scope.subjectDetail.id,
            name: $scope.subjectDetail.name,
            encoding: $scope.subjectDetail.encoding,
            description: $scope.subjectDetail.description
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
    $scope.delete = function (subjectId) {
        erp_subjectService.delete({
            subjectId: subjectId
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
        erp_subjectService.query({
            pageSize: $scope.paginationConf.itemsPerPage,
            currentPage: $scope.paginationConf.currentPage,
            subjectName: $scope.searchParam.subjectName
        }, function (resp) {
            if (!resp.error) {
                $scope.subjectList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message)
            }
        })
    };

    // 重置表单
    $scope.resetForm = function () {
        //$("#erpSystemDictSubjectPanel form")[0].reset()
        $scope.subjectDetail = {};
    };

    $scope.query();
}
