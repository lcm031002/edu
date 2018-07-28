package com.ebusiness.workflow.modules.index.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ebusiness.workflow.common.ServiceUtil;
import com.ebusiness.workflow.modules.index.dao.ExtBusinessEntrustDao;
import com.ebusiness.workflow.modules.index.dao.ExtBusinessRoleMappingDao;
import com.ebusiness.workflow.modules.index.dao.ExtProcessDefDao;
import com.ebusiness.workflow.modules.index.dao.ExtProcessRoleDefDao;
import com.ebusiness.workflow.modules.index.model.ExtBusinessEntrust;
import com.ebusiness.workflow.modules.index.model.ExtBusinessRoleMapping;
import com.ebusiness.workflow.modules.index.model.ExtProcessDef;
import com.ebusiness.workflow.modules.index.model.ExtProcessRoleDef;
import com.ebusiness.workflow.modules.index.service.BusinessRoleMappingService;
import com.ebusiness.workflow.modules.index.service.ExtBusinessEntrustService;
import com.ebusiness.workflow.modules.index.service.ExtProcessDefService;
import com.ebusiness.workflow.modules.index.service.LocalProcessService;
import com.ebusiness.workflow.modules.index.service.LocalTaskService;
import com.ebusiness.workflow.modules.index.service.ProcessRoleDefService;

/**
 * @ClassName: ProcessRoleDefServiceImpl
 * @Description: 流程节点角色定义服务实现类
 *
 */
