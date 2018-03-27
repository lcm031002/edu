package com.edu.erp.dao;

import java.util.List;

import java.util.Map;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TStudentSchedulePlan;

@Repository(value = "tStudentSchedulePlanDao")
public interface TStudentSchedulePlanDao {
    List<TStudentSchedulePlan> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentSchedulePlan tStudentSchedulePlan) throws Exception;

    void saveList(List<TStudentSchedulePlan> tStudentSchedulePlanList) throws Exception;

    void update(TStudentSchedulePlan tStudentSchedulePlan) throws Exception;

    /**
     * 压单处理
     * @param tStudentSchedulePlan 排课计划信息
     * @throws Exception
     */
    void pressPlan(TStudentSchedulePlan tStudentSchedulePlan) throws Exception;
    
    void updateStatus(List<Map<String, Object>> tStudentSchedulePlanList) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
    
    Integer isStuSchedPlanExisted(TStudentSchedulePlan tStudentSchedulePlan) throws Exception;

    /**
     * 查询需要在当前排课计划前就要处理的排课计划数量。如果大于0，则当前排课计划不能处理
     */
    Integer queryNeedMatchedPlanCount(Map<String, Object> paramMap) throws Exception;
}
