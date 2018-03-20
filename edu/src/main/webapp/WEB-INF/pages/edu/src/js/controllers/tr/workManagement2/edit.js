(function() {
    "use strict";

    angular.module('ework-ui')
        .controller('frWorkMgtWorkEditCtrl2', frWorkMgtWorkEditCtrl)
        .service('frWorkMgtWorkEdit', frWorkMgtWorkEditService)

    frWorkMgtWorkEditCtrl.$inject = ['$scope', '$log', '$uibModal', '$state', '$stateParams', 'frWorkMgtWorkEdit', '$uibMsgbox', '$rootScope'];
    function frWorkMgtWorkEditCtrl($scope, $log, $uibModal, $state, $stateParams, frWorkMgtWorkEdit, $uibMsgbox, $rootScope) {
        window.mgtWorkEdit = $scope.mgtWorkEdit;
        var vm = this;
        //- init variable
        vm.workId = $stateParams.id;
        vm.title = "编辑作业";
        vm.workTypes = [{
            type: 'PRE_CLASS',
            label: '课前预习'
        },{
            type: 'ENTRY_TEST',
            label: '入门测试'
        },{
            type: 'IN_CLASS',
            label: '课中例题'
        },{
            type: 'CLASS_TEST',
            label: '课堂测试'
        },{
            type: 'AFTER_CLASS',
            label: '课后作业'
        }]
        vm.courseTypes = [{
            type: 'COMMON',
            label: '普通课程'
        }, {
            type: 'DOUBLE_TEACHER',
            label: '双师课程'
        }]

        vm.back = back;
        vm.openDialog = openDialog;
        vm.delete = deleteWork;
        vm.updateWork = updateWork;
        vm.addVideo = addVideo;
        vm.deleteVideo = deleteVideo;
        vm.getPaperIds = getPaperIds;
        vm.getVideoUrls = getVideoUrls;

        //- 启动
        activate();

        function activate() {
            vm.workInfo = {
                workTitle: '',
                type: 'PRE_CLASS',
                papers: [],
                videos: [{}],
                courseType: 'DOUBLE_TEACHER'
            }
            var params = { workId: vm.workId }
            return getWork(params).then(function() {
                $log.info('activated workInfo view')
                vm.loading = false;
            })
        }

        /**
         * 查询作业
         */
        function getWork(params) {

            return frWorkMgtWorkEdit.query(params)
                .then(function(data) {
                    if (data.error) {
                        $uibMsgbox.error(data.message);
                        return
                    }
                    vm.workInfo = data.data.workInfo;
                    if (vm.workInfo.videos.length == 0) {
                        vm.workInfo.videos.push({});
                    }
                    return vm.workInfo;
                }, function(error) {
                    $uibMsgbox.error('请求失败');
                })
        }

        function addVideo() {
            vm.workInfo.videos.push({});
        }

        function deleteVideo(index) {
            vm.workInfo.videos.splice(index, 1);
        }
        /**
         * 更新作业
         */
        function updateWork() {
            vm.videoUrlError = false;
            if (notExistPaper(vm.workInfo.papers) && notExistVideos(vm.workInfo.videos)) {
                $uibMsgbox.alert("作业或视频至少要选择一个");
                return ;
            }
            var params = {
                workId: vm.workId,
                subjectId: $rootScope.selectedSubject,
                gradeId: $rootScope.selectedGrade,
                workTitle: vm.workInfo.workTitle,
                type: vm.workInfo.type,
                courseType: vm.workInfo.courseType,
                paperIds: vm.getPaperIds(vm.workInfo.papers),
                videoUrls: vm.getVideoUrls(vm.workInfo.videos),
                productCode: 'DOUBLE_TEACHER_2'
            }
            if (vm.videoUrlError) {
                $uibMsgbox.alert("存在视频格式不正确");
                return ;
            }
            return frWorkMgtWorkEdit.update(params)
                .then(function(data) {
                    if (data.error) {
                        $uibMsgbox.error(data.message || '请求数据失败！');
                        return ;
                    }
                    //- 调回到作业列表
                    $state.go('workManagement');
                }, function(error) {
                    console.log(error);
                })

            function notExistPaper(papers) {
                return papers.length === 0;
            }

            function notExistVideos(videos) {
                if (Object.keys(mgtWorkEdit.workInfo.videos[0]).indexOf('videoUrl') > -1) {
                    if (mgtWorkEdit.workInfo.videos[0]['videoUrl'].length > 0) {
                        return false;
                    }
                    return true;
                }
                return true;
            }
        }

        function getPaperIds(items) {
            if (angular.isArray(items) && items.length > 0) {
                return items.map(function(item) {
                    return item.id;
                }).join(',')
            }
            return ;
        }

        function getVideoUrls(items) {
            if (angular.isArray(items) && items.length > 0) {
                return items.map(function(item) {
                    if (item.videoUrl && item.videoUrl.indexOf('http') === -1) {
                        vm.videoUrlError = true;
                        return ''
                    }
                    return item.videoUrl;
                })
            }
            return [];
        }

        function back() {
            $state.go('workManagement');
        }

        //-
        function openDialog() {

            $uibModal.open({
                size: 'lg',
                templateUrl: 'templates/block/modal/mgt-work-modal.html',
                controller: 'frMgtWorkModalCtrl',
                controllerAs: 'mgtWorkModal',
                resolve: {
                    items: function() {
                        return vm.workInfo.papers || [];
                    }
                }
            }).result.then(function (data) {
                vm.workInfo.papers = data;
            }, function () {});
        }

        function deleteWork(items, index) {
            return items.splice(index, 1);
        }

    }

    frWorkMgtWorkEditService.$inject = ['$http', '$log', '$q'];
    function frWorkMgtWorkEditService($http, $log, $q) {
        return {
            query: query,
            update: update
        }
        function query(params) {
            return $http.get('/erp/work/queryWorkInfoById', {params: params})
                .then(queryWorkComplete)
                .catch(queryWorkFailed);

            function queryWorkComplete(response) {
                return response.data;
            }

            function queryWorkFailed(error) {
                $log.error('XHR Failed for getWorkById.' + error);
                $q.reject(error);
            }
        }

        function update(params) {
            return $http.put('/erp/work/updateWorkInfo', params || {})
                .then(updateWorkComplete)
                .catch(updateWorkFailed);

            function updateWorkComplete(response) {
                return response.data;
            }

            function updateWorkFailed(error) {
                $log.error('XHR Failed for updateWork.' + error.data);
                $q.reject(error);
            }
        }
    }

})();