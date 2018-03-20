/**
 * Created by hengshan.ou on 2017/1/16.
 */

"use strict";
angular.module('ework-ui').controller('erp_OrdersIndexActivityOnlineController', [
  '$rootScope',
  '$scope',
  '$state',
  '$log',
  '$uibMsgbox',
  'erp_activityService',
  function(
    $rootScope,
    $scope,
    $state,
    $log,
    $uibMsgbox,
    erp_activityService
  ) {
    // 操作类型，list: 列表，add: 添加，edit: 编辑
    $scope.optype = 'list'
    // 优惠券列表（普通活动）
    $scope.selectedCouponList = []
    // 背景图片列表（暂存）
    $scope.bgImgUrlList = []
    // 旧的图片列表（用于在编辑时，向后端传需要删除的图片列表）
    $scope.oldImgUrlList = []
    // 分享图片列表（暂存，目前分享只支持单张图片）
    $scope.shareImgList = []
    // 活动详情
    $scope.activityDetail = {}
    // 活动列表
    $scope.activityList = []
    // 活动列表查询条件
    $scope.searchParams = {
      name: '',
      type: '',
      buId: '',
      branchId: ''
    }
    // 活动列表分页配置
    $scope.pageConf = {
      totalItems: 0,
      currentPage: 1,
      itemsPerPage: 10,
      onChange: function () {
        $scope.getList();
      }
    }

    function initActivityDetail (detail) {
      detail = detail || {}
      $scope.activityDetail.id = detail.id || 0
      $scope.activityDetail.type = (detail.type || '1').toString()
      $scope.activityDetail.cityId = detail.cityId || ''
      $scope.activityDetail.buId = detail.buId || ''
      $scope.activityDetail.activityBranchRefs = detail.activityBranchRefs || []
      $scope.activityDetail.name = detail.name
      $scope.activityDetail.activityCouponRefs = detail.activityCouponRefs || []
      $scope.activityDetail.bgColor = detail.bgColor || '#f1f0f3'
      $scope.activityDetail.startDate = detail.startDate || ''
      $scope.activityDetail.endDate = detail.endDate || ''
      $scope.activityDetail.activityBgIMGRefs = detail.activityBgIMGRefs || []
      $scope.activityDetail.delActivityBgIMGRefs = []
      $scope.activityDetail.shareTitle = detail.shareTitle 
      $scope.activityDetail.shareDesc = detail.shareDesc 
      $scope.activityDetail.shareLink = detail.shareLink || ''
      $scope.activityDetail.shareImg = detail.shareImg 
      $scope.activityDetail.nuserCouponId=detail.nuserCouponId 
      $scope.activityDetail.ouserCouponId=detail.ouserCouponId 
    }

    /**
     * 获取活动列表
     * @return {[type]} [description]
     */
    $scope.getList = function () {
      $scope.loadStatues = true;
      var params = {}
      angular.extend(params, {
        currentPage: $scope.pageConf.currentPage,
        pageSize: $scope.pageConf.itemsPerPage
      }, $scope.searchParams)

      erp_activityService.query(params).$promise.then(function (resp) {
        $scope.loadStatues = false;
          $scope.pageConf.totalItems = resp.total
          $scope.activityList = resp.data
        }, function (resp) {
          $scope.loadStatues = false;
          console.log(resp)
          $uibMsgbox.error(resp.message || '获取活动列表失败！')
        })
    }

    // 初始化Scope的数据
    function initDetailPageData(item) {
      $scope.bgImgUrlList = []
      $scope.oldImgUrlList = []
      $scope.shareImgList = []
      $scope.selectedCouponList = []
      initActivityDetail(item);
    }

    /**
     * 添加活动
     */
    $scope.addActivity = function() {
      initDetailPageData()
      $scope.optype = 'add'
    }

    /**
     * 编辑活动
     * @param  {[type]} item [description]
     * @return {[type]}      [description]
     */
    $scope.editActivity = function (item) {
      initDetailPageData(item)
      $scope.bgImgUrlList = _.flatMap($scope.activityDetail.activityBgIMGRefs, function (item) {
        return item.imgUrl
      })
      $scope.selectedCouponList = _.flatMap($scope.activityDetail.activityCouponRefs, function (item) {
        return {
          id: item.couponId,
          name: item.couponName,
          type_name: item.couponTypeName
        }
      })
      $scope.oldImgUrlList = _.cloneDeep($scope.bgImgUrlList)
      $scope.activityDetail.shareImg && $scope.shareImgList.push($scope.activityDetail.shareImg)
      $scope.optype = 'edit'
    }

    /**
     * 保存活动（新增和编辑两种类型）
     * @return {[type]} [description]
     */
    $scope.saveActivity = function () {
      $scope.activityDetail.activityBgIMGRefs = [];
      $scope.activityDetail.activityBgIMGRefs = _.map($scope.bgImgUrlList, function (item) {
        return {imgUrl: item}
      })
      $scope.activityDetail.activityCouponRefs = _.map($scope.selectedCouponList, function (item) {
        return {couponId: item.id}
      })
      _.forEach($scope.shareImgList, function (url) {
        $scope.activityDetail.shareImg = url;
      })
      if ($scope.activityDetail.startDate > $scope.activityDetail.endDate ) {
        $uibMsgbox.error("开始时间不能大于结束时间！");
        return;
      }
      var waitingModal = $uibMsgbox.waiting('保存中，请稍候...')
      if($scope.optype == 'add'){
        erp_activityService.post($scope.activityDetail, function (resp) {
          waitingModal.close()
          if (!resp.error) {
            $scope.optype = 'list';
            $scope.getList();
          } else {
            $uibMsgbox.error(resp.message)
          }
          return resp;
        });
      }else{
        var delImgUrlList = _.difference($scope.oldImgUrlList, $scope.bgImgUrlList)
        $scope.activityDetail.delActivityBgIMGRefs = _.map(delImgUrlList, function (item) {
          return {imgUrl: item}
        })
        erp_activityService.put($scope.activityDetail, function (resp) {
          waitingModal.close()
          if (!resp.error) {
            $scope.optype = 'list';
            $scope.getList();
          } else {
            $uibMsgbox.error(resp.message)
          }
          return resp;
        });
     }
    }

    /**
     * 取消活动编辑
     * @return {[type]} [description]
     */
    $scope.cancelActivity = function () {
      $scope.optype = 'list'
    }

    /**
     * 删除活动
     * @param  {[type]} item [description]
     * @return {[type]}      [description]
     */
    $scope.changeStatus = function(item, status) {
      var opWords = ['删除', '启用', '停用']
      $uibMsgbox.confirm('确认' + opWords[status] + '所选的活动？', function (res) {
        if (res == 'yes') {
          erp_activityService.changeStatus({
            ids:item.id,
            status: status
          }, function (resp) {
            if (!resp.error) {
              $scope.getList()
            } else {
              $uibMsgbox.error(resp.message)
            }
          })
        }
      })
    }


    /**
     * 活动详情
     */
    $scope.insertCoupon = function (coupon) {
      if (!_.some($scope.selectedCouponList, {id: coupon.id})) {
        $scope.selectedCouponList.push(coupon)
      }
    }

    /**
     * 移除优惠券
     */
    $scope.removeCoupon = function (coupon) {
      _.remove($scope.selectedCouponList, {id: coupon.id})
    }

    /**
     * 页面加载后的初始化操作
     */
    $scope.getList()

    $scope.copyToClipboard = function (text) {
      superClipBoard.copy(text, {
        success: function () {
          alert('复制成功！')
        },
        error: function () {
          alert('复制失败！')
        }
      })
    }
  }
]);
