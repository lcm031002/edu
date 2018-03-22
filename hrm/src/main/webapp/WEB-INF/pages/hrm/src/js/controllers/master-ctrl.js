/**
 * Master Controller
 */

angular.module('ework-ui')
    .controller('MasterCtrl', ['$scope','$location', '$log','$state','ProjectService','MenusService', MasterCtrl]);

function MasterCtrl($scope,$location, $log,$state,ProjectService,MenusService) {
    $scope.topMenus = {};
    if(MenusService){
        $log.info("found service MenusService.");
        MenusService.get({},function(res){
            $log.info("found service result.");
            $scope.topMenus = res.menus;
            $log.info("found service result."+res);
            $.each($scope.topMenus,function(i,menu){
                if(menu.active=='true'){
                    $scope.curSystem = menu;
                    $log.info("menu active is " + menu.name);
                }
            });
            //刷新动作，初始化已经选中的菜单
            initial$location();
        });
    };


    $scope.selectSystem = function(sys){
    	if($scope.curSystem)
        $scope.curSystem.active = 'false';
        $scope.curSystem = sys;
        $scope.curSystem.active = 'true';

        if(sys.type=='$state'){
            $state.go(sys.value,{});
        }
    }



    function queryProjectInfo(){
        ProjectService.get({},function(resp){
            if(resp.error == 'false'){
                $scope.project = resp;
                $("title").text($scope.project.name);
            }
        });
    }

    queryProjectInfo();

    function initial$location(){
        var pathLocation  =  $location.path();
        $log.info("$location path is " + pathLocation);
        if(pathLocation){
            var subPath = pathLocation.split("/");
            $log.log(subPath.join(","));
            if(subPath.length<2||!subPath[1]){
                return;
            }
            var pathString = "/"+subPath[1];
            $log.log("sub menu path is "+pathString);
            var menu = genMenu($scope.topMenus,pathString);
            if(menu){
                $log.log("found menu is "+menu.name);
                if($scope.curSystem)
                $scope.curSystem.active = 'false';
                $scope.curSystem = menu;
                $scope.curSystem.active = 'true';
            }
        }else{
            $state.go("index",{});
        }
    }


    /**
     * 查询根菜单
     * @param menus
     * @param pathStr
     * @returns {*}
     */
    function genMenu(menus,pathStr){
        if(menus&&menus.length){
            var menu = null;
            $.each(menus,function(i,m){
                if(pathStr == m.path){
                    menu = m;
                }

                if(menu){
                    return ;
                }
            });
            return menu;
        }
        return null;
    }


    /**
     * 获取数组
     * @param n
     * @returns {Array}
     */
    $scope.getNumber = function(n){
        var list = new Array();
        for (var index = 0;index<n;index++){
            list.push(index+1);
        }
        return list;
    }

    /**
     * 选择行
     * @param row
     */
    $scope.checked = function(row){
        if(row.checked){
            row.checked = false;
        }else{
            row.checked = true;
        }
    }


    $scope.userMenus = {
        "index":"C10001",
        "name":"个人管理",
        "key":"userManagerment",
        "icon":"fa fa-diamond",
        "type":"$state",
        "value":"userManagerment",
        "path":"/userManagerment",
        "showMenu":"false",
        "menus":[
            {
                "index":"C10001",
                "name":"账户安全",
                "key":"UMAccountMgr",
                "icon":"fa fa-video-camera",
                "type":"$state",
                "value":"UMAccountMgr",
                "path":"/userManagerment/UMAccountMgr",
                "href":"templates/segment/account_security.html"
            },
            {
                "index":"C10002",
                "name":"个人信息",
                "key":"UMEmployeeInfo",
                "icon":"fa fa-video-camera",
                "type":"$state",
                "value":"UMEmployeeInfo",
                "path":"/userManagerment/UMEmployeeInfo",
                "href":"templates/segment/employee_info.html"
            }
        ]
    }

    $scope.selectUserNav = function(menu){
        if($scope.curNavSystem){
            $scope.curNavSystem.active = 'false';

        }
        $scope.curNavSystem = menu;
        $scope.curNavSystem.active = 'true';
        if(menu.menus&&menu.menus.length){
            if(menu.open){
                menu.open = false;
            }else{
                menu.open = true;
            }
        }

        if(menu.type=='$state'||menu.value){
            if($scope.curSystem){
                $scope.curSystem.active = 'false';
                $scope.curSystem = $scope.userMenus;
                $scope.curSystem.active = 'true';
            }
            $state.go(menu.value,menu);
        }
    }
}