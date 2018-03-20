/**
 * Created by hengshan.ou on 2017/1/16.
 */

"use strict";
angular.module('ework-ui').controller('erp_OrdersIndexCouponOnlineController', [
    '$rootScope',
    '$scope',
    '$state',
    '$log',
    '$uibMsgbox',
    '$uibModal',
    'erp_ebCouponService',
    function(
        $rootScope,
        $scope,
        $state,
        $log,
        $uibMsgbox,
        $uibModal,
        erp_ebCouponService
    ) {
        $scope.optype = 'list'
        $scope.coupon = createCoupon();
        $scope.couponList = []
        $scope.searchParam = {
            name: ''
        }
        $scope.pageConf = {
            totalItems: 0,
            currentPage: 1,
            itemsPerPage: 10,
            onChange: function() {
                $scope.getList();
            }
        }

        $scope.fieldInfos = [{
            fieldName: "生成数量",
            field: "totals",
            notAllowNegative: false,
            negativeVal: -1,
            isInteger: true
        }, {
            fieldName: "立减金额",
            field: "rate",
            notAllowNegative: true,
            isInteger: true,
            couponType: "cut"
        }, {
            fieldName: "优惠券启用价格",
            field: "amount_lower",
            notAllowNegative: true
        }, {
            fieldName: "优惠券抵扣上限",
            field: "discount",
            notAllowNegative: true,
            couponType: "discount"
        }, {
            fieldName: "课程数量",
            field: "course_num",
            notAllowNegative: true,
            isInteger: true
        }, {
            fieldName: "有效期",
            field: "validity",
            notAllowNegative: true,
            isInteger: true
        }];

        function createCoupon(coupon) {
            return {
                id: '', // 优惠券ID
                encoding: '', // 优惠券编码
                name: '', // 优惠券名称
                type: 'cut', // 优惠券类型 discount 打折 or cut 立减
                rate: 0, // 折扣或立减金额
                totals: 50, // 生成数量
                validity: 15, // 有效期限
                amount_lower: 0.0, // 订单下限
                discount: 0.0, // 优惠上限
                course_num: 1, // 课程数量
                fit: 'all', // 适用对象， all-所有人，old-老用户，new-新用户，uId- 用户ID
                is_share: 0, // 是否优惠共用 0-不共用，1-共用 默认不共用（已废弃）
                status: 3, // 状态：0-删除， 1-使用， 2-停用， 3-未使用
                describe: '', // 优惠说明
                all_course: 'N', // 应用于所有课程
                // uId: '',
                couponCourseList: [] // 课程列表 [{course_id: ''}]
            }
        }

        /**
         * 获取优惠券列表
         * @return {[type]} [description]
         */
        $scope.getList = function() {
            $scope.loadStatues = true;
            var params = {}
            angular.extend(params, {
                currentPage: $scope.pageConf.currentPage,
                pageSize: $scope.pageConf.itemsPerPage
            }, $scope.searchParam)
            erp_ebCouponService.query(params)
                .$promise.then(function(resp) {
                    $scope.loadStatues = false;
                    if (!resp.error) {
                        $scope.couponList = resp.data;
                        $scope.pageConf.totalItems = resp.total
                    } else {
                        $uibMsgbox.error(resp.message)
                    }
                }, function(resp) {
                    console.log(resp)
                })
        }

        $scope.handleAddCouponOnline = function() {
            $scope.coupon = createCoupon();
            $scope.optype = 'add'
        }

        $scope.handleAddCouponCourse = function() {
            $uibModal.open({
                size: 'xlg',
                backdrop: 'static',
                templateUrl: 'templates/block/modal/course-multiple-select.modal.html',
                controller: 'modal_courseMultiSelectController',
                resolve: {
                    selectedCourseList: function() {
                        return $scope.coupon.couponCourseList
                    }
                }
            }).result.then(function(courseList) {
                _.forEach(courseList, function(course) {
                    if (!_.some($scope.coupon.couponCourseList, { course_id: course.id })) {
                        $scope.coupon.couponCourseList.push({
                            course_id: course.id,
                            course: course
                        })
                    }
                })
            })
        }

        $scope.removeCouponCourse = function(course) {
            _.remove($scope.coupon.couponCourseList, { course_id: course.course_id })
        }

        $scope.checkFieldInfo = function(fieldInfo, coupon) {
            if (fieldInfo.couponType && coupon.type != fieldInfo.couponType) {
                return true;
            }

            var fieldVal = coupon[fieldInfo.field];
            if (!fieldVal) {
                $uibMsgbox.error("请输入" + fieldInfo.fieldName + "！");
                return false;
            }

            if (fieldInfo.notAllowNegative && fieldVal < 0) {
                $uibMsgbox.error(fieldInfo.fieldName + "不能输入负数！");
                return false;
            } else if (!fieldInfo.notAllowNegative && fieldVal < 0 && fieldInfo.negativeVal &&
                fieldInfo.negativeVal != fieldVal) {
                $uibMsgbox.error(fieldInfo.fieldName + "负数只能输入" + fieldInfo.negativeVal + "！");
                return false;
            }

            if (fieldInfo.isInteger && !Validator.intege.test(fieldVal)) {
                $uibMsgbox.error(fieldInfo.fieldName + "必须为整数！");
                return false;
            }
            return true;
        }

        /**
         * 保存优惠券
         * @return {[type]} [description]
         */
        $scope.saveCoupon = function() {
            var coupon = genCouponRequestParam();
            if (!coupon.name) {
                $uibMsgbox.error("请输入优惠券名称！");
                return false;
            }

            for (var i = 0; i < $scope.fieldInfos.length; i++) {
                if (!$scope.checkFieldInfo($scope.fieldInfos[i], coupon)) {
                    return false;
                }
            }

            if (coupon.type == 'discount' && coupon.rate != 0 && !coupon.rate) {
                $uibMsgbox.error("请输入折扣！");
                return false;
            } else if (coupon.type == 'discount' && (coupon.rate < 0 || coupon.rate > 1)) {
                $uibMsgbox.error("折扣值必须在[0, 1]区间！");
                return false;
            }

            if (coupon.type == 'cut' && (coupon.amount_lower - coupon.rate) < 0) {
                $uibMsgbox.error("立减金额不能超过优惠券启用价格！");
                return false;
            }

            if (coupon.all_course == 'N' && (!coupon.couponCourseList || coupon.couponCourseList.length == 0)) {
                $uibMsgbox.error("请至少添加一个课程！");
                return false;
            }

            if (coupon.all_course == 'N' && coupon.course_num && coupon.course_num > coupon.couponCourseList.length) {
                $uibMsgbox.error("添加的课程数不能小于课程数量设置值！");
                return false;
            }

            if (coupon.all_course == 'Y' && coupon.course_num != 1) {
                $uibMsgbox.error("应用于所有课程，课程数量必须设为1！");
                return false;
            }

            if (!coupon.describe) {
                $uibMsgbox.error("请输入优惠说明！");
                return false;
            } else {
                var descArr = coupon.describe.split("\n");
                if (descArr.length > 2) {
                    $uibMsgbox.error("优惠说明最多只能输入两行！");
                    return false;
                }

                for (var i = 0; i < descArr.length; i++) {
                    if (descArr[i].length > 16) {
                        $uibMsgbox.error("优惠说明每行最多只能输入16个字符！");
                        return false;
                    }
                }
            }

            if ($scope.optype == 'add') {
                erp_ebCouponService.add(coupon).$promise.then(function(resp) {
                    if (!resp.error) {
                        $uibMsgbox.success('添加成功！', function(res) {
                            $scope.coupon = createCoupon();
                            $scope.optype = 'list';
                            $scope.getList();
                        })
                    } else {
                        $uibMsgbox.error(resp.message)
                    }
                }, function(resp) {
                    console.log(resp)
                })
            } else if ($scope.optype == 'edit') {
                erp_ebCouponService.update(coupon).$promise.then(function(resp) {
                    if (!resp.error) {
                        $uibMsgbox.success('修改成功！', function(res) {
                            $scope.coupon = createCoupon();
                            $scope.optype = 'list';
                            $scope.getList();
                        })
                    } else {
                        $uibMsgbox.error(resp.message)
                    }
                }, function(resp) {
                    console.log(resp)
                })
            }
        }

        function genCouponRequestParam() {
            var couponCopy = _.cloneDeep($scope.coupon)
            var couponCourseList = []
            _.forEach(couponCopy.couponCourseList, function(item) {
                couponCourseList.push({
                    course_id: item.course.id
                })
            })

            return {
                id: couponCopy.id,
                encoding: couponCopy.encoding || '',
                name: couponCopy.name,
                type: couponCopy.type,
                rate: couponCopy.rate,
                totals: couponCopy.totals,
                validity: couponCopy.validity,
                amount_lower: couponCopy.amount_lower,
                discount: couponCopy.discount,
                course_num: couponCopy.course_num,
                fit: couponCopy.fit,
                is_share: couponCopy.is_share,
                status: couponCopy.status,
                describe: couponCopy.describe,
                all_course: couponCopy.all_course,
                couponCourseList: couponCourseList
            }
        }

        $scope.editCoupon = function(coupon) {
            $scope.coupon = _.cloneDeep(coupon);
            $scope.allCourseCoupon = ($scope.coupon.all_course == 'Y');
            $scope.optype = 'edit'
        }

        $scope.cancelCoupon = function() {
            $scope.optype = 'list'
        }

        $scope.putStatus = function(coupon, status) {
            var opNames = ['删除', '启用', '停用']
            $uibMsgbox.confirm('确定' + opNames[status] + '该优惠券？', function(res) {
                if (res == 'yes') {
                    erp_ebCouponService.changeStatus({
                        id: coupon.id,
                        status: status
                    }).$promise.then(function(resp) {
                        if (!resp.error) {
                            $uibMsgbox.success(resp.message || '操作成功')
                            $scope.getList();
                        } else {
                            $uibMsgbox.error(resp.message)
                        }
                    }, function(resp) {
                        console.log(resp)
                        $uibMsgbox.error('请求，错误码：' + resp.status + '，请联系管理员！')
                    })
                }
            })
        }

        $scope.onCheckAllCourse = function() {
            if ($scope.allCourseCoupon) {
                $scope.coupon.all_course = 'Y';
                $scope.coupon.course_num = 1;
                $scope.coupon.couponCourseList = [];
            } else {
                $scope.coupon.all_course = 'N';
            }
        }
        $scope.getList()
    }
]);