/**
 * Created by Liyong.zhu on 2016/7/6.
 */
angular.module('ework-ui')
    .controller('BSsuchMgrCtrl', [
        '$scope',
        '$log',
        '$state',
        'RefundService',
        'BranchsService',
        'CompanyService',
        'GradeService',
        'SeasonService',
        'SubjectService',
        'ProductService',
        'PUBSuchService',
        'PUBExportService',
        BSsuchMgrCtrl]);


function BSsuchMgrCtrl(
    $scope,
    $log,
    $state,
    RefundService,
    BranchsService,
    CompanyService,
    GradeService,
    SeasonService,
    SubjectService,
    ProductService,
    PUBSuchService,
    PUBExportService
    ){
    /**
     * 查询条件参数对象
     * @type {{}}
     */
    $scope.queryCourseParam = {};
    /**
     * 点名表数据
     * @type {Array}
     */
    $scope.suchData = [];
    $scope.isLoad = '';
    
    $scope.pageSearch={
    	startDate:null,
    	endDate:null
    		
    };
    /**
     * 点名表查询服务
     */
    $scope.query = function(){
        //查询数据信息
        var param = $scope.queryCourseParam;

        if($("#beginDate").val()!=""){
            param.startDate = $("#beginDate").val();
        }

        if($("#endDate").val()!=""){
            param.endDate = $("#endDate").val();
        }
        $scope.isLoad = 'loading';
        PUBSuchService.query(param,function(resp){
            $scope.isLoad = '';
            if(resp.error == 'false'){
                $scope.suchData = resp.data;
            }
        });

    }
    /**
     * 导出
     * @param such
     */
    $scope.extPort = function(such,modelId){
        var param = $scope.queryCourseParam;
        param.modelId = modelId;
        if($scope.queryCourseParam['productId']=='1'){
            param.courseId = such.courseId;
        }
        PUBExportService.get(param,function(resp){
            alert("导出成功!");
            //FIXME 导出
        });
    }

    /**
     * 选择条件
     */
    $scope.checkCondition = function(key,value){
        $scope.queryCourseParam[key] = value;
      //如果是归属公司联动
        if(key=='companyId')
        	{
		  $log.log("start query branc.hs.........."+value);
		  queryBranchsWithCompany(value);
        	}
        //如果是校区联动
        else if(key=='branchId')
    	{
        	$scope.shcId=value;
        	$log.log("start query branchs..........."+value);
        	queryWhenBranchChange();
    	}
        $scope.query();
    }

    /**
     * 查询公司信息
     */
    function queryCompanys(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.companyList = resp.data;
            }
        })
    }
    
    /**
     * 查询归属校区
     */
    function queryBranchsWithCompany(value){
        var param = {};
        param.companyId = value;
            BranchsService.get(param,function(resp){
                $scope.isLoadingBranchListSelected = '';
                if(resp.error == 'false'){
                	$scope.branchsList = resp.data;
                }
            });
        
    }
    /**
     * 查询归属公司的可见校区列表
     */
    function queryBranchs(){
        BranchsService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.branchsList = resp.data;
            }
        })
    }

    /**
     * 查询产品线
     */
    function queryProduct(){
        ProductService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.productList = resp.data;
                if($scope.productList&&$scope.productList.length){
                    $scope.queryCourseParam['productId'] = $scope.productList[0].id;
                }
            }
        })
    }

    /**
     * 查询课程季
     */
    function querySeason(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
            param.schId=$scope.shcId;
        }
        SeasonService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.seasonList = resp.data;
            }
        })
    }

    /**
     * 查询科目
     */
    function querySubject(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
            param.schId=$scope.shcId;
        }
        SubjectService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.subjectList = resp.data;
            }
        })
    }

    /**
     * 查询年级
     */
    function queryGradeList(){
        var param = {};
        if($scope.shcId&&$scope.shcId!=null){
            param.schId=$scope.shcId;
        }
        GradeService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.gradeList = resp.data;
            }
        })
    }
    
    function queryWhenBranchChange(){
    	querySeason();
    	queryGradeList();
    	querySubject();
    }



    queryProduct();
    queryGradeList();
    querySubject();
    querySeason();
    queryBranchs();
    queryCompanys();
    $scope.query();
}