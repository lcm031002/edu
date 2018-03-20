(function() {
    "use strict";

    angular.module('ework-ui')
        .config(['$stateProvider', function($stateProvider) {
            $stateProvider
                .state('/workManagement2/setting', {
                    url: '/tr/workManagement2/:id/setting',
                    templateUrl: 'templates/tr/workManagement2/workManagementSetting.html'
                })
                .state('/workManagement2/work/add', {
                    url: '/tr/workManagement2/work/add',
                    templateUrl: 'templates/tr/workManagement2/add.html'
                })
                .state('/workManagement2/work/edit', {
                    url: '/tr/workManagement2/work/:id/edit',
                    templateUrl: 'templates/tr/workManagement2/edit.html'
                })
                .state('/workManagement2/work/view', {
                    url: '/tr/workManagement2/work/:id/view?type',
                    templateUrl: 'templates/tr/workManagement2/view.html'
                })
        }])
        .controller('frWorkManagementCtrl_2', frWorkManagementCtrl)
        .service('frWorkMgt', frWorkMgtService)
        .filter('frStatusCode', frStatusCodeFilter)
        .filter('frType', frTypeFilter)


    frWorkManagementCtrl.$inject = ['$scope', '$state', 'frWorkMgt', '$log', '$uibMsgbox', '$location', '$q', '$rootScope']
    function frWorkManagementCtrl($scope, $state, frWorkMgt, $log, $uibMsgbox, $location, $q, $rootScope) {
        window.workMgt = $scope.workMgt;
        window.rootScope = $rootScope;
        var vm = this;
        vm.first = 1;
        vm.loading = true;
        vm.queryParams = {
            type: '',
            workTitle: '',
            statusCode: undefined,
            subjectId: '',
            gradeId: '',
            limit: 10,
            start: 0,
            productCode: 'DOUBLE_TEACHER_2'
        }
        $scope.$watch('selectedSubject', function(newValue, oldValue) {
            vm.queryParams.subjectId = newValue;
            if (vm.queryParams.subjectId && vm.queryParams.gradeId) {
                activate();
            }
        })

        $scope.$watch('selectedGrade', function(newValue, oldValue) {
            vm.queryParams.gradeId = newValue;
            if (vm.queryParams.subjectId && vm.queryParams.gradeId) {
                activate();
            }
        })
        vm.workTypes = [{
            value: '',
            label: '全部'
        },{
            value: 'PRE_CLASS',
            label: '课前预习'
        },{
            value: 'ENTRY_TEST',
            label: '入门测试'
        },{
            value: 'IN_CLASS',
            label: '课中例题'
        },{
            value: 'CLASS_TEST',
            label: '课堂测试'
        },{
            value: 'AFTER_CLASS',
            label: '课后作业'
        }]
        vm.statusCodes = [{
            label: '全部',
            value: ''
        },{
            label: '已发布',
            value: 'PUBLISHED'
        },{
            label: '未发布',
            value: 'INIT'
        }]
        /**
         * 分页参数
         * @type {Object}
         */
        vm.paginationConf = {
            currentPage: 1,
            totalItems: 1,
            itemsPerPage: 10,
            onChange: function() {
                // if (vm.queryParams.limit == vm.paginationConf.itemsPerPage && vm.first === 1 && vm.paginationConf.currentPage === 1) {
                //   return
                // }
                vm.queryParams.start = vm.paginationConf.currentPage - 1;
                sessionStorage.setItem("workManagementCurrentPage", vm.queryParams.start);
                vm.queryParams.limit = vm.paginationConf.itemsPerPage
                vm.getWorkList(vm.queryParams);
                vm.first++;
            }
        }

        //- methods
        vm.getWorkList = getWorkList;
        vm.setClass = setClass;
        vm.addWork = addWork;
        vm.edit = edit;
        vm.view = view;
        vm.delete = deleteWork;
        vm.remove = remove;
        vm.search = search;
        vm.toggleShow = toggleShow;
        vm.queryByCondition = queryByCondition;

        //- 启动
        // activate();

        function activate() {
            if(parseInt(sessionStorage.getItem('workManagementCurrentPage'))) {
                vm.queryParams.start = parseInt(sessionStorage.getItem('workManagementCurrentPage'));
                vm.paginationConf.currentPage = vm.queryParams.start + 1;
            }
            if(sessionStorage.getItem('workManagementSearchParams')) {
                vm.queryParams.workTitle = sessionStorage.getItem('workManagementSearchParams')
            }
            return getWorkList(vm.queryParams).then(function() {
                $log.info('activated workList view')
                vm.loading = false;
            })
        }

        function getWorkList(params) {
            return frWorkMgt.getWorkList(params)
                .then(function(data) {
                    if (data.error) {
                        $uibMsgbox.error(data.message || '请求数据失败！');
                        vm.paginationConf.totalItems = 0;
                        return
                    }
                    vm.workList = data.data.workList;
                    if (vm.workList.length === 0) {
                        vm.paginationConf.totalItems = 0;
                        return ;
                    }
                    vm.paginationConf.totalItems = data.total || 0;
                    return vm.workList;
                }, function(error) {
                    vm.paginationConf.totalItems = 0;
                    $uibMsgbox.error('请求失败');
                })
        }

        function setClass(workId) {
            //- 跳到课次设置
            $state.go('/workManagement2/setting', {id: workId});
        }

        function addWork() {
            $state.go('/workManagement2/work/add');
        }

        function edit(id) {
            $state.go('/workManagement2/work/edit', {id: id});
        }

        function view(id, type) {
            $state.go('/workManagement2/work/view', {id: id, type: type});
        }

        function deleteWork(id) {
            $uibMsgbox.confirm('确定删除?', function (result) {
                if(result != 'yes') {
                    return;
                }
                vm.remove(id);
            });
        }

        function remove(id) {
            return frWorkMgt.delete({workId: id})
                .then(function() {
                    activate();
                },function() {
                    $uibMsgbox.error('删除失败！');
                })
        }

        function search(searchParam) {
            vm.loading = true;
            vm.queryParams.start = 0;
            sessionStorage.setItem("workManagementCurrentPage", vm.queryParams.start);
            vm.queryParams.workTitle = searchParam;
            return getWorkList(vm.queryParams).then(function() {
                sessionStorage.setItem('workManagementSearchParams', searchParam)
                $log.info('search condition:' + searchParam);
            }).finally(function() {
                vm.loading = false;
            })
        }

        function toggleShow(key) {
            // var arr = ['type', 'statusCode']
            if (key == 'type') {
                vm[key + 'Field'] = !vm[key + 'Field'];
                vm['statusCodeField'] = false;
                return
            }
            if (key == 'statusCode') {
                vm[key + 'Field'] = !vm[key + 'Field'];
                vm['typeField'] = false;
                return
            }
        }

        function queryByCondition(type, key) {
            vm.loading = true;
            var arr = ['type', 'statusCode']
            angular.forEach(arr, function(item) {
                vm[item+'Field'] = false;
            })
            vm.queryParams[key] = type;
            vm.queryParams.start = 0;
            getWorkList(vm.queryParams).then(function() {
                $log.info('search selectType:' + type);
            }).finally(function() {
                vm.loading = false;
            })
        }

    }

    frWorkMgtService.$inject = ['$http', '$log', '$q'];
    function frWorkMgtService($http, $log, $q) {
        return {
            getWorkList: getWorkList,
            delete: deleteWork
        }

        function getWorkList(params) {
            return $http.post('/erp/work/queryWorkInfoList', params || {})
                .then(getCourseComplete)
                .catch(getCourseFailed);
            function getCourseComplete(response) {
                return response.data;
            }

            function getCourseFailed(error) {
                $log.error('XHR Failed for getCourses.' + error.data);
                return $q.reject(error);
            }
        }

        function deleteWork(params) {
            return $http.delete('/erp/work/deleteWorkInfo', {params: params})
                .then(deleteComplete)
                .catch(deleteFailed);
            function deleteComplete(response) {
                return response.data;
            }

            function deleteFailed(error) {
                $log.error('XHR Failed for deleteWork.' + error);
                return $q.reject(error);
            }
        }
    }

    function frStatusCodeFilter() {
        return function(code) {
            if (angular.isUndefined(code)) {return ;}
            if (code == 'INIT') {
                return '未发布';
            } else {
                return '已发布';
            }
        }
    }

    function frTypeFilter() {
        return function(type) {
            if (angular.isUndefined(type)) {return ;}
            switch(type) {
                case 'PRE_CLASS':
                    return '课前预习';
                case 'ENTRY_TEST':
                    return '入门测试';
                case 'IN_CLASS':
                    return '课中例题';
                case 'AFTER_CLASS':
                    return '课后作业';
                case 'CLASS_TEST':
                    return '课堂测试';
            }
        }
    }

})();
