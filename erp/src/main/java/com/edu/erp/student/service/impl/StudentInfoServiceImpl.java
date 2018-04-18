/**  
 * @Title: StudentInfoServiceImpl.java
 * @Package com.ebusiness.erp.student.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月14日 下午2:40:44
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.service.impl;

import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.student.business.StudentAccount;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.edu.erp.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.common.util.DateUtil;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.StudentInfoDao;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.erp.student.service.StudentContactService;
import com.edu.erp.student.service.StudentCounselorService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.student.service.StudentIntegralService;
import com.edu.erp.student.service.StudentRelService;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;


/**
 * @ClassName: StudentInfoServiceImpl
 * @Description: 学员信息服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月14日 下午2:40:44
 * 
 */
@Service("studentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService {
	private static final Logger log = Logger.getLogger(StudentInfoServiceImpl.class);

	@Resource(name = "studentInfoDao")
	private StudentInfoDao studentInfoDao;

	@Resource(name = "studentIntegralService")
	private StudentIntegralService studentIntegralService;

	@Resource(name = "studentRelService")
	private StudentRelService studentRelService;

	@Resource(name = "studentContactService")
	private StudentContactService studentContactService;

	@Resource(name = "studentCounselorService")
	private StudentCounselorService studentCounselorService;
	
	@Resource(name = "studentAccountService")
	private StudentAccountService studentAccountService;

	@Resource(name="dataGradeService")
	private DataGradeService dataGradeService;
	
	private QiNiuCoreCall qiNiuCoreCall = QiNiuCoreCall.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#queryStudents
	 * (java.util.Map)
	 */
	@Override
	public Page<StudentInfo> queryStudents(Map<String, Object> queryParam) throws Exception {
		Assert.notNull(queryParam);
		Page<StudentInfo> studentPage = this.studentInfoDao.queryStudents(queryParam);
		if (studentPage != null && CollectionUtils.isNotEmpty(studentPage.getResult())) {
			StringBuilder idBuilder = new StringBuilder();
			for (StudentInfo student : studentPage.getResult()) {
				idBuilder.append(",").append(student.getId());
			}
			queryParam.put("ids", idBuilder.substring(1));
			List<StudentInfo> students = this.studentInfoDao.queryStuCourseSchedCount(queryParam);
			if (CollectionUtils.isNotEmpty(students)) {
				for (StudentInfo student : students) {
					for (StudentInfo stu : studentPage.getResult()) {
						if (student.getId().longValue() == stu.getId().longValue()) {
							stu.setCourse_schedule_count(student.getCourse_schedule_count());
							break;
						}
					}
				}
			}
		}

		return studentPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentIndexAccount (java.util.Map)
	 */
	@Override
	public Map<String, Object> queryStudentIndexAccount(Map<String, Object> queryParam) throws Exception {
		Assert.notNull(queryParam);
		Assert.notNull(queryParam.get("studentId"));
		Assert.notNull(queryParam.get("buId"));
		return studentInfoDao.queryStudentIndexAccount(queryParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentCurrCounselors(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryStudentCurrCounselors(Map<String, Object> queryParam) throws Exception {
		Assert.notNull(queryParam);
		Assert.notNull(queryParam.get("studentId"));
		Assert.notNull(queryParam.get("buId"));
		return studentInfoDao.queryStudentCurrCounselors(queryParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.student.service.StudentInfoService#queryStudentOrders
	 * (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryStudentOrders(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("studentId"));
		Assert.notNull(param.get("buId"));
		Assert.notNull(param.get("beginDate"));
		Assert.notNull(param.get("endDate"));
		return studentInfoDao.queryStudentOrders(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentOrdersDetail (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryStudentOrdersDetail(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("studentId"));
		Assert.notNull(param.get("buId"));
		return studentInfoDao.queryStudentOrdersDetail(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.student.service.StudentInfoService#checkStudentName
	 * (java.util.Map)
	 */
	@Override
	public boolean checkStudentName(Map<String, Object> paramMap) throws Exception {
		if (StringUtils.isEmpty((String) paramMap.get("studentName"))) {
			return true;
		}
		Integer ret = null;
		ret = studentInfoDao.checkStudentName(paramMap);
		if (ret == null || ret.intValue() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 校验学生联系电话与联系方式号码是否重复
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkStudentContactPhone(Map<String, Object> paramMap) throws Exception {
		if (StringUtils.isEmpty((String) paramMap.get("phone"))) {
			return true;
		}
		Integer ret = null;
		ret = studentInfoDao.checkStudentContactPhone(paramMap);
		if (ret == null || ret.intValue() > 0) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.student.service.StudentInfoService#updateStudentInfo
	 * (com.ebusiness.erp.model.StudentInfo)
	 */
	@Override
	public void updateStudentInfo(StudentInfo studentInfo) throws Exception {
		Assert.notNull(studentInfo);
		Assert.notNull(studentInfo.getId(),"参数异常，缺少学员id");
		Assert.notNull(studentInfo.getUpdate_user());
		Assert.hasText(studentInfo.getStudent_name(),"学员姓名必填");
		Assert.notNull(studentInfo.getSex(),"性别必填");
		Assert.notNull(studentInfo.getAttend_school_id(),"就读学校必填");
		Assert.notNull(studentInfo.getGrade_id(),"年级必填");
		Assert.notNull(studentInfo.getStudent_status(),"学员状态必填");
		if (log.isDebugEnabled()) {
			log.debug("begin to update student,updater is " + studentInfo.getUpdate_user());
		}
		if(studentInfo.getStudent_status()==2){
			Map<String,Object> queryParam = new HashMap<String,Object>();
			queryParam.put("studentName",studentInfo.getStudent_name());
			queryParam.put("id",studentInfo.getId());
			int sameNameNumber = studentInfoDao.checkStudentName(queryParam);
			queryParam.clear();
			queryParam.put("id",studentInfo.getId());
			queryParam.put("phone",studentInfo.getPhone());
			int samePhoneNumber = studentInfoDao.checkStudentselfPhone(queryParam);
			if(sameNameNumber==0&&samePhoneNumber==0){
				throw new Exception("该生没有重复信息，状态无法设置为重复!");
			}
		}
		//判断学员姓名以及年级是否有改变
		Map<String,Object> queryparam = new HashMap<String,Object>();
		Long studentId = studentInfo.getId();
		Long buId = studentInfo.getBu_id();
		queryparam.put("studentId",studentId);
		queryparam.put("buId",buId);
		StudentInfo studentInfoCheck = studentInfoDao.queryStudentById(queryparam);
		String studentName = studentInfoCheck.getStudent_name();
		Long studentGradeId = studentInfoCheck.getGrade_id();
		//判断学生姓名是否有变化有则导入
        if(!studentInfo.getStudent_name().trim().equals(studentName)){
        	Long operatorId = studentInfo.getUpdate_user();
        	String updateTime = DateUtil.dateToString(studentInfo.getUpdate_time(),"yyyy-MM-dd");
			StudentNameReviseNote studentNameReviseNote = new StudentNameReviseNote();
			studentNameReviseNote.setStudentId(studentInfo.getId());
			studentNameReviseNote.setOperatorId(operatorId);
			studentNameReviseNote.setOriginalStudentName(studentName);
			studentNameReviseNote.setStudentName(studentInfo.getStudent_name());
			studentNameReviseNote.setUpdateTime(updateTime);
        	studentInfoDao.saveStudentNameRecord(studentNameReviseNote);
		}
		//判断学生年级是否有变化有则导入
		if(!studentInfo.getGrade_id().equals(studentGradeId)){
			Long operatorId = studentInfo.getUpdate_user();
			String updateTime = DateUtil.dateToString(studentInfo.getUpdate_time(),"yyyy-MM-dd");
//			String originalStudentGrade = dataGradeService.queryGradeNameById(studentGradeId);
//			String gradeName = dataGradeService.queryGradeNameById(studentInfo.getGrade_id());
			String originalStudentGrade = "";
			String gradeName = "";
			StudentGradeReviseNote studentGradeReviseNote = new StudentGradeReviseNote();
			studentGradeReviseNote.setStudentId(studentInfo.getId());
			studentGradeReviseNote.setOperatorId(operatorId);
			studentGradeReviseNote.setStudentGrade(gradeName);
			studentGradeReviseNote.setOriginalStudentGrade(originalStudentGrade);
			studentGradeReviseNote.setUpdateTime(updateTime);
			studentInfoDao.saveStudentGradeRecord(studentGradeReviseNote);
		}
		   //插入学生状态表
			Long updateUserId = studentInfo.getUpdate_user();
			StudentStatusInfo studentStatusInfo = new StudentStatusInfo();
			studentStatusInfo.setStudentId(studentId);
			studentStatusInfo.setStatus(studentInfo.getStudent_status());
			studentStatusInfo.setUpdateUserId(updateUserId);
			studentStatusInfo.setBuId(studentInfo.getBu_id());
			String updateTime = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			studentStatusInfo.setUpdateTime(updateTime);
			//判断该学生在这团队下是否存在
		    StudentStatusInfo studentStatusInfo1 = studentInfoDao.queryStudentStatusInfo(queryparam);
			if (studentStatusInfo1!= null) {
				//判断学员状态是否有改变，有则保存到状态流水表
				if(studentStatusInfo1.getStatus()!=null&&!studentStatusInfo1.getStatus().equals(studentInfo.getStudent_status())){
					studentInfoDao.updateStudentStatus(studentStatusInfo);
					StudentStatusReviseNote studentStatusReviseNote = new StudentStatusReviseNote();
					studentStatusReviseNote.setBeforeStatus(studentStatusInfo1.getStatus());
					studentStatusReviseNote.setAfterStatus(studentInfo.getStudent_status());
					studentStatusReviseNote.setStudentId(studentId);
					studentStatusReviseNote.setBuId(buId);
					studentStatusReviseNote.setUpdateUser(studentInfo.getUpdate_user());
					studentStatusReviseNote.setUpdateTime(updateTime);
					studentStatusReviseNote.setIfShow(1);
					studentInfoDao.saveStudentStatusReviseNote(studentStatusReviseNote);
				}
			} else {
				studentInfoDao.saveStudentStatusInfo(studentStatusInfo);
			}

		studentInfoDao.updateStudent(studentInfo);

		//维护新老学员关系
		//删除原有的新老学员关系
		delStudentRel(studentInfo.getId());
		Map<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("STUDENT_ID_NEW", studentInfo.getId());
		dataObj.put("STUDENT_ID_OLD", studentInfo.getStudent_id_old());
		dataObj.put("NEW_USED", 0);
		dataObj.put("OLD_USED", 0);
		insertStudentRel(dataObj);
		if (log.isDebugEnabled()) {
			log.debug("end to update student.");
		}
		//在学员推荐表中插入一条记录
		//-获取学员的最近推荐人
		ReferenceStudent referenceStudent = studentInfoDao.queryNewestRecord(studentInfo.getId());
		//-如果学员推荐人和历史最近推荐人信息不一样，则将学员推荐人归档
		ReferenceStudent var = new ReferenceStudent();
		var.setStudent_id(studentInfo.getId());
		var.setReference_student_id(studentInfo.getStudent_id_old() );
		var.setCreate_time(new Timestamp(System.currentTimeMillis()));
		var.setCreate_user(studentInfo.getUpdate_user());
		if(referenceStudent == null) {//如果不存在历史记录，则插入一条
			studentInfoDao.insertNewReferenceStudent(var);
		} else {
			if(studentInfo.getStudent_id_old() == null) {
				if(referenceStudent.getReference_student_id() !=null) {
					studentInfoDao.insertNewReferenceStudent(var);
				}
			} else if(!studentInfo.getStudent_id_old().equals(referenceStudent.getReference_student_id())) {
				studentInfoDao.insertNewReferenceStudent(var);
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentOrdersBJK (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryStudentOrdersBJK(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("studentId"));
		Assert.notNull(param.get("buId"));
		return studentInfoDao.queryStudentOrdersBJK(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentOrdersBJK (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryStudentOrdersWFD(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("studentId"));
		Assert.notNull(param.get("buId"));
		return studentInfoDao.queryStudentOrdersWFD(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.student.service.StudentInfoService#queryStudentCourse
	 * (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryStudentCourse(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("buId"));
		Assert.notNull(param.get("studentId"));
		Assert.notNull(param.get("businessType"));

		return studentInfoDao.queryStudentCourse(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentCourseTimes (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryStudentCourseTimes(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("studentId"));
		Assert.notNull(param.get("courseId"));

		return studentInfoDao.queryStudentCourseTimes(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.student.service.StudentInfoService#queryStudentById
	 * (java.util.Map)
	 */
	@Override
	public StudentInfo queryStudentById(Map<String, Object> queryParam) throws Exception {
		Assert.notNull(queryParam);
		Assert.notNull(queryParam.get("studentId"));
		StudentInfo studentInfo = studentInfoDao.queryStudentById(queryParam);

		if (studentInfo != null) {
			// 积分账户
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("student_id", studentInfo.getId());
			param.put("bu_id", studentInfo.getBu_id());
			List<StudentIntegral> studentIntegralList = studentIntegralService.queryStudentIntegral(param);
			studentInfo.setStudentIntegralList(studentIntegralList);

			// 我是推荐人
			Map<String, Object> queryRelParam = new HashMap<String, Object>();
			queryRelParam.put("STUDENT_ID_OLD", studentInfo.getId());
			List<StudentRel> myRels = studentRelService.queryStudentRel(queryRelParam);
			studentInfo.setMyRels(myRels);

			// 我是被推荐人
			queryRelParam = new HashMap<String, Object>();
			queryRelParam.put("STUDENT_ID_NEW", studentInfo.getId());
			List<StudentRel> relsMy = studentRelService.queryStudentRel(queryRelParam);
			studentInfo.setRelsMy(relsMy);

		}
		return studentInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.student.service.StudentInfoService#
	 * queryStudentAllCourseTimes(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryStudentAllCourseTimes(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("student_id"));
		Assert.notNull(param.get("bu_id"));
		Map<String, Object> allCourseTimes = studentInfoDao.queryStudentAllCourseTimes(param);
		return allCourseTimes;
	}

	@Override
	public List<Map<String, Object>> queryStudentCourseWfd(Map<String, Object> paramMap) throws Exception {
		return this.studentInfoDao.queryStudentCourseWfd(paramMap);
	}

	/**
	 * 
	 * @Description: 根据学生ID查询学生信息
	 * @param studentInfo
	 *            学生ID
	 * @return StudentInfo 返回类型
	 * @throws Exception
	 *             异常信息
	 */
	@Override
	public StudentBusiness selectOneStudent(StudentBusiness studentInfo) throws Exception {
		StudentBusiness studentBusiness = studentInfoDao.selectOneStudent(studentInfo);
		if (null != studentBusiness && null != studentBusiness.getIntegral()) {
			studentBusiness.setIs_old_student(studentBusiness.getIntegral().intValue() >= 20 ? 1 : 0);
		}
		return studentBusiness;
	}

	@Override
	public void upgradeStudentGrade(String isSummerNode) throws Exception {
		Assert.notNull(isSummerNode);
		if (!(Constants.YES.equals(isSummerNode) || Constants.NO.equals(isSummerNode))) {
			throw new Exception("参数值不正确，升级学习年级失败");
		}
		this.studentInfoDao.addGradeUpgradeHis(isSummerNode);
		this.studentInfoDao.upgradeStudentGrade(isSummerNode);
	}

	/**
	 * 新增学生
	 * 
	 * @param studentInfo
	 * @throws Exception
	 */
	@Override
	public void addStudentInfo(StudentInfo studentInfo) throws Exception {
		Assert.hasText(studentInfo.getStudent_name(), "请输入学生姓名");
		studentInfoDao.addStudentInfo(studentInfo);
	}
	
	@Override
	public Integer insertStudentRel(Map<String, Object> paramMap)
			throws Exception {
		return studentInfoDao.insertStudentRel(paramMap);
	}
	@Override
	public void delStudentRel(Long id) throws Exception {
		studentInfoDao.delStudentRel(id);
	}

	@Override
	public void toAdd(StudentInfo studentInfo, HttpServletRequest request) throws Exception {
		//校验必填
		Assert.hasText(studentInfo.getStudent_name(),"学员姓名必填");
		Assert.notNull(studentInfo.getSex(),"性别必填");
		Assert.notNull(studentInfo.getAttend_school_id(),"就读学校必填");
		Assert.notNull(studentInfo.getGrade_id(),"年级必填");
		Assert.notNull(studentInfo.getStudent_status(),"学员状态必填");

		Account user = WebContextUtils.genUser(request.getSession());
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		//新增学员
		studentInfo.setStatus(1);
		studentInfo.setCity_id(orgModel.getCityId());
		studentInfo.setBranch_id(orgModel.getId());
		studentInfo.setBu_id(orgModel.getBuId());
		studentInfo.setEncoding(EncodingSequenceUtil.getSequenceNum(27L));
		studentInfo.setCreate_user(user.getId());
		studentInfo.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
		addStudentInfo(studentInfo);
		Long studentId= studentInfo.getId();
		//在添加时将学员状态同步更新到学员状态表
		StudentStatusInfo studentStatusInfo = new StudentStatusInfo();
        studentStatusInfo.setStudentId(studentId);
        studentStatusInfo.setStatus(studentInfo.getStatus());
        studentStatusInfo.setBuId(studentInfo.getBu_id());
        studentStatusInfo.setUpdateUserId(studentInfo.getCreate_user());
        String updateTime = DateUtil.dateToString(studentInfo.getCreate_time(),"yyyy-MM-dd");
        studentStatusInfo.setUpdateTime(updateTime);
		studentInfoDao.saveStudentStatusInfo(studentStatusInfo);
		//由于报表需要,新增学员时需要把初始记录存入状态变更表
        StudentStatusReviseNote studentStatusReviseNote = new StudentStatusReviseNote();
        studentStatusReviseNote.setIfShow(0);
        studentStatusReviseNote.setAfterStatus(1);
        studentStatusReviseNote.setBeforeStatus(1);
        studentStatusReviseNote.setStudentId(studentId);
        studentStatusReviseNote.setBuId(studentInfo.getBu_id());
        studentStatusReviseNote.setUpdateTime(updateTime);
        studentStatusReviseNote.setUpdateUser(studentInfo.getCreate_user());
        studentInfoDao.saveStudentStatusReviseNote(studentStatusReviseNote);
		//添加学员联系方式
		for(StudentContact sc : studentInfo.getStudentContactList()){
			sc.setStudent_id(studentInfo.getId());
			sc.setCreate_user(user.getId());
			sc.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			sc.setIs_valid(StudentContact.ValidEnum.VALID.getCode());
			studentContactService.toAdd(sc);
			//如果电话号码是学员自己的
			if("5".equals(sc.getRelation())) {
				//设置学生上的联系人信息
				studentInfo.setContact_id(sc.getId());
				studentInfo.setRelation_name(sc.getRelation_name());	
				//设置学生上的联系人电话
				studentInfo.setPhone(sc.getLink_phone());
			}
			
		}
		//如果学员没有自己的电话号码，则绑定传入的第一个号码
		//设置学生上的联系人信息
		if(StringUtils.isEmpty(studentInfo.getPhone())){
			StudentContact sc = studentInfo.getStudentContactList().get(studentInfo.getStudentContactList().size()-1);
			//设置学生上的联系人信息
			studentInfo.setContact_id(sc.getId());
			studentInfo.setRelation_name(sc.getRelation_name());	
			//设置学生上的联系人电话
			studentInfo.setPhone(sc.getLink_phone());
		}
		studentInfo.setUpdate_user(user.getId());
		updateStudentInfo(studentInfo);
		
		//添加咨询师
		StudentCounselorInfo studentCounselorInfo = studentInfo.getCounselor();
		if(null != studentCounselorInfo) {
			Assert.notNull(studentCounselorInfo.getCounselor_id(),"咨询师编码必填");
			Assert.notNull(studentCounselorInfo.getStart_date(),"起始日期必填");
			Assert.notNull(studentCounselorInfo.getEnd_date(),"截至日期必填");
			
			studentCounselorInfo.setCreate_user(user.getId());
			studentCounselorInfo.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			studentCounselorInfo.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			studentCounselorInfo.setUpdate_user(user.getId());
			studentCounselorInfo.setBu_id(orgModel.getBuId());
			studentCounselorInfo.setBranch_id(orgModel.getId());
			studentCounselorInfo.setIs_valid(1);
			studentCounselorInfo.setStudent_id(studentInfo.getId());
			studentCounselorInfo.setCounselor_type(1L);
			studentCounselorService.toAdd(studentCounselorInfo);
		}	
		
		//添加学管师
		StudentCounselorInfo govern = studentInfo.getGovern();
		if (null != govern) {
			Assert.notNull(govern.getCounselor_id(),"学管师编码必填");
			Assert.notNull(govern.getStart_date(),"起始日期必填");
			Assert.notNull(govern.getEnd_date(),"截至日期必填");
			govern.setCreate_user(user.getId());
			govern.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			govern.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			govern.setUpdate_user(user.getId());
			govern.setBu_id(orgModel.getBuId());
			govern.setBranch_id(orgModel.getId());
			govern.setCounselor_type(2L);
			govern.setIs_valid(1);
			govern.setStudent_id(studentInfo.getId());
			studentCounselorService.toAdd(govern);
		}
		//维护新老学员关系
		if (studentInfo.getStudent_id_old() != null) {
			Map<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("STUDENT_ID_NEW", studentInfo.getId());
			dataObj.put("STUDENT_ID_OLD", studentInfo.getStudent_id_old());
			dataObj.put("NEW_USED", 0);
			dataObj.put("OLD_USED", 0);
			insertStudentRel(dataObj);
		}
//		//插入学员推荐人历史
//		ReferenceStudent var = new ReferenceStudent();
//		var.setStudent_id(studentInfo.getId());
//		var.setReference_student_id(studentInfo.getStudent_id_old() );
//		var.setCreate_time(new Timestamp(System.currentTimeMillis()));
//		var.setCreate_user(studentInfo.getUpdate_user());
//		studentInfoDao.insertNewReferenceStudent(var);
		
		// 创建学生账户
        studentAccountService.createAccount(studentInfo.getId(), studentInfo.getBu_id(),
                studentInfo.getCreate_user());

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
		String head_pic = qiNiuCoreCall.do_main + fileName;
		param.put("head_pic", head_pic);
		Integer ret = studentInfoDao.modifyPhoto(param);
		if (ret < 1) {
			throw new RuntimeException("照片保存到数据库错误");
		}
		return head_pic;
	}
	
	@Override
	public StudentInfo queryStudentById(Long id) throws Exception {
		HashMap<String, Object> param = new HashMap<>();
		param.put("studentId", id);
		return studentInfoDao.queryStudentById(param);
	}

	@Override
	public StudentInfo queryStudentByOrderId(Long orderId) throws Exception {
		return this.studentInfoDao.queryStudentByOrderId(orderId);
	}

	@Override
	public void updateStudentContact(StudentInfo studentInfo) throws Exception {
		Assert.notNull(studentInfo);
		Assert.notNull(studentInfo.getId(),"参数异常，缺少学员id");
		Assert.notNull(studentInfo.getUpdate_user());
		studentInfo.setPhone_verify(studentInfo.getPhone_verify() == null ? 0L:studentInfo.getPhone_verify());
		if (log.isDebugEnabled()) {
			log.debug("begin to update student contact,updater is " + studentInfo.getUpdate_user());
		}
		studentInfoDao.updateStudentContace(studentInfo);
	}
	
	@Override
	public List<ReferenceStudent> queryReferenceStudentHt(Long studentId)throws Exception {
		return studentInfoDao.queryReferenceStudentHt(studentId);
	}

	@Override
	public List<StudentInfo> queryStudentByNameAndPhone(String studentName, String phones, Long studentId) {
		return studentInfoDao.queryStudentByNameAndPhone(studentName, phones, studentId);
	}

	@Override
	public void updateStudentLoginNo(StudentInfo studentInfo) throws Exception{
		Assert.notNull(studentInfo);
		Assert.notNull(studentInfo.getId());
		Assert.notNull(studentInfo.getLogin_no());
		this.studentInfoDao.updateStudentLoginNo(studentInfo);
	}

	@Override
	public List<StudentAccount> queryStudentAccounts(Map<String, Object> paramMap) throws Exception {
		return this.studentInfoDao.queryStudentAccounts(paramMap);
	}

	@Override
	public boolean isStudentActive(Long studentId) {
		Integer active = this.studentInfoDao.queryStuActiveValue(studentId);
		return (active == null || active.intValue() == 0) ? false : true;
	}

}
