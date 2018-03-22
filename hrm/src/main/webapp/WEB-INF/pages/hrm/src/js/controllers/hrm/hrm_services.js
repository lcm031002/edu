/**
 * 
 */

angular.module('ework-ui').factory('workflowNew', [ '$resource', workflowNew ]);

angular.module('ework-ui').factory('workflowQuery', [ '$resource', workflowQuery ]);

angular.module('ework-ui').factory('workflowComplete', [ '$resource', workflowComplete ]);

angular.module('ework-ui').factory('DictsService', [ '$resource', DictsService ]);

angular.module('ework-ui').factory('DictTypeItemService', [ '$resource', DictTypeItemService ]);

angular.module('ework-ui').factory('DictItemService', [ '$resource', DictItemService ]);

angular.module('ework-ui').factory('OrgService', [ '$resource', OrgService ]);

angular.module('ework-ui').factory('EmployeeExtService', [ '$resource', EmployeeExtService ]);

angular.module('ework-ui').factory('EmployeeManageService', [ '$resource', EmployeeManageService ]);

angular.module('ework-ui').factory('PostService', [ '$resource', PostService ]);

angular.module('ework-ui').factory('RoleService', [ '$resource', RoleService ]);

angular.module('ework-ui').factory('AccountService', [ '$resource', AccountService ]);

angular.module('ework-ui').factory('ChangeEventService', [ '$resource', ChangeEventService ]);

angular.module('ework-ui').factory('ChangeEvent_HtService', [ '$resource', ChangeEvent_HtService ]);

angular.module('ework-ui').factory('EmployeeCheckService', [ '$resource', EmployeeCheckService ]);


/**
 * 字典定义
 * @param $resource
 * @returns {*}
 * @constructor
 */
