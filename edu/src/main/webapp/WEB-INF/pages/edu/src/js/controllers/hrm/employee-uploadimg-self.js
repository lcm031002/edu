/**
 * Created by Liyong.zhu on 2017/1/17.
 */
/**
 *
 */
angular.module('ework-ui')
    .controller('hrm_uploadImgSelfController', [
        '$scope',
        '$log',
        '$state',
        '$rootScope',
        'hrmUploadImgService',
        hrm_uploadImgSelfController]);

function hrm_uploadImgSelfController(
                      $scope,
                      $log,
                      $state,
                      $rootScope,
                      hrmUploadImgService
    ){

    var options =
    {
        thumbBox: '.thumbBox',
        spinner: '.spinner',
        imgSrc: 'images/avatar.jpg'
    }
    var cropper = $('.imageBox').cropbox(options);
    $('#upload-file').on('change', function(){
        var reader = new FileReader();
        reader.onload = function(e) {
            options.imgSrc = e.target.result;
            cropper = $('.imageBox').cropbox(options);
        }
        reader.readAsDataURL(this.files[0]);
        this.files = [];
    })
    $('#btnCrop').on('click', function(){
        var img = cropper.getDataURL();
        $('.cropped').html('');
        $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:15%;margin-top:4px;border-radius:1000px;box-shadow:0px 0px 12px #7E7E7E;float:left;margin-left:15px;" >');
        $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:25%;margin-top:4px;border-radius:1000px;box-shadow:0px 0px 12px #7E7E7E;float:left; margin-left:20px;">');
        $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:35%;margin-top:4px;border-radius:1000px;box-shadow:0px 0px 12px #7E7E7E;float:left; margin-left:20px;">');
    })
    $('#btnZoomIn').on('click', function(){
        cropper.zoomIn();
    })
    $('#btnZoomOut').on('click', function(){
        cropper.zoomOut();
    })

    //暂存的订单
    $scope.employeeId = $rootScope.curEmployee.id;

    $scope.uploadSave = function(){
        var imageData = cropper.getDataURL();
        if(imageData == null || $.trim(imageData.length) == 0){
            return false;
        }
        //openWait = true;
        var param = {};
        param._imageData = imageData;
        hrmUploadImgService.post(param,function(resp){
            if(resp.code == 200){
                $scope.imageUrl = resp.data;
                updateImage();
            }else{
                alert('上传失败咯！' + resp.msg);
            }
        });
    }



    function updateImage(){
        var param={};
        param.id=employeeId;
        param.staff_head=imageUrl;
        param=JSON.stringify(param);
        hrmUpdateEmployeeImgService.put(param,function(resp){
            if(!resp.error){
                alert("保存成功！");
            }else{
                alert("保存路径失败了!"+resp.msg);
            }
        });

    };
}


