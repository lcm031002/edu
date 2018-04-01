package com.edu.erp.student.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.TStudentScoreDao;
import com.edu.erp.model.TStudentScore;
import com.edu.erp.student.service.TStudentScoreService;

@Service("tStudentScoreService")
public class TStudentScoreServiceImpl implements TStudentScoreService {

    @Resource (name = "tStudentScoreDao")
    private TStudentScoreDao tStudentScoreDao;
    
    @Override
    public List<TStudentScore> queryByApplyId(Long applyId) throws Exception {
        return this.tStudentScoreDao.queryByApplyId(applyId);
    }

    @Override
    public void save(TStudentScore tStudentScore) throws Exception {
        Assert.notNull(tStudentScore.getApplyId(), "排课申请单编号为空，新增失败！");
        if (isStuScoreExisted(tStudentScore)) {
            throw new Exception("学生科目成绩已存在，新增失败！");
        }
        this.tStudentScoreDao.save(tStudentScore);
    }

    @Override
    public void saveList(List<TStudentScore> tStudentScoreList) throws Exception {
        this.tStudentScoreDao.saveList(tStudentScoreList);
    }

    @Override
    public void update(TStudentScore tStudentScore) throws Exception {
        Assert.notNull(tStudentScore.getId(), "学生科目考试成绩编号为空，更新失败！");
        Assert.notNull(tStudentScore.getApplyId(), "排课申请单编号为空，更新失败！");
        if (isStuScoreExisted(tStudentScore)) {
            throw new Exception("学生科目成绩已存在，更新失败！");
        }
        this.tStudentScoreDao.update(tStudentScore);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tStudentScoreDao.deleteById(id);
    }

    @Override
    public void deleteByApplyId(Long applyId) throws Exception {
        this.tStudentScoreDao.deleteByApplyId(applyId);
    }
    
    private boolean isStuScoreExisted(TStudentScore tStudentScore) throws Exception {
        Integer count = this.tStudentScoreDao.isStuScoreExisted(tStudentScore);
        return count > 0;
    }

}
