package com.edu.erp.basicdata.service.impl;

import com.edu.erp.basicdata.service.GradeService;
import com.edu.erp.dao.BasGradeDao;
import com.edu.erp.model.BasGrade;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {
    @Resource(name = "basGradeDao")
    private BasGradeDao basGradeDao;

    @Override
    public Page<BasGrade> queryForPage(Map<String, Object> paramMap) {
        return basGradeDao.queryForPage(paramMap);
    }
}
