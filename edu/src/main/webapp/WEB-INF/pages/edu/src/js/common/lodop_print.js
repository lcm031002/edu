/**
 * Created by Liyong.zhu on 2016/10/19.
 */
function CreatePrintPage(data) {

    //01报班
    var strHtml = '<style type="text/css">';
    strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; }';
    strHtml += 'tr.Line td{ border-top:1px;}';
    strHtml += 'tr td{ height:15px; line-height:15px;}';
    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    strHtml += '</style>';
    strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
    strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr><td>';

    strHtml += '<body style="margin:0 auto;text-align:center">';
    strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + data.branch_name + '	经办人:' + data.employee_name + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center" style="font-weight:900;">';
    strHtml += '<td width="150">学员编号</td>';
    strHtml += '<td width="66">学员姓名</td>';
    strHtml += '<td width="185">单据编号</td>';
    strHtml += '<td width="68">业务类型</td>';
    strHtml += '<td width="76">业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>'+data.student_encoding+'</td>';
    strHtml += '<td>'+data.student_name+'</td>';
    strHtml += '<td>'+data.encoding+'</td>';
    strHtml += '<td>报班</td>';
    strHtml += '<td>'+Format("yyyy-MM-dd", new Date(data.create_time))+'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="660px">';
    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    strHtml += '<td>课程商品名称</td>';
    strHtml += '<td>上课时间</td>';
    strHtml += '<td>下课时间</td>';
    strHtml += '<td>报班课时</td>';
    strHtml += '<td>报班总金额</td>';
    strHtml += '</tr>';
    for(var i = 0 ; i < data.details.length ; i++){
        var startTime = data.details[i].start_time;
        var endTime = data.details[i].end_time;
        if(startTime==null){
            startTime='';
        }
        if(endTime==null){
            endTime='';
        }
        strHtml += '<tr align="center">';
        strHtml += '<td>'+(i+1)+'</td>';
        strHtml += '<td>'+data.details[i].course_name+'</td>';
        strHtml += '<td>'+startTime+'</td>';
        strHtml += '<td>'+endTime+'</td>';
        strHtml += '<td>'+data.details[i].course_total_count+'</td>';
        strHtml += '<td>￥'+data.details[i].former_sum_price+'</td>';
        strHtml += '</tr>';
    }

    strHtml += '</table>';
    strHtml += '<table border="0" width="660px">';
    strHtml += '<tr height="15px">';
    strHtml += '<td style="font-weight:900;">优惠金额：'+(data.sum_price-data.actual_price)+'</td>';
    strHtml += '<td style="font-weight:900;">实际需缴费金额：￥'+data.actual_price+'</td>';
    strHtml += '<td style="font-weight:900;">已缴费金额：￥'+data.actual_price+'</td>';
    strHtml += '<td style="font-weight:900;">APP账号：'+  (data.studentInfo.login_no ? data.studentInfo.login_no : "") +'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="660px">';
    for(var i = 0 ; i < data.payment.details.length ; i++){
        strHtml += '<tr height="15px">';
        strHtml += '<td style="font-weight:900;">缴费明细：</td>';
        strHtml += '<td>'+ convertPayName(data.payment.details[i].payment_way) + '</td>';
        strHtml += '<td>￥'+(data.payment.details[i].staffappprem?data.payment.details[i].staffappprem:0)+'</td>';
        strHtml += '<td>'+(data.payment.details[i].org_name?data.payment.details[i].org_name:"")+'</td>';
        if(data.payment.details[i].device_code){
            strHtml += '<td>'+data.payment.details[i].device_code+'</td>';
        }
        strHtml += '<td>'+(data.payment.details[i].createTime?data.payment.details[i].createTime:"")+'</td>';
        strHtml += '</tr>';
    }
    if(data.bu_id==12){
        strHtml += '<tr height="15px"  style="font-weight:900;"><td colspan="6">以上优惠均需报整期课程并消耗完毕，若产生退费/冻结，则优惠取消</td></tr>';
    }else{
        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    }
    for(var i = 0 ; i < 9-data.payment.details.length-data.payment.details.length ; i++){
        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    }
    strHtml += '</table></td></tr>';
    strHtml += '</table>';
    strHtml += '</body>';

    KlxxPrint(strHtml);

}

