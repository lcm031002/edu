package com.edu.erp.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.model.TPSubject;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.DataGradeDao;
import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.model.Grade;
import com.github.pagehelper.Page;

@Service(value = "dataGradeService")
public class DataGradeServiceImpl implements DataGradeService{
	
	@Resource(name = "dataGradeDao")
	private DataGradeDao dataGradeDao;
	
	/**
	 * 分页查询
	 * 
	 * @param page 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<Grade> page(Page<Grade> page) throws Exception {
		return dataGradeDao.selectForPage(page);
	}
	
	@Override
	public Page<Map<String, Object>> queryPage(Map<String, Object> paramMap) throws Exception {
		return dataGradeDao.selectForPage(paramMap);
	}
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Grade> list(Map<String, Object> param) throws Exception {
		return dataGradeDao.selectList(param);
	}
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	@Override
	public long toAdd(Grade pojo) throws Exception {
		//0.查看在该团队下是否已经存在该科目
		HashMap<String,Object> param = new HashMap<>();
		param.put("grade_name", pojo.getGrade_name());
		param.put("encoding", pojo.getEncoding());
		param.put("bu_id", pojo.getBu_id());
		Grade grade = dataGradeDao.queryGradeListByNameOrEncoding(param);
		if(grade == null ) {
			Integer ret = dataGradeDao.insert(pojo);
			if(ret < 1)
				throw new RuntimeException("toAdd_error");
		}else{
			pojo.setId(grade.getId());
		}

		return pojo.getId();
	}
	
	/**
	 * 新增
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Override
	public void toAddBuRel(Map<String,Object> data) throws Exception {
		dataGradeDao.deleteData(data);
		Integer ret = dataGradeDao.insertGradeBuRel(data);
		if(ret < 1)
			throw new RuntimeException("toAdd_BuRel_error");
	}
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	@Override
	public void toUpdate(Grade pojo) throws Exception {
		Integer ret = dataGradeDao.update(pojo);
		if(ret < 1)
			throw new RuntimeException("toUpdate_error");
	}

	/**
	 * 根据IDS字符串改变状态
	 * @throws Exception
	 */
	@Override
	public void deleteData(Map<String,Object> param) throws Exception {
		dataGradeDao.deleteData(param);
	}

	@Override
	public String queryGradeNameById(Long id) {
		return dataGradeDao.queryGradeNameById(id);
	}
}
