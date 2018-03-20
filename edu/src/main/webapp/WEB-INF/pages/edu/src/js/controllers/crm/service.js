'use strict';
var webContext = 'data';
/**
 * 重要提醒查询服务
 */
angular.module('ework-ui').factory('crm_ReminderService',
		[ '$resource', crm_ReminderService ]);
/**
 * 待审批查询服务
 */
angular.module('ework-ui').factory('crm_ApprovedService',
		[ '$resource', crm_ApprovedService ]);
/**
 * 潜在客户查询服务
 */
angular.module('ework-ui').factory('crm_MyOrderedsService',
		[ '$resource', crm_MyOrderedsService ]);

// 年级报表统计
angular.module('ework-ui').factory('crm_GradeResourceService',
		[ '$resource', crm_GradeResourceService ]);
/**
 * 潜在客户查询服务
 */
angular.module('ework-ui').factory('crm_BranchsVisibleService',
		[ '$resource', crm_BranchsVisibleService ]);

/**
 * 是否是校区总监
 */
angular.module('ework-ui').factory('crm_isSchoolAdminService',
		[ '$resource', crm_isSchoolAdminService ]);


/**
 * 是否是有权限录入
 */
angular.module('ework-ui').factory('crm_hasInputRightService',
		[ '$resource', crm_hasInputRightService ]);


/**
 * 线索录入时根据手机号判断是否重复
 */
angular.module('ework-ui').factory('crm_checkRepeatService',
		[ '$resource', crm_checkRepeatService ]);


/**
 * 当前团队下的校区列表
 */
angular.module('ework-ui').factory('crm_queryInputRightBranch',
		[ '$resource', crm_queryInputRightBranch ]);


/**
 * 查询当前选中的默认校区
 */
angular.module('ework-ui').factory('crm_querySelectedBranch',
		[ '$resource', crm_querySelectedBranch ]);

/**
 * 潜在客户查询服务
 */
angular.module('ework-ui').factory('crm_BranchPerformanceService',
		[ '$resource', crm_BranchPerformanceService ]);
/**
 * 日历事件查询服务
 */
angular.module('ework-ui').factory('crm_BranchFullCalendarService',
		[ '$resource', crm_BranchFullCalendarService ]);
/**
 * 未报名客户查询服务
 */
angular.module('ework-ui').factory('crm_NoRegisterCtrlService',
		[ '$resource', crm_NoRegisterCtrlService ]);
/**
 * 通知提醒
 */
angular.module('ework-ui').factory('crm_AlertService',
		[ '$resource', crm_AlertService ]);

// 查询当前用户校区
angular.module('ework-ui').factory('crm_BranchsService',
		[ '$resource', crm_BranchsService ]);


//线索转移左边的咨询师列表
angular.module('ework-ui').factory('crm_tranfCnselorFromService',
		[ '$resource', crm_tranfCnselorFromService ]);


//线索转移右边的咨询师列表
angular.module('ework-ui').factory('crm_tranfCnselorToService',
		[ '$resource', crm_tranfCnselorToService ]);


//线索转移，根据咨询师ID查询名下可以转移的线索
angular.module('ework-ui').factory('crm_tranfRescQueryService',
		[ '$resource', crm_tranfRescQueryService ]);

//线索转移，单个转移
angular.module('ework-ui').factory('crm_tranfSingleToService',
		[ '$resource', crm_tranfSingleToService ]);

//线索转移，批量转移
angular.module('ework-ui').factory('crm_tranfBatchService',
		[ '$resource', crm_tranfBatchService ]);


