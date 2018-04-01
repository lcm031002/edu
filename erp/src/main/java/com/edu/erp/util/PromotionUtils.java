package com.edu.erp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.erp.dict.util.BaseModuleUtils;
import com.edu.erp.dict.util.OrganizationUtils;
import com.edu.erp.model.CouponInfo;

/**
 * 促销工具类
 * 
 * @author wCong
 *
 */
public class PromotionUtils extends BaseModuleUtils{
	
	/**
	 * 组装数据
	 * 
	 * @param p_id
	 * @param org_ids
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Long>> get_P_ORG_Ref(Long p_id, String org_ids) throws Exception{
		if(org_ids == null)
			org_ids = "";
		String[] org_idArr = org_ids.split(",");
		Set<Long> ids = new HashSet<Long>();
		
		for(String org_id : org_idArr){
			if(org_id.trim().length() == 0)
				continue; 
			ids.add(Long.parseLong(org_id)); 
			for(Long id : OrganizationUtils.getChildIds(Long.parseLong(org_id))){
				ids.add(id);
			}
		}
		
		List<Map<String, Long>> p_org = new ArrayList<Map<String,Long>>(ids.size());
		Map<String, Long> obj = null;
		
		for(Long org_id : ids){
			obj = new HashMap<String, Long>();
			obj.put("org_id", org_id);
			obj.put("p_id", p_id);
			p_org.add(obj);
		}
		
		return p_org;
	}
	
	/**
	 * 组装数据
	 *  
	 * @param one_id
	 * @param much_ids
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Long>> getStudentGiftInfoAdd(Long one_id, String much_ids) throws Exception{
		if(much_ids == null)
			much_ids = "";
		String[] org_idArr = much_ids.split(",");
		Set<Long> ids = new HashSet<Long>();
		
		for(String org_id : org_idArr){
			if(org_id.trim().length() == 0)
				continue; 
			ids.add(Long.parseLong(org_id)); 
			for(Long id : OrganizationUtils.getChildIds(Long.parseLong(org_id))){
				ids.add(id);
			}
		}
		 
		List<Map<String, Long>> one_much = new ArrayList<Map<String, Long>>(ids.size());
		Map<String, Long> obj = null;
		
		for(Long much_id : ids){
			obj = new HashMap<String, Long>();
			obj.put("much_id", much_id);
			obj.put("one_id", one_id);
			one_much.add(obj);
		}
		
		return one_much;
	}
	
	/**
	 * 生成优惠券编号 格式: 校区ID + 优惠规则ID + yyyyMMddHHmmss
	 * 
	 * @param couponInfo
	 * @return
	 * @throws Exception
	 */
	public static void generateCouponName(CouponInfo couponInfo,Integer length)throws Exception{
		
		StringBuilder name = new StringBuilder();
		
        name.append(couponInfo.getActivity_name())
		/*		.append(DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_6))
				.append(couponInfo.getName());*/
        .append(String.format("%0"+length+"d", couponInfo.getSort_num()));
		couponInfo.setName(name.toString());
	}
	
	/**
	 * 生成优惠券编号 格式: 校区ID + 优惠规则ID + HHmmss
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static void generateCouponEncoding(CouponInfo couponInfo,Integer length)throws Exception{

		StringBuilder encoding = new StringBuilder();
		if("".equals(StringUtil.nullToBlank(couponInfo.getCoupon_prefix()))){
			encoding.append(DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_6));
		}else{
			encoding.append(StringUtil.nullToBlank(couponInfo.getCoupon_prefix()));
		}
		if(couponInfo.getSort_num()==null){
			couponInfo.setSort_num(1);
		}
		
//		.append(couponInfo.getRule_id())
//		.append(couponInfo.getCity_id())
//		.append(DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_6))
		encoding.append(String.format("%0"+length+"d", couponInfo.getSort_num()));;
		couponInfo.setEncoding(encoding.toString());
	}
	
	/**
	 * 生成优惠券仓库优惠券编号 格式: 校区ID + 活动ID + 优惠规则ID + 序号 + yyyyMMddHHmmss
	 * 
	 * @param couponDepot
	 * @return
	 * @throws Exception
	 */
	public static void generateDepotCouponEncoding(CouponInfo couponDepot)throws Exception{
		
		StringBuilder encoding = new StringBuilder();
		
		encoding.append(couponDepot.getCoupon_prefix())
		        .append(couponDepot.getBranch_id())
				.append(couponDepot.getActivity_id())
				.append(couponDepot.getRule_id())
				.append(couponDepot.getEncoding())
				.append(DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2))
				;
		couponDepot.setEncoding(encoding.toString());
	}
}
