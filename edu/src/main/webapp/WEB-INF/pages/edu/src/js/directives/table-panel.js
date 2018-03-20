"use strict"

angular.module('ework-ui')
  .directive('rdTablePanel',[rdTablePanel]);

function rdTablePanel() {
  return {
    restrict: 'EA',
    replace: true,
    transclude: {
      title: '?rdPanelHeader',
      toolbar: '?rdPanelToolbar',
      searchbar: '?rdPanelSearchbar',

    },
    scope: {

    }
  }
}