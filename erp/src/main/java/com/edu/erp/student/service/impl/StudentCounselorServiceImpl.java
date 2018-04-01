package com.edu.erp.student.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.model.BaseObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.StudentCounselorDao;
import com.edu.erp.model.EmpOrgPost;
import com.edu.erp.model.StudentCounselorInfo;
import com.edu.erp.student.service.StudentCounselorService;
import com.github.pagehelper.Page;
import org.springframework.util.CollectionUtils;

@Service(value = "studentCounselorService")
public class StudentCounselorServiceImpl implements StudentCounselorService {

	private static final Logger log = Logger.getLogger(StudentCounselorServiceImpl.class);

	@Resource(name = "studentCounselorDao")
	private StudentCounselorDao studentCounselorDao;

	@Override
	public Page<StudentCounselorInfo> page(Map<String, Object> queryParam) throws Exception {
		return studentCounselorDao.selectForPage(queryParam);
	}

	@Override
	public Page<EmpOrgPost> selectForToPage(Map<String, Object> queryParam) throws Exception {
		return studentCounselorDao.selectForToPage(queryParam);
	}

	@Override
	public Page<EmpOrgPost> selectForFromPage(Map<String, Object> queryParam) throws Exception {
		return studentCounselorDao.selectForFromPage(queryParam);
	}

	/**
	 * 查询List
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 */
	@Override
	public Page<StudentCounselorInfo> list(Map<String, Object> param) throws Exception {
		return studentCounselorDao.selectList(param);
	}

	/**
	 * 新增
	 * 
	 * @param studentCounselorInfo
	 * @throws Exception
	 */
	@Override
	public void toAdd(StudentCounselorInfo studentCounselorInfo) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("student_id", studentCounselorInfo.getStudent_id());
		param.put("bu_id", studentCounselorInfo.getBu_id());
		param.put("branch_id", studentCounselorInfo.getBranch_id());
		param.put("counselor_type", studentCounselorInfo.getCounselor_type());
		param.put("start_date", studentCounselorInfo.getStart_date());
		param.put("end_date", studentCounselorInfo.getEnd_date());
		
		Date start_date = DateUtil.stringToDate(studentCounselorInfo.getStart_date(), "yyyy-MM-dd");
		Date end_date = DateUtil.stringToDate(studentCounselorInfo.getEnd_date(), "yyyy-MM-dd");
		if(end_date.before(start_date)) {
			throw new RuntimeException("截止时间必须大于等于开始时间");
		} else {
			if(start_date.after(DateUtil.getCurrDateOfDate("yyyy-MM-dd"))) {
				throw new RuntimeException("开始时间必须小于等于今天");
			}
		}
		
