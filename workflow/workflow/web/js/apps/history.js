/**
 * Created by Administrator on 2014/11/9.
 */

controllers.controller('HistoryController', ['$scope',
    function($scope) {

    }
]);

services.factory('HistoryService', ['$resource',
    function($resource){
        return $resource('/apps/loginuser', {}, {
            query: {method:'GET', params:{}, isArray:false}
        });
    }
]);
