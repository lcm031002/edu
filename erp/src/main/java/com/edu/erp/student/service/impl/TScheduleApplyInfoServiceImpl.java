package com.edu.erp.student.service.impl;

import com.edu.common.util.NumberUtils;
import com.edu.common.constants.Constants.SchedApplyAuditStatus;
import com.edu.common.constants.Constants.SchedApplyType;
import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.TScheduleApplyInfoDao;
import com.edu.erp.model.EmployeeInfo;
import com.edu.erp.model.TScheduleApplyInfo;
import com.edu.erp.model.TStudentSchedule;
import com.edu.erp.student.service.TScheduleApplyInfoService;
import com.edu.erp.student.service.TStudentRequirementService;
import com.edu.erp.student.service.TStudentSchedulePlanService;
import com.edu.erp.student.service.TStudentScheduleService;
import com.edu.erp.student.service.TStudentScoreService;
import com.github.pagehelper.Page;

@Service("tScheduleApplyInfoService")
public class TScheduleApplyInfoServiceImpl implements TScheduleApplyInfoService {
    @Resource(name = "tScheduleApplyInfoDao")
    private TScheduleApplyInfoDao schedApplyInfoDao;

    @Resource(name = "tStudentScoreService")
    private TStudentScoreService stuScoreService;

    @Resource(name = "tStudentRequirementService")
    private TStudentRequirementService stuReqService;

    @Resource(name = "tStudentScheduleService")
    private TStudentScheduleService stuSchedService;

    @Resource(name = "tStudentSchedulePlanService")
    private TStudentSchedulePlanService stuSchedPlanService;

    @Override
    public Page<TScheduleApplyInfo> queryForPage(Map<String, Object> paramMap) throws Exception {
        return this.schedApplyInfoDao.queryForPage(paramMap);
    }
    
