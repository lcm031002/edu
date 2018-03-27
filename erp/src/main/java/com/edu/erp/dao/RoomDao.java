package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TRoom;
import com.github.pagehelper.Page;

/***
 * Description ：教室信息DAO 接口
 * 
 * Author ：liwen。zeng
 *
 * Date : 2017-04-20
 */
@Repository(value = "roomDao")
public interface RoomDao {

	/**
	 * 分页查询教室信息
	 * 
	 * @param paramMap
	 * @return
	 */
	Page<TRoom> pageRoom(Map<String, Object> paramMap) throws Exception;
	
	List<TRoom> listRoom(Map<String, Object> paramMap) throws Exception;
	
	void insertRoom(TRoom room) throws Exception;
	
	void updateRoom(TRoom room) throws Exception;

	TRoom getRoomByIdForUpdate(@Param("id") Long id);

	TRoom queryById(Long id);
}
