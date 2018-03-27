package com.edu.erp.model;

import javax.xml.ws.Response;
import net.sf.json.JSONObject;

public class ResponseObject {
   private String msg;
   
   private String status;

   private JSONObject  data;

    public ResponseObject(){

    }

   public ResponseObject(String status, String msg) {
   		this.status = status;
   		this.msg = msg;
   }
   
	public String getMsg() {
		return msg;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject  data) {
		this.data = data;
	}
}
