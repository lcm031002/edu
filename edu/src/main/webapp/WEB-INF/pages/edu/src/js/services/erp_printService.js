angular.module('ework-ui').factory('erp_printService', [ 'erp_studentPrintService', erp_printService ]);

function erp_printService(erp_studentPrintService) {
	var services = {};
	services.printAccountDynamic = function(printParam,  hidePanelId) {
		if (hidePanelId) {
        	$("#" + hidePanelId).modal('hide');
        }
		
		erp_studentPrintService.query(printParam, function(resp){
            if(!resp.error) {
            	if (printParam.printType && printParam.printType == '05') {
                    var useragent = navigator.userAgent;
                    if(useragent.indexOf('Android') > -1 || useragent.indexOf('Adr') > -1){
                    	if(location.href.indexOf('klxuexi.org')>0) {
                            window.open("/printhtml/print_transfer.html?" + printParam.dynamicId + "&" + printParam.printType);
                        }else{
                            window.open("/klxxedu/printhtml/print_transfer.html?" + printParam.dynamicId + "&" + printParam.printType);
						}
					}
                    else {
            			createPrintPage05(resp.data);} //转账打印
            	}
            	if (printParam.printType && printParam.printType == '04') {
            		CreatePrintPage03(resp.data);  //取款打印
            	}
            	if (printParam.printType && printParam.printType == '01') {//报班打印
					if(resp.data.business_type == 2) {//1对1
						CreatePrintPageGxhYdyBb(resp.data);
					} else if(resp.data.business_type == 1) {//班级课
						if (resp.data.city_id == 3) {
							CreatePrintPageXiamen(resp.data);
						} else {
							CreatePrintPage(resp.data);
						}
					} else {
						CreatePrintPage(resp.data);
					}
            	}
            	if (printParam.printType && printParam.printType == '03') {//充值打印
					if(resp.data.rechargeInfo.CITY_ID == 3){
						CreatePrintPage04Xiamen([resp.data]);
					}else{
						CreatePrintPage04([resp.data]);
					}
            	}
            	if (printParam.printType && printParam.printType == '02') {//退费打印
						CreatePrintPageForRefund(resp.data);
            	}
            } else {
                alert(resp.message);
            }
        });
	}

	function CreatePrintPage03(data) {
	    //03取款
		var strHtml = '<style type="text/css">';
		strHtml += 'table { width:660px; font:12px Arial; color: #000;font-weight: 400; }';
		strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
		strHtml += 'tr td{ height:24px; line-height:24px;}'; 
		 strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}'; 
		strHtml += '</style>';
		strHtml += '<body style="margin:0 auto ;padding-top:350px;text-align:center">';
		strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
		strHtml += '<tr><td>';

		strHtml += '<table border="0" width="660px" align="center">';
		strHtml += '<tr align="left" style="font-weight:900; >';
		strHtml += '<td colspan="5">业务校区:' + data.withdrawing.BUNAME + '	经办人:' + data.withdrawing.USERNAME + '</td>';
		strHtml += '</tr>'; 
		strHtml += '<tr align="center" style="font-weight:900; >';
		strHtml += '<td width="150">学员编号</td>'; 
		strHtml += '<td width="66">学员姓名</td>'; 
		strHtml += '<td width="185">单据编号</td>'; 
		strHtml += '<td width="68">业务类型</td>'; 
//		strHtml += '<td width="135">业务校区</td>'; 
//		strHtml += '<td width="54">经办人</td>'; 
		strHtml += '<td width="76">业务日期</td>'; 
		strHtml += '</tr>'; 
		strHtml += '<tr align="center" >';
		strHtml += '<td>'+data.withdrawing.STUENCODING+'</td>';
		strHtml += '<td>'+data.withdrawing.STUDENTNAME+'</td>';
		strHtml += '<td>'+data.withdrawing.ENCODING+'</td>';
		strHtml += '<td>取款</td>'; 
//		strHtml += '<td>'+data.withdrawing.BUNAME+'</td>'; 
//		strHtml += '<td>'+data.withdrawing.USERNAME+'</td>'; 
		strHtml += '<td>'+data.withdrawing.INPUTTIME+'</td>';
		strHtml += '</tr>'; 
		strHtml += '<tr align="left">';
		strHtml += '<td colspan="7">取款信息：</td>';  
		strHtml += '</tr>';
		strHtml += '</table>'; 

		strHtml += '<table border="0" width="660px" style="margin-top:15px">';
		strHtml += '<tr height="15px" align="center" style="font-weight:900; >';
		strHtml += '<td>编号</td>'; 
		strHtml += '<td>操作账户</td>'; 
		strHtml += '<td>操作金额</td>'; 
		strHtml += '<td>手续费</td>'; 
		strHtml += '<td>取款金额</td>'; 
		strHtml += '</tr>'; 
		strHtml += '<tr align="center" ">';
		strHtml += '<td>1</td>'; 
		strHtml += '<td>'+data.withdrawing.PRODUCTLINE+'</td>';
		strHtml += '<td>￥'+data.withdrawing.PREAMOUNT+'</td>';
		strHtml += '<td>￥'+data.withdrawing.MONEY_FEE+'</td>';
		strHtml += '<td>￥'+data.withdrawing.MONEY+'</td>';
		strHtml += '</tr>';
		strHtml += '</table></td></tr>'; 

		strHtml += '</table>'; 
		strHtml += '</body>';
		KlxxPrint(strHtml)
	}
	
    
	function createPrintPage05 (data) {
   	//05转账
		var strHtml = '<style type="text/css">'; 
		strHtml += 'table { width:660px; font:12px Arial; color: #000;font-weight: 400; }'; 
		strHtml += 'tr.Line td{ border-top:1px dashed #000;}'; 
		strHtml += 'tr td{ height:24px; line-height:24px;}'; 
		 strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}'; 
		strHtml += '</style>';
		strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
		strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
		strHtml += '<tr><td>';

		strHtml += '<table border="0" width="660px" align="center">';
		strHtml += '<tr align="left" style="font-weight:900;">';
		strHtml += '<td colspan="5">业务校区:' + data.transferInfo.ORG_NAME + '	经办人:' + data.transferInfo.EMPLOYEE_NAME + '</td>'; 
		strHtml += '</tr>'; 
		strHtml += '<tr align="center" style="font-weight:900;">'; 
		strHtml += '<td width="150">学员编号</td>'; 
		strHtml += '<td width="66">学员姓名</td>'; 
		strHtml += '<td width="185">单据编号</td>'; 
		strHtml += '<td width="68">业务类型</td>';
		strHtml += '<td width="76">业务日期</td>'; 
		strHtml += '</tr>'; 
		strHtml += '<tr align="center">'; 
		strHtml += '<td>'+data.transferInfo.S_ENCODING+'</td>'; 
		strHtml += '<td>'+data.transferInfo.STUDENT_NAME+'</td>'; 
		strHtml += '<td>'+data.transferInfo.D_ENCODING+'</td>'; 
		strHtml += '<td>转账</td>';
		strHtml += '<td>'+data.transferInfo.INPUT_TIME+'</td>'; 
		strHtml += '</tr>'; 
		strHtml += '<tr align="left">'; 
		strHtml += '<td colspan="7">转账信息：</td>';  
		strHtml += '</tr>';
		strHtml += '</table>'; 

		strHtml += '<table border="0" width="660px" style="margin-top:15px">';
		strHtml += '<tr height="15px" align="center" style="font-weight:900;">'; 
		strHtml += '<td>编号</td>';
		strHtml += '<td>转出学员</td>'; 
		strHtml += '<td>转出账户</td>'; 
		strHtml += '<td>转入学员</td>'; 
		strHtml += '<td>转入账户</td>'; 
		strHtml += '<td>操作金额</td>'; 
		strHtml += '</tr>'; 
		strHtml += '<tr align="center">'; 
		strHtml += '<td>1</td>'; 
		strHtml += '<td>'+data.transferInfo.STUDENT_NAME+'</td>'; 
		strHtml += '<td>'+data.transferInfo.OUTPUT_PRODUCT_LINE+'</td>';
		strHtml += '<td>'+data.transferInfo.INPUT_STU_NAME+'</td>'; 
		strHtml += '<td>'+data.transferInfo.INPUT_PRODUCT_LINE+'</td>';
		strHtml += '<td>￥'+data.transferInfo.MONEY+'</td>'; 
		strHtml += '</tr>';
		strHtml += '</table></td></tr>'; 
		strHtml += '</table>'; 
		strHtml += '</body>';
		KlxxPrint(strHtml)
    }

	return services;
}
