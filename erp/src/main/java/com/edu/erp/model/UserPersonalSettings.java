package com.edu.erp.model;

public class UserPersonalSettings extends BaseObject {

    // 用户id
    private Long userId;

    private String paramName;

    private String paramVal;


    /**
     * 设置 用户id,对应字段 user_personal_settings.user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 用户id,对应字段 user_personal_settings.user_id
     */
    public Long getUserId() {
        return this.userId;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getParamVal() {
        return this.paramVal;
    }
}