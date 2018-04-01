package com.edu.erp.student.service;

import java.util.List;

import com.edu.erp.model.TStudentSchedule;

public interface TStudentScheduleService {

    List<TStudentSchedule> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentSchedule tStudentSchedule) throws Exception;

    void saveList(List<TStudentSchedule> tStudentScheduleList) throws Exception;

    void update(TStudentSchedule tStudentSchedule) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
}
