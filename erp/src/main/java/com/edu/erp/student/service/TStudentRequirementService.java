package com.edu.erp.student.service;

import java.util.List;

import com.edu.erp.model.TStudentRequirement;

public interface TStudentRequirementService {
    List<TStudentRequirement> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentRequirement tStudentReq) throws Exception;

    void saveList(List<TStudentRequirement> tStudentReqList) throws Exception;

    void update(TStudentRequirement tStudentReq) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;
}
