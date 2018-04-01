package com.edu.erp.student.service.impl;

import com.edu.common.constants.Constants.SchedApplyType;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.TStudentRequirementDao;
import com.edu.erp.model.TStudentRequirement;
import com.edu.erp.student.service.TStudentRequirementService;

@Service("tStudentRequirementService")
public class TStudentRequirementServiceImpl implements TStudentRequirementService {
    
    @Resource(name = "tStudentRequirementDao")
    private TStudentRequirementDao tStudentRequirementDao;

    @Override
    public List<TStudentRequirement> queryByApplyId(Long applyId) throws Exception {
        return this.tStudentRequirementDao.queryByApplyId(applyId);
    }

    @Override
    public void save(TStudentRequirement tStudentReq) throws Exception {
        Assert.notNull(tStudentReq.getApplyId(), "排课申请单编号为空，更新失败！");
        if (SchedApplyType.CHANGE_APPLY.equals(tStudentReq.getApplyType()) && this.tStudentRequirementDao.hasStuReq(tStudentReq.getApplyId()) > 0) {
            throw new Exception("换单申请单只能申请一个科目，添加失败！");
        }

        if (isStuReqExisted(tStudentReq)) {
            throw new Exception("该课程已规划，添加失败！");
        }
        if (isSeqExisted(tStudentReq)) {
            throw new Exception("该课程排序和其他课程重复，添加失败！");
        }
        this.tStudentRequirementDao.save(tStudentReq);
    }

    @Override
    public void saveList(List<TStudentRequirement> tStudentReqList) throws Exception {
        this.tStudentRequirementDao.saveList(tStudentReqList);
    }

    @Override
    public void update(TStudentRequirement tStudentReq) throws Exception {
        Assert.notNull(tStudentReq.getId(), "课程规划编号为空，更新失败！");
        Assert.notNull(tStudentReq.getApplyId(), "排课申请单编号为空，更新失败！");
        if (isStuReqExisted(tStudentReq)) {
            throw new Exception("该课程已规划，更新失败！");
        }
        if (isSeqExisted(tStudentReq)) {
            throw new Exception("该课程排序和其他课程重复，更新失败！");
        }
        this.tStudentRequirementDao.update(tStudentReq);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tStudentRequirementDao.deleteById(id);
    }

    @Override
    public void deleteByApplyId(Long applyId) throws Exception {
        this.tStudentRequirementDao.deleteByApplyId(applyId);
    }

    /**
     * 课程规划科目是否重复
     * @param tStudentReq 课程规划信息
     * @return 大于0-重复 等于0-没重复
     * @throws Exception
     */
    private boolean isStuReqExisted(TStudentRequirement tStudentReq) throws Exception {
        Integer count = this.tStudentRequirementDao.isStuReqExisted(tStudentReq);
        return count > 0;
    }

    /**
     * 课程规划科目序号是否重复
     * @param tStudentReq 课程规划信息
     * @return 大于0-重复 等于0-没重复
     * @throws Exception
     */
    private boolean isSeqExisted(TStudentRequirement tStudentReq) throws Exception {
        Integer count = this.tStudentRequirementDao.isSeqExisted(tStudentReq);
        return count > 0;
    }
}
