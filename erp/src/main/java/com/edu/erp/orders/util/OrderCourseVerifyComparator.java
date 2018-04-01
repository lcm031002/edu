package com.edu.erp.orders.util;

import com.edu.common.util.NumberUtils;
import java.util.Comparator;
import java.util.Map;

public class OrderCourseVerifyComparator implements Comparator<Map<String, Object>> {

    @Override
    public int compare(Map<String, Object> courseSeqMap1, Map<String, Object> courseSeqMap2) {
        String courseId1 = courseSeqMap1.get("courseId").toString();
        String seq1 = courseSeqMap1.get("seq").toString();
        String verifyResult1 = courseSeqMap1.get("verifyResult").toString();
//        Long studentId1 = NumberUtils.object2Long(courseSeqMap1.get("erpStudentId"));
//        if (studentId1 == null) {
//            studentId1 = NumberUtils.object2Long(courseSeqMap1.get("mtStudentId"));
//        }
        String value1 = courseId1 + seq1 + verifyResult1;

        String courseId2 = courseSeqMap2.get("courseId").toString();
        String seq2 = courseSeqMap2.get("seq").toString();
        String verifyResult2 = courseSeqMap2.get("verifyResult").toString();
//        Long studentId2 = NumberUtils.object2Long(courseSeqMap2.get("erpStudentId"));
//        if (studentId2 == null) {
//            studentId2 = NumberUtils.object2Long(courseSeqMap2.get("mtStudentId"));
//        }
        String value2 = courseId2 + seq2 + verifyResult2;
        return Long.valueOf(value1).compareTo(Long.valueOf(value2));
    }

}
