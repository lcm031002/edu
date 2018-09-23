package com.edu.erp.employee.service.impl;

import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.common.util.ModelDataUtils;
import com.edu.erp.dao.EmployeeInfoDao;
import com.edu.erp.employee.service.EmployeeMgrService;
import com.edu.erp.model.*;
import com.github.pagehelper.Page;
import com.klxx.common.util.IdcardUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

@Service("employeeMgrService")
public class EmployeeMgrServiceImpl implements EmployeeMgrService {
    private static final Logger log = Logger.getLogger(EmployeeMgrServiceImpl.class);

    @Resource(name = "employeeInfoDao")
    private EmployeeInfoDao employeeInfoDao;

    private QiNiuCoreCall qiNiuCoreCall = QiNiuCoreCall.getInstance();

    //查询员工档案信息列表
    @Override
    public Page<EmployeeInfo> queryEmployeeForPage(Map<String, Object> params) throws Exception {
        return employeeInfoDao.queryEmployeeForPage(params);
    }

    //根据id查新员工档案
    @Override
    public List<Map<String, Object>> queryEmployee(Long id) throws Exception {
        List<Map<String, Object>> list = employeeInfoDao.queryFieldKey();
        Map<String, Object> param = new HashMap<>();
        StringBuffer str = new StringBuffer();
        for (int i = 0, len = list.size(); i < len; i++) {
            Map<String, Object> map = list.get(i);            //得到list中的一个对象
            Set<String> set = map.keySet();                    //得到对象中的key
            List<String> keyList = new ArrayList<>();
            keyList.addAll(set);
            if (i < len - 1) {
                for (int j = 0; j < keyList.size(); j++) {
                    str.append(map.get(keyList.get(j)) + ",");
                }
            } else {
                for (int j = 0; j < keyList.size(); j++) {
                    str.append(map.get(keyList.get(j)));
                }
            }
        }

        param.put("str", str.toString());
        param.put("id", id);
        return employeeInfoDao.queryEmployee(param);
    }

    //查询启用的员工信息字段名
    @Override
    public List<Map<String, Object>> queryFieldKey() throws Exception {
        return employeeInfoDao.queryFieldKey();
    }

