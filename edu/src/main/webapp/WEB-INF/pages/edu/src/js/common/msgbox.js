angular.module('ework-ui')
  .factory('$uibMsgbox', ['$uibModal', $uibMsgbox])
  .filter('trustHtml', function ($sce) {
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    })

function $uibMsgbox($uibModal) {
  
  function openDialog(conf) {
    return $uibModal.open({
      animation: true,
      // size: 'sm',
      backdrop: 'static',
      controllerAs: '$ctrl',
      template: '<div class="msgbox"><div class="modal-header">'
            + '<h3 class="modal-title" id="modal-title"><i class="iconfont {{iconCls}} mr5"></i>{{title}}<button type="button" class="close" ng-click="$dismiss()"><span aria-hidden="true">×</span>  </button></h3>'
        + '</div>'
        + '<div class="modal-body" id="modal-body">'
            + '<div class="content" ng-bind-html="content|trustHtml"></div>'
        + '</div>'
        + '<div class="modal-footer" style="text-align:center;">'
            + '<button ng-if="showCancelBtn" class="btn btn-default" ng-click="$ctrl.cancel()">{{cancelText}}</button>'
            + '<button class="btn btn-primary" ng-click="$ctrl.ok()">{{okText}}</button>'
        + '</div></div>',
      resolve: {
        conf: function () {
          return conf
        }
      },
      controller: function ($scope, $uibModalInstance,conf) {
        var $ctrl = this;
        $scope.title = conf.title;
        $scope.content = conf.content;
        $scope.iconCls = conf.iconCls;       
        if (typeof conf.showCancelBtn != 'undefined') {
          $scope.showCancelBtn = conf.showCancelBtn;
        } else {
          $scope.showCancelBtn = true;
        }
        $scope.okText = conf.okText || '确定';
        $scope.cancelText = conf.cancelText || '取消';

        $ctrl.ok = function () {
          $uibModalInstance.close('yes')
        }
        $ctrl.cancel = function () {
          $uibModalInstance.dismiss('cancel')
        }
      }
    }).result.then(function () {
      conf.callback('yes')
    }, function () {
      conf.callback('cancel')
    })
  }

  return {
    alert: function () {
      var content = arguments[0] || '';
      var title = typeof arguments[1] == 'string' ? arguments[1] : '提示'
      var callback = typeof arguments[1] == 'function' ? arguments[1]
        : typeof arguments[2] == 'function' ? arguments[2]
        : function () {}
      openDialog({
        type: 'alert',
        content: content,
        title: title || '提示',
        showCancelBtn: false,
        iconCls: 'icon-public_reminder i-text-primary',
        callback: callback
      })
    },
    success: function () {
      var content = arguments[0] || '';
      var title = typeof arguments[1] == 'string' ? arguments[1] : '成功'
      var callback = typeof arguments[1] == 'function' ? arguments[1]
        : typeof arguments[2] == 'function' ? arguments[2]
        : function () {}
      openDialog({
        type: 'success',
        showCancelBtn: false,
        iconCls: 'icon-public_succeed i-text-success',
        content: content,
        title: title || '成功',
        callback: callback
      })
    },
    confirm: function () {
      var content = '',
        title = '提示',
        cancelText = '取消',
        okText = '确定',
        callback = null,
        showCancelBtn = true;

      if (typeof arguments[0] == 'object') {
        var conf = arguments[0]
        content = conf.content;
        title = conf.title || title;
        cancelText = conf.cancelText || cancelText;
        okText = conf.okText || okText;
        if (typeof conf.showCancelBtn != 'undefined') {
          showCancelBtn = conf.showCancelBtn;
        }
        callback = conf.callback || function () {}
      } else {
        var content = arguments[0] || '';
        var title = typeof arguments[1] == 'string' ? arguments[1] : '提示'
        var callback = typeof arguments[1] == 'function' ? arguments[1]
          : typeof arguments[2] == 'function' ? arguments[2]
          : function () {}
      }

      openDialog({
        type: 'confirm',
        iconCls: 'icon-public_warning i-text-warning',
        content: content,
        title: title,
        cancelText: cancelText,
        okText: okText,
        showCancelBtn: showCancelBtn,
        callback: callback
      })
    },
    warn: function () {
      var content = arguments[0] || '';
      var title = typeof arguments[1] == 'string' ? arguments[1] : '警告'
      var callback = typeof arguments[1] == 'function' ? arguments[1]
        : typeof arguments[2] == 'function' ? arguments[2]
        : function () {}
      openDialog({
        type: 'warn',
        iconCls: 'icon-public_warning i-text-warning',
        content: content,
        title: title,
        showCancelBtn: false,
        callback: callback
      })
    },
    error: function () {
      var content = arguments[0] || '';
      var title = typeof arguments[1] == 'string' ? arguments[1] : '错误'
      var callback = typeof arguments[1] == 'function' ? arguments[1]
        : typeof arguments[2] == 'function' ? arguments[2]
        : function () {}
      openDialog({
        type: 'warn',
        iconCls: 'icon-public_warning i-text-danger',
        content: content,
        title: title,
        showCancelBtn: false,
        callback: callback
      })
    },
    // waiting 
    waiting: function (content) {
      var uibModalInstance = null;
      content = content || '操作中，请稍候...'
      var modalInstance = $uibModal.open({
        animation: true,
        size: 'sm',
        backdrop: 'static',
        controllerAs: '$ctrl',
        template: '<div class="modal-body" id="modal-body">'
              + '<div class="content">{{content}}</div>'
          + '</div>',
        resolve: {
          conf: function () {
            return {
              content: content
            }
          }
        },
        controller: function ($scope, $uibModalInstance,conf) {
          var $ctrl = this;
          $scope.content = conf.content;
        }
      })
      return modalInstance
    },
    // 进度条
    progress: function () {
      return {
        setProgress: function () {

        }
      }
    },
    // TODO
    prompt: function (title, defaultValue, callback){
      var title = arguments[0] || '';
      var defaultValue = typeof arguments[1] == 'string' ? arguments[1] : ''
      var callback = typeof arguments[1] == 'function' ? arguments[1]
        : typeof arguments[2] == 'function' ? arguments[2]
        : function () {}
      openDialog({
        type: 'warn',
        iconCls: 'icon-public_reminder i-text-primary',
        content: content,
        title: title,
        callback: callback
      })
    }
  }
}