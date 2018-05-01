package com.edu.report.framework.dao;

import com.edu.report.model.TReportGxhStudentStatus;
import com.edu.report.model.TReportGxhStudentStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: TReportGxhStudentStatusDao
 * @Description: 学生状态报表DAO
 * @author ohs ohs@klxuexi.org
 * @date 2017年11月23日 下午2:42:22
 *
 */
@Repository(value = "reportGxhStudentStatusDao")
public interface TReportGxhStudentStatusDao {

	List<TReportGxhStudentStatus> queryForBu(Map<String, Object> param) throws Exception;

	List<TReportGxhStudentStatus> queryForBranch(Map<String, Object> param) throws Exception;

	List<TReportGxhStudentStatus> queryForStudents(Map<String, Object> param) throws Exception;
}
