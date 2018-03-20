angular.module('ework-ui')
  .directive('klCouponDropdown', ['erp_ebCouponService', '$uibMsgbox', klCouponDropdown])

function klCouponDropdown(
  erp_ebCouponService,
  $uibMsgbox
) {
  return {
    restrict: 'EA',
    transclude: true,
    scope: {
      multi: '=?',
      klSelected: '&'
    },
    template: '<div class="input-group kl-choose-teacher" '
        + 'auto-close="disabled" uib-dropdown is-open="isopen" on-toggle="onToggle(open)"> '
        + '<div uib-dropdown-menu class="dropdown-menu {{floatdir}}" style="width: 420px;"> '
          + '<form class="form" align="center" style="margin-bottom: 15px;"> '
            + '<div class="form-group"> '
              + '<div class="input-group">'
              + '<input type="text" class="form-control" ng-model="search_info" ng-change="changeSearchInfo()" /> '
              + '<span class="input-group-btn" ng-click="changeSearchInfo()"><button class="btn btn-default"><i class="fa fa-search"></i></button></span>'
              + '</div>'
            + '</div> '
          + '</form> '
          + '<div class="list-wrapper" >'
           + '<div class="text-center" '
              + 'ng-if="isLoading != \'isLoading\' && (!couponList || !couponList.length)"> '
              + '<span>暂无优惠券</span>'
            + '</div> '
            +'<table class="table table-hover" ng-if="isLoading != \'isLoading\' && couponList && couponList.length"> '
            + '<tr ng-repeat="coupon in couponList"> '
              +'<td class="cur" uib-popover="优惠券名称：{{coupon.name}}，优惠券编码：【{{coupon.encoding}}】，优惠券类型：【{{coupon.type_name}}】" '
              +' popover-trigger="\'mouseenter\'" ng-click="selectedCoupon(coupon)">'
                + ' {{coupon.name}} 【{{coupon.type_name}}】'
              +'</td> '
            + '</tr> '
            + '<tr ng-if="isLoading == \'isLoading\'"> '
              + '<td align="center"><img src="img/erp/loading2.gif"></td> '
            + '</tr> '
          + '</table>'
          + '</div>'
          + '<div class="row" ng-show="multi == true">'
            +'<div class="col-sm-12 text-center">'
            +   '<button class="btn btn-danger btn-sm">取消</button> '
            +'<button class="btn btn-primary btn-sm">确定</button>'
            +'</div>'
          +'</div>'
        + '</div> '
        + '<span class="input-group-btn" uib-dropdown-toggle ng-transclude> '
        + '</span> '
      + '</div> ',
    link: function (scope, element, attrs) {
      scope.searchparam = scope.searchparam || {}
      if (attrs.coursetype) {
        scope.searchparam.business_type = attrs.coursetype
      }
      scope.floatdir = attrs.floatdir == 'pull-left' ? 'pull-left':'pull-right' 
      scope.isopen = false
      scope.search_info = ''
      scope.couponList = []
      scope.isLoading = ''
      scope.choosenCouponList = []

      // TODO 优化右边滚动条 baiqb@klxuexi.org

      scope.changeSearchInfo = function () {
        scope.searchparam.p_name = scope.search_info
        // scope.searchparam.status=1;
        scope.searchparam.pageSize=100;
        scope.isLoading = 'isLoading';
        erp_ebCouponService.query(scope.searchparam,function(resp){
          scope.isLoading = '';
          if(!resp.error){
            scope.couponList = resp.data;
          }else{
            $uibMsgbox.error(resp.message);
          }
        })
      },

      // 在指令中，调用父scope方法，传参需要以对象的形式传入
      // 对象传入的属性名称和controller中方法传入的参数名称一致
      scope.selectedCoupon = function (coupon) {
        if (!scope.multi) {
          scope.klSelected({
            coupon: coupon
          })
          scope.isopen = false
          return
        }

        if (_.findIndex(scope.choosenCouponList, coupon) != -1) {
          scope.choosenCouponList.push(coupon)
        } else {
          _.remove(scope.choosenCouponList, course)
        }
      }
      
      scope.changeSearchInfo()
    }
  }
}