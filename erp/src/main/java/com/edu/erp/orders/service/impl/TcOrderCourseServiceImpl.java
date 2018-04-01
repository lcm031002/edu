package com.edu.erp.orders.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.edu.erp.dao.TCOrderCourseDao;
import com.edu.erp.dao.TLockDao;
import com.edu.erp.model.TCOrderCourse;
import com.edu.erp.model.TLock;
import com.edu.erp.model.TabChangeCourse;
import com.edu.erp.orders.service.ChangeCourseService;
import com.edu.erp.orders.service.OrderCourseTimesInfoService;
import com.edu.erp.orders.service.TcOrderCourseService;

@Service(value = "tcOrderCourseService")
public class TcOrderCourseServiceImpl implements TcOrderCourseService {
    @Resource(name = "changeCourseService")
    private ChangeCourseService changeCourseService;

    @Resource(name = "orderCourseTimesInfoService")
    private OrderCourseTimesInfoService orderCourseTimesInfoService;

    @Resource(name = "tCOrderCourseDao")
    private TCOrderCourseDao tcOrderCourseDao;
    
    @Resource(name = "tLockDao")
    private TLockDao tLockDao;

    @Override
    public void addByChangeNo(String changeNo) throws Exception {
        List<TabChangeCourse> changeCourseList = this.changeCourseService.queryByChangeNo(changeNo);
        if (!CollectionUtils.isEmpty(changeCourseList)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            TCOrderCourse tcOrderCourse = null;
            for (TabChangeCourse changeCourse : changeCourseList) {
                tcOrderCourse = new TCOrderCourse();
                tcOrderCourse.setChange_id(changeCourse.getChange_id());
                tcOrderCourse.setOrder_id(changeCourse.getOrder_id());
                tcOrderCourse.setOrder_course_id(changeCourse.getOrder_course_id());
                tcOrderCourse.setCourse_times(changeCourse.getCourse_times() == null ? 0L : changeCourse.getCourse_times().longValue());
                tcOrderCourse.setTotal_amount(changeCourse.getTotal_amount());
                tcOrderCourse.setAttend_amount(changeCourse.getAttend_amount());
                tcOrderCourse.setPre_amount(changeCourse.getPre_amount());
                this.tcOrderCourseDao.saveTcOrderCourse(tcOrderCourse);

                paramMap.put("tcOrderCourseId", tcOrderCourse.getId());
                paramMap.put("changeNo", changeNo);
                paramMap.put("changeCourseId", changeCourse.getId());
                paramMap.put("changeId", changeCourse.getChange_id());
                paramMap.put("orderId", changeCourse.getOrder_id());
                paramMap.put("orderCourseId", changeCourse.getOrder_course_id());
                this.tcOrderCourseDao.saveTcOrderCourseTimesByChangeNo(paramMap);
            }
        }
    }

    @Override
    public void checkOrderChange(Long orderCourseId) throws Exception {
        Integer courseLockCount = this.tcOrderCourseDao.queryCourseLockCount(orderCourseId);
        if (courseLockCount > 0) {
            throw new Exception("本次退费课程或联报课程存在被锁定的课程，不允许退费");
        }

        Integer courseTimesLockCount = this.tcOrderCourseDao.queryCourseTimesLockCount(orderCourseId);
        if (courseTimesLockCount > 0) {
            throw new Exception("本次退费所退课次存在被锁定的课次，不允许退费");
        }
    }

    @Override
    public void addLock(Long orderChangeId, Long orderCourseId) throws Exception {
        checkOrderChange(orderCourseId);

        TLock tLock = new TLock();
        tLock.setResourceId(orderCourseId);
        tLock.setBusiId(orderChangeId);
        this.tLockDao.saveLock(tLock);
    }

    /**
     * 课程批改校验
     * 1. 校验课程批改课时是否大于剩余课时
     * 2. 校验批改课次是否已考勤或者被挂起
     * @param orderChangeId
     * @throws Exception
     */
    @Override
    public void checkSurplusCountAndAttendStatus(Long orderChangeId) throws Exception {
        List<TCOrderCourse> tCOrderCourseList = tcOrderCourseDao.queryTcOrderCourseByChangeId(orderChangeId);
        if (!CollectionUtils.isEmpty(tCOrderCourseList)) {
            StringBuilder errMsgBuilder = new StringBuilder();
            StringBuilder timesErrMsgBuilder = new StringBuilder();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            for (TCOrderCourse tCOrderCourse : tCOrderCourseList) {
                if (tCOrderCourse.getCourse_times() > tCOrderCourse.getCourse_surplus_count()) {
                    errMsgBuilder.append("课程").append(tCOrderCourse.getCourse_name()).append("本次所退次数(")
                            .append(tCOrderCourse.getCourse_times()).append(")大于剩余次数(")
                            .append(tCOrderCourse.getCourse_surplus_count()).append(").");
                }

                paramMap.clear();
                paramMap.put("change_course_id", tCOrderCourse.getId());
                // 校验课次考勤状态
                List<Map<String, Object>> courseTimesList = orderCourseTimesInfoService
                        .queryCourseTimesAttendType(paramMap);
                if (!CollectionUtils.isEmpty(courseTimesList)) {
                    for (Map<String, Object> courseTimes : courseTimesList) {
                        timesErrMsgBuilder.append(",").append(String.valueOf(courseTimes.get("course_times")));
                    }
                    if (timesErrMsgBuilder.length() > 0) {
                        errMsgBuilder.append("课程").append(tCOrderCourse.getCourse_name()).append("本次所退课次(")
                                .append(timesErrMsgBuilder.substring(1)).append(")已考勤或被挂起,不可进行退费操作.");
                    }
                }
            }

            if (errMsgBuilder.length() > 0) {
                throw new Exception(errMsgBuilder.toString());
            }
        }
    }

}