function CreatePrintPageXiamen(data) {
    //01报班

    var strHtml = '<style type="text/css">';
    strHtml += 'table { width:640px; font:12px Arial;font-weight: 400; }';
    strHtml += 'tr.Line td{ border-top:1px;}';
    strHtml += 'tr td{ height:15px; line-height:15px;}';
    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    strHtml += '</style>';
    strHtml += '<body style="margin:0 auto ;padding-top:350px;text-align:center">';
    strHtml += '<table width="640px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr><td>';

    strHtml += '<table border="0" width="640px" align="center">';
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + data.branch_name.replace('个性化','').replace('培英精品班','').replace('大小班','') + '	                 日期:' + Format("yyyy-MM-dd", new Date(data.create_time)) + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr height="15px" style="font-weight:900;">';
    strHtml += '<td align="center">学员姓名</td>';
    strHtml += '<td width="33%" align="left">业务类型</td>';
    strHtml += '<td width="33%" align="left">APP账号</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml += '<td align="center"  >'+data.student_name+'</td>';
    strHtml += '<td width="33%"  align="left">报班</td>';
    strHtml += '<td width="33%"  align="left">' + (data.studentInfo.login_no ? data.studentInfo.login_no : "") + '</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="640px" align="center">';
    strHtml += '<tr align="left" height="15px" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    strHtml += '<td>报班总金额</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml += '<td>'+1+'</td>';
    strHtml += '<td>￥'+data.actual_price+'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="640px">';
    strHtml += '<tr height="15px">';
    strHtml += '<td style="font-weight:900;">优惠金额：'+0+'</td>';
    strHtml += '<td style="font-weight:900;">实际需缴费金额：￥'+data.actual_price+'</td>';
    strHtml += '<td style="font-weight:900;">已缴费金额：￥'+data.actual_price+'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="640px">';
    strHtml += '<tr height="15px">';
    strHtml += '<td style="font-weight:900;">缴费明细：</td>';
    strHtml += '<td>1</td>';
    strHtml += '<td>￥'+data.actual_price+'</td>';
    strHtml += '<td>'+data.branch_name.replace('个性化','').replace('培英精品班','').replace('大小班','')+'</td>';
    strHtml += '<td>'+ Format("yyyy-MM-dd", new Date(data.create_time))+'</td>';
    strHtml += '</tr>';
    if(data.bu_id==12){
        strHtml += '<tr height="15px"  style="font-weight:900;"><td colspan="6">以上优惠均需报整期课程并消耗完毕，若产生退费/冻结，则优惠取消</td></tr>';
    }else{
        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    }
    for(var i = 0 ; i < 9-data.payment.details.length-data.details.length ; i++){
        strHtml += '<tr height="15px"><td colspan="6">--</td></tr>';
    }
    strHtml += '</table></td></tr>';
    strHtml += '</table>';
    strHtml += '</body>';

    KlxxPrint(strHtml);
}

