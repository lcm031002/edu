(function() {
  "use strict";

  angular.module('ework-ui')
    .controller('frClassWorkModalCtrl', frClassWorkModalCtrl)
    .service('frClassWorkModal', frClassWorkModalService)

  frClassWorkModalCtrl.$inject = ['$scope', '$log','$uibModalInstance', '$uibMsgbox', 'uibDateParser', 'frClassWorkModal', 'items', '$rootScope', '$stateParams']
  function frClassWorkModalCtrl($scope, $log, $uibModalInstance, $uibMsgbox, uibDateParser, frClassWorkModal, items, $rootScope, $stateParams) {
    var vm = this;
    window.ss = $scope;
    angular.extend(vm, {$stateParams})
    vm.queryParams = {
      courseName: '',
      gradeId: $rootScope.selectedGrade,
      subjectId: $rootScope.selectedSubject,
      limit: 10,
      start: 0,
      productCode: $stateParams.productCode
    }
    window.classWorkModal = $scope.classWorkModal;
    console.log('classWorkModal');
    vm.courseList = [];
    vm.paginationConf = {
      currentPage: 1,
      totalItems: 0,
      itemsPerPage: 10,
      onChange: function() {
        vm.queryParams.start = vm.paginationConf.currentPage - 1;
        vm.queryParams.limit = vm.paginationConf.itemsPerPage;
        getCourseClassList(vm.queryParams);
      }
    }

    //- methods
    vm.handleModalCancel = handleModalCancel;
    vm.handleModalConfirm = handleModalConfirm;
    vm.search = search;
    vm.toggleCourseClass = toggleCourseClass;
    vm.select = select;
    vm.delete = deleteCourseClass;
    vm.positionOfChild = positionOfChild;
    //- todo: 根据已选与列表进行匹配，自动在列表中识别出已选的item
    vm.handleClassList = handleClassList;
    vm.clearAll = clearAll;
    vm.isSelected = isSelected;

    activate();

    function activate() {
      vm.showSelectCourseClass = angular.copy(items) || [];
      vm.refSelectCourseClass= items
    } 

    //- 请求课程课次列表
    function getCourseClassList(params) {
      return frClassWorkModal.get(params)
        .then(function(data) {
          if (data.error) {
            $uibMsgbox.error(data.message || '请求数据失败！');
            vm.paginationConf.totalItems = 0;
            return 
          }
          vm.courseList = data.data;
          if (vm.courseList.length === 0) {
            vm.paginationConf.totalItems = 0;
            return ;
          }
          vm.paginationConf.totalItems = data.total || 0;
          return vm.courseList;
        }, function(error) {
          $uibMsgbox.error(error.message || '请求数据失败！');
          vm.paginationConf.totalItems = 0;
        })
    }

    function handleModalCancel() {
      $uibModalInstance.dismiss('cancel');
    }

    function handleModalConfirm() {
      if ($stateParams.productCode=='DOUBLE_TEACHER'){
        if (!checkMTCourse(vm.showSelectCourseClass)){
          return ;
        }
      }else {
        if(checkDateValidation(vm.showSelectCourseClass)) {
          $uibMsgbox.error('课次时间不一致，请重新检查!');
          return ;
        }
        $uibModalInstance.close(vm.showSelectCourseClass);
      }
    }

    function checkDateValidation(items) {
      var result = []
      var firstItem
      if(items.length) {
        firstItem = items[0]
      }
      result = items.filter(function(item) {
        return item.startDate !== firstItem.startDate ||item.startTime !== firstItem.startTime || item.endTime !== firstItem.endTime
      })
      return result.length ? true : false;
      
    }

    function checkMTCourse(items) {
      return frClassWorkModal.checkMTCourse(items)
        .then(function(data) {
          if (data.error){
            $uibMsgbox.error(data.message);
            return false;
          }
          if(!data.data){
            $uibMsgbox.error('未绑定主场课程，请重新绑定!');
            return false;
          } else {
            if(checkDateValidation(vm.showSelectCourseClass)) {
              $uibMsgbox.error('课次时间不一致，请重新检查!');
              return ;
            }
            $uibModalInstance.close(vm.showSelectCourseClass);
          }
        }, function(error) {
          $uibMsgbox.error(error.message || '请求失败！');
        })
    }

    function search(key) {
      if (!key) {
        $uibMsgbox.error('请填写课程标题');
        return ;
      }
      angular.extend(vm.queryParams, {courseName: key, limit: 10, start: 0})
      getCourseClassList(vm.queryParams)
        .then(function() {
          $log.info('search success: ' + key);
        })
    }

    function toggleCourseClass(course) {
      if (course && course.open) {
        course.open = !course.open;
      } else {
        course['open'] = true;
      }
    }

    function isSelected(courseClass) {
      var origialIndex = -1;
      return vm.showSelectCourseClass.some(function(item) {
        return isMyself(item, courseClass)
      })
    }

    function select(item, parentId, parentTitle) {
      var item1 = angular.copy(item)
      var origialIndex
      origialIndex = vm.positionOfChild(vm.showSelectCourseClass, parentId);
      if (origialIndex === -1) {
        if (parentTitle) {
          item1.classTitle = parentTitle + ' - ' + item1.classTitle
        }
        vm.showSelectCourseClass.push(item1);
      } else {
        if (isMyself(vm.showSelectCourseClass[origialIndex], item1)) {
          vm.showSelectCourseClass.splice(origialIndex, 1);
        } else if (isBrother(vm.showSelectCourseClass[origialIndex], item1)) {
          if (parentTitle) {
            item1.classTitle = parentTitle + ' - ' + item1.classTitle
          }
          vm.showSelectCourseClass[origialIndex] = item1;
        }
      }

    }

    function isBrother(item, another) {
      return item.parentId === another.parentId
    }

    function isMyself(item, another) {
      return (item.parentId === another.parentId) && (item.seq === another.seq)
    }

    function deleteCourseClass(item) {
      var index = vm.showSelectCourseClass.indexOf(item);
      if (index > -1) {
        item.selected = false;
        vm.showSelectCourseClass.splice(index, 1);
      }
    }

    function positionOfChild(items, parentId) {
      var origialIndex = -1;
      angular.forEach(items, function(item, index) {
        if (item.parentId === parentId) {
          origialIndex = index;
        }
      })
      return origialIndex;
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
        vm.showSelectCourseClass = [];
      });
    }

  }

  frClassWorkModalService.$inject = ['$http', '$log'];
  function frClassWorkModalService($http, $log) {
    return {
      get: get,
      checkMTCourse: checkMTCourse
    }

    function get(params) {
      return $http.post('/erp/myCourse/searchCourseClassList', params || {})
        .then(getCourseClassListComplete)
        .catch(getCourseClassListFailed);

      function getCourseClassListComplete(response) {
        return response.data;
      }

      function getCourseClassListFailed(error) {
        $log.error('XHR Failed for CourseClassList.' + error.data);
      }
    }

    function checkMTCourse(params) {
      return $http.post('/erp/work/checkMTCourse', params || {})
      .then(checkMTCourseComplete)
      .catch(checkMTCourseFailed);

      function checkMTCourseComplete(response) {
        return response.data;
      }

      function checkMTCourseFailed(error) {
        $log.error('XHR Failed for CourseClassList.' + error.data);
      }
    }
  }
})();