package com.edu.erp.orders.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.dao.TabChangeCourseDao;
import com.edu.erp.model.TabChangeCourse;
import com.edu.erp.orders.service.ChangeCourseService;

@Service(value = "changeCourseService")
public class ChangeCourseServiceImpl implements ChangeCourseService {
    @Resource(name = "tabChangeCourseDao")
    private TabChangeCourseDao tabChangeCourseDao;
    
    @Override
    public void add(TabChangeCourse tabChangeCourse) throws Exception {
        this.tabChangeCourseDao.add(tabChangeCourse);
    }

    @Override
    public void update(TabChangeCourse tabChangeCourse) throws Exception {
        this.tabChangeCourseDao.update(tabChangeCourse);
    }

    @Override
    public void updateByChangeNo(TabChangeCourse tabChangeCourse) throws Exception {
        this.tabChangeCourseDao.updateByChangeNo(tabChangeCourse);
    }

    @Override
    public List<TabChangeCourse> queryByChangeNo(String changeNo) throws Exception {
        return this.tabChangeCourseDao.queryByChangeNo(changeNo);
    }

}
