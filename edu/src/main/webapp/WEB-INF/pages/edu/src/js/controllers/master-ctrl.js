/**
 * Master Controller
 */

angular.module('ework-ui')
    .controller('MasterCtrl', ['$scope','$rootScope','$location', '$log','$state','ProjectService','MenusService','edu_accountService','edu_LoginEmployeeService', MasterCtrl]);

function MasterCtrl($scope,$rootScope,$location, $log,$state,ProjectService,MenusService,edu_accountService,edu_LoginEmployeeService) {
    $scope.topMenus = {};
    $scope.topBodyURL = 'templates/block/top.html?_='+(new Date()).getTime();
    $scope.leftNavURL = 'templates/block/left_nav.html?_='+(new Date()).getTime();
    $scope.screenfull = screenfull
    $scope.onFullScreenToggle = function () {
        if (screenfull.enabled) {
            screenfull.toggle();
        } else {
            // Ignore or do something else
        }
    }
    function queryAccount(){
        edu_accountService.query({},function(resp){
            if(!resp.error){
                $rootScope.curAccount = resp.data;
            }else{
                alert(resp.message);
            }
        });
    }

    function queryEmployeeInfo(){
        edu_LoginEmployeeService.query({},function(resp){
            if(!resp.error){
                $rootScope.curEmployee = resp.data;
            }else{
                alert(resp.message);
            }
        });
    }

    queryAccount();
    queryEmployeeInfo();


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
            //刷新动作，初始化已经选中的菜单,从而确保左侧导航正确选中
            initial$location();
            
            $scope.userMenus = genMenu($scope.topMenus,"/userManagerment");
            
            $log.info("userMenus found. ");
        },function(resp){
            location.href = "/edu?_="+(new Date()).getTime();
        });
    };
    
    $rootScope.genMenu = function(path){
        return genMenu($scope.topMenus,path);
    }
    
    $rootScope.selectSystem = function(sys){
        if(!sys){return}
    	if($scope.curSystem)
            $scope.curSystem.active = 'false';
        $scope.curSystem = sys;
        $scope.curSystem.active = 'true';
        if(sys.type=='$state'){
            // $state.go(sys.value,$scope.curSystem);
            if($scope.curSystem.key!='index' && $scope.curSystem.menus){
                if (window.location.href.indexOf('studentId') != -1) {
                    window.location.href = "?_t="+new Date().getTime()+"#" + $scope.curSystem.menus[0].path;
                } else {
                    $state.go($scope.curSystem.menus[0].value,$scope.curSystem.menus[0]);
                }
            }else if($scope.curSystem.key=='index' ){
                if (window.location.href.indexOf('studentId') != -1) {
                    window.location.href = "?_t="+new Date().getTime()+"#" + $scope.curSystem.path;
                } else {
                    $state.go($scope.curSystem.value,$scope.curSystem);
                }
            }
        }
    };

    $scope.selectSystem = function(sys){
        if(!sys){return}
        if($scope.curSystem)
            $scope.curSystem.active = 'false';
        $scope.curSystem = sys;
        $scope.curSystem.active = 'true';
        if(sys.type=='$state'){
            // $state.go(sys.value,$scope.curSystem);
            if($scope.curSystem.key!='index' && $scope.curSystem.menus){
                if (window.location.href.indexOf('studentId') != -1) {
                    window.location.href = "?_t="+new Date().getTime()+"#" + $scope.curSystem.menus[0].path;
                } else {
                    $state.go($scope.curSystem.menus[0].value,$scope.curSystem.menus[0]);
                }
            }else if($scope.curSystem.key=='index' ){
                if (window.location.href.indexOf('studentId') != -1) {
                    window.location.href = "?_t="+new Date().getTime()+"#" + $scope.curSystem.path;
                } else {
                    $state.go($scope.curSystem.value,$scope.curSystem);
                }
            }
        }
    };



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
            var menu = genTopMenu($scope.topMenus,pathString);
            if(menu){
                $log.log("found menu is "+menu.name+",href is " + menu.href);
                if($scope.curSystem){
                    $scope.curSystem.active = 'false';
                }
                $scope.curSystem = menu;
                $scope.curSystem.active = 'true';
                $log.log("$location end,menu is " + menu.name);
            }else{
                $log.log("$location end,menu is not found,see  pathString is " + pathString);
            }


        }else{
            $state.go("students",{});
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
            for(var index=0;index<menus.length;index++){
            	if(pathStr == menus[index].path){
                    menu = menus[index];
                    break;
                }else{
                    if(menus[index].menus){
                        menu = genMenu(menus[index].menus,pathStr);
                    }
                }
            }
            
            return menu;
        }
        return null;
    }

    /**
     * 查询根菜单
     * @param menus
     * @param pathStr
     * @returns {*}
     */
    function genTopMenu(menus,pathStr){
        if(menus&&menus.length){
            var menu = null;
            $.each(menus,function(i,m){
                if(pathStr == m.path){
                    menu = m;
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


    

    $scope.selectUserNav = function(menu){

        if($scope.curNavSystem){
            $scope.curNavSystem.active = 'false';

        }
        $scope.curNavSystem = menu;
        if($scope.curNavSystem)
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