package com.edu.erp.orders.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.TabChangeCourseTimesDao;
import com.edu.erp.model.TabChangeCourseTimes;
import com.edu.erp.orders.service.ChangeCourseTimesService;

@Service(value = "changeCourseTimesService")
public class ChangeCourseTimesServiceImpl implements ChangeCourseTimesService {
    @Resource(name = "tabChangeCourseTimesDao")
    private TabChangeCourseTimesDao tabChangeCourseTimesDao;

    @Override
    public void add(Long changeCourseId, String refundCourseTimes, String changeNo) throws Exception {
        if (StringUtils.isNotEmpty(refundCourseTimes)) {
            String[] refundCourseTimesArray = refundCourseTimes.split(",");
            List<TabChangeCourseTimes> changeCourseTimesList = new ArrayList<TabChangeCourseTimes>();
            TabChangeCourseTimes changeCourseTimes = null;
            for (String refundCourseTime : refundCourseTimesArray) {
                changeCourseTimes = new TabChangeCourseTimes();
                changeCourseTimes.setChange_course_id(changeCourseId);
                changeCourseTimes.setChange_no(changeNo);
                changeCourseTimes.setCourse_times(Integer.parseInt(refundCourseTime));
                changeCourseTimesList.add(changeCourseTimes);
            }
            this.tabChangeCourseTimesDao.addList(changeCourseTimesList);
        }
    }

    @Override
    public Integer queryChangeCourseTimes(Map<String, Object> paramMap) throws Exception {
        return this.tabChangeCourseTimesDao.queryChangeCourseTimes(paramMap);
    }

    @Override
    public void updateByChangeNo(TabChangeCourseTimes tabChangeCourseTimes) throws Exception {
        this.tabChangeCourseTimesDao.updateByChangeNo(tabChangeCourseTimes);
        
    }

}
