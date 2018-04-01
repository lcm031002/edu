package com.edu.erp.student.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.TStudentScheduleDao;
import com.edu.erp.model.TStudentSchedule;
import com.edu.erp.student.service.TStudentScheduleService;

@Service("tStudentScheduleService")
public class TStudentScheduleServiceImpl implements TStudentScheduleService {

    @Resource(name = "tStudentScheduleDao")
    private TStudentScheduleDao tStudentScheduleDao;

    @Override
    public List<TStudentSchedule> queryByApplyId(Long applyId) throws Exception {
        return this.tStudentScheduleDao.queryByApplyId(applyId);
    }

    @Override
    public void save(TStudentSchedule tStudentSchedule) throws Exception {
        Assert.notNull(tStudentSchedule.getApplyId(), "排课申请单编号为空，新增失败！");
        Assert.notNull(tStudentSchedule.getSchedule(), "学员档期信息为空，新增失败！");
        this.tStudentScheduleDao.save(tStudentSchedule);
    }

    @Override
    public void saveList(List<TStudentSchedule> tStudentScheduleList) throws Exception {
        this.tStudentScheduleDao.saveList(tStudentScheduleList);
    }

    @Override
    public void update(TStudentSchedule tStudentSchedule) throws Exception {
        Assert.notNull(tStudentSchedule.getId(), "学生档期编号为空，更新失败！");
        Assert.notNull(tStudentSchedule.getApplyId(), "排课申请单编号为空，更新失败！");
        Assert.notNull(tStudentSchedule.getSchedule(), "学员档期信息为空，更新失败！");
        this.tStudentScheduleDao.update(tStudentSchedule);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tStudentScheduleDao.deleteById(id);
    }

    @Override
    public void deleteByApplyId(Long applyId) throws Exception {
        this.tStudentScheduleDao.deleteByApplyId(applyId);
    }

}
