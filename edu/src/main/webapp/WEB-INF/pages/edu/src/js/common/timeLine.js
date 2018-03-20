/**
 * 给定时间刻度控件的定义
 * @param scope 范围
 * @param param 参数
 */
function TimeLine(scope, param,funCallBack) {
	/*
	 * 生成给定月份的下一个月份。
	 * 注意：这里的月份是1~12，不是0~11
	 * @month 给定的月份
	 */
	function nextMonth(month) {
		if (month == 12) {
			return 1;
		} else {
			return month + 1;
		}
	}
	
	/*
	 * 获取给定月份的第一天的日期
	 * @yyyy 给定月份所在年
	 * @MM 给定的月份：0~11
	 */
	function genMonthFirstDate(year, month) {
		var firstDate = new Date();
		firstDate.setFullYear(year);
		firstDate.setMonth(month);
		firstDate.setDate(1);
		firstDate.setHours(0);
		firstDate.setMinutes(0);
		firstDate.setSeconds(0);
		firstDate.setMilliseconds(1);
		return firstDate;
	}
	/*
	 * 计算两个日期间的天数 
	 * @begin:计算起始日
	 * @end：计算截止日期
	 */
	function genDays(begin, end) {
		var length = (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
		if (length < 0) {
			length = length * (-1);
		}
		length = Math.round(length);
		return length;
	}
	/*
	 * 获取两个日期间的月份，同一月份的，不返回月份
	 * @返回结构：[1,2,...,11,12]
	 * @begin:计算起始日期
	 * @end：计算截止日
	 */
	function genMonths(begin, end) {
		// 由当前月推算下个月
		var yyyy = begin.getFullYear();
		var MM = begin.getMonth();
		var endYYYY = end.getFullYear();
		var endMM = end.getMonth();

		var months = [];
		while (yyyy != endYYYY || MM != endMM) {
			months.push({
				name : MM + 1
			});
			if (MM + 1 == 12) {
				MM = 0;
				yyyy = yyyy + 1;
			} else {
				MM += 1;
			}
		}
		months.push({
			name : MM + 1
		});
		return months;
	}

	/*
	 * 获取给定日期对应的月份的天数
	 * @da：获取给定的日期所在月份的天数
	 */
	function genMonthsDays(da) {
		if (!da && !(da instanceof Date)) {
			da = new Date();
		}
		// 由当前月推算下个月
//		var yyyy = da.getFullYear();
//		var MM = da.getMonth();
//		var nextMonth = 0;
//		var nextYYYY = yyyy;
//		if (MM == 11) {
//			nextMonth = 0;
//			nextYYYY = yyyy + 1;
//		} else {
//			nextMonth = MM + 1;
//			nextYYYY = yyyy;
//		}

		// 获取月初
		// var firstDate = genStartDay(da);
		// 获取下一个月的月初
		var lastDate = nextMonthDay(da);

		// 计算当月初距离下个月的月初之间的天数
		return genDays(da, lastDate);
	}
    /*获取月初*/
    function genStartDay(da){
        var day = new Date();
        day.setFullYear(da.getFullYear());
        day.setMonth(da.getMonth());
        day.setDate(1);
        day.setHours(0);
        day.setMinutes(0);
        day.setSeconds(0);
        day.setMilliseconds(0);
        return day;
    }
    /*获取下一个月的同一天*/
    function nextMonthDay(beginDate){
        var endDate = nextDay(beginDate);
        while(endDate.getDate()!=beginDate.getDate()){
            endDate = nextDay(endDate);
        }
        return endDate;
    }

    /*获取下一日*/
    function nextDay(date){
        var dat = new Date();
        dat.setTime(date.getTime()+24*60*60*1000);
        return dat;
    }

	/*
	 * 获取给定的日期，到月初的占比
	 * @dates 给定的日期
	 */
	function genDateLength(dates) {
		var monthLength = genMonthsDays(dates);
		var days = dates.getDate();

		return (1.0 * days) / monthLength;
	}
	/*
	 * 计算给定日期距离初始日期的left值
	 * @date:待计算的日期
	 * @beginDate：起始日期
	 * @monthLength：一个月的刻度
	 */
	function genDateLeft(date, beginDate, monthLength) {
		var months = genMonths(beginDate, date);
		return ((1.0 / 13) * ((months.length - 1) + genDateLength(date) - genDateLength(beginDate)));
	}

	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	// 例子：
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	var Format = function(fmt, date) { // author: meizz
		var o = {
			"M+" : date.getMonth() + 1, // 月份
			"d+" : date.getDate(), // 日
			"h+" : date.getHours(), // 小时
			"m+" : date.getMinutes(), // 分
			"s+" : date.getSeconds(), // 秒
			"q+" : Math.floor((date.getMonth() + 3) / 3), // 季度
			"S" : date.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}

	/* 获取初始值 */
	function genInitialModel() {
		var date = new Date();
		var year = date.getFullYear();
		var before = new Date();
		before.setFullYear(year - 1);
		return {
			beginDate : null,
			endDate : null,
			width : '',
			selectBeginDate : date,
			selectBeginDateString : '',
			selectEndDate : date,
			selectEndDateString : '',
			months : [],
			year : 0,
			width : 1000,
			marginLeft : 0,
			selectBeginDateLeft : 0,
			selectEndDateLeft : 0,
			selfEndDate : date,
			selfBeginDate : before,
			inputSelfBeginDate : Format('yyyy-MM.dd', before),
			inputSelfEndDate : Format('yyyy-MM.dd', date)
		};
	}
	/* 以当前日期为截止日 */
	function genEndDate() {
		var endDate = new Date();
		return endDate;
	}
	/* 将输入字符串转换为日期 */
	function genDateByString(inputString) {
		if (!inputString) {
			return null;
		} else {
			var pattern = /^[1-9][0-9]{3}[-]{1}[0-9]{1,2}[.]{1}[0-9]{1,2}$/;
			if (!inputString.match(pattern)) {
				return null;
			} else {
				var temp = inputString;
				temp = temp.replace("-", "/");
				temp = temp.replace(".", "/");
				return new Date(temp);
			}
		}
	}
	/* 移动指定的天数 */
	function moveDays(number, key) {

		if (key == 'timeSlider1') {
			scope.model.selectBeginDate = moveDate(scope.model.selectBeginDate,
					number);

		} else {
			scope.model.selectEndDate = moveDate(scope.model.selectEndDate,
					number);

		}

		if (Format('yyyy/MM/dd HH:mm:ss', scope.model.selectBeginDate) < Format(
				'yyyy/MM/dd HH:mm:ss', scope.model.selectEndDate)) {

		} else {
			var temp = scope.model.selectEndDate;
			scope.model.selectEndDate = scope.model.selectBeginDate;
			scope.model.selectBeginDate = temp;

		}

		if (scope.model.selectBeginDate > new Date()) {
			scope.model.selectBeginDate = new Date();
			scope.model.selectEndDate = new Date();
		}
		if (scope.model.selectEndDate > new Date()) {
			scope.model.selectEndDate = new Date();
		}
		scope.model.selectBeginDateLeft = genDateLeft(
				scope.model.selectBeginDate, beginDate, lengthMonth);
		scope.model.selectEndDateLeft = genDateLeft(scope.model.selectEndDate,
				beginDate, lengthMonth);
		scope.model.timeSlider1 = scope.model.selectBeginDateLeft;
		scope.model.timeSlider2 = scope.model.selectEndDateLeft;
		scope.model.selectBeginDateString = Format('MM.dd',
				scope.model.selectBeginDate);
		scope.model.selectEndDateString = Format('MM.dd',
				scope.model.selectEndDate);
		scope.model.inputSelectBeginDate = Format('yyyy-MM.dd',
				scope.model.selectBeginDate);
		scope.model.inputSelectEndDate = Format('yyyy-MM.dd',
				scope.model.selectEndDate);
	}
	/* 移动若干天 */
	function moveDate(date, number) {
		var time = date.getTime();
		time += number * 24 * 60 * 60 * 1000;
		var newDate = new Date();
		newDate.setTime(time);
		return newDate;
	}
	/*回调函数*/
	function callBack(){
		try{
			/*回调函数*/
			if(funCallBack instanceof Function){
				funCallBack();
			}
		}catch(e){
			
		}
		
	}

	// 获取初始值
	scope.model = $.extend({}, genInitialModel());

	/* 获取当前时间 */
	var endDate = genEndDate();
	scope.model.endDate = endDate;

	/* 获取当前选择的时间段 */
	scope.model.selectBeginDate = endDate;
	scope.model.selectEndDate = endDate;

	/* 设置初始化参数 */
	if (param) {
		scope.model = $.extend({}, scope.model, param);
		if (scope.model.selectEndDate > new Date()) {
			scope.model.selectEndDate = new Date();
		}
		if (scope.model.selectBeginDate > scope.model.selectEndDate) {
			scope.model.selectBeginDate = scope.model.selectEndDate;
		}
	}

	/* 获取当前的年度 */
	scope.model.year = endDate.getFullYear();

	/* 获取开始时间 */
	var beginDate = new Date();
	beginDate.setFullYear(endDate.getFullYear() - 1);
	scope.model.beginDate = beginDate;

	/* 生成刻度 */
	scope.model.months = genMonths(beginDate, endDate);

	/* 计算月度宽度 */
	var lengthMonth = 100;
	var beginDaysMonthLength = 0;

	/* 计算月初应该要marginLeft的值 */
	if (scope.model.width) {
		lengthMonth = scope.model.width / 13;
		beginDaysMonthLength = lengthMonth * genDateLength(beginDate);
		scope.model.marginLeft = beginDaysMonthLength * -1;
		scope.model.monthLength = lengthMonth;
	}

	// 当前选择的开始时间，距离截止时间的left
	var selectBeginDate = scope.model.selectBeginDate;
	scope.model.selectBeginDateLeft = genDateLeft(selectBeginDate, beginDate,
			lengthMonth);
	scope.model.timeSlider1 = scope.model.selectBeginDateLeft;

	scope.model.selectBeginDateString = Format('MM.dd',
			scope.model.selectBeginDate);
	scope.model.inputSelectBeginDate = Format('yyyy-MM.dd',
			scope.model.selectBeginDate);

	// 计算当前选则的截止时间的left
	var selectEndDate = scope.model.selectEndDate;
	scope.model.selectEndDateLeft = genDateLeft(selectEndDate, beginDate,
			lengthMonth);
	scope.model.timeSlider2 = scope.model.selectEndDateLeft;

	scope.model.selectEndDateString = Format('MM.dd', scope.model.selectEndDate);
	scope.model.inputSelectEndDate = Format('yyyy-MM.dd',
			scope.model.selectEndDate);
	scope.model.dayWidth = scope.model.width * 1.0
			/ genDays(beginDate, endDate);
	
	
	/* 修改开始日期 */
	scope.changeInputSelectBeginDate = function() {
		var inputSelectBeginDate = genDateByString(scope.model.inputSelectBeginDate);
		if (!inputSelectBeginDate) {
			scope.model.inputSelectBeginDate = Format('yyyy-MM.dd',
					scope.model.selectBeginDate);
		} else {
			var temp = Format('yyyy/MM/dd', inputSelectBeginDate);

			var beginDate = Format('yyyy/MM/dd', scope.model.beginDate);
			var endDate = Format('yyyy/MM/dd', scope.model.endDate);

			if (temp < beginDate) {
				scope.model.selectBeginDate = scope.model.beginDate;
			} else if (temp > endDate) {
				scope.model.selectBeginDate = scope.model.endDate;
			} else {
				var date = new Date(temp);
				scope.model.selectBeginDate = date;
				scope.model.inputSelectBeginDate = Format('yyyy-MM.dd',
						scope.model.selectBeginDate);
				if (scope.model.inputSelectEndDate < scope.model.inputSelectBeginDate) {
					scope.model.selectBeginDate = scope.model.selectEndDate;
				}
			}
			TimeLine(scope, {
				selectBeginDate : scope.model.selectBeginDate,
				selectEndDate : scope.model.selectEndDate,
				width:scope.model.width
			},funCallBack);
		}
	}

	/* 修改截止日期 */
	scope.changeInputSelectEndDate = function() {
		var inputSelectEndDate = genDateByString(scope.model.inputSelectEndDate);
		if (!inputSelectEndDate) {
			scope.model.inputSelectEndDate = Format('yyyy-MM.dd',
					scope.model.selectEndDate);
		} else {
			var temp = Format('yyyy/MM/dd', inputSelectEndDate);

			var beginDate = Format('yyyy/MM/dd', scope.model.beginDate);
			var endDate = Format('yyyy/MM/dd', scope.model.endDate);

			if (temp < beginDate) {
				scope.model.selectEndDate = scope.model.beginDate;
			} else if (temp > endDate) {
				scope.model.selectEndDate = scope.model.endDate;
			} else {
				var date = inputSelectEndDate;
				scope.model.selectEndDate = date;
				scope.model.inputSelectEndDate = Format('yyyy-MM.dd',
						scope.model.selectEndDate);
				if (scope.model.inputSelectEndDate < scope.model.inputSelectBeginDate) {
					scope.model.selectEndDate = scope.model.selectBeginDate;
				}

			}
			TimeLine(scope, {
				selectBeginDate : scope.model.selectBeginDate,
				selectEndDate : scope.model.selectEndDate,
				width:scope.model.width
			},funCallBack);
		}
	}

	/* 鼠标按下后开始移动 */
	scope.timeLineBeginSelectDown = function($event, key) {
		scope.model.selectSliperKey = key;
		scope.model.selectSliperPageX = $event.pageX;
		scope.model.selectMoved = 0;
	}

	/* 监听鼠标释放事件 */
	scope.timeLineBeginSelectUp = function($event, key) {
		if(scope.model.selectSliperKey){
			callBack();
		}
		scope.model.selectSliperKey = undefined;
		scope.model.selectSliperPageX = undefined;
	}

	/* 监听移动事件 */
	scope.timeLineBeginSelectMove = function($event, key) {
		if (scope.model.selectSliperPageX) {
			var delta = $event.pageX - scope.model.selectSliperPageX;
			scope.model.selectSliperPageX = $event.pageX;

			var number = Math.round(delta / scope.model.dayWidth);
			if (number) {
				moveDays(number, scope.model.selectSliperKey);
			}

		}
	}
	
	/* 设置为今天 */
	scope.timeLingToday = function() {
		TimeLine(scope, {
			selectBeginDate : new Date(),
			selectEndDate : new Date(),
			width:scope.model.width
		},funCallBack);
	}

	/* 设置为最近一周 */
	scope.timeLingWeek = function() {
		var today = new Date();
		today.setMilliseconds(0);
		var weekBeforeTime = today.getTime() - 24 * 60 * 60 * 1000 * 7;

		var before = new Date();
		before.setMilliseconds(0);
		before.setTime(weekBeforeTime);

		TimeLine(scope, {
			selectBeginDate : before,
			selectEndDate : today,
			width:scope.model.width
		},funCallBack);
	}

	/* 设置为最近一月 */
	scope.timeLingMonth = function() {
		var today = new Date();
		var month = today.getMonth();
		today.setMilliseconds(0);

		var before = new Date();
		if (month == 0) {
			month = 12;
		} else {
			month = month - 1;
		}
		before.setMonth(month);
		before.setMilliseconds(0);

		TimeLine(scope, {
			selectBeginDate : before,
			selectEndDate : today,
			width:scope.model.width
		},funCallBack);
	}

	/* 设置为最近一年 */
	scope.timeLingYear = function() {
		var today = new Date();
		var year = today.getFullYear();
		today.setMilliseconds(0);

		var before = new Date();
		year = year - 1;
		before.setFullYear(year);
		before.setMilliseconds(0);

		TimeLine(scope, {
			selectBeginDate : before,
			selectEndDate : today,
			width:scope.model.width
		},funCallBack);
	}
	/* 一年前 */
	scope.timeLingYearBefore = function() {
		scope.model.type = 'before';
	}

	/* 今年 */
	scope.timeLingYearThis = function() {
		scope.model.type = undefined;
	}

	/* 自定义开始时间的修改 */
	scope.changeInputSelfBeginDate = function() {
		var inputSelfBeginDate = genDateByString(scope.model.inputSelfBeginDate);
		if (!inputSelfBeginDate) {
			scope.model.inputSelfBeginDate = Format('yyyy-MM.dd',
					scope.model.selfBeginDate);
		} else {
			var temp = Format('yyyy/MM/dd', inputSelfBeginDate);
			var endDate = Format('yyyy/MM/dd', scope.model.selfEndDate);

			if (temp > endDate) {
				scope.model.selfEndDate = inputSelfBeginDate;
			}
			scope.model.selfBeginDate = inputSelfBeginDate;
			scope.model.inputSelfBeginDate = Format('yyyy-MM.dd',
					scope.model.selfBeginDate);
			scope.model.inputSelfEndDate = Format('yyyy-MM.dd',
					scope.model.selfEndDate);
		}
		callBack();
	}

	/* 自定义截止时间的修改 */
	scope.changeInputSelfEndDate = function() {
		var inputSelfEndDate = genDateByString(scope.model.inputSelfEndDate);
		if (!inputSelfEndDate) {
			scope.model.inputSelfEndDate = Format('yyyy-MM.dd',
					scope.model.selfEndDate);
		} else {
			var temp = Format('yyyy/MM/dd', inputSelfEndDate);
			var beginDate = Format('yyyy/MM/dd', scope.model.selfBeginDate);

			if (temp < beginDate) {
				scope.model.selfBeginDate = inputSelfEndDate;
			}
			scope.model.selfEndDate = inputSelfEndDate;
			scope.model.inputSelfBeginDate = Format('yyyy-MM.dd',
					scope.model.selfBeginDate);
			scope.model.inputSelfEndDate = Format('yyyy-MM.dd',
					scope.model.selfEndDate);
		}
		callBack();
	}

	/* 获取开始日期 */
	scope.genTimeLineBeginDate = function(pattern) {
		var beginDate = null;
		if (scope.model.type) {
			beginDate = scope.model.selfBeginDate;
		} else {
			beginDate = scope.model.selectBeginDate;
		}
		if (pattern) {
			return Format(pattern, beginDate);
		} else {
			return beginDate;
		}
	}

	/* 获取截止日期 */
	scope.genTimeLineEndDate = function(pattern) {
		var endDate = null;
		if (scope.model.type) {
			endDate = scope.model.selfEndDate;
		} else {
			endDate = scope.model.selectEndDate;
		}
		if (pattern) {
			return Format(pattern, endDate);
		} else {
			return endDate;
		}
	}
	
	/*回调*/
	callBack();

}