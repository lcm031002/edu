package com.edu.erp.student.service.impl;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.StudentManagerDao;
import com.edu.erp.model.StudentManagerAnalysis;
import com.edu.erp.student.service.StudentManagerService;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("studentManagerService")
public class StudentManagerServiceImpl implements StudentManagerService {

    @Resource(name = "studentManagerDao")
    private StudentManagerDao studentManagerDao;

    @Override
    public List<StudentManagerAnalysis> queryBuAnalysis(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap.get("bu_id"), "请选择一个团队！");
        return this.studentManagerDao.queryBuAnalysis(paramMap);
    }

    @Override
    public List<StudentManagerAnalysis> queryBranchAnalysis(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap.get("bu_id"), "请选择一个团队！");
        return this.studentManagerDao.queryBranchAnalysis(paramMap);
    }

    @Override
    public List<StudentManagerAnalysis> queryStuMgrAnalysis(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap.get("bu_id"), "请选择一个团队！");
        Assert.notNull(paramMap.get("branch_id"), "请选择一个业务校区！");
        return this.studentManagerDao.queryStuMgrAnalysis(paramMap);
    }

    @Override
    public List<StudentManagerAnalysis> queryStudentAnalysis(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap.get("bu_id"), "请选择一个团队！");
        Assert.notNull(paramMap.get("branch_id"), "请选择一个业务校区！");
        Assert.notNull(paramMap.get("studentManagerId"), "请选择一个学管师！");
        return this.studentManagerDao.queryStudentAnalysis(paramMap);
    }

    @Override
    public Page<Map<String, Object>> queryYdyCourseSchedInfo(Map<String, Object> paramMap) throws Exception {
        if (paramMap.get("startDate") != null) {
            paramMap.put("startDate", DateUtil.stringDateToLong(paramMap.get("startDate").toString()));
        }
        if (paramMap.get("endDate") != null) {
            paramMap.put("endDate", DateUtil.stringDateToLong(paramMap.get("endDate").toString()));
        }
        return this.studentManagerDao.queryYdyCourseSchedInfo(paramMap);
    }
}