function crm_tranfCnselorFromService($resource) {
	return $resource('/gxhcrm/query/transf/from', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
};

function crm_tranfCnselorToService($resource) {
	return $resource('/gxhcrm/query/transf/to', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
};

function crm_tranfRescQueryService($resource) {
	return $resource('/gxhcrm/query/transf/resc', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
};

function crm_tranfSingleToService($resource) {
	return $resource('/gxhcrm/rescRecTrsf/toTransfer', {}, {
		post : {
			method : 'POST',
			params : {},
			isArray : false
		}
	});
};

function crm_tranfBatchService($resource) {
	return $resource('/gxhcrm/rescRecTrsf/toBatchTrsf', {}, {
		post : {
			method : 'POST',
			params : {},
			isArray : false
		}
	});
};


angular.module('ework-ui').factory('crm_DictDataService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/dict/data/:actionType/:action', {
				actionType : 'actionType',
				action : '@action'
			}, {
				add : {
					method : 'POST',
					params : {
						actionType : 'add',
						action : 'toAdd'
					},
					isArray : false
				},
				page : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'dataPage'
					},
					isArray : false
				},
				tree : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'orgTree'
					},
					isArray : false
				},
				remove : {
					method : 'DELETE',
					params : {
						actionType : 'delete',
						action : 'toRemove'
					},
					isArray : false
				},
				update : {
					method : 'PUT',
					params : {
						actionType : 'update',
						action : 'toUpdate'
					},
					isArray : false
				},
				// 机构授权
				auth : {
					method : 'POST',
					params : {
						actionType : 'auth',
						action : 'toAuth'
					},
					isArray : false
				}
			});
		} ]);

angular.module('ework-ui').factory('crm_queryDictDataService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/dict/data', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : true
				}
			});
		} ]);


angular.module('ework-ui').factory('crm_queryBranchsService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/branchs', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				}
			});
		} ]);
/**
 * 查询登录用户的数据
 */
angular.module('ework-ui').factory('crm_LoginUserService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/system/query/loginuser', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				}
			});
		} ]);

function crm_BranchsService($resource) {
	return $resource('/gxhcrm/query/branchs', {}, {
		queryAllBranchsByUser : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}
/**
 * 重要提醒查询：查询当前所有的重要提醒数据
 * 
 * @param $resource
 * @returns {*} 返回对象说明： pageSize:分页尺寸，默认20条 currentPage:当前页，默认1 totalPage:当前总页数
 *          totalCount:总的记录数 resultList:当前记录详情
 * @constructor
 */
function crm_ReminderService($resource) {
	return $resource('/gxhcrm/home/reminder/:actionType/:action', {
		actionType : 'actionType',
		action : '@action'
	}, {
		add : {
			method : 'POST',
			params : {
				actionType : 'add',
				action : 'toAdd'
			},
			isArray : false
		},
		page : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'page'
			},
			isArray : false
		},
		query : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'list'
			},
			isArray : false
		},
		count : {
			method : 'GET',
			params : {
				actionType : 'count',
				action : 'totalNum'
			},
			isArray : false
		},
		remove : {
			method : 'DELETE',
			params : {
				actionType : 'delete',
				action : 'toRemove'
			},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {
				actionType : 'update',
				action : 'toUpdate'
			},
			isArray : false
		}
	});
}

/**
 * 待审批查询：查询当前所有的待审批数据
 * 
 * @param $resource
 * @returns {*} 返回对象说明： pageSize:分页尺寸，默认20条 currentPage:当前页，默认1 totalPage:当前总页数
 *          totalCount:总的记录数 resultList:当前记录详情
 * @constructor
 */
function crm_ApprovedService($resource) {
	return $resource('/gxhcrm/home/pendingTask/:actionType/:action', {
		actionType : 'actionType',
		action : '@action'
	}, {
		add : {
			method : 'POST',
			params : {
				actionType : 'add',
				action : 'toAdd'
			},
			isArray : false
		},
		page : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'page'
			},
			isArray : false
		},
		query : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'list'
			},
			isArray : false
		},
		count : {
			method : 'GET',
			params : {
				actionType : 'count',
				action : 'totalNum'
			},
			isArray : false
		},
		remove : {
			method : 'DELETE',
			params : {
				actionType : 'delete',
				action : 'toRemove'
			},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {
				actionType : 'update',
				action : 'toUpdate'
			},
			isArray : false
		},
		distBranch : {
			method : 'PUT',
			params : {
				actionType : 'update',
				action : 'distBranch'
			},
			isArray : false
		}
	});
}
/**
 * 潜在客户查询服务
 * 
 * @param $resource
 * @returns {*} 返回对象说明： pageSize:分页尺寸，默认20条 currentPage:当前页，默认1 totalPage:当前总页数
 *          totalCount:总的记录数 resultList:当前记录详情
 * @constructor
 */
