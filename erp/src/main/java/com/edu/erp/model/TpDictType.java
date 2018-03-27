package com.edu.erp.model;

public class TpDictType extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 4182599905601905820L;

	// 类型名称
	private String name;

	// 类型代码
	private String code;

	// 详细描述
	private String descdtl;

	// 是否有效状态 0：无效 1：有效
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public enum ProcessStatus {
		YES(1, "有效"), NO(0, "无效");
		// 是否有效码，0：无效，1：有效
		private Integer vCode;
		private String vName;

		public Integer getvCode() {
			return vCode;
		}

		public void setvCode(Integer vCode) {
			this.vCode = vCode;
		}

		public String getvName() {
			return vName;
		}

		public void setvName(String vName) {
			this.vName = vName;
		}

		private ProcessStatus(Integer c, String n) {
			vCode = c;
			vName = n;
		}
	}

	public String getDescdtl() {
		return descdtl;
	}

	public void setDescdtl(String descdtl) {
		this.descdtl = descdtl;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("字典类型Id：");
		buff.append(getId());
		buff.append("类型名称：");
		buff.append(getName());
		buff.append("，");
		buff.append("类型编码： ");
		buff.append(getCode());
		return buff.toString();
	}
}
