'use strict';

/**
 * Route configuration for the RDash module.
 */
angular.module('ework-ui').config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
        var param = {};
        function loadMenu(menus){
            if(menus.type=='$state'){
                if(!param[menus.value]){
                    param[menus.value] = {
                        value:menus.value,
                        url: menus.path,
                        templateUrl: menus.href,
                        params: menus.params
                    };
                    if(menus.value){
                        $stateProvider
                            .state(menus.value, {
                                url: menus.path,
                                templateUrl: menus.href,
                                params: menus.params
                            });
                    }
                }
            }
            
            if(menus.menus){
                var allmenus = menus.menus;
                if(allmenus){
                    $.each(allmenus,function(i,menu){
                        loadMenu(menu);
                    });
                }
            }
            
        }

        $.ajax({
            url:'common/rightservice',
            type:'GET',
            async:false,
            dataType:'json',
            success:function(data, textStatus){
                loadMenu(data);
                param = null;
            },
            error:function(){
                //error
            }
        });
    }
]);
