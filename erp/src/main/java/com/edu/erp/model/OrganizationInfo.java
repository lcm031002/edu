package com.edu.erp.model;

public class OrganizationInfo extends BaseObject {

    private static final long serialVersionUID = 1L;

    // 上级组织
    private Long parent_id;
    // 产品线
    private Long product_line;
    // 组织名称
    private String org_name;
    //简称
    private String shortOrgName;
    //组织id
    private String buId;
    // 组织类型
    private Integer org_type;/* 1：地区级别 2：部门级别 3：团队级别 4：校区级别 */
    private Integer sort_num;
    // 地址
    private String address;

    private String org_type_name;

    private String parent_org_name;

    // logo
    private String logo;

    // 邮箱
    private String email;

    // 电话
    private String phone;

    // 校区类型
    private Integer orgKind;


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

    public Integer getSort_num() {
        return sort_num;
    }

    public void setSort_num(Integer sort_num) {
        this.sort_num = sort_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