function crm_MyOrderedsService($resource) {
	return $resource('/gxhcrm/home/myOrdered/:actionType/:action', {
		actionType : 'actionType',
		action : '@action'
	}, {
		add : {
			method : 'POST',
			params : {
				actionType : 'add',
				action : 'toAdd'
			},
			isArray : false
		},
		page : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'page'
			},
			isArray : false
		},
		query : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'list'
			},
			isArray : false
		},
		count : {
			method : 'GET',
			params : {
				actionType : 'count',
				action : 'totalNum'
			},
			isArray : false
		},
		remove : {
			method : 'POST',
			params : {
				actionType : 'delete',
				action : 'removeOrder'
			},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {
				actionType : 'update',
				action : 'updateOrder'
			},
			isArray : false
		},
		addagain : {
			method : 'POST',
			params : {
				actionType : 'add',
				action : 'addnew'
			},
			isArray : false
		},
		pages:{method:'POST',params:{actionType:'query',action:'pages'},isArray:false}
	});
}

function crm_GradeResourceService($resource) {
	return $resource('/gxhcrm/home/gradeResource/:actionType/:action', {
		actionType : 'actionType',
		action : '@action'
	}, {
		add : {
			method : 'POST',
			params : {
				actionType : 'add',
				action : 'toAdd'
			},
			isArray : false
		},
		page : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'page'
			},
			isArray : false
		},
		query : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'list'
			},
			isArray : false
		},
		count : {
			method : 'GET',
			params : {
				actionType : 'count',
				action : 'totalNum'
			},
			isArray : false
		},
		remove : {
			method : 'DELETE',
			params : {
				actionType : 'delete',
				action : 'toRemove'
			},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {
				actionType : 'update',
				action : 'toUpdate'
			},
			isArray : false
		}
	});
}

/**
 * 查询可见校区服务
 * 
 * @param $resource
 * @returns {*} resultList:当前可见校区列表
 * @constructor
 */
