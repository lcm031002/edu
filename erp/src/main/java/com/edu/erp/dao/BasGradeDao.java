package com.edu.erp.dao;

import com.edu.erp.model.BasGrade;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("basGradeDao")
public interface BasGradeDao {
    Page<BasGrade> queryForPage(Map<String, Object> paramMap);
}
