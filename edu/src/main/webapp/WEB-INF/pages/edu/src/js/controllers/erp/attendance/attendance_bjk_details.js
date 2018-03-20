/**
 * Created by Liyong.zhu on 2017/2/18.
 */
"use strict";
angular
    .module('ework-ui')
    .controller('erp_attendanceBJKDetailsController', [
        '$rootScope',
        '$uibMsgbox',
        '$sce',
        '$scope',
        '$log',
        'erp_attendanceVideoService',
        erp_attendanceBJKDetailsController]);

function erp_attendanceBJKDetailsController(
    $rootScope,
    $uibMsgbox,
    $sce,
    $scope,
    $log,
    erp_attendanceVideoService) {
    $scope.videoNo = $("#rootIndex_videoNo").val();
    $scope.broadType = true;
    $scope.broadUrl = '';
    /**
     * 查询课程
     */
    $scope.queryVideo = function(){
        var param = {
            videoNo:$scope.videoNo
        }
        $scope.loadStatues = true;
        erp_attendanceVideoService.query(param,function(resp){
            $scope.loadStatues = false;
            if(!resp.error){
                $scope.videoList = resp.data;
            }else{
                $uibMsgbox.error(resp.message);
            }
        })
    }
    $scope.queryVideo();
    $scope.broadCast = function(url){
        $scope.broadType = false;
        $scope.broadUrl = url;
    }
    $scope.close = function(){
        $scope.broadType = true;
    }
    $scope.videoUrlFun = function(url){
        var urlFun = $sce.trustAsResourceUrl(url);
        return urlFun;
    };
}