package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.DictTypeSub;

/**
 * @ClassName: DictTypeSubDao
 * @Description: 数据字典子项持久层
 * @author WP
 * @date 2016年9月7日 
 * 
 */
@Repository(value="dictTypeSubDao")
public interface DictTypeSubDao {
	
	
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
	Integer insertDicData(DictTypeSub param);
	
	/**
	 * 
	 * @Title: updateDicData
	 * @Description: 更新字典子项
	 * @param Map<String,Object>
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	Integer updateDicData(Map<String,Object>param);
	
	/**
	 * 
	 * @Title: deleteDicData
	 * @Description: 禁用字典子项
	 * @param id
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	Integer deleteDicData(Integer id);
	

}
