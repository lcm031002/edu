package com.ebusiness.hrm.model;

public enum StatusEnum {
	VALID(1, "有效"),
	INVALID(2, "无效"),
	DELETE(0, "删除");
	private Integer code;
	private String desc;
	private StatusEnum(Integer code, String desc){
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
	public static String getDesc(Integer code){
		StatusEnum[] vals=StatusEnum.values();
		for(StatusEnum v : vals){
			if(v.getCode()==code){
				return v.getDesc();
			}
		}
		return "";
	}
}
