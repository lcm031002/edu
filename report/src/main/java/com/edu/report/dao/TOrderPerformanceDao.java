package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.report.model.TOrderPerformance;
import com.edu.report.model.TBusinessStatistics;
/**
 * @ClassName: TOrderPerformanceDao
 * @Description: 课程顾问绩效DAO
 *
 */
@Repository("orderPerformanceDao")
public interface TOrderPerformanceDao {
	
	void deleteTaskFlow(Map<String, Object> param)
			throws Exception;
	
	void saveTaskFlow(Map<String, Object> param)
			throws Exception;
	
    /**
     * 查询学员
     * @param paramMap 查询条件  团队、开始时间、结束时间
     * @return 课程顾问绩效
     * @throws Exception
     */
    List<TOrderPerformance> selectForList(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询运营统计数据
	 * @param paramMap 查询条件  团队、课程季、年级
	 * @return 运营统计-总表
	 * @throws Exception
	 */
	List<TBusinessStatistics> selectForStatisticsList(Map<String, Object> paramMap) throws Exception;


}
