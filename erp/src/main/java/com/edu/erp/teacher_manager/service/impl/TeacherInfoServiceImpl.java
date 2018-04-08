package com.edu.erp.teacher_manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import javax.annotation.Resource;

import com.edu.erp.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.erp.dao.TeacherInfoDao;
import com.edu.erp.teacher_manager.service.TeacherInfoService;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;

import jxl.common.Logger;

@Service(value = "teacherInfoService")
public class TeacherInfoServiceImpl implements TeacherInfoService {

	private Logger log = Logger.getLogger(TeacherInfoService.class);

	private QiNiuCoreCall qiNiuCoreCall = QiNiuCoreCall.getInstance();

	@Resource(name = "teacherInfoDao")
	private TeacherInfoDao teacherInfoDao;

	/**
	 * 分页查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<Teacher> page(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("city_id"));
		return teacherInfoDao.page(param);
	}

	/**
	 * 教师列表查询
	 * @param param 查询条件
	 * @return 教师列表
	 * @throws Exception
	 */
	@Override
	public List<Teacher> selectList(Map<String, Object> param) throws Exception {
		return teacherInfoDao.selectList(param);
	}

	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Teacher> list(Map<String, Object> param) throws Exception {
		return teacherInfoDao.list(param);
	}

	/**
	 * 新增
	 * 
	 * @param teacher
	 * @param teacherSubjectList
	 * @throws Exception
	 */
	@Override
	public void toAdd(Teacher teacher, List<TeacherSubject> teacherSubjectList,List<TeacherTeamRel> teacherTeamRelList)
			throws Exception {
		Assert.hasText(teacher.getPhone(),"电话号码必填");
		Assert.notNull(teacher.getBu_id(),"所属团队必填");
		int ret = teacherInfoDao.isEncodingExisted(teacher);
		if (ret > 0) {
			throw new Exception("所在地区已存在该教师编码，请重新输入");
		}
		//是否在归属city已经存在电话号码
		ret = teacherInfoDao.isPhoneExisted(teacher);
		if (ret > 0) {
			throw new Exception("所在地区已存在该电话号码，请重新输入");
		}
		
		ret = teacherInfoDao.toAdd(teacher);
		if (ret < 1) {
			throw new RuntimeException("toAdd_error");
		}
		
		if (!CollectionUtils.isEmpty(teacherSubjectList)) {
			for (TeacherSubject ts : teacherSubjectList) {
				ts.setTeacher_id(teacher.getId());
			}
			teacherInfoDao.insertSubjectRef(teacherSubjectList);
		}

		if(!CollectionUtils.isEmpty(teacherTeamRelList)){
			for(TeacherTeamRel teacherTeamRel : teacherTeamRelList){
				teacherTeamRel.setTeacherId(teacher.getId());
				teacherInfoDao.insertTeamRef(teacherTeamRel);
			}

		}
	}

	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @param teacherSubjectList
	 *            部门IDS
	 * @throws Exception
	 */
	@Override
	public void toUpdate(Teacher pojo, List<TeacherSubject> teacherSubjectList,List<TeacherTeamRel> teacherTeamRelList)
			throws Exception {
		
		int ret = teacherInfoDao.isEncodingExisted(pojo);
		if (ret > 0) {
			throw new Exception("所在地区已存在该教师编码，请重新输入");
		}
		//是否在归属city已经存在电话号码
		ret = teacherInfoDao.isPhoneExisted(pojo);
		if (ret > 0) {
			throw new Exception("所在地区已存在该电话号码，请重新输入");
		}
		ret = teacherInfoDao.toUpdate(pojo);
		if (ret < 1) {
			throw new RuntimeException("toUpdate_error");
		}
		teacherInfoDao.toRemoveSubjectRef(pojo.getId().toString());
		if (teacherSubjectList.size() > 0) {
		  teacherInfoDao.insertSubjectRef(teacherSubjectList);
		}
		teacherInfoDao.toRemoveTeamRef(pojo.getId());
		if (teacherTeamRelList.size() > 0) {
			for(TeacherTeamRel teacherTeamRel : teacherTeamRelList){
				teacherTeamRel.setTeacherId(pojo.getId());
				teacherInfoDao.insertTeamRef(teacherTeamRel);
			}
		}
	}

