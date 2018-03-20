angular.module('ework-ui').controller('erp_courseRoomController', [
    '$rootScope',
    '$scope',
    '$log',
    '$uibMsgbox',
    '$uibModal',
    'erp_courseService',
    'erp_studentBuOrgsService',
    'erp_timeSeasonService',
    'PUBORGSelectedService',
    'erp_roomService',
    erp_courseRoomController
]);

function erp_courseRoomController($rootScope,
    $scope,
    $log,
    $uibMsgbox,
    $uibModal,
    erp_courseService,
    erp_studentBuOrgsService,
    erp_timeSeasonService,
    PUBORGSelectedService,
    erp_roomService) {
    $scope.paginationConf = {
        currentPage: 1, //当前页
        totalItems: 0,
        onChange: function () {
            $scope.querySelectingCourse();
        }
    };

    $scope.queryParam = {
        selectedBranch: '',
        selectedTimeSeason: '',
        courseSearchInfo: ''
    };

    // 列表信息
    $scope.dataList = [];

    // 显示课次
    $scope.showCourseTimes = function (course) {
        if (course.isShowCourseTime) {
            course.isShowCourseTime = false;
        } else {
            course.isShowCourseTime = true;
            $scope.queryCourseTimes(course);
        }
    };

    // 查询课次
    $scope.queryCourseTimes = function(course) {
        var param = {};
        param.p_course_id = course.id;
        //course.isQueryCourseTimes = 'isQueryCourseTimes';
        erp_roomService.queryRoomRel(param, function (resp) {
            //course.isQueryCourseTimes = '';
            if (!resp.error) {
                course.courseTimeList = resp.data;
                if (course.courseTimeList && course.courseTimeList.length > 0) {
                    $.each(course.courseTimeList, function (i, model) {
                        var course_date = "" + model.course_date;
                        if (course_date && course_date.length == 8) {
                            model.course_date_str = course_date.substring(0, 4) + "-" + course_date.substring(4, 6) + "-" + course_date.substring(6, 8);
                        }
                        // 反向更新模型的id
                        if($scope.modalData && $scope.modalData.course_id == model.course_id && $scope.modalData.seq == model.seq) {
                            $scope.modalData.id = model.id;
                        }
                    });
                }
            }
        });
    };

    $scope.modalData = {};
    $scope.oldRoomId = undefined; //旧的教室
    $scope.currentCourse = undefined; //当前的课程

    // 设置和解绑弹框
    $scope.toSettings = function (courseTime, course, status) {
        $scope.currentCourse = course;
        $scope.currentCourse.bindStauts = status;
        $scope.oldRoomId = courseTime.room_id;
        angular.copy(courseTime, $scope.modalData);
        // 查询教室
        queryRoom(courseTime.branch_id);

        var modalInstance = $uibModal.open({
            size: 'lg',
            templateUrl: 'course_room_modal.html',
            controller: 'erp_courseRoomModalController',
            scope: $scope
        });
        modalInstance.result.then(function (result) {
        }, function (reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };

    // 批量绑定和解绑弹框
    $scope.showBatchBinding = function (course,status) {
        $scope.batchList = course;
        $scope.batchList.bindStatus = status; 
        $scope.batchList.applyToOther = false;
        angular.forEach($scope.batchList.roomClassSet, function (date) {
            date.Selected = false;
        }); 
        queryRoom(course.branch_id); // 查询教室
        // $scope.queryCourseTimes(course);
        var modalInstance = $uibModal.open({
            size: 'lg',
            templateUrl: 'batch_binding_modal.html',
            controller: 'erp_courseRoomModalController',
            scope: $scope
        });
        modalInstance.result.then(function (result) {
        }, function (reason) {
            $log.info('DrawModal dismissed at: ' + new Date());
        });
    };  

    /**
     * 查询课程
     */
    $scope.querySelectingCourse = function () {
        var param = {
            branch_id: $scope.queryParam.selectedBranch ? $scope.queryParam.selectedBranch.id : -1,
            season_id: $scope.queryParam.selectedTimeSeason ? $scope.queryParam.selectedTimeSeason.id : -1,
            business_type: 1, //班级课
            search_info: $scope.queryParam.courseSearchInfo,
            status: 1 //上架课程
        };
        param.currentPage = $scope.paginationConf.currentPage;
        param.pageSize = $scope.paginationConf.itemsPerPage;
        param.student_num = 'true';

        $scope.toSelectingCourseList = [];

        $scope.loadStatues  = true;
        erp_courseService.query(param, function (resp) {
            $scope.loadStatues = false;
            if (!resp.error) {
                $scope.dataList = resp.data;
                $scope.paginationConf.totalItems = resp.total || 0;
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    };

    /**
     * 查询校区
     */
    function queryBuOrgs() {
        erp_studentBuOrgsService.query({}, function (resp) {
            if (!resp.error) {
                $scope.branchList = resp.data;
                querySelectedOrg();
            }
        })
    }

    /**
     * 查询课程季
     */
    function queryTimeSeason() {
        erp_timeSeasonService.list({}, function (resp) {
            if (!resp.error) {
                $scope.timeSeasonList = resp.data;
            }
        });
    }

    $scope.roomList = [];

    /**
     * 查询教室
     */
    function queryRoom(branch_id) {
        erp_roomService.queryRoom({
            p_branch_id: branch_id
        }, function (res) {
            if (!res.error) {
                $scope.roomList = res.data;
            } else {
                $uibMsgbox.error(res.message);
            }
        });
    }

    function querySelectedOrg() {
        PUBORGSelectedService.query({}, function (resp) {
            if (!resp.error) {
                $scope.selectedOrg = resp.data;
                if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
                    $.each($scope.branchList, function (i, b) {
                        if (b.id == $scope.selectedOrg.id) {
                            $scope.queryParam.selectedBranch = b;
                        }
                    });
                    $scope.branchList = [$scope.queryParam.selectedBranch];
                } else {
                    $scope.queryParam.selectedBranch = $scope.branchList[0];
                }
                $scope.querySelectingCourse();
            } else {
                $uibMsgbox.error(resp.message);
            }
        })
    } 

    $('title').text("课次教室绑定 | 快乐学习");
    queryBuOrgs();
    queryTimeSeason();
}

angular.module('ework-ui')
    .controller('erp_courseRoomModalController', [
        '$scope',
        '$uibModalInstance',
        '$uibMsgbox',
        'erp_roomService',
        erp_courseRoomModalController
    ]);

function erp_courseRoomModalController($scope, $uibModalInstance, $uibMsgbox, erp_roomService) {

    $scope.handleModalCancel = function () {
        $uibModalInstance.dismiss('cancel')
    };

    $scope.handleModalConfirm = function (status) {
        if (status == true) {
            $scope.settingRoom();
        } else {
            $scope.untieRoom();
        }
    }

    //设置教室
    $scope.settingRoom = function () {
        // 原来未选定教室， 现在也未选定教室 或 教室未变化(且未选中应用到其它课次)
        if (!$scope.oldRoomId && !$scope.modalData.room_id ||
            ($scope.oldRoomId == $scope.modalData.room_id )) {
            $uibModalInstance.dismiss('cancel');
            return;
        }

        delete $scope.modalData.course_date_str;
        var _uibModalInstance = $uibMsgbox.waiting('正在绑定教室，请稍候...');
        erp_roomService.saveRoomRel($scope.modalData, function (res) {
            _uibModalInstance.close();
            if (!res.error) {
                $scope.queryCourseTimes($scope.currentCourse);
                $uibModalInstance.close("success");
            } else {
                if ($scope.modalData.applyToOther) {
                    $scope.queryCourseTimes($scope.currentCourse);

                }
                $uibMsgbox.error(res.message.replace(/\n/g, '<br>'));
            }
        });
    }

    //解绑教室
    $scope.untieRoom = function () {
        var _uibModalInstance = $uibMsgbox.waiting('正在解绑教室，请稍候...');
        var params={
            id:$scope.modalData.course_id,
            seq:$scope.modalData.seq,
        }
        erp_roomService.untieRoom(params, function (res) {
            _uibModalInstance.close();
            if (!res.error) {
                $scope.queryCourseTimes($scope.currentCourse);    
                $uibModalInstance.close("success");
            } else {
                if ($scope.modalData.applyToOther) {
                    $scope.queryCourseTimes($scope.currentCourse);
                }
                $uibMsgbox.error(res.message.replace(/\n/g, '<br>'));
            }
        });
    }

    //选择所有未绑定的课次
    $scope.chooseAllCheck = function () {
        if ($scope.batchList.applyToOther == true) {
            angular.forEach($scope.batchList.roomClassSet, function (date) {
                date.Selected = true;
            });
        }else{
            var statusTag = true;
            angular.forEach($scope.batchList.roomClassSet, function (date) {
                date.Selected = false;
                if(date.roomId!=null){
                    statusTag = false;
                }
                if($scope.batchList.bindStatus==true && statusTag == true){
                    date.roomId = null;
                }
            });
            if($scope.batchList.bindStatus==true && statusTag == true){
                $scope.batchList.room_id = null;
            }
        }
    }
    //选择所有未绑定的课次的教室名称
    $scope.chooseAllRoom = function () {
        angular.forEach($scope.batchList.roomClassSet, function (date) {
            date.roomId = $scope.batchList.room_id;
        });
    }
    //选择未绑定星期课次
    $scope.chooseCheck = function (arr) {
        $scope.allTag = true;
        if (arr.Selected == false) {
            $scope.allTag = null;
            var statusTag = true;            
            angular.forEach($scope.batchList.roomClassSet, function (date) {
                if (date.Selected == true) {
                    $scope.allTag = true;
                }
                if(date.roomId!=null){
                    statusTag = false;
                }
            });
            if($scope.batchList.bindStatus==true && statusTag == true){
                arr.roomId = null;
            }
        }
        if($scope.batchList.bindStatus==true && statusTag == true){
            $scope.batchList.room_id = null;
        }
    }
    // 批量绑定    
    $scope.BatchModalConfirm = function (status) {
        if (status == true) {
            if($scope.batchList.applyToOther==true){
                if($scope.batchList.room_id==null){
                    $uibMsgbox.confirm('请选择教室');  
                    return false;                  
                }
            }else{
                var checkTag = true;                
                angular.forEach($scope.batchList.roomClassSet, function (date) {
                    if (date.Selected == true && date.roomId ==null) {
                        checkTag = false;
                    }
                });
                if(checkTag==false){
                    $uibMsgbox.confirm('请选择教室');
                    return false
                }
            }
            $scope.settingAllRoom();
        } else {
            $scope.untieAllRoom();
        }
    }
    $scope.settingAllRoom = function(){
        var params = [];
        angular.forEach($scope.batchList.roomClassSet, function (date) {
            if (date.Selected == true) {
                params.push(date);
            }
        });
        var _uibModalInstance = $uibMsgbox.waiting('正在绑定教室，请稍候...');        
        erp_roomService.saveAllRoom(params, function (res) {
            _uibModalInstance.close();
            if (!res.error) {
                $scope.queryCourseTimes($scope.batchList);      
                $uibModalInstance.close("success");
            } else {
                if ($scope.batchList.applyToOther) {
                    $scope.queryCourseTimes($scope.batchList);
                }
                $uibMsgbox.error(res.message.replace(/\n/g, '<br>'));
            }
        })
    }
    $scope.untieAllRoom = function(){
        var params = [];    
        var statusTag = true;       
        angular.forEach($scope.batchList.roomClassSet, function (date) {
            if (date.Selected == true) {
                date.roomId = null;
                params.push(date)
            }
            if(date.roomId!=null){
                statusTag = false;
            }
        });
        if(statusTag == true){
            $scope.batchList.room_id = null;
        }
        var _uibModalInstance = $uibMsgbox.waiting('正在解绑教室，请稍候...'); 
        erp_roomService.untieAllRoom(params, function (res) {
            _uibModalInstance.close();
            if (!res.error) {
                $scope.queryCourseTimes($scope.batchList);
                $uibModalInstance.close("success");
            } else {
                if ($scope.batchList.applyToOther) {
                    $scope.queryCourseTimes($scope.batchList);
                }
                $uibMsgbox.error(res.message.replace(/\n/g, '<br>'));
            }
        })
    }
}
