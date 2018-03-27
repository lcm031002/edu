package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.Grade;
import com.github.pagehelper.Page;
@Repository(value = "dataGradeDao")
public interface DataGradeDao {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<Grade> selectForPage(Page<Grade> page) throws Exception;
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> selectForPage(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<Grade> selectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer insert(Grade pojo) throws Exception;
	Integer insertGradeBuRel(Map<String, Object> data) throws Exception;
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer update(Grade pojo) throws Exception;
	Integer updateGradeBuRel(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer deleteData(Map<String, Object> param) throws Exception;
	/**
	 * 新增校区关系
	 * 
	 * @param refs
	 * @return
	 * @throws Exception
	 */
	Integer toAddOrgRef(List<Map<String, Long>> refs) throws Exception;
	
	/**
	 * 删除校区关系
	 * 
	 * @param 课程IDS
	 * @return
	 * @throws Exception
	 */
	Integer toRemoveOrgRef(String ids) throws Exception;

	/**
	 * 查询年级冲突列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Grade queryGradeListByNameOrEncoding(Map<String, Object> param) throws Exception;


	String queryGradeNameById(@Param("id") Long id);
}
