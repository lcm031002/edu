package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.EmployeeInfo;
import com.edu.erp.model.TScheduleApplyInfo;
import com.github.pagehelper.Page;

@Repository(value = "tScheduleApplyInfoDao")
public interface TScheduleApplyInfoDao {

    Page<TScheduleApplyInfo> queryForPage(Map<String, Object> paramMap) throws Exception;
    
    TScheduleApplyInfo queryDetail(Map<String, Object> paramMap) throws Exception;
    
    TScheduleApplyInfo queryById(Long id) throws Exception;

    TScheduleApplyInfo queryLatestApply(TScheduleApplyInfo tScheduleApplyInfo) throws Exception;
    
    void save(TScheduleApplyInfo schedApplyInfo) throws Exception;
    
    void update(TScheduleApplyInfo schedApplyInfo) throws Exception;
    
    void updateStatus(TScheduleApplyInfo schedApplyInfo) throws Exception;
    
    void updateStatusAfterMatch(Long id) throws Exception;
    
    void updateStatusAfterApplyReject(Long id) throws Exception;

    void auditScheduleApply(TScheduleApplyInfo scheduleApplyInfo) throws Exception;
    
    List<EmployeeInfo> queryCounselorInfo(Long studentId) throws Exception;
    
    void deleteById(Long id) throws Exception;
    
    Integer hasOtherApply(Long studentId) throws Exception;
}
