package com.edu.erp.model;

public class DataGrade extends BaseObject {

    // 年级编码
    private String encoding;

    // 年级名称
    private String gradeName;

    // 上一级年级编码
    private Long lastId;

    // 上一级年级编码
    private String lastEncoding;

    // 上一级年级名称
    private String lastGradeName;

    // 描述
    private String description;

    // 排序
    private Integer sort;

    /**
     * 设置 年级编码,对应字段 tab_data_grade.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 年级编码,对应字段 tab_data_grade.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 年级名称,对应字段 tab_data_grade.grade_name
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * 获取 年级名称,对应字段 tab_data_grade.grade_name
     */
    public String getGradeName() {
        return this.gradeName;
    }

    /**
     * 设置 上一级年级编码,对应字段 tab_data_grade.last_id
     */
    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    /**
     * 获取 上一级年级编码,对应字段 tab_data_grade.last_id
     */
    public Long getLastId() {
        return this.lastId;
    }

    /**
     * 设置 上一级年级编码,对应字段 tab_data_grade.last_encoding
     */
    public void setLastEncoding(String lastEncoding) {
        this.lastEncoding = lastEncoding;
    }

    /**
     * 获取 上一级年级编码,对应字段 tab_data_grade.last_encoding
     */
    public String getLastEncoding() {
        return this.lastEncoding;
    }

    /**
     * 设置 上一级年级名称,对应字段 tab_data_grade.last_grade_name
     */
    public void setLastGradeName(String lastGradeName) {
        this.lastGradeName = lastGradeName;
    }

    /**
     * 获取 上一级年级名称,对应字段 tab_data_grade.last_grade_name
     */
    public String getLastGradeName() {
        return this.lastGradeName;
    }

    /**
     * 设置 描述,对应字段 tab_data_grade.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 tab_data_grade.description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置 排序,对应字段 tab_data_grade.sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取 排序,对应字段 tab_data_grade.sort
     */
    public Integer getSort() {
        return this.sort;
    }
}