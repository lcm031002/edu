package com.edu.erp.basicdata.service;

import com.edu.erp.model.BasGrade;
import com.github.pagehelper.Page;

import java.util.Map;

public interface GradeService {
    Page<BasGrade> queryForPage(Map<String, Object> paramMap);
}
