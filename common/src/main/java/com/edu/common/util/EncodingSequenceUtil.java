package com.edu.common.util;

import com.edu.common.model.InvoicesSequence;
import com.edu.common.service.InvoicesSequenceService;

public class EncodingSequenceUtil {

    private static final String CZDJ_PREFIX = "SGCZ"; // 1：充值单据
    private static final String TFDJ_PREFIX = "SGTF"; // 2：退费单据
    private static final String QKDJ_PREFIX = "SGQX"; // 3：取现单据
    private static final String ZZDJ_PREFIX = "SGHZ"; // 4：互账单据
    private static final String BJK_PREFIX = "SGBK"; // 5：班级课
    private static final String YDY_PREFIX = "SGYY"; // 6：一对一
    private static final String WFD_PREFIX = "SGWF"; // 7：晚辅导
    private static final String TYPE_FPTT_PREFIX = "SGFP";// 8：发票抬头
    private static final String CZ_ZF_PREFIX = "SGCZ_ZF"; // 9：充值作废
    private static final String QK_ZF_PREFIX = "SGQX_ZF"; // 10：取现作废
    private static final String DD_ZF_PREFIX = "SGDD_ZF"; // 11：订单作废
    private static final String TF_ZF_PREFIX = "SGTF_ZF"; // 12：退费作废
    private static final String XYBH_PREFIX = "SGXY";// 13:学员编号
    private static final String SSA_PREFIX = "SGSA"; // 14:排课申请编号
    private static final String YDY_ATTEND_PREFIX = "SGKQ"; // 15:考勤单号

    private static InvoicesSequenceService invoicesSequenceService = ApplicationContextUtil
            .getBean("invoicesSequenceService");

    /**
     * 生成各种单据编号
     * 
     * @param invoicesType
     *            单据类型
     */
    public static String getSequenceNum(Long invoicesType) {
        synchronized (EncodingSequenceUtil.class) {
            try {
                InvoicesSequence sequence = invoicesSequenceService
                        .genSequenceByInvoicesType(invoicesType);
                String sequenceNum = null;

                sequenceNum = fromatSequence(String.valueOf(sequence.getSequence()));

                String DATA_STR = DateUtil.getCurrDate("yyyyMMdd");

                String PREFIX = "";

                switch (Integer.parseInt(invoicesType.toString())) {
                    case 1:
                        PREFIX = CZDJ_PREFIX;
                        break;
                    case 2:
                        PREFIX = TFDJ_PREFIX;
                        break;
                    case 3:
                        PREFIX = QKDJ_PREFIX;
                        break;
                    case 4:
                        PREFIX = ZZDJ_PREFIX;
                        break;
                    case 5:
                        PREFIX = BJK_PREFIX;
                        break;
                    case 6:
                        PREFIX = YDY_PREFIX;
                        break;
                    case 7:
                        PREFIX = WFD_PREFIX;
                        break;
                    case 8:
                        PREFIX = TYPE_FPTT_PREFIX;
                        break;
                    case 9:
                        PREFIX = CZ_ZF_PREFIX;
                        break;
                    case 10:
                        PREFIX = QK_ZF_PREFIX;
                        break;
                    case 11:
                        PREFIX = DD_ZF_PREFIX;
                        break;
                    case 12:
                        PREFIX = TF_ZF_PREFIX;
                        break;
                    case 13:
                        PREFIX = XYBH_PREFIX;
                        break;
                    case 14:
                        PREFIX = SSA_PREFIX;
                        break;
                    case 15:
                        PREFIX = YDY_ATTEND_PREFIX;
                        break;
                    default:
                        break;
                }

                /*invoicesSequenceService
                        .updateInvoicesSequenceByInvoicesType(invoicesType);*/
                PREFIX += "_";
                return PREFIX + DATA_STR + sequenceNum;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String fromatSequence(String sequenceNum) {
        int len = sequenceNum.length();
        if (len < 6) {
            len = 6 - len;
            for (int i = 0; i < len; i++)
                sequenceNum = "0" + sequenceNum;
        }
        return sequenceNum;
    }
}