	/**
	 * 根据IDS字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void toChangeStatus(String ids, Integer status) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
		teacherInfoDao.toChangeStatus(param);
		// 删除状态 则删除关联表数据
		if (status == BaseObject.StatusEnum.DELETE.getCode()) {
			teacherInfoDao.toRemoveSubjectRef(ids);
		}
	}

	@Override
	public List<Teacher> queryStudentTeachers(Long studentId, Long branchId)
			throws Exception {
		return this.teacherInfoDao.queryStudentTeachers(studentId, branchId);
	}

	/**
	 * 设置教师图像
	 * 
	 * @param teacher_id
	 * @param photo
	 * @throws Exception
	 */
	@Override
	public void toSetPhoto(Long teacher_id, String photo) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("teacher_id", teacher_id);
		param.put("photo", photo);
		Integer ret = teacherInfoDao.toSetPhoto(param);
		if (ret < 1) {
			throw new RuntimeException("toSetPhoto_error");
		}
	}

	@Override
	public Teacher queryOne(Long teacherId) throws Exception {
		return teacherInfoDao.queryOne(teacherId);
	}

	/**
	 * 新增班级课文档模板
	 * 
	 * @param edocCourse
	 * @return
	 * @throws Exception
	 */
	@Override
	public void toAddTemplateTeacherInfo(TEDocTemplateTeacherInfo edocCourse)
			throws Exception {
		int ret = teacherInfoDao.toAddTemplateTeacherInfo(edocCourse);
		if (ret < 1) {
			throw new Exception("toAddTemplateTeacherInfo.error");
		}
	}

	/**
	 * 校验课程商品模板
	 * 
	 * @param param
	 * @throws Exception
	 */
	@Override
	public void toCheckTemplateTeacherInfo(Map<String, Object> param)
			throws Exception {
		param.put("error_code", 0);
		param.put("error_desc", "");
		teacherInfoDao.toCheckTemplateTeacherInfo(param);
		String error_code = param.get("error_code").toString();
		String error_desc = (String) param.get("error_desc");
		if (!"0".equals(error_code)) {
			throw new Exception(error_desc);
		}
	}

	@Override
	public List<Map<String, Object>> getAsyn(Map<String, Object> params)
			throws Exception {
		return teacherInfoDao.getAsyn(params);
	}

	/**
	 * params: ids:需要产出邀请码的教师id
	 * 
	 */
	@Override
	public String genInvation(Map<String, Object> params) throws Exception {
		List<Teacher> teachers = teacherInfoDao.list(params);
		StringBuilder failedTeachers = new StringBuilder();
		for (Teacher t : teachers) {
			// 校验是否有手机号
			if ("".equals(StringUtil.emptyToString(t.getPhone()))) {
				failedTeachers.append(t.getTeacher_name() + "@" + t.getEncoding() + ";" );
				continue;
			}

			// 产生邀请码
			params.put("invation_code", StringUtil.getRandomString(6));
			params.put("id", t.getId());
			teacherInfoDao.genInvation(params);
			// 发送邀请码短信
			/* 发送短信通知 */
			try {
				String msg_content = "您的邀请码为：" + params.get("invation_code") + ",可登录教师工作台查看您的工作量信息!";
				//messageRequirementService.sendMessage(msg_content, t.getPhone(), null);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("短信接口异常，验证码发送失败");
			}
		}
		return failedTeachers.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.teacher_manager.service.TeacherInfoService#
	 * queryOrderTeacher(java.lang.Long)
	 */
	@Override
	public List<Teacher> queryOrderTeacher(Long orderId) throws Exception {
		Assert.notNull(orderId);
		return teacherInfoDao.queryOrderTeacher(orderId);
	}

	/**
	 * 上传新头像，删除旧头像
	 */
	@Override
	public void uploadAndSetPhoto(Map<String, String> json) throws Exception {
		String teacherId = json.get("teacherId");
		String photoBase64 = json.get("photoBase64");
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
		param.put("teacher_id", teacherId);
		param.put("photo", qiNiuCoreCall.do_main + fileName);
		Integer ret = teacherInfoDao.setPhoto(param);
		if (ret < 1) {
			throw new RuntimeException("照片保存到数据库错误");
		}
	}

	@Override
	public List<Map<String,Object>> searchTeacherSubjects(Integer teacherId) throws Exception {
		Assert.notNull(teacherId);
		return teacherInfoDao.searchTeacherSubjects(teacherId);
	}

	/**
	 * 查询老师课程表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<Map<String, Object>> queryCourseSched(Map<String, Object> paramMap) throws Exception {
		return this.teacherInfoDao.queryCourseSched(paramMap);
	}

	/**
	 * 查询老师日程安排
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> queryTeacherSched(Map<String, Object> paramMap) throws Exception {
		List<Map<String, Object>> teacherSchedList =  this.teacherInfoDao.queryTeacherSched(paramMap);
		if (CollectionUtils.isEmpty(teacherSchedList)) {
			return null;
		}

		Map<String, List<Map<String, Object>>> showMap = new HashMap<String, List<Map<String, Object>>>();
		Map<String, List<Map<String, Object>>> moreMap = new HashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> showList = null;
		List<Map<String, Object>> moreList = null;
		for (Map<String, Object> teacherSchedMap : teacherSchedList) {
			String moreKey = teacherSchedMap.get("xAxis").toString() + "_" + teacherSchedMap.get("yIndex").toString();
			String showKey = moreKey + "_" + teacherSchedMap.get("schedStatusName").toString();
			if (!showMap.containsKey(showKey)) {
				showList = new ArrayList<Map<String, Object>>();
				showList.add(teacherSchedMap);
				showMap.put(showKey, showList);
			}

			if (moreMap.containsKey(moreKey)) {
				moreMap.get(moreKey).add(teacherSchedMap);
			} else {
				moreList = new ArrayList<Map<String, Object>>();
				moreList.add(teacherSchedMap);
				moreMap.put(moreKey, moreList);
			}
		}

		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> groupMap = null;
		List<Map<String, Object>> allShowList = null;
		for (Entry<String, List<Map<String, Object>>> moreEntry : moreMap.entrySet()) {
			groupMap = new HashMap<String, Object>();
			String key = moreEntry.getKey();
			String[] xyAxisArr = key.split("_");
			groupMap.put("x", xyAxisArr[0]);
			groupMap.put("y", xyAxisArr[1]);
			groupMap.put("moreList", moreEntry.getValue());

			allShowList = new ArrayList<Map<String, Object>>();
			String showKey = key + "_预";
			if (showMap.containsKey(showKey)) {
				allShowList.addAll(showMap.get(showKey));
			}

			showKey = key + "_正";
			if (showMap.containsKey(showKey)) {
				allShowList.addAll(showMap.get(showKey));
			}
			groupMap.put("showList", allShowList);
			resultMap.add(groupMap);
		}

		return resultMap;
	}

	/**
	 * 查询教师关联团队
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TeacherTeamRel> searchTeacherTeam(Integer teacherId) throws Exception {
		Assert.notNull(teacherId);
		return teacherInfoDao.searchTeacherTeam(teacherId);
	}

	@Override
	public void changeTeacherStatus(Map<String, Object> param) {
		this.teacherInfoDao.changeTeacherStatus(param);
	}

}
