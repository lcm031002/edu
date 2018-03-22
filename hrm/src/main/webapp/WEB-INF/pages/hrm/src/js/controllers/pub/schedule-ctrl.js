/**
 * Created by Liyong.zhu on 2016/6/1.
 */
angular.module('ework-ui')
    .controller('ScheduleCtrl', [
        '$scope',
        '$log',
        '$state',
        'CoursesInfoService',
        'CompanyService',
        'BranchsService',
        'TeacherListService',
        'TeacherScheduleService',
        'CourseTimesService',
        'StudentListService',
        'StudentScheduleService',
        ScheduleCtrl]);

function ScheduleCtrl(
    $scope,
    $log,
    $state,
    CoursesInfoService,
    CompanyService,
    BranchsService,
    TeacherListService,
    TeacherScheduleService,
    CourseTimesService,
    StudentListService,
    StudentScheduleService){
    //课程排课信息列表
    $scope.courseInfoList = [];
    $scope.currentCourse = {};
    //公司列表
    $scope.companyList = [];
    //校区列表
    $scope.branchsList = [];
    //教师搜索列表
    $scope.teacherList = [];
    //课程学员列表
    $scope.studentsList = [];

    //课程列表当前页码
    $scope.currentPage = 1;
    //课程列表当前总页码
    $scope.totalPage = 1;
    //课程列表当前页行数
    $scope.pageSize = 20;
    //教师搜索列表分页信息定义
    $scope.selectedBranch=null;
    $scope.paginationBars = [];
    $scope.initPageNumber=false;
    $scope.teacherPage = {
        currentPage:1,
        totalPage:1,
        pageSize:20,
        paginationBars:[],
        teacher_search_info:null,
        initPageNumber:false
    }
    //选择的校区
    $scope.selectedBranch = null;
    //选择的公司
    $scope.selectedCompany = null;
    

    /**
     * 查询课程排课基本信息
     */
    $scope.queryInfo = function(){
        queryCoursesInfo();
    }

    /**
     * 查询内账学生人数
     * @param course
     */
    $scope.queryERPStudentList = function(course){
        $('#myModal').modal('show');
        $scope.currentCourse.courseId =course.id;
    }

    /**
     * 查询外账学生人数
     * @param course
     */
    $scope.queryPUBStudentList = function(course){
        $('#myModal2').modal('show');
        $scope.currentCourse.courseId =course.id;
    }

    /**
     * 打开教师修改页面
     * @param course
     */
    $scope.searchTeacher = function(course){
        $('#teacherSeacher').modal('show');
        $scope.currentCourse = course;
        queryTeacherList();
    }

    /**
     * 打开课程时间修改页面
     * @param course
     */
    $scope.changeCourse = function(course){
    	$scope.currentCourse=course;
    	if(course.courseBeginDate){
        $scope.currentCourse.startDate= course.courseBeginDate;
        $scope.currentCourse.endDate = course.courseEndDate;

    	}
    	else if('courseDate'in course &&course.courseDate){
            $scope.currentCourse.startDate=course.courseDate;
        	}
        $('#selectedCourseInfoPanel').modal('show');
 
    }

    /**
     * 查询教师列表
     */
    $scope.queryTeacherList = function(){
        queryTeacherList();
    }

    /**
     * 进入编辑状态
     * @param courseInfo
     */
    $scope.aditCourse = function(courseInfo){
        courseInfo.edit = true;
    };

    /**
     * 保存课程排课修改 FIXME
     * @param courseInfo
     */
    $scope.saveCourse = function(course){
    	if(checkSchedule(course)){
    		alert("教师日常检查存在冲突！")
    		return;
    	}
    	//检查是否有学员在同一时间段报了当前的这名课程
    	if(checkConflictByStudent()){
    		alert("学员日常检查存在冲突！")
    		return;
    	}
    	var param = {};
        param.id = course.id;
        param.startTime = course.startTime;
        param.teacherId=course.teacher_id;
     	$log.log("teacher_id="+course.teacher_id);
        param.endTime = course.endTime;
        //如果是课次信息
        if(course.courseDate)
        param.courseDate = (course.courseDate).replace(new RegExp("-", 'g'),"");
        //如果是课程信息
        if(course.courseBeginDate){
       	 param.courseBeginDate = course.courseBeginDate;
         param.courseEndDate = course.courseEndDate;
        }
        CoursesInfoService.saveCourse(param,function(resp){
        	if(resp.error=='false'||resp.error==false)
          alert("保存成功!")
          else if(resp.error=='false'){
        	  
        	  alert("保存失败"+resp.message);
          }
        	$scope.queryCourseIndex($scope.currentPage);
        });
        course.edit = false;
    }

    /**
     * 选择教师确认
     */
    $scope.comfirmSelectedTeacher = function(){
        if(!$scope.selectedTeacher){
            alert("请选择教师！");
            return;
        }
        $scope.currentCourse.tearch_name = $scope.selectedTeacher.name;
        $scope.currentCourse.teacher_id = $scope.selectedTeacher.id;
        $log.log("techaer_id="+$scope.selectedTeacher.id);
        $log.log("techaer_name="+$scope.selectedTeacher.name);
        $('#teacherSeacher').modal('hide');
        //检查老师和学员的上课安排的冲突情况
        checkSchedule($scope.currentCourse);
    }

    /**
     * 修改上课时间
     * @param course
     */
    $scope.updatecCourseInfo = function (course){
        //检查老师和学员的上课安排的冲突情况
        if(course.courseBeginDate){
        	course.courseBeginDate=$scope.getFormatDate(course.startDate,"yyyy-MM-dd");
        	course.courseEndDate=$scope.getFormatDate(course.endDate,"yyyy-MM-dd");
        }
         if(course.courseDate){
        	course.courseDate=$scope.getFormatDate(course.startDate,"yyyy-MM-dd");
        }
        if(course.startDateTime){
        	course.startTime=new Date(course.startDateTime).Format("hh:mm");
        }
        if(course.endDateTime){
        	course.endTime=new Date(course.endDateTime).Format("hh:mm");
        }
        checkSchedule($scope.currentCourse);
        $scope.currentCourse=course;
        $('#selectedCourseInfoPanel').modal('hide');
    }

    
    $scope.getFormatDate=function(formatterDate,format){
    	var date=new Date(formatterDate).Format(format);
    	return date;
    }

    /**
     * 显示老师上课日程冲突信息
     */
    $scope.showScheduleConflict = function(course){
    	 queryTeacherSchedule(course);
         queryStudentList(course);
        $('#scheduleConflictPanel').modal('show');
    };

    /**
     * 展示学员的课程安排
     * @param student
     */
    $scope.showStudentSchedule = function(student){
        $scope.isShowStudentSchedule = true;
        queryStudentSchedule(student.id);
    };
    /**
     * 不展示学员的课程安排
     * @param student
     */
    $scope.concelStudentSchedule = function(){
        $scope.isShowStudentSchedule = false;
    };
    /**
     * 查询单个学员的日程安排的情况
     * @param studentId
     */
    function queryStudentSchedule(studentId){
    	if($scope.studentsList&&$scope.studentsList.length>0){
    	$.each($scope.studentsList,function(i,model){
    		if(mode.id==studentId){
    			$scope.studentScheduleList=model.conflictSchdules;
    			
    		}
    	});
    	}
    }



    /**
     * 展示课程详细课次信息
     * @param course
     */
    $scope.showCourseTimes = function(course){
        if(course.showCourseTime){
            course.showCourseTime = false;
        }else{
            course.showCourseTime = true;
            queryCourseTimes(course);
        }
    }

    /**
     * 查询报班课程下的学员列表，并展示其中上课存在冲突的学员的日程信息
     */
    function queryStudentList(course){
        var param = {};
        param.courseId = course.id;
        if('courseDate' in course){
        	param.startDate=course.courseDate;
        	param.endDate=course.courseDate;
        }
        
        else if('courseBeginDate' in course){
        	param.startDate=course.courseBeginDate;
        	param.endDate=course.courseBeginDate;
        	
        }
        if(course.startTime)
        	param.startTime=course.startTime;
        if(course.endTime)
        	param.endTime=course.endTime;
        	
        $scope.studentsList = [];
        $scope.studentListLoading = 'loading...';
        StudentListService.queryPostData(param,function(resp){
            if(resp.error == 'false'){
                $scope.studentsList = resp.data;
            }
            $scope.studentListLoading = '';

        });
    }

    /**
     * 查询老师的日程安排
     * @param teacher_id 教师id
     */
    function queryTeacherSchedule(course){
        var param = {};
        param.teacherId = course.teacher_id;
        TeacherScheduleService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.teacherScheduleList = resp.data;
                checkConfilctByTeacher(resp.data,course);
            }
        })
    }
    
    //检查课程冲突
    function checkConfilctByTeacher(teacherScheduleList,course){
    	 course.scheduleConflict=false;
    	 var compareBeginDate=course.courseBeginDate?course.courseBeginDate+" "+course.startTime:course.courseDate+" "+course.startTime;
    	 var compareEndDate=course.courseEndDate?course.courseEndDate+" "+course.endTime:course.courseDate+" "+course.endTime;
    	 compareBeginDate=new Date(compareBeginDate);
    	 compareEndDate=new Date(compareEndDate);
    	 var compareBeginTime=compareBeginDate.getHours()*60+compareBeginDate.getMinutes();
    	 var compareEndTime=compareEndDate.getHours()*60+compareEndDate.getMinutes();
    	$.each(teacherScheduleList,function(i,model){
    		if(model['id']!=course['id']){
    		var startDate=new Date(model.startDate);
    		var endDate=new Date(model.endDate);
    		 var startTime=startDate.getHours()*60+startDate.getMinutes();
        	 var endTime=endDate.getHours()*60+endDate.getMinutes();
    		//判断选择的排课日期是否冲突
    		if((startDate>=compareBeginDate)&&startDate
    				<=compareEndDate&&(startTime>=compareBeginTime&&startTime<=compareEndTime)){
    			model.state=1;
    		}
         if((endDate>=compareBeginDate&&endDate<=compareEndDate)&&(endTime>=compareBeginTime&&endTime<=compareEndTime)){
    			model.state=1;
    		}
                 course.scheduleConflict=model.state==1?true:false;//标注为已冲突
                teacherScheduleList[i].scheduleConflict=(model.state==1)?true:false;//课程是否冲突
        		teacherScheduleList[i].stateInfo=model.state==1?'上课冲突':'';
    		}
    	});
    }
    
    //检查学员级别是否存在冲突
    function checkConflictByStudent(){
    	if($scope.studentsList&&$scope.studentsList.length>0){
     	$.each($scope.studentsList,function(i,model){
    		if(mode.id==studentId){
    			if(model.conflictSchdules&&model.conflictSchdules.lenth>0)
    				return true;
    			
    		}
    	});
    	}
    	
     	return false;
    }
  

    /**
     * 课程信息分页查询
     */
    function queryCoursesInfo(){
        var param = {};
        param.pageSize = $scope.pageSize;
        param.currentPage = $scope.currentPage;
        param.totalPage = $scope.totalPage;
        param.p_searchInfo = $scope.search_info;
        if($scope.selectedBranch)
        param.p_selectedBranch=$scope.selectedBranch.id;
        if($scope.selectedCompany){
            param.p_selectedCompany =  $scope.selectedCompany.id;
        }
        if($scope.selectedBranch){
            param.p_selectedBranch =  $scope.selectedBranch.id;
        }
        $scope.courseInfoList = [];
        $scope.isLoadingCourses = 'loading...';

        CoursesInfoService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.courseInfoList = resp.data;
                $scope.currentPage = resp.currentPage;
                $scope.totalPage = resp.totalPage;
                $scope.pageSize = resp.pageSize;
                changeNumber($scope);
            }
            $scope.isLoadingCourses = '';
        });
    }

    /**
     * 选择行
     * @param row
     */
    $scope.checked = function(row){
      	angular.forEach($scope.teacherList, function(data){
		data.checked=false;
		});
        if(row.checked){
            row.checked = false;
            $scope.selectedTeacher = null;
        }else{
            row.checked = true;
            $scope.selectedTeacher = row;
        }
      
    }

    

    /**
     * 查询归属公司信息
     */
    function queryCompany(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.companyList = resp.data;
            }
        })
    }

    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.branchsList = resp.data;
            }
        })
    }
    
    
   $scope.queryBranchsWithId= function (company){
    	var param={};
    	param.companyId=company.id;
    	$log.log("start query");
    	$log.log(param.companyId);
        BranchsService.get(param,function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.branchsList = resp.data;
            }
        })
    }
   $scope.changeParam=function(branch){
	   $scope.selectedBranch=branch;
   }


    /**
     * 教师查询列表
     */
    function queryTeacherList(){
        var param = {};
        $.extend(param,$scope.teacherPage);
        param.p_teacher_search_info=$scope.teacherPage.teacher_search_info;
        $log.log($scope.teacherPage.teacher_search_info);
        $scope.isteacherListLoading = 'loading...';
        TeacherListService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.teacherList = resp.data;
                $scope.teacherPage.currentPage = resp.currentPage;
                $scope.teacherPage.totalPage = resp.totalPage;
                $scope.teacherPage.pageSize = resp.pageSize;
                changeNumber($scope.teacherPage);
            }
            $scope.isteacherListLoading = '';
        })
    }
    
    $scope.queryTeacherIndex=function($index){
    	if(($index<=0||$index>$scope.teacherPage.totalPage)&&$scope.totalPage>0)
    		return;
    	 $scope.teacherPage.currentPage=$index;
    	 $scope.queryTeacherList();
    }
    
    $scope.queryCourseIndex=function($index){
    	if(($index<=0||$index>$scope.totalPage)&&$scope.totalPage>0)
    		return;
    	 $scope.currentPage=$index;
    	 $scope.queryInfo();
    	
    }

    //检查教师上课冲突
    function checkSchedule(course){
    	 queryTeacherSchedule(course);
    	 checkConflictByStudent();
        return course.scheduleConflict;
    }

    /**
     * 查询课程课次信息
     * @param course
     */
    function queryCourseTimes(course){
        var param = {};
        param.courseId = course.id;
        $scope.isLoadingCourseTimes = 'loading...';
        CourseTimesService.get(param,function(resp){
            if(resp.error == 'false'){
                course.courseTimeList = resp.data;
            }
            $scope.isLoadingCourseTimes = '';
        })
    }

    queryBranchs();

    queryCoursesInfo();

    queryCompany();

    /**
     * 获取分页导航条
     */
    function changeNumber($pageModel){
    	 var list = new Array();
         //如果当前页码跳到了尾页
         if($pageModel.currentPage==$pageModel.totalPage){
        	 var $i=$pageModel.totalPage-9;
        	 var $index=$i<0?1:$i;
        	 for (var index = $index;index<=$pageModel.totalPage;index++){
                 list.push(index);
             }
        	 $pageModel.paginationBars=list;
        	 
         }
    
         //首页
         else  if($pageModel.currentPage==1){
        	 for (var index=1;index<=$pageModel.pageSize&&index<=$pageModel.totalPage;){
                 list.push(index);
                 index++;
             }
        	 $pageModel.paginationBars=list;
         }
       //如果当前页码不在尾页
         else{
        	 var len=$pageModel.paginationBars.length;
        	 var lastIndex=$pageModel.paginationBars[len-1];
        	 if($pageModel.currentPage< $pageModel.paginationBars[0]){
        		 $pageModel.paginationBars.splice(0,0,$pageModel.paginationBars[0]-1);
        		 $pageModel.paginationBars.splice(len,1); 
        	 }
        	 else if($pageModel.currentPage>lastIndex &&$pageModel.currentPage<$pageModel.totalPage){
        		 $pageModel.paginationBars.splice(0,1); 
        		 $pageModel.paginationBars.splice(len-1,0,lastIndex+1);
        	 }
         }
         
    	
    }


}

Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}