function CreatePrintPageGxhYdyBb(data) {
    //01个性化1对1报班
    var strHtml = '<style type="text/css">';
    strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; }';
    strHtml += 'tr.Line td{ border-top:1px;}';
    strHtml += 'tr td{ height:15px; line-height:15px;}';
    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    strHtml += '</style>';
    strHtml += '<body style="margin:0 auto ;padding-top:350px;text-align:center">';
    strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr><td>';

    strHtml += '<table border="0" width="660px" align="center">';
    strHtml += '<tr height="15px" style="font-weight:900;">';
    strHtml += '<td>学员编码</td>';
    strHtml += '<td>学员APPID</td>';
    strHtml += '<td>学员姓名</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml += '<td>'+data.studentInfo.encoding+'</td>';
    strHtml += '<td>' + (data.studentInfo.login_no ? data.studentInfo.login_no : "") + '</td>';
    strHtml += '<td>'+data.studentInfo.student_name+'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table style="margin-top:10px" border="0" width="660px" align="center">';
    strHtml += '<tr height="15px" style="font-weight:900;">';
    strHtml += '<td>单据编码</td>';
    strHtml += '<td>业务类型</td>';
    strHtml += '<td>业务校区</td>';
    strHtml += '<td>经办人</td>';
    strHtml += '<td>业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml += '<td>'+data.encoding+'</td>';
    strHtml += '<td>报班</td>';
    strHtml += '<td> ' + data.branch_name + '</td>';
    strHtml += '<td> ' + data.employee_name + '</td>';
    strHtml += '<td> ' + Format("yyyy-MM-dd", new Date(data.create_time)) + '</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table style="margin-top:10px" border="0" width="660px" align="center">';
    strHtml += '<tr align="left" height="15px" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    strHtml += '<td>课程商品名称</td>';
    strHtml += '<td>报班课时</td>';
    strHtml += '<td>报班总金额</td>';
    strHtml += '</tr>';
    $.each(data.details,function(i,n) {
        strHtml += '<tr>';
        strHtml += '<td>'+ (i+1) +'</td>';
        strHtml += '<td>'+n.course_name +'</td>';
        strHtml += '<td>'+n.course_total_count+'</td>';
        strHtml += '<td>￥'+n.discount_sum_price+'</td>';
        strHtml += '</tr>';
    });
    strHtml += '</table>';

    strHtml += '<table style="margin-top:10px" border="0" width="660px">';
    strHtml += '<tr height="15px">';
    strHtml += '<td style="font-weight:900;">优惠金额：'+ (data.sum_price - data.actual_price)+'</td>';
    strHtml += '<td style="font-weight:900;">实际需缴费金额：￥'+data.sum_price+'</td>';
    strHtml += '<td style="font-weight:900;">已缴费金额：￥'+data.actual_price+'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table style="margin-top:10px" border="0" width="660px">';
    strHtml += '<tr height="15px">';
    strHtml += '<td style="font-weight:900;">缴费明细：</td>';
    strHtml += '</tr>';
    $.each(data.payment.details,function(i,n) {
            strHtml += '<tr height="15px">';
            strHtml += '<td style="font-weight:900;">' + n.payment_way_str + '</td>';
            strHtml += '<td >' + n.staffappprem + '</td>';
            strHtml += '</tr>';
    });
    // if(data.payment.cashPrice && data.payment.cashPrice != 0) {
    //     strHtml += '<tr height="15px">';
    //     strHtml += '<td style="font-weight:900;">现金</td>';
    //     strHtml += '<td >' + data.payment.cashPrice + '</td>';
    //     strHtml += '</tr>';
    // }
    //
    // if(data.payment.cardPrice && data.payment.cardPrice != 0) {
    //     strHtml += '<tr height="15px">';
    //     strHtml += '<td style="font-weight:900;">刷卡</td>';
    //     strHtml += '<td >' + data.payment.cardPrice + '</td>';
    //     strHtml += '</tr>';
    // }
    // if(data.payment.transferPrice && data.payment.transferPrice != 0) {
    //     strHtml += '<tr height="15px">';
    //     strHtml += '<td style="font-weight:900;">转账</td>';
    //     strHtml += '<td >' + data.payment.transferPrice + '</td>';
    //     strHtml += '</tr>';
    // }
    // if(data.payment.accountPrice && data.payment.accountPrice != 0) {
    //     strHtml += '<tr height="15px">';
    //     strHtml += '<td style="font-weight:900;">储值</td>';
    //     strHtml += '<td >' + data.payment.accountPrice + '</td>';
    //     strHtml += '</tr>';
    // }
    // if(data.payment.frozenAccountPrice && data.payment.frozenAccountPrice != 0) {
    //     strHtml += '<tr height="15px">';
    //     strHtml += '<td style="font-weight:900;">冻结</td>';
    //     strHtml += '<td >' + data.payment.frozenAccountPrice + '</td>';
    //     strHtml += '</tr>';
    // }
    strHtml += '</table></td></tr>';
    strHtml += '</table>';
    strHtml += '</body>';

    KlxxPrint(strHtml);
}


function CreatePrintPage04(data) {
//04充值  对公报表处还有
    var strHtml = '<style type="text/css">';
    strHtml += 'table { width:640px; font:12px Arial; color: #000;font-weight: 400; }';
    strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
    strHtml += 'tr td{ height:24px; line-height:24px;}';
    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    strHtml += '</style>';
    strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
    strHtml += '<table width="640px" style="display:inline-block" border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr><td>';

    strHtml += '<table border="0" width="640px" align="center">';
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + data[0].rechargeInfo.ORG_NAME + '	经办人:' + data[0].rechargeInfo.EMPLOYEE_NAME + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center" style="font-weight:900;">';
    strHtml += '<td width="150">学员编号</td>';
    strHtml += '<td width="66">学员姓名</td>';
    strHtml += '<td width="185">单据编号</td>';
    strHtml += '<td width="68">业务类型</td>';
//	strHtml += '<td width="135">业务校区</td>';
//	strHtml += '<td width="54">经办人</td>';
    strHtml += '<td width="76">业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>'+data[0].rechargeInfo.S_ENCODING+'</td>';
    strHtml += '<td>'+data[0].rechargeInfo.STUDENT_NAME+'</td>';
    strHtml += '<td>'+data[0].rechargeInfo.R_ENCODING+'</td>';
    strHtml += '<td>充值</td>';
//	strHtml += '<td>'+data.rechargeInfo.ORG_NAME+'</td>';
//	strHtml += '<td>'+data.rechargeInfo.EMPLOYEE_NAME+'</td>';
    strHtml += '<td>'+data[0].rechargeInfo.INPUT_TIME+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="7">充值信息：</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="640px" style="margin-top:15px">';
    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    // strHtml += '<td>操作账户</td>';
    strHtml += '<td>方式</td>';
    strHtml += '<td>操作金额</td>';
    strHtml += '<td>备注</td>';
    strHtml += '</tr>';
    for(var i=0;i<data.length;i++){
        strHtml += '<tr align="center">';
        strHtml += '<td>' + (i + 1) + '</td>';
        // strHtml += '<td>'+data[i].rechargeInfo.NAME+'</td>';
        strHtml += '<td>'+data[i].rechargeInfo.PAY_NAME+'</td>';//缴费方式
        strHtml += '<td>￥'+data[i].rechargeInfo.MONEY+'</td>';
        strHtml += '<td>'+ fitlerEmptyReturn(data[i].rechargeInfo.REMARK, '')+'</td>';
        strHtml += '</tr>';
    }
    strHtml += '</table></td></tr>';
    strHtml += '</table>';
    strHtml += '</body>';
    KlxxPrint(strHtml);
}

