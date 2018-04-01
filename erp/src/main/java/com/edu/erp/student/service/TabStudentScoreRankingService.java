package com.edu.erp.student.service;

import com.edu.erp.model.TabStudentScoreRanking;
import java.util.List;

public interface TabStudentScoreRankingService {
    List<TabStudentScoreRanking> queryByStuScoreInfoId(Long stuScoreInfoId) throws Exception;

    void save(TabStudentScoreRanking tabStudentScoreRanking) throws Exception;

    void saveList(List<TabStudentScoreRanking> tabStudentScoreRankingList) throws Exception;

    void update(TabStudentScoreRanking tabStudentScoreRanking) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByStuScoreInfoId(Long stuScoreInfoId) throws Exception;
}