function DictsService($resource){
    return $resource('/hrm/hrmSystemSettings/hrmDicttype/dictservice', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

/**
 * 字典类型服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function DictTypeItemService($resource){
    return $resource('/hrm/hrmSystemSettings/hrmDicttype/dictservice', {}, {
        add : {
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}


/**
 * 字典子项服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function DictItemService($resource){
    return $resource('/hrm/hrmSystemSettings/hrmDicttype/dictservice/sub', {}, {
    	query : {
            method : 'GET',
            params : {},
            isArray : false
        },
    	add : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove:{
            method : 'DELETE',
            params : {},
            isArray : false
        },
        queryDictSubAll : {
        	url	:'/hrm/hrmSystemSettings/hrmDicttype/dictservice/suball',
            method : 'GET',
            params : {},
            isArray : false
        },
    });
}


/**
 * 组织机构
 * @param $resource
 * @returns {*}
 * @constructor
 */
function OrgService($resource){
    return $resource('/hrm/hrmSystemSettings/hrmOrgmgr/orgservice', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        //查询团队组织
        queryBu : {
        	url:'/hrm/hrmSystemSettings/hrmOrgmgr/orgservice/areas',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        //查询校区组织
        querySch : {
        	url:'/hrm/hrmSystemSettings/hrmOrgmgr/orgservice/schools',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
      //地址在组织机构
        queryOrgWithAccount:{
        	url:'/hrm/hrmSystemSettings/hrmAccountOrg/queryOrgWithAccount',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
	    add : {
	        method : 'POST',
	        params : {},
	        isArray : false
	    },
	    update : {
	        method : 'PUT',
	        params : {},
	        isArray : false
	    },
	    remove : {
	        method : 'DELETE',
	        params : {},
	        isArray : false
	    }
    });
}


/**
 * 员工档案定义
 * @param $resource
 * @returns {*}
 * @constructor
 */
function EmployeeExtService($resource){
    return $resource('/hrm/hrmEmployee/hrmEmployeeExt/employee/employeeextservice', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        
        queryField : {
        	url	 :  '/hrm/hrmEmployee/hrmEmployeeExt/employee/employeeextfieldservice',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
	    add : {
	        method : 'POST',
	        params : {},
	        isArray : false
	    },
	    update : {
	        method : 'PUT',
	        params : {},
	        isArray : false
	    },
	    remove : {
	        method : 'DELETE',
	        params : {},
	        isArray : false
	    },
	    queryAreas:{
	    	url : '/hrm/hrmEmployee/hrmEmployeeExt/employee/employeeextservice/areas',
	    	method : 'GET',
            params : {},
            isArray : false
	    }
    });
}

/**
 * 员工档案管理
 * @param $resource
 * @returns {*}
 * @constructor
 */
function EmployeeManageService($resource){
    return $resource('/hrm/employee/employeeservice', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryEmployeeForPage : 
        {
        	url:'/hrm/employee/employeeservice/page',
            method : 'POST',
            params : {},
            isArray : false
        },
        queryEmployeeInfo:{
        	url:'/hrm/employee/employeeservice/employeeInfo',
        	method : 'GET',
            params : {},
            isArray : false
        },
	    add : {
	    	method : 'POST',
	        params : {},
	        isArray : false
	    },
	    update : {
	    	method : 'PUT',
	        params : {},
	        isArray : false
	    },
	    queryEmployeeEdu:{
	    	url:'/hrm/employee/employeeservice/employeeInfo/edu',
	    	method : 'GET',
	    	params : {},
	    	isArray : false
	    },
	    addEmployeeEdu : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/edu',
	    	method : 'POST',
	    	params : {},
	    	isArray : false
	    },
	    updateEmployeeEdu : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/edu',
	    	method : 'PUT',
	    	params : {},
	    	isArray : false
	    },
	    deleteEmployeeEdu : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/edu',
	    	method : 'DELETE',
	    	params : {},
	    	isArray : false
	    },
	    
	    queryEmployeeExp:{
	    	url:'/hrm/employee/employeeservice/employeeInfo/exp',
	    	method : 'GET',
	    	params : {},
	    	isArray : false
	    },
	    addEmployeeExp : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/exp',
	    	method : 'POST',
	    	params : {},
	    	isArray : false
	    },
	    updateEmployeeExp : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/exp',
	    	method : 'PUT',
	    	params : {},
	    	isArray : false
	    },
	    deleteEmployeeExp : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/exp',
	    	method : 'DELETE',
	    	params : {},
	    	isArray : false
	    },
	    
	    queryEmployeeSum:{
	    	url:'/hrm/employee/employeeservice/employeeInfo/sum',
	    	method : 'GET',
	    	params : {},
	    	isArray : false
	    },
	    addEmployeeSum : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/sum',
	    	method : 'POST',
	    	params : {},
	    	isArray : false
	    },
	    updateEmployeeSum : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/sum',
	    	method : 'PUT',
	    	params : {},
	    	isArray : false
	    },
	    deleteEmployeeSum : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/sum',
	    	method : 'DELETE',
	    	params : {},
	    	isArray : false
	    },
	    
	    queryEmployeeRew:{
	    	url:'/hrm/employee/employeeservice/employeeInfo/rew',
	    	method : 'GET',
	    	params : {},
	    	isArray : false
	    },
	    addEmployeeRew : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/rew',
	    	method : 'POST',
	    	params : {},
	    	isArray : false
	    },
	    updateEmployeeRew : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/rew',
	    	method : 'PUT',
	    	params : {},
	    	isArray : false
	    },
	    deleteEmployeeRew : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/rew',
	    	method : 'DELETE',
	    	params : {},
	    	isArray : false
	    },

	    updateEmployeeStatic : {
	    	url:'/hrm/employee/employeeservice/employeeInfo/static',
	    	method : 'PUT',
	    	params : {},
	    	isArray : false
	    },

	  
    });
}