function crm_BranchsVisibleService($resource) {
	return $resource('/gxhcrm/home/users/branchs', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}


/**
 * 判断是不是校区总监、课程顾问总监、咨询师总监
 * 
 * @param $resource
 * @returns {*} resultList:true/false
 * @constructor
 */
function crm_isSchoolAdminService($resource) {
	return $resource('/gxhcrm/home/users/isSchoolAdmin', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}


/**
 * 是否有权限录入资源
 * 
 * @param $resource
 * @returns {*} resultList:是否有权限录入资源
 * @constructor
 */
function crm_hasInputRightService($resource) {
	return $resource('/gxhcrm/home/users/hasInputRight', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}


/**
 * 根据输入的手机号 判断是否有重复录入的资源
 * 
 * @param $resource
 * @returns {*} resultList:是否有权限录入资源
 * @constructor
 */
function crm_checkRepeatService($resource) {
	return $resource('/gxhcrm/rescRecInput/add/checkRepeat', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 查询当前团队下的校区列表
 * 
 * @param $resource
 * @returns {*} resultList:查询当前团队下的校区列表
 * @constructor
 */
function crm_queryInputRightBranch($resource) {
	return $resource('/gxhcrm/home/users/queryInputRightBranch', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}


/**
 * 查询当前选中的默认校区
 * 
 * @param $resource
 * @returns {*} resultList:查询当前选中的默认校区
 * @constructor
 */
function crm_querySelectedBranch($resource) {
	return $resource('/gxhcrm/home/users/branchs', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 查询可见校区业绩服务
 * 
 * @param $resource
 * @returns {*} resultList:当前可见校区列表
 * @constructor
 */
function crm_BranchPerformanceService($resource) {
	return $resource('/gxhcrm/home/performance/days', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
}

/**
 * 校区日历查询
 * 
 * @param $resource
 * @returns {*}
 * @constructor
 */
function crm_BranchFullCalendarService($resource) {
	return $resource('/gxhcrm/home/branchFullcalendar/:action', {
		action : '@action'
	}, {
		query : {
			method : 'GET',
			isArray : false
		},
		save : {
			method : 'POST',
			headers : {
				Accept : 'application/json'
			}
		}
	});
}

/**
 * 未报班查询服务
 * 
 * @param $resource
 * @returns {*} 返回对象说明： pageSize:分页尺寸，默认20条 currentPage:当前页，默认1 totalPage:当前总页数
 *          totalCount:总的记录数 resultList:当前记录详情
 * @constructor
 */
function crm_NoRegisterCtrlService($resource) {
	return $resource('/gxhcrm/home/noRegisterCtrl/:actionType/:action', {
		actionType : 'actionType',
		action : '@action'
	}, {
		add : {
			method : 'POST',
			params : {
				actionType : 'add',
				action : 'toAdd'
			},
			isArray : false
		},
		page : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'page'
			},
			isArray : false
		},
		query : {
			method : 'GET',
			params : {
				actionType : 'query',
				action : 'list'
			},
			isArray : false
		},
		count : {
			method : 'GET',
			params : {
				actionType : 'count',
				action : 'totalNum'
			},
			isArray : false
		},
		remove : {
			method : 'DELETE',
			params : {
				actionType : 'delete',
				action : 'toRemove'
			},
			isArray : false
		},
		update : {
			method : 'PUT',
			params : {
				actionType : 'update',
				action : 'toUpdate'
			},
			isArray : false
		}
	});
}

/**
 * 通知提醒
 * 
 * @param $resource
 * @returns {*}
 * @constructor
 */
function crm_AlertService($resource) {
	return $resource('/gxhcrm/home/alert/:action', {
		action : '@action'
	}, {
		query : {
			method : 'GET',
			isArray : false
		},
		save : {
			method : 'POST',
			headers : {
				Accept : 'application/json'
			}
		}
	});
}

angular.module('ework-ui').factory("actionInjector", function() {
	var actionInjector = {
		request : function(config) {
			if (config.data) {
				if (config.data.action) {
					delete config.data.action;
				}
				if (config.data.actionType) {
					delete config.data.actionType;
				}

				if (config.data.selected) {
					delete config.data.selected;
				}
				if (config.data.delFlag) {
					delete config.data.delFlag;
				}
			}
			return config;
		}
	};
	return actionInjector;
});
// 增加过滤器
angular.module('ework-ui').config([ '$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('actionInjector');
} ]);


// 查询资源数据
angular.module('ework-ui').factory('crm_loadDataRescRecService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryRescRec', {}, {
				page : {
					method : 'GET',
					params : {},
					isArray : false
				}
			});
		} ]);

// 查询报表数据
angular.module('ework-ui').factory('crm_loadReportDataRecService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/report/query/:reportType', {}, {
				page : {
					method : 'GET',
					params : {},
					isArray : false
				}
			});
		} ]);


// 基础类型
angular.module('ework-ui').factory('crm_DictTypeService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/dict/type/:actionType/:action', {
				actionType : 'actionType',
				action : '@action'
			}, {
				add : {
					method : 'POST',
					params : {
						actionType : 'add',
						action : 'toAdd'
					},
					isArray : false
				},
				page : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'dataPage'
					},
					isArray : false
				},
				tree : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'orgTree'
					},
					isArray : false
				},
				remove : {
					method : 'DELETE',
					params : {
						actionType : 'delete',
						action : 'toRemove'
					},
					isArray : false
				},
				update : {
					method : 'PUT',
					params : {
						actionType : 'update',
						action : 'toUpdate'
					},
					isArray : false
				},
				// 机构授权
				auth : {
					method : 'POST',
					params : {
						actionType : 'auth',
						action : 'toAuth'
					},
					isArray : false
				},
			});
		} ]);
