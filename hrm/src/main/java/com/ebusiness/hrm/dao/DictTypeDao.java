package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.DictType;

/**
 * @ClassName: DictTypeDao
 * @Description: 数据字典定义项持久层
 * @author WP
 * @date 2016年9月7日 
 * 
 */
@Repository(value="dictTypeDao")
public interface DictTypeDao {
	
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
	Integer insertDicTypes(DictType item);

	
	
	

}
