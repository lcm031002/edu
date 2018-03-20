(function() {
  "use strict";

  angular.module('ework-ui')
    .controller('frWorkMgtWorkViewCtrl', frWorkMgtWorkViewCtrl)
    .service('frPaperItem', frPaperItemService)
    .filter('trustUrl', trustUrlFilter)
    .filter('trustHtml', ['$sce', function ($sce) {
      return function (input) {
          return $sce.trustAsHtml(input)
      }
    }])

  frWorkMgtWorkViewCtrl.$inject = ['$scope', '$log', '$uibModal', 'frPaperItem', '$stateParams', '$sce'];
  function frWorkMgtWorkViewCtrl($scope, $log, $uibModal, frPaperItem, $stateParams, $sce) {
    window.mgtWorkView = $scope.mgtWorkView;
    var vm = this;
    angular.extend(vm, {$stateParams})
    //- init variable
    vm.id = $stateParams.id;
    vm.type = $stateParams.type || 'paper';
    if (vm.type == 'paper') {
      vm.title = "作业浏览"
    } else if (vm.type == 'video') {
      vm.title = "视频"
    }
    vm.page = 1;
    vm.total = 1;
    vm.loading = true;

    //- method
    vm.activate = activate;
    vm.getPaperItems = getPaperItems
    vm.back = back;
    vm.next = next;
    vm.previous = previous;
    vm.hasPrevious = hasPrevious;
    vm.hasNext = hasNext;
    vm.paper = 0;
    vm.prePaper = prePaper;
    vm.nextPaper = nextPaper;
    vm.hasPaperPrevious = hasPaperPrevious;
    vm.hasPaperNext = hasPaperNext;
    //- 启动
    activate();

    function activate() {
      return getPaperItems().then(function() {
        $log.info('activated courses view')
        if (vm.type === 'paper') {
          vm.total = vm.paperInfo.length;
        }
        
      }).finally(function() {
        vm.loading = false;
      })
    }

    function getPaperItems() {
      var params = {
        workIds: vm.id,
        type: vm.type
      }
      return frPaperItem.getPaperItem(params)
        .then(function(data) {
          if (vm.type === 'paper') {
            var i = vm.paper;
            vm.paperTotal = data.paperList.length;
            vm.paperName = data.paperList && data.paperList[i] && data.paperList[i].paperName
            vm.paperInfo = data.paperList && data.paperList[i] && data.paperList[i].paperInfo;
            angular.forEach(vm.paperInfo, function(item) {
              var diffLevelValue = parseInt(item.diffLevel && item.diffLevel.dataValue || 0)
              if (diffLevelValue > 5) {
                item.diffLevel.dataValue = 5;
              }
              var i = 0
              item['stars'] = [];
              for(i=0; i < diffLevelValue; i++) {
                item['stars'].push({id: i});
              }
            })
            return vm.paperInfo;
          } else {
            vm.videoList = data.videoList;
            vm.videoUrl = vm.videoList && vm.videoList[0].videoUrl;
          }
        }, function(error) {
          console.log(error);
        }).finally(function() {
          vm.loading = false;
        })
    }

    function back() {
      history.back();
    }

    function previous() {
      if (vm.hasPrevious()) {
        return vm.page--;
      }
    }

    function next() {
      if (vm.hasNext()) {
        return vm.page++;
      }
    }

    function hasPrevious() {
      return vm.page > 1;
    }

    function hasNext() {
      return vm.page < vm.total;
    }

    function prePaper() {
      if (vm.hasPaperPrevious()) {
        getPaperItems();
        vm.page = 1;       
        return vm.paper--;
      }
    }

    function nextPaper() {
      if (vm.hasPaperNext()) {
        getPaperItems();
        vm.page = 1;  
        return vm.paper++;
      }
    }

    function hasPaperPrevious() {
      return vm.paper > 0;
    }

    function hasPaperNext() {
      return vm.paper < vm.paperTotal-1;
    }
  }

  frPaperItemService.$inject = ['$http', '$log', '$q'];
  function frPaperItemService($http, $log, $q) {
    return {
      getPaperItem: getPaperItem
    }

    function getPaperItem(params) {
      return $http.get('/erp/myCourse/queryWorkRecord', {params: params})
        .then(getComplete)
        .catch(getFailed);

      function getComplete(response) {
        return response.data.data;
      }

      function getFailed(error) {
        $log.error('XHR Failed for getCourses.' + error.data);
        $q.reject(error);
      }
    }
  }

  trustUrlFilter.$inject = ['$sce'];
  function trustUrlFilter($sce) {
    return function (recordingUrl) {
      return $sce.trustAsResourceUrl(recordingUrl);
    };
  }

})();
