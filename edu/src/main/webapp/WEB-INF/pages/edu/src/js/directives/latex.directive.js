angular.module('ework-ui')
  .directive('latex', function() {
    return {
      restrict: 'AE',
      link: function(scope, element) {
        var newDom = element.clone();
        element.replaceWith(newDom);
        scope.$watch(function() {
          return element.html();
        }, function() {
          newDom.html(element.html());
          if(window.MathJax){
            window.MathJax.Hub.Typeset(newDom[0]);
          }
        });
      }
    };
  });
