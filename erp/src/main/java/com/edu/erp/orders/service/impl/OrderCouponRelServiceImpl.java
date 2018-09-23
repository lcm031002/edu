package com.edu.erp.orders.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.OrderCouponRelDao;
import com.edu.erp.model.OrderCouponRel;
import com.edu.erp.orders.service.OrderCouponRelService;

/**
 * @ClassName: OrderCouponRelServiceImpl
 * @Description: 优惠券服务
 *
 */
@Service(value = "orderCouponRelService")
public class OrderCouponRelServiceImpl implements OrderCouponRelService{
	
	@Resource(name = "orderCouponRelDao")
	private OrderCouponRelDao orderCouponRelDao;

	@Override
	public int saveOrderCouponRel(OrderCouponRel rel) throws Exception {
		Assert.notNull(rel);
		Assert.notNull(rel.getOrder_id());
		Assert.notNull(rel.getCoupon_id());
		Assert.notNull(rel.getCoupon_encoding());
		return orderCouponRelDao.saveOrderCouponRel(rel);
	}

	@Override
	public int deleteOrderCouponRel(OrderCouponRel rel) throws Exception {
		Assert.notNull(rel);
		Assert.notNull(rel.getOrder_id());
		return orderCouponRelDao.deleteOrderCouponRel(rel);
	}

	@Override
	public List<OrderCouponRel> queryByOrderId(OrderCouponRel rel)
			throws Exception {
		Assert.notNull(rel);
		Assert.notNull(rel.getOrder_id());
		return orderCouponRelDao.queryByOrderId(rel);
	}

}
