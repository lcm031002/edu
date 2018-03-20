/**
 * 优惠券下拉框
 */
angular.module('ework-ui').directive('klCouponSelect', [
  'erp_ebCouponService',
  klCouponSelect
])

function klCouponSelect(
  erp_ebCouponService
) {
  return {
    restrict: 'EA',
    replace: true,
    scope: {
      coupon: '='
    },
    template: '<select class="form-control" '
    + ' ng-model="coupon" ng-options="coupon.id as coupon.name for coupon in couponList">'
    + '</select>',
    link: function (scope, element, attrs) {
      console.log(scope.coupon)
      scope.couponList = []
      erp_ebCouponService.query({
        currentPage: 1,
        pageSize: 999
      }, function (resp) {
        if (!scope.error) {
          scope.couponList = resp.data
        }
      })
    }
  }
}