function CreatePrintPage04Xiamen(data) {
//04充值  对公报表处还有
    var strHtml = '<style type="text/css">';
    strHtml += 'table { width:640px; font:12px Arial; color: #000;font-weight: 400; }';
    strHtml += 'tr.Line td{ border-top:1px dashed #000;}';
    strHtml += 'tr td{ height:24px; line-height:24px;}';
    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    strHtml += '</style>';
    strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center;">';
    strHtml += '<table width="640px" style="display:inline-block;"  border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr><td>';

    strHtml += '<table border="0" width="640px" align="center">';
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + data[0].rechargeInfo.ORG_NAME.replace('个性化','').replace('培英精品班','').replace('大小班','') + '	经办人:' + data[0].rechargeInfo.EMPLOYEE_NAME + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center" style="font-weight:900;">';
    strHtml += '<td width="66">学员姓名</td>';
    strHtml += '<td width="68">业务类型</td>';
//	strHtml += '<td width="135">业务校区</td>';
//	strHtml += '<td width="54">经办人</td>';
    strHtml += '<td width="76">业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>'+data[0].rechargeInfo.STUDENT_NAME+'</td>';
    strHtml += '<td>充值</td>';
//	strHtml += '<td>'+data.rechargeInfo.ORG_NAME+'</td>';
//	strHtml += '<td>'+data.rechargeInfo.EMPLOYEE_NAME+'</td>';
    strHtml += '<td>'+data[0].rechargeInfo.INPUT_TIME+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="7">充值信息：</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="640px" style="margin-top:15px">';
    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    // strHtml += '<td>操作账户</td>';
    strHtml += '<td>方式</td>';
    strHtml += '<td>操作金额</td>';
    strHtml += '</tr>';
    for(var i=0;i<data.length;i++){
        strHtml += '<tr align="center">';
        strHtml += '<td>' + (i + 1) + '</td>';
        // strHtml += '<td>'+data[i].rechargeInfo.NAME+'</td>';
        strHtml += '<td>'+data[i].rechargeInfo.PAY_NAME+'</td>';//缴费方式
        strHtml += '<td>￥'+data[i].rechargeInfo.MONEY+'</td>';
        strHtml += '</tr>';
    }
    strHtml += '</table></td></tr>';
    strHtml += '</table>';
    strHtml += '</body>';
    KlxxPrint(strHtml);
}

function CreatePrintPage05(data) {
//05转账
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
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + data.transferInfo.ORG_NAME + '	经办人:' + data.transferInfo.EMPLOYEE_NAME + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center" style="font-weight:900;">';
    strHtml += '<td width="150">学员编号</td>';
    strHtml += '<td width="66">学员姓名</td>';
    strHtml += '<td width="185">单据编号</td>';
    strHtml += '<td width="68">业务类型</td>';
//	strHtml += '<td width="135">业务校区</td>';
//	strHtml += '<td width="54">经办人</td>';
    strHtml += '<td width="76">业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>'+data.transferInfo.S_ENCODING+'</td>';
    strHtml += '<td>'+data.transferInfo.STUDENT_NAME+'</td>';
    strHtml += '<td>'+data.transferInfo.D_ENCODING+'</td>';
    strHtml += '<td>转账</td>';
//	strHtml += '<td>'+data.transferInfo.ORG_NAME+'</td>';
//	strHtml += '<td>'+data.transferInfo.EMPLOYEE_NAME+'</td>';
    strHtml += '<td>'+data.transferInfo.INPUT_TIME+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="7">转账信息：</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="660px" style="margin-top:15px">';
    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    strHtml += '<td>转出账户</td>';
    strHtml += '<td>转入学员</td>';
    strHtml += '<td>转入账户</td>';
    strHtml += '<td>操作金额</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>1</td>';
    strHtml += '<td>'+data.transferInfo.OUTPUT_PRODUCT_LINE+'</td>';
    strHtml += '<td>'+data.transferInfo.INPUT_STU_NAME+'</td>';
    strHtml += '<td>'+data.transferInfo.INPUT_PRODUCT_LINE+'</td>';
    strHtml += '<td>￥'+data.transferInfo.MONEY+'</td>';
    strHtml += '</tr>';
    strHtml += '</table></td></tr>';

    strHtml += '</table>';
    strHtml += '</body>';

    KlxxPrint(strHtml);
}

