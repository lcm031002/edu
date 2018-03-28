/**  
 * @Title: DataCompanyAccountService.java
 * @Package com.edu.erp.common.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月20日 下午4:48:45
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.common.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.DataCompanyAccount;
import com.edu.erp.model.TimeSeason;
import com.github.pagehelper.Page;

/**
 * @ClassName: DataCompanyAccountService
 * @Description: 公司账户服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月20日 下午4:48:45
 *
 */
public interface DataCompanyAccountService {
	
	Page<DataCompanyAccount> queryPage(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 查询列表数据
	 * @Title: selectCompanyAccountList
	 * @Description: 
	 * @param param
	 * @return
	 * @throws Exception
	 *      设定文件 List<DataCompanyAccount> 返回类型
	 *
	 */
	List<DataCompanyAccount> queryCompanyAccountList(Map<String, Object> param)
			throws Exception;
	
	
	
	/**
	 * 保存公司账户
	 * 
	 * @param timeSeason 课程季对象
	 * @return
	 */
	void save(DataCompanyAccount companyAccount) throws Exception;
	
	/**
	 * 删除公司账户
	 * 
	 * @param timeSeason 课程季对象
	 * @return
	 */
	void deleteById(String strId) throws Exception;
	
	/**
	 * 更新公司账户
	 * 
	 * @param timeSeason 课程季对象
	 * @return
	 */
	void update(DataCompanyAccount companyAccount) throws Exception;
	
}
