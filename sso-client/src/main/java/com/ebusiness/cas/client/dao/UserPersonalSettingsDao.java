/**  
 * @Title: UserPersonalSettingsDao.java
 * @Package com.ebusiness.cas.client.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月8日 下午8:35:18
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ebusiness.cas.client.common.model.UserPersonalSettings;

/**
 * @ClassName: UserPersonalSettingsDao
 * @Description: 用户参数设置
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月8日 下午8:35:18
 * 
 */
@Repository("userPersonalSettingsDao")
public interface UserPersonalSettingsDao {
	void addSettings(UserPersonalSettings settings) throws Exception;

	List<UserPersonalSettings> queryUserSettings(Long userId) throws Exception;

	void delete(UserPersonalSettings settings) throws Exception;
}
