package com.edu.erp.model;

import java.util.Date;

/**
 * 摄像机模型
 *
 * @author: linj
 * @create: 2017/12/8  10:46
 */
public class CameraInfo extends BaseObject{



    private Long room_id;

    private String room_name;

    private String room_code;

    private Long bu_id;

    private Long branch_id;

    private String branch_name;

    private String camera_code;   //摄像机编码

    private String channel_id;    //频道id

    private String pf_address;    //拉流地址

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public Long getBu_id() {
        return bu_id;
    }

    public void setBu_id(Long bu_id) {
        this.bu_id = bu_id;
    }

    public Long getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Long branch_id) {
        this.branch_id = branch_id;
    }

    public String getCamera_code() {
        return camera_code;
    }

    public void setCamera_code(String camera_code) {
        this.camera_code = camera_code;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getPf_address() {
        return pf_address;
    }

    public void setPf_address(String pf_address) {
        this.pf_address = pf_address;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}
