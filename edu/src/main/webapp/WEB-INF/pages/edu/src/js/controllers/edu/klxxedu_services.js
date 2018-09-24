/**
 * Created by Liyong.zhu on 2017/1/15.
 */
'use strict';

//登出服务
angular.module('ework-ui').factory('LogoutService', [ '$resource', LogoutService ]);

//员工查询服务
angular.module('ework-ui').factory('edu_EmployeeService', [ '$resource', edu_EmployeeService ]);

//账户服务
angular.module('ework-ui').factory('edu_accountService', [ '$resource', edu_accountService ]);

/**
 * 账户查询服务
 * @param $resource
 * @returns {*}
 */
function edu_accountService($resource){
    return $resource('/erp/common/accountservice', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 员工服务
 * @param $resource
 * @returns {*}
 */
function edu_EmployeeService($resource){
    return $resource('/common/employeeservice/list', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 登出
 * @param $resource
 * @returns {*}
 * @constructor
 */
function LogoutService($resource){
    return $resource('/common/logout', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}
