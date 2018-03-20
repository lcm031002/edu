/*通用aJax*/
function toolAjax(url, method, obj, callBackMethod, datatype){
	$.ajax({
		type : method,       
		url : url,
		data : obj,
		dataType : datatype,
		success:function(data){
			callBackMethod(data);
		},
		error : function(xhr, msg, e){
			alert(msg);
			alert(e);
		}
	});
}

function toolUploadFile(url, elementId, dataType, callBackMethod){
	 $.ajaxFileUpload ({
         url: url, //你处理上传文件的服务端
         secureuri: false,
         fileElementId: elementId,
         dataType: dataType,
         success: function (data){
        	 callBackMethod(data);
         },
         error: function (html,status,e){
         	alert("e=" + e);
         }
	 });
}

/*校验时间大小*/
function checkStartEndTime(stateTime, endTime){  
    var start=new Date(stateTime.replace("-", "/").replace("-", "/"));  
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
    if(end < start){
        return false;  
    }
    return true;  
}

/*检验时间间隔天数*/
function checkStartEndDateIntervalDays(stateTime, endTime, days){
  var start=new Date(stateTime.replace("-", "/").replace("-", "/")).getTime();
  var end=new Date(endTime.replace("-", "/").replace("-", "/")).getTime();
  if(Math.abs((end - start)) / (1000*60*60*24) > days){
    return false;
  }
  return true;
}

/*百分比*/
function fixPercentToSize(size, percent){
	 return size * percent;    
}

function isNotEmpty(strVal) {
	if (strVal == null || strVal == undefined) {
		return false;
	}
	var str = $.trim(strVal);
	if(str == '')
		return false;
	return true;
}

function isEmpty(strVal) {
	if (strVal == null || strVal == undefined) {
		return true;
	}
	var str = $.trim(strVal);
	if(str == '' || str == 'null')
		return true;
	return false;
}

/*字符串包含判断*/
function contains(string, substr, isIgnoreCase){
    if (isIgnoreCase){
         string = string.toLowerCase();
         substr = substr.toLowerCase();
    }
    var startChar = substr.substring(0, 1);
    var strLen = substr.length;

    for (var j = 0; j<string.length - strLen + 1; j++){
         if (string.charAt(j) == startChar){
             if (string.substring(j, j+strLen) == substr){
                 return true;
             }   
         }
    }
    return false;
}

/** 
 * 替换指定位置的内容 
 * strVal:被替换的字符串, content:要替换的内容  start:开始位置 end:结束位置
 * start如果为空或<0，那么从首位开始，end为空那么到末位结束 
 *
 ***/
function replaceByIndex(strVal, content, start, end){
	var dest = '';
	if(isEmpty(strVal)){
		return strVal;
	}
	var len = strVal.length;
	var st = start;
	var en = end;
	if(isEmpty(start) || start < 0)
		st = 0;
	if(isEmpty(end) || end < 0 || end >= len)
		en = len - 1;
	if(start > end){
		return strVal;
	}
	for(var i=0;i<len;i++){
		var append = strVal[i];
		if(st <= i && i <= end){
			append = content;
		}
		dest += append;
	}
	return dest;
}

/**
 * 列表单条选择检查
 * @param $datagrid
 * @returns {Boolean}
 */
function checkSelectOne($datagrid) {
	var rows = $datagrid.datagrid('getSelections');
	if (!rows || rows.length == 0) {
		$.messager.alert('信息 ', '请选择一条记录', 'info');
		return '';
	}
	if (rows.length > 1) {
		$.messager.alert('信息 ', '只能选择一条记录', 'info');
		return '';
	}
	return rows[0];
}

function displayPassword(pwd){
	var display = '';
	if(pwd == null)
		return display;
	for(var i=0;i<pwd.length;i++){
		display += '*';
	}
	return display;
}

function checkFileType(fileClass, fileValue){
	var pictureClass = '.jpg .jpeg .gif .bmp .png';
	var excelClass = '.excel';
	
	var compareClass = '';
	if(fileClass == 'pictureClass'){
		compareClass = pictureClass;
	}else if(fileClass == 'excelClass'){
		compareClass = excelClass;
	}
	
	if(isEmpty(fileValue))
		return false;
	var fileExt = fileValue.substr(fileValue.lastIndexOf('.')).toLowerCase();
	if(compareClass.indexOf(fileExt) != -1){
		return true;
	}
	return false;
}

