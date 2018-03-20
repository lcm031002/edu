(function() {
  "use strict";

  angular.module('ework-ui')
    .config(frMyCourseConfig)
    .controller('frMyCourseCtrl', frMyCourseCtrl)
    .filter('frStatus', frStatusFilter)
    .service('frMyCourse', frMyCourseService)

  frMyCourseConfig.$inject = ['$stateProvider']
  function frMyCourseConfig($stateProvider) {
    $stateProvider
      .state('/myCourse/myCourseClass', {
        url: '/tr/myCourse/:id/myCourseClass',
        templateUrl: 'templates/tr/template/myCourseClass.html'
      })
      .state('/myCourse/video', {
        url: '/tr/myCourse/:id/video',
        templateUrl: 'templates/tr/video.html'
      })
  }

  frMyCourseCtrl.$inject = ['$scope','frMyCourse', '$log', '$state', '$uibMsgbox', '$rootScope'];
  function frMyCourseCtrl($scope, frMyCourse, $log, $state, $uibMsgbox, $rootScope) {
    window.myCourse = $scope.myCourse;
    var vm = this;
    //- init variable
    vm.title = "我的课程";
    vm.first = 1;
    vm.loading = true;
    vm.courseInfoList = [];
    vm.queryParams = {
      courseTitle: '',
      subjectId: '',
      gradeId: '',
      limit: 10,
      start: 0
    }
    vm.courseStatuses = [{
      label: '全部',
      value: ''
    },{
      label: '未结课',
      value: 1
    },{
      label: '已结课',
      value: 2
    }]
    vm.paginationConf = {
      currentPage: 1,
      totalItems: 0,
      itemsPerPage: 10,
      onChange: function() {
        // if (vm.queryParams.limit == vm.paginationConf.itemsPerPage && vm.first === 1 && vm.paginationConf.currentPage === 1) {
        //   return
        // }
        vm.queryParams.start = vm.paginationConf.currentPage - 1;
        sessionStorage.setItem('myCourseCurrentPage', parseInt(vm.queryParams.start))
        vm.queryParams.limit = vm.paginationConf.itemsPerPage;
        vm.getCourses(vm.queryParams);
        vm.first++;
      }
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

    //- method
    vm.activate = activate;
    vm.go = go;
    vm.getCourses = getCourses;
    vm.search = search;
    vm.toggleShow = toggleShow;
    vm.queryByCondition = queryByCondition;

    //- 启动
    // activate();

    function activate() {
      if(sessionStorage.getItem('myCourseCurrentPage')) {
        vm.queryParams.start = parseInt(sessionStorage.getItem('myCourseCurrentPage'));
        vm.paginationConf.currentPage = parseInt(vm.queryParams.start) + 1;
      }
      if(sessionStorage.getItem('myCourseSearchParams')) {
        vm.queryParams.courseTitle = sessionStorage.getItem('myCourseSearchParams')
      }
      return getCourses(vm.queryParams).then(function() {
        $log.info('activated courses view')
        vm.loading = false;
      })
    }

    function go(stateName, id) {
      $state.go(stateName, {id: id});
    }

    function search(key) {
      vm.loading = true;
      vm.queryParams.courseTitle = key
      vm.queryParams.start = 0;
      sessionStorage.setItem("myCourseCurrentPage", vm.queryParams.start);
      return getCourses(vm.queryParams).then(function() {
        sessionStorage.setItem('myCourseSearchParams', key)
        $log.info('search condition:' + key);
        if (vm.courseInfoList.length === 0) {
          vm.paginationConf.totalItems = 0;
        }
        vm.loading = false;
      })
    }

    function toggleShow(key) {
      if (key == 'courseStatus') {
        vm[key + 'Field'] = !vm[key + 'Field'];
        return 
      }
    }

    function queryByCondition(type, key) {
      vm.loading = true;
      var arr = ['courseStatus']
      angular.forEach(arr, function(item) {
        vm[item+'Field'] = false;
      })
      vm.queryParams[key] = type;
      vm.queryParams.start = 0;
      getCourses(vm.queryParams).then(function() {
        $log.info('search selectType:' + type);
      }).finally(function() {
        vm.loading = false;
      })
    }

    /**
     * [depend on course name to search result]
     * @param  {[string]} key [course name]
     * @return {[type]}     [description]
     */
    function getCourses(params) {
      return frMyCourse.getCourses(params)
        .then(function(data) {
          if (data.error) {
            $uibMsgbox.error(data.message || '请求数据失败！');
            vm.paginationConf.totalItems = 0;
            return ;
          }
          vm.courseInfoList = data.data.courseList;
          vm.paginationConf.totalItems = data.total || 0;
          return vm.courseInfoList;
        }, function(error) {
          console.log(error);
        })
    }

  }

  function frStatusFilter() {
    return function(status) {
      if (angular.isUndefined(status)) {return ;}
      if (status == 1) {
        return '未结课';
      } else if(status == 2) {
        return '已结课';
      } else {
        return '';
      }
    }
  }

  frMyCourseService.$inject = ['$http', '$log', '$q'];
  function frMyCourseService($http, $log, $q) {
    return {
      getCourses: getCourses
    }

    function getCourses(params) {
      return $http.post('/erp/myCourse/queryCourseDetailList', params || {})
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
  }

})();