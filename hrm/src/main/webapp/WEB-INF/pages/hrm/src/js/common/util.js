
/*拼接ORM_MP*/
var convertOrgMpURL = function(auth, URL, nosuffix){
	var resURL = contextPath + '/org_mp' + URL;
	if(auth){
		resURL = contextPath +'/' + auth.orgMp.org_mp + URL;
	}
	//无须时间参数
	if(true == nosuffix){
		return resURL;
	}
	//解决微信浏览器缓存问题
	if(URL.indexOf("?") > 0){
		resURL += ('&_=' + (new Date().getTime())); 
	}else
		resURL += ('?_=' + (new Date().getTime())); 
	return resURL;
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
function isNotEmpty(strVal) {
	return !isEmpty(strVal);
}

function toolAjax(url, method, obj, callBackMethod, datatype,errorCallBackMethod){
	$.ajax({
		type : method,       
		url : url,
		data : obj,
		dataType : datatype,
		success:function(data){
			callBackMethod(data);
		},
		error : function(xhr, msg, e){
			errorCallBackMethod(msg);
		}
	});
}

var PageObj = function(){
	var url = null;				//地址
	this.param = null;
	this.rows = null; 			//每页行数
	var totalIndex = null;   	//显示页码数 (偶数)
	this.page = null; 			//当前页
	var totalPage = null; 		//总页数
	var dataListDOM = null;		//数据DOM
	var parseMethod = null;  	//处理数据
	
	this.init = function(_url, _param, _parseMethod, _rows, _totalIndex, _page, _dataListDOM, _beginDOM, _upDOM, _downDOM, _endDOM){
		url = _url;
		this.param = _param;
		parseMethod = _parseMethod;
		if(_rows == null || _rows == undefined || _rows.length == 0)
			_rows = 8;//默认为每页8行数据
		if(_totalIndex == null || _totalIndex == undefined || _totalIndex.length == 0)
			_totalIndex = 10;//默认显示10个页码
		if(_page == null || _page == undefined || _page.length == 0)
			_page = 1;//默认当前页为第一页
		this.rows = _rows;
		totalIndex = _totalIndex;
		this.page = _page;
		dataListDOM = _dataListDOM;
		this.initAction(_beginDOM, _upDOM, _downDOM, _endDOM);
		this.initPage();
	};
	
	var pageCallBack = function(data){
		var appendList = "";
		var appendIndex = "";
		//总页数
		totalPage = data.totalPage;
		
		appendList = parseMethod(data);
		
		for(var i=1;i<totalIndex+1;i++){
			//总页数和显示页码数 比较
			var differ = totalPage - totalIndex;
			//当前 前(totalIndex/2)页
			var front = this.page - (totalIndex / 2);
			//当前 后(totalIndex/2)页
			var behind = this.page + (totalIndex / 2);
			var j = i;
			if(differ > 0 && front > 0){
				j +=  front;
			}
			if(j > totalPage)
				break;
			appendIndex += '<li onclick="this.selectPage(' + j + ')" class="pageIndex"><span>'+ j + '</span></li>';
		}
		
		//展示数据
		dataListDOM.html(appendList);
		//清除原页码 新增新页码
		$('.pageIndex').remove();
//		$('#up').after(appendIndex);
		//展示记录数
		/*$('#floor').html('第【'  + this.page + '】页 共【' + data.totalPage + '】页  总计【' + data.total + '】条记录');*/
	};
	
	this.initPage = function(){
		this.param.page = this.page;
		this.param.rows = this.rows;
		toolAjax(url, 'post', this.param, pageCallBack, 'json')
	};
	
	this.initAction = function(beginDOM, upDOM, downDOM, endDOM){
		try {
			beginDOM.bind('click', this.begin);
			upDOM.bind('click', this.up);
			downDOM.bind('click', this.down);
			endDOM.bind('click', this.end);
		} catch (e) {
		}
	};
	
	this.begin = function(){
		//首页
		this.page = 1;
		this.initPage();
	};
	this.up = function(){
		//上一页
		if(this.page == 1){
			return;
		}
		this.page = this.page - 1;
		this.initPage();
	};
	this.down = function(){
		//下一页
		if(this.page == totalPage){
			return;
		}
		this.page = this.page + 1;
		this.initPage();
	};
	this.end = function(){
		//末页
		this.page = totalPage;
		this.initPage();
	};
	this.selectPage = function(_page){
		//当前选中页
		this.page = _page;
		this.initPage();
	};
	
};