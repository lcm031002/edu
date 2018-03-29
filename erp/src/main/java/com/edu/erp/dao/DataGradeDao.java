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
	 * @param paramMap
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
	 * @param param
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer deleteData(Map<String, Object> param) throws Exception;

	/**
	 * 查询年级冲突列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Grade queryGradeListByNameOrEncoding(Map<String, Object> param) throws Exception;
}
