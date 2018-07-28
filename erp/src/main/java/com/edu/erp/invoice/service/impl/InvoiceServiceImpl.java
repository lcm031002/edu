package com.edu.erp.invoice.service.impl;

import com.edu.erp.invoice.service.TabInvoiceReceiveLogService;
import com.edu.erp.model.TabDataInvoice.StatusEnum;
import com.edu.erp.model.TabInvoiceReceiveLog;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.constants.Constants;
import com.edu.erp.dao.DataInvoiceDao;
import com.edu.erp.model.TabDataInvoice;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.invoice.service.InvoiceService;
import com.github.pagehelper.Page;

/**
 * 
 * @ClassName: InvoiceServiceImpl
 * @Description: 发票服务
 *
 */
@Service(value = "invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

	@Resource(name = "dataInvoiceDao")
	private DataInvoiceDao dataInvoiceDao;

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "tabInvoiceReceiveLogService")
	private TabInvoiceReceiveLogService tabInvoiceReceiveLogService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.InvoiceService#queryByOrderid(java
	 * .lang.Long)
	 */
	@Override
	public List<TabDataInvoice> queryByOrderId(Long orderId) throws Exception {
		Assert.notNull(orderId);
		return dataInvoiceDao.queryByOrderId(orderId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.InvoiceService#queryById(
	 * java.util.Map)
	 */
	@Override
	public Page<TabDataInvoice> queryForPage(Map<String, Object> paramMap)
			throws Exception {
		Assert.notNull(paramMap);
		Assert.notNull(paramMap.get("bu_id"));
		// Assert.notNull(paramMap.get("status"));
		// Assert.notNull(paramMap.get("branch_id"));
		return dataInvoiceDao.queryForPage(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.InvoiceService#queryById(
	 * java.lang.Long)
	 */
	@Override
	public TabDataInvoice queryById(Long id) throws Exception {
		Assert.notNull(id);
		return dataInvoiceDao.queryById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.InvoiceService#queryOrderInvoceMoney
	 * (java.lang.Long)
	 */
	@Override
	public TabDataInvoice queryOrderInvoceMoney(Long orderId) throws Exception {
		Assert.notNull(orderId);
		return dataInvoiceDao.queryOrderInvoceMoney(orderId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.InvoiceService#save(com.ebusiness
	 * .erp.model.TabDataInvoice)
	 */
	@Override
	public void save(TabDataInvoice invoice) throws Exception {
		Assert.notNull(invoice);
		Assert.notNull(invoice.getBranchId());
		dataInvoiceDao.save(invoice);
		addInvoiceReceiveLog(invoice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.InvoiceService#update(com.ebusiness
	 * .erp.model.TabDataInvoice)
	 */
	@Override
	public void update(TabDataInvoice invoice) throws Exception {
		Assert.notNull(invoice);
		if (StatusEnum.REJECT.getCode().intValue() == invoice.getStatus()) {
			invoice.setReceiveStatus(TabInvoiceReceiveLog.StatusEnum.REJECTED.getCode());
		} else  if (StatusEnum.MAKEOUT.getCode().intValue() == invoice.getStatus()) {
			invoice.setReceiveStatus(TabInvoiceReceiveLog.StatusEnum.WAIT_RECEIVE.getCode());
		}
		dataInvoiceDao.update(invoice);

		// 开票，更新订单发票状态为已开票
		if (TabDataInvoice.StatusEnum.MAKEOUT.getCode().intValue() == invoice.getStatus()) {
			this.orderInfoService.updateInvoiceStatus(invoice.getOrderId(),
					Constants.OrderInvoiceStatus.MAKE_OUT);
		} else if (TabDataInvoice.StatusEnum.RECYCLECANCEL.getCode().intValue() == invoice
				.getStatus()
				|| TabDataInvoice.StatusEnum.NOTRECYCLECANCEL.getCode().intValue() == invoice
						.getStatus()) { // 回收作废或者未回收作废，更新订单发票状态为未开票
			this.orderInfoService.updateInvoiceStatus(invoice.getOrderId(),
					Constants.OrderInvoiceStatus.NOT_MAKE_OUT);
		}

		if (StatusEnum.REJECT.getCode().intValue() == invoice.getStatus() || StatusEnum.MAKEOUT.getCode().intValue() == invoice.getStatus()) {
			addInvoiceReceiveLog(invoice);
		}
	}

	private void addInvoiceReceiveLog(TabDataInvoice invoice) throws Exception {
		TabInvoiceReceiveLog invoiceReceiveLog = new TabInvoiceReceiveLog();
		invoiceReceiveLog.setInvoiceId(invoice.getId());
		invoiceReceiveLog.setStatus(invoice.getStatus());
		invoiceReceiveLog.setRemark(invoice.getDescription());
		if (StatusEnum.APPLY.getCode().intValue() == invoice.getStatus()) {
			invoiceReceiveLog.setCreate_user(invoice.getCreate_user());
			invoiceReceiveLog.setCreate_time(invoice.getCreate_time());
		} else {
			invoiceReceiveLog.setCreate_user(invoice.getUpdate_user());
			invoiceReceiveLog.setCreate_time(invoice.getUpdate_time());
		}
		this.tabInvoiceReceiveLogService.addByInvoice(invoiceReceiveLog);
	}

	@Override
	public boolean isExist(Long id) throws Exception {
		Integer count = dataInvoiceDao.isExist(id);
		return count != null && count > 0;
	}

	@Override
	public void receiveInvoice(TabDataInvoice invoice) throws Exception {
		this.dataInvoiceDao.receiveInvoice(invoice);
	}

}
