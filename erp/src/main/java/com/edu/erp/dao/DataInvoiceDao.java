/**  
 * @Title: DataInvoiceDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月6日 下午1:34:56
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabDataInvoice;
import com.github.pagehelper.Page;

/**
 * @ClassName: DataInvoiceDao
 * @Description: 发票信息持久层
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月6日 下午1:34:56
 * 
 */
@Repository(value = "dataInvoiceDao")
public interface DataInvoiceDao {
	/**
	 * 
	 * @Title: queryByOrderId
	 * @Description: 通过订单ID查询发票信息
	 * @param orderId
	 *            订单ID
	 * @return 发票信息
	 * @throws Exception
	 *             设定文件 List<TabDataInvoice> 返回类型
	 * 
	 */
	List<TabDataInvoice> queryByOrderId(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryForPage
	 * @Description: 查询发票信息
	 * @param queryPojo
	 *            查询参数
	 * @return 分页查询结果
	 * @throws Exception
	 *             设定文件 Page<TabDataInvoice> 返回类型
	 * 
	 */
	Page<TabDataInvoice> queryForPage(Map<String, Object> paramMap)
			throws Exception;

	/**
	 * 
	 * @Title: queryById
	 * @Description: 查询发票信息
	 * @param id
	 *            发票ID
	 * @return 发票信息
	 * @throws Exception
	 *             设定文件 TabDataInvoice 返回类型
	 * 
	 */
	TabDataInvoice queryById(Long id) throws Exception;

	/**
	 * 
	 * @Title: queryOrderInvoceMoney
	 * @Description: 查询订单发票金额
	 * @param orderId
	 *            订单ID
	 * @return 发票金额情况信息
	 * @throws Exception
	 *             设定文件 TabDataInvoice 返回类型
	 *
	 */
	TabDataInvoice queryOrderInvoceMoney(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: save
	 * @Description: 新增发票信息
	 * @param invoice
	 *            发票信息
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void save(TabDataInvoice invoice) throws Exception;

	/**
	 * 
	 * @Title: update
	 * @Description: 修改发票信息
	 * @param invoice
	 *            发票信息
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void update(TabDataInvoice invoice) throws Exception;
	
	/**
	 * 判断发票是否存在
	 * @param id 发票编号
	 * @return 记录数
	 * @throws Exception
	 */
	Integer isExist(Long id) throws Exception;

	/**
	 * 领取发票
	 * @param invoice 发票信息
	 * @throws Exception
	 */
	void receiveInvoice(TabDataInvoice invoice) throws Exception;
}
