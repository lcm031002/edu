package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabEbCoupon;
import com.edu.erp.model.TabEbCouponCourse;
import com.github.pagehelper.Page;

@Repository("tabEbCouponDao")
public interface TabEbCouponDao {
    Page<Long> queryForPage(Map<String, Object> paramMap) throws Exception;

    List<TabEbCoupon> queryForList(Map<String, Object> paramMap) throws Exception;

    void add(TabEbCoupon tabEbCoupon) throws Exception;
    
    void addCouponCourse(List<TabEbCouponCourse> couponCourseList) throws Exception;
    
    void deleteCouponCourse(Long couponId) throws Exception;

    void update(TabEbCoupon tabEbCoupon) throws Exception;

    void changeStatus(TabEbCoupon tabEbCoupon) throws Exception;
}