// 基础字典数据服务
angular.module('ework-ui').factory('crm_DictDataService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/dict/data/:actionType/:action', {
				actionType : 'actionType',
				action : '@action'
			}, {
				add : {
					method : 'POST',
					params : {
						actionType : 'add',
						action : 'toAdd'
					},
					isArray : false
				},
				page : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'dataPage'
					},
					isArray : false
				},
				tree : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'orgTree'
					},
					isArray : false
				},
				remove : {
					method : 'DELETE',
					params : {
						actionType : 'delete',
						action : 'toRemove'
					},
					isArray : false
				},
				update : {
					method : 'PUT',
					params : {
						actionType : 'update',
						action : 'toUpdate'
					},
					isArray : false
				},
				// 机构授权
				auth : {
					method : 'POST',
					params : {
						actionType : 'auth',
						action : 'toAuth'
					},
					isArray : false
				},
			});
		} ]);

angular.module('ework-ui').factory('crm_querySubjectService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/querySubject', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : true
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_queryCurrentUserInfoService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryCurrentUserInfo', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);

angular.module('ework-ui').factory('crm_queryGradesService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryGradeList', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_querySingleRescRecService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/querySingleRescRec', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_queryRescRecProcService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryRescRecProc', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_queryRescinfoService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryRescinfo', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_RescRecProcService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/rescRecProc/:actionType/:action', {
				actionType : '@actionType',
				action : '@action'
			}, {
				add : {
					method : 'POST',
					params : {
						actionType : 'add',
						action : 'toAdd'
					},
					isArray : false
				},
				page : {
					method : 'GET',
					params : {
						actionType : 'query',
						action : 'dataPage'
					},
					isArray : false
				},
				remove : {
					method : 'DELETE',
					params : {
						actionType : 'delete',
						action : 'toRemove'
					},
					isArray : false
				},
				update : {
					method : 'PUT',
					params : {
						actionType : 'update',
						action : 'toUpdate'
					},
					isArray : false
				},
				// 机构授权
				auth : {
					method : 'POST',
					params : {
						actionType : 'auth',
						action : 'toAuth'
					},
					isArray : false
				}
			});
		} ]);
angular.module('ework-ui').factory('crm_queryRescRecListService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryRescRecList', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_queryAschListService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryAschList', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);

angular.module('ework-ui').factory('crm_queryTeacherListService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryTeacherList', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);

angular.module('ework-ui').factory('crm_queryCnselorListService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/queryCnselorList', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);


angular.module('ework-ui').factory('crm_getSequenceNumService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/query/getSequenceNum', {}, {
				query : {
					method : 'GET',
					params : {},
					isArray : false
				},
			});
		} ]);
angular.module('ework-ui').factory('crm_VisitPlanService',
				['$resource',function($resource) {
							return $resource(
									'/gxhcrm/rescRecProc/planVisit/:actionType/:action',
									{
										actionType : '@actionType',
										action : '@action'
									}, {
										add : {
											method : 'POST',
											params : {
												actionType : 'add',
												action : 'toAdd'
											},
											isArray : false
										},
										remove : {
											method : 'DELETE',
											params : {
												actionType : 'delete',
												action : 'toRemove'
											},
											isArray : false
										},
										querySingle : {
											method : 'GET',
											params : {
												actionType : 'query',
												action : 'querySingle'
											},
											isArray : false
										}
									});
						} ]);
angular.module('ework-ui').factory('crm_addOrderService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/rescRecProc/toAddOrder', {}, {
				add : {
					method : 'POST',
					params : {},
					isArray : false
				}
			});
		} ]);

angular.module('ework-ui').factory('crm_GxhCrmDictService',
		[ '$resource', function($resource) {
			return $resource('/gxhcrm/dict/:action', {
				action : '@action'
			}, {
				query : {
					method : 'POST',
					isArray : true
				},
				queryOne : {
					method : 'POST'
				},
				add : {
					method : 'POST'
				},
				update : {
					method : 'POST'
				}
			});
		} ]);
