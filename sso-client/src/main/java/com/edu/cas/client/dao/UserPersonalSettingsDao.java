package com.edu.cas.client.dao;

import java.util.List;

import com.edu.cas.client.common.model.UserPersonalSettings;
import org.springframework.stereotype.Repository;

import com.edu.cas.client.common.model.UserPersonalSettings;

/**
 * @ClassName: UserPersonalSettingsDao
 * @Description: 用户参数设置
 *
 */
@Repository("userPersonalSettingsDao")
public interface UserPersonalSettingsDao {
	void addSettings(UserPersonalSettings settings) throws Exception;

	List<UserPersonalSettings> queryUserSettings(Long userId) throws Exception;

	void delete(UserPersonalSettings settings) throws Exception;
}
