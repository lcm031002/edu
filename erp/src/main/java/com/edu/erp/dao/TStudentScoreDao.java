package com.edu.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TStudentScore;

@Repository(value = "tStudentScoreDao")
public interface TStudentScoreDao {
    List<TStudentScore> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentScore tStudentScore) throws Exception;

    void saveList(List<TStudentScore> tStudentScoreList) throws Exception;

    void update(TStudentScore tStudentScore) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
    
    Integer isStuScoreExisted(TStudentScore tStudentScore) throws Exception;
}
