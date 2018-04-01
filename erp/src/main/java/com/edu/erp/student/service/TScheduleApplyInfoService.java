package com.edu.erp.student.service;

import java.util.Map;

import com.edu.erp.model.TScheduleApplyInfo;
import com.github.pagehelper.Page;

public interface TScheduleApplyInfoService {
    Page<TScheduleApplyInfo> queryForPage(Map<String, Object> paramMap) throws Exception;
    
    TScheduleApplyInfo queryDetail(Map<String, Object> paramMap) throws Exception;
    
    TScheduleApplyInfo queryById(Long id) throws Exception;

    TScheduleApplyInfo save(TScheduleApplyInfo schedApplyInfo) throws Exception;

    void update(TScheduleApplyInfo schedApplyInfo) throws Exception;

    void updateStatus(TScheduleApplyInfo schedApplyInfo) throws Exception;

    /**
     * 换单申请审批
     * @param scheduleApplyInfo
     * @throws Exception
     */
    void auditScheduleApply(TScheduleApplyInfo scheduleApplyInfo) throws Exception;
    
    /**
     * 排课申请教师匹配后，更新排课申请状态
     * @param id 排课申请单ID
     * @throws Exception
     */
    void updateStatusAfterMatch(Long id) throws Exception;
    /**
     * 换单审批拒绝后，修改申请单状态为已完成
     * @param id 排课申请单ID
     * @throws Exception
     */
    void updateStatusAfterApplyReject(Long id) throws Exception;
    
    void deleteById(Long id) throws Exception;
    
}