@Repository(value = "processLocalService")
@Transactional
public class ProcessServiceImpl implements ProcessRoleDefService,
		BusinessRoleMappingService, LocalTaskService, LocalProcessService,
		ExtProcessDefService, ExtBusinessEntrustService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProcessServiceImpl.class);

	@Resource(name = "extProcessRoleDefDao")
	private ExtProcessRoleDefDao extProcessRoleDefDao;

	@Resource(name = "extBusinessrolMappingDao")
	private ExtBusinessRoleMappingDao extBusinessrolMappingDao;

	@Resource(name = "processEngine")
	private ProcessEngine processEngine;

	@Resource(name = "extProcessDefDao")
	private ExtProcessDefDao extProcessDefDao;

	@Resource(name = "extBusinessEntrustDao")
	private ExtBusinessEntrustDao extBusinessEntrustDao;
	
	@Resource(name = "taskService")
	private TaskService taskService;

	public ProcessServiceImpl() {
		ServiceUtil.getInstance().setBusinessRoleMappingService(this);
		ServiceUtil.getInstance().setExtProcessDefService(this);
		ServiceUtil.getInstance().setProcessRoleDefService(this);
	}

	@Override
	public List<ExtProcessRoleDef> queryByKey(String key) {
		ExtProcessRoleDef entity = new ExtProcessRoleDef();
		entity.setProcessKey(key);
		return extProcessRoleDefDao.list(
				"from ExtProcessRoleDef where processKey=:processKey order by id",
				ExtProcessRoleDef.class, entity);
	}

	@Override
	public void add(ExtBusinessRoleMapping entity) {
		try {
			extBusinessrolMappingDao.add(entity);
		} catch (Exception e) {
			LOGGER.error("error found ,see:", e);
		}
	}

	@Override
	public void update(ExtBusinessRoleMapping entity) {
		try {
			extBusinessrolMappingDao.update(entity);
		} catch (Exception e) {
			LOGGER.error("error found ,see:", e);
		}
	}

	@Override
	public void delete(ExtBusinessRoleMapping entity) {
		try {
			extBusinessrolMappingDao.delete(entity);
		} catch (Exception e) {
			LOGGER.error("error found ,see:", e);
		}
	}

	@Override
	public List<ExtBusinessRoleMapping> queryRoleMappings(
			ExtBusinessRoleMapping entity) {
		try {
			return extBusinessrolMappingDao
					.list("from ExtBusinessRoleMapping mapping where mapping.processKey=:processKey and mapping.processTask=:processTask",
							ExtBusinessRoleMapping.class, entity);
		} catch (Exception e) {
			LOGGER.error("error found ,see:", e);
		}
		return null;
	}

	@Override
	public List<ExtBusinessRoleMapping> queryByProcessKey(
			ExtBusinessRoleMapping entity) {
		try {
			return extBusinessrolMappingDao
					.list("from ExtBusinessRoleMapping mapping where mapping.processRoleDefId=:processRoleDefId",
							ExtBusinessRoleMapping.class, entity);
		} catch (Exception e) {
			LOGGER.error("error found ,see:", e);
		}
		return null;
	}

	@Override
	public List<ProcessDefinition> queryUserProcess(String businessRole) {
		List<ProcessDefinition> result = new ArrayList<ProcessDefinition>();
		try {
			// 查询业务角色绑定的工作流程实例与角色
			ExtBusinessRoleMapping mapping = new ExtBusinessRoleMapping();
			mapping.setBusinessRole(businessRole);
			List<ExtBusinessRoleMapping> mappings = extBusinessrolMappingDao
					.list("from ExtBusinessRoleMapping mapping where mapping.businessRole=:businessRole",
							ExtBusinessRoleMapping.class, mapping);
			if (!CollectionUtils.isEmpty(mappings)) {
				Set<ExtProcessRoleDef> processDefinitionKeys = new HashSet<ExtProcessRoleDef>();
				for (ExtBusinessRoleMapping map : mappings) {
					processDefinitionKeys.add(map.getProcessRoleDef());
				}
				for (ExtProcessRoleDef processRoleDef : processDefinitionKeys) {
					ProcessDefinitionQuery processDefinitionQuery = processEngine
							.getRepositoryService()
							.createProcessDefinitionQuery()
							.processDefinitionKey(
									processRoleDef.getProcessKey());
					result.add(processDefinitionQuery.uniqueResult());
				}
			}
		} catch (Exception e) {
			LOGGER.error("error found ,see:", e);
		}
		return result;
	}

	@Override
	public List<Task> queryUserTasks(String businessRole) {
		List<Task> result = new ArrayList<Task>();
		/*
		 * try { // 查询业务角色绑定的工作流程实例与角色 ExtBusinessRoleMapping mapping = new
		 * ExtBusinessRoleMapping(); mapping.setBusinessRole(businessRole);
		 * List<ExtBusinessRoleMapping> mappings = extBusinessrolMappingDao
		 * .list(
		 * "from ExtBusinessRoleMapping mapping where mapping.businessRole=:businessRole"
		 * , ExtBusinessRoleMapping.class, mapping); if
		 * (!CollectionUtils.isEmpty(mappings)) { Set<String> processRoles = new
		 * HashSet<String>(); for (ExtBusinessRoleMapping map : mappings) {
		 * processRoles.add(map.getJbpmRole()); } for (String jbpmRole :
		 * processRoles) { List<Task> personalTasks =
		 * processEngine.getTaskService() .findPersonalTasks(jbpmRole);
		 * result.addAll(personalTasks);
		 * 
		 * List<Task> groupTasks = processEngine.getTaskService()
		 * .findGroupTasks(jbpmRole); result.addAll(groupTasks); } } } catch
		 * (Exception e) { LOGGER.error("error found ,see:", e); }
		 */
		return result;
	}

	@Override
	public void add(ExtProcessDef entity) {
		try {
			extProcessDefDao.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(ExtProcessDef entity) {
		try {
			extProcessDefDao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<ExtProcessDef> queryAll() {
		try {
			return extProcessDefDao.list("from ExtProcessDef def",ExtProcessDef.class,new ExtProcessDef());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ExtProcessDef queryOne(String deployId) {
		try {
			ExtProcessDef def = new ExtProcessDef();
			def.setProcessDeployId(deployId);
			List<ExtProcessDef> list = extProcessDefDao
					.list("from ExtProcessDef def where def.processDeployId=:processDeployId",
							ExtProcessDef.class, def);
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(ExtProcessRoleDef entity) {
		try {
			extProcessRoleDefDao.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(ExtProcessRoleDef entity) {
		try {
			extProcessRoleDefDao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteProcessRoleDef(String processKey) {
		ExtProcessRoleDef entity = new ExtProcessRoleDef();
		entity.setProcessKey(processKey);
		String hql = "delete from ExtProcessRoleDef entity where entity.processKey=:processKey";
		try {
			extProcessRoleDefDao.delete(entity, hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ExtProcessRoleDef queryOne(String processKey, String processTask) {
		ExtProcessRoleDef entity = new ExtProcessRoleDef();
		entity.setProcessKey(processKey);
		entity.setProcessTask(processTask);
		String hql = "from ExtProcessRoleDef entity where entity.processKey=:processKey and entity.processTask=:processTask";
		try {
			List<ExtProcessRoleDef> result = extProcessRoleDefDao.list(hql,
					ExtProcessRoleDef.class, entity);
			if (!CollectionUtils.isEmpty(result)) {
				if (result.size() > 1) {
					throw new Exception("数据异常，流程节点不唯一！");
				} else {
					return result.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteMappingsByProcessKey(ExtBusinessRoleMapping entity) {
		extBusinessrolMappingDao
				.list("delete from ExtBusinessRoleMapping mapping where mapping.processRoleDefId=:processRoleDefId",
						ExtBusinessRoleMapping.class, entity);
	}

	@Override
	public ExtBusinessRoleMapping queryOne(Long id) {
		try {
			return extBusinessrolMappingDao.findById(
					ExtBusinessRoleMapping.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addBusinessEntrust(ExtBusinessEntrust entity) {
		try {
			extBusinessEntrustDao.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cancelExtBusinessEntrust(ExtBusinessEntrust entity) {
		try {
			String hql = "UPDATE ExtBusinessEntrust ENTITY SET ENTITY.status = 0 WHERE ENTITY.id = :id";
			extBusinessEntrustDao.update(entity, hql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<ExtBusinessEntrust> queryHistoryEntrust(String consignorRole,String consigneeTaskId) {
		ExtBusinessEntrust entity = new ExtBusinessEntrust();
		entity.setConsignorRole(consignorRole);
		entity.setConsigneeTaskId(consigneeTaskId);
		
		String hql = "from ExtBusinessEntrust ENTITY WHERE ENTITY.consignorRole =:consignorRole and ENTITY.consigneeTaskId =:consigneeTaskId ";
		List<ExtBusinessEntrust> lists = extBusinessEntrustDao.list(hql,
				ExtBusinessEntrust.class, entity);
		return lists;
	}

	@Override
	public List<ExtBusinessEntrust> queryCurrentEntrust(String consigneeRole,
			Date currentTime) {
		ExtBusinessEntrust entity = new ExtBusinessEntrust();
		entity.setConsignorRole(consigneeRole);
		entity.setBeginTime(currentTime);
		String hql = "from ExtBusinessEntrust ENTITY WHERE ENTITY.consigneeRole =:consigneeRole and :beginTime BETWEEN ENTITY.beginTime and ENTITY.endTime and ENTITY.status=1";
		List<ExtBusinessEntrust> lists = extBusinessEntrustDao.list(hql,
				ExtBusinessEntrust.class, entity);
		return lists;
	}

	@Override
	public ExtBusinessEntrust queryBusinessEntrust(Long id) {
		try {
			return extBusinessEntrustDao.findById(ExtBusinessEntrust.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
