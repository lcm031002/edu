/**  
 * @Title: ExtProcessDefService.java
 * @Package com.ebusiness.workflow.modules.index.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月2日 下午12:06:00
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.service;

import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtProcessDef;

/**
 * @ClassName: ExtProcessDefService
 * @Description: 流程定义服务类
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月2日 下午12:06:00
 *
 */
public interface ExtProcessDefService {
	void add(ExtProcessDef entity);
	void delete(ExtProcessDef entity);
	List<ExtProcessDef> queryAll();
	ExtProcessDef queryOne(String deployId);
}
