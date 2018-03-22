package com.ebusiness.hrm.employee;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.EmployeeExtDao;
import com.ebusiness.hrm.model.EmployeeExt;

/**
 * @ClassName: EmployeeExtServiceImpl
 * @Description: 员工档案服务处理类
 * @author WP
 * @date 2016年9月28日 
 * 
 */
@Service(value="employeeExtService")
public class EmployeeExtServiceImpl implements EmployeeExtService {

	@Resource(name="employeeExtDao")
	private EmployeeExtDao employeeExtDao;
	
	//查询员工档案定义项
	@Override
	public List<Map<String, Object>> queryEmployeeExt(){
		return employeeExtDao.queryEmployeeExt();		 
	}
	
	//查询员工档案定义项启用的字段信息
	@Override
	public List<Map<String, Object>> queryEmployeeExtField(){
		return employeeExtDao.queryEmployeeExtField();		 
	}
	
	//新增员工档案定义项
	@Override
	public boolean insertEmployeeExt(EmployeeExt param) throws Exception {
		List<Map<String, Object>> list = employeeExtDao.queryEmployeeExt();
		for(Integer i=0;i<list.size();i++){		
			if(param.getFieldKey().equals(list.get(i).get("fieldKey"))){
				throw new Exception("关键字已存在，请重新命名");
			}
		}
		if(param.getFieldKey()!=null&&param.getFieldKey()!=""){
		employeeExtDao.insertEmployeefield(param);
		Integer ret = employeeExtDao.insertEmployeeExt(param);
		if(ret<1){
			throw new Exception("新增失败");
		}				
		return ret==1;
		}else{
			throw new Exception("关键字不能为空");
		}
	}
	
	//更新员工档案定义项
	@Override
	public void updateEmployeeExt(EmployeeExt param) throws Exception {
		List<Map<String, Object>> list = employeeExtDao.queryEmployeeExt();
		for(Integer i=0;i<list.size();i++){		
			if(param.getFieldKey().equals(list.get(i).get("fieldKey"))){
				throw new Exception("关键字已存在，请重新命名");
			}
		}
		Long id = param.getId();
		EmployeeExt ext = employeeExtDao.queryEmployeeField(id);
		String oldFieldKey =ext.getFieldKey();
		if(oldFieldKey.equals(param.getFieldKey())){
			Integer ret = employeeExtDao.updateEmployeeExt(param);
			if(ret<1){
				throw new Exception("更新失败");
			}
		}else if(param.getFieldKey()!=null&&param.getFieldKey()!="" ){
			param.setOldFieldKey(oldFieldKey);
			Integer ret = employeeExtDao.updateEmployeeExt(param);
			if(ret<1){
				throw new Exception("更新定义表失败");
			}
			Integer res = employeeExtDao.updateEmployeeField(param);
			if(res>1){
				throw new Exception("更新信息表字段名失败");
			}
		}else{
			throw new Exception("关键字不能为空");
		}
	}
	
	//禁用启用
	@Override
	public boolean removeEmployeeExt(Integer id) throws Exception {
		return employeeExtDao.removeEmployeeExt(id) == 1;
	}
}
