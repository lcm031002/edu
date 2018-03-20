"use strict";
angular.module('ework-ui')
		.controller(
				'crmRescRecInputController',
				[
						'$rootScope',
						'$scope',
						'crm_queryDictDataService',
						'crm_queryBranchsService',
						'crm_queryGradesService',
						'crm_querySingleRescRecService',
						'crm_queryRescRecProcService',
						'crm_queryAschListService',
						'crm_queryTeacherListService',
						'crm_queryCnselorListService',
						'crm_queryRescRecListService',
						'crm_queryCurrentUserInfoService',
						'crm_getSequenceNumService',
						'crm_loadDataRescRecService',
						'crm_LoginUserService',
						'$http',
						'$uibMsgbox',
						'crm_isSchoolAdminService',
						'crm_hasInputRightService',
						'crm_BranchsVisibleService',
						'crm_checkRepeatService',
						'crm_RescRecProcService',
						'crm_subjectService',
						function($rootScope, $scope, crm_queryDictDataService,
								crm_queryBranchsService, crm_queryGradesService,
								crm_querySingleRescRecService,
								crm_queryRescRecProcService, crm_queryAschListService,crm_queryTeacherListService,
								crm_queryCnselorListService,
								crm_queryRescRecListService,
								crm_queryCurrentUserInfoService,
								crm_getSequenceNumService, crm_loadDataRescRecService,
								crm_LoginUserService,$http,$uibMsgbox,crm_isSchoolAdminService,
								crm_hasInputRightService,crm_BranchsVisibleService,crm_checkRepeatService,
								crm_RescRecProcService,crm_subjectService) {
							/*--------------星级插件_strat-----------*/
							$scope.initnumber=function(number){
								$(".my-rating")
								.starRating(
										{	
											starSize : 40,
											initialRating :number,//初始化的星星数量
											useFullStars : true,
											disableAfterRate : false,
											callback : function(
													currentRating, $el) {
												$('#selectnumber').val(currentRating
												.toString());
											}
										});
							}
							/*--------------星级插件_end-------------*/
							$scope.popW_trace = null;

							$scope.searchParam = {};

							// 资源列表
							$scope.resourceRecsInput = {};

							$scope.linksList = [ {
								name : ''
							} ];
							
							$scope.sexs = [
								{id: "1",  text:"男"  },
							    {id: "0",  text:"女"  },
							];

							$scope.newRescRec = {
								ordered : '0'
							};

							$scope.resource = {
								sex : '1'
							};
							
							$scope.subjectList = [];
							$scope.selSubjectList = [];
							$scope.subjectSearchInfo = '';

							$scope.qualitys = [ {
								id : 1,
								name : '高'
							}, {
								id : 2,
								name : '中'
							}, {
								id : 3,
								name : '低'
							} ];
							$scope.isSuc = false;
							// 咨询师列表
							$scope.cnselors = [];
							$scope.aschs = [];
							$scope.selected_cnsl = 0;

							// 联系人数组下标
							$scope.linkIndex = 0;

							// 资源更多信息
							$scope.moreInfo = {};

							// 资源跟踪列表
							$scope.traceInfo = {};
							
							$scope.init = function() {
								$scope.searchParam.p_isInput = "1";
								// 查询当前用户信息
								crm_queryCurrentUserInfoService.query({},function(res) {
									$scope.user = res.user;
									if ($scope.user) {
										$scope.newRescRec.cnselor_id = $scope.user.employeeId;
										$scope.newRescRec.cnselor_name = $scope.user.employeeName;
									}
								});
								
								crm_isSchoolAdminService.query({}, function(res) {
									$scope.isSchoolAdmin=res.flag;
								});
								
								crm_hasInputRightService.query({}, function(res) {
									$scope.hasInputRight=res.flag;
									// console.log($scope.hasInputRight);
								});
								
								crm_subjectService.query({}, function(res) {
									$scope.subjectList =  res.data;
								});
								
								// 分页
								agPageObject($scope, crm_loadDataRescRecService,
										$scope.searchParam);
								$scope.totalPage = 0;
							};

							$scope.init();
							
							  $scope.removeSubject = function (subject) {
							        subject.checked = false;
							        _.remove($scope.selSubjectList, subject);
							    };
							    
							    $scope.handleSubjectChange = function (subject) {
							        if (subject.checked && !_.some($scope.selSubjectList, subject)) {
							            $scope.selSubjectList.push(subject)
							        } else {
							            _.remove($scope.selSubjectList, subject);
							        }
							    };

							$scope.setCurrentUserInfo = function(data) {
								$scope.user = data.user;
								if ($scope.user) {
									$scope.newRescRec.cnselor_id = $scope.user.employeeId;
									$scope.newRescRec.cnselor_name = $scope.user.employeeName;
								}
							};

							$scope.pageCallBack = function() {
								var newArray = new Array();
								$scope.isLoading = '';
								if($scope.items != null) {
									$.each($scope.items, function(i, model) {
										model.is_trace = false;
										newArray.push(model);
										newArray.push({
											is_trace : true,
											id : model.id
										});
									});
								}
								$scope.resourceRecsInput = newArray;
                                $scope.pageConf.totalItems = $scope.total || 0;
							}

							// 清理form
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

							// 清理form
							$scope.clearFormFun = function() {
								var serialNo = "";
								if ($scope.newRescRec.id) {
									crm_getSequenceNumService.query({},
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
							};
							
							$scope.checkform = function() {
								if (!$scope.branchId) {
									$uibMsgbox.error('业务校区必须选择！');
									return false;
								}
								if (!$scope.newRescRec.res_type_obj) {
									$uibMsgbox.error('资源类型必须选择！');
									return false;
								}
								if (isEmpty($scope.newRescRec.visit_time)) {
									$uibMsgbox.error('资源日期必须选择！');
									return false;
								}
								if (isEmpty($scope.resource.rela_id)) {
									$uibMsgbox.error('联系人关系必须填写！');
									return false;
								}
								if (isEmpty($scope.resource.call)) {
									$uibMsgbox.error('联系人称呼必须填写！');
									return false;
								}
								if($scope.rescRecForm.$invalid) {
									$uibMsgbox.error('联系电话格式不对！');
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
								
								if (!isEmpty($scope.recommend_teacher) && ($scope.recommend_teacher_id == null)) {
									$uibMsgbox.error('推荐老师必须选择！');
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
									if (isEmpty(this.rela_id)) {
										$uibMsgbox.error('联系人关系必须填写!');
										flag = false;
										return false;
									}

									if (isEmpty(this.name)) {
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
							$scope.form_input = false;
							$scope.form_upload = false;
							$scope.form_operate = "add";
							// 修改
							$scope.updateResourceRec = function(rec) {
								window.location.href = '#modify'
								if($scope.form_input){
									$uibMsgbox.error('请先关闭修改框！');
									return;
								}
								if (!$scope.form_input) {
									$scope.form_input = true;
								}
								if (!$scope.form_upload) {
									$scope.form_upload = false;
								}
								$scope.form_operate = "update";

								if(rec.resource_quality==null){
									rec.resource_quality=0;
								}
								$scope.initnumber(rec.resource_quality);
								$('#selectnumber').val(rec.resource_quality);							
								$scope.form_input_more = true;
								$scope.ischeck = false;
								$scope.clearForm();
								$scope.newRescRec = rec;
								
								$scope.selSubjectList = [];
								
								if(!isEmpty($scope.newRescRec.subject_intend)) {
									var subjectArr = $scope.newRescRec.subject_intend.split(",");
									
									for (var i = 0; i < subjectArr.length; i++) {
										var sName = subjectArr[i];
										for(var j = 0; j< $scope.subjectList.length;j++) {
											var tmpSub = $scope.subjectList[j];
											if(sName == tmpSub.name) {
												tmpSub.checked = true;
												$scope.selSubjectList.push(tmpSub);
											}
										}
									}
								};
								
								$scope.recommend_teacher = rec.recommend_teacher_name;
								$scope.recommend_teacher_id = rec.recommend_teacher_id;
								$scope.newRescRec.Resource_quality=$scope.newRescRec.resource_quality;
								$scope.newRescRec.bu_obj = getSelects(
									$scope.newRescRec.bu_id,
									$scope.bus);
								$scope.newRescRec.branch_obj = getSelects(
									$scope.newRescRec.branch_id,
									$scope.branchs);
								$scope.newRescRec.res_type_obj = getSelects(
										$scope.newRescRec.res_type,
									$scope.resTypes);
								if (!$scope.channels) {
									$scope.changeResType(rec.res_type, true);
								} else {
									$scope.newRescRec.channel_obj = getSelects(
										$scope.newRescRec.channel_id,
										$scope.channels);
								}

								$scope.newRescRec.grade_obj = getSelects(
										$scope.newRescRec.grade_id,
										$scope.grades);
								$scope.newRescRec.clue_stage_obj = getSelects(
										$scope.newRescRec.clue_stage_id,
										$scope.ClueStages);
								$scope.newRescRec.bargain_intent_obj = getSelects(
										$scope.newRescRec.bargain_intent_id,
										$scope.BargainIntents);
								// 资源进展
								$scope.newRescRec.resc.sex_obj = getSelects(
										$scope.newRescRec.resc.sex,
										$scope.sexs);
								
								$scope.copy($scope.resource, rec.resc, true);
								if ($scope.resource.rela_id) {
										$scope.resource.rela_id = "" + $scope.resource.rela_id;
								}
								$scope.copy($scope.linksList,
										rec.resc.rescLinks, true);
								if ($scope.linksList && $scope.linksList.length > 0) {
									$.each($scope.linksList, function(idx, link) {
										if (link.rela_id) {
											link.rela_id = "" + link.rela_id;
										}
									});
								}
								if (!$scope.newRescRec.ordered) {
									$scope.newRescRec.ordered = 0;
								}
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
													// 分页
													$scope.query();
												} else {
													$uibMsgbox.error('请联系管理员！删除失败！原因：' + rs.errMsg);
												}

											}, function(rs) {
												$uibMsgbox.error('请联系管理员！错误原因：' + rs.data);
											}); 
								     }
								});
							}
							

							// 资源录入
							$scope.add = function() {
								if (!$scope.hasInputRight) {
									$uibMsgbox.warn('在当前团队下没有录入权限，请切换团队');
									return;
								}
								if ($scope.form_input) {
									return;
								}
								$scope.form_operate = "add";
								$scope.initnumber(1);
								$('#selectnumber').val(1);
								$scope.form_input = true;
								$scope.form_input_more = false;
								$scope.ischeck = false;
								$scope.clearForm();
								if ($scope.bus && $scope.bus.length > 0) {
									$scope.newRescRec.bu_obj = $scope.bus[0];
								}
								if ($scope.branchs && $scope.branchs.length > 0) {
									$scope.newRescRec.branch_obj = $scope.branchs[0];
								}
								if ($scope.user) {
									$scope.newRescRec.cnselor_id = $scope.user.employeeId;
									$scope.newRescRec.cnselor_name = $scope.user.employeeName;
								}
								$scope.newRescRec.visit_time = moment().format('YYYY-MM-DD');
								$scope.newRescRec.resc = {};
								$scope.newRescRec.resc.sex_obj = getSelects(
										"1",
										$scope.sexs);
								console.log($scope.newRescRec.resc.sex_obj);
								
								$scope.recommend_teacher = '';
								$scope.recommend_teacher_id = null;
								$scope.selSubjectList = [];
								
								crm_getSequenceNumService.query({},
										$scope.setSerialNo);
								// console.log($scope.newRescRec);
							};
							
							// 资源录入
							$scope.upload = function() {
								if ($scope.form_upload) {
									return;
								}
								$scope.form_operate = "upload";
								$scope.form_upload = true;
								$scope.form_input = false;
							};
							
							
							$scope.fileName = "";
							
							$scope.fileChange = function() {
								// console.log("fileChange");
								var datafile = document.querySelector('input[name=fileupload]').files[0];
								if(typeof(datafile) != "undefined") {
									$scope.fileName = datafile.name;
									// console.log($scope.fileName);
								}
							};
							
							$scope.uploadFile = function() {
								 var fd = new FormData();
							     var datafile = document.querySelector('input[name=fileupload]').files[0];
							     fd.append("file", datafile);
							     $scope.fileName = datafile.name;
							     _uibModalInstance = $uibMsgbox.waiting('数据保存中，请稍候...');
							    //  console.log(datafile);
							     $http({
						                method:'POST',
						                url   : '/gxhcrm/rescRecInput/uploadfile',
						                data: fd,
						                headers: {'Content-Type':undefined},
						                transformRequest: angular.identity
						            }).success(function (response) {
						                // console.log(response.result);
						            	if(_uibModalInstance != null){
											_uibModalInstance.close();
										}
						                if(response.result) {
						                	$uibMsgbox.success('数据导入成功！');
						                	$scope.query();
						                	$scope.form_upload = false;
											$scope.form_input = false;
											$scope.fileName = "";
						                }else {
						                	$uibMsgbox.error('数据导入失败！');
						                }
						            }).error(function () {
						            	if(_uibModalInstance != null){
											_uibModalInstance.close();
										}
						            });
							};
							
							$scope.downloadTempalte = function() {
								location.href = "/gxhcrm/rescRecInput/downloadTemplatefile";
							};

							// 获取资源编号
							$scope.setSerialNo = function(rs) {
								$scope.newRescRec.serial_no = rs.serialNo;
							}

							// 关闭
							$scope.closeForm = function() {
								$scope.clearForm();
								$scope.form_input = false;
								$scope.form_upload = false;
								$scope.form_input_more = false;
//								location.reload();
							};

							$scope.newRescRec.grade_id = null;
							var _uibModalInstance = null;

							// 保存并返回成功或失败
							$scope.save = function() {
								if (!$scope.checkform()) {
									return false;
								}
								
								// 保存数据导数据库
								var data = $('#rescRecForm').serializeArray();
								data.push({
									name:'visit_time',
									value:$scope.newRescRec.visit_time
								});
								if($scope.newRescRec.id != null){
									data.push({
										name:'id',
										value:$scope.newRescRec.id
									});
								}
								if(typeof($scope.newRescRec.resc) != "undefined" && $scope.newRescRec.resc.id != null){
									data.push({
										name:'resc.id',
										value:$scope.newRescRec.resc.id
									});
								}
								if(typeof($scope.resource.mp) != "undefined" && $scope.resource.mp != null){
									data.push({
										name:'resc.mp',
										value:$scope.resource.mp
									});
								}
								var selSubject = [];
								angular.forEach($scope.selSubjectList, function(data,index,array){
									//data等价于array[index]
									selSubject.push(array[index].name);
								});
					            data.push({
									name:'subject_intend',
									value:selSubject.join(",")
								});
					            
								_uibModalInstance = $uibMsgbox.waiting('数据保存中，请稍候...');
								toolAjax("/gxhcrm/rescRecInput/toAdd", 'post',
											data,
											$scope.saveFormCallBack, 'json');
								
							};

							$scope.saveFormCallBack = function(data) {
								if (data.result == null || data.result == '0') {
									if(_uibModalInstance != null){
										_uibModalInstance.close();
									}
									// 保存失败提示错误信息
									$uibMsgbox.error('保存失败！');
								} else {
										if(_uibModalInstance != null){
											_uibModalInstance.close();
										}
										$uibMsgbox.success('保存成功');
										$scope.isSuc = true;
										// 保存成功后清除form并管理form区域
										$scope.clearForm();
										$scope.ischeck = false;
										$scope.form_input = false;
									//刷新页面
//									location.reload();
								}
								$scope.query();
							}
							// 分页							
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
								$scope.searchParam.p_isInput = "1";
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
                                $scope.pageSize = $scope.pageConf.itemsPerPage;
								$scope.currentPage = $scope.pageConf.currentPage;
								$scope.isLoading = 'loading...';
								$scope.resourceRecsInput = [];
								$scope.load();
							}

							$scope.isLoading = '';

							$scope.change_cnselor_name = function() {
								if ($scope.ischeck) {
									$scope.newRescRec.cnselor_id = null;
									$scope.ischeck = false;
								}
							}

							// 咨询师窗口宽度
							$scope.cnselor_win_left = 0;

							// 查询咨询师
							$scope.enter_search_cnselor = function(event,
									target) {
								if (event.keyCode == 13) {
									$scope.search_cnselor(target);
									return false;
								}
							}

							$scope.search_btn_cnselor = function(target) {
								$scope.cnselor_win_left = $(target).offset().left
										- $('#cnselor_div').width();
								$scope.search_cnselor(target);
							}

							$scope.search_cnselor = function(target) {
								$scope.ischeck = false;
								crm_queryCnselorListService.query({cnselorName : encodeURIComponent($scope.newRescRec.cnselor_name)},
												function(data) {
													$scope.cnselors = {}
													$scope.cnselors = data.cnselorList;
													if (!isEmpty($scope.cnselors)
															&& $scope.cnselors.length == 1) {// 只查到一条咨询师直接赋值
														$scope.newRescRec.cnselor_id = $scope.cnselors[0].id;
														$scope.newRescRec.cnselor_name = $scope.cnselors[0].employee_name;
														$scope.ischeck = true;
													} else {// 没查到显示窗口
														$scope.selected_cnselor(target);
													}
												});
							}

							// 咨询师窗口
							$scope.selected_cnselor = function(target) {
								$scope.ischeck = false;
								if ($scope.cnselor_win_left == 0) {
									$scope.cnselor_win_left = $(target)
											.offset().left;
								}
								// 设定窗口的位置
								$("#cnselorlist").css("top",
										$(target).offset().top + 18).css(
										"left", $scope.cnselor_win_left)
										.slideToggle("nomal", "swing");
								if (!isEmpty($scope.newRescRec.cnselor_name)) {
									$scope.ctn_cnselor_name = $scope.newRescRec.cnselor_name;
									$scope.cnSelorSearch();
								}
							}

							// 咨询师查询
							$scope.cnSelorSearch = function() {
								crm_queryCnselorListService.query({cnselorName : encodeURIComponent($scope.ctn_cnselor_name)}, $scope.showCnselorCallBack);
							}

							$scope.ctn_cnselor_name = '';

							$scope.checkRepeat = function() {
								// console.log($scope.resource.mp);
								if (!isEmpty($scope.resource.mp)) {
									crm_checkRepeatService.query({p_mp : encodeURIComponent($scope.resource.mp)},
											function(res) {
												if(res.flag) {
													 $uibMsgbox.confirm('根据联系方式查询，该线索已经由'+'<span class="text-danger">【'+res.data.employee_name+'】</span>在【'+res.data.crea_time+'】录入，是否继续录入？', function (rs) {
														//  console.log(rs);
													      if (rs == 'cancel') {
													    	  $scope.closeForm();
													      }
													    })
												}
											});
									}
							}

							$scope.rescRecList = function(res) {
								if (!isEmpty(res) && !isEmpty(res.data)) {
									$scope.clearForm();
									$scope.newRescRec = res.data[0];
									$scope.copy($scope.resource,
											res.data[0].resc, true);
									$scope.copy($scope.linksList,
											res.data[0].resc.rescLinks, true);
									$scope.form_input = true;
								}
							}

							// 学校查询
							$scope.cnAschSearch = function() {
								crm_queryAschListService.query({schoolName : encodeURIComponent($scope.ctn_school_name)}, $scope.showAschCallBack);
							}

							$scope.showAschCallBack = function(res) {
								$scope.aschs = []
								$scope.aschs = res.aschList;
							}
							/* 就读学校_start */
							$scope.resource.attend_sch_name = '';
							$scope.attendSchWait = false;
							$scope.attendSchResult = [];

							$scope.loadAttendSchData = function() {
								if ($scope.attendSchWait)
									return;
								if (isEmpty($scope.resource.attend_sch_name))
									return;

								$scope.attendSchWait = true;
								crm_queryAschListService.query({attend_sch_name : encodeURIComponent($scope.resource.attend_sch_name),row_num : 10},
												function(Res) {
													$scope.attendSchResult = [];
													if (Res.code == 200)
														if (Res.data)
															for (var i = 0; i < Res.data.length; i++)
																$scope.attendSchResult.push(Res.data[i]);
																$scope.attendSchWait = false;
												});
							}
							$scope.getDropInstance = function() {
								$scope.loadInstance = {
									'AttendSch' : $scope.loadDropAttendSchData
								};
							}
							$scope.openDropMenu = function() {
								$scope.loadAttendSchData();
							}
							$scope.closeDropMenu = function() {
								$scope.attendSchResult = [];
							}
							$scope.selectMenu = function(attendSch) {
								$scope.resource.attend_sch_name = attendSch.attend_sch_name;
								$scope.resource.attend_sch_id = attendSch.attend_sch_id;
								$scope.closeDropMenu();
							}
							/* 就读学校_end */
							
							
							/* 老师_start */
							$scope.recommend_teacher = '';
							$scope.recommend_teacher_id = null;
							$scope.teacherWait = false;
							$scope.teacherResult = [];
							

							$scope.loadTeacherData = function() {
								if ($scope.attendSchWait)
									return;
								if (isEmpty($scope.recommend_teacher))
									return;

								$scope.teacherWait = true;
								crm_queryTeacherListService.query({teacher_name : encodeURIComponent($scope.recommend_teacher),row_num : 10},
												function(Res) {
													$scope.teacherResult = [];
													if (Res.code == 200) {
														if (Res.data) {
															for (var i = 0; i < Res.data.length; i++) {
																  $scope.teacherResult.push(Res.data[i]);
															}
															$scope.teacherWait = false;
//															}
														}
													}
														
												});
							};
							$scope.getDropTeacherInstance = function() {
								$scope.loadInstance = {
									'teacher' : $scope.loadTeacherData
								};
							}
							$scope.openDropTeacherMenu = function() {
								$scope.loadTeacherData();
							}
							$scope.closeDropTeacherMenu = function() {
								$scope.teacherResult = [];
								
							}
							$scope.selectTeacherMenu = function(attendSch) {
								$scope.recommend_teacher = attendSch.teacher_name+"-"+attendSch.encoding;
								$scope.recommend_teacher_id = attendSch.id;
								$scope.closeDropTeacherMenu();
							}
							/* 老师_end */

							// 导出
							$scope.exp = function() {
								if($scope.searchParam.p_start_date) {
									
								}
								if (!$scope.searchParam.p_start_date || !$scope.searchParam.p_end_date) {
									$uibMsgbox.error('请先选择资源日期范围！');
									return false;
								}
								var param = "";
								$scope.searchParam.isInput = "1";
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
								
								for ( var p in $scope.searchParam) {
									param += p + "="+ $scope.searchParam[p] + "&";
								}
								if (param && param != '') {
									param += "1=1"
								}
								if (confirm("确定要导出?")) {
									location.href = "/gxhcrm/export/exportResInput?"+ encodeURI(encodeURI(param));
								}
							};

							$scope.addLink = function(idx) {
								$scope.linkIndex++;
								$scope.linksList.splice(idx + 1, 0, {});
							};
							$scope.deleteLink = function(idx) {
								// 删除 通过id去删除
//								toolAjax("/gxhcrm/query/toDelete/"+ $scope.linksList[idx].id, 'post', '',
//										function(data) {
//										}, 'json');
								$scope.linkIndex--;
								$scope.linksList.splice(idx, 1);
							};

							$scope.trace_id = "";

							// 查看更多信息
							$scope.showTraceInfo = function(rec) {
								$scope.traceInfoDetail = rec;
								crm_queryRescRecProcService.query({
									recId : rec.id
								}, function(res) {
									$scope.traceInfo = {};
									$scope.traceInfo = res.data;
									for (var i = 0; i < $scope.resourceRecsInput.length; i++) {
										$scope.resourceRecsInput[i].selected = '';
									}
									if ($scope.trace_id == rec.id) {
										$scope.trace_id = "";
									} else {
										$scope.trace_id = rec.id;
										rec.selected = 'selected';
									}

								});
							};

							$scope.setBranchs = function(res) {
								$scope.branchs = res.branchs;
								if ($scope.branchs && $scope.branchs.length > 0) {
									$scope.newRescRec.branch_obj = $scope.branchs[0];
									$scope.searchParam.p_branchId = $scope.newRescRec.branch_obj.id;
								}
							};

							$scope.setBus = function(res) {
								$scope.bus = [];
								$scope.bus = res.bus;
								if ($scope.bus && $scope.bus.length > 0) {
									$scope.newRescRec.bu_obj = $scope.bus[0];
									crm_BranchsVisibleService.query({}, $scope.setBranchs);
								}
							};

							$scope.setChannelList = function(data, initSelectorValue) {
								$scope.channels = [];
								$scope.channels = data;
								if (initSelectorValue) {
                  $scope.newRescRec.channel_obj = getSelects(
                      $scope.newRescRec.channel_id,
                      $scope.channels);
								}
							};

							$scope.setGradesList = function(data) {
								$scope.grades = [];
								$scope.grades = data;
							}

							$scope.setBargainIntentList = function(data) {
								$scope.BargainIntents = [];
								$scope.BargainIntents = data;
							};
							$scope.setRelationList = function(data) {
								$scope.Relations = [];
								$scope.Relations = data;
							};
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
								/* 资源类别_end */
							};
							$scope.setDigResList = function(data) {
								$scope.digRes = [];
								$scope.digRes = data;
							};
							

							//资源进展列表
							$scope.setClueStageList = function(data) {
								$scope.Stages = [];
								$scope.ClueStages = [];
								$scope.Stages = data;
								$scope.ClueStages.push({
									"id" : -1,
									"name" : "全部"
								});
								for (var i = 0; i < data.length; i++) {
									$scope.ClueStages.push(data[i]);
								}
								
								$scope.searchParam.clue_stage_obj = $scope.ClueStages[0];
							};
							//搜索资源进展改变
							$scope.SearchClue = function(cluestage){
								$scope.searchParam.clue_stage_obj = cluestage
							}
							// 查询资源情况
							crm_queryDictDataService.query({
								dictTypeCode : 'BargainIntent'
							}, $scope.setBargainIntentList);
							// 查询关系列表
							crm_queryDictDataService.query({
								dictTypeCode : 'Relation'
							}, $scope.setRelationList);
							// 查询资源情况
							crm_queryDictDataService.query({
								dictTypeCode : 'ClueStage'
							}, $scope.setClueStageList);
							// 资源类型
							crm_queryDictDataService.query({
								dictTypeCode : 'ResType'
							}, $scope.setResTypeList);
							// 挖掘资源
							crm_queryDictDataService.query({
								dictTypeCode : 'DigRes'
							}, $scope.setDigResList);
							// 查询团队
							crm_queryBranchsService.query({
								queryType : "getBusByUser"
							}, $scope.setBus);

							crm_queryDictDataService.query({
								dictTypeCode : 'grade'
							}, $scope.setGradesList);

							$scope.changeResType = function(parentDataId, initSelectorValue) {
                // 查询渠道列表
                crm_queryDictDataService.query({
                  dictTypeCode : 'Channel',
									parentDataId : parentDataId
                }, function(data) {
                	$scope.setChannelList(data, initSelectorValue);
                });
							}

							$scope.changeCompany = function() {
								crm_queryBranchsService.query({
									buId : $scope.newRescRec.bu_obj.id,
									queryType : "getBranchsByUser"
								}, $scope.setBranchs);
							};

							$scope.inputMore = function() {
								if ($scope.form_input_more) {
									$scope.form_input_more = false;
								} else {
									$scope.form_input_more = true;
								}
							}

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
									"M+" : date.getMonth() + 1, // 月份
									"d+" : date.getDate(), // 日
									"h+" : date.getHours(), // 小时
									"m+" : date.getMinutes(), // 分
									"s+" : date.getSeconds(), // 秒
									"q+" : Math
											.floor((date.getMonth() + 3) / 3), // 季度
									"S" : date.getMilliseconds()
								// 毫秒
								};
								if (/(y+)/.test(fmt))
									fmt = fmt.replace(RegExp.$1, (date
											.getFullYear() + "")
											.substr(4 - RegExp.$1.length));
								for ( var k in o)
									if (new RegExp("(" + k + ")").test(fmt))
										fmt = fmt
												.replace(
														RegExp.$1,
														(RegExp.$1.length == 1) ? (o[k])
																: (("00" + o[k])
																		.substr(("" + o[k]).length)));
								return fmt;
							}
						} ]);
