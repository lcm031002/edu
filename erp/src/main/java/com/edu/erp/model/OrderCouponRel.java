/**  
 * @Title: OrderCouponRel.java
 * @Package com.pojo
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年12月8日 上午2:05:53
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;


/**
 * @ClassName: OrderCouponRel
 * @Description: 订单优惠券关系表
 * @author zhuliyong zly@entstudy.com
 * @date 2015年12月8日 上午2:05:53
 *
 */
public class OrderCouponRel  extends BaseObject{

	private static final long serialVersionUID = 8348408840350355891L;
	
	private Long order_id;
	
	private Long coupon_id;
	
	private Long coupon_amount;
	
	private String coupon_encoding;
	
	private String coupon_name;
	

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(Long coupon_id) {
		this.coupon_id = coupon_id;
	}

	public Long getCoupon_amount() {
		return coupon_amount;
	}

	public void setCoupon_amount(Long coupon_amount) {
		this.coupon_amount = coupon_amount;
	}

	public String getCoupon_encoding() {
		return coupon_encoding;
	}

	public void setCoupon_encoding(String coupon_encoding) {
		this.coupon_encoding = coupon_encoding;
	}

	public String getCoupon_name() {
		return coupon_name;
	}

	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}

	@Override
	public String toString() {
		return "OrderCouponRel [order_id=" + order_id + ", coupon_id="
				+ coupon_id + ", coupon_amount=" + coupon_amount
				+ ", coupon_encoding=" + coupon_encoding + ", coupon_name="
				+ coupon_name + ", toString()=" + super.toString() + "]";
	}
	
}
