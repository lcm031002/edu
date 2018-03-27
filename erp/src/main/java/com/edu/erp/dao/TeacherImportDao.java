package com.edu.erp.dao;

import java.util.HashSet;
import java.util.Set;

import com.edu.erp.model.OrganizationInfo;
import com.edu.erp.model.TPSubject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.NameEncodingId;

@Repository(value = "teacherImportDao")
public interface TeacherImportDao {
	
	public HashSet<String> queryExistEncoding(@Param("encodingSet") Set<String> encodingSet, @Param("city_id") Long city_id);
	
	public HashSet<String> queryExistPhone(@Param("encodingSet") Set<String> phoneSet, @Param("city_id") Long city_id);
	
	public Set<NameEncodingId> queryEmployeeNameEncodingId(@Param("employeeEncodings") Set<String> employeeEncodings);
	
	public int isEncodingExisted(@Param("teacherEncoding") String teacherEncoding, @Param("city_id") Long city_id);

	public int isPhoneExisted(@Param("phone") String phone, @Param("city_id") Long city_id);

	public Set<TPSubject> querySubjectOfCity(@Param("cityId") Long cityId);

	public Set<OrganizationInfo> queryBuOfCity(@Param("cityId") Long cityId);
}
