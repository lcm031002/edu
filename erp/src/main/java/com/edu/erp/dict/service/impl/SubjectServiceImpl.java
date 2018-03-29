package com.edu.erp.dict.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.dao.SubjectDao;
import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.dict.service.SubjectService;
import com.edu.erp.model.TPSubject;
import com.github.pagehelper.Page;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

	@Resource(name = "subjectDao")
	private SubjectDao subjectDao;
	
	@Resource(name = "dataGradeService")
	private DataGradeService dataGradeService;
	
	@Override
	public void save(TPSubject subject) throws Exception {
		//0.查看在该团队下是否已经存在该科目
		HashMap<String,Object> param = new HashMap<>();
		param.put("name", subject.getName());
		param.put("encoding", subject.getEncoding());
		param.put("bu_id", subject.getBuId());
		TPSubject  tpSubject= subjectDao.querySubjectListByNameOrEncoding(param);
		if(tpSubject == null ) {
			subjectDao.saveData(subject);
		}else{
			subject.setId(tpSubject.getId());
		}

		//2.保存 团队科目关系
		Map<String, Object> buRel = new HashMap<String, Object>();
		buRel.put("bu_id", subject.getBuId());
		buRel.put("dict_type", "tp_subject");
		buRel.put("dict_id", subject.getId());
		subjectDao.deleteData(buRel);
		dataGradeService.toAddBuRel(buRel);
	}
	
	@Override
	public Page<TPSubject> queryDataList(Map<String,Object> param) throws Exception {
		
		return subjectDao.selectForPage(param);
	}

	@Override
	public void deleteData(Map<String,Object> param) throws Exception {
		subjectDao.deleteData(param);
	}

	@Override
	public TPSubject queryData(String id) throws Exception 
	{
		return subjectDao.selectById(id);
	}

	@Override
	public void updateData(TPSubject subject) throws Exception 
	{
		subjectDao.updateData(subject);
	}

	@Override
	public List<TPSubject> queryList(Map<String, Object> param) throws Exception 
	{
		return subjectDao.queryList(param);
	}
	
	@Override
	public List<TPSubject> querySubjectListByBuID(Map<String, Object> param) throws Exception 
	{
		return subjectDao.querySubjectListByBuID(param);
	}

/*	*//**
	 * 
	 * @Description: 查询科目
	 * @param  businessType 业务模型
	 * @param  city_id 城市ID
	 * @throws Exception
	 *             设定文件
	 *             
	 * @return List<DictionaryBusiness> 返回类型
	 *//*
	@Override
	public List<DictionaryBusiness> querySeasons(Long businessType,Long city_id,Long product_line,Long student_id,Long bu_id) throws Exception {
		return this.subjectDao.querySeasons(businessType,city_id, product_line,student_id,bu_id);
	}*/

}