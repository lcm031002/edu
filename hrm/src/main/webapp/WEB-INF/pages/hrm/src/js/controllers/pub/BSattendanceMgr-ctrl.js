/**
 * Created by Liyong.zhu on 2016/6/25.
 */
angular.module('ework-ui')
    .controller('BSattendanceMgrCtrl', [
        '$scope',
        '$log',
        '$state',
        'ProductService',
        'SeasonService',
        'BranchsService',
        'CompanyService',
        'GradeService',
        'SubjectService',
        'PUBAttendanceCourseService',
        'PUBAttendanceCourseTimeService',
        'PUBAttendanceCoursetimeStudentService',
        BSattendanceMgrCtrl]);


function BSattendanceMgrCtrl(
    $scope,
    $log,
    $state,
    ProductService,
    SeasonService,
    BranchsService,
    CompanyService,
    GradeService,
    SubjectService,
    PUBAttendanceCourseService,
    PUBAttendanceCourseTimeService,
    PUBAttendanceCoursetimeStudentService
    ){
    $scope.isLoadingAttendClass = '';
    //待考勤课程列表
    $scope.attendClassList = [];
    //查询参数：归属公司、所属校区、课程季、年级、科目、关键字
    $scope.queryParam = {};
    //考勤状态
    //$scope.attendParam = {};
    //是否显示高级查询
    $scope.isShowSeniorQuery = false;
    //显示课程课次信息
    $scope.showCourseTimes = function(course){
        if(course.isShowCourseTimes){
            course.isShowCourseTimes = false;
            queryPUBAttendanceCourseTime(course);
        }else{
            course.isShowCourseTimes = true;
            queryPUBAttendanceCourseTime(course);
        }
    }
    

    /**
     * 初始化分页数组
     * @param n
     * @returns {Array}
     */
   $scope.initNumber = function(n){
        var list = new Array();
        for (var index = 1;index<=10&&index<=n;index++){
            list.push(index);
        }
        $scope.paginationBars=list;
    }
    
    /**
     * 获取分页导航条
     */
    function changeNumber(){
    	 var list = new Array();
         //如果当前页码跳到了尾页
         if($scope.currentPage==$scope.totalPage){
        	 var $i=$scope.totalPage-9;
        	 var $index=$i<0?1:$i;
        	 for (var index = $index;index<=$scope.totalPage;index++){
                 list.push(index);
             }
        	 $scope.paginationBars=list;
        	 
         }
         //首页
         else if($scope.currentPage==1){
        	 $scope.initNumber($scope.totalPage);
         } else{ //如果当前页码不在尾页
        	 var len=$scope.paginationBars.length;
        	 var lastIndex=$scope.paginationBars[len-1];
        	 if($scope.currentPage< $scope.paginationBars[0]){
        		 $scope.paginationBars.splice(0,0,$scope.paginationBars[0]-1);
        		 $scope.paginationBars.splice(len,1); 
        	 }
        	 else if($scope.currentPage>lastIndex &&$scope.currentPage<$scope.totalPage){
        		 $scope.paginationBars.splice(0,1); 
        		 $scope.paginationBars.splice(len-1,0,lastIndex+1);
        	 }
         }
    }
    
    $scope.queryIndex=function($index){
    	if(($index<=0||$index>$scope.totalPage)&&$scope.totalPage>0)
    		return;
    	 $scope.currentPage=$index;
    	 $scope.queryInfo();
    }
    

    /**
     * 显示高级查询
     */
    $scope.showSeniorQuery = function(){
        if($scope.isShowSeniorQuery){
            $scope.isShowSeniorQuery = false;
        }else{
            $scope.isShowSeniorQuery = true;
        }
    }

    /**
     * 选择参数值
     * @param key
     * @param value
     */
    $scope.selectParam = function(key,value){
        $scope.queryParam[key] = value;
    }

    /**
     * 查询待考勤课程
     */
    $scope.queryInfo = function(){
        queryPUBAttendanceCourse();
    }

    /**
     * 去考勤
     * @param attendClassTimes
     */
    $scope.toAttendListPanel = function(course,attendClassTime){
        $scope.currentCourse = course;
        $scope.currentAttendClassTime = attendClassTime;
        $("#attendCourseTimesStudentListPanel").modal("show");
        queryPUBAttendanceCoursetimeStudent();
    }

    /**
     * 考勤状态修改
     * @param student
     * @param type
     */
    $scope.attendTypeChange = function(student,type){
        student.attendType = type;
    } 

    /**
     * 修改考勤状态
     */
    $scope.updateAttendType = function(){
        var param = {};
        var updateAttendList = [];
        $.each($scope.currentCourse.attendStudentList,function(i,s){
            if(s.checked){
                updateAttendList.push(s);
            }
        });

        if(!updateAttendList||!updateAttendList.length){
            alert("请选择要考勤的学员！");
            return ;
        }

        param.studentList = updateAttendList;
        param.schedulingId = $scope.currentAttendClassTime.schedulingId;
        PUBAttendanceCoursetimeStudentService.update(param,function(resp){
                alert("考勤成功！");
        },function(err){
        	    alert('请联系管理员，批量考勤异常，异常信息：'+err.data.message);
        })
    }

    /**
     *  选择考勤状态
     * @param key
     * @param value
     */
    /*$scope.selectAttendParam = function(key,value){
        $scope.attendParam[key] = value;
    }*/

    /**
     * 外账考勤课程课时信息查询
     * @param course
     */
    function queryPUBAttendanceCourseTime(course){
        var param = {};
        param.courseId = course.course_id;
        course.courseTimeInfoList = [];
        $scope.isAttendClassTime = 'loading...';
        PUBAttendanceCourseTimeService.query(param,function(resp){
            $scope.isAttendClassTime = '';
            course.courseTimeInfoList = resp.data;
        },function(err){
        	alert("获取考勤课次信息异常！，请联系管理员！异常信息：" + err.data.message);
        })
    }

    /**
     * 查询对公系统的考勤课程
     */
    function queryPUBAttendanceCourse(){
        $scope.isLoadingAttendClass = 'loading...';
        $scope.attendClassList = [];
        $scope.queryParam.busiType = 1;
        $scope.queryParam.rows = 9; //页数
        $scope.queryParam.pageSize = $scope.pageSize;
        $scope.queryParam.currentPage = $scope.currentPage;
        $scope.queryParam.totalPage = $scope.totalPage;
        PUBAttendanceCourseService.query($scope.queryParam,function(resp){
        	$scope.attendClassList = resp.data;
            $scope.isLoadingAttendClass = '';
            $scope.currentPage = resp.currentPage;
            $scope.totalPage = resp.totalPage;
            $scope.pageSize = resp.pageSize;
            changeNumber();
        },function(err){
        	alert('系统异常，请联系管理员！ 异常信息：'+err.data.message);
        })
    }

    /**
     * 查询产品线类型定义
     */
    function queryProduct(){
        ProductService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.productList = resp.data;
            }
        })
    }

    /**
     * 课程季参数
     */
    function querySeason(){
        var param = {};
        SeasonService.query(param,function(resp){
        	resp.data.unshift({id:'-1',name:'全部'});
            $scope.seasonList = resp.data;
            $scope.selectedSeason = $scope.seasonList[0];
        },function(err){
        	alert('异常:' + err.data.message+'请联系管理员！');
        })
    }

    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
            	resp.data.unshift({id:'-1',name:'全部'});
                $scope.branchsList = resp.data;
                $scope.selectedBranch = $scope.branchsList[0];
            }
        })
    }


    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
            	resp.data.unshift({id:'-1',name:'全部'});
                $scope.companys = resp.data;
                $scope.selectedCompany = $scope.companys[0];
        },function(err){
        	alert("请联系系统管理员！异常："+err.data.message);
        })
    }

    /**
     * 查询年级
     */
    function queryGradeList(){
        var param = {};
        GradeService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.gradeList = resp.data;
            }
        })
    }

    /**
     * 查询科目
     */
    function querySubject(){
        var param = {};
        SubjectService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.subjectList = resp.data;
            }
        })
    }
    $scope.coursetimeStudentList = null;
    /**
     * 查询单一课次考勤的学员列表
     */
    function queryPUBAttendanceCoursetimeStudent(){
        var param = {};
        param.schedulingId = $scope.currentAttendClassTime.schedulingId;
        PUBAttendanceCoursetimeStudentService.query(param,function(resp){
                $scope.currentCourse.attendStudentList = resp.data;
        },function(err){
        	alert("系统异常，请联系管理员！异常信息："+err.data.message);
        })
    }

    queryPUBAttendanceCourse();
    queryProduct();
    querySeason();
    queryCompanys();
    queryBranchs();
    queryGradeList();
    querySubject();
}