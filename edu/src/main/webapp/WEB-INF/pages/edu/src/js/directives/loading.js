/**
 * Loading Directive
 * @see http://tobiasahlin.com/spinkit/
 */

angular
    .module('ework-ui')
    .directive('rdLoading', rdLoading);

function rdLoading() {
    var directive = {
        restrict: 'AE',
        transclude: true,
        template: 
        		'<div class="modal fade in" style ="display:block;background: rgba(0,0,0,.6); top: 0;">'
        	+ 	'<div class="modal-dialog modal-sm" style="top: 130px;"><div class="modal-content">'
        	+ 		'<div class="modal-content" style="box-shadow: 0 5px 15px rgba(0,0,0,.3);">'
        	+   		'<div class="text-center" style="padding: 20px;" ng-transclude>'
        	+			'</div>'
        	+ 	'</div'
        	+	'</div>'
    };
    return directive;
};