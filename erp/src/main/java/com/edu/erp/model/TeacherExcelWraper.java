package com.edu.erp.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @Description 教师Excel行包装类
 * @Author zenglw
 */
public class TeacherExcelWraper {

    private TeacherExcel teacherExcel;

    private ExcelHtmlRow excelHtmlRow;

    private TeacherExcelCheckHelperForWrap dataHelper;

    public TeacherExcelWraper(TeacherExcel teacherExcel, TeacherExcelCheckHelperForWrap dataHelper) {
        this.teacherExcel = teacherExcel;
        this.dataHelper = dataHelper;
    }

    public ExcelHtmlRow wrapFromExcel() {

        ExcelHtmlRow excelHtmlRow = new ExcelHtmlRow();
        try {
            Method[] methods = this.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().contains("wrap") && !method.getName().equals("wrapFromExcel")) {
                    excelHtmlRow.getCells().add((ExcelHtmlCell) method.invoke(this, null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        excelHtmlRow.sortCellsOfRow();
        return excelHtmlRow;
    }

    // --------------------封装属性到ExcelHtmlCell-----------------
    public ExcelHtmlCell wrapEmployeeEncodingAndId() {
        // 获取单元格对象
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(1);
        cell.setCellName("employee_encoding");
        cell.setDisplayValue(teacherExcel.getEmployee_encoding());
        if (StringUtils.isEmpty(teacherExcel.getEmployee_encoding())) {
            cell.setCheckErrorMessage("员工编码必填");
        } else {
            for (NameEncodingId nameEncodingId : dataHelper.getEmployeeNameEncodingId()) {
                if (nameEncodingId.getEncoding().equals(cell.getDisplayValue())) {
                    cell.setHoldValue(nameEncodingId.getId());
                    cell.setDisplayValue(nameEncodingId.getName());
                    break;
                }
            }
            if (cell.getHoldValue() == null) {
                cell.setCheckErrorMessage("员工不存在");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapEncoding() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(2);
        cell.setCellName("teacher_encoding");
        cell.setDisplayValue(teacherExcel.getTeacher_encoding());
        if (StringUtils.isEmpty(teacherExcel.getTeacher_encoding())) {
            cell.setCheckErrorMessage("教师编码必填");
        } else {
            cell.setHoldValue(teacherExcel.getTeacher_encoding());
            if (dataHelper.getExistEncodings().contains(teacherExcel.getTeacher_encoding())) {
                cell.setCheckErrorMessage("系统已存在该教师编码");
            } else if (dataHelper.getRepeatEncodings().contains(teacherExcel.getTeacher_encoding())) {
                cell.setCheckErrorMessage("文档中已存在该教师编码");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapTeacherName() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(3);
        cell.setCellName("teacher_name");
        cell.setDisplayValue(teacherExcel.getTeacher_name());
        cell.setHoldValue(teacherExcel.getTeacher_name());
        if (StringUtils.isEmpty(teacherExcel.getTeacher_name())) {
            cell.setCheckErrorMessage("教师名称必填");
        }
        return cell;
    }

    public ExcelHtmlCell wrapNickname() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(4);
        cell.setCellName("nickname");
        cell.setDisplayValue(teacherExcel.getNickname());
        cell.setHoldValue(teacherExcel.getNickname());
        return cell;
    }

    public ExcelHtmlCell wrapTeacher_age() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(5);
        cell.setCellName("teacher_age");
        cell.setDisplayValue(teacherExcel.getTeacher_age() == null ? null : String.valueOf(teacherExcel.getTeacher_age()));
        cell.setHoldValue(teacherExcel.getTeacher_age());
        if (teacherExcel.getTeacher_age() == null) {
            cell.setCheckErrorMessage("教师年龄必填");
        }
        return cell;
    }

    public ExcelHtmlCell wrapSeniority() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(6);
        cell.setCellName("seniority");
        cell.setDisplayValue(teacherExcel.getSeniority() == null ? null : String.valueOf(teacherExcel.getSeniority()));
        cell.setHoldValue(teacherExcel.getSeniority());
        if (teacherExcel.getSeniority() == null) {
            cell.setCheckErrorMessage("教师教龄必填");
        }
        return cell;
    }

    public ExcelHtmlCell wrapTeacher_type() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(7);
        cell.setCellName("teacher_type");
        String teacher_type = teacherExcel.getTeacher_type();
        cell.setDisplayValue(teacher_type);
        if (StringUtils.isEmpty(teacherExcel.getTeacher_type())) {
            cell.setCheckErrorMessage("教师类型必填");
        } else {
            Integer var = TeacherType.getValueByName(teacher_type);
            cell.setHoldValue(var);
            if (var == null) {
                cell.setCheckErrorMessage("不支持的教师状态");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapStatus() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(8);
        cell.setCellName("status");
        String status = teacherExcel.getStatus();
        cell.setDisplayValue(status);
        if (StringUtils.isEmpty(status)) {
            cell.setCheckErrorMessage("教师状态必填");
        } else {
            Integer var = TeacherStatus.getValueByName(status);
            cell.setHoldValue(var);
            if (var == null) {
                cell.setCheckErrorMessage("不支持的教师状态");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapSex() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(9);
        cell.setCellName("sex");
        String sex = teacherExcel.getSex();
        cell.setDisplayValue(sex);
        if (StringUtils.isEmpty(sex)) {
            cell.setCheckErrorMessage("性别必填");
        } else {
            Integer var = Sex.getValueByName(sex);
            cell.setHoldValue(var);
            if (var == null) {
                cell.setCheckErrorMessage("不支持的教师性别");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapPhone() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(10);
        cell.setCellName("phone");
        cell.setDisplayValue(teacherExcel.getPhone());
        cell.setHoldValue(teacherExcel.getPhone());
        if (StringUtils.isEmpty(teacherExcel.getPhone())) {
            cell.setCheckErrorMessage("手机号码必填");
        } else {
            String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(teacherExcel.getPhone());
            if (!m.matches()) {
                cell.setCheckErrorMessage("手机号码格式错误");
            } else {
                //校验该地区是否已经存在手机号码
                //是否在归属city已经存在电话号码
                if (dataHelper.getExistPhones().contains(teacherExcel.getPhone())) {
                    cell.setCheckErrorMessage("该地区已存在该电话号码");
                } else if (dataHelper.getRepeatPhones().contains(teacherExcel.getPhone())) {
                    cell.setCheckErrorMessage("文档中该地区已存在该电话号码");
                }
            }
        }
        return cell;
    }


    public ExcelHtmlCell wrapIs_pluralistic() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(11);
        cell.setCellName("is_pluralistic");
        String var0 = teacherExcel.getIs_pluralistic();
        cell.setDisplayValue(var0);
        if (StringUtils.isEmpty(var0)) {
            cell.setCheckErrorMessage("是否兼职必填");
        } else {
            Integer var = PartTime.getValueByName(var0);
            cell.setHoldValue(var);
            if (var == null) {
                cell.setCheckErrorMessage("不支持的兼职类型");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapTeacherBuId() {
        // 获取单元格对象
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(12);
        cell.setCellName("bu");
        cell.setDisplayValue(teacherExcel.getBu_name());
        if (StringUtils.isEmpty(teacherExcel.getBu_name())) {
            cell.setCheckErrorMessage("团队必填");
        } else {
            for (OrganizationInfo org : dataHelper.getBuList()) {
                if (org.getOrg_name().equals(cell.getDisplayValue())) {
                    cell.setHoldValue(org.getId());
                    cell.setDisplayValue(org.getOrg_name());
                    break;
                }
            }
            if (cell.getHoldValue() == null) {
                cell.setCheckErrorMessage("团队不存在");
            }
        }
        return cell;
    }

    public ExcelHtmlCell wrapSubject_names() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(13);
        cell.setCellName("subject_names");
        String subject_names = teacherExcel.getSubject_names();
        if (StringUtils.isEmpty(subject_names)) {
            cell.setCheckErrorMessage("科目必填");
        } else {
            String[] split = subject_names.split("[,，]");
            //List<String> subject_namesNew = new ArrayList<>();
            List<String> errorSubject = new ArrayList<>();
            List<Long> subject_ids = new ArrayList<>();
            Long buId = null;
            for (OrganizationInfo org : dataHelper.getBuList()) {
                if (org.getOrg_name().equals(teacherExcel.getBu_name())) {
                    buId = org.getId();
                    break;
                }
            }
            if (buId != null) {
                boolean checked = false;
                for (String subject_name : split) {
                    checked = false;
                    for (TPSubject subject : dataHelper.getSubjectList()) {
                        if (subject.getName().equals(subject_name) && subject.getBuId().equals(buId)) {
                            //subject_namesNew.add(subject_name);
                            subject_ids.add(subject.getId());
                            checked = true;
                            break;
                        }
                    }
                     if(!checked) {
                        //科目存在异常
                        errorSubject.add(subject_name);
                    }
                }
                cell.setCheckErrorMessage("存在错误的科目有：" +  StringUtils.join(errorSubject, ","));
            } else {
                //团队信息错误
                cell.setCheckErrorMessage("团队信息错误");
            }
            cell.setDisplayValue(subject_names);
            cell.setHoldValue(StringUtils.join(subject_ids, ","));
        }
        return cell;
    }

    public ExcelHtmlCell wrapEmail() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(14);
        cell.setCellName("email");
        cell.setDisplayValue(teacherExcel.getEmail());
        cell.setHoldValue(teacherExcel.getEmail());
        return cell;
    }

    public ExcelHtmlCell wrapDescription() {
        ExcelHtmlCell cell = new ExcelHtmlCell<>();
        cell.setOrder(15);
        cell.setCellName("description");
        cell.setDisplayValue(teacherExcel.getDescription());
        cell.setHoldValue(teacherExcel.getDescription());
        return cell;
    }

    public TeacherExcel getTeacherExcel() {
        return teacherExcel;
    }

    public void setTeacherExcel(TeacherExcel teacherExcel) {
        this.teacherExcel = teacherExcel;
    }

    public ExcelHtmlRow getExcelHtmlRow() {
        if (excelHtmlRow == null) {
            excelHtmlRow = this.wrapFromExcel();
        }
        return excelHtmlRow;
    }

    public void setExcelHtmlRow(ExcelHtmlRow excelHtmlRow) {
        this.excelHtmlRow = excelHtmlRow;
    }

    public TeacherExcelCheckHelperForWrap getDataHelper() {
        return dataHelper;
    }

    public void setDataHelper(TeacherExcelCheckHelperForWrap dataHelper) {
        this.dataHelper = dataHelper;
    }

    /**
     * 教师类型
     *
     * @author zenglw
     */
    private enum TeacherType {

        ORDINARY(1, "普通教师"), MAIN(2, "主讲教师"), ASSIT_DOUBLE(2, "辅导老师-双师"), ASSIT_PY(3, "辅导老师-培英");

        private Integer value;
        private String name;

        TeacherType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举名称查询枚举值
         *
         * @param name
         * @return
         */
        public static Integer getValueByName(String name) {
            TeacherType[] values = TeacherType.values();
            for (TeacherType var : values) {
                if (var.getName().equals(name)) {
                    return var.getValue();
                }
            }
            return null;
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

    /**
     * 教师状态
     *
     * @author zenglw
     */
    private enum TeacherStatus {

        TRY(1, "试用员工"), FORMAL(2, "正式员工"), BACK(3, "返聘");

        private Integer value;
        private String name;

        TeacherStatus(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举名称查询枚举值
         *
         * @param name
         * @return
         */
        public static Integer getValueByName(String name) {
            TeacherStatus[] values = TeacherStatus.values();
            for (TeacherStatus var : values) {
                if (var.getName().equals(name)) {
                    return var.getValue();
                }
            }
            return null;
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

    /**
     * 性别枚举
     *
     * @author zenglw
     */
    private enum Sex {

        YES(1, "男"), NO(2, "女");

        private Integer value;
        private String name;

        Sex(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举名称查询枚举值
         *
         * @param name
         * @return
         */
        public static Integer getValueByName(String name) {
            Sex[] values = Sex.values();
            for (Sex var : values) {
                if (var.getName().equals(name)) {
                    return var.getValue();
                }
            }
            return null;
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

    /**
     * 是否兼职枚举
     *
     * @author zenglw
     */
    private enum PartTime {

        YES(1, "是"), NO(0, "否");

        private Integer value;
        private String name;

        PartTime(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举名称查询枚举值
         *
         * @param name
         * @return
         */
        public static Integer getValueByName(String name) {
            PartTime[] values = PartTime.values();
            for (PartTime partTime : values) {
                if (partTime.getName().equals(name)) {
                    return partTime.getValue();
                }
            }
            return null;
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
}
