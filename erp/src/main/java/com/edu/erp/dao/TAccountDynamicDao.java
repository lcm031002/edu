package com.edu.erp.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TAccountDynamic;
import com.github.pagehelper.Page;

/**
 * @ClassName: TAccountDynamicDao
 * @Description: 账户单据信息
 *
 */

@Repository(value = "tAccountDynamicDao")
public interface TAccountDynamicDao {

	/**
	 * 分页查询账户单据
	 * 
	 * @param pageParam
	 * @return
	 */
	public Page<TAccountDynamic> pageQueryStudentAccountDynamic(
            Map<String, Object> pageParam) throws Exception;

	/**
	 * 根据dynamicId查询账户动态信息
	 * @param dynamicId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryInfoByDynamicId(Long dynamicId) throws Exception;
	
	/***
	 * 学生 充值 理赔 取款作废
	 * @param param
	 *  入参：p_dynamic_id        :账户变动记录Id
	 *       p_encoding          :记录编号
	 *       p_input_user        :操作人
	 *       p_remark            :备注
	 *  出参：o_err_code          :错误编号
	 *       o_err_desc          :错误描述
	 * @throws Exception
	 */
	void accountDynamicCancel(Map<String, Object> param)throws Exception;

	/**
	 * 新增修改历史
	 */
	Integer addReChargeHis(Map<String, Object> His) throws Exception;

	/**
	 * 新增刷卡转账充值
	 */
	Integer addAccountCharge(Map<String, Object> rechangeInfo) throws Exception;

	/**
	 * 修改POS机和转账卡号
	 */
	public int updateReCharge(Map<String, Object> params);
}
