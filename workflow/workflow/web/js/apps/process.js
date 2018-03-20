/**
 * Created by Administrator on 2014/11/9.
 */
controllers.controller('ProcessController', [
    '$scope',
    'ProcessService',
    'ProcessDeployService',
    function($scope,ProcessService,ProcessDeployService) {
        $scope.menuId = '流程管理';
        //$scope.classPathUrl = 'D:/workspace/FEoSA/src/main/webapp/workflow/jpdl';
        $scope.webContext = genWebContext();
        
        function query(){
        	ProcessService.query({
                //classpathurl:$scope.classPathUrl
            },function(repsponse){
                if(!repsponse.error){
                    $scope.processs = repsponse.fileList;
                    $scope.path = repsponse.path;
                    //$scope.classPathUrl = repsponse.path;
                }else{
                    alert("流程信息获取不正确！");
                }
            },function(){
                alert("流程信息获取异常！");
            });
        }
        
        query()
        /**
         *
         * @param processe
         */
        $scope.deploy = function(processe){
            ProcessDeployService.query({
                processFile:processe.file
            },function(response){
                if(!response.error){
                    alert("发布成功！");
                    query();
                }else{
                    alert("发布失败！");
                }
            })
        }

        /**
         *
         * @param processe
         */
        $scope.delProcess = function(processe){
            alert("已经删除");
        }

        /**
         *
         */
        $scope.showImg = function(processe){
            if(processe.show){
                processe.show = false;
            }else{
                processe.show = true;
            }
        }
    }
]);

services.factory('ProcessService', ['$resource',
    function($resource){
        return $resource(webContext+'/rest/processinfo',{},{
            query: {method:'GET', params:{}, isArray:false}
        })
    }
]);

services.factory('ProcessDeployService', ['$resource',
    function($resource){
        return $resource(webContext+'/rest/deployprocess',{},{
            query: {method:'GET', params:{}, isArray:false}
        })
    }
]);
