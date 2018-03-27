package com.edu.common.util;

import java.io.Serializable;

/**
 * 响应对象
 *
 */
public class Responder implements Serializable{
	private static final long serialVersionUID = -6060965769808700891L;
	
	private Integer code; 		//100-初始化；200-成功；300-警告；500-失败
	private String msg;			//信息
	private Object data;		//数据
	
	public Responder() {
		this.code = 100;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
