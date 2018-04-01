package com.edu.erp.student.service.impl;

import com.edu.erp.dao.TabStudentScoreRankingDao;
import com.edu.erp.model.TabStudentScoreRanking;
import com.edu.erp.student.service.TabStudentScoreRankingService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("tabStudentScoreRankingService")
public class TabStudentScoreRankingServiceImpl implements TabStudentScoreRankingService {

    @Resource(name = "tabStudentScoreRankingDao")
    private TabStudentScoreRankingDao tabStudentScoreRankingDao;

    @Override
    public List<TabStudentScoreRanking> queryByStuScoreInfoId(Long stuScoreInfoId) throws Exception {
        return this.tabStudentScoreRankingDao.queryByStuScoreInfoId(stuScoreInfoId);
    }

    @Override
    public void save(TabStudentScoreRanking tabStudentScoreRanking) throws Exception {
        this.tabStudentScoreRankingDao.save(tabStudentScoreRanking);
    }

    @Override
    public void saveList(List<TabStudentScoreRanking> tabStudentScoreRankingList) throws Exception {
        this.tabStudentScoreRankingDao.saveList(tabStudentScoreRankingList);
    }

    @Override
    public void update(TabStudentScoreRanking tabStudentScoreRanking) throws Exception {
        this.tabStudentScoreRankingDao.update(tabStudentScoreRanking);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tabStudentScoreRankingDao.deleteById(id);
    }

    @Override
    public void deleteByStuScoreInfoId(Long stuScoreInfoId) throws Exception {
        this.tabStudentScoreRankingDao.deleteByStuScoreInfoId(stuScoreInfoId);
    }
}
