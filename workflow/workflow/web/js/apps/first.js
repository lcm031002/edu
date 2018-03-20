/**
 * Created by Administrator on 2014/11/9.
 */

controllers.controller('FirstController', ['$scope',
    function($scope) {

    }
]);

services.factory('FirstService', ['$resource',
    function($resource){
        return $resource('/apps/loginuser', {}, {
            query: {method:'GET', params:{}, isArray:false}
        });
    }
]);
