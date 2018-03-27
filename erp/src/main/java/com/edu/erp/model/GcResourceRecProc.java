package com.edu.erp.model;



//资源记录日志
public class GcResourceRecProc extends BaseObject {

	private static final long serialVersionUID = 1L;

	//资源记录ID
	private Long resource_rec_id;
	
	//操作类型  1：资源录入，2：资源分配，3，资源预约访 4：资源约访，5资源报班
    private Integer opt_type;
    
    //业务id
    private Long busi_id;
    
    //业务内容
    private String busi_content;
    
    public static enum optTypeEnum{
    	INPUT(1,"资源信息录入"),DIST(2,"资源分配"),
    	PLAN_VISIT(3,"资源预约访"),VISIT(5,"资源约访"),
    	ORDER(6,"资源报班"),TSF(4,"资源转移"),LISTENPLAN(7,"试听预约"),
    	ORDERPLAN(8,"报班预约"),PAY(9,"支付"),ONLINEORDER(10,"在线报班"),
    	DIST_BRANCH(11,"分配校区");
    	private Integer optType;
    	private String optTypeName;
    	
    	private optTypeEnum(Integer optType,String optTypeName){
    		this.optType =optType ;
    		this.optTypeName =optTypeName ;
    	}
    	
    	public void setOptType(Integer optType){
    		this.optType = optType;
    	}
    	
    	public Integer getOptType(){
    		return this.optType;
    	}
    	
    	public void setOptTypeName(String optTypeName){
    		this.optTypeName = optTypeName;
    	}
    	public String getOptTypeName(){
    		return this.optTypeName;
    	}
    }

	public Long getResource_rec_id() {
		return resource_rec_id;
	}

	public void setResource_rec_id(Long resource_rec_id) {
		this.resource_rec_id = resource_rec_id;
	}

	public Integer getOpt_type() {
		return opt_type;
	}

	public void setOpt_type(Integer opt_type) {
		this.opt_type = opt_type;
	}

	public Long getBusi_id() {
		return busi_id;
	}

	public void setBusi_id(Long busi_id) {
		this.busi_id = busi_id;
	}

	public String getBusi_content() {
		return busi_content;
	}

	public void setBusi_content(String busi_content) {
		this.busi_content = busi_content;
	}
    
}
