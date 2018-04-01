/**
 * Created by Liyong.zhu on 2016/4/6.
 */
angular
    .module('ework-ui')
    .controller('LeftNavCtrl', ['$scope', '$rootScope','$location','$log','$state','$cookieStore', '$uibMsgbox', 'erp_studentsService', LeftNavCtrl]);

function LeftNavCtrl($scope,$rootScope,$location,$log,$state,$cookieStore, $uibMsgbox, erp_studentsService) {
    window.sss = $scope;
    $scope.curNavSystem = null;
    $scope.student = null

    $('.left_nav').mCustomScrollbar({
        theme: 'klxx'
    });

    function queryStudentInfo(){
        erp_studentsService.query({
            row_num: 20,
            studentId: $scope.studentId
        }, function(resp){
            if(!resp.error && resp.data.length){
                $scope.student = resp.data[0];
            }
        });
    }

    $scope.selectNav = function(menu){
        if($scope.curNavSystem){
            $scope.curNavSystem.active = 'false';
        }
        $scope.curNavSystem = menu;

        // 学生ID
        if (menu.href) {
            $scope.studentId = $("#rootIndex_studentId").val()
            if ($scope.studentId) {
                queryStudentInfo()
            } else {
                $scope.student = null
            }
        }

        if($scope.curNavSystem){
            if(menu.menus&&menu.menus.length){
                if(menu.open){
                    menu.open = false;
                }else{
                    closeAllMenus();
                    menu.open = true;
                }
            } else {
                $scope.curNavSystem.active = 'true';
            }

            if(menu.type == '$state'|| menu.value){
                $state.go(menu.value, menu);
                if(!menu.href && menu.menus){
                    $scope.selectNav(menu.menus[0]);
                }
            }
        }
    }
    $rootScope.selectNav = $scope.selectNav;
    $scope.isNavDown = false;
    $scope.downNav = function(){
        if($scope.isNavDown){
            $scope.isNavDown = false;
        }else{
            $scope.isNavDown = true;
        }
    }

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
        /**
         * Fixed Bug 点击菜单后，要点两次才会展开
         * 原因：通过点击菜单栏进行跳转的，已经对菜单栏进行initial$location操作，
         * 同时对$scope.curNavSystem 进行了赋值操作，因此监听路由变化时，如果
         * $scope.curNavSystem中的key与toState的name一致，则不是需要再重新初始化菜单栏
         */
        if ($scope.curNavSystem && $scope.curNavSystem.key && $scope.curNavSystem.key != toState.name) {
            initial$location();
        }
    })
    $scope.$watch('$parent.curSystem',function(newValue,oldValue, scope){
        $scope.studentId = $("#rootIndex_studentId").val()
        if ($scope.studentId) {
            queryStudentInfo()
        } else {
            $scope.student = null
        }
        initial$location();
    });

    function closeAllMenus() {
        if ($scope.$parent.curSystem && $scope.$parent.curSystem.menus) {
            var menus = $scope.$parent.curSystem.menus
            for (var i = menus.length - 1; i >= 0; i--) {
                menus[i].open = false;
            }
        }
    }

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

/**
 * erp v5 -> paperBank management
 * subjects list
 * grade list
 */
frPaperBankService.$inject = ['$http', '$q', '$log']
function frPaperBankService($http, $q, $log) {
    return {
        getSubjects: getSubjects,
        getGrades: getGrades
    }

    function getSubjects() {
        return $http.get('/erp/tr/subject/list', {})
            .then(getSubjectsComplete)
            .catch(getSubjectsFailed);

        function getSubjectsComplete(response) {
            return response.data;
        }

        function getSubjectsFailed(error) {
            $log.error('XHR Failed for getSubjects.' + error);
            return $q.reject(error);
        }
    }

    function getGrades() {
        return $http.get('/erp/tr/grade/list', {})
            .then(getGradesComplete)
            .catch(getGradesFailed);

        function getGradesComplete(response) {
            return response.data;
        }

        function getGradesFailed(error) {
            $log.error('XHR Failed for getGrades.' + error);
            return $q.reject(error);
        }
    }
}