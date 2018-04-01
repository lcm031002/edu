package com.edu.erp.student.service.impl;

import com.edu.erp.dao.TabStudentScoreDao;
import com.edu.erp.model.TabStudentScore;
import com.edu.erp.student.service.TabStudentScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("tabStudentScoreService")
public class TabStudentScoreServiceImpl implements TabStudentScoreService {

    @Resource(name = "tabStudentScoreDao")
    private TabStudentScoreDao tabStudentScoreDao;

    @Override
    public List<TabStudentScore> queryByStuScoreInfoId(Long stuScoreInfoId) throws Exception {
        return this.tabStudentScoreDao.queryByStuScoreInfoId(stuScoreInfoId);
    }

    @Override
    public void save(TabStudentScore tabStudentScore) throws Exception {
        this.tabStudentScoreDao.save(tabStudentScore);
    }

    @Override
    public void saveList(List<TabStudentScore> tabStudentScoreList) throws Exception {
        this.tabStudentScoreDao.saveList(tabStudentScoreList);
    }

    @Override
    public void update(TabStudentScore tabStudentScore) throws Exception {
        this.tabStudentScoreDao.update(tabStudentScore);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tabStudentScoreDao.deleteById(id);
    }

    @Override
    public void deleteByStuScoreInfoId(Long stuScoreInfoId) throws Exception {
        this.tabStudentScoreDao.deleteByStuScoreInfoId(stuScoreInfoId);
    }
}
