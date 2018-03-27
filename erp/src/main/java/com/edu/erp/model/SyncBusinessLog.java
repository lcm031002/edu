package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zenglw on 2018/1/24.
 */
public class SyncBusinessLog implements Serializable {
    private Long id;
    //UUID唯一标识业务
    private String businessId = UUID.randomUUID().toString();

    private BusinessType businessType;
    //业务主表的id
    private Long mainTableId;
    //tab_eai_log_info 表ID
    private Long logId;

    private Date createTime = new Date();

    private Long createUser;

    public SyncBusinessLog() {
    }

    public SyncBusinessLog(BusinessType businessType, Long mainTableId,Long createUser) {
        this.businessType = businessType;
        this.mainTableId = mainTableId;
        this.createUser = createUser;
    }

    public static enum BusinessType {
        UPDATE_COURSE_SCHEDULING("update_course_scheduling","更新课次","t_course_scheduling"),
        UPDATE_COURSE("update_course","更新课程","t_course");

        private String code;
        private String desc;
        private String mainTable;

        BusinessType(String code, String desc,String mainTable) {
            this.code = code;
            this.desc = desc;
            this.mainTable = mainTable;
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

        public String getMainTable() {
            return mainTable;
        }

        public void setMainTable(String mainTable) {
            this.mainTable = mainTable;
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public Long getMainTableId() {
        return mainTableId;
    }

    public void setMainTableId(Long mainTableId) {
        this.mainTableId = mainTableId;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
