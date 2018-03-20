"use strict";
angular
    .module('ework-ui')
    .controller('crmRescRecDistributeController',
		[   '$rootScope',
            '$scope',
            'crm_queryDictDataService',
            'crm_queryBranchsService',
		    'crm_querySingleRescRecService',
            'crm_queryRescRecProcService',
            'crm_queryCnselorListService',
		    'crm_loadDataRescRecService',
            '$stateParams',
            crmRescRecDistributeController
        ]);

function crmRescRecDistributeController(
            $rootScope,
            $scope,
            queryDictDataService,
            queryBranchsService,
            querySingleRescRecService,
            queryRescRecProcService,
            queryCnselorListService,
            loadDataRescRecService,
            $stateParams
    ){

    $scope.searchParam = {};

    if($stateParams.name){
        $scope.searchParam.p_searchString = $stateParams.name;
    }
    //资源列表
    $scope.resourceRecsDist = {};
    //		              更多信息窗口
    $scope.popW_more = {};

    //资源更多信息
    $scope.moreInfo ={};

    //		      咨询师列表
    $scope.cnselors=[]

    $scope.selected_cnsl = 0;

    $scope.isLoading='loading...';

    //		      分页
    agPageObject($scope,loadDataRescRecService,$scope.searchParam);

    $scope.init = function(){
        $scope.searchParam.p_isDist = "1";
        $scope.load();
    };

    $scope.pageCallBack = function(data){
        $scope.resourceRecsDist = [];
        var newArray = new Array();
        $scope.isLoading='';
        $.each($scope.items,function(i,model){
            model.is_trace=false;
            newArray.push(model);
            newArray.push({is_trace:true,id:model.id});
        });
        $scope.resourceRecsDist =newArray;
    }

    $scope.isLoading='loading...';

    $scope.init();

    $scope.ctn_cnselor_name = '';

    $scope.$watch('searchParam.p_searchString',function(newNameMp){
        $scope.query();
    });

    $scope.query = function(){
        var url = "/gxhcrm/query/queryRescRec";
        $scope.searchParam.p_isDist = "1";
        $scope.isLoading = 'loading...';
        $scope.resourceRecsDist = [];
        $scope.load();
    }

    //				导出
    $scope.exp = function(){
        var param = "";

        for(var p in $scope.searchParam){
            if(!$scope.searchParam[p]){
                param += p+"="+$scope.searchParam[p]+"&";
            }
        }
        if(confirm("确定要导出?")){
            location.href =   "/gxhcrm/rescRecDist/toExp?" +encodeURI(encodeURI(param)) ;
        }
    };

    //关闭窗口的方法
    $scope.closeMoreInfoWin = function(obj){
        $('#recMoreInfo').hide("slow");
        $('#cnselorlist').hide();
    }

    //				查看更多信息
    $scope.showMoreInfo = function(rec_Id,target){
        querySingleRescRecService.query({recId:rec_Id},$scope.showMoreInfoCallBack);
    };


    //				查看更多信息回调函数
    $scope.showMoreInfoCallBack = function(res){
        $scope.moreInfo = {};
        $scope.moreInfo = res.data;
        //设定窗口的位置
        $("#recMoreInfo").slideToggle("nomal","swing");
    }

    //分配资源
    $scope.distributeResources = function(rec){
        if(!$scope.dist_cnselor_id){
            alert("请在右侧选择咨询师！");
            return;
        }
        $scope.currentRec = rec;
        //分配咨询师
        $scope.toDistribute();
    }

    //选择咨询师
    $scope.selectDistCnselorId = function(cnsl){
        if(cnsl&&cnsl.id){
            $scope.dist_cnselor_id = cnsl.id;
        }
    }

    //
    $scope.setSchools = function(res){
        if(!res.error){
            $scope.schools = res.data;
            if($scope.schools&&$scope.schools.length>0){
                $scope.dist_branch_id = $scope.schools[0].branchId;
            }
        }
    };

    //选择校区
    $scope.selectSchool = function(school){
        $scope.dist_branch_id = school.branchId;
    }

    $scope.change_cnselor_name = function(){
        if($scope.ischeck){
            $scope.dist_cnselor_id =null;
            $scope.ischeck = false;
        }
    }

    $scope.trace_id="";

    //				查看更多信息
    $scope.showTraceInfo = function(rec){
        queryRescRecProcService.query({recId:rec.id},function(res){
            $scope.traceInfo ={};
            $scope.traceInfo = res.data;

            if($scope.trace_id == rec.id){
                $scope.trace_id = "";
            }else{
                $scope.trace_id = rec.id;
            }

        });
    };

    $scope.toDistribute = function(){
        if(isEmpty($scope.dist_branch_id)){
            alert('请选择学校！');
            return;
        }
        if(isEmpty($scope.dist_cnselor_id)){
            alert('请选择咨询师！');
            return;
        }
        var url = "/gxhcrm/rescRecDist/toDistribute";
        $scope.param = {};
        $scope.param.bu_id = $scope.currentRec.bu_id;
        $scope.param.id = $scope.currentRec.id;
        $scope.param.branch_id = $scope.dist_branch_id;
        $scope.param.cnselor_id = $scope.dist_cnselor_id;
        $scope.isRun = 'running';
        toolAjax(url, 'post',$scope.param,$scope.toDistributeCallBack, 'json');
    }

    $scope.toDistributeCallBack = function(data){
        if (data.result == null || data.result == '0') {
    //						保存失败提示错误信息
            alert('分配失败！');
        }else{
            alert('分配成功');
            $scope.resourceRecsDist.splice($scope.currentIdx,1);
        }
        $scope.$apply(function(){
            $scope.isRun = '';
        });
        $scope.init();
    }

    //				查询咨询师
    $scope.enter_search_cnselor = function(event,target){
        if(event.keyCode==13)
        {
            $scope.search_cnselor(target);
            return false;
        }
    }


    $scope.search_cnselor = function(target){
        $scope.ischeck = false;
        queryCnselorListService.query({cnselorName:encodeURIComponent($scope.dist_cnselor_name)},
            function(data){
                $scope.cnselors ={};
                $scope.cnselors = data.cnselorList;
                if(!isEmpty($scope.cnselors) && $scope.cnselors.length == 1){
                    //只查到一条咨询师直接赋值
                    $scope.dist_cnselor_id =$scope.cnselors[0].id;
                    $scope.dist_cnselor_name =$scope.cnselors[0].employee_name;
                    $scope.ischeck = true;
                }else{//没查到显示窗口
                    $scope.selected_cnselor(target);
                }
            }
        );
    }

    //				咨询师查询
    $scope.cnSelorSearch = function(){
        queryCnselorListService.query({
            cnselorName:encodeURIComponent($scope.ctn_cnselor_name),
            branchId:$scope.dist_branch_id
        },$scope.showCnselorCallBack);
    }

    $scope.showCnselorCallBack = function(data){
        $scope.cnselors ={}
        $scope.cnselors = data.cnselorList;
        $scope.selected_cnsl = 0;
    }

    //初始化查询校区
    queryBranchsService.query({buId:null,queryType:"getBranchsByUser"},$scope.setSchools);

}
