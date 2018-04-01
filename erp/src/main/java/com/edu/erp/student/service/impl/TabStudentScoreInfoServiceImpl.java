package com.edu.erp.student.service.impl;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.TabStudentScoreInfoDao;
import com.edu.erp.model.TabStudentScore;
import com.edu.erp.model.TabStudentScoreInfo;
import com.edu.erp.model.TabStudentScoreRanking;
import com.edu.erp.student.service.TabStudentScoreInfoService;
import com.edu.erp.student.service.TabStudentScoreRankingService;
import com.edu.erp.student.service.TabStudentScoreService;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("tabStudentScoreInfoService")
public class TabStudentScoreInfoServiceImpl implements TabStudentScoreInfoService {

    @Resource(name = "tabStudentScoreInfoDao")
    private TabStudentScoreInfoDao tabStudentScoreInfoDao;

    @Resource(name = "tabStudentScoreService")
    private TabStudentScoreService tabStudentScoreService;

    @Resource(name = "tabStudentScoreRankingService")
    private TabStudentScoreRankingService tabStudentScoreRankingService;

    @Override
    public Page<TabStudentScoreInfo> selectForPage(Map<String, Object> queryParam) throws Exception {
        Page<Long> idPage = this.tabStudentScoreInfoDao.selectForPage(queryParam);

        Page<TabStudentScoreInfo> page = new Page<TabStudentScoreInfo>();
        if (idPage != null && !CollectionUtils.isEmpty(idPage.getResult())) {
            page.setTotal(idPage.getTotal());
            page.setPageNum(idPage.getPageNum());
            page.setPages(idPage.getPages());
            page.setPageSize(idPage.getPageSize());

            StringBuilder idBuilder = new StringBuilder();
            for (Long id : idPage.getResult()) {
                idBuilder.append(",").append(id);
            }

            queryParam.put("ids", idBuilder.substring(1));
            List<TabStudentScoreInfo> studentScoreInfoList = this.selectForList(queryParam);
            page.addAll(studentScoreInfoList);
        } else {
            page.setTotal(0);
            page.setPages(0);
        }
        return page;
    }

    @Override
    public List<TabStudentScoreInfo> selectForList(Map<String, Object> queryParam) throws Exception {
        return this.tabStudentScoreInfoDao.selectForList(queryParam);
    }

    @Override
    public TabStudentScoreInfo selectById(Long id) throws Exception {
        return this.tabStudentScoreInfoDao.selectById(id);
    }

    @Override
    public TabStudentScoreInfo selectOne(Map<String, Object> queryParam) throws Exception {
        List<TabStudentScoreInfo> studentScoreInfoList = this.selectForList(queryParam);
        return CollectionUtils.isEmpty(studentScoreInfoList) ? null : studentScoreInfoList.get(0);
    }

    @Override
    public void save(TabStudentScoreInfo tabStudentScoreInfo) throws Exception {
        tabStudentScoreInfo.setSubmitDate(DateUtil.getCurrDateTime());
        this.tabStudentScoreInfoDao.save(tabStudentScoreInfo);
        saveSubObjects(tabStudentScoreInfo, false);
    }

    @Override
    public void update(TabStudentScoreInfo tabStudentScoreInfo) throws Exception {
        this.tabStudentScoreInfoDao.update(tabStudentScoreInfo);
        this.tabStudentScoreService.deleteByStuScoreInfoId(tabStudentScoreInfo.getId());
        this.tabStudentScoreRankingService.deleteByStuScoreInfoId(tabStudentScoreInfo.getId());
        saveSubObjects(tabStudentScoreInfo, true);
    }

    private void saveSubObjects(TabStudentScoreInfo tabStudentScoreInfo, boolean isUpdate) throws Exception {
        if (!CollectionUtils.isEmpty(tabStudentScoreInfo.getStudentScoreList())) {
            for (TabStudentScore tabStudentScore : tabStudentScoreInfo.getStudentScoreList()) {
                tabStudentScore.setStudentScoreInfoId(tabStudentScoreInfo.getId());
                if (isUpdate) {
                    tabStudentScore.setCreate_user(tabStudentScoreInfo.getUpdate_user());
                    tabStudentScore.setCreate_time(tabStudentScoreInfo.getUpdate_time());
                } else {
                    tabStudentScore.setCreate_user(tabStudentScoreInfo.getCreate_user());
                    tabStudentScore.setCreate_time(tabStudentScoreInfo.getCreate_time());
                }
            }
            this.tabStudentScoreService.saveList(tabStudentScoreInfo.getStudentScoreList());
        }

        if (!CollectionUtils.isEmpty(tabStudentScoreInfo.getStudentScoreRankingList())) {
            for (TabStudentScoreRanking tabStudentScoreRanking : tabStudentScoreInfo.getStudentScoreRankingList()) {
                tabStudentScoreRanking.setStudentScoreInfoId(tabStudentScoreInfo.getId());
                if (isUpdate) {
                    tabStudentScoreRanking.setCreate_user(tabStudentScoreInfo.getUpdate_user());
                    tabStudentScoreRanking.setCreate_time(tabStudentScoreInfo.getUpdate_time());
                } else {
                    tabStudentScoreRanking.setCreate_user(tabStudentScoreInfo.getCreate_user());
                    tabStudentScoreRanking.setCreate_time(tabStudentScoreInfo.getCreate_time());
                }
            }
            this.tabStudentScoreRankingService.saveList(tabStudentScoreInfo.getStudentScoreRankingList());
        }
    }

    @Override
    public void changeStatus(TabStudentScoreInfo tabStudentScoreInfo) throws Exception {
        this.tabStudentScoreInfoDao.changeStatus(tabStudentScoreInfo);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tabStudentScoreService.deleteByStuScoreInfoId(id);
        this.tabStudentScoreRankingService.deleteByStuScoreInfoId(id);
        this.tabStudentScoreInfoDao.deleteById(id);
    }
}
