package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

public class OrganizationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    // 上级组织
    private Long parent_id;
    // 产品线
    private Long product_line;
    // 组织名称
    private String org_name;
    // 组织类型
    private Integer org_type;/* 1：地区级别 2：部门级别 3：团队级别 4：校区级别 */

    private Integer sort_number;

    private Integer status;

    private long update_user;

    private Date update_time;

    private long create_user;

    private Date create_time;

    private String org_type_name;// 由于数据库表中org_type字段类型为number,而前端需显示字符类型的各个级别，该字段仅代替org_type显示字符串

    private String address;

    private Double longitude;

    private Double latitude;

    private String parent_org_name;

    private Integer org; // 组织机构代号

    // logo
    private String logo;
    // 域名
    private String domain;
    // 商务号
    private String mchid;
    // 终端编号
    private String terminalNo;
    // 商务号
    private String mid;
    // 终端编号
    private String tid;
    // 电话
    private String phone;
    // 老学员积分
    private Integer oldStuIntegral;
    // 校区类型
    private Integer orgKind;
    // 校区邮箱
    private String email;
    // 组织机构简称
    private String shortOrgName;

    public String getParent_org_name() {
        return parent_org_name;
    }

    public void setParent_org_name(String parent_org_name) {
        this.parent_org_name = parent_org_name;
    }

    public String getOrg_type_name() {
        return org_type_name;
    }

    public void setOrg_type_name(String org_type_name) {
        this.org_type_name = org_type_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "OrganizationInfo [id=" + id + ", parent_id=" + parent_id + ", product_line=" + product_line
                + ", org_name=" + org_name + ", org_type=" + org_type + ", sort_number=" + sort_number + ", Status="
                + status + ", Update_user=" + update_user + ", Update_time=" + update_time + ", create_user="
                + create_user + ", create_time=" + create_time + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((update_time == null) ? 0 : update_time.hashCode());
        result = prime * result + (int) (update_user ^ (update_user >>> 32));
        result = prime * result + ((create_time == null) ? 0 : create_time.hashCode());
        result = prime * result + (int) (create_user ^ (create_user >>> 32));
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((org_name == null) ? 0 : org_name.hashCode());
        result = prime * result + ((org_type == null) ? 0 : org_type.hashCode());
        result = prime * result + ((parent_id == null) ? 0 : parent_id.hashCode());
        result = prime * result + ((product_line == null) ? 0 : product_line.hashCode());
        result = prime * result + ((sort_number == null) ? 0 : sort_number.hashCode());
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
        OrganizationInfo other = (OrganizationInfo) obj;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (update_time == null) {
            if (other.update_time != null)
                return false;
        } else if (!update_time.equals(other.update_time))
            return false;
        if (update_user != other.update_user)
            return false;
        if (create_time == null) {
            if (other.create_time != null)
                return false;
        } else if (!create_time.equals(other.create_time))
            return false;
        if (create_user != other.create_user)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (org_name == null) {
            if (other.org_name != null)
                return false;
        } else if (!org_name.equals(other.org_name))
            return false;
        if (org_type == null) {
            if (other.org_type != null)
                return false;
        } else if (!org_type.equals(other.org_type))
            return false;
        if (parent_id == null) {
            if (other.parent_id != null)
                return false;
        } else if (!parent_id.equals(other.parent_id))
            return false;
        if (product_line == null) {
            if (other.product_line != null)
                return false;
        } else if (!product_line.equals(other.product_line))
            return false;
        if (sort_number == null) {
            if (other.sort_number != null)
                return false;
        } else if (!sort_number.equals(other.sort_number))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Long getProduct_line() {
        return product_line;
    }

    public void setProduct_line(Long product_line) {
        this.product_line = product_line;
    }

    public String getOrg_name() {
        return org_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public Integer getOrg_type() {
        return org_type;
    }

    public void setOrg_type(Integer org_type) {
        this.org_type = org_type;
    }

    public Integer getSort_number() {
        return sort_number;
    }

    public void setSort_number(Integer sort_number) {
        this.sort_number = sort_number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(long update_user) {
        this.update_user = update_user;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(long create_user) {
        this.create_user = create_user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOldStuIntegral() {
        return oldStuIntegral;
    }

    public void setOldStuIntegral(Integer oldStuIntegral) {
        this.oldStuIntegral = oldStuIntegral;
    }

    public Integer getOrgKind() {
        return orgKind;
    }

    public void setOrgKind(Integer orgKind) {
        this.orgKind = orgKind;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static enum OrgTypeEnum {
        CITY(1, "地区级别"), DEP(2, "部门级别"), // 部门和团队是同级的
        BU(3, "团队级别"), BRANCH(4, "校区级别");
        private Integer code;
        private String desc;

        private OrgTypeEnum(Integer code, String desc) {
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
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getShortOrgName() {
        return shortOrgName;
    }

    public void setShortOrgName(String shortOrgName) {
        this.shortOrgName = shortOrgName;
    }
}
