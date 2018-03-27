/**  
 * @Title: DataCompanyAccountDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月20日 下午4:46:03
 * @version KLXX ERPV4.0  
 */
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
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月20日 下午4:46:03
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
	 * @param empIdStr
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteById(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 修改
	 * 
	 * @param DataCompanyAccount
	 *            公司账户
	 * @return 空
	 * @throws Exception
	 */
	void update(DataCompanyAccount companyAccount) throws Exception;
	
	
	/**
	 * 修改
	 * 
	 * @param DataCompanyAccount
	 *            公司账户
	 * @return 空
	 * @throws Exception
	 */
	void insert(DataCompanyAccount companyAccount) throws Exception;

}
