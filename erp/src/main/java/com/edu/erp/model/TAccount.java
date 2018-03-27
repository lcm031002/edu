package com.edu.erp.model;


/***
 * Description : 学生账户表 POJO
 * 
 * Author ： junli.zhang
 * 
 * Date : 2014-09-01
 */
public class TAccount extends BaseObject{
	
    private static final long serialVersionUID = 1L;
	
    private Long product_line;  // 产品线类型
  		  		
    private Long student_id;  // 学生ID
  		
    private Long city_id;  // 地区ID
  		
    private Long branch_id;  // 校区ID
  		
    private Long account_type;  // 账户类型:0充值账户, 1一元课程账户
  		
    private Double fee_amount;  // 账户余额
    
    private Double frozen_account;  // 冻结账户
    
    private Double refund_account;  // 退费账户
  		
    private Long creater_id;  // 账户创建人
  		
    private Long bu_id;  // 团队ID
  		
 	private String remark;
 	
    public Long getProduct_line() {
      return product_line;
    }
	
    public void setProduct_line(Long product_line) {
      this.product_line = product_line;
    }
  		
  		
    public Long getStudent_id() {
      return student_id;
    }
	
    public void setStudent_id(Long student_id) {
      this.student_id = student_id;
    }
  		
    public Long getCity_id() {
      return city_id;
    }
	
    public void setCity_id(Long city_id) {
      this.city_id = city_id;
    }
  		
    public Long getBranch_id() {
      return branch_id;
    }
	
    public void setBranch_id(Long branch_id) {
      this.branch_id = branch_id;
    }
  		
    public Long getAccount_type() {
      return account_type;
    }
	
    public void setAccount_type(Long account_type) {
      this.account_type = account_type;
    }
  		
    public Double getFee_amount() {
      return fee_amount;
    }
	
    public void setFee_amount(Double fee_amount) {
      this.fee_amount = fee_amount;
    }
  		
    public Long getCreater_id() {
      return creater_id;
    }
	
    public void setCreater_id(Long creater_id) {
      this.creater_id = creater_id;
    }
  		
  		
    public Long getBu_id() {
      return bu_id;
    }
	
    public void setBu_id(Long bu_id) {
      this.bu_id = bu_id;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getFrozen_account() {
		return frozen_account;
	}

	public void setFrozen_account(Double frozen_account) {
		this.frozen_account = frozen_account;
	}

	public Double getRefund_account() {
		return refund_account;
	}

	public void setRefund_account(Double refund_account) {
		this.refund_account = refund_account;
	}
  		
}