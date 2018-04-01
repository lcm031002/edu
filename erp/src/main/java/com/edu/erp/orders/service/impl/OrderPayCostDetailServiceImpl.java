/**  
 * @Title: OrderPayCostDetailServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:52:59
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.OrderPayCostDao;
import com.edu.erp.model.TabOrderPayCostDetail;
import com.edu.erp.orders.service.OrderPayCostDetailService;

/**
 * @ClassName: OrderPayCostDetailServiceImpl
 * @Description: 报班单详细缴费信息
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:52:59
 *
 */
@Service(value = "orderPayCostDetailService")
public class OrderPayCostDetailServiceImpl implements OrderPayCostDetailService {
	
	@Resource(name = "orderPayCostDao")
	private OrderPayCostDao orderPayCostDao;
	
	/* (non-Javadoc)
	 * @see com.ebusiness.erp.orders.service.OrderPayCostDetailService#saveOrderPayCostDetail(com.ebusiness.erp.model.TabOrderPayCostDetail)
	 */
	@Override
	public int saveOrderPayCostDetail(
			TabOrderPayCostDetail orderPayCostDetail) throws Exception {
		Assert.notNull(orderPayCostDetail);
		Assert.notNull(orderPayCostDetail.getOrder_buy_id());
		Assert.notNull(orderPayCostDetail.getPayment_way());
		Assert.notNull(orderPayCostDetail.getStaffappprem());
		orderPayCostDetail.setCreate_time(new Date());
		
		return orderPayCostDao.saveOrderPayCostDetail(orderPayCostDetail);
	}
	
	public int deleteOrderPayCostDetail(
			TabOrderPayCostDetail orderPayCostDetail) throws Exception {
		Assert.notNull(orderPayCostDetail);
		Assert.notNull(orderPayCostDetail.getOrder_buy_id());
		
		return orderPayCostDao.deleteOrderPayCostDetail(orderPayCostDetail);
	}

    @Override
    public void deleteDetailByOrderId(Long orderId) throws Exception {
        this.orderPayCostDao.deleteDetailByOrderId(orderId);
    }


}
