package com.edu.erp.dao;

import com.edu.erp.model.StudentManagerAnalysis;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("studentManagerDao")
public interface StudentManagerDao {
    /**
     * 整个团队学员上课情况
     * @param paramMap 查询参数
     * @return 团队团队学员上课情况
     * @throws Exception
     */
    List<StudentManagerAnalysis> queryBuAnalysis(Map<String, Object> paramMap) throws Exception;

    /**
     * 团队下每个校区学员上课情况
     * @param paramMap 查询参数
     * @return 团队下每个校区学员上课情况
     * @throws Exception
     */
    List<StudentManagerAnalysis> queryBranchAnalysis(Map<String, Object> paramMap) throws Exception;

    /**
     * 校区下每个学管师学员上课情况
     * @param paramMap 查询参数
     * @return 校区下每个学管师学员上课情况
     * @throws Exception
     */
    List<StudentManagerAnalysis> queryStuMgrAnalysis(Map<String, Object> paramMap) throws Exception;

    /**
     * 学管师每个学员上课情况
     * @param paramMap 查询参数
     * @return 学管师每个学员上课情况
     * @throws Exception
     */
    List<StudentManagerAnalysis> queryStudentAnalysis(Map<String, Object> paramMap) throws Exception;

    /**
     * 一对一排课记录查询
     * @param paramMap
     * @return 一对一排课记录信息
     * @throws Exception
     */
    Page<Map<String, Object>> queryYdyCourseSchedInfo(Map<String, Object> paramMap) throws Exception;
}
