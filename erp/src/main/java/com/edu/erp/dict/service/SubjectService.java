package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TPSubject;
import com.github.pagehelper.Page;

/**
 * 科目Service
 * 
 * @author lpe
 * @date 2014-8-19
 */
public interface SubjectService {

	/**
	 * 保存科目信息
	 * @Title: save
	 * @Description: 
	 * @param subject 科目对象
	 *      设定文件 void 返回类型
	 *
	 */
	void save(TPSubject subject) throws Exception;

	Page<TPSubject> queryDataList(Map<String,Object> param) throws Exception;

	/**
	 * @return 空
	 * @throws Exception
	 */

	void deleteData(Map<String,Object> param) throws Exception;

	/**
	 * @param id
	 *            数据ID
	 * @return DataDictionary 数据字典对象
	 * @throws Exception
	 */
	TPSubject queryData(String id) throws Exception;

	/**
	 * 修改
	 * 
	 * @param dataDictionary
	 *            数据字典对象
	 * @return 空
	 * @throws Exception
	 */
	void updateData(TPSubject dataDictionary) throws Exception;

	public List<TPSubject> queryList(Map<String, Object> param)
			throws Exception;

	/**
	 * 查询bu_id下的科目
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<TPSubject> querySubjectListByBuID(Map<String, Object> param) throws Exception;
}