function fitlerEmptyReturn(str, back){
	if(isEmpty(str)){
		return back;
	}
	return str;
}
var Validator = {
	    intege: /^-?[1-9]\d*$/, //整数
	    intege1: /^[1-9]\d*$/, //正整数
	    intege2: /^-[1-9]\d*$/, //负整数
	    num: /^([+-]?)\d*\\.?\d+$/, //数字
	    num1: /^[1-9]\d*|0$/, //正数（正整数 + 0）
	    num2: /^-[1-9]\d*|0$/, //负数（负整数 + 0）
	    num3: /^[0-9]+([.]{1}[0-9]+){0,1}$/, //整数+小数
	    positiveNumber:/^(0{1}|[1-9]+[0-9]*)(\\.\d+){0,1}$/,//正整数+小数
	    ascii: /^[\\x00-\\xFF]+$/, //仅ACSII字符
	    chinese: /^[\\u4E00-\\u9FA5]+$/, //仅中文
	    email: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/, //邮件
	    letter: /^[A-Za-z]+$/, //字母
	    letter_l: /^[a-z]+$/, //小写字母
	    letter_u: /^[A-Z]+$/, //大写字母
	    mobile: /^0?(13|14|15|17|18)[0-9]{9}$/, //手机
	    notempty: /^\\S+$/, //非空
	    password: /^.*[A-Za-z0-9\\w_-]+.*$/, //密码
	    fullNumber: /^[0-9]+$/, //数字
	    tel: /^[0-9\-()（）]{7,18}$/, //电话号码的函数(包括验证国内区号,国际区号,分机号)
	    username: /^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$/,//用户名
	    chineseLetter: /^[(\\u4E00-\\u9FA5)|(A-Za-z)]+$/,//中文加字母
	    noNumOnly: /^\\w*[(\\u4E00-\\u9FA5)|(A-Za-z)]+[\\w|(\\u4E00-\\u9FA5)]*$/,//不能纯数字
	    phone: /^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$/,
	    zipcode: /^[0-9]{6}$/, //邮编
	    noNumberFirst: /^[(\\u4E00-\\u9FA5)|(A-Za-z)]+[(\\u4E00-\\u9FA5)|(A-Za-z)|(0-9)]*$/,//允许中文 字母 数字 不能以数字开头
	    businessCode: /^\\d{15}$/,
	    cardNo: /(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/,
	    account: /[a-zA-Z0-9_]{6,20}/, // 账号  6-20长度非中文
	    id_id_15: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/, //15位身份证
	    id_id_18: /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/, //18位身份证
	    positiveNumber2: /^(\d)*(\.(\d){0,2})?$/, // 0、正数、2位小数
	    natural: /^(0|([1-9]\d*))$/, // 0、正整数	
	    qq: /[1-9]\d{4,}/
	};
/* 将输入字符串转换为数字 */
function genFloatByString(inputString) {
    if (!inputString) {
        return null;
    } else {
        var reg = new RegExp("^(-?\\d+)(\\.\\d+)?$");
        if (!reg.test(inputString)) {
            return null;
        }
        var temp = inputString;
        try {
            temp = parseFloat(inputString);
            if (temp == NaN) {
                temp = 0;
            }
        } catch (e) {
            temp = 0;
        }

        return temp;
    }
}
function Format(fmt, date) { // author: meizz
    var o = {
        "M+" : date.getMonth() + 1, // 月份
        "d+" : date.getDate(), // 日
        "h+" : date.getHours(), // 小时
        "m+" : date.getMinutes(), // 分
        "s+" : date.getSeconds(), // 秒
        "q+" : Math
            .floor((date.getMonth() + 3) / 3), // 季度
        "S" : date.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date
            .getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt
                .replace(
                RegExp.$1,
                (RegExp.$1.length == 1) ? (o[k])
                    : (("00" + o[k])
                    .substr(("" + o[k]).length)));
    return fmt;
}

/* 将输入字符串转换为数字 */
function genNumByString(inputString) {
    if (!inputString) {
        return null;
    } else {
        var reg = new RegExp("^[0-9]*$");
        if (!reg.test(inputString)) {
            return null;
        }
        var temp = inputString;
        try {
            temp = parseInt(inputString);
            if (temp == NaN) {
                temp = 0;
            }
        } catch (e) {
            temp = 0;
        }

        return temp;
    }
}

function getCurrentDate() {
	var now = new Date();
	return now.format("yyyy-MM-dd", now);
}

function getLatestWeek() {
	var now = new Date();
	return now.format("yyyy-MM-dd", now.setDate(now.getDate() - 7));
}

function getLastDayOfMonth(year, month) {
	var date = new Date(year, month, 0);
	return date.getDate();
}

function getLatestMonth() {
	var now = new Date();
	var month = now.getMonth();
	var date = now.getDate();
	if (date >= getLastDayOfMonth(now.getFullYear(), month)) {
		now.setDate(1);
	} else {
		now.setMonth(month - 1);
		now.setDate(date + 1);
	}
	
	return now.format("yyyy-MM-dd", now);
}

function getLatestYear() {
	var now = new Date();
	now.setFullYear(now.getFullYear() - 1);
	now.setDate(now.getDate() + 1);
	return now.format("yyyy-MM-dd", now);
}

function getNextTwoDays() {
	var now = new Date();
	return now.format("yyyy-MM-dd", now.setDate(now.getDate() + 1));
}

function getNextWeek() {
	var now = new Date();
	return now.format("yyyy-MM-dd", now.setDate(now.getDate() + 7));
}

function getNextMonth() {
	var now = new Date();
	var month = now.getMonth();
	var date = now.getDate();
	if (date >= getLastDayOfMonth(now.getFullYear(), month)) {
		now.setDate(1);
	} else {
		now.setMonth(month + 1);
		now.setDate(date + 1);
	}
	
	return now.format("yyyy-MM-dd", now);
}

function addYear(year) {
	var now = new Date();
	now.setFullYear(now.getFullYear() + year);
	if (year < 0) {
		now.setDate(now.getDate() + 1);
	} else {
		now.setDate(now.getDate() - 1);
	}
	return now.format("yyyy-MM-dd", now);
}

