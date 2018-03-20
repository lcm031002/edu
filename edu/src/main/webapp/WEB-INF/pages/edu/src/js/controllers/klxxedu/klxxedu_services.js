/**
 * Created by Liyong.zhu on 2017/1/15.
 */
'use strict';

//登出服务
angular.module('ework-ui').factory('LogoutService', [ '$resource', LogoutService ]);

//员工查询服务
angular.module('ework-ui').factory('klxx_EmployeeService', [ '$resource', klxx_EmployeeService ]);

//账户服务
angular.module('ework-ui').factory('klxx_accountService', [ '$resource', klxx_accountService ]);

/**
 * 账户查询服务
 * @param $resource
 * @returns {*}
 */
function klxx_accountService($resource){
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
function klxx_EmployeeService($resource){
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