function CreatePrintPage06(data) {
//06理赔
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
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + data.claimInfo.ORG_NAME + '	经办人:' + data.claimInfo.EMPLOYEE_NAME + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center" style="font-weight:900;">';
    strHtml += '<td width="160">学员编号</td>';
    strHtml += '<td width="66">学员姓名</td>';
    strHtml += '<td width="185">单据编号</td>';
    strHtml += '<td width="68">业务类型</td>';
//	strHtml += '<td width="140">业务校区</td>';
//	strHtml += '<td width="54">经办人</td>';
    strHtml += '<td width="76">业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>'+data.claimInfo.S_ENCODING+'</td>';
    strHtml += '<td>'+data.claimInfo.STUDENT_NAME+'</td>';
    strHtml += '<td>'+data.claimInfo.D_ENCODING+'</td>';
    strHtml += '<td>理赔</td>';
//	strHtml += '<td>'+data.claimInfo.ORG_NAME+'</td>';
//	strHtml += '<td>'+data.claimInfo.EMPLOYEE_NAME+'</td>';
    strHtml += '<td>'+data.claimInfo.INPUT_TIME+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="7">理赔信息：</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="660px" style="margin-top:15px">';
    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    strHtml += '<td>理赔金额</td>';
    strHtml += '<td>账户</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>1</td>';
    strHtml += '<td>￥'+data.claimInfo.MONEY+'</td>';
    strHtml += '<td>'+data.claimInfo.PRODUCT_LINE+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="3">理赔原因：'+data.claimInfo.REMARK+'</td>';
    strHtml += '</tr>';

    strHtml += '</table></td></tr>';
    strHtml += '</table>';
    strHtml += '</body>';

    KlxxPrint(strHtml);

}

function CreatePrintPage06Xiamen(data) {
//06理赔
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
    strHtml += '<td colspan="5">业务校区:' + data.claimInfo.ORG_NAME.replace('个性化','').replace('培英精品班','').replace('大小班','') + '	经办人:' + data.claimInfo.EMPLOYEE_NAME + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center" style="font-weight:900;">';
    strHtml += '<td width="66">学员姓名</td>';
    strHtml += '<td width="68">业务类型</td>';
//	strHtml += '<td width="140">业务校区</td>';
//	strHtml += '<td width="54">经办人</td>';
    strHtml += '<td width="76">业务日期</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>'+data.claimInfo.S_ENCODING+'</td>';
    strHtml += '<td>'+data.claimInfo.STUDENT_NAME+'</td>';
    strHtml += '<td>'+data.claimInfo.D_ENCODING+'</td>';
    strHtml += '<td>理赔</td>';
//	strHtml += '<td>'+data.claimInfo.ORG_NAME+'</td>';
//	strHtml += '<td>'+data.claimInfo.EMPLOYEE_NAME+'</td>';
    strHtml += '<td>'+data.claimInfo.INPUT_TIME+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="7">理赔信息：</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="660px" style="margin-top:15px">';
    strHtml += '<tr height="15px" align="center" style="font-weight:900;">';
    strHtml += '<td>编号</td>';
    strHtml += '<td>理赔金额</td>';
    strHtml += '<td>账户</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="center">';
    strHtml += '<td>1</td>';
    strHtml += '<td>￥'+data.claimInfo.MONEY+'</td>';
    strHtml += '<td>'+data.claimInfo.PRODUCT_LINE+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr align="left">';
    strHtml += '<td colspan="3">理赔原因：'+data.claimInfo.REMARK+'</td>';
    strHtml += '</tr>';

    strHtml += '</table></td></tr>';

    strHtml += '</table>';
    strHtml += '</body>';
    KlxxPrint(strHtml);

}

