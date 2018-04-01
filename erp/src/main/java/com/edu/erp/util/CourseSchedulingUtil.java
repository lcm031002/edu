package com.edu.erp.util;

import com.edu.erp.course_manager.business.CourseTimeDate;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by zenglw on 2018/1/29.
 */
public class CourseSchedulingUtil {

    private static final String subPattern1 = "^[1-7]";//不指定上下课时间例如：1
    private static final String subPattern2 = "^[1-7]~(([0-1][0-9])|(2[0-3]))[:：]([0-5][0-9])[--](([0-1][0-9])|(2[0-3])):([0-5][0-9])";//3~16:00-18:00

    private static final Pattern regex1 = Pattern.compile(subPattern1);
    private static final Pattern regex2 = Pattern.compile(subPattern2);

    public static final String[] dateFormats = {"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd"};



    /**
     * 判断上课周期格式是否正确
     *
     * @param coursePeriod
     * @return
     */
    public static boolean isCoursePeriodLegal(String coursePeriod) {
        String[] splits = coursePeriod.split("[,，]");
        for (String split : splits) {
            if (!regex1.matcher(split).matches() && !regex2.matcher(split).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据{P#coursePeriod} 计算在{P#baseDate}后的下次上课时间.
     * Note:下次上课时间的范围不包括{P#baseDate}
     *
     * @param coursePeriod 上课周期
     * @param baseDate     计算下次上课时间的时间基线
     * @return CourseTimeDate
     */
    public static CourseTimeDate getNextCourseDate(String coursePeriod, Date baseDate) {
        if (!isCoursePeriodLegal(coursePeriod)) {
            throw new IllegalArgumentException("错误的上课周期格式:" + coursePeriod);
        }
        String[] splits = coursePeriod.split("[,，]");
        CourseTimeDate courseTimeDate = new CourseTimeDate();
        Calendar calendar = Calendar.getInstance();
        int loopCount = 0;
        while (loopCount < 7) {
            loopCount++;
            baseDate = DateUtils.addDays(baseDate, 1);
            calendar.setTime(baseDate);
            //获取baseDate是一周的第几天，周一为第一天
            int day = calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
            for (String split : splits) {
                if (split.equals(String.valueOf(day))) {//不知道上下课时间
                    courseTimeDate.setCourseTimeDate(baseDate);
                    return courseTimeDate;
                } else if (split.contains(day + "~")) {//指定上下课时间
                    String[] startEndTimeList = split.replace(day + "~", "").split("[--]");
                    courseTimeDate.setCourseTimeDate(baseDate);
                    courseTimeDate.setStartTime(startEndTimeList[0]);
                    courseTimeDate.setEndTime(startEndTimeList[1]);
                    return courseTimeDate;
                }
            }
        }
        return courseTimeDate;
    }
}
