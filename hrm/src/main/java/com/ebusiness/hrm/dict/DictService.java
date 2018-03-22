package com.ebusiness.hrm.dict;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.exception.DataBaseException;
import com.ebusiness.hrm.model.DictType;
import com.ebusiness.hrm.model.DictTypeSub;

/**
 * @ClassName: DictService
 * @Description: 数据字典服务
 * @author WP
 * @date 2016年9月7日 
 * 
 */
public interface DictService {
	
	/**
	 * 
	 * @Title: queryDicTypes
	 * @Description: 查询数据字典定义项
	 * 
	 * @return List<Map<String,Object>> 返回类型
	 */
	List<Map<String,Object>> queryDicTypes();
	
	/**
	 * 
	 * @Title: insertDicTypes
	 * @Description: 添加数据字典定义项
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean insertDicTypes(DictType item) throws DataBaseException;
	
	/**
	 * 
	 * @Title: queryDicChildDatas
	 * @Description: 通过定义项id查询子项信息
	 * @param dictTypeId
	 *            定义项id
	 *
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryDicChildDatas(Integer dictTypeId);
	
	/**
	 * 
	 * @Title: insertDicData
	 * @Description: 添加数据字典子项
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean insertDicData(DictTypeSub param) throws DataBaseException;
	
	/**
	 * 
	 * @Title: updateDicData
	 * @Description: 更新字典子项
	 * @param Map<String,Object>
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateDicData(Map<String,Object>param) throws DataBaseException;
	
	/**
	 * 
	 * @Title: deleteDicData
	 * @Description: 禁用字典子项
	 * @param id
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deleteDicData(Integer id) throws DataBaseException;
	
	/**
	 * 
	 * @Title: queryDictSubAll
	 * @Description: 查询数据字典所有子项
	 * @param 
	 *            
	 *
	 * @return Map<String, Object> 返回类型
	 */
	Map<String, Object> queryDictSubAll() throws Exception;

}
