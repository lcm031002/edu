/**
 * Alerts Controller
 */
"use strict";
angular
    .module('ework-ui')
    .controller('crm_IndexCtrl', ['$scope', crm_IndexCtrl]);

function crm_IndexCtrl($scope) {
    $scope.alerts = [{
        type: 'success',
        msg: '通知：个性化CRM系统最新上线，欢迎大家试用!'
    }, {
        type: 'danger',
        msg: '提醒：这周五需要完成寒假班的报班，注意及时进行考勤！'
    }];

    $scope.addAlert = function() {
        $scope.alerts.push({
            msg: 'Another alert!'
        });
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
}