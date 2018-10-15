package com.edu.erp.attendance.business;

public enum EnumAttendType {

    WX(-1, "无效"),
    BJK_CS(10, "班级课考勤初始状态"),
    BJK_GQ(11, "班级课挂起"),
    BJK_ZCSK(12, "班级课正常上课"),
    YDY_CS(20, "1对1考勤初始状态"),
    YDY_ZCSK(21, "1对1正常上课"),
    YDY_WGSK(22, "1对1违规上课"),
    YDY_QX(23, "1对1排课取消"),
    YDY_XSKK(24, "1对1学生旷课老师到"),
    YDY_LSKG(25, "1对1老师旷工学生到"),
    YDY_SG_LSWD(26, "1对1学管事故-学生到老师未到"),
    YDY_SG_LSD(27, "1对1学管事故-学生未到老师到"),
    YDY_WKQ(28, "1对1未考勤"),
    YDY_ZF(29, "1对1考勤作废"),
    WFD_CS(30, "晚辅导考勤初始状态"),
    WFD_ZCSK(31, "晚辅导正常上课");

    private long code;
    private String desc;

    EnumAttendType(long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EnumAttendType of(long code) {
        final EnumAttendType[] values = EnumAttendType.values();
        for (EnumAttendType value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new RuntimeException("不存在的考勤类型：" + code);
    }

    public long getCode() {
        return code;
    }
}