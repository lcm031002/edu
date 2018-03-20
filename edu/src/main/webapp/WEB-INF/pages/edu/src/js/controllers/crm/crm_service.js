/**
 * Created by Liyong.zhu on 2016/9/12.
 */
'use strict';
/**
 * 团队查询服务
 */
angular.module('ework-ui').factory('crm_buService',
		[ '$resource', crm_buService ]);
/**
 * 校区查询服务
 */
angular.module('ework-ui').factory('crm_branchService',
		[ '$resource', crm_branchService ]);

/**
 * 科目服务
 */
angular.module('ework-ui').factory('crm_subjectService',
    [ '$resource', crm_subjectService ]);

/**
 * 渠道统计服务
 */
angular.module('ework-ui').factory('crm_channelService',
		[ '$resource', crm_channelService ]);
/**
 * 渠转化率统计服务
 */
angular.module('ework-ui').factory('crm_convertRateService',
		[ '$resource', crm_convertRateService ]);
/**
 * 平均单笔统计服务
 */
angular.module('ework-ui').factory('crm_avgachieveService',
		[ '$resource', crm_avgachieveService ]);
/**
 * 业绩总额统计服务
 */
angular.module('ework-ui').factory('crm_achievetotalService',
		[ '$resource', crm_achievetotalService ]);
/**
 * 校区业绩排名服务
 */
angular.module('ework-ui').factory('crm_orderedsortService',
		[ '$resource', crm_orderedsortService ]);
/**
 * 业绩资源对比服务
 */
angular.module('ework-ui').factory('crm_performancegcService',
		[ '$resource', crm_performancegcService ]);
/**
 * 业绩资源对比服务
 */
angular.module('ework-ui').factory('crm_excavategcService',
		[ '$resource', crm_excavategcService ]);

/**
 * 约访率统计
 */
angular.module('ework-ui').factory('crm_planVisitService',
		[ '$resource', crm_planVisitService ]);

/**
 * 现场成单率
 */
angular.module('ework-ui').factory('crm_sceneService',
		[ '$resource', crm_sceneService ]);

/**
 * 资源转化率
 */
angular.module('ework-ui').factory('crm_resConvationGcService',
		[ '$resource', crm_resConvationGcService ]);


function crm_subjectService($resource){
    return $resource('/erp/dictionary/subject/list', {}, {
        query : {
            method : 'GET',
            params : {},
            isArray : false
        }
    });
}


function crm_buService($resource) {
	return $resource('/gxhcrm/common/buservice', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_branchService($resource) {
	return $resource('/gxhcrm/common/branchservice', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_channelService($resource) {
	return $resource('/gxhcrm/report/queryPage/channelPage', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_convertRateService($resource) {
	return $resource('/gxhcrm/report/query/convertrate', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_avgachieveService($resource) {
	return $resource('/gxhcrm/report/query/avgachieve', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_achievetotalService($resource) {
	return $resource('/gxhcrm/report/query/achievetotal', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_orderedsortService($resource) {
	return $resource('/gxhcrm/report/queryPage/orderedsortPage', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_performancegcService($resource) {
	return $resource('/gxhcrm/report/query/performancegc', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_excavategcService($resource) {
	return $resource('/gxhcrm/report/query/excavategc', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_planVisitService($resource) {
	return $resource('/gxhcrm/report/query/planVisit', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}
function crm_sceneService($resource) {
	return $resource('/gxhcrm/report/query/scene', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

function crm_resConvationGcService($resource) {
	return $resource('/gxhcrm/report/query/resConvationGc', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}
