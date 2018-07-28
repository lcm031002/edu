package com.ebusiness.workflow.modules.index.service;

import java.util.List;

import com.ebusiness.workflow.modules.index.model.ExtProcessDef;

/**
 * @ClassName: ExtProcessDefService
 * @Description: 流程定义服务类
 *
 */
public interface ExtProcessDefService {
	void add(ExtProcessDef entity);
	void delete(ExtProcessDef entity);
	List<ExtProcessDef> queryAll();
	ExtProcessDef queryOne(String deployId);
}
