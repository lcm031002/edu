package com.edu.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TStudentRequirement;

@Repository(value = "tStudentRequirementDao")
public interface TStudentRequirementDao {
    List<TStudentRequirement> queryByApplyId(Long applyId) throws Exception;

    void save(TStudentRequirement tStudentReq) throws Exception;

    void saveList(List<TStudentRequirement> tStudentReqList) throws Exception;

    void update(TStudentRequirement tStudentReq) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByApplyId(Long applyId) throws Exception;

    /**
     * 课程规划科目是否重复
     * @param tStudentReq 课程规划信息
     * @return 大于0-重复 等于0-没重复
     * @throws Exception
     */
    Integer isStuReqExisted(TStudentRequirement tStudentReq) throws Exception;

    /**
     * 课程规划科目序号是否重复
     * @param tStudentReq 课程规划信息
     * @return 大于0-重复 等于0-没重复
     * @throws Exception
     */
    Integer isSeqExisted(TStudentRequirement tStudentReq) throws Exception;

    /**
     * 已有课程规划信息
     * @param applyId 申请单id
     * @return
     * @throws Exception
     */
    Integer hasStuReq(Long applyId) throws Exception;
}
