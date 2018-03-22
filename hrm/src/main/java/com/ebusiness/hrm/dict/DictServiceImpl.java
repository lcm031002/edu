package com.ebusiness.hrm.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ebusiness.hrm.dao.DictTypeDao;
import com.ebusiness.hrm.dao.DictTypeSubDao;
import com.ebusiness.hrm.exception.DataBaseException;
import com.ebusiness.hrm.model.DictType;
import com.ebusiness.hrm.model.DictTypeSub;


/**
 * @ClassName: DictServiceImpl
 * @Description: 数据字典服务处理类
 * @author WP
 * @date 2016年9月7日 
 * 
 */
@Service(value="dictService")
public class DictServiceImpl implements DictService  {

	@Resource(name="dictTypeDao")
	private DictTypeDao dictTypeDao;
	
	@Resource(name="dictTypeSubDao")
	private DictTypeSubDao dictTypeSubDao;
	
	//查询数据字典定义项
	@Override
	public List<Map<String,Object>> queryDicTypes() {
		List<Map<String,Object>> dicList= dictTypeDao.queryDicTypes();
		if(dicList.isEmpty())
			return dicList;
		for(Map<String,Object> map:dicList){
			Integer dictTypeId=Integer.valueOf(map.get("id").toString());
			map.put("items", dictTypeSubDao.queryDicChildDatas(dictTypeId));
		}
		return dicList;
	}
	
	
	//查询数据字典子项
	@Override
	public List<Map<String,Object>> queryDicChildDatas(Integer dictTypeId) {
		
		return dictTypeSubDao.queryDicChildDatas(dictTypeId);
	}
	
	
	//添加数据字典子项
	@Override
	public boolean insertDicData(DictTypeSub param) throws DataBaseException {
		Integer count=0;
		if(param.getName()==null){ throw new DataBaseException("子项名称不能为空");}
		try {
			count=dictTypeSubDao.insertDicData(param);
		} catch (Exception e) {
			throw new DataBaseException("添加字典失败:"+e.getMessage());
		}
		return count==1;
	}
	
	
	//更新数据字典子项
	@Override
	public boolean updateDicData(Map<String, Object> param) {
		return dictTypeSubDao.updateDicData(param)>=1;
	}
	
	
	//删除数据字典子项
	@Override
	public boolean deleteDicData(Integer id) {
		return dictTypeSubDao.deleteDicData(id)==1;
	}

	//添加数据字典定义项
	@Override
	public boolean insertDicTypes(DictType item) throws DataBaseException {
		Integer count=0;
		if(item.getName()==null){ throw new DataBaseException("字典名称不能为空");}
		try {
			
			count=dictTypeDao.insertDicTypes(item);
		} catch (Exception e) {
			throw new DataBaseException("添加字典失败:"+e.getMessage());
		}
		return count==1;
	}
	
	//查询数据字典所有子项
	@Override
	public Map<String, Object> queryDictSubAll() throws Exception{
		List<Map<String,Object>> dicList= dictTypeDao.queryDicTypes();
		Map<String, Object> map = new HashMap<>();
		for(Map<String, Object> dic:dicList){
			Integer dictTypeId=Integer.valueOf(dic.get("id").toString());
			List<Map<String, Object>> dicSub=dictTypeSubDao.queryDicChildDatas(dictTypeId);
			map.put((String) dic.get("key"), dicSub);
		}
		return map;
	}

}
