package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TpDictData;
import com.edu.erp.model.TpDictType;
import com.github.pagehelper.Page;

public interface GcDictService {
	
	/**
	 * 字典类型分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TpDictType> typePage(Page<TpDictType> page) throws Exception;
	
	/**
	 * 所属类型数据分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TpDictData> dataPage(Page<TpDictData> page) throws Exception;
	
	/**
	 * 根据条件查询数据List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<TpDictData> dataList(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据字典类型查询
	 * @return 数据字典数据
	 * @throws Exception
	 */
	List<TpDictData> selectByDictTypeCode(String dictTypeCode) throws Exception;
	
	/**
	 * 根据字典类型查询
	 * @return 数据字典数据，按字典类型分组
	 * @throws Exception
	 */
	Map<String, List<TpDictData>> selectByDictTypeCodes(String dictTypeCodes) throws Exception;
	
	/**
     * 根据字典类型、子类型查询
     * @param paramMap 字典类型查询条件
     * 条件1：typeCode 字典类型
     * 条件2：subTypeCode 字典子类型
     * @return 数据字典数据
     * @throws Exception
     */
    List<TpDictData> selectDictData(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @param org_ids 校区
	 * @throws Exception
	 */
	void toAdd(TpDictData pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toUpdate(TpDictData pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status) throws Exception;
	
	public List<Map> getBranchsByUser(Map<String, Object> param) throws Exception;
	
	public List<Map> getBusByUser(Map<String, Object> param) throws Exception;

	List<Map<String, Object>> getPosts(Map<String, Object> params) throws Exception;

}
