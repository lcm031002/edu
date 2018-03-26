package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;

public class BaseObject implements Serializable, Cloneable {
    private static final long serialVersionUID = -6442902605373735062L;

    // 主键
    private Long id;
    // 状态
    private Integer status;
    // 创建用户
    private Long create_user;
    // 创建时间
    private Date create_time;
    // 更新用户
    private Long update_user;
    // 更新时间
    private Date update_time;
    // 新增用户名
    private String create_user_name;
    // 修改用户名
    private String update_user_name;

    public static enum StatusEnum {
        VALID(1, "有效"),
        INVALID(2, "无效"),
        DELETE(0, "删除");
        private Integer code;
        private String desc;

        private StatusEnum(Integer code, String desc) {
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
            StatusEnum[] vals = StatusEnum.values();
            for (StatusEnum v : vals) {
                if (v.getCode() == code) {
                    return v.getDesc();
                }
            }
            return "";
        }
    }

    @Override
    public Object clone() {
        Object bo = null;
        try {
            bo = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bo;
    }

    public Long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Long create_user) {
        this.create_user = create_user;
    }


    public Long getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Long update_user) {
        this.update_user = update_user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getUpdate_user_name() {
        return update_user_name;
    }

    public void setUpdate_user_name(String update_user_name) {
        this.update_user_name = update_user_name;
    }

}