/**
 * 岗位服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function PostService($resource){
    return $resource('/hrm/common/postservice', {}, {
        queryPostForPage :
        {
            url: '/hrm/commonPage/postservice',
        	method : 'POST',
            params : {},
            isArray : false
        },
        queryPost:
        {
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        
	    add : {
	        method : 'POST',
	        params : {},
	        isArray : false
	    },
	    update : {
	        method : 'PUT',
	        params : {},
	        isArray : false
	    },
	    remove : {
	        method : 'DELETE',
	        params : {},
	        isArray : false
	    }
    });
}

/**
 * 角色服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function RoleService($resource){
    return $resource('/hrm/hrmSystemSettings/hrmRoleMgr', {}, {
        queryRoleForPage:{
        	url:'/hrm/hrmSystemSettings/hrmRoleMgr/page',
            method : 'POST',
            params : {},
            isArray : false
        },
        add : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove:{
        	method : 'DELETE',
        	params : {},
        	isArray : false
        }, 
        
        queryRoleMenu:{
        	url:'/hrm/hrmSystemSettings/hrmRoleMgr/sub',
        	method : 'GET',
        	params : {},
        	isArray : false
        },
        
        updateRoleMenu:{
        	url:'/hrm/hrmSystemSettings/hrmRoleMgr/sub',
        	method : 'PUT',
        	params : {},
        	isArray : false
        } 
    });
}
/**
 * 账户服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function AccountService($resource){
    return $resource('/hrm/hrmSystemSettings/hrmAccountMgr', {}, {
         addAccount : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        remove:{
        	method : 'DELETE',
        	params : {},
        	isArray : false
        },
    	queryAccountForPage:{
        	url:'/hrm/hrmSystemSettings/hrmAccountMgr/page',
            method : 'POST',
            params : {},
            isArray : false
        },
        queryRoleWithAccount:{
        	url:'/hrm/hrmSystemSettings/hrmAccountMgr/queryRoleWithAccount',
            method : 'GET',
            params : {},
            isArray : false
        },
        updateAccountRole : {
        	url:'/hrm/hrmSystemSettings/hrmAccountMgr/updateAccountRole',
            method : 'PUT',
            params : {},
            isArray : false
        },
        updateAccountOrg : {
        	url:'/hrm/hrmSystemSettings/hrmAccountMgr/updateAccountOrg',
        	method : 'PUT',
        	params : {},
        	isArray : false
        }
        
    });
}

/**
 * 人事异动服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function ChangeEventService($resource){
	return $resource('/hrm/changeevent/service',{},{
		queryChangEventPage:{
			method:'POST',
			params:{},
			isArray:false
		},
		
		queryChangeEventType:{
			url : '/hrm/changeevent_type/service',
			method:'GET',
			params:{},
			isArray:false
		},
		
		queryDetail:{
			url : '/hrm/changeevent/service/detail',
			method:'GET',
			params:{},
			isArray:false
		},
		
		updateFollow:{
			url : '/hrm/changeeventfollow/service',
			method:'POST',
			params:{},
			isArray:false
		},
		//发送审批结果
		post:{
			url : '/hrm/updateapply/service',
			method:'POST',
			params:{},
			isArray:false
		}
		
	})
}

/**
 * 人事处理历史
 * @param $resource
 * @returns {*}
 * @constructor
 */
function ChangeEvent_HtService($resource){
	return $resource('/hrm/changeevent_ht/service',{},{
		queryChangEvent_HtPage:{
			method:'POST',
			params:{},
			isArray:false
		},
		queryDetail:{
			url:'/hrm/changeht/service/detail',
			method:'POST',
			params:{},
			isArray:false
		},
	})
}


/**
 * 人事异动管理
 * @param $resource
 * @returns {*}
 * @constructor
 */
function EmployeeCheckService($resource){
	return $resource('/hrm/applynew/service',{},{
		newModel:{
			method:'POST',
			params:{},
			isArray:false
		}
	})
}

/**
 * 工作流
 * @param $resource
 * @returns {*}
 * @constructor
 */
function workflowNew($resource){
	return $resource('/erp/workflow/task/new',{},{
		//发起一个新流程
		post:{
			method:'POST',
			params:{},
			isArray:false
		},
		query:{
			method:'GET',
			params:{},
			isArray:false
		}
	})
}


/**
 * 查询工作流分支
 * @param $resource
 * @returns {*}
 * @constructor
 */
function workflowQuery($resource){
	return $resource('/erp/workflow/task/outcomes',{},{
		query:{
			method:'GET',
			params:{},
			isArray:false
		}
	})
}

/**
 * 工作流分支选择处理
 * @param $resource
 * @returns {*}
 * @constructor
 */
function workflowComplete($resource){
	return $resource('/erp/workflow/task/complete',{},{
		post:{
			method:'POST',
			params:{},
			isArray:false
		}
	})
}