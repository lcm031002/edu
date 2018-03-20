/**
 * Created by Liyong.zhu on 2016/6/14.
 */
angular.module('ework-ui')
    .controller('FileUpLoadCtrl', [
        '$scope',
        '$log',
        '$state',
        'FileUploadService',
        'FileUploadDataService',
        FileUpLoadCtrl]);

function FileUpLoadCtrl(
        $scope,
        $log,
        $state,
        FileUploadService,
        FileUploadDataService
    ){
    //加载中效果
    $scope.isLoading = '';
    $scope.loaded=false;
    //模版ID
    $scope.modelId = null;
    $scope.test="1";
    $scope.datas=null;
    $scope.child = {};
    /**
     * 文件上传服务
     */
    $scope.fileUpload = function(){
        $scope.error_info = "";
        //未选择文件
        if(!$("#fileUploader").val()){
            alert("请选择要导入的excel！");
            $scope.error_info = "请选择要导入的excel！";
            return;
        }

        //选择的文件不是.xls后缀的格式
        if(!$("#fileUploader").val().endsWith(".xls")&&!$("#fileUploader").val().endsWith(".xlsx")){
            alert("请选择.xls格式的文件！");
            $scope.error_info = "请选择.xls或者.xlsx格式的文件！";
            return;
        }

        //需要选择对应的模版
        var selectdeModel = null;
        if($scope.fileList){
            $.each($scope.fileList,function(i,f){
                if(f.checked){
                    selectdeModel = true;
                }
            });
        }

        if(!selectdeModel){
            alert("请选择模版！");
            $scope.error_info = "请选择对应的excel模版！";
            return;
        }
        $scope.isLoading = 'loading...';
        //异步上传文件服务
        $.ajaxFileUpload ({
            url: "excels/inputview/input/"+$scope.modelId,
            secureuri: false,
            fileElementId: 'fileUploader',
            dataType: "json",
            success: function (resp){
                $scope.isLoading = '';
                if(resp['message']){
                	alert(resp['message']);
                }
				$scope.step = 'two';
				$scope.$apply();
				$scope.cols = resp.cols;
				$scope.datas = resp.data;
				$scope.loaded=true;
            },
            error: function (html,status,e){
                $scope.isLoading = '';
                alert("上传失败！");
                $scope.step = 'two';
                $scope.$apply();
            }
        })
    }

    $checkedIndex=-1;
    $scope.checkedFile =  function (file,position){
    	if($checkedIndex!=-1){
    		$scope.fileList[$checkedIndex].checked=false;
    	}
    	file.checked=!file.checked;
    	$scope.modelId=file.id;
    	$checkedIndex=position;
    }
    /**
     * @param order
     */
    $scope.checkData = function(data){
        if(data.checked){
        	data.checked = false;
        }else{
        	data.checked = true;
        }
    }
    
    /**
     * 全选反选当前页面的数据
     */
    $scope.checkAllRows = function(){
    	var currentPage=$scope.child.currentPage;
    	var itemsPerPage=$scope.child.itemsPerPage;
    	var currentPageDatas=$scope.datas.slice(((currentPage-1)*itemsPerPage),((currentPage)*itemsPerPage));
    	$log.log(currentPageDatas.length+"=="+$scope.child.itemsPerPage);
        if($scope.checkAll){
            $.each(currentPageDatas,function(i,model){
            	if(i!=0)
                model.checked = false;
            });
            $scope.checkAll = false;
        }else{
            $.each(currentPageDatas,function(i,model){
            	if(i!=0)
                model.checked = true;
            });
            $scope.checkAll = true;
        }
    }
    /**
     * 提交
     */
    $scope.submit = function(){
    	   var param = {};
        if(!$scope.datas||!$scope.datas.length){
            alert("没有数据.");
            return;
        }

        var valid=true;
        var selected =  new Array();
        $.each($scope.datas,function(i,d){
            if(d.checked){
            	if(d.result!='true'){
            		valid=false;
            		alert("第"+(i+1)+"行数据不合法!");
            	}
            	selected.push(d);
            }
        });
        if(!selected.length){
            alert("请选择要提交的数据！");
            return;
        }
        if(!valid)
        	return;
        param.dataRow=JSON.parse(JSON.stringify(selected));
        param.modelId = $scope.modelId;
        FileUploadDataService.add(param,function(resp){
            if(resp.error ==  'false'||resp.error==false){
                alert("保存成功！");
            }
            if(resp.message){
            	alert("erro:"+resp.message);
            }
        }).error(function(data, status, headers, config) {
            if(status='404'){
            	alert("服务器已停止服务,请联系管理员!");
            }
            if(data.message){
            	alert(data.message);
            }
        });
    }

    /**
     * 返回
     */
    $scope.returnBack = function(){
        $scope.step = 'one';
        $scope.isLoading = '';
    }

    $scope.step = 'one';
    $scope.fileList = [];

    function queryFileUpload(){
        var param = {};

        FileUploadService.get(param,function(resp){
            if(resp.error == 'false'||resp.error==false){
                $scope.fileList = resp.data;
            }
        })
    }
    
    /**
     * 初始化模版列表
     */
    function queryFileUploadData(){
        var param = {};

        FileUploadDataService.get(param,function(resp){
            if(resp.error == 'false'){
                $scope.cols = resp.cols;
                $scope.datas = resp.data;
                $scope.modelId = resp.modelId;
            }
        })
    }

    queryFileUpload();

    //queryFileUploadData();
}