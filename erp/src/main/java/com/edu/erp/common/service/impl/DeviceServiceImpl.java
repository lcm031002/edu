/**
 * @Title: DeviceServiceImpl.java
 * @Package: com.edu.erp.common.service.impl
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月8日 下午2:31:26
 * @version KLXX ERPV5.0
 */
package com.edu.erp.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.common.service.DeviceService;
import com.edu.erp.dao.DeviceDao;
import com.edu.erp.model.TabDataDevice;
import com.github.pagehelper.Page;

/**
 * @ClassName: DeviceServiceImpl
 * @Description:
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月8日 下午2:31:26
 * 
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
	
	@Resource(name = "deviceDao")
	private DeviceDao deviceDao;

	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DeviceService#queryForPage(java.util.Map)
	 */
	@Override
	public Page<TabDataDevice> queryForPage(Map<String, Object> param)
			throws Exception {
		return deviceDao.selectForPage(param);
	}

	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DeviceService#queryForList(java.util.Map)
	 */
	@Override
	public List<TabDataDevice> queryForList(Map<String, Object> param)
			throws Exception {
		return deviceDao.selectForList(param);
	}
	
	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DeviceService#queryById(java.lang.String)
	 */
	@Override
	public TabDataDevice queryById(String id) throws Exception {
		return deviceDao.selectById(id);
	}

	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DeviceService#toAdd(com.edu.erp.model.TabDataDevice)
	 */
	@Override
	public void toAdd(TabDataDevice pojo) throws Exception {
		Assert.notNull(pojo.getAccount_id(),"账户必填");
		Assert.notNull(pojo.getCity_id(),"城市必填");
		//Assert.notNull(pojo.getBu_id(),"团队必填");
		Assert.notNull(pojo.getDevice_code(),"设备编码必填");
		Assert.notNull(pojo.getSimple_name(),"设备名称必填");
		deviceDao.insert(pojo);
	}

	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DeviceService#toUpdate(com.edu.erp.model.TabDataDevice)
	 */
	@Override
	public void toUpdate(TabDataDevice pojo) throws Exception {
		Assert.notNull(pojo.getAccount_id(),"账户必填");
		Assert.notNull(pojo.getId(),"id必填");
		Assert.notNull(pojo.getCity_id(),"城市必填");
		//Assert.notNull(pojo.getBu_id(),"团队必填");
		Assert.notNull(pojo.getDevice_code(),"设备编码必填");
		Assert.notNull(pojo.getSimple_name(),"设备名称必填");
		deviceDao.update(pojo);
	}

	/* (non-Javadoc)
	 * @see com.edu.erp.common.service.DeviceService#toChangeStatus(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void toChangeStatus(String ids, Integer status, Long updateUser) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
		param.put("update_user", updateUser);
		deviceDao.changeStatus(param);
	}

}
