package com.edu.erp.dict.service;

import com.edu.erp.model.CoopOrg;
import com.edu.erp.model.CoopOrgRel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CoopOrgService {
    /**
     * 分页查询
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    Page<CoopOrg> selectForPage(Map<String, Object> paramMap);

    /**
     * 根据条件查询List<T>
     *
     * @param paramMap 动态参数
     * @return
     * @throws Exception
     */
    List<CoopOrg> selectList(Map<String, Object> paramMap);

    /**
     * 新增
     *
     * @param coopOrg
     * @throws Exception
     * @return 影响行数
     */
    Integer insert(CoopOrg coopOrg);

    /**
     * 根据ID修改
     *
     * @param coopOrg
     * @throws Exception
     * @return 影响行数
     */
    Integer update(CoopOrg coopOrg);

    /**
     * 根据ids字符串改变状态
     *
     * @param paramMap
     * @throws Exception
     * @return 影响行数
     */
    Integer changeStatus(Map<String, Object> paramMap);

    /**
     * 新增抽成
     *
     * @param coopOrgRel
     * @throws Exception
     * @return 影响行数
     */
    Integer insertPercentage(CoopOrgRel coopOrgRel);

    /**
     * 根据条件查询List<T>
     *
     * @param paramMap 动态参数
     * @return
     * @throws Exception
     */
    List<CoopOrgRel> selectPercentageList(Map<String, Object> paramMap);
}
