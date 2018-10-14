/**
 * Created by Liyong.zhu on 2016/4/6.
 */
angular.module('ework-ui')
    .controller('TopHeadCtrl', [
        '$rootScope',
        '$scope',
        '$state',
        '$log',
        '$cookies',
        '$uibMsgbox',
        'PUBORGService',
        'PUBORGSelectedService',
        'PUBSystemParamService',
        'PUBEmployeeService',
        'LogoutService',
        TopHeadCtrl]);

function TopHeadCtrl(
        $rootScope,
        $scope ,
        $state,
        $log,
        $cookies,
        $uibMsgbox,
        PUBORGService,
        PUBORGSelectedService,
        PUBSystemParamService,
        PUBEmployeeService,
        LogoutService
    )
{
    $scope.selectedNode = {};
    $rootScope.orgRootNode = {};
    $scope.systemParam = {};
    $scope.version = 'v 5.0.1'

    $scope.toChangeOrg = function(){
        queryOrg();
    }
    $scope.logout = function(){
        LogoutService.query({},function(resp){

        });
        return true;
    }

    function queryOrg(){
        PUBORGService.get({},function(resp){
            if(resp.error == false){
                $rootScope.orgRootNode = resp.data;
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
                        'data' : $rootScope.orgRootNode,
                        "check_callback" : function (operation, node, parent, position, more) {
                            //TODO
                            console.log(operation)
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
        PUBORGService.put(node,function(resp){
            // Fixed: Bug#1723 【功能】在学员主页切换团队，抛出404异常
            // Fixed By baiqb@klxuexi.org

            window.location = '?#/students'
            location.reload(true)

        })
    }

    function querySelectedOrg(){
        PUBORGSelectedService.query({},function(resp){
            if(!resp.error){
                $scope.selectedNode = resp.data;
                $rootScope.selectedOrg = resp.data;
              if ($scope.selectedOrg && $scope.selectedOrg.id && $scope.selectedOrg.type == "4") {
                $.each($scope.branchList, function(i, b) {
                  if (b.id == $scope.selectedOrg.id) {
                    $scope.searchParam.p_branch_id = b.id;
                  }
                });
              } else {
                $uibMsgbox.warn('您还没选择校区，请选择校区！', function() {
                  setTimeout(function() {
                    $('.btn-group.sel-org.pull-left').addClass('open');
                  }, 300);
                })
              }

                if ($scope.selectedNode.text && $scope.selectedNode.text.length > 8) {
                    $scope.selectedNode.text = $scope.selectedNode.text.substring(0, 7) + '...'
                }
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