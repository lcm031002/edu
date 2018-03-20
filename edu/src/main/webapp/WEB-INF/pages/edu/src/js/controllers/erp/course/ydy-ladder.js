angular.module('ework-ui').controller('erp_ydyLadderController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibModal',
    '$uibMsgbox',
    'erp_gradeService',
    'erp_courseService',
    'erp_courseLadderService',
    function ($rootScope,
              $scope,
              $log,
              $uibModal,
              $uibMsgbox,
              erp_gradeService,
              erp_courseService,
              erp_courseLadderService) {
        $scope.viewCtrl = {
            opType: 'add',
            show: 'list',
            rowOpType: 'add'
        };
        $scope.ladderList = []; // 阶梯管理列表

        $scope.dataList = []; // 阶梯列表

        $scope.deleteDataList = []; // 删除的阶梯列表

        $scope.courseLadderRelList = []; //适用课程

        $scope.deleteCourseLadderRelList = []; // 删除的适用课程

        $scope.img_src = ''; //图片预览

        $scope.searchParam = {
            p_ladder_name: ''
        };

        $scope.pageConf = {
            currentPage: 1,
            totalItems: 0,
            itemsPerPage: 10,
            onChange: function () {
                $scope.query();
            }
        };

        // 上传的文件改变事件
        $scope.onFileChange = function(files) {
            var file = files[0];
            if(!file.type.match(/jpg|jpeg|bmp|png/i)) {
                $uibMsgbox.error("图片格式无效！");
                return;
            }
            var fileReader = new FileReader();
            fileReader.onload = function () {
                $scope.ladderModelData.img_src = this.result;
                $scope.img_src = this.result;
                $scope.$apply();
            };
            fileReader.readAsDataURL(file);
        };

        $scope.ladderModelData = {}; //阶梯列表的模态数据

        // 与表单绑定的数据，用于添加和修改
        $scope.modalData = {};

        // 获取当前页面数据
        $scope.query = function () {
            var queryParam = $scope.searchParam;
            queryParam.pageSize = $scope.pageConf.itemsPerPage;
            queryParam.currentPage = $scope.pageConf.currentPage;
            erp_courseLadderService.query(queryParam, function (resp) {
                if (!resp.error) {
                    $scope.ladderList = resp.data;
                    $scope.pageConf.totalItems = resp.total || 0;

                    // 显示课程阶梯列表
                    $scope.showList();
                } else {
                    $uibMsgbox.error(resp.message)
                }
            })
        };

        $scope.handleQuery = function () {
            $scope.query();
        };

        // 保存课程阶梯
        $scope.handleSaveLadder = function () {
            var commitData = _.cloneDeep($scope.modalData);
            if(!$scope.dataList || $scope.dataList.length < 1) {
                $uibMsgbox.error('阶梯列表：不能为空');
                return;
            }
            if(!$scope.courseLadderRelList || $scope.courseLadderRelList.length < 1) {
                $uibMsgbox.error('适用课程：不能为空');
                return;
            }
            commitData.ladderList = $scope.dataList.concat($scope.deleteDataList);
            commitData.courseLadderRelList = $scope.courseLadderRelList.concat($scope.deleteCourseLadderRelList);
            if($scope.viewCtrl.opType == 'add') {
                erp_courseLadderService.add(commitData, function (resp) {
                    if (!resp.error) {
                        $uibMsgbox.success('添加成功！');
                        $scope.query();
                    } else {
                        $uibMsgbox.error(resp.message);
                    }
                });
            } else if($scope.viewCtrl.opType == 'edit') {
                erp_courseLadderService.update(commitData, function (resp) {
                    if (!resp.error) {
                        $uibMsgbox.success('修改成功！');
                        $scope.query();
                    } else {
                        $uibMsgbox.error(resp.message);
                    }
                });
            }
        };
        // 添加课程
        $scope.insertCourse = function (course) {
        	if (!$scope.modalData.grade_id) {
        		$uibMsgbox.alert("请先选择年级！");
        		return;
        	}
        	
        	if (course.grade_id != $scope.modalData.grade_id) {
        		$uibMsgbox.alert("课程所属年级必须和阶梯所选年级一致！");
        		return;
        	}
        	
            if (_.some($scope.courseLadderRelList, {course_id: course.id})) {
                $uibMsgbox.alert('课程已存在，不用重复添加！');
                return;
            }
            var courseLadderRel = {
                course_id : course.id,
                course_code : course.course_no,
                course_name : course.course_name
            };
            if (course.actual_people_count && course.actual_people_count > 0) {
                courseLadderRel.is_course_book = 1; //已报班
            } else {
                courseLadderRel.is_course_book = 0; //未报班
            }
            $scope.courseLadderRelList.push(courseLadderRel);
        };
        // 移除课程
        $scope.handleRemoveCourse = function (rowData, index) {
            if(rowData.id && rowData.is_course_book && rowData.is_course_book == 1) {
                $uibMsgbox.alert('课程已报名，不能取消绑定！');
                return;
            }
            $uibMsgbox.confirm('确定移除选中课程？', function (result) {
                if(result != 'yes') {
                    return;
                }
                //非新增行
                if(rowData.id) {
                    rowData.status = 2; //删除状态
                    $scope.deleteCourseLadderRelList.push(rowData);
                }
                $scope.courseLadderRelList.splice(index, 1);
            });
        };
        // 添加课程阶梯
        $scope.handleAddLadder = function () {
            $scope.showLadderDetail('add');
        };

        // 修改课程阶梯
        $scope.handleEditLadder = function (item) {
            $scope.showLadderDetail('edit', item);
        };

        // 处理【删除】
        $scope.handleRemoveLadder = function (id) {
            $uibMsgbox.confirm('确定删除选中阶梯？', function (result) {
                if(result != 'yes') {
                    return;
                }
                $scope.remove(id);
            });
        };

        // 显示课程阶梯详情
        $scope.showLadderDetail = function (opType, detail) {
            detail = detail || {
                    ladder_type : 1,
                    ladder_algorithm : 1
                };
            $scope.modalData = _.cloneDeep(detail);
            $scope.dataList = $scope.modalData.ladderList || []; // 阶梯列表
            $scope.courseLadderRelList = $scope.modalData.courseLadderRelList || []; //适用课程
            $scope.deleteDataList = [];
            $scope.deleteCourseLadderRelList = [];
            $scope.viewCtrl.opType = opType;
            $scope.viewCtrl.show = 'detail';
        };

        // 显示课程阶梯列表
        $scope.showList = function () {
            $scope.viewCtrl.show = 'list';
        };

        // 添加阶梯列表项
        $scope.handleRowAdd = function () {
            $scope.rowOpType = 'add';
            $scope.ladderModelData = {};
            $scope.img_src = '';
            $uibModal.open({
                size: 'lg',
                templateUrl: 'templates/block/modal/course-ydy-ladder.modal.html',
                controller: 'erp_ydyLadderModalController',
                scope: $scope
            }).result.then(function () {
                $scope.dataList.push($scope.ladderModelData);
            }, function () { });
        };

        // 修改阶梯列表项
        $scope.handleRowEdit = function (rowData, index) {
            $scope.rowOpType = 'edit';
            $scope.ladderModelData = _.cloneDeep(rowData);
            $scope.img_src = $scope.ladderModelData.img_src || $scope.ladderModelData.img_path;
            $uibModal.open({
                size: 'lg',
                templateUrl: 'templates/block/modal/course-ydy-ladder.modal.html',
                controller: 'erp_ydyLadderModalController',
                scope: $scope
            }).result.then(function () {
                //非新增行
                if($scope.ladderModelData.id) {
                    $scope.ladderModelData.status = 1; //修改状态
                }
                $scope.dataList[index] = $scope.ladderModelData;
            }, function () { });
        };

        // 删除阶梯列表项
        $scope.handleRowDelete = function (rowData, index) {
            $uibMsgbox.confirm('确定移除选中数据？', function (result) {
                if(result != 'yes') {
                    return;
                }
                //非新增行
                if(rowData.id) {
                    rowData.status = 2; //删除状态
                    $scope.deleteDataList.push(rowData);
                }
                $scope.dataList.splice(index, 1);
            });
        };

        // 查询年级
        $scope.queryGrade = function(){
            erp_gradeService.querySelectDatas({},function(resp){
                if(!resp.error){
                    $scope.gradeList = resp.data;
                }else{
                    $uibMsgbox.error(resp.message);
                }
            })
        };

        /**
         * 查询课程
         */
        $scope.queryCourse = function(){
            var param = {
                grade_id:$scope.modalData.grade_id ? $scope.modalData.grade_id : -1,
                business_type:2 //1对1
            };
            erp_courseService.query(param,function(resp){
                if(!resp.error){
                    $scope.selectingCourseList = resp.data;

                }else{
                    $uibMsgbox.alert(resp.message);
                }
            })
        };

        // 删除
        $scope.remove = function (ids) {
            erp_courseLadderService.remove({
                ids: ids
            }, function (resp) {
                if (!resp.error) {
                    $uibMsgbox.success('删除成功！');
                    $scope.query();
                } else {
                    $uibMsgbox.error(resp.message);
                }
            })
        };

        // 状态变化
        $scope.onStatusChange = function (data) {
            erp_courseLadderService.changeStatus({
                id: data.id,
                status: data.status
            }, function (resp) {
                if (!resp.error) {
                    $scope.query();
                } else {
                    $uibMsgbox.error(resp.message);
                }
            })
        };

        // 初始化
        $scope.initial = function () {
            // 查询年级
            $scope.queryGrade();
            // 阶梯类型
            $scope.ladderTypeList = [{id:1, name: '课程累计课时'}];
            // 阶梯算法
            $scope.ladderAlgorithmList = [{id:1, name: '通用算法'}];
            // 查询
            $scope.handleQuery();
        };

        $scope.initial();
    }
]);
