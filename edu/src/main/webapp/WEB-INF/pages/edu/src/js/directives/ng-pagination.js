/**
 * name: ng-pagination
 * Version: 1.0.0 beta
 */
angular
  .module('ework-ui')
  .directive('ngPagination', ngPagination);

function ngPagination(){
    return {
        restrict: 'EA',
        template: 
        '<div class="container-fluid"><div class="row">' +
            '<div class="col-md-3 hidden-xs hidden-sm text-left">' + 
                '<div class="page-total pagination" ng-show="conf.totalItems > 0 && conf.showInfos">' +
                    '每页&nbsp;' + 
                    '<select class="form-control2" style="display:inline-block;width:auto;" ng-model="conf.itemsPerPage" ' +
                        'ng-options="option for option in conf.perPageOptions" ' +
                        'ng-change="changeItemsPerPage()">' +
                    '</select>' + '&nbsp;条/共<strong>{{ conf.totalItems }}</strong>条' +
                    // '跳至第&nbsp;<input class="form-control2" style="display:inline-block;" type="text" size="3" ng-model="jumpPageNum" ng-keyup="jumpPageKeyUp($event)" ng-blur="jumpToPage()"/>&nbsp;页' +
                '</div>' +
            '</div>'+
            '<div class="{{conf.showInfos ? \'col-md-6\': \'\'}} col-xs-12 text-center">' +
                '<ul class="pagination" ng-show="conf.totalItems > 0">' +
                    '<li ng-show="conf.showFirstAndLast" ng-class="{disabled: conf.currentPage == 1}" ng-click="prevPage()"><span>&laquo;</span></li>' +
                    '<li ng-repeat="item in pageList track by $index" ng-class="{active: item == conf.currentPage, separate: item == \'...\'}" ' +
                        'ng-click="changeCurrentPage(item)">' +
                        '<span>{{ item }}</span>' +
                    '</li>' +
                    '<li ng-show="conf.showFirstAndLast" ng-class="{disabled: conf.currentPage == conf.numberOfPages}" ng-click="nextPage()"><span>&raquo;</span></li>' +
                '</ul>' + 
            '</div>' +
            '<div class="col-xs-12 text-center" ng-show="conf.totalItems <= 0"> <div class="no-data"></div> 暂无数据</div>' +
        '</div></div>',
        replace: true,
        scope: {
            conf: '='
        },
        link: function(scope, element, attrs) {
            scope.jumpPageNum = 1;
            var conf = scope.conf || {};
            // 默认分页长度
            var defaultPagesLength = 9;

            // 默认分页选项可调整每页显示的条数
            var defaultPerPageOptions = [10, 15, 20, 30, 50];

            // 默认每页的个数
            var defaultPerPage = 10;
            // 获取分页长度
            if(conf.pagesLength) {
                // 判断一下分页长度
                conf.pagesLength = parseInt(conf.pagesLength, 10);

                if(!conf.pagesLength) {
                    conf.pagesLength = defaultPagesLength;
                }

                // 分页长度必须为奇数，如果传偶数时，自动处理
                if(conf.pagesLength % 2 === 0) {
                    conf.pagesLength += 1;
                }

            } else {
                conf.pagesLength = defaultPagesLength
            }

            if (conf.showInfos == undefined || conf.showInfos == null) {
                conf.showInfos = true
            } else {
                conf.showInfos = conf.showInfos
            }
            // 分页选项可调整每页显示的条数
            if(!conf.perPageOptions){
                conf.perPageOptions = defaultPerPageOptions;
            }

            // 获取当前页
            if (!conf.currentPage) {
                conf.currentPage = 1;
            }
            if (!conf.showFirstAndLast) {
                conf.showFirstAndLast = false
            }
            // pageList数组
            function getPagination(newValue, oldValue) {
                
                // conf.currentPage
                if(conf.currentPage) {
                    conf.currentPage = parseInt(scope.conf.currentPage, 10);
                }

                if(!conf.currentPage) {
                    conf.currentPage = 1;
                }

                // conf.totalItems
                if(conf.totalItems) {
                    conf.totalItems = parseInt(conf.totalItems, 10);
                }

                // conf.totalItems
                if(!conf.totalItems) {
                    conf.totalItems = 0;
                    return;
                }
                
                // conf.itemsPerPage 
                if(conf.itemsPerPage) {
                    conf.itemsPerPage = parseInt(conf.itemsPerPage, 10);
                }
                if(!conf.itemsPerPage) {
                    conf.itemsPerPage = defaultPerPage;
                }

                // numberOfPages
                conf.numberOfPages = Math.ceil(conf.totalItems/conf.itemsPerPage);

                // 如果分页总数>0，并且当前页大于分页总数
                if(scope.conf.numberOfPages > 0 && scope.conf.currentPage > scope.conf.numberOfPages){
                    scope.conf.currentPage = scope.conf.numberOfPages;
                }

                // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                var perPageOptionsLength = scope.conf.perPageOptions.length;

                // 定义状态
                var perPageOptionsStatus;
                for(var i = 0; i < perPageOptionsLength; i++){
                    if(conf.perPageOptions[i] == conf.itemsPerPage){
                        perPageOptionsStatus = true;
                    }
                }
                // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                if(!perPageOptionsStatus){
                    conf.perPageOptions.push(conf.itemsPerPage);
                }

                // 对选项进行sort
                conf.perPageOptions.sort(function(a, b) {return a - b});
                

                // 页码相关
                scope.pageList = [];
                if(conf.numberOfPages <= conf.pagesLength){
                    // 判断总页数如果小于等于分页的长度，若小于则直接显示
                    for(i =1; i <= conf.numberOfPages; i++){
                        scope.pageList.push(i);
                    }
                }else{
                    // 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）
                    // 计算中心偏移量
                    var offset = (conf.pagesLength - 1) / 2;
                    if(conf.currentPage <= offset){
                        // 左边没有...
                        for(i = 1; i <= offset + 1; i++){
                            scope.pageList.push(i);
                        }
                        scope.pageList.push('...');
                        scope.pageList.push(conf.numberOfPages);
                    }else if(conf.currentPage > conf.numberOfPages - offset){
                        scope.pageList.push(1);
                        scope.pageList.push('...');
                        for(i = offset + 1; i >= 1; i--){
                            scope.pageList.push(conf.numberOfPages - i);
                        }
                        scope.pageList.push(conf.numberOfPages);
                    }else{
                        // 最后一种情况，两边都有...
                        scope.pageList.push(1);
                        scope.pageList.push('...');

                        for(i = Math.ceil(offset / 2) ; i >= 1; i--){
                            scope.pageList.push(conf.currentPage - i);
                        }
                        scope.pageList.push(conf.currentPage);
                        for(i = 1; i <= offset / 2; i++){
                            scope.pageList.push(conf.currentPage + i);
                        }

                        scope.pageList.push('...');
                        scope.pageList.push(conf.numberOfPages);
                    }
                }

                scope.$parent.conf = conf;
            }

            // prevPage
            scope.prevPage = function() {
                if(conf.currentPage > 1){
                    conf.currentPage -= 1;
                    getPagination();
                    if(conf.onChange) {
                        conf.onChange();
                    }
                }
            };

            // nextPage
            scope.nextPage = function() {
                if(conf.currentPage < conf.numberOfPages){
                    conf.currentPage += 1;
                    getPagination();
                    if(conf.onChange) {
                        conf.onChange();
                    }
                }
            };

            // 变更当前页
            scope.changeCurrentPage = function(item) {
                
                if(item == '...' || conf.currentPage == item){
                    return;
                }else{
                    conf.currentPage = item;
                    getPagination();
                    // conf.onChange()函数
                    if(conf.onChange) {    
                        conf.onChange();
                    }
                }
            };

            // 修改每页展示的条数
            scope.changeItemsPerPage = function() {

                // 一发展示条数变更，当前页将重置为1
                conf.currentPage = 1;

                getPagination();
                // conf.onChange()函数
                if(conf.onChange) {    
                    conf.onChange();
                }
            };

            // 跳转页
            scope.jumpToPage = function() {
                num = scope.jumpPageNum;
                if(num.match(/\d+/)) {
                    num = parseInt(num, 10);
                
                    if(num && num != conf.currentPage) {
                        if(num > conf.numberOfPages) {
                            num = conf.numberOfPages;
                        }

                        // 跳转
                        if (conf.currentPage != num) {
                            conf.currentPage = num;
                            getPagination();
                            // conf.onChange()函数
                            if(conf.onChange) {    
                                conf.onChange();
                            }
                            scope.jumpPageNum = num;
                        }
                    }
                }
                
            };

            scope.jumpPageKeyUp = function(e) {
                var keycode = window.event ? e.keyCode :e.which;
                var value = e.target.value;
                value = value.replace(/\D/g,'')
                if (Number.isNaN(value)) {
                  scope.jumpPageNum = value;
                }
                if(keycode == 13) {
                    scope.jumpToPage();
                }
            }

            scope.$watch('conf.totalItems', function(value, oldValue) {
                // 在无值或值相等的时候，去执行onChange事件
                if(value && value != oldValue && oldValue != 0) {
                    if(conf.onChange) {
                        conf.currentPage = 1;    
                        conf.onChange();
                    }
                }
                getPagination();
            })
            
        }
    };
}
