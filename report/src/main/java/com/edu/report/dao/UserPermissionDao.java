package com.edu.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userPermissionDao")
public interface UserPermissionDao {
	 /**
     * 查询在某个团队下的具有权限的校区
     * @param buId
     * @param userId
     * @return
     */
    List<Long> queryValidBranchIdsInBu(@Param("buId") Long buId,@Param("userId") Long userId);
}
