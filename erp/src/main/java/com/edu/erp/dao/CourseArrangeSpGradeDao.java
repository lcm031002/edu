package com.edu.erp.dao;

import com.edu.erp.model.CourseArrangeSpGrade;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository(value = "courseArrangeSpGradeDao")
public interface CourseArrangeSpGradeDao {

    void add(CourseArrangeSpGrade courseArrangeSpGrade) throws Exception;

    void addList(List<CourseArrangeSpGrade> courseArrangeSpGradeList) throws Exception;

    void deleteByArrangeSpId(Long arrangeSpId) throws Exception;

    void deleteByArrangeSpSubjectId(Long arrangeSpSubjectId) throws Exception;

    void deleteById(Long id) throws Exception;

}
