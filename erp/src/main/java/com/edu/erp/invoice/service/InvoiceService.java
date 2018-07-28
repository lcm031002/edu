package com.edu.erp.invoice.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TabDataInvoice;
import com.github.pagehelper.Page;

/**
 * @ClassName: InvoiceService
 * @Description: TODO(这里用一句话描述这个类的作用)
 *
 */
public interface InvoiceService {
	/**
	 * 
	 * @Title: queryByOrderid
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
	 * @param paramMap
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
	 * @return true-存在 false-不存在
	 */
	boolean isExist(Long id) throws Exception;

	/**
	 * 领取发票
	 * @param invoice
	 * @throws Exception
	 */
	void receiveInvoice(TabDataInvoice invoice) throws Exception;
}
