package com.ebusiness.hrm.employee;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.common.util.ModelDataUtils;
import com.klxx.common.util.IdcardUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.ebusiness.hrm.dao.EmployeeManageDao;
import com.ebusiness.hrm.exception.DataBaseException;
import com.ebusiness.hrm.model.Employee;
import com.ebusiness.hrm.model.EmployeeEdu;
import com.ebusiness.hrm.model.EmployeeExperience;
import com.ebusiness.hrm.model.EmployeeReward;
import com.ebusiness.hrm.model.EmployeeSummarize;
import com.ebusiness.hrm.model.PageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * @ClassName: EmployeeManageServiceImpl
 * @Description: 员工档案管理服务处理类
 * @author WP
 * @date 2016年10月13日 
 * 
 */
@Service(value="employeeManageService")
public class EmployeeManageServiceImpl implements EmployeeManageService {
	
	private static final Logger log = Logger
			.getLogger(EmployeeManageServiceImpl.class);

	
	@Resource(name="employeeManageDao")
	private EmployeeManageDao employeeManageDao;

	private QiNiuCoreCall qiNiuCoreCall = QiNiuCoreCall.getInstance();
	
	//查询员工档案信息列表
	@Override
	public PageInfo<Employee> queryEmployeeForPage(String dept,Integer post,
			Integer enterType,String encoding,String employee_name,PageParam pageParam) throws Exception{
		Page<Employee> list = null;
		PageInfo<Employee> page = new PageInfo<Employee>();
		
		try{
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("dept", dept);
			params.put("post", post);
			params.put("enterType", enterType);
			params.put("emp_id", encoding);
			params.put("employee_name", employee_name);
			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());
			
			list = employeeManageDao.queryEmployeeForPage(params);
			log.error("list:"+list);
			page = new PageInfo<Employee>(list);
		}catch(Exception e){
			log.error("Exception:",e);
		}
		return page;
	}
	
	//根据id查新员工档案
	@Override
	public List<Map<String, Object>> queryEmployee(Long id) throws Exception{
		List<Map<String, Object>> list = employeeManageDao.queryFieldKey();
		Map<String, Object> param = new HashMap<>();
		StringBuffer str = new StringBuffer();
		for(int i=0,len=list.size();i<len;i++){			
			Map<String, Object> map = list.get(i);			//得到list中的一个对象
			Set<String> set = map.keySet();					//得到对象中的key
			List<String> keyList = new ArrayList<>();
			keyList.addAll(set);
			if(i<len-1){
			for(int j=0;j<keyList.size();j++){
				str.append(map.get(keyList.get(j))+",");				
			}
			}else{
				for(int j=0;j<keyList.size();j++){			
				str.append(map.get(keyList.get(j)));
				}
				}
		}

		param.put("str", str.toString());
		param.put("id", id);
		return employeeManageDao.queryEmployee(param);
	}
	
	//查询启用的员工信息字段名
	@Override
	public List<Map<String, Object>> queryFieldKey() throws Exception {
		return employeeManageDao.queryFieldKey();
	}
	
	//新增员工
	@Override
	public boolean insertEmployee(Map<String, Object> param) throws Exception{
		Map<String, Object> map =(Map<String, Object>) param.get("work");
		//如果员工编码已经存在不允许添加
		Employee emp =ModelDataUtils.getPojoMatch(Employee.class, map);

		checkEmployee(emp);
		boolean suc = true;
		try{
		StringBuffer key = new StringBuffer();
		StringBuffer value = new StringBuffer();
		
		param.remove("work");
			Set<String> set = param.keySet();				//将前台传递的key值封装
			List<String> keyList = new ArrayList<>();		//new一个List集合
			keyList.addAll(set);							//将set集合封装到List集合
			
				for(Integer i=0;i<keyList.size();i++){		//对list进行遍历
 					if(i<keyList.size()-1){					//因为sql语句规范要求最后一句不能加逗号，所以要减一
					key.append(keyList.get(i));
					key.append(",");
					value.append("#{"+keyList.get(i)+"}");
					value.append(",");
					}else{
						key.append(keyList.get(i));
						value.append("#{"+keyList.get(i)+"}");
					}
				}
				param.put("key", key.toString());
				param.put("value", value.toString());
		employeeManageDao.insertEmployee(param);
		map.put("id", param.get("id"));
		if(map.get("entryDate")!=null){
		 String entryDate=(String)map.get("entryDate");
		 map.put("entryDate",entryDate);
		}else {
			map.put("entryDate","");
		}

		//此处修改相当于添加
		employeeManageDao.updateEmployeeStatic(map);
		}catch(Exception e){
			e.printStackTrace();
			suc=false;
			throw new DataBaseException(e.getMessage());
		}
		return suc;
	}

	private void checkEmployee(Employee e){
		Employee param = null;
		Assert.hasText(e.getEmployee_name(),"员工姓名必填");
		Assert.hasText(e.getEncoding(),"员工编码必填");
		param = new Employee();
		param.setId(e.getId());
		param.setEncoding(e.getEncoding());
		if(CollectionUtils.isNotEmpty(employeeManageDao.queryOtherEmployeeByParam(param))) {
			throw new RuntimeException("员工编码"+ e.getEncoding() +"已经存在");
		}

		if(StringUtils.isNotEmpty(e.getID_ID()) ) {
			//校验身份证格式
			if(!(IdcardUtils.validateCard(e.getID_ID())?true:IdcardUtils.validatePassport(e.getID_ID())?true:false)){
				throw new RuntimeException("身份证号码格式错误");
			}
			//判断身份证是否已经存在
			param = new Employee();
			param.setId(e.getId());
			param.setID_ID(e.getID_ID());
			if(!CollectionUtils.isEmpty(employeeManageDao.queryOtherEmployeeByParam(param))){
				throw new RuntimeException("身份证号码"+ (String) e.getID_ID() +"已经存在");
			}
		}
		//校验是否有相同手机号
		if(StringUtils.isNotEmpty(e.getPhone())) {
			param = new Employee();
			param.setId(e.getId());
			param.setPhone(e.getPhone());
			if(!CollectionUtils.isEmpty(employeeManageDao.queryOtherEmployeeByParam(param))){
				throw new RuntimeException("已经存在相同手机号的用户");
			}
		}


	}

	//更新员工档案
	@Override
	public void updateEmployee(Map<String, Object> param) throws Exception{
		StringBuffer str = new StringBuffer();
		
		Set<String> set = param.keySet();
		List<String> keyList = new ArrayList<>();
		keyList.addAll(set);
		//因为对象本身id不能被修改，所以更新的字段中要除去id，否则sq报错
		keyList.remove("id");	
		for(Integer i=0;i<keyList.size();i++){
			if(i<keyList.size()-1){
				str.append(keyList.get(i)+"="+"#{"+keyList.get(i)+"},");			
			}else{
				str.append(keyList.get(i)+"="+"#{"+keyList.get(i)+"}");
			}
		}
		param.put("str", str.toString());
		Integer ret = employeeManageDao.updateEmployee(param);
		if(ret<1){
			throw new Exception("更新失败");
		}
	}
	
	//根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
	@Override
	public List<Map<String, Object>> queryEmployeeInfo(String searchInfo) throws Exception{
		return employeeManageDao.queryEmployeeInfo(searchInfo);
	}
	
	//查询员工教育经历
	@Override
	public List<EmployeeEdu> queryEmployeeEdu(Long employee_id) throws Exception{
		return employeeManageDao.queryEmployeeEdu(employee_id);
	}
	
	//添加员工教育经历
	@Override
	public boolean addEmployeeEdu(EmployeeEdu param) throws Exception{
		Integer ret = employeeManageDao.addEmployeeEdu(param);
		if(ret<1){
			throw new Exception("添加员工教育经历失败");
		}
		return ret==1;
	}
	
	//修改员工教育经历
	@Override
	public boolean updateEmployeeEdu(EmployeeEdu param) throws Exception{
		Integer ret = employeeManageDao.updateEmployeeEdu(param);
		if(ret<1){
			throw new Exception("修改员工教育经历失败");
		}
		return ret==1;
	}
	
	//删除员工教育经历
	@Override
	public boolean deleteEmployeeEdu(Long id) throws Exception{
		Integer ret = employeeManageDao.deleteEmployeeEdu(id);
		if(ret<1){
			throw new Exception("删除员工教育经历失败");
		}
		return ret==1;
	}
	
	
	//查询员工工作经历
	@Override
	public List<EmployeeExperience> queryEmployeeExp(Long employee_id) throws Exception{
		return employeeManageDao.queryEmployeeExp(employee_id);
	}
	
	//添加员工工作经历
	@Override
	public boolean addEmployeeExp(EmployeeExperience param) throws Exception{
		Integer ret = employeeManageDao.addEmployeeExp(param);
		if(ret<1){
			throw new Exception("添加员工工作经历失败");
		}
		return ret==1;
	}
	
	//修改员工工作经历
	@Override
	public boolean updateEmployeeExp(EmployeeExperience param) throws Exception{
		Integer ret = employeeManageDao.updateEmployeeExp(param);
		if(ret<1){
			throw new Exception("修改员工工作经历失败");
		}
		return ret==1;
	}
	
	//删除员工工作经历
	@Override
	public boolean deleteEmployeeExp(Long id) throws Exception{
		Integer ret = employeeManageDao.deleteEmployeeExp(id);
		if(ret<1){
			throw new Exception("删除员工工作经历失败");
		}
		return ret==1;
	}
	
	
	//查询员工工作经历
	@Override
	public List<EmployeeSummarize> queryEmployeeSum(Long employee_id) throws Exception{
		return employeeManageDao.queryEmployeeSum(employee_id);
	}
	
	//添加员工工作经历
	@Override
	public boolean addEmployeeSum(EmployeeSummarize param) throws Exception{
		Integer ret = employeeManageDao.addEmployeeSum(param);
		if(ret<1){
			throw new Exception("添加员工工作总结失败");
		}
		return ret==1;
	}
	
	//修改员工工作经历
	@Override
	public boolean updateEmployeeSum(EmployeeSummarize param) throws Exception{
		Integer ret = employeeManageDao.updateEmployeeSum(param);
		if(ret<1){
			throw new Exception("修改员工工作总结失败");
		}
		return ret==1;
	}
	
	//删除员工工作经历
	@Override
	public boolean deleteEmployeeSum(Long id) throws Exception{
		Integer ret = employeeManageDao.deleteEmployeeSum(id);
		if(ret<1){
			throw new Exception("删除员工工作总结失败");
		}
		return ret==1;
	}
	
	
	//查询员工奖惩信息
	@Override
	public List<EmployeeReward> queryEmployeeRew(Long employee_id) throws Exception{
		return employeeManageDao.queryEmployeeRew(employee_id);
	}
	
	//添加员工奖惩信息
	@Override
	public boolean addEmployeeRew(EmployeeReward param) throws Exception{
		Integer ret = employeeManageDao.addEmployeeRew(param);
		if(ret<1){
			throw new Exception("添加员工奖惩信息失败");
		}
		return ret==1;
	}
	
	//修改员工奖惩信息
	@Override
	public boolean updateEmployeeRew(EmployeeReward param) throws Exception{
		Integer ret = employeeManageDao.updateEmployeeRew(param);
		if(ret<1){
			throw new Exception("修改员工奖惩信息失败");
		}
		return ret==1;
	}
	
	//删除员工奖惩信息
	@Override
	public boolean deleteEmployeeRew(Long id) throws Exception{
		Integer ret = employeeManageDao.deleteEmployeeRew(id);
		if(ret<1){
			throw new Exception("删除员工奖惩信息失败");
		}
		return ret==1;
	}
	
		
		//修改员工任职/固定信息
		@Override
		public boolean updateEmployeeStatic(Map<String, Object> param) throws Exception{
			if((String)param.get("entryDate")!=null){
				String entryDate=(String)param.get("entryDate");
				param.put("entryDate",entryDate);
			}else {
				param.put("entryDate","");
			}

			param.put("ID_ID",param.get("id_ID"));
			param.remove("id_ID");
			Employee e = ModelDataUtils.getPojoMatch(Employee.class,param);
			checkEmployee(e);
			Integer ret = employeeManageDao.updateEmployeeStatic(param);
			if(ret<1){
				throw new Exception("修改员工任职信息失败");
			}
			return ret==1;
		}
		
		
		//修改员工任职/固定信息头像
		@Override
		public boolean updateEmployeeImage(Map<String, Object> param) throws Exception{
			Integer ret = employeeManageDao.updateEmployeeImage(param);
			if(ret<1){
				throw new Exception("修改员工头像失败");
			}
			return ret==1;
		}

		/* (non-Javadoc)
		 * @see com.ebusiness.hrm.employee.EmployeeManageService#queryEmployeeById(java.lang.Long)
		 */
		@Override
		public Employee queryEmployeeById(Long employeeId) throws Exception {
			Assert.notNull(employeeId);
			return employeeManageDao.queryEmployeeById(employeeId);
		}
		
		
		//根据传入的员工id查询该员工绑定的岗位信息
		@Override
		public List<Map<String, Object>> queryPostByEmpId(Long employee_id) throws Exception{
			return employeeManageDao.queryPostByEmpId(employee_id);
		}
		
		//添加员工岗位信息
		@Override
		public boolean addEmpPost(Map<String, Object> param) throws Exception{
			Integer ret = employeeManageDao.addEmpPost(param);
			if(ret<1){
				throw new Exception("添加员工岗位信息失败");
			}
			return ret==1;
		}
		
		//启用禁用员工岗位
		@Override
		public boolean removeEmpPost(Long id) throws Exception{
			Integer ret = employeeManageDao.removeEmpPost(id);
			if(ret<1){
				throw new Exception("删除员工奖惩信息失败");
			}
			return ret==1;
		}
		
		//启用禁用员工状态
		@Override
		public boolean setStatus(Long id) throws Exception{
			Integer ret = employeeManageDao.setStatus(id);
			if(ret<1){
				throw new Exception("改变员工状态失败");
			}
			return ret==1;
		}

	@Override
	public String modifyPhoto(Map<String, String> json) throws Exception {
		String id = json.get("id");
		String photoBase64 = json.get("photoBase64");
		Assert.hasText(id,"缺少参数");
		Assert.hasText(photoBase64,"缺少参数");
		String fileName = qiNiuCoreCall.genFileName(photoBase64);
		// 上传头像
		qiNiuCoreCall.uploadBase64(photoBase64, fileName);
		// 删除旧头像
		String oldPhoto = json.get("oldPhoto");
		if (!StringUtils.isEmpty(oldPhoto)) {
			try {
				qiNiuCoreCall.delete(oldPhoto.substring(oldPhoto.indexOf("webERP")));
			} catch (Exception e) {
				log.error("error found,see:", e);
			}
		}
		// 更新数据库
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		String staff_head = qiNiuCoreCall.do_main + fileName;
		param.put("staff_head", staff_head);
		Integer ret = employeeManageDao.updateEmployeeImage(param);
		if (ret < 1) {
			throw new RuntimeException("照片保存到数据库错误");
		}
		return staff_head;
	}
}
