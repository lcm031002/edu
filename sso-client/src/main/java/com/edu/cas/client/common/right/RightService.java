package com.edu.cas.client.common.right;

import java.util.List;

import com.edu.cas.client.common.model.Permission;

/**
 * @ClassName: RightService
 * @Description: 菜单权限服务
 *
 */
public interface RightService {
	/**
	 * 
	 * @Title: queryUserRightModel
	 * @Description: 查询指定用户的权限
	 * @param userId
	 *            用户ID
	 * @throws Exception
	 *             设定文件
	 * @return List<RightModel> 返回类型
	 * 
	 */
	List<Permission> queryUserRightModel(Long userId) throws Exception;
	
}
