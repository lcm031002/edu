/**
 * Created by Liyong.zhu on 2016/11/9.
 */
'use strict';

angular.module('ework-ui').factory('FileUploadService', [ '$resource', FileUploadService ]);

angular.module('ework-ui').factory('FileUploadDataService', [ '$resource', FileUploadDataService ]);
/**
 * 文件上传服务
 * @param $resource
 * @returns {*}
 * @constructor
 */
function FileUploadService($resource){
    return $resource('data/common/fileUpload.json', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}

FileUploadService.uploadService = "excels/inputview/input";

/**
 * 文件上传数据服务
 * @returns {Object}
 * @constructor
 */
function FileUploadDataService($resource){
    return $resource('data/common/fileUploadDatas.json', {}, {
        query:{
            method : 'GET',
            params : {},
            isArray : false
        },
        add : {
        	url:'excels/inputview/add',
            method : 'POST',
            params : {},
            isArray : false
        },
        update:{
            method : 'PUT',
            params : {},
            isArray : false
        },
        delete:{
            method : 'DELETE',
            params : {},
            isArray : false
        }
    });
}