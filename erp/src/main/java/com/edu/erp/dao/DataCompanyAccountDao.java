package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.DataCompanyAccount;
import com.edu.erp.model.TimeSeason;
import com.github.pagehelper.Page;

/**
 * @ClassName: DataCompanyAccountDao
 * @Description: 公司账户服务
 * 
 */
@Repository(value = "dataCompanyAccountDao")
public interface DataCompanyAccountDao {
	/**
	 * 
	 * @Title: queryCompanyInfo
	 * @Description: 查询公司账户信息
	 * @param param
	 *            查询参数
	 * @return
	 * @throws Exception
	 *             设定文件 List<DataCompanyAccount> 返回类型
	 *
	 */
	Page<DataCompanyAccount> selectForPage(Map<String, Object> param) throws Exception;

	/**
	 * 列表查询：用于下拉列表
	 * 
	 * @Title: selectList
	 * @Description:
	 * @param param
	 * @return
	 * @throws Exception
	 *             设定文件 List<DataCompanyAccount> 返回类型
	 *
	 */
	List<DataCompanyAccount> selectList(Map<String, Object> param) throws Exception;

	/**
	 * 删除公司账户记录
	 * @param id 公司账户ID
	 * @return 空
	 * @throws Exception
	 */

	void deleteById(Long id) throws Exception;
	
	
	/**
	 * 修改
	 * 
	 * @param companyAccount
	 *            公司账户
	 * @return 空
	 * @throws Exception
	 */
	void update(DataCompanyAccount companyAccount) throws Exception;
	
	
	/**
	 * 修改
	 * 
	 * @param companyAccount
	 *            公司账户
	 * @return 空
	 * @throws Exception
	 */
	void insert(DataCompanyAccount companyAccount) throws Exception;

}
