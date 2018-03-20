/**
 * Created by Liyong.zhu on 2016/9/19.
 */
"use strict";
angular.module('ework-ui').controller('erp_studentTraceInfoAddController', [
    '$rootScope',
    '$state',
    '$scope',
    '$uibMsgbox',
    'FileUploader',
    'erp_studentContactRelationService',
    'erp_dictService',
    'erp_studentTraceInfoService',
    erp_studentTraceInfoAddController
]);

function erp_studentTraceInfoAddController(
    $rootScope,
    $state,
    $scope,
    $uibMsgbox,
    FileUploader,
    erp_studentContactRelationService,
    erp_dictService,
    erp_studentTraceInfoService
) {
    $scope.waitingModal = null
    // TODO 添加异常的捕获
    function getFileType(fileName) {
        if (!fileName || !fileName.split) {
            return null
        }
        if (fileName.split('.').length <= 0) {
            return null;
        }
        return '.' + fileName.split('.').pop().toLowerCase();
    }
    function getFileIconCls(fileType) {
        console.log(fileType)
        if (fileType == '.jpg' || fileType == '.png') {
            return 'fa fa-file-image-o'
        } else if (fileType == '.pdf') {
            return 'fa fa-file-pdf-o'
        } else if (fileType == '.doc' || fileType == '.docx' || fileType == '.wps') {
            return 'fa fa-file-word-o'
        } else {
            return 'fa fa-file-o'
        }
    }
    $scope.uploader = new FileUploader({
        filters: [{
            name: 'fileSize',
            fn: function (item) {
                var fileType = getFileType(item.name)
                if (_.indexOf(['.jpg', '.png', '.pdf', '.doc', '.docx'], fileType) == -1) {
                    return false;
                }
                if (_.indexOf(['jpg', 'png'], fileType) != -1) {
                    if (item.size > 2*1000*1000) {
                        $uibMsgbox.error('您选择的图片【' + item.name + '】大小超过2M，请压缩后再上传！');
                        return false
                    }
                }
                return true
            }
        }],
        onAfterAddingAll: function (items) {
            _.forEach(items, function (item) {
                item.iconCls = getFileIconCls(getFileType(item.file.name))
            })
        },
        onCompleteAll: function () {
            if ($scope.waitingModal && typeof $scope.waitingModal.close == 'function') {
                $scope.waitingModal.close()
            }
            $scope.goBackConfirm();        
        }
    });
    $scope.studentTraceInfo = {
        studentTraceDetailList: [],
        studentTracePlanList: [],
        studentTraceAttachList: []
    };

    $scope.studentId = $("#rootIndex_studentId").val();
    $scope.id = $("#rootIndex_id").val();
    $scope.opType = $("#rootIndex_opType").val();

    $scope.relationList = [];
    $scope.getRelationList = function () {
        erp_studentContactRelationService.query({}, function (resp) {
            $scope.relationList = resp.data;
        });
    }

    $scope.traceTypeList = [];
    $scope.getTraceTypeList = function () {
        erp_dictService.get({
            code: 'traceType'
        }, function (resp) {
            if (!resp.error) {
                $scope.traceTypeList = resp.data;
            }
        });
    }

    $scope.traceDetailTab = 1;
    $scope.changeTraceDetailTab = function (idx) {
        $scope.traceDetailTab = idx;
    }

    $scope.tracePlanTab = 1;
    $scope.changeTracePlanTab = function (idx) {
        $scope.tracePlanTab = idx;
    }
    $scope.deleteUploadedFile = function (item) {
        $uibMsgbox.confirm('确定删除已选择的附件【' + item.fileName +'】？', function (res) {
            if (res == 'yes') {
                erp_studentTraceInfoService.deleteAttachById({
                    id: item.id
                }).$promise.then(function (resp) {
                    if (!resp.error) {
                        $scope.studentTraceInfo.studentTraceAttachList.splice(_.findIndex($scope.studentTraceInfo.studentTraceAttachList, {id: item.id}))
                    } else {
                        $uibMsgbox.error(resp.message)
                    }
                }, function (resp) {
                    $uibMsgbox.error('请求失败！' + resp.message)
                })
            }
        })
    }
    $scope.queryById = function () {
        return erp_studentTraceInfoService.queryById({
            id: $scope.id
        }, function (resp) {
            if (!resp.error) {
                $scope.studentTraceInfo = resp.data;
                if (resp.data.relation) {
                    $scope.studentTraceInfo.relation = Number(resp.data.relation);
                }
                if (resp.data && resp.data.studentTraceDetailList && resp.data.studentTraceDetailList.length > 0) {
                    $.each(resp.data.studentTraceDetailList, function (idx, studentTraceDetail) {
                        if (studentTraceDetail.type && studentTraceDetail.content) {
                            $('#detailContent' + studentTraceDetail.type).val(studentTraceDetail.content);
                        }
                    });
                }
                if (resp.data && resp.data.studentTracePlanList && resp.data.studentTracePlanList.length > 0) {
                    $.each(resp.data.studentTracePlanList, function (idx, studentTracePlan) {
                        if (studentTracePlan.type && studentTracePlan.content) {
                            $('#planContent' + studentTracePlan.type).val(studentTracePlan.content);
                        }
                    });
                }
                if ($scope.studentTraceInfo.studentTraceAttachList) {
                    _.forEach($scope.studentTraceInfo.studentTraceAttachList, function (item) {
                        item.iconCls = getFileIconCls(item.fileType.toLowerCase())
                    })
                }
            } else {
                $uibMsgbox.error(resp.message);
            }
        }).$promise
    }
    function uploadAllFiles (uploader, traceId) {
        if (uploader.queue.length <= 0) {
            return $scope.goBackConfirm();
        }
        _.forEach(uploader.queue, function (item) {
            item.url = '/erp/studentTraceInfo/uploadAttach?traceId=' + traceId
        })
        $scope.waitingModal = $uibMsgbox.waiting('上传附件中，请稍候...');
        uploader.uploadAll();
    }
    function traceInfoValid(traceInfo) {
        if (!traceInfo.traceDate) {
            $uibMsgbox.error('沟通日期必填！');
            return false;
        }
        if (!traceInfo.traceTime) {
            $uibMsgbox.error('沟通时间必填！');
            return false;
        }
        if (!traceInfo.relation) {
            $uibMsgbox.error('沟通对象必填！');
            return false;
        }
        if (!traceInfo.traceType) {
            $uibMsgbox.error('沟通类型必填！');
            return false;
        }
        if (!traceInfo.tracePurpose) {
            $uibMsgbox.error('沟通目的/备注必填！');
            return false;
        }
        return true;
    }
    $scope.saveStudentTrace = function () {
        if (!traceInfoValid($scope.studentTraceInfo)) {
            return;
        }
        $scope.studentTraceInfo.studentTraceDetailList = [];
        $scope.studentTraceInfo.studentTracePlanList = [];
        _.forEach($scope.studentTraceInfo.studentTraceAttachList, function (item) {
            if (item.iconCls) {
                delete item.iconCls
            }
        });

        for (var i = 1; i <= 4; i++) {
            if ($('#detailContent' + i).val()) {
              $scope.studentTraceInfo.studentTraceDetailList.push({ type: i, content: $('#detailContent' + i).val() });
            }
        }

        for (var i = 1; i <= 3; i++) {
            if ($('#planContent' + i).val()) {
                $scope.studentTraceInfo.studentTracePlanList.push({ type: i, content: $('#planContent' + i).val() });
            }
        }

        var waitingModal = $uibMsgbox.waiting('保存中基本信息，请稍候...');
        if ($scope.id) {
            $scope.studentTraceInfo.id = $scope.id;
            erp_studentTraceInfoService.update($scope.studentTraceInfo).$promise.then(function(resp) {
                if (!resp.error) {
                    uploadAllFiles($scope.uploader, $scope.id)
                    waitingModal.close();
                } else {
                    $uibMsgbox.error(resp.message);
                    waitingModal.close();
                }
            }, function (resp) {
                waitingModal.close();
                $uibMsgbox.error('请求失败，请联系管理员！错误码：' + resp.status + '，错误信息：' + resp.statusText);
            })
        } else {
            $scope.studentTraceInfo.studentId = $scope.studentId;
            erp_studentTraceInfoService.add($scope.studentTraceInfo).$promise.then(function (resp) {
                if (!resp.error) {
                    $scope.id = $scope.studentTraceInfo.id = resp.data.id
                    uploadAllFiles($scope.uploader, $scope.id)
                    waitingModal.close();
                } else {
                    $uibMsgbox.error(resp.message);
                    waitingModal.close();
                }
            }, function () {
                waitingModal.close();
                $uibMsgbox.error('请求失败，请联系管理员！错误码：' + resp.status + '，错误信息：' + resp.statusText)
            })
        }
    }

    $scope.goBackConfirm = function () {
        $uibMsgbox.confirm('跟踪信息保存成功！是否返回跟踪列表页面？', function(res) {
            if (res == 'yes') {
                $scope.goBack();
            } else {
                $scope.queryById().then(function () {
                    $scope.uploader.clearQueue();
                })
            }
        })
    }
    $scope.goBack = function () {
        $state.go('studentTraceInfo', {
            path: '/studentMgr/studentTraceInfo'
        });
    }
    $scope.initial = function () {
        $scope.getRelationList();
        $scope.getTraceTypeList();
        if ($scope.id) {
            $scope.queryById();
        }
    }

    $scope.initial();
}
