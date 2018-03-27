package com.edu.common.constants;

/**
 * Created by zenglw on 2017/10/22.
 */
public enum ProductLine {

    JIA_YIN(11L,"佳音"),PEI_YIN(1L,"培英精品班"),WAN_FU(3L,"晚辅导"),GE_XING(4L,"个性化");

    private Long id;
    private String name;

    private ProductLine(Long id,String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
