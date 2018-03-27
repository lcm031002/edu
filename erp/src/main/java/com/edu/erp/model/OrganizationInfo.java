package com.edu.erp.model;

public class OrganizationInfo extends BaseObject {

    private static final long serialVersionUID = 1L;

    // 上级组织
    private Long parent_id;
    // 产品线
    private Long product_line;
    // 组织名称
    private String org_name;
    //组织id
    private String buId;
    // 组织类型
    private Integer org_type;/* 1：地区级别 2：部门级别 3：团队级别 4：校区级别 */
    private Integer sort_number;
    // 地址
    private String address;
    // 经度
    private Double longitude;
    // 纬度
    private Double latitude;

    private String org_type_name;

    private String parent_org_name;
    // 机构代号
    private Integer org;
    // logo
    private String logo;
    // 域名
    private String domain;
    // 商户号
    private String mchid;
    // 终端编号
    private String terminalNo;
    // 商户号(WEB)
    private String mid;
    // 终端编号(WEB)
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

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getOrg_name() {
        return org_name;
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

    public Long getProduct_line() {
        return product_line;
    }

    public void setProduct_line(Long product_line) {
        this.product_line = product_line;
    }

    public Integer getSort_number() {
        return sort_number;
    }

    public void setSort_number(Integer sort_number) {
        this.sort_number = sort_number;
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

    public String getOrg_type_name() {
        return org_type_name;
    }

    public void setOrg_type_name(String org_type_name) {
        this.org_type_name = org_type_name;
    }

    public String getParent_org_name() {
        return parent_org_name;
    }

    public void setParent_org_name(String parent_org_name) {
        this.parent_org_name = parent_org_name;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
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

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }
}
