/**
 * Created by Liyong.zhu on 2016/11/9.
 */
'use strict';

/**
 * 流程发布文件服务
 */
angular.module('ework-ui').factory('erp_workflowMgrmentService',
    [ '$resource', erp_workflowMgrmentService ]);

/**
 * 流程实例定义服务
 */
angular.module('ework-ui').factory('erp_workflowDeployedProcessDefinitionService',
    [ '$resource', erp_workflowDeployedProcessDefinitionService ]);

/**
 * 流程实例服务
 */
angular.module('ework-ui').factory('erp_workflowProcessInstanceService',
    [ '$resource', erp_workflowProcessInstanceService ]);

/**
 * 流程节点服务
 */
angular.module('ework-ui').factory('erp_workflowProcessNodeRoleService',
    [ '$resource', erp_workflowProcessNodeRoleService ]);

angular.module('ework-ui').factory('erp_workflowProcessNodeRoleMappingService',
    [ '$resource', erp_workflowProcessNodeRoleMappingService ]);

angular.module('ework-ui').factory('erp_workflowProcessTaskService',
    [ '$resource', erp_workflowProcessTaskService ]);

angular.module('ework-ui').factory('erp_workflowTaskExamineAndApproveService',
    [ '$resource', erp_workflowTaskExamineAndApproveService ]);

angular.module('ework-ui').factory('erp_workflowTaskOutcomesService',
    [ '$resource', erp_workflowTaskOutcomesService ]);

angular.module('ework-ui').factory('erp_workflowUserTaskService',
    [ '$resource', erp_workflowUserTaskService ]);

angular.module('ework-ui').factory('erp_workflowMyAppicationService',
	    [ '$resource', erp_workflowMyAppicationService ]);

angular.module('ework-ui').factory('erp_workflowBranchAppicationService',
    [ '$resource', erp_workflowBranchAppicationService ]);

angular.module('ework-ui').factory('erp_workflowTaskTodoService',
    [ '$resource', erp_workflowTaskTodoService ]);

angular.module('ework-ui').factory('erp_workflowTaskService',
	    [ '$resource', erp_workflowTaskService ]);

angular.module('ework-ui').factory('erp_workflowTaskTodoService',
    [ '$resource', erp_workflowTaskTodoService ]);

angular.module('ework-ui').factory('erp_workflowTaskTodoChangeService',
    [ '$resource', erp_workflowTaskTodoChangeService ]);

function erp_workflowTaskTodoChangeService($resource){
    return $resource('/erp/workflow/task/todo_change', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post : {
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowTaskTodoService($resource){
    return $resource('/erp/workflow/task/todo', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        queryByOrderId : {
        	url : '/erp/workflow/task/orderId',
        	method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowTaskService($resource){
	return $resource('/erp/workflow/task', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowMyAppicationService($resource){
	return $resource('/erp/workflow/task/myAppication', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        del:{
        	method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowBranchAppicationService($resource){
    return $resource('/erp/workflow/task/branchAppication', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowProcessInstanceService($resource){
    return $resource('/erp/workflow/processInstance', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        put : {
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete : {
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowDeployedProcessDefinitionService($resource) {
    return $resource('/erp/workflow/deployedProcessDefinition', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowMgrmentService($resource){
    return $resource('/erp/workflow/processinfo', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}

/**
 * 流程节点的权限绑定数据服务
 * @param $resource
 * @returns {*}
 */
function erp_workflowProcessNodeRoleService($resource){
    return $resource('/erp/workflow/processNodeRole', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        }
    });
}
/**
 * 流程节点与角色绑定服务
 * @param $resource
 * @returns {*}
 */
function erp_workflowProcessNodeRoleMappingService($resource){
    return $resource('/erp/workflow/rolemapping', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}
/**
 * 流程任务服务
 * @param $resource
 * @returns {*}
 */
function erp_workflowProcessTaskService($resource){
    return $resource('/erp/workflow/taskinfos', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        },
        post:{
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}
/**
 * 审批任务
 * @param $resource
 * @returns {*}
 */
function erp_workflowTaskExamineAndApproveService($resource){
    return $resource('/erp/workflow/task/complete', {}, {
        post:{
            method : 'POST',
            params : {},
            isArray : false
        }
    });
}

/**
 * 查询任务步骤
 * @param $resource
 * @returns {*}
 */
function erp_workflowTaskOutcomesService($resource){
    return $resource('/erp/workflow/task/outcomes', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}

function erp_workflowUserTaskService($resource){
    return $resource('/erp/workflow/user_task/todo_page', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}