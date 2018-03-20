var LODOP = null;
/**
 * 打印一段html
 * @param strHtml
 */
function KlxxPrint(strHtml) {
	LODOP = getLodop();
	if (LODOP.PRINT_INIT && LODOP.ADD_PRINT_HTM && LODOP.PREVIEW) {
		LODOP.PRINT_INIT();
		LODOP.ADD_PRINT_HTM(0, 0, "210mm", 680, strHtml);
		LODOP.PREVIEW();
	}
}
//单选打印
var ydyAttendanceQrcode = function(strHtml,infos) {
    LODOP = getLodop();
    if (LODOP.PRINT_INIT && LODOP.ADD_PRINT_HTM && LODOP.PREVIEW) {
        LODOP.PRINT_INIT();
        LODOP.ADD_PRINT_HTM(0, 0, "210mm", 680, strHtml);
        LODOP.ADD_PRINT_BARCODE(30,630,120,120,"QRCode",infos[0].encoding);
        LODOP.PREVIEW();
    }

}
//批量打印
var ydyAttendanceMoreQrcode = function(array,infos) {
    LODOP = getLodop();
    if (LODOP.PRINT_INIT && LODOP.ADD_PRINT_HTM && LODOP.PREVIEW) {
        LODOP.PRINT_INIT();
        for (var i=0;i<infos.length;i++){
            LODOP.ADD_PRINT_HTM(0, 0, "210mm", 680, array[i]);
            LODOP.ADD_PRINT_BARCODE(30,630,120,120,"QRCode",infos[i].encoding);
            LODOP.NEWPAGE();//强制分页
        }
        LODOP.PREVIEW();
    }
    }



var	PrintUtil = {
		LODOP: null,

		printPage01: function(data){
			LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
			//01报班
				var strHtml = '<style type="text/css">';
				strHtml += 'table { width:660px; font:12px Arial; color: #000;font-weight: 400; }';
				strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
				strHtml += 'tr td{ height:15px; line-height:15px;}';
				 strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
				strHtml += '</style>';
				strHtml += '<table width="660px" style="margin:180px 0 0 90px;" height="600px" border="0" cellspacing="0" cellpadding="0">';
				strHtml += '<tr height="300px"><td ></td></tr>';
				strHtml += '<tr height="330px"><td>';

				strHtml += '<table border="0" width="660px" height="48" align="center">';
				strHtml += '<tr align="left" style="font-weight:900;">';
				strHtml += '<td colspan="5">业务校区:' + data.orderInfo.ORG_NAME + '	经办人:' + data.orderInfo.EMPLOYEE_NAME + '</td>';
				strHtml += '</tr>';
				strHtml += '<tr align="center" style="font-weight:900;">';
				strHtml += '<td width="150">学员编号</td>';
				strHtml += '<td width="66">学员姓名</td>';
				strHtml += '<td width="185">单据编号</td>';
				strHtml += '<td width="68">业务类型</td>';
//				strHtml += '<td width="135">业务校区</td>';
//				strHtml += '<td width="54">经办人</td>';
				strHtml += '<td width="76">业务日期</td>';
				strHtml += '</tr>';
				strHtml += '<tr align="center">';
				strHtml += '<td>'+data.orderInfo.ENCODING+'</td>';
				strHtml += '<td>'+data.orderInfo.STUDENT_NAME+'</td>';
				strHtml += '<td>'+data.orderInfo.ORDER_CODE+'</td>';
				strHtml += '<td>报班</td>';
//				strHtml += '<td>'+data.orderInfo.ORG_NAME+'</td>';
//				strHtml += '<td>'+data.orderInfo.EMPLOYEE_NAME+'</td>';
				strHtml += '<td>'+data.orderInfo.CREATE_TIME+'</td>';
				strHtml += '</tr>';
				strHtml += '</table>';

				strHtml += '<table border="0" width="660px" height="48">';
				strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
				strHtml += '<td>编号</td>';
				strHtml += '<td>课程商品名称</td>';
//				strHtml += '<td>课程说明</td>';
				strHtml += '<td>上课时间</td>';
				strHtml += '<td>下课时间</td>';
				strHtml += '<td>报班课时</td>';
				strHtml += '<td>报班总金额</td>';
				strHtml += '</tr>';
				for(var i = 0 ; i < data.orderDetailList.length ; i++){
					strHtml += '<tr align="center">';
					strHtml += '<td>'+(i+1)+'</td>';
					strHtml += '<td>'+data.orderDetailList[i].COURSE_NAME+'</td>';
//					strHtml += '<td>'+data.orderDetailList[i].REMARK+'</td>';
					strHtml += '<td>'+data.orderDetailList[i].START_TIME+'</td>';
					strHtml += '<td>'+data.orderDetailList[i].END_TIME+'</td>';
					strHtml += '<td>'+data.orderDetailList[i].COURSE_TOTAL_COUNT+'</td>';
					strHtml += '<td>￥'+data.orderDetailList[i].SUM_PRICE+'</td>';
					strHtml += '</tr>';
				}

				strHtml += '</table>';

				strHtml += '<table border="0" width="660px" height="24">';
				strHtml += '<tr height="15px">';
				strHtml += '<td style="font-weight:900;">优惠金额：'+(data.orderInfo.SUM_PRICE-data.orderInfo.ACTUAL_PRICE)+'</td>';
				strHtml += '<td style="font-weight:900;">实际需缴费金额：￥'+data.orderInfo.ACTUAL_PRICE+'</td>';
				strHtml += '<td style="font-weight:900;">已缴费金额：￥'+data.orderInfo.ACTUAL_PRICE+'</td>';
				strHtml += '</tr>';
				strHtml += '</table>';

				strHtml += '<table border="0" width="660px" height="24">';
				for(var i = 0 ; i < data.payDetailList.length ; i++){
					strHtml += '<tr height="15px">';
					strHtml += '<td style="font-weight:900;">缴费明细：</td>';
					strHtml += '<td>'+ convertPayName(data.payDetailList[i].PAYMENT_WAY) + '</td>';
					//strHtml += '<td>'+ convertPayName(data.payDetailList[i].PAY_NAME) + '</td>';
					strHtml += '<td>￥'+data.payDetailList[i].STAFFAPPPREM+'</td>';
					strHtml += '<td>'+data.payDetailList[i].ORG_NAME+'</td>';
//					strHtml += '<td>'+ fitlerEmpty(data.payDetailList[i].CLIENT_CARD_NO) + '</td>';
					strHtml += '<td>'+data.payDetailList[i].CREATE_TIME+'</td>';
					strHtml += '</tr>';
				}
				for(var i = 0 ; i < 10-data.payDetailList.length-data.orderDetailList.length ; i++){
					strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
				}
				strHtml += '</table></td></tr>';
				strHtml += '</table>';

				LODOP.ADD_PRINT_HTM(0,0,"210mm",680,strHtml);
				// LODOP.PRINT();
				LODOP.PREVIEW();
		},
		printPage03: function(data){
			LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));

			//03充值
				var strHtml = '<style type="text/css">';
				strHtml += 'table { width:660px; font:12px Arial; color: #000;font-weight: 400; }';
				strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
				strHtml += 'tr td{ height:24px; line-height:24px;}';
				 strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
				strHtml += '</style>';
				strHtml += '<table width="660px" style="margin:80px 0 0 90px;" height="600px" border="0" cellspacing="0" cellpadding="0">';
				strHtml += '<tr height="300px"><td ></td></tr>';
				strHtml += '<tr height="330px"><td>';

				strHtml += '<table border="0" width="660px" height="48" align="center">';
				strHtml += '<tr align="left" style="font-weight:900;">';
				strHtml += '<td colspan="5">业务校区:' + data.rechargeInfo.ORG_NAME + '	经办人:' + data.rechargeInfo.EMPLOYEE_NAME + '</td>';
				strHtml += '</tr>';
				strHtml += '<tr align="center" style="font-weight:900;">';
				strHtml += '<td width="150">学员编号</td>';
				strHtml += '<td width="66">学员姓名</td>';
				strHtml += '<td width="185">单据编号</td>';
				strHtml += '<td width="68">业务类型</td>';
