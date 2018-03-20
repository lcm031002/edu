(function() {
  "use strict";

  angular.module('ework-ui')
    .controller('frMyCourseClassCtrl', frMyCourseClassCtrl)
    .service('frMyCourseClass', frMyCourseClassService)

  frMyCourseClassCtrl.$inject = ['$scope', '$log', 'frMyCourseClass', '$stateParams', '$state'];
  function frMyCourseClassCtrl($scope, $log, frMyCourseClass, $stateParams, $state) {
    window.myCourseClass = $scope.myCourseClass;
    var vm = this;
    vm.id = $stateParams.id;
    //- init variable
    vm.title = "我的课次";
    vm.courseClassInfoList = [];
    vm.loading = true;

    //- method
    vm.activate = activate;
    vm.getCourseClass = getCourseClass;
    vm.back = back;
    vm.go = go;

    //- 启动
    activate();

    function activate() {
      return getCourseClass(vm.id).then(function() {
        $log.info('activated courseClass view');
        vm.loading = false;
      })
    }

    function getCourseClass(key) {
      var params = {
        courseId: key
      };
      return frMyCourseClass.getCourseClass(params)
        .then(function(data) {
          vm.courseClassInfoList = data.data.courseClassList;
          return vm.courseClassInfoList;
        }, function(error) {
          console.log(error);
        })
    }

    function back() {
      history.back();
    }

    function go(stateName, id, type) {
      $state.go(stateName, {id: id, type: type});
    }

  }

  frMyCourseClassService.$inject = ['$http', '$log', '$q'];
  function frMyCourseClassService($http, $log, $q) {
    return {
      getCourseClass: getCourseClass
    }

    function getCourseClass(params) {
      return $http.get('/erp/myCourse/queryCourseClassDetailList', {params: params})
        .then(getCourseClassComplete)
        .catch(getCourseClassFailed);

      function getCourseClassComplete(response) {
        return response.data;
      }

      function getCourseClassFailed(error) {
        $log.error('XHR Failed for getCourseClass.' + error);
        $q.reject(error);
      }
    }
  }

})();