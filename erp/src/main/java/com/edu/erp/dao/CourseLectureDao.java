package com.edu.erp.dao;

import com.edu.erp.model.CourseLectureInfo;
import com.edu.erp.model.CourseLectureRecord;
import com.github.pagehelper.Page;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository(value = "courseLectureDao")
public interface CourseLectureDao {
    /**
     * 查询打印列表
     * @param paramMap
     * @return
     */
    Page<CourseLectureInfo> selectLecturePrintList(Map<String, Object> paramMap);

    /**
     * 打印次数计数
     * @param id
     * @return
     */
    int updatePrintCount(Integer id);

}
