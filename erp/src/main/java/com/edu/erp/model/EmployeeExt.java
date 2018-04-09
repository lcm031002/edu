package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;


public class EmployeeExt implements Serializable {

    private static final long serialVersionUID = 1L;
    //字段id
    private Long id;
    //字段名称
    private String fieldName;
    //字段关键字
    private String fieldKey;
    //字段状态
    private Integer fieldStatus;
    //字段状态返回值，该字段仅fieldstatus类型转换后传递数据到前台
    private String status;
    //字段值类型
    private String fieldType;
    //数据字典
    private Long fieldDictType;
    //创建人
    private Long createId;
    //创建时间
    private Date createTime;
    //修改人
    private Long updateId;
    //修改时间
    private Date updateTime;
    //备注
    private String remark;
    //修改前字段名
    private String oldFieldKey;
    //数据字典名
    private String dictTypeName;

    public static enum FieldStatusEnum {
        VALID(1, "启用"),
        INVALID(2, "默认"),
        DELETE(0, "禁用");
        private Integer code;
        private String desc;

        private FieldStatusEnum(Integer code, String desc) {
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

        public static String getDesc(Integer code) {
            FieldStatusEnum[] vals = FieldStatusEnum.values();
            for (FieldStatusEnum v : vals) {
                if (v.getCode() == code) {
                    return v.getDesc();
                }
            }
            return "";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public Integer getFieldStatus() {
        return fieldStatus;
    }

    public void setFieldStatus(Integer fieldStatus) {
        this.fieldStatus = fieldStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Long getFieldDictType() {
        return fieldDictType;
    }

    public void setFieldDictType(Long fieldDictType) {
        this.fieldDictType = fieldDictType;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public String getOldFieldKey() {
        return oldFieldKey;
    }

    public void setOldFieldKey(String oldFieldKey) {
        this.oldFieldKey = oldFieldKey;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createId == null) ? 0 : createId.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((fieldDictType == null) ? 0 : fieldDictType.hashCode());
        result = prime * result + ((fieldKey == null) ? 0 : fieldKey.hashCode());
        result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
        result = prime * result + ((fieldStatus == null) ? 0 : fieldStatus.hashCode());
        result = prime * result + ((fieldType == null) ? 0 : fieldType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((updateId == null) ? 0 : updateId.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmployeeExt other = (EmployeeExt) obj;
        if (createId == null) {
            if (other.createId != null)
                return false;
        } else if (!createId.equals(other.createId))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (fieldDictType == null) {
            if (other.fieldDictType != null)
                return false;
        } else if (!fieldDictType.equals(other.fieldDictType))
            return false;
        if (fieldKey == null) {
            if (other.fieldKey != null)
                return false;
        } else if (!fieldKey.equals(other.fieldKey))
            return false;
        if (fieldName == null) {
            if (other.fieldName != null)
                return false;
        } else if (!fieldName.equals(other.fieldName))
            return false;
        if (fieldStatus == null) {
            if (other.fieldStatus != null)
                return false;
        } else if (!fieldStatus.equals(other.fieldStatus))
            return false;
        if (fieldType == null) {
            if (other.fieldType != null)
                return false;
        } else if (!fieldType.equals(other.fieldType))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (updateId == null) {
            if (other.updateId != null)
                return false;
        } else if (!updateId.equals(other.updateId))
            return false;
        if (updateTime == null) {
            if (other.updateTime != null)
                return false;
        } else if (!updateTime.equals(other.updateTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeExt [id=" + id + ", fieldName=" + fieldName + ", fieldKey=" + fieldKey + ", fieldStatus="
                + fieldStatus + ", fieldType=" + fieldType + ", fieldDictType=" + fieldDictType + ", createId="
                + createId + ", createTime=" + createTime + ", updateId=" + updateId + ", updateTime=" + updateTime
                + ", remark=" + remark + "]";
    }


}
