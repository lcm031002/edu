/**
 * Created by Liyong.zhu on 2016/4/6.
 */
angular
    .module('ework-ui')
    .controller('LeftNavCtrl', ['$scope', '$rootScope','$location','$log','$state',LeftNavCtrl]);

function LeftNavCtrl($scope,$rootScope,$location,$log,$state) {
    $scope.curNavSystem = null;

    $scope.selectNav = function(menu){
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
            $state.go(menu.value,menu);
        }
    }
    $scope.isNavDown = false;
    $scope.downNav = function(){
        if($scope.isNavDown){
            $scope.isNavDown = false;
        }else{
            $scope.isNavDown = true;
        }
    }

    $scope.$watch('$parent.curSystem',function(newValue,oldValue, scope){

        initial$location();

    });

    function initial$location(){
        var pathLocation  =  $location.path();
        $log.info("$location path is " + pathLocation);
        if(pathLocation){
            $log.log("sub menu path is "+pathLocation);
            if($scope.$parent.curSystem){
                var menu = genMenu($scope.$parent.curSystem.menus,pathLocation);
                if(menu){
                    $log.log("found menu is "+menu.name);
                    $scope.selectNav(menu);
                }
            }
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

                else {
                    if(m.menus){
                        menu = genMenu(m.menus,pathStr);
                        if(menu){
                            $log.log("found sub menu,so open parent menu "+ m.name);
                            $scope.selectNav(m);
                        }
                    }
                }
            });
            return menu;
        }
        return null;
    }
}