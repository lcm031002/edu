package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.SortNumInfo;
import com.github.pagehelper.Page;

@Repository(value = "sortNumInfoDao")
public interface SortNumInfoDao {
	/**
	 * 
	 * 保存排号信息
	 * @param pojo
	 * @throws Exception
	 */
	void saveSortNumInfo(SortNumInfo sortNumInfo) throws Exception;
	/**
	 * 查询分页排号信息
	 * @param sortNumInfo
	 * @return
	 * @throws Exception
	 */
	Page<SortNumInfo> querySortNumPage(Map<String, Object> map)throws Exception;
	/**
	 * 查询排号信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<SortNumInfo> querySortNumInfo(SortNumInfo sortNumInfo)throws Exception;
	/**
	 * 根据课程ID查询排号明细
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	List<SortNumInfo> querySortNumDetailByCourseId(Long courseId)throws Exception;
	/**
	 * 统计当前课程对应的排号人数
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	Page<SortNumInfo> queryCountSortNumInfo(Map<String, Object> map)throws Exception;
	/**
	 * 统计当前课程课次对应的排号人数
	 * @param sortNumInfo
	 * @return
	 * @throws Exception
	 */
	List<SortNumInfo> queryCountSortNumInfoBySeq(SortNumInfo sortNumInfo)throws Exception;
	/**
	 * 查询最大的排号码
	 * @param map  
	 * studentId,courseId
	 * @return
	 * @throws Exception
	 */
	Long queryMaxNum(Map<String, Object> map)throws Exception;
	
	/**
	 * 取消排号
	 * @param sortNumInfo
	 * @throws Exception
	 */
	void deleteSortNum(SortNumInfo sortNumInfo)throws Exception ;
	/**
	 * 取消订单排号
	 * @param sortNumInfo
	 * @throws Exception
	 */
	void deleteOrderSortNum(Map<String, Object> map)throws Exception ;
	
}
