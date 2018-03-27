package com.edu.erp.model;

/**
 * 删除标识枚举
 * @author Madison
 * @date 2014-7-29
 */
public enum DelFlag 
{
	DEL_FLAG_N("0", "未删除"), DEL_FLAG_Y("1", "已删除");
	
	private DelFlag(String code, String desc) 
	{
		this.code = code;
		this.desc = desc;
	}
	
	/** 代码 */
	private String code;
	
	/** 描述 */
	private String desc;
	
	/**
	 * 根据枚举code获取枚举常量
	 * @param 	code		枚举Code
	 * @return	DelFlag		枚举常量
	 */
	public static DelFlag getEnumByCode(String code)
	{
		DelFlag[] delFlagAry = DelFlag.values();
		for(DelFlag delFlag : delFlagAry)
		{
			if(code.equals(delFlag.getCode()))
				return delFlag;
		}
		
		throw new IllegalArgumentException("无效的枚举Code【" + code + "】！");
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
