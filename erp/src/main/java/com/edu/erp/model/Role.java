package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    //角色名称
    private String roleName;
    //备注
    private String remark;
    //模块
    private String mod;

    private String searchInfo;

    private Long accountId;

    private Long createId;
    private Long updateId;

    //备注更新
    private String updateRemark;

    List<String> selectedPrivileges;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateRemark() {
        return updateRemark;
    }

    public void setUpdateRemark(String updateRemark) {
        this.updateRemark = updateRemark;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<String> getSelectedPrivileges() {
        return selectedPrivileges;
    }

    public void setSelectedPrivileges(List<String> selectedPrivileges) {
        this.selectedPrivileges = selectedPrivileges;
    }
}
