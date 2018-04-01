/**
 * 
 */
angular.module('ework-ui').controller('OrgCtrl', [
    '$scope',
    '$log',
    '$state',
    '$timeout',
    '$uibModal',
    '$uibMsgbox',
    'OrgService',
    'erp_dictService',
    'erp_organizationService',
    OrgCtrl]);

function OrgCtrl($scope,
    $log,
    $state,
    $timeout,
    $uibModal,
    $uibMsgbox,
    OrgService,
    erp_dictService,
    erp_organizationService) {
	$scope.orgDatas = [];
	$scope.itemOperateType = '';
    $scope.selectedItem = {};
    $scope.orgItem = {};
    $scope.productLineList = [];
    $scope.dictOrgList = [];
    $scope.bgImgUrlList = [];
    $scope.dictOrgKindList = []; // 校区类型选择框下拉值
    
    $scope.mapOptions = {
        searchInfo: '',
        longitude: 0,
        latitude: 0
    }
    $scope.reloadPage = function(){window.location.reload();}

    /**
     * 打开添加组织机构的面板
     */

    $scope.toAddItem = function(item){
    	$scope.itemOperateType = 'add';
    	
        $scope.selectedItem = item;
        $scope.selectedItem.address = null;
        $scope.selectedItem.longitude = null;
        $scope.selectedItem.latitude = null;
        $scope.selectedItem.domain = null;
        $scope.selectedItem.mchid = null;
        $scope.selectedItem.terminalNo = null;
        $scope.selectedItem.phone = null;
        $scope.selectedItem.org = "" + item.org;
        $scope.selectedItem.orgKind = "" + item.orgKind;
        
        if (item.org_type != 4) {
        	$scope.selectedItem.parent_id = item.id;
            $scope.selectedItem.parent_org_name = item.org_name;
        } else {
        	$scope.orgSelectType.type = item.org_type.toString();
        }
    	
        $('#OrgItemPanel').modal({'show':'true','backdrop':'static'});
        $('#OrgItemPanel').on('shown.bs.modal', function(){
            $scope.initOrgMap('add-org-map');
        });
    }
    
    $scope.toUpdateItem = function(item){
        $scope.selectedItem = item;
        if (item.org) {
          $scope.selectedItem.org = "" + item.org;
        }

        if (item.orgKind) {
          $scope.selectedItem.orgKind = "" + item.orgKind;
        }
        $scope.itemOperateType = 'update';
        $scope.orgSelectType.type = item.org_type.toString();
        
        if (item.logo) {
        	$scope.bgImgUrlList = [];
        	$scope.bgImgUrlList.push(item.logo);
        }
        
        $('#OrgItemPanel').modal('show');
        $('#OrgItemPanel').on('shown.bs.modal', function(){
            $scope.initOrgMap('add-org-map');
        })
    }
    
    $scope.toDeleteItem = function(item){
        $scope.selectedItem = item;
    	$scope.title=$scope.selectedItem.status==1?"禁用":"启用";
    	$scope.modalBody="确认"+$scope.title+"当前【"+$scope.selectedItem.org_name+"】组织机构吗?";
        $scope.itemOperateType = 'remove';
        $('#DeleteOrgItemPanel').modal('show');
    }
    
    $scope.initOrgMap = function (mapElementId) {
        if (!$scope.orgMap) {
            $scope.orgMap = new BMap.Map(mapElementId);
            $scope.orgMap.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
            $scope.orgMap.enableScrollWheelZoom(true);
            $scope.orgMapLocal = new BMap.LocalSearch($scope.orgMap, {
                renderOptions:{
                    map: $scope.orgMap,
                    panel: 'search-result-panel'
                },
                onInfoHtmlSet: function (poi, html) {
                    $scope.$apply(function() {
                        $scope.selectedItem.address = poi.address;
                        $scope.selectedItem.latitude = poi.point.lat;
                        $scope.selectedItem.longitude = poi.point.lng;
                    })
                }
            });
        }
        
    }

    $scope.orgMapSearch = function (address) {
        $scope.orgMapLocal.search(address)
    }

    /**
     * 添加组织机构
     */
    $scope.saveOrgItem = function(){
        if($scope.itemOperateType=='add'){
            $scope.addOrgItem();
        }else if($scope.itemOperateType=='update'){
            $scope.updateOrgItem();
        }else if($scope.itemOperateType=='remove'){
            $scope.removeOrgItem();
        }
    }

    $scope.addOrgItem = function(){
    	var item={};
    	$scope.orgItem.status = 1;
 		$scope.orgItem.parent_id = $scope.selectedItem.parent_id;
 		$scope.orgItem.org_name = $scope.selectedItem.name;
 		$scope.orgItem.org_type = $scope.orgSelectType.type;
 		if (!isEmpty($scope.selectedItem.address)) {
 			$scope.orgItem.address = $scope.selectedItem.address;
 		}
 		if (!isEmpty($scope.selectedItem.longitude)) {
 			$scope.orgItem.longitude = $scope.selectedItem.longitude;
 		}
 		if (!isEmpty($scope.selectedItem.latitude)) {
 			$scope.orgItem.latitude = $scope.selectedItem.latitude;
 		}
 		if (!isEmpty($scope.selectedItem.product_line)) {
 			$scope.orgItem.product_line = $scope.selectedItem.product_line;
 		}
 		if (!isEmpty($scope.selectedItem.org)) {
 			$scope.orgItem.org = $scope.selectedItem.org;
 		}
 		if (!isEmpty($scope.selectedItem.domain)) {
 			$scope.orgItem.domain = $scope.selectedItem.domain;
 		}
 		if (!isEmpty($scope.selectedItem.mchid)) {
 			$scope.orgItem.mchid = $scope.selectedItem.mchid;
 		}
 		if (!isEmpty($scope.selectedItem.terminalNo)) {
 			$scope.orgItem.terminalNo = $scope.selectedItem.terminalNo;
 		}
 		if (!isEmpty($scope.selectedItem.phone)) {
 			$scope.orgItem.phone = $scope.selectedItem.phone;
 		}
 		if (!isEmpty($scope.selectedItem.oldStuIntegral)) {
 			$scope.orgItem.oldStuIntegral = $scope.selectedItem.oldStuIntegral;
 		}
 		if (!isEmpty($scope.selectedItem.orgKind)) {
 			$scope.orgItem.orgKind = $scope.selectedItem.orgKind;
 		}
 		if (!isEmpty($scope.selectedItem.email)) {
 			$scope.orgItem.email = $scope.selectedItem.email;
 		}
      if (!isEmpty($scope.selectedItem.shortOrgName)) {
        $scope.orgItem.shortOrgName = $scope.selectedItem.shortOrgName;
      }
 		
 		if(!$scope.orgItem.org_name){
 			$uibMsgbox.error("新组织不能为空！");
			return;
		}

      if(!$scope.orgItem.shortOrgName){
        $uibMsgbox.error("组织机构简称不能为空！");
        return;
      }
 		
 		if (!$scope.orgItem.oldStuIntegral && $scope.orgItem.org_type == 3) {
 			$uibMsgbox.error("老学员积分不能为空！");
			return;
 		}
        var _waitingModal = $uibMsgbox.waiting('添加中，请稍候...');
        erp_organizationService.add($scope.orgItem,function(resp){
            _waitingModal.close();
        	if(!resp.error){
	                $('#AddOrgItemPanel').modal('hide');
	                $uibMsgbox.success("添加成功！");
	                $scope.orgDatas[ $scope.orgDatas.length]= $scope.orgItem;
	                $scope.reloadPage();
            } else {
            	$uibMsgbox.error("添加失败！失败信息："+resp.message);
            }
        })
    }

    $scope.updateOrgItem = function(){
    	delete $scope.selectedItem.parent_org_name;
    	if (!$scope.selectedItem.oldStuIntegral && $scope.orgSelectType.type == 3) {
 			$uibMsgbox.error("老学员积分不能为空！");
			return;
 		}
        erp_organizationService.update($scope.selectedItem,function(resp){
            if(!resp.error){
                $('#UpdateOrgItemPanel').modal('hide');
                $uibMsgbox.success("修改成功！");
                $scope.reloadPage();
            }else{
            	$uibMsgbox.error("修改失败！失败信息："+resp.message);
            }
        })
    }

    $scope.removeOrgItem = function(){
    	var item={};
    	$log.log("item id is " + $scope.selectedItem.id);
    	OrgService.remove({"id" : $scope.selectedItem.id}, function(resp){
    		if(resp.error == false){
                $('#DeleteOrgItemPanel').modal('hide');
                $uibMsgbox.success($scope.title + "成功！");
                $scope.reloadPage();
            }else{
            	$uibMsgbox.error($scope.title + "失败！失败信息："+resp.message);
            }
        })
    }


    /**
     * 组织类型下拉框
     */   
    $scope.orgSelectType = { type: '1' };

    $scope.orgType =  [
        { type: '2', name: '地区级别' },
        { type: '3', name: '团队级别' },
        { type: '4', name: '校区级别' }
    ];
        
    /**
     * 查询组织机构
     */
    function queryOrg() {
        var param = {};
        erp_organizationService.query(param, function (resp) {
            if (resp.error == false) {
                $scope.orgDatas = resp.data;
                $scope.delayTree();
            }
        })
    };

    //延迟生成树结构  
    $scope.delayTree = function () {
        var todo = function () {
            $scope.toTree();
        };
        $timeout(todo, 0);
    }

    $scope.toTree = function () {
        $("#tree_table_id").treetable({ expandable: true });

        // Highlight selected row
        $("#tree_table_id tbody").on("mousedown", "tr", function () {
            $(".selected").not(this).removeClass("selected");
            $(this).toggleClass("selected");
        });

        $("#tree_table_id .folder").each(function () {
            $(this).parents("#tree_table_id tr").droppable({
                accept: ".file, .folder",
                drop: function (e, ui) {
                    var droppedEl = ui.draggable.parents("tr");
                    $("#tree_table_id").treetable("move", droppedEl.data("ttId"), $(this).data("ttId"));
                },
                hoverClass: "accept",
                over: function (e, ui) {
                    var droppedEl = ui.draggable.parents("tr");
                    if (this != droppedEl[0] && !$(this).is(".expanded")) {
                        $("#tree_table_id").treetable("expandNode", $(this).data("ttId"));
                    }
                }
            });
        });

    }

    $scope.queryProductLine = function () {
        erp_organizationService.queryProductLine({}, function (resp) {
            if (!resp.error && resp.data && resp.data.length > 0) {
                $scope.productLineList = resp.data;
            }
        })
    }

    $scope.queryDictOrgList = function () {
        erp_organizationService.queryDictOrgList({}, function (resp) {
            if (!resp.error && resp.data && resp.data.length > 0) {
                $scope.dictOrgList = resp.data;
            }
        })
    }

    $scope.queryDictOrgKindList = function () {
        erp_dictService.query({ "code": "orgKind" }, function (resp) {
            if (!resp.error && resp.data && resp.data.length > 0) {
                $scope.dictOrgKindList = resp.data;
            }
        })
    }

    $scope.uploadLogo = function (img) {
        erp_organizationService.uploadLogo({
            id: $scope.selectedItem.id,
            logo: img
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success("上传成功");
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    $scope.deleteLogo = function (img) {
        erp_organizationService.deleteLogo({
            id: $scope.selectedItem.id
        }, function (resp) {
            if (!resp.error) {
                $uibMsgbox.success("删除成功");
            } else {
                $uibMsgbox.error(resp.message);
            }
        });
    }

    queryOrg();
    $scope.queryProductLine();
    $scope.queryDictOrgList();
    $scope.queryDictOrgKindList();

}