package com.edu.erp.dao;

import com.edu.erp.model.CourseArrangeSpecialist;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository(value = "courseArrangeSpecialistDao")
public interface CourseArrangeSpecialistDao {

    Page<Long> queryForPage(Map<String, Object> paramMap) throws Exception;

    List<CourseArrangeSpecialist> queryForList(Map<String, Object> paramMap) throws Exception;

    CourseArrangeSpecialist queryById(Long id) throws Exception;

    void add(CourseArrangeSpecialist courseArrangeSpecialist) throws Exception;

    void update(CourseArrangeSpecialist courseArrangeSpecialist) throws Exception;

    void deleteById(Long id) throws Exception;

    int queryCourseArrangeSpecialistCount(CourseArrangeSpecialist courseArrangeSpecialist) throws  Exception;

    List<Map<String, Object>> queryCourseArrangeSp(Map<String, Object> paramMap) throws Exception;
}
