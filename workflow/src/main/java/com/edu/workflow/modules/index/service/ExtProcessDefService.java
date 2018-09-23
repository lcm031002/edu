package com.edu.workflow.modules.index.service;

import com.edu.workflow.modules.index.model.ExtProcessDef;
import java.util.List;

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
