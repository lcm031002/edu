(function() {
  "use strict";

  angular.module('ework-ui')
    .controller('frMgtWorkModalCtrl', frMgtWorkModalCtrl)
    .service('frMgtWorkModal', frMgtWorkModalService)

  frMgtWorkModalCtrl.$inject = ['$scope', '$uibModalInstance', '$uibMsgbox', 'items', 'frMgtWorkModal', '$log', '$rootScope', '$stateParams']
  function frMgtWorkModalCtrl($scope, $uibModalInstance, $uibMsgbox, items, frMgtWorkModal, $log, $rootScope, $stateParams) {
    var vm = this;
    window.ss = $scope;
    vm.items  = items;
    window.mgtWorkModal = $scope.mgtWorkModal;
    vm.queryParams = {
      subjectId: $rootScope.selectedSubject,
      gradeId: $rootScope.selectedGrade,
      productCode: $stateParams.productCode
    }
    //- methods
    vm.handleModalCancel = handleModalCancel;
    vm.handleModalConfirm = handleModalConfirm;
    vm.search = search;
    vm.select = select;
    vm.delete = deleteWork;
    vm.clearAll = clearAll;

    activate();

    function activate() {
      getPaperList(vm.queryParams)
        .then(function() {
          $log.info('PaperList view');
          if (vm.items.length) {
            angular.forEach(vm.items, function(item) {
              var index = _.findIndex(vm.workRecords, function(record) {
                return record.id == item.id
              })
              if (index > -1) {
                vm.workRecords[index]['selected'] = true;
              }
            })
          }
        })
    } 


    function getPaperList(params) {
      return frMgtWorkModal.get(params)
        .then(function(data) {
          vm.workRecords = data.data.paperList || [];
          return vm.courseList;
        }, function(error) {
          console.log(error);
        })
    }

    function handleModalCancel() {
      $uibModalInstance.dismiss('cancel');
    }

    function handleModalConfirm() {
      $uibModalInstance.close(vm.items);
    }

    function search(key) {
      if (!key) {
        $uibMsgbox.error('请填写搜索条件');
        return ;
      }
      vm.queryParams['paperName'] = key;
      getPaperList(vm.queryParams)
        .then(function() {
          $log.info('PaperList search result');
        })

    }

    function select(item) {
      if (_.findIndex(vm.items, function(o) { return o.id == item.id; }) < 0) {
        item['selected'] = true;
        vm.items.push(item);
      } else {
        vm.delete(item);
      }
    }

    function deleteWork(item) {
      var index = _.findIndex(vm.items, function(o) { return o.id == item.id; })
      if (index > -1) {
        vm.items.splice(index, 1);
      }

      var originalIndex = _.findIndex(vm.workRecords, function(record) {
        return record.id == item.id
      })
      if (originalIndex > -1) {
        vm.workRecords[originalIndex]['selected'] = false;
      }

    }

    function handleClassList(originalItems, items) {
      var courseClassList;
      angular.forEach(items, function(item, index, arr) {
        item['selected'] = true;
      })
    }

    function clearAll() {
      $uibMsgbox.confirm('确定全部清空?', function (result) {
        if(result != 'yes') {
          return;
        }
        vm.items = [];
        angular.forEach(vm.workRecords, function(item, index, arr) {
          item['selected'] = false;
        })
      });

    }

  }

  frMgtWorkModalService.$inject = ['$http', '$log', '$q'];
  function frMgtWorkModalService($http, $log, $q) {
    return {
      get: get
    }

    function get(params) {
      return $http.post('/erp/work/queryPaperList', params || {})
        .then(getPaperListComplete)
        .catch(getPaperListFailed);

      function getPaperListComplete(response) {
        return response.data;
      }

      function getPaperListFailed(error) {
        $log.error('XHR Failed for CourseClassList.' + error.data);
        $q.reject(error);
      }
    }
  }
  
})();