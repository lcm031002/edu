package com.edu.erp.student.service;

import java.util.List;

import com.edu.erp.model.TStudentSchedulePlan;
import java.util.Map;

public interface TStudentSchedulePlanService {

    List<TStudentSchedulePlan> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentSchedulePlan tStudentSchedulePlan) throws Exception;

    void saveList(List<TStudentSchedulePlan> tStudentSchedulePlanList) throws Exception;
    
    void addByApplyId(Long applyId, Long userId) throws Exception;

    void update(TStudentSchedulePlan tStudentSchedulePlan) throws Exception;
    
    void updateStatus(List<Map<String, Object>> tStudentSchedulePlanList) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
}