    //新增员工
    @Override
    public void insertEmployee(EmployeeInfo employeeInfo) throws Exception {
        checkEmployee(employeeInfo);
        try {
            employeeInfoDao.insertEmployee(employeeInfo);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void checkEmployee(EmployeeInfo e) {
        EmployeeInfo param = null;
        Assert.hasText(e.getEmployee_name(), "员工姓名必填");
        Assert.hasText(e.getEncoding(), "员工编码必填");
        param = new EmployeeInfo();
        param.setId(e.getId());
        param.setEncoding(e.getEncoding());
        if (CollectionUtils.isNotEmpty(employeeInfoDao.queryOtherEmployeeByParam(param))) {
            throw new RuntimeException("员工编码" + e.getEncoding() + "已经存在");
        }

        if (StringUtils.isNotEmpty(e.getId_card())) {
            //校验身份证格式
            if (!(IdcardUtils.validateCard(e.getId_card()) ? true : IdcardUtils.validatePassport(e.getId_card()) ? true : false)) {
                throw new RuntimeException("身份证号码格式错误");
            }
            //判断身份证是否已经存在
            param = new EmployeeInfo();
            param.setId(e.getId());
            param.setId_card(e.getId_card());
            if (!CollectionUtils.isEmpty(employeeInfoDao.queryOtherEmployeeByParam(param))) {
                throw new RuntimeException("身份证号码" + (String) e.getId_card() + "已经存在");
            }
        }
        //校验是否有相同手机号
        if (StringUtils.isNotEmpty(e.getPhone())) {
            param = new EmployeeInfo();
            param.setId(e.getId());
            param.setPhone(e.getPhone());
            if (!CollectionUtils.isEmpty(employeeInfoDao.queryOtherEmployeeByParam(param))) {
                throw new RuntimeException("已经存在相同手机号的用户");
            }
        }


    }

    //更新员工档案
    @Override
    public void updateEmployee(Map<String, Object> param) throws Exception {
        StringBuffer str = new StringBuffer();

        Set<String> set = param.keySet();
        List<String> keyList = new ArrayList<>();
        keyList.addAll(set);
        //因为对象本身id不能被修改，所以更新的字段中要除去id，否则sq报错
        keyList.remove("id");
        for (Integer i = 0; i < keyList.size(); i++) {
            if (i < keyList.size() - 1) {
                str.append(keyList.get(i) + "=" + "#{" + keyList.get(i) + "},");
            } else {
                str.append(keyList.get(i) + "=" + "#{" + keyList.get(i) + "}");
            }
        }
        param.put("str", str.toString());
        Integer ret = employeeInfoDao.updateEmployee(param);
        if (ret < 1) {
            throw new Exception("更新失败");
        }
    }

    //根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
    @Override
    public List<Map<String, Object>> queryEmployeeInfo(String searchInfo) throws Exception {
        return employeeInfoDao.queryEmployeeInfo(searchInfo);
    }

    //查询员工教育经历
    @Override
    public List<EmployeeEdu> queryEmployeeEdu(Long employee_id) throws Exception {
        return employeeInfoDao.queryEmployeeEdu(employee_id);
    }

    //添加员工教育经历
    @Override
    public boolean addEmployeeEdu(EmployeeEdu param) throws Exception {
        Integer ret = employeeInfoDao.addEmployeeEdu(param);
        if (ret < 1) {
            throw new Exception("添加员工教育经历失败");
        }
        return ret == 1;
    }

    //修改员工教育经历
    @Override
    public boolean updateEmployeeEdu(EmployeeEdu param) throws Exception {
        Integer ret = employeeInfoDao.updateEmployeeEdu(param);
        if (ret < 1) {
            throw new Exception("修改员工教育经历失败");
        }
        return ret == 1;
    }

    //删除员工教育经历
    @Override
    public boolean deleteEmployeeEdu(Long id) throws Exception {
        Integer ret = employeeInfoDao.deleteEmployeeEdu(id);
        if (ret < 1) {
            throw new Exception("删除员工教育经历失败");
        }
        return ret == 1;
    }


    //查询员工工作经历
    @Override
    public List<EmployeeExperience> queryEmployeeExp(Long employee_id) throws Exception {
        return employeeInfoDao.queryEmployeeExp(employee_id);
    }

    //添加员工工作经历
    @Override
    public boolean addEmployeeExp(EmployeeExperience param) throws Exception {
        Integer ret = employeeInfoDao.addEmployeeExp(param);
        if (ret < 1) {
            throw new Exception("添加员工工作经历失败");
        }
        return ret == 1;
    }

    //修改员工工作经历
    @Override
    public boolean updateEmployeeExp(EmployeeExperience param) throws Exception {
        Integer ret = employeeInfoDao.updateEmployeeExp(param);
        if (ret < 1) {
            throw new Exception("修改员工工作经历失败");
        }
        return ret == 1;
    }

    //删除员工工作经历
    @Override
    public boolean deleteEmployeeExp(Long id) throws Exception {
        Integer ret = employeeInfoDao.deleteEmployeeExp(id);
        if (ret < 1) {
            throw new Exception("删除员工工作经历失败");
        }
        return ret == 1;
    }


    //查询员工工作经历
    @Override
    public List<EmployeeSummarize> queryEmployeeSum(Long employee_id) throws Exception {
        return employeeInfoDao.queryEmployeeSum(employee_id);
    }

    //添加员工工作经历
    @Override
    public boolean addEmployeeSum(EmployeeSummarize param) throws Exception {
        Integer ret = employeeInfoDao.addEmployeeSum(param);
        if (ret < 1) {
            throw new Exception("添加员工工作总结失败");
        }
        return ret == 1;
    }

    //修改员工工作经历
    @Override
    public boolean updateEmployeeSum(EmployeeSummarize param) throws Exception {
        Integer ret = employeeInfoDao.updateEmployeeSum(param);
        if (ret < 1) {
            throw new Exception("修改员工工作总结失败");
        }
        return ret == 1;
    }

    //删除员工工作经历
    @Override
    public boolean deleteEmployeeSum(Long id) throws Exception {
        Integer ret = employeeInfoDao.deleteEmployeeSum(id);
        if (ret < 1) {
            throw new Exception("删除员工工作总结失败");
        }
        return ret == 1;
    }


    //查询员工奖惩信息
    @Override
    public List<EmployeeReward> queryEmployeeRew(Long employee_id) throws Exception {
        return employeeInfoDao.queryEmployeeRew(employee_id);
    }

    //添加员工奖惩信息
    @Override
    public boolean addEmployeeRew(EmployeeReward param) throws Exception {
        Integer ret = employeeInfoDao.addEmployeeRew(param);
        if (ret < 1) {
            throw new Exception("添加员工奖惩信息失败");
        }
        return ret == 1;
    }

    //修改员工奖惩信息
    @Override
    public boolean updateEmployeeRew(EmployeeReward param) throws Exception {
        Integer ret = employeeInfoDao.updateEmployeeRew(param);
        if (ret < 1) {
            throw new Exception("修改员工奖惩信息失败");
        }
        return ret == 1;
    }

    //删除员工奖惩信息
    @Override
    public boolean deleteEmployeeRew(Long id) throws Exception {
        Integer ret = employeeInfoDao.deleteEmployeeRew(id);
        if (ret < 1) {
            throw new Exception("删除员工奖惩信息失败");
        }
        return ret == 1;
    }


    //修改员工任职/固定信息
    @Override
    public boolean updateEmployeeStatic(Map<String, Object> param) throws Exception {
        if ((String) param.get("entryDate") != null) {
            String entryDate = (String) param.get("entryDate");
            param.put("entryDate", entryDate);
        } else {
            param.put("entryDate", "");
        }

        param.put("ID_ID", param.get("id_ID"));
        param.remove("id_ID");
        EmployeeInfo e = ModelDataUtils.getPojoMatch(EmployeeInfo.class, param);
        checkEmployee(e);
        Integer ret = employeeInfoDao.updateEmployeeStatic(param);
        if (ret < 1) {
            throw new Exception("修改员工任职信息失败");
        }
        return ret == 1;
    }


    //修改员工任职/固定信息头像
    @Override
    public boolean updateEmployeeImage(Map<String, Object> param) throws Exception {
        Integer ret = employeeInfoDao.updateEmployeeImage(param);
        if (ret < 1) {
            throw new Exception("修改员工头像失败");
        }
        return ret == 1;
    }

    /* (non-Javadoc)
     * @see com.ebusiness.hrm.employee.EmployeeManageService#queryEmployeeById(java.lang.Long)
     */
    @Override
    public EmployeeInfo queryEmployeeById(Long employeeId) throws Exception {
        Assert.notNull(employeeId);
        return employeeInfoDao.queryEmployeeById(employeeId);
    }


    //根据传入的员工id查询该员工绑定的岗位信息
    @Override
    public List<Map<String, Object>> queryPostByEmpId(Long employee_id) throws Exception {
        return employeeInfoDao.queryPostByEmpId(employee_id);
    }

    //添加员工岗位信息
    @Override
    public boolean addEmpPost(Map<String, Object> param) throws Exception {
        Integer ret = employeeInfoDao.addEmpPost(param);
        if (ret < 1) {
            throw new Exception("添加员工岗位信息失败");
        }
        return ret == 1;
    }

    //启用禁用员工岗位
    @Override
    public boolean removeEmpPost(Long id) throws Exception {
        Integer ret = employeeInfoDao.removeEmpPost(id);
        if (ret < 1) {
            throw new Exception("删除员工奖惩信息失败");
        }
        return ret == 1;
    }

    //启用禁用员工状态
    @Override
    public boolean setStatus(Long id) throws Exception {
        Integer ret = employeeInfoDao.setStatus(id);
        if (ret < 1) {
            throw new Exception("改变员工状态失败");
        }
        return ret == 1;
    }

    @Override
    public String modifyPhoto(Map<String, String> json) throws Exception {
        String id = json.get("id");
        String photoBase64 = json.get("photoBase64");
        Assert.hasText(id, "缺少参数");
        Assert.hasText(photoBase64, "缺少参数");
        String fileName = qiNiuCoreCall.genFileName(photoBase64);
        // 上传头像
        qiNiuCoreCall.uploadBase64(photoBase64, fileName);
        // 删除旧头像
        String oldPhoto = json.get("oldPhoto");
        if (!StringUtils.isEmpty(oldPhoto)) {
            try {
                qiNiuCoreCall.delete(oldPhoto.substring(oldPhoto.indexOf("webERP")));
            } catch (Exception e) {
                log.error("error found,see:", e);
            }
        }
        // 更新数据库
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        String staff_head = qiNiuCoreCall.do_main + fileName;
        param.put("staff_head", staff_head);
        Integer ret = employeeInfoDao.updateEmployeeImage(param);
        if (ret < 1) {
            throw new RuntimeException("照片保存到数据库错误");
        }
        return staff_head;
    }

    @Override
    public EmployeeInfo queryEmpInfoByOrgIdAndId(Long id,Long branch_id) {
        Assert.notNull(id);
        Assert.notNull(branch_id);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id", id);
        params.put("branch_id", branch_id);
        return employeeInfoDao.queryEmpInfoByOrgIdAndId(params);
    }
}
