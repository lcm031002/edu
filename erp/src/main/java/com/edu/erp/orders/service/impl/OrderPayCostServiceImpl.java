/**  
 * @Title: OrderPayCostServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:52:34
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.OrderPayCostDao;
import com.edu.erp.model.TabOrderPayCost;
import com.edu.erp.model.TabOrderPayCostDetail;
import com.edu.erp.orders.service.OrderPayCostDetailService;
import com.edu.erp.orders.service.OrderPayCostService;

/**
 * @ClassName: OrderPayCostServiceImpl
 * @Description: 支付信息服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:52:34
 * 
 */
@Service(value = "orderPayCostService")
public class OrderPayCostServiceImpl implements OrderPayCostService {

	@Resource(name = "orderPayCostDao")
	private OrderPayCostDao orderPayCostDao;

	@Resource(name = "orderPayCostDetailService")
	private OrderPayCostDetailService orderPayCostDetailService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderPayCostService#saveOrderPayCost
	 * (com.ebusiness.erp.model.TabOrderPayCost)
	 */
	@Override
	public int saveOrderPayCost(TabOrderPayCost orderPayCost) throws Exception {
		Assert.notNull(orderPayCost);
		Assert.notNull(orderPayCost.getEncoding());
		Assert.notNull(orderPayCost.getOrderId());
		Assert.notNull(orderPayCost.getStudentId());
		Assert.notNull(orderPayCost.getSumPrice());
		Assert.notNull(orderPayCost.getActualPrice());
		Assert.notNull(orderPayCost.getBranchId());
		Assert.notNull(orderPayCost.getBuId());
		Assert.notNull(orderPayCost.getCity_id());

		orderPayCost.setAreaOrgId(orderPayCost.getCity_id());
		orderPayCost.setBuyDate(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		orderPayCost.setPaymentType(2L);
		orderPayCost.setStatus(2);
		orderPayCost.setBefore_operate_balance(orderPayCost.getSumPrice());
		orderPayCost.setCreate_time(new Date());
		int count = orderPayCostDao.saveOrderPayCost(orderPayCost);
		if (count > 0) {
			if (!CollectionUtils.isEmpty(orderPayCost.getDetails())) {
				for (TabOrderPayCostDetail detail : orderPayCost.getDetails()) {
					detail.setOrder_buy_id(orderPayCost.getId());
					orderPayCostDetailService.saveOrderPayCostDetail(detail);
				}
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderPayCostService#deleteOrderPayCost
	 * (com.ebusiness.erp.model.TabOrderPayCost)
	 */
	@Override
	public void deleteOrderPayCost(TabOrderPayCost orderPayCost)
			throws Exception {
		Assert.notNull(orderPayCost);
		Assert.notNull(orderPayCost.getOrderId());
		Long orderId = orderPayCost.getOrderId();
		this.orderPayCostDetailService.deleteDetailByOrderId(orderId);
		this.orderPayCostDao.deleteByOrderId(orderId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderPayCostService#queryTabOrderPayCost
	 * (java.lang.Long)
	 */
	@Override
	public TabOrderPayCost queryTabOrderPayCost(Long orderId) throws Exception {
		Assert.notNull(orderId);
		TabOrderPayCost tabOrderPayCost = orderPayCostDao
				.queryTabOrderPayCost(orderId);
		if (tabOrderPayCost == null) {
			return tabOrderPayCost;
		}

		List<TabOrderPayCostDetail> details = orderPayCostDao
				.queryTabOrderPayCostDetail(orderId);

		if (!CollectionUtils.isEmpty(details)) {
			tabOrderPayCost.setDetails(details);
		}

		return tabOrderPayCost;
	}

}
