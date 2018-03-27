/**  
 * @Title: RightService.java
 * @Package com.edu.cas.client.common.right
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 上午11:41:55
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.right;

import java.util.List;

import com.edu.cas.client.common.model.Permission;

/**
 * @ClassName: RightService
 * @Description: 菜单权限服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 上午11:41:55
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
