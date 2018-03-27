package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.PayFlowInfo;
import com.github.pagehelper.Page;

public interface PayFlowDao {
	
	/**
	 * 
	 * @Title: queryPayFlow
	 * @Description: 	查询在线支付流水
	 * @param queryParam
	 * @return
	 * @throws Exception    设定文件
	 * Page<PayFlow>    返回类型
	 *
	 */
	Page<PayFlowInfo> queryPayFlow(Map<String, Object> queryParam)throws Exception;
	
	Double queryTotalAmount(Map<String, Object> queryParam)throws Exception;
	
	List<Map<String,Object>> queryTeam(Long buId) throws Exception;
	
}
