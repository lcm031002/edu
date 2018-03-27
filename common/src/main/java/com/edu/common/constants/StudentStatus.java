package com.edu.common.constants;

/**
 * Created by zenglw on 2017/12/21.
 */
public enum StudentStatus {

    IN_READING(3,"在读");

    private Integer value;
    private String name;

    StudentStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
