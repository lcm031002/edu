/**
 * Created by Liyong.zhu on 2016/4/6.
 */
angular.module('ework-ui')
    .controller('TopHeadCtrl', [
        '$scope',
        '$state',
        '$log',
        'PUBORGService',
        'PUBORGSelectedService',
        'PUBSystemParamService',
        'PUBEmployeeService',
        TopHeadCtrl]);

function TopHeadCtrl(
        $scope ,
        $state,
        $log,
        PUBORGService,
        PUBORGSelectedService,
        PUBSystemParamService,
        PUBEmployeeService
    )
{
    $scope.selectedNode = {};
    $scope.rootNode = {};
    $scope.systemParam = {};


    $scope.toChangeOrg = function(){
        queryOrg();
    }

    function queryOrg(){
        PUBORGService.get({},function(resp){
            if(resp.error == false){
                $scope.rootNode = resp.data;
                $("#org_tree").jstree({
                    "plugins" : ["types"],
                    "types" : {
                        "menus" : {
                            "icon" : "fa fa-folder-o"
                        },
                        "leaf" : {
                            "icon" : "fa fa-file-text-o"
                        }
                    },
                    'core' : {
                        'data' : $scope.rootNode,
                        "check_callback" : function (operation, node, parent, position, more) {
                            //TODO
                        }
                    }
                });

                $("#org_tree").bind('click.jstree', function(event) {
                    var eventNodeName = event.target.text;
                    var node = {
                    }
                    node.id = event.target.id.replace("_anchor","");
                  if(event.target.id&&event.target.id!='')
                   checkedOrg(node);
                })
                //双击  确定jstree.js中已经添加双击事件
                $("#org_tree").bind('dblclick.jstree',function(event){
                    var eventNodeName = event.target.text;
                    $log.log(eventNodeName);
                    var node = {
                    }
                    node.id = event.target.id.replace("_anchor","");
                    checkedOrg(node);
                });
                $log.log("组织机构加载成功!");
            }
        })
    }

    function checkedOrg(node){
    	debugger;
        PUBORGService.put(node,function(resp){
            location.reload(true);
            if(resp.error == 'false'){

            }
        })
    }

    function querySelectedOrg(){
        PUBORGSelectedService.query({},function(resp){
            if(!resp.error){
                $scope.selectedNode = resp.data;
            }
        })
    }

    function querySystemParam(){
        PUBSystemParamService.query({},function(resp){
            if(!resp.error){
                $scope.systemParam = resp.data;
            }
        })
    }

    function queryEmployee(){
        PUBEmployeeService.query({},function(resp){
            if(!resp.error){
                $scope.employee = resp.data;
            }
        });
    }

    queryOrg();
    querySelectedOrg();
    querySystemParam();
    queryEmployee();
}