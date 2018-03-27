package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TpDictData;
import com.github.pagehelper.Page;

public interface DictDataDao {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TpDictData> page(Page<TpDictData> page) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<TpDictData> selectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toAdd(TpDictData pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toUpdate(TpDictData pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toChangeStatus(Map<String, Object> param) throws Exception;

	public List<Map> getBranchsByUser(Map<String, Object> param) throws Exception;
	
	public List<Map> getBusByUser(Map<String, Object> param) throws Exception;
	
	public List<Map<String,Object>> getOrgTree(Map<String, Object> param) throws Exception;
	
//	 通过机构查询对应权限
	 List<Map<String,Object>> queryDataOrg(Integer dataId) throws Exception;
//   批量新增菜单
	  void batchAddDataOrg(Map<String, Object> orgMps) throws Exception;
//	   批量删除菜单
	  void batchDeleteDataOrg(Map<String, Object> orgMps) throws Exception;

	  List<Map<String, Object>> getPosts(Map<String, Object> params) throws Exception;
}
