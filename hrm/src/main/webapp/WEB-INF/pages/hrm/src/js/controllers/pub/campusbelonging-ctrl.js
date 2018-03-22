/**
 * Created by Liyong.zhu on 2016/6/13.
 */
angular.module('ework-ui')
    .controller('CampusBelongingCtrl', [
        '$scope',
        '$log',
        '$state',
        'CompanyService',
        'BranchsService',
        CampusBelongingCtrl]);

function CampusBelongingCtrl(
    $scope,
    $log,
    $state,
    CompanyService,
    BranchsService
    ){

    //公司列表
    $scope.companyList = [];

    //校区列表
    $scope.branchsList = [];
    
    //地区列表
    $scope.areaList = [];
    
    //团队列表
    $scope.teamList=[];

    //已选择的校区列表
    $scope.branchsListSelected = [];

    //公司列表
    $scope.checkedCompany = null;
    
    $scope.isAllSelected=0;

    /**
     * 确认提交
     */
    $scope.comfirmSubmit = function(){
        if(!$scope.checkedCompany ){
            alert("请选择要修改的公司！");
            return;
        }
        var param = {};
        param = $scope.checkedCompany;
        param.schools=$scope.checkedCompany.branchsList;
        param.companyId=$scope.checkedCompany.id;
        $log.log(param);
        CompanyService.update(param,function(resp){
           if(resp.error == 'false'){
               alert("修改成功！");
           }
        });
    }

    /**
     * 添加校区面板
     */
    $scope.toAddSelectedBranchPanel = function(){
        if(!$scope.checkedCompany){
            alert("请选择公司！");
            return ;
        }
        $('#addBranchListPanel').modal('show');
        queryBranchs();
    }

    /**
     * 选中行
     * @param row
     */
    $scope.checked = function(row){
        if($scope.checkedCompany&&$scope.checkedCompany.id!=row.id){
            $scope.checkedCompany.checked = false;
        }
        if(row.checked){
            row.checked = false;
        }else{
            row.checked = true;
            $scope.checkedCompany = row;
            $log.log("start query branch");
            queryBranchsSelected(row);
        }
    }

    /**
     * 选择行
     * @param row
     */
    $scope.checkedRow = function(row){
        if(row.checked){
            row.checked = false;
        }else{
            row.checked = true;
        }
    }

    /**
     * 移除行
     */
    $scope.removeRow = function(row){
        var selectedBranchList = [];
        if($scope.checkedCompany.branchsList&&$scope.checkedCompany.branchsList.length){
            $.each($scope.checkedCompany.branchsList,function(i,b){
                if(row.id != b.id){
                    selectedBranchList.push(b);
                }
            })
            $scope.checkedCompany.branchsList = selectedBranchList;
        }

    }

    /**
     * 添加校区
     */
    $scope.addBranch = function(){
        if(!$scope.branchsList){
            alert("异常数据！请联系管理员！");
            return;
        }
        var exist = false;
        $scope.uncheckedBranchsList=[];
        $.each($scope.branchsList,function(i,b){
           if(b.checked){
                exist = true;
                if(!$scope.checkedCompany.branchsList){
                    $scope.checkedCompany.branchsList = [];
                }
               $scope.checkedCompany.branchsList.push(b);
           }
           else{
        	   $scope.uncheckedBranchsList.push(b);
           }
        });
       $scope.branchsList=$scope.uncheckedBranchsList;
        if(!exist){
            alert("请选择校区！");
            return;
        }
       // queryBranchs();

        
    }
    //全选与反选
    $scope.toggleAll = function() {
    	$scope.isAllSelected=$scope.isAllSelected==0?true:false;
        $.each($scope.branchsList,function(i,item){
        	item.checked = $scope.isAllSelected; 
        });
     }
    /**
     * 查询归属公司
     */
    function queryCompany(){
        CompanyService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.companyList = resp.data;
            }
        })
    }

    
    //查询地区
    function queryAreas(){
        CompanyService.queryAreas({},function(resp){
            if(resp.error == 'false'){
                $scope.areaList = resp.data;
            }
        })
    }
    
    
    $scope.queryTeams=function(area){
    	var param={};
    
    	param.parentId=area.id;
    	$log.log(param.parentId);
    	 CompanyService.querySubOrg(param,function(resp){
             if(resp.error == 'false'){
                 $scope.teamList = resp.data;
             }
         })
    	
    }
    
    $scope.queryBranchs=function(team){
    	var param={};
    	param.parentId=team.id;
    	 CompanyService.querySubOrg(param,function(resp){
             if(resp.error == 'false'){
                 $scope.branchsList = resp.data;
             }
         })
    	
    }
    /**
     * 查询归属校区
     */
    function queryBranchsSelected(row){
        var param = {};
        param.companyId = row.id;
        if(!row.branchsList){
            row.branchsList = [];
            $scope.isLoadingBranchListSelected = 'loading...';
            BranchsService.get(param,function(resp){
                $scope.isLoadingBranchListSelected = '';
                if(resp.error == 'false'){
                    row.branchsList = resp.data;
                }
            });
        }
    }

    function queryBranchs(){
        var param = {};
        $scope.branchsList = [];
        $scope.isLoadingBranchList = 'loading...';
        $log.log("begin to query branchs.");
        BranchsService.get(param,function(resp){
            $scope.isLoadingBranchList = '';
            $log.log("found branchs.");
            if(resp.error == 'false'){
                var branchsList = [];
                var data = resp.data;
                $.each(data,function(i,d){
                    var exist = false;
                    $.each($scope.checkedCompany.branchsList,function(j,s){
                        if(d.id == s.id){
                            exist = true;
                        }
                    });
                    if(!exist){
                        branchsList.push(d);
                    }
                });
                $scope.branchsList = branchsList;
            }
        });
    }

    queryCompany();
    queryAreas();
}