//退费
function CreatePrintPageForRefund (refund){
    var strHtml = '<style type="text/css">';
    strHtml += 'table { width:660px; font:12px Arial;font-weight: 400; }';
    strHtml += 'tr.Line td{ border-top:1px;}';
    strHtml += 'tr td{ height:15px; line-height:15px;}';
    strHtml += 'h1{ text-align:center; font:12px 微软雅黑,Arial,Helvetica,sans-serif}';
    strHtml += '</style>';
    strHtml += '<body style="margin:0 auto;padding-top:350px;text-align:center">';
    strHtml += '<table width="660px" style="display:inline-block"  border="0" cellspacing="0" cellpadding="0">';
    strHtml += '<tr><td>';

    strHtml += '<table border="0" width="660px" align="center">';
    strHtml += '<tr align="left" style="font-weight:900;">';
    strHtml += '<td colspan="5">业务校区:' + refund.branch_name + '	                 日期:' + Format("yyyy-MM-dd", new Date(refund.create_time)) + '</td>';
    strHtml += '</tr>';
    strHtml += '<tr height="15px" style="font-weight:900;">';
    strHtml += '<td align="center">学员姓名</td>';
    strHtml += '<td width="63%" align="left">业务类型</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml += '<td align="center"  >'+ refund.student_name + '(' + refund.student_encoding + ')' +'</td>';
    strHtml += '<td width="63%"  align="left">退费</td>';
    strHtml += '</tr>';
    strHtml += '</table>';

    strHtml += '<table border="0" width="660px" align="center">';
    strHtml += '<tr align="left" height="15px" style="font-weight:900;">';
    strHtml += '<td>单据编号</td>';
    strHtml += '<td>退费总金额</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml += '<td>'+ refund.encoding +'</td>';
    strHtml += '<td>￥'+ refund.fee_amount +'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';
    strHtml += '</body>';

    KlxxPrint(strHtml);
}
// 一对一考勤打印
function CreatePrintPageYdyAttendance(data) {

    var strHtml ='<body>';
    strHtml +='<table style="width:700px;font-size: 14px;height:30px"><tr><td></td></tr></table>';
    strHtml +='<table style="width:600px;font-size: 14px;line-height:16px;font-weight: 500;margin:0 0 0 30px;">';
    strHtml +='<tr>';
    strHtml +='<td>单据编号：</td>';
    strHtml +='<td>'+data[0].encoding+'</td>';
    strHtml +='<td>教师姓名：</td>';
    strHtml +='<td>'+data[0].attTeacherName.substr(0,4)+'</td>';
    strHtml +='<td>科目：</td>';
    strHtml +='<td>'+data[0].subject_name+'</td>';
    strHtml +='<td style="width:70px"></td>'
    strHtml +='</tr>';
    strHtml += '<tr>';
    strHtml +='<td>课程：</td>';
    strHtml += '<td>'+data[0].course_name.substr(0,13)+'</td>';
    strHtml +='<td></td>';
    strHtml +='<td></td>';
    strHtml += '<td>学管师：</td>';
    strHtml +='<td>'+data[0].counselor_name.substr(0,4)+'</td>';
    strHtml +='</tr>';
    strHtml +='<tr>';
    strHtml +='<td>上课日期：</td>';
    strHtml +='<td>'+(data[0].course_date+'').substring(0, 4)+'-'+(data[0].course_date+'').substring(4, 6)+'-'+(data[0].course_date+'').substring(6, 8)+'</td>';
    strHtml += '<td>上课时间：</td>';
    strHtml +='<td>'+data[0].start_time+'</td>';
    strHtml +='<td>下课时间：</td>';
    strHtml += '<td>'+data[0].end_time+'</td>';
    strHtml += '</tr>';
    strHtml += '<tr>';
    strHtml +='<td>学生姓名：</td>';
    strHtml +='<td>'+data[0].student_name+'</td>';
    strHtml +='<td>年级：</td>';
    strHtml +='<td>'+data[0].grade_name+'</td>';
    strHtml +='<td></td>';
    strHtml +='<td></td>';
    strHtml += '</tr>';
    strHtml += '</table>';
    strHtml +='<table style="width:700px;font-size: 14px;height:80px"><tr><td></td></tr></table>';
    strHtml +='<table style="width:700px;font-size: 14px;line-height:16px;font-weight: 500; margin:0 0 0 30px">';
    strHtml +='<tr>';
    strHtml +='<td>单据编号：</td>';
    strHtml +='<td>'+data[0].encoding+'</td>';
    strHtml +='<td>教师姓名：</td>';
    strHtml +='<td>'+data[0].attTeacherName.substr(0,4)+'</td>';
    strHtml +='<td>科目：</td>';
    strHtml +='<td>'+data[0].subject_name+'</td>';
    strHtml +='</tr>';
    strHtml += '<tr>';
    strHtml += '<td>课程：</td>';
    strHtml += '<td>'+data[0].course_name.substr(0,13)+'</td>';
    strHtml +='<td></td>';
    strHtml += '<td></td>';
    strHtml +='<td>学管师：</td>';
    strHtml +='<td>'+data[0].counselor_name.substr(0,4)+'</td>';
    strHtml +='</tr>';
    strHtml +='<tr>';
    strHtml += '<td>上课日期：</td>';
    strHtml +='<td>'+(data[0].course_date+'').substring(0, 4)+'-'+(data[0].course_date+'').substring(4, 6)+'-'+(data[0].course_date+'').substring(6, 8)+'</td>';
    strHtml +='<td>上课时间：</td>';
    strHtml +='<td>'+data[0].start_time+'</td>';
    strHtml +='<td>下课时间：</td>';
    strHtml += '<td>'+data[0].end_time+'</td>';
    strHtml +='</tr>';
    strHtml +='<tr>';
    strHtml +='<td>学生姓名：</td>';
    strHtml +=  '<td>'+data[0].student_name.substr(0,8)+'</td>';
    strHtml += '<td>年级：</td>';
    strHtml += '<td>'+data[0].grade_name+'</td>';
    strHtml += '</tr>';
    strHtml += '</table>';
    strHtml +='<table style="width:700px;font-size: 14px;height:270px"><tr><td></td></tr></table>';
    strHtml +='<table style="width:700px;font-size: 14px;line-height:16px;font-weight: 500;margin:0 0 0 30px">';
    strHtml += '<tr>';
    strHtml +=  '<td>学生姓名：</td>';
    strHtml +='<td>'+data[0].student_name.substr(0,8)+'</td>';
    strHtml += '<td style="padding-left: 50px">教师姓名：</td>';
    strHtml += '<td>'+data[0].attTeacherName.substr(0,4)+'</td>';
    strHtml +=  '<td >日期：</td>';
    strHtml += '<td>'+(data[0].course_date+'').substring(0, 4)+'-'+(data[0].course_date+'').substring(4, 6)+'-'+(data[0].course_date+'').substring(6, 8)+'</td>';
    strHtml += '<td>科目：</td>';
    strHtml += '<td>'+data[0].subject_name+'</td>';
    strHtml += '</tr>';
    strHtml +='<tr>';
    strHtml +=' <td>学管师：</td>';
    strHtml +='<td>'+data[0].counselor_name+'</td>';
    strHtml +='<td style="padding-left: 50px">单据编号：</td>';
    strHtml +='<td >'+data[0].encoding+'</td>';
    strHtml += '<td></td>';
    strHtml += '<td></td>';
    strHtml +='<td>时间：</td>';
    strHtml +='<td>'+data[0].start_time+'-'+data[0].end_time+'</td>';
    strHtml +='</tr>';
    strHtml += '</table>';
    strHtml +='</body>';
    ydyAttendanceQrcode(strHtml,data);
}
//批量打印
function CreatePrintPageMoreYdyAttendance(data) {
    var array=[];
    for(var i=0;i<data.length;i++){
    var strHtml ='<body>';
        strHtml +='<table style="width:700px;font-size: 14px;height:30px"><tr><td></td></tr></table>';
        strHtml +='<table style="width:600px;font-size: 14px;line-height:16px;font-weight: 500;margin:0 0 0 30px;">';
        strHtml +='<tr>';
        strHtml +='<td>单据编号：</td>';
        strHtml +='<td>'+data[i].encoding+'</td>';
        strHtml +='<td>教师姓名：</td>';
        strHtml +='<td>'+data[i].attTeacherName.substr(0,4)+'</td>';
        strHtml +='<td>科目：</td>';
        strHtml +='<td>'+data[i].subject_name+'</td>';
        strHtml +='<td style="width:70px"></td>'
        strHtml +='</tr>';
        strHtml += '<tr>';
        strHtml +='<td>课程：</td>';
        strHtml += '<td>'+data[i].course_name.substr(0,13)+'</td>';
        strHtml +='<td></td>';
        strHtml +='<td></td>';
        strHtml += '<td>学管师：</td>';
        strHtml +='<td>'+data[i].counselor_name.substr(0,4)+'</td>';
        strHtml +='</tr>';
        strHtml +='<tr>';
        strHtml +='<td>上课日期：</td>';
        strHtml +='<td>'+(data[i].course_date+'').substring(0, 4)+'-'+(data[i].course_date+'').substring(4, 6)+'-'+(data[i].course_date+'').substring(6, 8)+'</td>';
        strHtml += '<td>上课时间：</td>';
        strHtml +='<td>'+data[i].start_time+'</td>';
        strHtml +='<td>下课时间：</td>';
        strHtml += '<td>'+data[i].end_time+'</td>';
        strHtml += '</tr>';
        strHtml += '<tr>';
        strHtml +='<td>学生姓名：</td>';
        strHtml +='<td>'+data[i].student_name+'</td>';
        strHtml +='<td>年级：</td>';
        strHtml +='<td>'+data[i].grade_name+'</td>';
        strHtml +='<td></td>';
        strHtml +='<td></td>';
        strHtml += '</tr>';
        strHtml += '</table>';
        strHtml +='<table style="width:700px;font-size: 14px;height:80px"><tr><td></td></tr></table>';
        strHtml +='<table style="width:700px;font-size: 14px;line-height:16px;font-weight: 500; margin:0 0 0 30px">';
        strHtml +='<tr>';
        strHtml +='<td>单据编号：</td>';
        strHtml +='<td>'+data[i].encoding+'</td>';
        strHtml +='<td>教师姓名：</td>';
        strHtml +='<td>'+data[i].attTeacherName.substr(0,4)+'</td>';
        strHtml +='<td>科目：</td>';
        strHtml +='<td>'+data[i].subject_name+'</td>';
        strHtml +='</tr>';
        strHtml += '<tr>';
        strHtml += '<td>课程：</td>';
        strHtml += '<td>'+data[i].course_name.substr(0,13)+'</td>';
        strHtml +='<td></td>';
        strHtml += '<td></td>';
        strHtml +='<td>学管师：</td>';
        strHtml +='<td>'+data[i].counselor_name.substr(0,4)+'</td>';
        strHtml +='</tr>';
        strHtml +='<tr>';
        strHtml += '<td>上课日期：</td>';
        strHtml +='<td>'+(data[i].course_date+'').substring(0, 4)+'-'+(data[i].course_date+'').substring(4, 6)+'-'+(data[i].course_date+'').substring(6, 8)+'</td>';
        strHtml +='<td>上课时间：</td>';
        strHtml +='<td>'+data[i].start_time+'</td>';
        strHtml +='<td>下课时间：</td>';
        strHtml += '<td>'+data[i].end_time+'</td>';
        strHtml +='</tr>';
        strHtml +='<tr>';
        strHtml +='<td>学生姓名：</td>';
        strHtml +=  '<td>'+data[i].student_name.substr(0,8)+'</td>';
        strHtml += '<td>年级：</td>';
        strHtml += '<td>'+data[i].grade_name+'</td>';
        strHtml += '</tr>';
        strHtml += '</table>';
        strHtml +='<table style="width:700px;font-size: 14px;height:270px"><tr><td></td></tr></table>';
        strHtml +='<table style="width:700px;font-size: 14px;line-height:16px;font-weight: 500;margin:0 0 0 30px">';
        strHtml += '<tr>';
        strHtml +=  '<td>学生姓名：</td>';
        strHtml +='<td>'+data[i].student_name.substr(0,8)+'</td>';
        strHtml += '<td style="padding-left: 50px">教师姓名：</td>';
        strHtml += '<td>'+data[i].attTeacherName.substr(0,4)+'</td>';
        strHtml +=  '<td >日期：</td>';
        strHtml += '<td>'+(data[i].course_date+'').substring(0, 4)+'-'+(data[i].course_date+'').substring(4, 6)+'-'+(data[i].course_date+'').substring(6, 8)+'</td>';
        strHtml += '<td>科目：</td>';
        strHtml += '<td>'+data[i].subject_name+'</td>';
        strHtml += '</tr>';
        strHtml +='<tr>';
        strHtml +=' <td>学管师：</td>';
        strHtml +='<td>'+data[i].counselor_name+'</td>';
        strHtml +='<td style="padding-left: 50px">单据编号：</td>';
        strHtml +='<td >'+data[i].encoding+'</td>';
        strHtml += '<td></td>';
        strHtml += '<td></td>';
        strHtml +='<td>时间：</td>';
        strHtml +='<td>'+data[i].start_time+'-'+data[i].end_time+'</td>';
        strHtml +='</tr>';
        strHtml += '</table>';
        strHtml +='</body>';
    array.push(strHtml);
        }
    ydyAttendanceMoreQrcode(array,data);
    }
