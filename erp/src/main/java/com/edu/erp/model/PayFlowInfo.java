package com.edu.erp.model;

/***
 * Description : 订单在线支付流水表
 * 
 */
public class PayFlowInfo {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String pay_orderid;
    private Long student_id;
    private String student_encoding;
    private String pay_wechat;
    private String acc_no;
    private Integer pay_way;
    private String pay_way_name;
    private String mer_id;
    private String mer_name;
    private String student_name;
    private Double pay_amount;
    private String pay_time;
    private String pay_phone;
    private String remark;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(Double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_phone() {
        return pay_phone;
    }

    public void setPay_phone(String pay_phone) {
        this.pay_phone = pay_phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPay_orderid() {
        return pay_orderid;
    }

    public void setPay_orderid(String pay_orderid) {
        this.pay_orderid = pay_orderid;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getStudent_encoding() {
        return student_encoding;
    }

    public void setStudent_encoding(String student_encoding) {
        this.student_encoding = student_encoding;
    }

    public String getPay_wechat() {
        return pay_wechat;
    }

    public void setPay_wechat(String pay_wechat) {
        this.pay_wechat = pay_wechat;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public Integer getPay_way() {
        return pay_way;
    }

    public void setPay_way(Integer pay_way) {
        this.pay_way = pay_way;
    }

    public String getPay_way_name() {
        return pay_way_name;
    }

    public void setPay_way_name(String pay_way_name) {
        this.pay_way_name = pay_way_name;
    }

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public String getMer_name() {
        return mer_name;
    }

    public void setMer_name(String mer_name) {
        this.mer_name = mer_name;
    }

}