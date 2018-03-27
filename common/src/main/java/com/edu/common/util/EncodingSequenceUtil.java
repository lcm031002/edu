package com.edu.common.util;

import com.edu.common.model.InvoicesSequence;
import com.edu.common.service.InvoicesSequenceService;

public class EncodingSequenceUtil {

    private static final String CZDJ_PREFIX = "CZ"; // 1：充值单据
    private static final String JFDJ_PREFIX = "JF"; // 2：缴费单据
    private static final String ZBDJ_PREFIX = "ZB"; // 3：转班单据
    private static final String TFDJ_PREFIX = "TF"; // 4：退费单据
    private static final String QKDJ_PREFIX = "QK"; // 5：取款单据
    private static final String LPDJ_PREFIX = "LP"; // 6：理赔单据
    private static final String ZZDJ_PREFIX = "ZZ"; // 7：转账单据
    private static final String KQDJ_PREFIX = "KQ"; // 8：考勤单据
    private static final String BJK_PREFIX = "BJK"; // 9：班级课
    private static final String YDY_PREFIX = "YDY"; // 10：一对一
    private static final String WFD_PREFIX = "WFD"; // 11：晚辅导
    private static final String TYPE_KM_PREFIX = "TYPE_KM";// 12：科目
    private static final String TYPE_KCMB_PREFIX = "TYPE_KCMB";// 13：课程目标
    private static final String TYPE_KCMX_PREFIX = "TYPE_KCMX";// 14：课程模型
    private static final String TYPE_CP_PREFIX = "TYPE_CP";// 15：产品
    private static final String TYPE_XXLX_PREFIX = "TYPE_XXLX";// 16：学校类型
    private static final String TYPE_XSKSZT_PREFIX = "TYPE_XSKSZT";// 17：学生考勤状态
    private static final String TYPE_LSKQZT_PREFIX = "TYPE_LSKQZT";// 18：教师考勤状态
    private static final String TYPE_FPTT_PREFIX = "TYPE_FPTT";// 19：发票抬头
    private static final String CZ_ZF_PREFIX = "CZ_ZF"; // 20：充值作废
    private static final String LP_ZF_PREFIX = "LP_ZF"; // 21：理赔作废
    private static final String QK_ZF_PREFIX = "QK_ZF"; // 22：取款作废
    private static final String DD_ZF_PREFIX = "DD_ZF"; // 23：订单作废
    private static final String TF_ZF_PREFIX = "TF_ZF"; // 24：退费作废
    private static final String YYZX_PREFIX = "YYZX";// 25:一元转校
    private static final String YYJZ_PREFIX = "YYJZ";// 26:一元结转
    private static final String XYBH_PREFIX = "XYBH";// 27:学员编号
    private static final String ZYBH_PREFIX = "ZYBH";// 28:资源编号
    private static final String YHQ_PREFIX = "YHQ"; // 29:优惠券（线上）
    private static final String SSA_PREFIX = "SSA"; // 30:排课申请编号
    private static final String YDY_ATTEND_PREFIX = "att-gxh"; // 31:考勤单号

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

                sequenceNum = fromatSequence(String.valueOf(sequence
                        .getSequence()));

                String DATA_STR = DateUtil.getCurrDate("yyyyMMdd");

                String PREFIX = "";

                switch (Integer.parseInt(invoicesType.toString())) {
                    case 1:
                        PREFIX = CZDJ_PREFIX;
                        break;
                    case 2:
                        PREFIX = JFDJ_PREFIX;
                        break;
                    case 3:
                        PREFIX = ZBDJ_PREFIX;
                        break;
                    case 4:
                        PREFIX = TFDJ_PREFIX;
                        break;
                    case 5:
                        PREFIX = QKDJ_PREFIX;
                        break;
                    case 6:
                        PREFIX = LPDJ_PREFIX;
                        break;
                    case 7:
                        PREFIX = ZZDJ_PREFIX;
                        break;
                    case 8:
                        PREFIX = KQDJ_PREFIX;
                        break;
                    case 9:
                        PREFIX = BJK_PREFIX;
                        break;
                    case 10:
                        PREFIX = YDY_PREFIX;
                        break;
                    case 11:
                        PREFIX = WFD_PREFIX;
                        break;
                    case 12:
                        PREFIX = TYPE_KM_PREFIX;
                        break;
                    case 13:
                        PREFIX = TYPE_KCMB_PREFIX;
                        break;
                    case 14:
                        PREFIX = TYPE_KCMX_PREFIX;
                        break;
                    case 15:
                        PREFIX = TYPE_CP_PREFIX;
                        break;
                    case 16:
                        PREFIX = TYPE_XXLX_PREFIX;
                        break;
                    case 17:
                        PREFIX = TYPE_XSKSZT_PREFIX;
                        break;
                    case 18:
                        PREFIX = TYPE_LSKQZT_PREFIX;
                        break;
                    case 19:
                        PREFIX = TYPE_FPTT_PREFIX;
                        break;
                    case 20:
                        PREFIX = CZ_ZF_PREFIX;
                        break;
                    case 21:
                        PREFIX = LP_ZF_PREFIX;
                        break;
                    case 22:
                        PREFIX = QK_ZF_PREFIX;
                        break;
                    case 23:
                        PREFIX = DD_ZF_PREFIX;
                        break;
                    case 24:
                        PREFIX = TF_ZF_PREFIX;
                        break;
                    case 25:
                        PREFIX = YYZX_PREFIX;
                        break;
                    case 26:
                        PREFIX = YYJZ_PREFIX;
                        break;
                    case 27:
                        PREFIX = XYBH_PREFIX;
                        break;
                    case 28:
                        PREFIX = ZYBH_PREFIX;
                        break;
                    case 29:
                        PREFIX = YHQ_PREFIX;
                        break;
                    case 30:
                        PREFIX = SSA_PREFIX;
                        break;
                    case 31:
                        PREFIX = YDY_ATTEND_PREFIX;
                        break;
                    default:
                        break;
                }

                /*invoicesSequenceService
                        .updateInvoicesSequenceByInvoicesType(invoicesType);*/
                PREFIX += "_";
                System.out.println(PREFIX + DATA_STR + sequenceNum);
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