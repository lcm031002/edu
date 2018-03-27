package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TPSubject;
import com.github.pagehelper.Page;

/**
 * 科目dao
 * 
 * @author lpe
 * @date 2014-8-19
 */
@Repository(value = "subjectDao")
public interface SubjectDao {
	/**
	 * 保存数据字典
	 * 
	 * @param dataDictionary
	 *            数据字典对象
	 * @return
	 * @throws Exception
	 */
	void saveData(TPSubject subject) throws Exception;

	Page<TPSubject> selectForPage(Map<String, Object> param) throws Exception;

	/**
	 * @param empIdStr
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteData(Map<String, Object> param) throws Exception;

	/**
	 * @param id
	 *            数据ID
	 * @return DataDictionary 数据字典对象
	 * @throws Exception
	 */
	TPSubject selectById(String id) throws Exception;

	/**
	 * 修改
	 * 
	 * @param dataDictionary
	 *            数据字典对象
	 * @return 空
	 * @throws Exception
	 */
	void updateData(TPSubject dataDictionary) throws Exception;

	List<TPSubject> queryList(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询bu_id团队下是否存在name或者encoding的科目
	 * @param param
	 * @return
	 * @throws Exception
	 */
	TPSubject querySubjectListByNameOrEncoding(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询bu_id下的科目
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Select("SELECT distinct t.* FROM tp_subject t inner join Bu_Dict_Rel bdr on bdr.bu_id = #{bu_id} and bdr.DICT_ID = t.id and bdr.DICT_TYPE = 'tp_subject' WHERE t.STATUS = 1")
	List<TPSubject> querySubjectListByBuID(Map<String, Object> param) throws Exception;
	
	
}
