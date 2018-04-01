package com.edu.erp.orders.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.erp.dao.AttendanceDao;
import com.edu.erp.model.TabOrderInfo;
import com.edu.erp.util.RenewalCfg;

public abstract class OrderRenewalService {
    private static Logger log = LogManager.getLogger(OrderRenewalService.class);

    // 默认续单判断逻辑
    private static void defaultRenewalBusi(TabOrderInfo orderInfo) {
        // 三个月的1次考勤以上认为是续单
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("student_id", orderInfo.getStudent_id());
            AttendanceDao attendanceDao = ApplicationContextUtil.<AttendanceDao> getBean("attendanceDao");
            Integer count = attendanceDao.countAttendByStudent(paramMap);
            if (count > 1) {
                orderInfo.setOrder_type(4L);
            }
        } catch (Exception e) {
            log.error("考勤查询错误！" + e);
        }
    }

    /**
     * 写续单判断逻辑
     * 
     * @param ob
     *            订单对象
     */
    public abstract void renewalBusi(TabOrderInfo orderInfo);

    /**
     * 是否续单设置
     * 
     * @param orderInfo
     */
    public static void processRenewal(TabOrderInfo orderInfo) {
        // 1、 取团队和类的名称
        String beanName = RenewalCfg.getInstance().getConfigItem(String.valueOf(orderInfo.getBu_id()));
        String isOpenRenewalProcess = RenewalCfg.getInstance().getConfigItem("isOpenRenewalProcess");
        if ("true".equals(isOpenRenewalProcess) && StringUtils.isNotEmpty(beanName)) {
            try {
                OrderRenewalService ors = ApplicationContextUtil.getBean(beanName);
                ors.renewalBusi(orderInfo);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("是否续单设置，类型错误或找不到对应类：" + beanName + e.getClass());
//                defaultRenewalBusi(orderInfo);
            }
        } else if ("true".equals(isOpenRenewalProcess)) {// 其他地区走默认续单规则
//            defaultRenewalBusi(orderInfo);
        }
    }
}
