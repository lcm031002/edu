"use strict";
angular
		.module('ework-ui')
		.controller(
				'crmRescRecProcController',
				[
						'$rootScope',
						'$scope',
						'$state',
						'crm_queryDictDataService',
						'crm_querySingleRescRecService',
						'crm_queryRescRecProcService',
						'crm_queryRescinfoService',
						'crm_VisitPlanService',
						'crm_addOrderService',
						'crm_loadDataRescRecService',
						'$stateParams',
						'crm_RescRecProcService',
						'crm_LoginUserService',
						'erp_studentsService',
						'$uibMsgbox','crm_isSchoolAdminService',
						function($rootScope, $scope,$state, crm_queryDictDataService,
								crm_querySingleRescRecService,
								crm_queryRescRecProcService, crm_queryRescinfoService,
								crm_VisitPlanService, crm_addOrderService,
								crm_loadDataRescRecService, $stateParams,
								crm_RescRecProcService, crm_LoginUserService,erp_studentsService,$uibMsgbox,crm_isSchoolAdminService) {

              /*--------------星级插件_strat-----------*/
							$scope.initnumber = function (number) {
								$(".my-rating").starRating({
									starSize: 40,
									initialRating: number,//初始化的星星数量
									useFullStars: true,
									disableAfterRate: false,
									callback: function (
										currentRating, $el) {
										$('#selectnumber').val(currentRating
											.toString());
									}
								});
							}
									/*--------------星级插件_end-------------*/	

							$scope.changeVisitWay = function() {
								if ($scope.currentRec.visit_way.code == 'no_longer_follow') {
									$scope.newProc.next_visit_time = getCurrentDate();
								} else {
                                    $scope.newProc.next_visit_time = '';
								}
							}
							
							$scope.isUpdate = false;
							$scope.userInfo = crm_LoginUserService.get({}, function (response) {
								if (response.flag) {
									$(response.data).each(
										function (i, model) {
											if (model.post_code == 'adviser_admin'
												|| model.post_code == 'counselor_admin'
												|| model.post_code == 'LearningManagementDivision_admin') {
												$scope.isUpdate = true;
											}
										});
								}
							}, function (e) {
								//$scope.logout();
							});

							// 搜索/导出参数
							$scope.searchParam = {};
							// 更多信息窗口
							$scope.popW_more = {};
							// 跟踪窗口
							$scope.popW_trace = {};

							$scope.linksList = [ {
								name : ''
							} ];
							$scope.sexoptions = [{  
						        id: 1,  
						        sex:'男'  
						    },{  
						        id: 0,  
						        sex:'女'  
						    }]; 
							$scope.orderedoptions = [{  
						        id: 1,  
						        name:'是'  
						    },{  
						        id: 0,  
						        name:'否'  
						    }]; 
							$scope.newProc = {};
							$scope.isShow = false;

							// 资源列表
							$scope.rescRecProc = {};
							
							$scope.p_date_types = [
								{id: 1,  text:"资源日期"  },
							    {id: 2,  text:"下次跟进日期"  },
							    {id: 3,  text:"最近一次跟进日期"  }
							];
							
							$scope.sexs = [
								{id: "1",  text:"男"  },
							    {id: "0",  text:"女"  },
							];
							
							
							// 资源更多信息
							$scope.moreInfo = {};

							// 资源跟踪列表
							$scope.traceInfo = {};

							$scope.pageRowList = [ {
								value : 5,
								label : 5
							}, {
								value : 10,
								label : 10
							}, {
								value : 20,
								label : 20
							}, {
								value : 30,
								label : 30
							}, {
								value : 40,
								label : 40
							}, {
								value : 50,
								label : 50
							}, {
								value : 100,
								label : 100
							} ];
							$scope.pageRow = $scope.pageRowList[1];
							$scope.changePageRows = function() {
								$scope.pageSize = $scope.pageRow.value;
								$scope.load();
							}


							$scope.searchParam = {};

							$scope.init = function() {
								$scope.searchParam.p_mp = $scope.mp;
								$scope.searchParam.p_name = $scope.name;
								$scope.searchParam.p_isProc = "1";
								crm_isSchoolAdminService.query({}, function(res) {
									$scope.isSchoolAdmin=res.flag;
								});
								// 分页
								agPageObject($scope, crm_loadDataRescRecService,
										$scope.searchParam);
								$scope.totalPage = 0;

							};

							$scope.pageCallBack = function() {
								var newArray = new Array();
								$scope.isLoading = '';
								if($scope.items != null) {
									$.each($scope.items, function(i, model) {
										model.is_trace = false;
										model.selected = '';
										newArray.push(model);
										newArray.push({
											is_trace : true,
											id : model.id
										});
									});
								}
								$scope.rescRecProc = newArray;
								$scope.pageConf.totalItems = $scope.total || 0;
							};

							$scope.isLoading = '';

							if ($stateParams.name) {
								$scope.searchParam.p_searchString = $stateParams.name;
							}

							//分页
							$scope.pageConf = {
								currentPage: 1,
								totalItems: 0,
								itemsPerPage: 10,
								onChange: function () {
								  $scope.query()
								}
							}
							$scope.query = function() {
								var url = "/gxhcrm/query/queryRescRec";
								$scope.searchParam.p_isProc = "1";
								if ($scope.searchParam.clue_stage) {
									$scope.searchParam.p_clue_stage_id = $scope.searchParam.clue_stage.id;
								}
								// console.log($scope.searchParam);
								$scope.pageSize = $scope.pageConf.itemsPerPage;
								$scope.currentPage = $scope.pageConf.currentPage;
								$scope.isLoading = 'loading...';
								$scope.resourceRecsInput = [];
								if ($scope.searchParam.resType) {
									$scope.searchParam.p_res_type = $scope.searchParam.resType.id;
								}
								if ($scope.searchParam.channelType) {
									$scope.searchParam.p_channel_type = $scope.searchParam.channelType.id;
								}else {
									$scope.searchParam.p_channel_type = '';
								}
								if ($scope.searchParam.cnselState) {
									$scope.searchParam.p_cnsel_state = $scope.searchParam.cnselState.id;
								}else {
									$scope.searchParam.p_cnsel_state = '';
								}
								if ($scope.searchParam.clue_stage_obj) {
									$scope.searchParam.p_clue_stage_id = $scope.searchParam.clue_stage_obj.id;
								}else {
									$scope.searchParam.p_clue_stage_id = '';
								}
								$scope.load();
							}

							$scope.init();

							/* 排序_start */
							$scope.next_visit_time_sort = 0;
							$scope.pageSort = function(_seq) {
								if ($scope.next_visit_time_sort == 0) {
									$scope.next_visit_time_sort = 1;
								} else if ($scope.next_visit_time_sort == 1) {
									$scope.next_visit_time_sort = 2;
								} else if ($scope.next_visit_time_sort == 2) {
									$scope.next_visit_time_sort = 1;
								}
								$scope.searchParam.p_next_visit_time_sort = $scope.next_visit_time_sort;
								$scope.query();
							}
							/* 排序_end */

							// 导出
							$scope.exp = function() {
								var param = "";
								$scope.searchParam.isProc = "1";
								for ( var p in $scope.searchParam) {
									if ($scope.searchParam[p]) {
										param += p + "="
												+ $scope.searchParam[p] + "&";
									}
								}
								if (confirm("确定要导出?")) {
									location.href = "/gxhcrm/export/exportResProc?"
											+ encodeURI(encodeURI(param));
								}
							};

							// 清理form
							$scope.clearform = function() {
								$scope.newProc = {};
							};
							// 订单回填清理form
							$scope.orderclearform = function() {
								$scope.order = {};
							};
							// 预约访清理form
							$scope.planclearform = function() {
								$scope.planvisit = {};
							};

							// 预约访
							$scope.form_plan_visit = false;

							// 约访记录
							$scope.form_proc = false;
							// 订单回填
							$scope.form_order = false;

							// 资源处理
							$scope.showProc = function(l_rec) {
								$scope.showTraceForm(l_rec);
								$scope.form_proc = true;
								// 预约访
								$scope.form_plan_visit = false;
								// 订单回填
								$scope.form_order = false;
								
								$scope.closeMoreinfo();
								$scope.currentRec = {};
								$scope.currentRec = l_rec;
								$scope.currentRec.bargain_intent = getSelects(
										$scope.currentRec.bargain_intent_id,
										$scope.BargainIntents);
								$scope.currentRec.clue_stage = getSelects(
										$scope.currentRec.clue_stage_id,
										$scope.ClueStages);
								$scope.newProc.visit_time = new Date();
							};

							// 订单回填
							$scope.showOrder = function(l_rec) {
								$scope.showTraceForm(l_rec);
								$scope.form_order = true;
								// 预约访
								$scope.form_plan_visit = false;
								// 约访记录
								$scope.form_proc = false;

								$scope.closeMoreinfo();
								$scope.currentRec = {};
								$scope.currentRec = l_rec;
                                $scope.changeResType(l_rec.res_type);
							};

                           $scope.studentList = [];

							//报班
							$scope.addOrder = function(l_rec){
								//查询学员信息
								//-1>获取学员联系方式
								var linkPhone = l_rec.resc.mp?"'"+l_rec.resc.mp + "'":'';
								$.each(l_rec.resc.rescLinks,function(i,n) {
									if(n.mp) {
                                        linkPhone = linkPhone + ",'" +  n.mp+ "'";
									}
								});

                            $scope.currentRec = l_rec;
  
								//-2>查询学员是否存在
							erp_studentsService.queryStudentByNameAndPhone({
                                    studentName : l_rec.resc.name,
									phones: linkPhone
								}, function (resp) {
									if (!resp.error) {
										if (resp.data && resp.data.length > 0) {
											$scope.studentList = resp.data;
											$('#studentSelectModal').modal('show');
										} else {
											//-不存在：跳转到学员新增
											$state.go('newStudent', {
												resource_rec_id: l_rec.id,
												"path": "/students/newStudent",
												"href": "templates/erp/student/newStudent.html"
											});
										}
									} else {
										$uibMsgbox.error(resp.message);
									}
								});
							};

							$scope.curStudent = {};
							$scope.setCurStudent = function(student) {
								$.each($scope.studentList, function(idx, stu) {
									if (stu.id != student.id) {
										stu.checked = false;
									}
									$scope.curStudent = student;
								});
							}

							$scope.addOrderForStudent = function() {
								if (!$scope.curStudent || !$scope.curStudent.id) {
									$uibMsgbox.error("请选择一个学员进行报班！");
									return;
								}
								$('#studentSelectModal').modal('hide');
								$state.go('ordersMgrOrders', {
									studentId: $scope.curStudent.id,
									resource_rec_id: $scope.currentRec.id,
									"path": "/orders/ordersMgr/ordersMgrOrders",
									"href": "templates/erp/orders/orders.html"
								});
							}

							$scope.addNewStudent = function () {
								$('#studentSelectModal').modal('hide');
								$state.go('newStudent', {
									resource_rec_id: $scope.currentRec.id,
									"path": "/students/newStudent",
									"href": "templates/erp/student/newStudent.html"
								});
							}

							// 约访
							$scope.showPlanVisit = function(l_rec) {
								$scope.showTraceForm(l_rec);
								$scope.form_plan_visit = true;
								// 约访记录
								$scope.form_proc = false;
								// 订单回填
								$scope.form_order = false;

								$scope.currentRec = {};
								$scope.currentRec = l_rec;
								$scope.planvisit.remark = '';
							};
							//搜索资源进展改变
							$scope.SearchClue = function(cluestage){								
								$scope.searchParam.clue_stage_obj = cluestage
							}

							//资源进展改变
							$scope.changeClue = function(cluestage){
								$scope.currentRec.clue_stage = cluestage
							}

							$scope.checkform = function() {
								if (!$scope.currentRec.clue_stage) {
									$uibMsgbox.error('资源进展必须选择！');
									return false;
								}
								if (($scope.currentRec.clue_stage.name == '已上门') && !$scope.newProc.first_visit_time && !$scope.currentRec.first_visit_time) {
									$uibMsgbox.error('首次上门时间必须选择！');
									return false;
								}
								if (!$scope.currentRec.visit_way) {
									$uibMsgbox.error('跟进方式必须选择！');
									return false;
								}
								if (!$scope.newProc.visit_time) {
									$uibMsgbox.error('跟进时间必须填写！');
									return false;
								}
								if (!$scope.newProc.next_visit_time) {
									$uibMsgbox.error('下次跟进时间必须填写！');
									return false;
								}
								if (!($('#talk_content').val())) {
									$uibMsgbox.error('沟通内容必须填写！');
									return false;
								}
								return true;
							}

							// 保存并返回成功或失败
							$scope.save = function() {
								console.log($scope.currentRec.clue_stage.name)
								if (!$scope.checkform()) {
									return false;
								}
								if ($scope.currentRec.clue_stage.name == '无意向') {
									$scope.newProc.visit_time = '';
								}
								
								$scope.newProc.cnsel_state = $scope.currentRec.cnsel_state;
								$scope.newProc.resc_rec_id = $scope.currentRec.id;
								if ($scope.currentRec.visit_way) {
									$scope.newProc.visit_way_name = $scope.currentRec.visit_way.name;
									$scope.newProc.visit_way_id = $scope.currentRec.visit_way.id;
								}
								if ($scope.currentRec.clue_stage) {
									$scope.newProc.clue_stage_name = $scope.currentRec.clue_stage.name;
									$scope.newProc.clue_stage_id = $scope.currentRec.clue_stage.id;
								}
								if ($scope.currentRec.bargain_intent) {
									$scope.newProc.bargain_intent_id = $scope.currentRec.bargain_intent.id;
								}

								if ($scope.currentRec.resc.name) {
									$scope.newProc.studentName = $scope.currentRec.resc.name;
								}

								// 保存数据导数据库
								crm_RescRecProcService.add($scope.newProc,
										$scope.saveFormCallBack);
							};
							$scope.order = {};

							// 订单回填校验
							$scope.ordercheckform = function() {
								if (isEmpty($scope.order.orderNo)) {
									$uibMsgbox.error('报班单号必须填写！');
									return false;
								}
								if (isEmpty($scope.order.feeAmount)) {
									$uibMsgbox.error('订单金额必须填写！');
									return false;
								}
								if (isEmpty($scope.order.courseCount)) {
									$uibMsgbox.error('报班课时必须填写！');
									return false;
								}
								if (isEmpty($scope.order.channel_id)) {
									$uibMsgbox.error('来源渠道必须填写！');
									return false;
								}
								if (isEmpty($scope.order.orderTime)) {
									$uibMsgbox.error('报班时间必须填写！');
									return false;
								}
								

								return true;
							}

							// 订单回填保存并返回成功或失败
							$scope.ordersave = function() {
								if (!$scope.ordercheckform()) {
									return false;
								}
								$scope.ordersave_status=true;
								$scope.order.rescRecId = $scope.currentRec.id;
//								$scope.order.orderTime = $('#orderTime').val();
								$scope.order.student_name=$scope.currentRec.resc.name;
								crm_addOrderService.add($scope.order,
										$scope.orderSaveFormCallBack);
							};

							// 显示删除预约访
							$scope.showPlanVisitRbt = function(trace) {
								trace.showPlanVisit = true;
							}
							// 隐藏删除预防
							$scope.hidePlanVisitRbt = function(trace) {
								trace.showPlanVisit = false;
							}

							// 删除指定预约访问
							$scope.removePlanVisit = function(trace) {
								// 如果是预约访记录，就可以删除
								if (trace.optType == 3) {
									if (confirm('确认删除此约访记录？')) {
										crm_VisitPlanService.remove({
											planId : trace.busiId
										}, $scope.removePlanVisitFormCallBack);
									}
								}
							}

							$scope.removePlanVisitFormCallBack = function(data) {
								if (data.status) {
									$uibMsgbox.success('删除成功！');
								} else {
									$uibMsgbox.error('删除失败，请联系管理员！原因：' + data.errMsg);
								}

								crm_queryRescRecProcService.query({
									recId: $scope.trace_id
								}, function (res) {
									$scope.traceInfo = {};
									$scope.traceInfo = res.data;
									for (var i = 0; i < $scope.rescRecProc.length; i++) {
										$scope.rescRecProc[i].selected = '';
									}
								});
							}

							// 编辑预约访
							$scope.editPlanVisit = function(trace) {
								crm_VisitPlanService.querySingle({
									planId : trace.busiId
								}, $scope.planVisitQuerySingleFormCallBack);
							}

							// 查找到此预预防
							$scope.planVisitQuerySingleFormCallBack = function(
									rs) {
								if (rs.status) {
									$scope.showPlanVisit();

									$scope.planvisit = rs.data;
									// 打开约访记录
									$scope.form_plan_visit = true;
									// 关闭资源处理
									$scope.form_proc = false;
									// 关闭订单回填
									$scope.form_order = false;
								} else {
									$uibMsgbox.error('打开预约访编辑错误！请联系管理员！错误原因：' + rs.errMsg);
								}
							}

							// 预约访校验
							$scope.planvisitcheckform = function() {
								if (isEmpty($scope.planvisit.branch_id)) {
									$uibMsgbox.error('预约访校区必须填写！');
									return false;
								}
								if (isEmpty($scope.planvisit.planVisitTime)) {
									$uibMsgbox.error('预约访日期必须填写！');
									return false;
								}
								if (isEmpty($scope.planvisit.planVisitTime2)) {
									$uibMsgbox.error('预约访时间必须填写！');
									return false;
								}
								if (isEmpty($scope.planvisit.planVisitObject)) {
									$uibMsgbox.error('约访对象必须填写！');
									return false;
								}
								return true;
							}
							$scope.planvisit = {};

							// 预约访保存并返回成功或失败
							$scope.plansave = function() {
								if (!$scope.planvisitcheckform()) {
									return false;
								}
								if (!$scope.planvisit.id) {
									$scope.planvisit.rescRecId = $scope.currentRec.id;
								}
								crm_VisitPlanService.add($scope.planvisit,
										$scope.planVisitSaveFormCallBack);
							};

							$scope.saveFormCallBack = function(data) {
								$scope.load();
								if (!data.result) {
									// 保存失败提示错误信息
									$uibMsgbox.error('保存失败:' + data.errMsg);
								} else {
									$uibMsgbox.success('保存成功！');
									// 保存成功后清除form并管理form区域
									$scope.form_proc = false;
									$scope.closeform();
									//刷新页面
									//location.reload();
								}
							},
							// 订单回填回调
							$scope.orderSaveFormCallBack = function(data) {
								$scope.ordersave_status=false;
								if (data.result == null || data.result == '0') {
									// 保存失败提示错误信息
									$uibMsgbox.error('保存失败: ' + data.errMsg);
								} else {
									$uibMsgbox.success('保存成功！');
									// 保存成功后清除form并管理form区域
									$scope.form_order = false;
									$scope.ordercloseform();
								}
								$scope.init();
							},

							// 预约访回填回调
							$scope.planVisitSaveFormCallBack = function(data) {
								if (!data.result) {
									// 保存失败提示错误信息
									$uibMsgbox.error('保存失败:' + data.errMsg);
								} else {
									$uibMsgbox.success('保存成功！');
									// 保存成功后清除form并管理form区域
									$scope.plancloseform();
								}
							},

							// 处理沟通关闭
							$scope.closeform = function() {
								$scope.clearform();
								$scope.closeMoreinfo();
								$scope.form_proc = false;
								$scope.currentRec = {};
								$scope.closeTraceForm();
							};

							// 订单回填关闭
							$scope.ordercloseform = function() {
								$scope.form_order = false;
								$scope.orderclearform();
								$scope.closeMoreinfo();
								$scope.currentRec = {};
								$scope.closeTraceForm();
							};

							// 预约访关闭
							$scope.plancloseform = function() {
								$scope.form_plan_visit = false;
								$scope.planclearform();
								$scope.closeMoreinfo();
								$scope.currentRec = {};
								$scope.closeTraceForm();
							};

							$scope.closeMoreinfo = function() {
								$('#recMoreInfo').hide("slow");
							}

							$scope.remove = function(rec) {
								$uibMsgbox.confirm("您确定要删除资源：【" + rec.serial_no + "】?", function (rs){
									if (rs == 'cancel') {
								    	  return;
								      }else {
								    	  crm_RescRecProcService.remove({
												recId : rec.id
											}, function(data) {
												if (data.status) {
													$uibMsgbox.success(rec.serial_no + '删除成功!');
													$scope.init();
												} else {
													$uibMsgbox.error('请联系管理员！删除失败！原因：' + rs.errMsg);
												}

											}, function(rs) {
												$uibMsgbox.error('请联系管理员！错误原因：' + rs.data);
											}); 
								      }
								});

							}

							// 查看更多信息
							$scope.showMoreInfo = function(rec_Id, target) {
								crm_querySingleRescRecService.query({
									recId : rec_Id
								}, $scope.showMoreInfoCallBack);
							};
							// 查看更多信息回调函数
							$scope.showMoreInfoCallBack = function(res) {
								$scope.moreInfo = {};
								$scope.moreInfo = res.data;
								// 设定窗口的位置
								$("#recMoreInfo").slideToggle("nomal", "swing");
							}

							$scope.trace_id = "";

							// 查看更多信息
							/* 下钻信息_start */
							$scope.showTraceInfo = function(rec) {
								var trace_id = $scope.trace_id;
								$scope.closeAllForm();
								$scope.traceInfoDetail = rec;
								if (rec.is_trace)
									return;
								crm_queryRescRecProcService.query({
									recId: rec.id
								}, function (res) {
									$scope.traceInfo = {};
									$scope.traceInfo = res.data;
									for (var i = 0; i < $scope.rescRecProc.length; i++) {
										$scope.rescRecProc[i].selected = '';
									}
									if (trace_id == rec.id) {
										$scope.trace_id = "";
										$scope.closeAllForm();
									} else {
										$scope.trace_id = rec.id;
										rec.selected = 'selected';
									}
								});
							};
							//报班保存按钮
							$scope.ordersave_status=false;
							$scope.showTraceForm = function(rec) {
								for (var i = 0; i < $scope.rescRecProc.length; i++) {
									$scope.rescRecProc[i].selected = '';
								}
//								if ($scope.trace_id == rec.id) {
//									$scope.closeAllForm();
//								} else {
									$scope.is_form = true;
									$scope.trace_id = rec.id;
									rec.selected = 'selected';
//								}
							}
							$scope.closeAllForm = function() {
								$scope.closeform();
								$scope.ordercloseform();
								$scope.plancloseform();
							}
							$scope.closeTraceForm = function() {
								for (var i = 0; i < $scope.rescRecProc.length; i++) {
									$scope.rescRecProc[i].selected = '';
								}
								$scope.is_form = false;
								$scope.trace_id = "";
							}
							/* 下钻信息_end */

							// 保存 检查表单字段
							$scope.checkforms = function() {
								if (!$scope.newRescRec.branch_id) {
									$uibMsgbox.error('业务学校必须填写！');
									return false;
								}
								if (!$scope.newRescRec.res_type_obj) {
									$uibMsgbox.error('资源类型必须选择！');
									return false;
								}
							
								var visitTime = $("#visit_time").val();
								if (isEmpty(visitTime)) {
									$uibMsgbox.error('资源日期必须选择！');
									return false;
								}
								if (isEmpty($scope.resource.call)) {
									$uibMsgbox.error('称呼必须填写！');
									return false;
								}
								if (isEmpty($scope.resource.mp)) {
									$uibMsgbox.error('联系电话必须填写！');
									return false;
								}
								if (isEmpty($scope.resource.attend_sch_name)) {
									$uibMsgbox.error('就读学校必须填写！');
									return false;
								}
								if (!$scope.newRescRec.grade_obj) {
									$uibMsgbox.error('年级必须选择！');
									return false;
								}
								if (!$scope.newRescRec.clue_stage_obj) {
									$uibMsgbox.error('资源进展必须选择！');
									return false;
								}
								if (isEmpty($scope.newRescRec.channel_obj)) {
									$uibMsgbox.error('来源渠道必须选择！');
									return false;
								}
								var flag = true;

								if (!isEmpty($scope.linksList)
										&& $scope.linksList.length == 1
										&& $scope.linksList[0].name == ''
										&& isEmpty($scope.linksList[0].rela_id)
										&& isEmpty($scope.linksList[0].sex)
										&& isEmpty($scope.linksList[0].mp)) {
									$scope.linksList = null;
									$('#linksList').remove();
								}

								$($scope.linksList).each(function() {
									if (isEmpty(this.rela_obj)) {
										$uibMsgbox.error('联系人关系必须填写!');
										flag = false;
										return false;
									}

									if (isEmpty(this.link_name)) {
										$uibMsgbox.error('联系人姓名必须填写！');
										flag = false;
										return false;
									}

									if (isEmpty(this.mp)) {
										$uibMsgbox.error('联系人联系电话必须填写!');
										flag = false;
										return false;
									}
								});

								if (!flag) {
									return false;
								}

								return true;
							};
							// 联系人数组下标
							$scope.linkIndex = 0;

							$scope.addLink = function(idx) {
								$scope.linkIndex++;
								$scope.linksList.splice(idx + 1, 0, {});
							};
							$scope.deleteLink = function(idx){
								//删除 通过id去删除
//								toolAjax("/gxhcrm/query/toDelete/"+$scope.linksList[idx].id, 'post','',function(data){
//								},'json');
								$scope.linkIndex--;
								$scope.linksList.splice(idx, 1);
							};
							
							// 声明对象
							$scope.newRescRec = {
								ordered : '0'
							};
							// 年级
							$scope.setGradesList = function(data) {
								$scope.grades = [];
								$scope.grades = data;
							}
							// 资源类型
							$scope.setResTypeList = function(data) {
								$scope.resTypes = [];
								$scope.resTypes = data;

								/* 资源类别_start */
								$scope.resTypesSearch = [];
								$scope.resTypesSearch.push({
									"id" : -1,
									"name" : "全部"
								});
								for (var i = 0; i < data.length; i++) {
									$scope.resTypesSearch.push(data[i]);
								}
								$scope.searchParam.resType = $scope.resTypesSearch[0];
							};
							// 第三栏 关系（父子 母子..）

							$scope.setRelationList = function(data) {
								$scope.Relations = [];
								$scope.Relations = data;
							};
							/*-------------修改_strat-------------------*/
							// 点击修改按钮	
							$scope.companylist={};
							$scope.updateResourceRec = function(rec) {
								if($scope.form_input){
									$uibMsgbox.error('请先关闭修改框！');
									return;
								}
								
								if (!$scope.form_input) {
									$scope.form_input = true;
								}
								
								if(rec.resource_quality==null){
									rec.resource_quality=1;
								}
								$scope.initnumber(rec.resource_quality);
								$('#selectnumber').val(rec.resource_quality);		
								$scope.form_input_more = true;
								$scope.ischeck = false;
								$scope.clearForm();
								// 第三栏 基础信息
								crm_queryRescinfoService.query({
									recId : rec.id
								}, function(data) {
									$scope.linksList = data.linkData;
									$scope.companylist=data.compang;
									$.each($scope.linksList, function(i, link) {
										// 成交意向
										link.rela_obj = getSelects(
												link.rela_id,
												$scope.Relations);
										// 成交意向
										link.sex_obj = getSelects(
												link.sex,
												$scope.sexoptions);
									});
//									$scope.linksList = $scope.copy(rec.resc.rescLinks);
								});
								
								// 已经有的可以直接复制
								$scope.newRescRec = rec;
								console.log($scope.newRescRec);
								
								$scope.newRescRec.Resource_quality=$scope.newRescRec.resource_quality;
								// 年级
								$scope.newRescRec.grade_obj = getSelects(
										$scope.newRescRec.grade_id,
										$scope.grades);
								// 资源类别
								$scope.newRescRec.res_type_obj = getSelects(
										$scope.newRescRec.res_type,
										$scope.resTypes);
								// 资源进展
								$scope.newRescRec.clue_stage_obj = getSelects(
										$scope.newRescRec.clue_stage_id,
										$scope.ClueStages);
								
								console.log($scope.newRescRec.resc.sex);
							
								// 资源进展
								$scope.newRescRec.resc.sex_obj = getSelects(
										$scope.newRescRec.resc.sex,
										$scope.sexs);
								
								// 来源渠道
								if (!$scope.channels) {
									$scope.changeResType(rec.res_type, true);
								} else {
									$scope.newRescRec.channel_obj = getSelects(
										$scope.newRescRec.channel_id,
										$scope.channels);
								}

								// 成交意向
								$scope.newRescRec.bargain_intent_obj = getSelects(
										$scope.newRescRec.bargain_intent_id,
										$scope.BargainIntents);
								
								// 是否报名
								$scope.newRescRec.ordered_obj = getSelects(
										$scope.newRescRec.ordered,
										$scope.orderedoptions);
								
								// 称呼
								$scope.resource.call = $scope.newRescRec.resc.call;
								// 就读学校
								$scope.resource.attend_sch_name = $scope.newRescRec.resc.attend_sch_name;
								// 学生姓名
								$scope.resource.name = $scope.newRescRec.resc.name;
								// 联系电话
								$scope.resource.mp = $scope.newRescRec.resc.mp;
								// 班级
								$scope.resource.classinfo = $scope.newRescRec.resc.classinfo;
								$scope.copy($scope.resource, rec.resc, true);
								if ($scope.resource.rela_id) {
									$scope.resource.rela_id = "" + $scope.newRescRec.resc.rela_id;
								}

								if (!$scope.newRescRec.ordered) {
									$scope.newRescRec.ordered = 0;
								}
							}
							$scope.form_input = false;
							// 清理from
							$scope.clearForm = function() {
								$scope.linksList = [ {
									name : ''
								} ];
								$scope.newRescRec = {
									ordered : '0'
								};
								$scope.resource = {
									sex : '1'
								};
								$scope.ischeck = false;
							};
							// 下拉更多
							$scope.inputMore = function() {
								if ($scope.form_input_more) {
									$scope.form_input_more = false;
								} else {
									$scope.form_input_more = true;
								}
							}

							// 保存并返回成功或失败
							$scope.saves = function() {
								if (!$scope.checkforms()) {
									return false;
								}
								console.info($scope.newRescRec);
								// 保存数据导数据库
								toolAjax("/gxhcrm/rescRecInput/toAdd", 'post',
										$('#rescRecForms').serialize(),
										$scope.saveFormCallBack, 'json');
								$scope.form_input = false;
								$scope.form_input_more = false;
							};
							// 关闭
							$scope.closeForm = function() {
								$scope.clearForm();
								$scope.form_input = false;
								$scope.form_input_more = false;
								$scope.currentRec = {};
								$scope.closeTraceForm();
//								location.reload();
							};

							$scope.newRescRec.grade_id = null;
							// 重置
						/*	$scope.clearFormFun = function() {
								var serialNo = "";
								if ($scope.newRescRec.id) {
									getSequenceNumService.query({},
											$scope.setSerialNo);
								}
								serialNo = $scope.newRescRec.serial_no;

								$scope.resource = {
									sex : '1'
								};
								$scope.newRescRec = {
									ordered : '0',
									serial_no : serialNo
								};
								$scope.linksList = [ {
									name : ''
								} ];
								$scope.ischeck = false;
							};*/

							// 保存
							// 保存并返回成功或失败
					/*		$scope.save = function() {
								if (!$scope.checkform()) {
									return false;
								}
								// 保存数据导数据库
								toolAjax(contextPath
										+ "/gxhcrm/rescRecInput/toAdd", 'post',
										$('#rescRecForm').serialize(),
										$scope.saveFormCallBack, 'json');
								
							};*/

							/*--------------修改_end---------------------------*/

							$scope.closeWindow = function(obj) {
								$(obj).hide("slow");
							};

							$scope.setBranchs = function(data) {
								$scope.branchs = data;
								$scope.branchs.unshift({
									'branchId' : -1,
									'branchName' : '全部'
								});
								$scope.newRescRec.branch_id = $scope.branchs[0].branchId;
							};

							$scope.setBus = function(data) {
								$scope.bus = [];
								$scope.bus = data;

								$scope.newRescRec.bu_id = null;
								$scope.newRescRec.bu_id = $scope.bus[0].buId;
								queryBranchs($scope.newRescRec.bu_id,
										$scope.setBranchs);
							};

							$scope.setClueStageList = function(data) {
								$scope.ClueStages = [];
								$scope.ClueStagesSeach = [];
								$scope.ClueStagesSeach.push({
									"id" : -1,
									"name" : "全部"
								});
								for (var i = 0; i < data.length -1; i++) {
									$scope.ClueStagesSeach.push(data[i]);
									$scope.ClueStages.push(data[i]);
								}
								$scope.searchParam.clue_stage_obj = $scope.ClueStagesSeach[0];
							};
							$scope.setBargainIntentList = function(data) {
								$scope.BargainIntents = [];
								$scope.BargainIntents = data;
							};

							$scope.setVisitwaysList = function(data) {
								$scope.Visitways = [];
								$scope.Visitways = data;
							};
							$scope.setChannelList = function (data, initSelectorValue) {
								$scope.channels = [];
								$scope.channels = data;
								if (initSelectorValue) {
									$scope.newRescRec.channel_obj = getSelects(
										$scope.newRescRec.channel_id,
										$scope.channels);
								}
							};

							// 查询资源情况
							crm_queryDictDataService.query({
								dictTypeCode : 'ClueStage'
							}, $scope.setClueStageList);
							// 成交意向
							crm_queryDictDataService.query({
								dictTypeCode : 'BargainIntent'
							}, $scope.setBargainIntentList);
							// 回访方式
							crm_queryDictDataService.query({
								dictTypeCode : 'Visitway'
							}, $scope.setVisitwaysList);
							/*------------------*/
							// 年级
							crm_queryDictDataService.query({
								dictTypeCode : 'grade'
							}, $scope.setGradesList);
							// 资源类型
							crm_queryDictDataService.query({
								dictTypeCode : 'ResType'
							}, $scope.setResTypeList);
							// 查询关系列表
							crm_queryDictDataService.query({
								dictTypeCode : 'Relation'
							}, $scope.setRelationList);
							
							$scope.changeResType = function (parentDataId, initSelectorValue) {
								// 查询渠道列表
								crm_queryDictDataService.query({
									dictTypeCode: 'Channel',
									parentDataId: parentDataId
								}, function (data) {
									$scope.setChannelList(data, initSelectorValue);
								});
							}
							/*-----------------*/

							$scope.changeCompany = function() {
								queryBranchs($scope.newRescRec.bu_id,
										$scope.setBranchs);
							};
							/**
							 * 将所有 s 的属性复制给 r
							 * 
							 * @param r
							 *            {Object}
							 * @param s
							 *            {Object}
							 * @param is_overwrite
							 *            {Boolean} 如指定为 false ，则不覆盖已有的值，其它值 包括
							 *            undefined ，都表示 s 中的同名属性将覆盖 r 中的值
							 */
							$scope.copy = function(r, s, is_overwrite) { // TODO:
								if (!s || !r)
									return r;

								for ( var p in s) {
									if (is_overwrite !== false || !(p in r)
											|| p != 'id') {
										r[p] = s[p];
									}
								}
								return r;
							}
							/*
							 * 
							 * 关于select使用的说明：<br/> <p>1、如果select绑定了一个对象数组，那么ng-model对应到这个数组的一个item;</p>
							 * <p>2、如果select绑定了一个对象数组，那么ng-model初始化的时候，必须是这个数组里边的一个item;</p>
							 * <p>3、一个<option>元素，被硬编码塞到select元素中作为空元素，作为未选中状态;</p>
							 * 
							 */
							function getSelects(id, datas) {
								var row;
								$.each(datas, function(i, model) {
									if (id == model.id) {
										row = model;
									}
								});
								return row;
							}

							function Format(fmt, date) { // author: meizz
								var o = {
									"M+": date.getMonth() + 1, // 月份
									"d+": date.getDate(), // 日
									"h+": date.getHours(), // 小时
									"m+": date.getMinutes(), // 分
									"s+": date.getSeconds(), // 秒
									"q+": Math
										.floor((date.getMonth() + 3) / 3), // 季度
									"S": date.getMilliseconds()
									// 毫秒
								};
								if (/(y+)/.test(fmt))
									fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
										.substr(4 - RegExp.$1.length));
								for (var k in o)
									if (new RegExp("(" + k + ")").test(fmt))
										fmt = fmt.replace(RegExp.$1,(RegExp.$1.length == 1) ? (o[k])
												: (("00" + o[k]).substr(("" + o[k]).length)));
								return fmt;
							}
						} ]);
