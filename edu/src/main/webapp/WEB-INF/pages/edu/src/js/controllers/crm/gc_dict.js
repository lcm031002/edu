"use strict";
angular.module('ework-ui').controller('crmDictController', [
  '$rootScope',
  '$scope',
  'crm_queryDictDataService',
  'crm_queryBranchsService',
  'crm_queryGradesService',
  'crm_querySingleRescRecService',
  'crm_queryRescRecProcService',
  'crm_queryAschListService',
  'crm_queryCnselorListService',
  'crm_queryRescRecListService',
  'crm_GxhCrmDictService',
  'crm_DictDataService',
  'crm_DictTypeService',
  crmDictController
]);

function crmDictController(
  $rootScope,
  $scope,
  queryDictDataService,
  queryBranchsService,
  queryGradesService,
  querySingleRescRecService,
  queryRescRecProcService,
  queryAschListService,
  queryCnselorListService,
  queryRescRecListService,
  GxhCrmDictService,
  DictDataService,
  DictTypeService
) {
  $scope.DictType = {}; // 字典类型
  $scope.DictData = {}; // 字典条码

  // 分页_start
  $scope.DictType.PageObj = new PageObj();
  $scope.DictData.PageObj = new PageObj();
  $scope.DictType.pageLoading = '';
  $scope.DictData.pageLoading = '';
  // 字典类型分页数据请求回调
  $scope.DictType.pageCallBack = function(data) {
      $scope.DictType.pageLoading = '';
      $scope.DictType.pageResults = [];
      var newArray = new Array();
      for (var i = 0; i < data.rows.length; i++) {
        data.rows[i].selected = '';
        newArray.push(data.rows[i]);
      }
      if (!$scope.$$phase) {
        $scope
          .$apply(function() {
            $scope.DictType.pageResults = newArray;
            $scope.DictType.PageObj.totalPage = data.totalPage;
          });
      } else {
        $scope.DictType.pageResults = newArray;
        $scope.DictType.PageObj.totalPage = data.totalPage;
      }

      var type_ids = '';
      for (var i = 0; i < $scope.DictType.pageResults.length; i++) {
        if (type_ids.length > 0) {
          type_ids += ",";
        }
        type_ids += $scope.DictType.pageResults[i].id;
      }

      $scope.DictData.PageObj.param.p_type_id = null;
      $scope.DictData.PageObj.param.p_type_ids = type_ids;
      $scope.DictData.searchPage();
    }
    // 字典条码分页数据请求回调
  $scope.DictData.pageCallBack = function(data) {
    $scope.DictData.pageLoading = '';
    $scope.DictData.pageResults = [];
    var newArray = [];
    for (var i = 0; i < data.rows.length; i++) {
      data.rows[i].selected = '';
      newArray.push(data.rows[i]);
    }
    if (!$scope.$$phase) {
      $scope
        .$apply(function() {
          $scope.DictData.pageResults = newArray;
          $scope.DictData.PageObj.totalPage = data.totalPage;
        });
    } else {
      $scope.DictData.pageResults = newArray;
      $scope.DictData.PageObj.totalPage = data.totalPage;
    }
  }

  $scope.DictType.pageLoading = 'loading...';

  $scope.DictType.PageObj.init("/gxhcrm/dict/type/page", {},
    $scope.DictType.pageCallBack, 10, 7, 1, $(''));

  $scope.DictData.pageLoading = 'loading...';
  $scope.DictData.PageObj.init("/gxhcrm/dict/data/page", {},
    $scope.DictData.pageCallBack, 8, 7, 1, $(''));

  $scope.DictType.begin = function() {
    $scope.DictType.pageLoading = 'loading...';
    $scope.DictType.PageObj.begin();
  }
  $scope.DictType.up = function() {
    if ($scope.DictType.PageObj.page > 1) {
      $scope.DictType.pageLoading = 'loading...';
    }
    $scope.DictType.PageObj.up();
  }
  $scope.DictType.down = function() {
    if ($scope.DictType.PageObj.page < $scope.DictType.PageObj.totalPage) {
      $scope.DictType.pageLoading = 'loading...';
    }
    $scope.DictType.PageObj.down();
  }
  $scope.DictType.end = function() {
    $scope.DictType.pageLoading = 'loading...';
    $scope.DictType.PageObj.end();
  }
  $scope.DictData.begin = function() {
    $scope.DictData.pageLoading = 'loading...';
    $scope.DictData.PageObj.begin();
  }
  $scope.DictData.up = function() {
    if ($scope.DictData.PageObj.page > 1) {
      $scope.DictData.pageLoading = 'loading...';
    }
    $scope.DictData.PageObj.up();
  }
  $scope.DictData.down = function() {
    if ($scope.DictData.PageObj.page < $scope.DictData.PageObj.totalPage) {
      $scope.DictData.pageLoading = 'loading...';
    }
    $scope.DictData.PageObj.down();
  }
  $scope.DictData.end = function() {
    $scope.DictData.pageLoading = 'loading...';
    $scope.DictData.PageObj.end();
  }
  $scope.DictType.searchPage = function() {
    $scope.DictType.pageLoading = 'loading...';
    $scope.DictType.PageObj.page = 1;
    $scope.DictType.PageObj.initPage();
  }
  $scope.DictData.searchPage = function() {
      $scope.DictData.pageLoading = 'loading...';
      $scope.DictData.PageObj.page = 1;
      $scope.DictData.PageObj.initPage();
    }
    // 分页_end

  // 表格选中_start
  $scope.selectDictType = function(dictType) {
    $scope.DictType.currentData = dictType;
    for (var i = 0; i < $scope.DictType.pageResults.length; i++) {
      $scope.DictType.pageResults[i].selected = '';
    }
    if (dictType) {
      dictType.selected = 'selected';
      if (dictType.id) {
        $scope.DictData.PageObj.param.p_type_ids = null;
        $scope.DictData.PageObj.param.p_type_id = dictType.id;
        $scope.DictData.searchPage();
      }
    }
  }

  $scope.selectDictData = function(dictData) {
      for (var i = 0; i < $scope.DictData.pageResults.length; i++) {
        $scope.DictData.pageResults[i].selected = '';
      }
      if (dictData) {
        dictData.selected = 'selected';
      }
    }
    // 表格选中_end

  // 新增修改_start
  $scope.DictTypeSettingDialog = function(
    setting_type, dictType) {
    $scope.DictType.settingData = {};
    $scope.DictType.currentData = {};
    $scope.DictType.settingData.title = '新增字典类型';
    $scope.DictType.settingData.setting_type = 1;
    if (setting_type == 2) {
      // 修改
      $scope.DictType.settingData.title = '修改字典类型';
      $scope.DictType.settingData.setting_type = 2;
      $scope.DictType.settingData = dictType;
      $scope.DictType.currentData = dictType;
      $scope.selectDictType(dictType);
    }
    $scope.DictType.settingDialog = 'open';
  }
  $scope.DictTypeCloseSettingDialog = function() {
    $scope.DictType.settingDialog = '';
  }

  $scope.DictTypeSetting = function() {
    if ($scope.DictType.settingData.wait) {
      return;
    }
    if (isEmpty($scope.DictType.currentData.name)) {
      $scope.DictType.settingData.msg = '字典名称不可为空';
      return;
    }
    if (isEmpty($scope.DictType.currentData.code)) {
      $scope.DictType.settingData.msg = '字典编码不可为空';
      return;
    }
    $scope.DictType.settingData.wait = false;
    if (!isEmpty($scope.DictType.currentData.id)) {
      delete $scope.DictType.currentData.wait;
      delete $scope.DictType.currentData.selected;
      // 修改功能
      DictTypeService.update(
        $scope.DictType.currentData,
        function(rs) {
          if (rs.status == 1) {
            alert('修改成功！');
            $scope
              .DictTypeCloseSettingDialog();
            $scope.DictType
              .searchPage();
          }
          if (rs.status == 0) {
            $scope.DictType.settingData.msg = rs.errMsg;
          }
        },
        function(rs) {
          $scope.DictType.settingData.msg = rs;
        });
    } else {
      delete $scope.DictType.currentData.wait;
      delete $scope.DictType.currentData.selected;
      // 字典的新增功能
      DictTypeService.add(
        $scope.DictType.currentData,
        function(rs) {
          if (rs.status == 1) {
            alert('新增成功！');
            $scope
              .DictTypeCloseSettingDialog();
            $scope.DictType
              .searchPage();
          }
          if (rs.status == 0) {
            $scope.DictType.settingData.msg = rs.errMsg;
          }
        },
        function(rs) {
          $scope.DictType.settingData.msg = rs;
        }
      );
    }

  }
  $scope.DictTypeClear = function() {
    $scope.DictType.currentData = null;
  }
  // 新增修改_end

  // 新增修改_start
  $scope.DictDataSettingDialog = function(
    setting_type, dictData) {
	  if (!$scope.DictType.currentData) {
			alert('请先选择字典类型');
			return;
		}
    $scope.DictData.currentData = {};
    $scope.DictData.settingData = {};
    $scope.DictData.settingData.title = '新增字典类型';
    $scope.DictData.settingData.setting_type = 1;
    if (setting_type == 2) {
      $scope.selectDictData(dictData);
      $scope.DictData.settingData.title = '修改字典类型';
      $scope.DictData.settingData.setting_type = 2;
      $scope.DictData.settingData = dictData;
      $scope.DictData.currentData = dictData;
    }
    $scope.DictData.settingDialog = 'open';
  }
  
  $scope.DictDataCloseSettingDialog = function() {
    $scope.DictData.settingDialog = '';
  }

  $scope.DictDataSetting = function() {
    if ($scope.DictData.settingData.wait) {
      return;
    }
    if (!$scope.DictType.currentData) {
      $scope.DictData.settingData.msg = '请先选择字典类型';
      return;
    }
    if (isEmpty($scope.DictData.currentData.name)) {
      $scope.DictData.settingData.msg = '条目名称不可为空';
      return;
    }
    if (isEmpty($scope.DictData.currentData.code)) {
      $scope.DictData.settingData.msg = '条目编码不可为空';
      return;
    }
    $scope.DictData.settingData.wait = false;
    $scope.DictData.currentData.type_id = $scope.DictType.currentData.id;
    if (!isEmpty($scope.DictData.currentData.id)) {
      delete $scope.DictData.currentData.wait;
      delete $scope.DictData.currentData.selected;
      /*
       * status 1 ""
       */
      // 修改功能
      DictDataService
        .update(
          $scope.DictData.currentData,
          function(rs) {
            if (rs.status == 1) {
              alert('修改成功！');
              $scope
                .DictDataCloseSettingDialog();
              $scope.DictData
                .searchPage();
            }
            if (rs.status == 0) {
              $scope.DictData.settingData.msg = rs.errMsg;
            }
          },
          function(rs) {
            $scope.DictData.settingData.msg = rs;
          });

    } else {
      delete $scope.DictData.currentData.wait;
      delete $scope.DictData.currentData.selected;
      // 字典的新增功能
      DictDataService
        .add(
          $scope.DictData.currentData,
          function(rs) {
            if (rs.status == 1) {
              alert('新增成功！');
              $scope
                .DictDataCloseSettingDialog();
              $scope.DictData
                .searchPage();
            }
            if (rs.status == 0) {
              $scope.DictData.settingData.msg = rs.errMsg;
            }
          },
          function(rs) {
            $scope.DictData.settingData.msg = rs;
          });
    }
  }
  $scope.DictDataClear = function() {
      $scope.DictData.currentData.name = "";
      $scope.DictData.currentData.code = "";
      $scope.DictData.currentData.descdtl = "";
    }
    // 新增修改_end

  // 搜索_start
  $scope.DictType.name_code_desc = '请输入：名称/编码/描述';
  $scope.DictType.name_code_desc_blur = function() {
    if ($scope.DictType.name_code_desc == '')
      $scope.DictType.name_code_desc = '请输入：名称/编码/描述';
  }
  $scope.DictType.name_code_desc_fous = function() {
    if ($scope.DictType.name_code_desc == '请输入：名称/编码/描述')
      $scope.DictType.name_code_desc = '';
  }
  $scope.DictType.search = function() {
      if ($scope.DictType.name_code_desc == '请输入：名称/编码/描述')
        $scope.DictType.name_code_desc = '';
      $scope.DictType.PageObj.param.p_name_code_desc = $scope.DictType.name_code_desc;
      $scope.DictType.searchPage();
    }
    // 搜索_end
    // data搜索_start
  $scope.DictData.name_code_desc = '请输入：名称/编码/描述';
  $scope.DictData.name_code_desc_blur = function() {
    if ($scope.DictData.name_code_desc == '')
      $scope.DictData.name_code_desc = '请输入：名称/编码/描述';
  }
  $scope.DictData.name_code_desc_fous = function() {
    if ($scope.DictData.name_code_desc == '请输入：名称/编码/描述')
      $scope.DictData.name_code_desc = '';
  }
  $scope.DictData.search = function() {
      if ($scope.DictData.name_code_desc == '请输入：名称/编码/描述')
        $scope.DictData.name_code_desc = '';
      $scope.DictData.PageObj.param.p_name_code_desc = $scope.DictData.name_code_desc;
      $scope.DictData.searchPage();
    }
    // data搜索_end
    // 删除
  $scope.remove = function(id) {
    if (confirm('您确定要删除此条数据？')) {
      DictDataService.remove({
        ids: id
      }, function(rs) {
        if (rs.status == 1) {
          alert('删除成功！');
          $scope.DictData.searchPage();
        }
        if (rs.status == 0) {
          alert('删除失败，原因：' + rs.errMsg + '请联系管理员！');
        }
      }, function(rs) {
        alert('删除失败，原因：' + rs + '请联系管理员！');
      });
    }
  }

  $scope.setting = {
    check: {
      enable: true
    },
    view: {
      selectedMulti: true
    },
    edit: {
      enable: true,
      editNameSelectAll: true,
      showRemoveBtn: false,
      showRenameBtn: false,
    },
    key: {
      name: 'menu_name'
    },
    data: {
      simpleData: {
        enable: true,
        idKey: 'id',
        pIdKey: 'parent_id',
        rootPId: 0
      }
    },
    callback: {}
  };

  $scope.showAuth = function(item) {
    $scope.currentItem = item;
    $scope.menuParam = {};
    if (item) {
      $scope.menuParam.dataId = item.id;
    }
    $('#selectAll').removeAttr('checked');
    zTreeObj($scope, DictDataService);
    $('#auth').modal("toggle");
  }

  $scope.saveAuth = function() {
    DictDataService.auth({
      data_id: $scope.currentItem.id,
      orgMenus: $scope.zTree
        .getCheckedNodes(true)
    }, function(rs) {
      if (rs.status == 1) {
        alert('授权成功！');
        $('#auth').modal('toggle');
        $scope.DictData.searchPage();
      }
      if (rs.status == 0) {
        alert('授权失败！');
        $scope.errMsg = rs.errMsg;
      }
    });
  }

}
