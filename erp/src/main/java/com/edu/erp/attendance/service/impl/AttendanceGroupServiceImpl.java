package com.edu.erp.attendance.service.impl;

import com.edu.erp.attendance.service.AttendanceGroupService;
import com.edu.erp.dao.AttendTeacherGroupDao;
import com.edu.erp.dict.util.BaseModuleUtils;
import com.edu.erp.model.AttendTeacherGroup;
import com.github.pagehelper.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service(value = "attendanceGroupService")
public class AttendanceGroupServiceImpl implements AttendanceGroupService {
	
	@Resource(name = "attendTeacherGroupDao") 
	private AttendTeacherGroupDao attendTeacherGroupDao;

	/** 
	 * 分页查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<AttendTeacherGroup> queryAttendanceGroupForPage(Map<String, Object> param) throws Exception {
		return attendTeacherGroupDao.page(param);
	}
	
	/** 
	 * 查询组详细信息（包括组下的教师）
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AttendTeacherGroup> listAttendanceGroup(Map param) throws Exception {
		Assert.notNull(param.get("city_id"));
		Assert.notNull(param.get("group_id"));
		return attendTeacherGroupDao.selectList(param);
	}
	
	@Override
	public void toAdd(AttendTeacherGroup pojo) throws Exception {
		Assert.notNull(pojo.getBranch_id());
		Assert.notNull(pojo.getName());
		Integer ret = attendTeacherGroupDao.toAdd(pojo);
		if(ret < 1) {
			throw new RuntimeException("toAdd_error");
		}  else if(StringUtils.isNotEmpty(pojo.getTeacher_ids())) {
			//添加教师组教师关系
			List<Map<String, Long>> ref = BaseModuleUtils.refMajorForeign(Long.parseLong(pojo.getId().toString()), pojo.getTeacher_ids());
			if(ref.size() > 0)
				attendTeacherGroupDao.toAddTeacherRef(ref);
		}
	}

	@Override
	public void toUpdate(AttendTeacherGroup pojo) throws Exception {
		Assert.notNull(pojo.getId());
		Assert.notNull(pojo.getBranch_id());
		Assert.notNull(pojo.getName());
		Integer ret = attendTeacherGroupDao.toUpdate(pojo);
		if(ret < 1) {
			throw new RuntimeException("toUpdate_error");
		} else{
			toSetTeacher(pojo.getId().toString(), pojo.getTeacher_ids());
		}
	}

	@Override
	public void toChangeStatus(String ids, Integer status) {
		Assert.notNull(ids);
		Assert.notNull(status);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
		attendTeacherGroupDao.toChangeStatus(param);
	}

	/**
	 * 设置教师关系
	 * 
	 * @param teach_group_id
	 * @param teacher_ids
	 * @throws Exception
	 */
	@Override
	public void toSetTeacher(String teach_group_id, String teacher_ids) throws Exception {
		Assert.notNull(teach_group_id);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("teach_group_id",teach_group_id);
		attendTeacherGroupDao.toRemoveTeacherRef(param);
		List<Map<String, Long>> ref = BaseModuleUtils.refMajorForeign(Long.parseLong(teach_group_id), teacher_ids);
		if(ref.size() > 0)
			attendTeacherGroupDao.toAddTeacherRef(ref);
	}

}
