(function() {
  "use strict";

  angular.module('ework-ui')
    .controller('frWorkMgtSetCtrl', frWorkManagementSettingCtrl)
    .service('frWorkMgtSet', frWorkMgtSetService)

  frWorkManagementSettingCtrl.$inject = ['$scope', '$log', 'frWorkMgtSet', '$uibModal', '$stateParams', '$state', '$uibMsgbox'];
  function frWorkManagementSettingCtrl($scope, $log, frWorkMgtSet, $uibModal, $stateParams, $state, $uibMsgbox) {
    window.mgtSet = $scope.mgtSet;
    var vm = this;
    angular.extend(vm, {$stateParams})
    //- init variable
    vm.title = "绑定课次";
    vm.workId = $stateParams.id;    
    //- method
    vm.activate = activate;
    vm.back = back;
    vm.openDialog = openDialog;
    vm.delete = deleteCourseClass;
    vm.queryClassWork = queryClassWork;
    vm.bindClassWork = bindClassWork;
    vm.getSpecialParams = getSpecialParams;
    vm.submitLoading = false;
    //- 启动
    activate();

    function activate() {
      vm.loading = true;
      return queryClassWork(vm.workId).then(function() {
        $log.info('queryClassWork success!');
        vm.loading = false;
      })

    }

    function queryClassWork(id) {
      var params = {
        workId: id
      }
      return frWorkMgtSet.query(params)
        .then(function(data) {
          if (parseInt(data.status) === 200) {
            vm.classWorkInfo = data.data.classWorkInfo;
            //- 这里需要将字符串字符转化为标准的时间格式
            //- 发布时间是不给编辑，有选择的课次默认设置
            vm.dateDisabled = true;
            if (vm.classWorkInfo.workStartTime) {
              vm.date = new Date(vm.classWorkInfo.workStartTime);
              vm.time = new Date(vm.classWorkInfo.workStartTime).format("yyyy-MM-dd hh:mm").split(' ')[1];
            }
            if (vm.classWorkInfo.workEndTime) {
              vm.endDate = new Date(vm.classWorkInfo.workEndTime);
              vm.endTime = new Date(vm.classWorkInfo.workEndTime).format("yyyy-MM-dd hh:mm").split(' ')[1];
            }
            // 如果是课前作业，允许修改开始时间
            if (vm.classWorkInfo.type == 'PRE_CLASS' || vm.classWorkInfo.type == 'EXAM') {
              vm.dateDisabled = false;
            }
            // 如果作业类型是课后作业，才允许修改结束时间
            if(vm.classWorkInfo.type === 'AFTER_CLASS' || vm.classWorkInfo.type == 'EXAM') {
              vm.endDateDisabled = false;
            } else {
              vm.endDateDisabled = true;
            }
            return
          }
        }, function(error) {
          console.log(error);
        })
    }

    /**
     * 根据作业类型配置开始于结束时间
     * @params {作业类型} type 
     * @params {} date: Date
     * @return time
     */
    function timeRuleConfigByType(date, type, start, end) {
      switch(type) {
        case 'PRE_CLASS': {
          return {
            startTime: start,
            endTime: start,
            endDate: new Date(new Date(date).getTime() + 3*24*3600*1000)
          }
        }
        case 'ENTRY_TEST':
        case 'CLASS_TEST':
        case 'IN_CLASS':
        case 'EXAM': {
          var date1 = conputeTime(date, start, 15, false)
          var date2 = conputeTime(date, end, 120, true)
          return {
            startTime: date1.time,
            startDate: date1.startDate,
            endTime: date2.time,
            endDate: date2.endDate
          }
        }
        case 'AFTER_CLASS': {
          var date = date.format('yyyy-MM-dd')
          // end > 22: 00 ,tomorrow 22:00
          if ( Date.parse(new Date(date + ' ' + end)) > Date.parse(new Date(date + ' 22:00')) ) {
            var endDateUnix = Date.parse(new Date(date + ' ' + end)) + 24*60*60*1000
            var endDate = new Date(endDateUnix).format('yyyy-MM-dd')
            console.log(endDate)
            return {
              startTime: end,
              endTime: '22:00',
              startDate: date,
              endDate: endDate
            }
          } else {
            return {
              startTime: end,
              endTime: '22:00',
              startDate: date,
              endDate: date
            }
          }

        }
      }
    }

    // periodTime 分钟
    function conputeTime(date, time, periodTime, plus) {
      var date1 = date.format('yyyy-MM-dd')
      var timestamp
      var format, hour, min
      if(!plus) {
        timestamp = Date.parse(new Date(date1 + ' ' + time)) - periodTime * 60 * 1000;
      } else {
        timestamp = Date.parse(new Date(date1 + ' ' + time)) + periodTime * 60 * 1000;
      }
      var endDate = new Date(timestamp)
      hour = endDate.getHours()
      hour = hour < 10 ? '0' + hour : hour
      min = endDate.getMinutes()
      min = min < 10 ? '0' + min : min
      if (!plus) {
        return {
          startDate: endDate.format('yyyy-MM-dd'),
          time: hour + ':' + min
        }
      } else {
        return {
          endDate: endDate.format('yyyy-MM-dd'),
          time: hour + ':' + min
        }
      }
    }

    function back() {
      history.back();
    }

    function bindClassWork() {
      //- 这里是绑定课次请求
      if (!vm.date || !vm.endDate) {
        $uibMsgbox.error('请选择时间');
        return '';
      }
      vm.submitLoading = true;
      var params = {
        workId: vm.workId,
        workStartTime: vm.date.split(' ')[0] + ' ' + vm.time,
        workEndTime: vm.endDate.split(' ')[0] + ' ' + vm.endTime,
        productCode: vm.$stateParams.productCode,
        courseIds: vm.getSpecialParams(vm.classWorkInfo.classList || [], 'parentId'),
        seqs: vm.getSpecialParams(vm.classWorkInfo.classList || [], 'seq')
      };

      return frWorkMgtSet.bindClassWork(params)
        .then(function(data) {
          vm.submitLoading = false;
          if (data.error) {
            $uibMsgbox.error(data.message || '绑定失败');
          } else {
            $state.go('workManagement');
          }
        }, function(error) {
          vm.submitLoading = false;
          $uibMsgbox.error(error.message || '绑定失败');
        })
    }

    function getSpecialParams(items, key) {
      if (angular.isArray(items) && items.length > 0) {
        return items.map(function(item) {
          return item[key];
        }).join(',');
      }
      return '';
    }

    //- 打开绑定课次，选择需要绑定的课次
    function openDialog() {

      $uibModal.open({
        size: 'lg',
        templateUrl: 'templates/block/modal/class-work-modal.html',
        controller: 'frClassWorkModalCtrl',
        controllerAs: 'classWorkModal',
        resolve: {
          items: function() {
            return vm.classWorkInfo.classList;
          }
        }
      }).result.then(function (data) {
        vm.classWorkInfo.classList = data;
        // 根据里面的时间去除对应的课程时间段
        if(data.length) {
          if (vm.classWorkInfo.type == 'PRE_CLASS') {
            vm.date = new Date(new Date(data[0].startDate).getTime() - 3*24*3600*1000);
          } else {
            vm.date = new Date(data[0].startDate)
          }
          vm.endDate = new Date(data[0].startDate)
          var dateObj = timeRuleConfigByType(vm.date, vm.classWorkInfo.type, data[0].startTime, data[0].endTime)
          vm.time = dateObj.startTime
          vm.endTime = dateObj.endTime
          vm.endDate = dateObj.endDate || vm.date
          vm.date = dateObj.startDate || vm.date
        }
      }, function () {});
    }

    function deleteCourseClass(items, index) {
      return items.splice(index, 1);
    }

  }

  frWorkMgtSetService.$inject = ['$http', '$log', '$q'];
  function frWorkMgtSetService($http, $log, $q) {
    return {
      query: query,
      bindClassWork: bindClassWork
    }

    function query(params) {
      //- 查询课次绑定信息
      return $http.get('/erp/work/queryClassWork', {params: params})
        .then(queryComplete)
        .catch(queryFailed);

      function queryComplete(response) {
        return response.data;
      }

      function queryFailed(error) {
        $log.error('XHR Failed for courseClassSet.' + error);
        $q.reject(error);
      }
    }

    function bindClassWork(params) {
      return $http.post('/erp/work/bindClassWork', params || {})
        .then(bindClassWorkComplete)
        .catch(bindClassWorkFailed);

      function bindClassWorkComplete(response) {
        return response.data;
      }

      function bindClassWorkFailed(error) {
        $log.error('XHR Failed for ClassWork ' + error);
        $q.reject(error);
      }
    }

    Date.prototype.format = function(fmt) { 
      var o = { 
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
      }; 
      if(/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
      }
      for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
      }
      return fmt; 
    }
  }

})();