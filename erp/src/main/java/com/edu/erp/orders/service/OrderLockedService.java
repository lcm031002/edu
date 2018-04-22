package com.edu.erp.orders.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TOrderLock;

public interface OrderLockedService {

	List<TOrderLock> queryLockedOrderList(Map<String, Object> param) throws Exception;
	
	void carryForward(Map<String, Object> param, Long userId) throws Exception;
}