    @Override
    public TScheduleApplyInfo queryDetail(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap.get("id"), "排课申请单编号不能为空！");
        TScheduleApplyInfo scheduleApplyInfo = this.schedApplyInfoDao.queryDetail(paramMap);
        Long applyId = NumberUtils.object2Long(paramMap.get("id"));
        scheduleApplyInfo.setStuSchedPlanList(this.stuSchedPlanService.queryByApplyId(applyId));
        return  scheduleApplyInfo;
    }

    @Override
    public TScheduleApplyInfo queryById(Long id) throws Exception {
        Assert.notNull(id, "排课申请单编号不能为空！");
        return this.schedApplyInfoDao.queryById(id);
    }

    @Override
    public TScheduleApplyInfo save(TScheduleApplyInfo schedApplyInfo) throws Exception {
        Assert.notNull(schedApplyInfo.getStudentId(), "没有选择学生，无法报班！");
        Integer count = this.schedApplyInfoDao.hasOtherApply(schedApplyInfo.getStudentId());
        if (count > 0) {
            throw new Exception("该学生存在草稿状态或者未匹配完成的申请单，不能再次申请！");
        }

        List<EmployeeInfo> empList = this.schedApplyInfoDao.queryCounselorInfo(schedApplyInfo.getStudentId());
        if (CollectionUtils.isEmpty(empList) || empList.size() < 2) {
            throw new Exception("学生没有绑定学管师、咨询师，请先到学员管理页面绑定！");
        }

        schedApplyInfo.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.SSA_PREFIX));
        for (EmployeeInfo emp : empList) {
            if ("1".equals(emp.getCounselor_type())) {
                schedApplyInfo.setCounselorId(emp.getId());
                schedApplyInfo.setCounselor(emp.getEmployee_name());
                schedApplyInfo.setCounselorPhone(emp.getPhone());
                schedApplyInfo.setCounselorLine(emp.getTel());
            } else if ("2".equals(emp.getCounselor_type())) {
                schedApplyInfo.setCourseAdminId(emp.getId());
                schedApplyInfo.setCourseAdmin(emp.getEmployee_name());
                schedApplyInfo.setCourseAdminPhone(emp.getPhone());
                schedApplyInfo.setCourseAdminLine(emp.getTel());
            }
        }

        TScheduleApplyInfo oldSchedApplyInfo = this.schedApplyInfoDao.queryLatestApply(schedApplyInfo);
        if (oldSchedApplyInfo != null) {
            schedApplyInfo.setStudentCharacter(oldSchedApplyInfo.getStudentCharacter());
            schedApplyInfo.setParentInfo(oldSchedApplyInfo.getParentInfo());
            schedApplyInfo.setStudentSituation(oldSchedApplyInfo.getStudentSituation());
            schedApplyInfo.setWorkDirections(oldSchedApplyInfo.getWorkDirections());
            schedApplyInfo.setWorkRemark(oldSchedApplyInfo.getWorkRemark());
        }

        schedApplyInfo.setStatus(Constants.SchedApplyStatus.DRAFT); // 草稿状态
        schedApplyInfo.setBeginDate(DateUtil.getCurrDateOfDate(DateUtil.DATE_FORMAT));
        this.schedApplyInfoDao.save(schedApplyInfo);
        return this.queryById(schedApplyInfo.getId());
    }

    @Override
    public void update(TScheduleApplyInfo schedApplyInfo) throws Exception {
        Assert.notNull(schedApplyInfo.getId(), "排课申请单编号为空，无法更新！");
        if (schedApplyInfo.getBeginDate() != null
                && schedApplyInfo.getBeginDate().compareTo(DateUtil.getCurrDateOfDate(DateUtil.DATE_FORMAT)) < 0) {
            throw new Exception("开课日期不能小于当前日期！");
        }

        if (SchedApplyType.CHANGE_APPLY.equals(schedApplyInfo.getApplyType()) && CollectionUtils.isNotEmpty(schedApplyInfo.getStuReqList()) && schedApplyInfo.getStuReqList().size() > 1) {
            throw new Exception("换单申请单只能申请一个科目！");
        }

        if (SchedApplyType.CHANGE_APPLY.equals(schedApplyInfo.getApplyType()) && StringUtils.isEmpty(schedApplyInfo.getAuditStatus())) {
            schedApplyInfo.setAuditStatus(SchedApplyAuditStatus.WAIT_AUDIT);
        }

        this.schedApplyInfoDao.update(schedApplyInfo);

        if (StringUtils.isNotEmpty(schedApplyInfo.getSchedule())) {
            this.stuSchedService.deleteByApplyId(schedApplyInfo.getId());
            TStudentSchedule stuSched = new TStudentSchedule();
            stuSched.setApplyId(schedApplyInfo.getId());
            stuSched.setSchedule(schedApplyInfo.getSchedule());
            this.stuSchedService.save(stuSched);
        }

        // 排课申请提交，根据课程规划生成初步排课意向
        if (schedApplyInfo.getStatus().intValue() == Constants.SchedApplyStatus.SUBMIT.intValue()) {
            this.stuSchedPlanService.addByApplyId(schedApplyInfo.getId(), schedApplyInfo.getUpdate_user());
        }
    }

    @Override
    public void updateStatus(TScheduleApplyInfo schedApplyInfo) throws Exception {
        this.schedApplyInfoDao.updateStatus(schedApplyInfo);
    }

    @Override
    public void auditScheduleApply(TScheduleApplyInfo scheduleApplyInfo) throws Exception {
        this.schedApplyInfoDao.auditScheduleApply(scheduleApplyInfo);
        if (SchedApplyAuditStatus.REJECT.equals(scheduleApplyInfo.getAuditStatus())) {
            updateStatusAfterApplyReject(scheduleApplyInfo.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws Exception {
        TScheduleApplyInfo schedApplyInfo = this.queryById(id);
        if (Constants.SchedApplyStatus.DRAFT != schedApplyInfo.getStatus().intValue() &&
                Constants.SchedApplyStatus.CANCELED != schedApplyInfo.getStatus().intValue()) {
            throw new Exception("排课申请单不是草稿状态，不能删除！");
        }
        this.stuScoreService.deleteByApplyId(id);
        this.stuReqService.deleteByApplyId(id);
        this.stuSchedService.deleteByApplyId(id);
        this.stuSchedPlanService.deleteByApplyId(id);
        this.schedApplyInfoDao.deleteById(id);
    }

    @Override
    public void updateStatusAfterMatch(Long id) throws Exception {
        this.schedApplyInfoDao.updateStatusAfterMatch(id);
    }
    
    @Override
    public void updateStatusAfterApplyReject(Long id) throws Exception {
        this.schedApplyInfoDao.updateStatusAfterApplyReject(id);
    }
}
