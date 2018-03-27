package com.edu.erp.dao;

import com.edu.erp.model.CourseArrangeSpSubject;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository(value = "courseArrangeSpSubjectDao")
public interface CourseArrangeSpSubjectDao {

    void add(CourseArrangeSpSubject courseArrangeSpSubject) throws Exception;

    void addList(List<CourseArrangeSpSubject> courseArrangeSpSubjectList) throws Exception;

    void deleteByArrangeSpId(Long arrangeSpId) throws Exception;

    void deleteById(Long id) throws Exception;

}
