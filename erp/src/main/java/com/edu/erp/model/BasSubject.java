package com.edu.erp.model;

public class BasSubject extends BaseObject {

    // 科目编码
    private String encoding;

    // 科目名称
    private String subjectName;

    // 描述
    private String description;

    /**
     * 设置 科目编码,对应字段 t_bas_subject.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 科目编码,对应字段 t_bas_subject.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 科目名称,对应字段 t_bas_subject.subject_name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * 获取 科目名称,对应字段 t_bas_subject.subject_name
     */
    public String getSubjectName() {
        return this.subjectName;
    }

    /**
     * 设置 描述,对应字段 t_bas_subject.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 t_bas_subject.description
     */
    public String getDescription() {
        return this.description;
    }
}