//				strHtml += '<td width="135">业务校区</td>';
//				strHtml += '<td width="54">经办人</td>';
				strHtml += '<td width="76">业务日期</td>';
				strHtml += '</tr>';
				strHtml += '<tr align="center">';
				strHtml += '<td>'+data.rechargeInfo.S_ENCODING+'</td>';
				strHtml += '<td>'+data.rechargeInfo.STUDENT_NAME+'</td>';
				strHtml += '<td>'+data.rechargeInfo.R_ENCODING+'</td>';
				strHtml += '<td>充值</td>';
//				strHtml += '<td>'+data.rechargeInfo.ORG_NAME+'</td>';
//				strHtml += '<td>'+data.rechargeInfo.EMPLOYEE_NAME+'</td>';
				strHtml += '<td>'+data.rechargeInfo.INPUT_TIME+'</td>';
				strHtml += '</tr>';
				strHtml += '<tr align="left">';
				strHtml += '<td colspan="7">充值信息：</td>';
				strHtml += '</tr>';
				strHtml += '</table>';

				strHtml += '<table border="0" width="660px" height="48" style="margin-top:15">';
				strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
				strHtml += '<td>编号</td>';
				strHtml += '<td>操作账户</td>';
				strHtml += '<td>方式</td>';
				strHtml += '<td>操作金额</td>';
				strHtml += '<td>备注</td>';
				strHtml += '</tr>';
				strHtml += '<tr align="center">';
				strHtml += '<td>1</td>';
				strHtml += '<td>'+data.rechargeInfo.NAME+'</td>';
				strHtml += '<td>'+convertPayName(data.rechargeInfo.PAY_NAME)+'</td>';
				strHtml += '<td>￥'+data.rechargeInfo.MONEY+'</td>';
				strHtml += '<td>'+ fitlerEmptyReturn(data.rechargeInfo.REMARK, '')+'</td>';
				strHtml += '</tr>';
				strHtml += '</table></td></tr>';

				strHtml += '</table>';

				LODOP.ADD_PRINT_HTM(0,0,"210mm",680,strHtml);
				// LODOP.PRINT();
				LODOP.PREVIEW();
		}
};


/*打印付款方式转换 1指代”现金“，2指代”转账“，3指代“刷卡”，4指代“储值账户”*/ /*外账打印*/
function convertPayName(pay_name){
	var new_pay = pay_name;
	if('现金' == pay_name){
		new_pay = 1;
	}else if('银行转账' == pay_name){
		new_pay = 2;
	}else if('刷卡' == pay_name){
		new_pay = 3;
	}else if('储值账户' == pay_name){
		new_pay = 4;
	}
	return new_pay;
}
/*打印付款方式转换 1指代”现金“，2指代”转账“，3指代“刷卡”，4指代“储值账户”*/
function convertPayId(pay_id){
	var new_pay = null;
	if(1 == pay_id){
		new_pay = '现金';
	}else if(2 == pay_id){
		new_pay = '银行转账';
	}else if(3 == pay_id){
		new_pay = '刷卡';
	}else if(4 == pay_id){
		new_pay = '储值账户';
	}
	return new_pay;
}

function fitlerEmpty(str){
	if(isEmpty(str)){
		return '无';
	}
	return str;
}