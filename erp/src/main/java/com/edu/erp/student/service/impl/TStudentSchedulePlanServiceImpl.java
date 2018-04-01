package com.edu.erp.student.service.impl;

import com.edu.common.util.NumberUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.TStudentSchedulePlanDao;
import com.edu.erp.model.TStudentRequirement;
import com.edu.erp.model.TStudentSchedulePlan;
import com.edu.erp.student.service.TScheduleApplyInfoService;
import com.edu.erp.student.service.TStudentRequirementService;
import com.edu.erp.student.service.TStudentSchedulePlanService;

@Service("tStudentSchedulePlanService")
public class TStudentSchedulePlanServiceImpl implements TStudentSchedulePlanService {

    @Resource(name = "tStudentSchedulePlanDao")
    private TStudentSchedulePlanDao tStudentSchedulePlanDao;
    
    @Resource(name = "tStudentRequirementService")
    private TStudentRequirementService stuReqService;
    
    @Resource(name = "tScheduleApplyInfoService")
    private TScheduleApplyInfoService schedApplyInfoService;

    @Override
    public List<TStudentSchedulePlan> queryByApplyId(Long applyId) throws Exception {
        return this.tStudentSchedulePlanDao.queryByApplyId(applyId);
    }

    @Override
    public void save(TStudentSchedulePlan tStudentSchedulePlan) throws Exception {
        Assert.notNull(tStudentSchedulePlan.getApplyId(), "排课申请单编号为空，新增失败！");
        this.tStudentSchedulePlanDao.save(tStudentSchedulePlan);
    }

    @Override
    public void saveList(List<TStudentSchedulePlan> tStudentSchedulePlanList) throws Exception {
        this.tStudentSchedulePlanDao.saveList(tStudentSchedulePlanList);
    }
    
    @Override
    public void addByApplyId(Long applyId, Long userId) throws Exception {
        List<TStudentRequirement> stuReqList = this.stuReqService.queryByApplyId(applyId);
        if (CollectionUtils.isNotEmpty(stuReqList)) {
            this.deleteByApplyId(applyId);
            List<TStudentSchedulePlan> stuSchedPlanList = new ArrayList<TStudentSchedulePlan>();
            TStudentSchedulePlan stuSchedPlan = null;
            for (TStudentRequirement stuReq : stuReqList) {
                for (int i = 0; i < stuReq.getRequirement(); i++) {
                    stuSchedPlan = new TStudentSchedulePlan();
                    stuSchedPlan.setApplyId(applyId);
                    stuSchedPlan.setCreate_user(userId);
                    stuSchedPlan.setCreate_time(DateUtil.getCurrDateTime());
                    stuSchedPlan.setSubjectId(stuReq.getSubjectId());
                    stuSchedPlan.setSubjectName(stuReq.getSubjectName());
                    stuSchedPlan.setStatus(Constants.StuSchedPlanStatus.NOT_SCHEDULED);
                    stuSchedPlanList.add(stuSchedPlan);
                }
            }
            this.saveList(stuSchedPlanList);
        }
    }

    @Override
    public void update(TStudentSchedulePlan tStudentSchedulePlan) throws Exception {
        Assert.notNull(tStudentSchedulePlan.getId(), "初步排课意向编号为空，更新失败！");
        Assert.notNull(tStudentSchedulePlan.getApplyId(), "排课申请单编号为空，更新失败！");
        if (tStudentSchedulePlan.getCourseDate() != null && StringUtils.isNotEmpty(tStudentSchedulePlan.getStartTime())
                && StringUtils.isNotEmpty(tStudentSchedulePlan.getEndTime()) && isStuSchedPlanExisted(tStudentSchedulePlan)) {
            throw new Exception("该初步排课意向重复，更新失败！");
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("applyId", tStudentSchedulePlan.getApplyId());
        paramMap.put("planIds", tStudentSchedulePlan.getId());
        if (!checkBeforeUpdate(paramMap)) {
            throw new Exception("请按排课规划顺序进行处理！");
        }

        if (Constants.StuSchedPlanStatus.YA_DAN == tStudentSchedulePlan.getStatus().intValue()) { // 压单
            this.tStudentSchedulePlanDao.pressPlan(tStudentSchedulePlan);
        } else {
            this.tStudentSchedulePlanDao.update(tStudentSchedulePlan);
        }

        this.schedApplyInfoService.updateStatusAfterMatch(tStudentSchedulePlan.getApplyId());
    }
    
    @Override
    public void updateStatus(List<Map<String, Object>> tStudentSchedulePlanList) throws Exception {
        if (CollectionUtils.isEmpty(tStudentSchedulePlanList)) {
            throw new Exception("没有选择记录，处理失败！");
        }
        Long applyId = NumberUtils.object2Long(tStudentSchedulePlanList.get(0).get("applyId"));
        if (applyId == null) {
            throw new Exception("排课申请单编号为空，处理失败！");
        }

        StringBuilder planIdBuilder = new StringBuilder();
        for (Map<String, Object> stuSchedPlanMap : tStudentSchedulePlanList) {
            planIdBuilder.append(",").append(NumberUtils.object2Long(stuSchedPlanMap.get("id")));
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("applyId", applyId);
        paramMap.put("planIds", planIdBuilder.substring(1));
        if (!checkBeforeUpdate(paramMap)) {
            throw new Exception("请按排课规划顺序进行处理！");
        }
        this.tStudentSchedulePlanDao.updateStatus(tStudentSchedulePlanList);
        this.schedApplyInfoService.updateStatusAfterMatch(NumberUtils.object2Long(tStudentSchedulePlanList.get(0).get("applyId")));
    }

    private boolean checkBeforeUpdate(Map<String, Object> paramMap) throws Exception {
        return this.tStudentSchedulePlanDao.queryNeedMatchedPlanCount(paramMap).intValue() == 0;
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tStudentSchedulePlanDao.deleteById(id);
    }

    @Override
    public void deleteByApplyId(Long applyId) throws Exception {
        this.tStudentSchedulePlanDao.deleteByApplyId(applyId);
    }

    private boolean isStuSchedPlanExisted(TStudentSchedulePlan tStudentSchedulePlan) throws Exception {
        Integer count = this.tStudentSchedulePlanDao.isStuSchedPlanExisted(tStudentSchedulePlan);
        return count > 0;
    }
}
