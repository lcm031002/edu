/**
 * Widget Header Directive
 */

angular
    .module('ework-ui')
    .directive('rdWidgetHeader', rdWidgetTitle);

function rdWidgetTitle() {
    var directive = {
        requires: '^rdWidget',
        scope: {
            title: '@',
            icon: '@'
        },
        transclude: true,
        template: '<div class="widget-header container-fluid"><div class="row"><div class="col-xs-12 col-sm-12"><i class="fa" ng-class="icon"></i> {{title}} </div><div class="col-xs-12 col-sm-12" ng-transclude></div></div></div>',
        restrict: 'E'
    };
    return directive;
};