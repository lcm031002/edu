package com.edu.erp.model;

/**
 * @ClassName: TabInvoiceReceiveLog
 * @Description: 发票领取记录
 */
public class TabInvoiceReceiveLog extends BaseObject {

    private Long invoiceId; // 发票ID

    private String receiverCode; // 领取人代码

    private String receiver; // 领取人

    private String remark; // 备注

    private String statusName;

    public static enum StatusEnum {
        WAIT_RECEIVE(1, "待领取"), RECEIVED(2, "已领取"), CANCEL_RECEIVE(3, "领取作废"), APPLY(4, "开票中"), REJECTED(5, "已拒绝");

        private Integer code;
        private String desc;

        private StatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