		// 获取该学员最新的咨询学管师
		StudentCounselorInfo prevCounselor = studentCounselorDao.queryNewest(param);
		if(prevCounselor != null) {
			if(!start_date.after(DateUtil.stringToDate(prevCounselor.getStart_date(),"yyyy-MM-dd"))) {
				throw new RuntimeException("上一个咨询师（学管师）的有效期必须大于等于1天");
			}
			prevCounselor.setEnd_date(DateUtil.dateToString(DateUtil.addDate(start_date, -1), "yyyy-MM-dd"));
			prevCounselor.setIs_valid(StudentCounselorInfo.StatusEnum.INVALID.getCode());
			Integer ret2 = studentCounselorDao.update(prevCounselor);
			if (ret2 < 1)
				throw new RuntimeException("toUpdate_error");
		}
		//studentCounselorDao.inValidAllCounselor(param);
		Integer ret = studentCounselorDao.insert(studentCounselorInfo);
		if (ret < 1)
			throw new RuntimeException("toAdd_error");
	}

	/**
	 * 根据ID修改
	 * 
	 * 
	 * @param studentCounselorInfo
	 * @throws Exception
	 */
	@Override
	public void toUpdate(StudentCounselorInfo studentCounselorInfo) throws Exception {
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("id", studentCounselorInfo.getId());
//		param.put("student_id", studentCounselorInfo.getStudent_id());
//		param.put("bu_id", studentCounselorInfo.getBu_id());
//		param.put("branch_id", studentCounselorInfo.getBranch_id());
//		param.put("counselor_type", studentCounselorInfo.getCounselor_type());
//		param.put("start_date", studentCounselorInfo.getStart_date());
//		param.put("end_date", studentCounselorInfo.getEnd_date());
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
//		
//		if(simpleDateFormat.parse(studentCounselorInfo.getEnd_date()).before(simpleDateFormat.parse(studentCounselorInfo.getStart_date()))) {
//			throw new RuntimeException("截止时间必须大于等于开始时间");
//		}
//		//判断新插入的咨询师（如果是学管师，需要根据branch_id过滤） 是否和数据库中已经存在的学员的咨询师（学管师）的日期交叉
//		Integer count = studentCounselorDao.isDateIntersect(param);
//		if(count>0) {
//			throw new RuntimeException("上一个咨询师（学管师）的有效期必须大于等于1天");
//		}
//		Integer ret = studentCounselorDao.update(studentCounselorInfo);
//		if (ret < 1)
//			throw new RuntimeException("toUpdate_error");
	}

	/**
	 * 根据ID修改状态
	 * 
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void toChangeStatus(String ids, Integer status) throws Exception {

	}

	@Override
	public void toTransfer(String studentConselorId, String newConselorId) throws Exception {
		java.util.Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", studentConselorId);
		StudentCounselorInfo info = studentCounselorDao.selectOne(param);
		if (info != null) {
			if(!DateUtil.stringToDate(info.getStart_date(), "yyyy-MM-dd").before(DateUtil.getCurrDateOfDate("yyyy-MM-dd"))) {
				throw new RuntimeException("转入学管师的生效开始时间和转出学管师的生效开始时间重叠");
			}
			info.setEnd_date(DateUtil.dateToString(DateUtil.addDate(DateUtil.getCurrDateOfDate("yyyy-MM-dd"), -1), "yyyy-MM-dd"));
			info.setIs_valid(StudentCounselorInfo.StatusEnum.INVALID.getCode());
			studentCounselorDao.update(info);
			info.setIs_valid(StudentCounselorInfo.StatusEnum.VALID.getCode());
			info.setCounselor_id(Long.valueOf(newConselorId));
			info.setCreate_time(Calendar.getInstance().getTime());
			info.setStart_date(DateUtil.getCurrDate(DateUtil.DATE_FORMAT));
			info.setEnd_date(DateUtil.dateToString(DateUtil.getDateOfYearsAgoOrLater(2), DateUtil.DATE_FORMAT));
			studentCounselorDao.insert(info);
			log.info("学生归属学管师转移成功");
		} else {
			throw new Exception("ID:" + studentConselorId + ",对应的学生学管师记录不存在");
		}
	}

	@Override
	public Page<StudentCounselorInfo> queryStudentListPage(Map<String, Object> queryParam) throws Exception {
		return studentCounselorDao.queryStudentListPage(queryParam);
	}

	private List<StudentCounselorInfo> querStudentCounselorInfoByCounselorId(String counselor_id) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("counselor_id", counselor_id);
		return studentCounselorDao.querStudentCounselorInfoByCounselorId(param);
	}

	/**
	 * 学员批量转移，把某个学管师名下的学员全部转移到另外一个学管师
	 */
	public void toBatchTransfer(String p_from_conselor_id, String p_to_conselor_id) throws Exception {
		List<StudentCounselorInfo> list = this.querStudentCounselorInfoByCounselorId(p_from_conselor_id);
		if (!CollectionUtils.isEmpty(list)) {
			for (StudentCounselorInfo info : list) {
				try {
					this.toTransfer(String.valueOf(info.getStu_counselor_id()), p_to_conselor_id);
				} catch (Exception e) {
					log.error("Excetion：", e);
				}
			}
		}
	}

}
