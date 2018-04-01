package com.edu.erp.student.service;

import java.util.List;

import com.edu.erp.model.TStudentScore;

public interface TStudentScoreService {
    List<TStudentScore> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentScore tStudentScore) throws Exception;

    void saveList(List<TStudentScore> tStudentScoreList) throws Exception;

    void update(TStudentScore tStudentScore) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
}
