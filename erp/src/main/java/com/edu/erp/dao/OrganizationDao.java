package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.OrganizationInfo;

@Repository(value = "organizationDao")
public interface OrganizationDao {

    /**
     * @param param 查询团队下的校区：buId为必填参数，branchs为用户可见校区列表
     * @return
     * @throws Exception 设定文件
     *                   List<OrganizationInfo>    返回类型
     * @Title: queryBuBranchs
     * @Description: 查询团队下的校区
     */
    List<OrganizationInfo> queryBuBranchs(Map<String, Object> param);

    /**
     * 根据<T>查询List
     *
     * @param orgInfo
     * @return List<T>
     * @throws Exception
     */
    List<OrganizationInfo> selectList(OrganizationInfo orgInfo);


    /**
     * 根据参数查询List<T>
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<OrganizationInfo> queryList(Map<String, Object> param);

    OrganizationInfo selectById(Long id);

    /**
     * 组织机构新增
     *
     * @param orgInfo 组织机构信息
     * @throws Exception
     */
    void insert(OrganizationInfo orgInfo);

    /**
     * 组织机构更新
     *
     * @param orgInfo 组织机构信息
     * @throws Exception
     */
    void update(OrganizationInfo orgInfo);

    /**
     * 更新组织机构logo
     *
     * @param paramMap 更新数据
     * @throws Exception
     */
    void updateLogo(Map<String, Object> paramMap);

    /**
     * 删除组织机构logo
     *
     * @param paramMap logo数据
     * @throws Exception
     */
    void deleteLogo(Map<String, Object> paramMap);

    Integer genSortNum(Long parentId);

}
