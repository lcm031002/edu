package com.edu.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TStudentSchedule;

@Repository(value = "tStudentScheduleDao")
public interface TStudentScheduleDao {
    List<TStudentSchedule> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentSchedule tStudentSchedule) throws Exception;

    void saveList(List<TStudentSchedule> tStudentScheduleList) throws Exception;

    void update(TStudentSchedule tStudentSchedule) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
}
