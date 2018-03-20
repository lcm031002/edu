angular.module('ework-ui')
  .controller('erp_ordersActivityBannerController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    'PUBORGSelectedService',
    'erp_activityBannerService',
    erp_ordersActivityBannerController
  ])

function erp_ordersActivityBannerController(
  $rootScope,
  $scope,
  $state,
  $log,
  $uibMsgbox,
  PUBORGSelectedService,
  erp_activityBannerService
) {
  $scope.optype = 'list';
  $scope.bannerList = []
  $scope.bannerImgs = []
  $scope.banner = createBanner();
  $scope.searchParams = {
    buId: '',
    branchId: ''
  }
  $scope.pageConf = {
    totalItems: 0,
    currentPage: 1,
    itemsPerPage: 10,
    onChange: function () {
      $scope.getList()
    }
  }
  $scope.getList = function () {
    $scope.loadStatues = true;
    erp_activityBannerService.query({
      buId: $scope.searchParams.buId,
      branchId: $scope.searchParams.branchId,
      page: $scope.pageConf.currentPage,
      pageSize: $scope.pageConf.itemsPerPage
    }).$promise.then(function (resp) {
      $scope.loadStatues = false;
      if (!resp.error) {
        $scope.bannerList = resp.data
        $scope.pageConf.totalItems = resp.total
      } else {
        $uibMsgbox.error(resp.message)
      }
    }, function (resp) {
      $uibMsgbox.error('请求失败，请联系管理员，错误码：' + resp.status)
    })
  }
  $scope.changeStatus = function (banner, status) {
    var opWords = ['删除', '启用', '停用']
    $uibMsgbox.confirm('确定要' + opWords[status] + '此活动图片？', function (res) {
      if (res == 'yes') {
        erp_activityBannerService.changeStatus({
          ids: banner.id.toString(),
          status: status
        }).$promise.then(function (resp) {
          if (!resp.error) {
            $scope.getList()
          } else {
            $uibMsgbox.error(resp.message)
            $scope.getList()
          }
        }, function (resp) {
          $uibMsgbox.error('请求数据失败，错误码：' + resp.status)
        })
      }
    })
  }
  function createBanner(instance) {
    $scope.bannerImgs = []
    instance = instance || {}
    if (instance.imgUrl) {
      $scope.bannerImgs.push(instance.imgUrl)
    }
    return {
      id: instance.id || null,
      title: instance.title || '',
      imgUrl: instance.imgUrl || '',
      oldImgUrl: instance.oldImgUrl || '',
      linkUrl: instance.linkUrl || '',
      startDate: instance.startDate || '',
      endDate: instance.endDate || '',
      cityId: instance.cityId || null,
      buId: instance.buId || null,
      branchId: instance.branchId || null,
      sort: (instance.sort || 1).toString()
    }
  }

  $scope.addBanner = function () {
    $scope.banner = createBanner();
    $scope.optype = 'add'
  }

  $scope.editBanner = function (banner) {
    $scope.banner = createBanner(banner)
    $scope.banner.oldImgUrl = $scope.banner.imgUrl
    $scope.optype = 'edit'
  }

  $scope.saveBanner = function () {
    if ($scope.valid()) {
      $scope.banner.imgUrl = $scope.bannerImgs[0]
      var waitingModal = $uibMsgbox.waiting('保存中，请稍候...')
      if ($scope.optype == 'add') {
        erp_activityBannerService.post($scope.banner)
          .$promise.then(function (resp) {
            waitingModal.close()
            if (!resp.error) {
              $uibMsgbox.success('添加成功！')
              $scope.getList()
              $scope.optype = 'list'
            } else {
              $uibMsgbox.error(resp.message)
            }
          }, function (resp) {
            waitingModal.close()
            console.log('数据请求失败，错误码：' + resp.status)
          })
      }else{
         erp_activityBannerService.put($scope.banner)
          .$promise.then(function (resp) {
            waitingModal.close()
            if (!resp.error) {
              $uibMsgbox.success('修改成功！')
              $scope.getList()
              $scope.optype = 'list'
            } else {
              $uibMsgbox.error(resp.message)
            }
          }, function (resp) {
            waitingModal.close()
            console.log('数据请求失败，错误码：' + resp.status)
          })
      }
    }
  }

  $scope.deleteBanner = function(item) {
      $uibMsgbox.confirm('确认删除所选的图片？', function (res) {
        if (res == 'yes') {
          erp_activityBannerService.changeStatus({"ids":item.id,"status":0}, function (resp) {
          })
          $scope.getList()
        } else {}
      })
  }

  $scope.cancelEdit = function () {
    $scope.optype = 'list'
  }

  $scope.valid = function () {
    var note = $uibMsgbox.warn
    var b = $scope.banner
    if (!b.title) {
      note('请输入活动标题')
      return false
    }
    if (!b.linkUrl) {
      note('请输入跳转链接')
      return false
    }
    if (!$scope.bannerImgs.length) {
      note('请选择活动图片')
      return false
    }
    if (!b.startDate) {
      note('请选择开始时间')
      return false
    }
    if (!b.endDate) {
      note('请选择结束时间')
      return false
    }
    if (!b.buId) {
      note('请选择团队')
      return false
    }
    if (!b.sort) {
      note('请选择序号')
      return false
    }
    return true
  }
  PUBORGSelectedService.query({}, function(resp) {
    if (!resp.error) {
      $scope.searchParams.buId = resp.data.buId;
      $scope.getList()
    } else  {
      console.log(resp.message)
    }
  })
}
