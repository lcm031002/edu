package com.edu.erp.model;

public class TpDictData extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 条目名称
	private String name;

	// 条目编码
	private String code;

	// 序号 显示的先后顺序
	private Integer orderNo;

	private Integer type_id;

	private String type_name;

	// 团队名称
	private String bu_name;
	// 团队id
	private Long bu_id;
	// 详细描述
	private String descdtl;
	
	private String dict_type_code;

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("字典数据id：");
		buff.append(getId());
		buff.append("，");
		buff.append("类型id： ");
		buff.append(getType_id());
		buff.append("，");
		buff.append("名称： ");
		buff.append(getName());
		buff.append("，");
		buff.append("编码： ");
		buff.append(getCode());
		buff.append("，");
		buff.append("是否可用： ");
		buff.append(getStatus());
		buff.append("，");
		buff.append("顺序编号： ");
		buff.append(getOrderNo());
		return buff.toString();
	}

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public String getDescdtl() {
		return descdtl;
	}

	public void setDescdtl(String descdtl) {
		this.descdtl = descdtl;
	}

	public String getDict_type_code() {
		return dict_type_code;
	}

	public void setDict_type_code(String dict_type_code) {
		this.dict_type_code = dict_type_code;
	}
}
