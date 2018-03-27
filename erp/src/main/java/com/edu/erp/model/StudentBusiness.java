package com.edu.erp.model;


/**
 * @ClassName: StudentBusiness
 * @Description: 查询学生的账户信息
 * @author zhuliyong zly@entstudy.com
 * @date 2014年9月23日 下午2:08:10
 */
public class StudentBusiness extends StudentInfo {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 2422440920583422856L;

	
	public StudentBusiness(){
		
	}
	
	public StudentBusiness(long id){
		 setId(id);
	}
	
	
	private Long payment;// 已缴费

	private Long unpayment;// 欠费

	private Long tryLearn;// 试听、排课

	private Long listening_count; // 试听记录
		
	private Long account;// 储值账户

	private Integer businessType;// 业务模型

	private String grade_name;// 学生年级

	private String branch_name;// 开户学校

	private String counselorName;// 咨询师

	private String customerRelName;// 学管师
	
	private String seachString;//搜索字段
	
	private Long acount_id;//账户ID
	
	private Long balance;//余额
	
	private Long deduct;//扣费金额
	
	private Long balance1;//大小班余额
	private Long balance2;//一对一余额
	private Long balance3;//晚辅导余额
	private Long product_line;//产品线
	
	private String school_name;//学校

	private String selected;//是否选中的标志位
	
	public Long getAccount() {
		return account;
	}
	
	public Long getAcount_id() {
		return acount_id;
	}

	public Long getBalance() {
		return balance;
	}

	public Long getBalance1() {
		return balance1;
	}

	public Long getBalance2() {
		return balance2;
	}

	public Long getBalance3() {
		return balance3;
	}


	public Integer getBusinessType() {
		return businessType;
	}


	public String getCounselorName() {
		return counselorName;
	}

	public String getCustomerRelName() {
		return customerRelName;
	}

	public Long getDeduct() {
		return deduct;
	}


	public Long getListening_count() {
		return listening_count;
	}

	public Long getPayment() {
		return payment;
	}

	public Long getProduct_line() {
		return product_line;
	}

	public String getSchool_name() {
		return school_name;
	}

	public String getSeachString() {
		return seachString;
	}

	public String getSelected() {
		return selected;
	}

	public Long getTryLearn() {
		return tryLearn;
	}

	public Long getUnpayment() {
		return unpayment;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public void setAcount_id(Long acount_id) {
		this.acount_id = acount_id;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public void setBalance1(Long balance1) {
		this.balance1 = balance1;
	}

	public void setBalance2(Long balance2) {
		this.balance2 = balance2;
	}

	public void setBalance3(Long balance3) {
		this.balance3 = balance3;
	}


	public void setCounselorName(String counselorName) {
		this.counselorName = counselorName;
	}

	public void setCustomerRelName(String customerRelName) {
		this.customerRelName = customerRelName;
	}

	public void setDeduct(Long deduct) {
		this.deduct = deduct;
	}

	public void setListening_count(Long listening_count) {
		this.listening_count = listening_count;
	}

	public void setPayment(Long payment) {
		this.payment = payment;
	}

	public void setProduct_line(Long product_line) {
		this.product_line = product_line;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public void setSeachString(String seachString) {
		this.seachString = seachString;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setTryLearn(Long tryLearn) {
		this.tryLearn = tryLearn;
	}

	public void setUnpayment(Long unpayment) {
		this.unpayment = unpayment;
	}

	@Override
	public String toString() {
		return "StudentBusiness [payment=" + payment + ", unpayment="
				+ unpayment + ", tryLearn=" + tryLearn + ", account=" + account
				+ ", businessType=" + businessType + ", grade_name="
				+ grade_name + ", branch_name=" + branch_name
				+ ", counselorName=" + counselorName + ", customerRelName="
				+ customerRelName + ",toString() is " + super.toString() + "]";
	}
	
	
}
