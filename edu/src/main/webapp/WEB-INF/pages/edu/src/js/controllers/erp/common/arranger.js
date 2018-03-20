(function() {
  'use strict';

  angular
    .module('ework-ui')
    .controller('erp_arrangerController', erp_arrangerController);

  erp_arrangerController.$inject = [
    '$scope',
    '$state',
    '$uibMsgbox',
    'erp_arrangerService'
  ];
  function erp_arrangerController(
    $scope,
    $state,
    $uibMsgbox,
    erp_arrangerService
  ) {
    var vm = this;
    $scope.arrangerList = [];
    $scope.paginationConf = {
      currentPage: 1, //当前页
      totalItems: 0,
      itemsPerPage: 10,
      onChange: function(){
          $scope.getArrangerList();
      }
  };

    $scope.onAddArranger = function() {
      $state.go('systemDataArrangerEdit', {
        optype: 'add'
      })
    }

    $scope.onEditArranger = function (item) {
      $state.go('systemDataArrangerEdit', {
        optype: 'edit',
        id: item.id
      })
    }

    $scope.onDeleteArranger = function (item) {
      $uibMsgbox.confirm('确认删除所选的排课专员？', function (res) {
        if (res == 'yes') {
          erp_arrangerService.delete({
            id: item.id
          }).$promise.then(function(resp) {
            if (!resp.error) {
              $uibMsgbox.success('删除成功！');
              $scope.getArrangerList();
            } else {
              $uibMsgbox.error(resp.message || '删除失败！请重试或联系管理员！')
            }
          }, function(resp) {
            console.error(resp);
            $uibMsgbox.error('请求失败！请联系重试或者管理员！');
          })
        }
      })
    }

    $scope.getArrangerList = function () {
      $scope.searchParams = {
        pageSize: $scope.paginationConf.itemsPerPage,
        currentPage: $scope.paginationConf.currentPage
      }
      $scope.loadStatues = true;
      erp_arrangerService.query($scope.searchParams).$promise
        .then(function (resp) {
          $scope.loadStatues = false;
          if (!resp.error) {
            _.forEach(resp.data, function(item) {
              _.forEach(item.courseArrangeSpSubjectList, function(subject) {
                subject.gradeNameList = []
                _.forEach(subject.courseArrangeSpGradeList, function(grade) {
                  subject.gradeNameList.push(grade.gradeName)
                })
              })
            })
            $scope.arrangerList = resp.data;
            $scope.paginationConf.totalItems = resp.total; 
            setTimeout(function(){
              $('[data-toggle="popover"]').popover()
            })
          } else {
            $uibMsgbox.error(resp.message || '请求数据失败，请联系管理员！')
          }
        }, function(resp) {
          console.error(resp)
          $uibMsgbox.error('请求数据失败，请联系管理员！' + resp.message || '')
        })
    }

    activate();
    ////////////////

    function activate() { 
      $scope.getArrangerList();
    }
  }
})();