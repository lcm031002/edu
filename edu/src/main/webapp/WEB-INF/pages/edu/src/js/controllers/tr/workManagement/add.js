(function() {
    "use strict";

    angular.module('ework-ui')
        .controller('frWorkMgtWorkAddCtrl', frWorkMgtWorkAddCtrl)
        .service('frWorkMgtWorkAdd', frWorkMgtWorkAddService)

    frWorkMgtWorkAddCtrl.$inject = ['$scope', '$log', '$uibModal', '$resource', 'frWorkMgtWorkAdd', '$state', '$uibMsgbox', '$rootScope', '$stateParams'];
    function frWorkMgtWorkAddCtrl($scope, $log, $uibModal, $resource, frWorkMgtWorkAdd, $state, $uibMsgbox, $rootScope, $stateParams) {
        window.mgtWorkAdd = $scope.mgtWorkAdd;
        // window.rootScopeAdd = $rootScope
        var vm = this;
        angular.extend(vm, { $stateParams })
        //- init variable
        vm.title = "添加作业";
        vm.productCode = $stateParams.productCode || 'DOUBLE_TEACHER'
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
        },{
          type: 'EXAM',
          label: '考试'
        }]

        vm.productLineCodes = [{
            type: 'HI_STUDY',
            label: '培英班课程'
        }, {
            type: 'DOUBLE_TEACHER',
            label: '双师课程'
        }]

        //- method
        vm.activate = activate;
        vm.back = back;
        vm.openDialog = openDialog;
        vm.delete = deleteWork;
        vm.createWork = createWork;
        vm.getPaperIds = getPaperIds;
        vm.getVideoUrls = getVideoUrls;
        vm.addVideo = addVideo;
        vm.deleteVideo = deleteVideo;
        //- 启动
        activate();

        function activate() {
            vm.workInfo = {
                workTitle: '',
                type: 'PRE_CLASS',
                papers: [],
                videos: [{}],
                productCode: vm.productCode || 'DOUBLE_TEACHER'
            }
        }

        function addVideo() {
            vm.workInfo.videos.push({});
        }

        function deleteVideo(index) {
            vm.workInfo.videos.splice(index, 1);
        }

        function back() {
            history.back();
        }

        /**
         * 创建作业
         */
        function createWork() {
            vm.videoUrlError = false;
            if (notExistPaper(vm.workInfo.papers) && notExistVideos(vm.workInfo.videos)) {
                $uibMsgbox.alert("作业或视频至少要选择一个");
                return ;
            }
            var params = {
                subjectId: $rootScope.selectedSubject,
                gradeId: $rootScope.selectedGrade,
                workTitle: vm.workInfo.workTitle,
                type: vm.workInfo.type,
                productCode: vm.productCode,
                paperIds: vm.getPaperIds(vm.workInfo.papers),
                videoUrls: vm.getVideoUrls(vm.workInfo.videos)
            }

            if (vm.videoUrlError) {
                $uibMsgbox.alert("存在视频格式不正确");
                return ;
            }

            return frWorkMgtWorkAdd.create(params)
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
                if (Object.keys(mgtWorkAdd.workInfo.videos[0]).indexOf('videoUrl') > -1) {
                    if (mgtWorkAdd.workInfo.videos[0]['videoUrl'].length > 0) {
                        return false;
                    }
                    return true;
                }
                return true;
            }
        }

        /**
         * 根据已选组成需要的ids: 'xxx,xxx1'
         */
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

        //-
        function openDialog() {
            $uibModal.open({
                size: 'lg',
                templateUrl: 'templates/block/modal/mgt-work-modal.html',
                controller: 'frMgtWorkModalCtrl',
                controllerAs: 'mgtWorkModal',
                resolve: {
                    items: function() {
                        return vm.workInfo.papers;
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

    frWorkMgtWorkAddService.$inject = ['$http', '$log'];
    function frWorkMgtWorkAddService($http, $log) {
        return {
            create: create
        }

        function create(params) {
            return $http.post('/erp/work/insertWorkInfo', params || {})
                .then(createWorkComplete)
                .catch(createWorkFailed);

            function createWorkComplete(response) {
                return response.data;
            }

            function createWorkFailed(error) {
                $log.error('XHR Failed for createWork.' + error.data);
            }
        }
    }

})();