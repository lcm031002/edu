package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.GcOrder;
import com.edu.erp.model.GcResourceRecProc;

@Repository("gcOrderDao")
public interface GcOrderDao {

	/**
	 * 查看线索跟踪是否已经存在成单
	 * @param param
	 * @return
	 */
	List<GcOrder> queryGcOrderForAdd(Map<String, Object> param);
	
	/**
	 * 订单回填
	 * @param gcOrder
	 */
	void insertGcOrder(GcOrder gcOrder);
	
	/**
	 * 更新线索跟踪记录状态为成单
	 * @param channel_id 渠道id
	 * @param id gc_resource_rec的主键
	 * @param studentName
	 */
	void updateResourceRecToSuccess(@Param("channel_id") Long channel_id, @Param("id") Long id);
	
	/**
	 * 插入资源处理处理记录
	 * @param param
	 */
	void insertResourceProcessRec(GcResourceRecProc gcResourceRecProc);
	
	/**
	 * 查找资源信息记录表
	 * @param id
	 * @return
	 */
	Long queryChannelIdByGcResourceRecId(Long id);
	
	/**
	 * 根据订单id查询订单总课时
	 * @param orderId
	 * @return
	 */
	Integer sumTotalCourseTimes(Long orderId);
	
	/**
	 * 将学员的Id保存到线索跟踪记录中
	 * @param studentId
	 */
	void updateStudentIdToGcResourceRec(@Param("resource_rec_id") Long resource_rec_id, @Param("studentId") Long studentId);

	@Update("update gc_resource set name=#{name,jdbcType=VARCHAR} where id =#{id,jdbcType=NUMERIC} ")
	Integer updateGcResource(@Param("id") Long id, @Param("name") String name);

	@Select("select resource_id from gc_resource_rec where id = #{id,jdbcType=NUMERIC}")
	Long queryResourceIdByResourceRecId(@Param("id") Long resourceRecId);
}
