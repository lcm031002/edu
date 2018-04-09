package com.edu.erp.employee.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.erp.employee.service.EmployeeMgrService;
import com.edu.erp.model.*;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeMgrController extends BaseController {
    private static Logger log = Logger.getLogger(EmployeeMgrController.class);

    @Resource(name = "employeeMgrService")
    private EmployeeMgrService employeeMgrService;

    @RequestMapping(value = {"/employee/employeeservice/page"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployeeForPage(HttpServletRequest request,
                                             HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query Employeepage");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> params = this.initParamMap(request, true, StringUtils.EMPTY);
            Page<EmployeeInfo> page = employeeMgrService.queryEmployeeForPage(params);
            setRespDataForPage(request, page.getResult(), result);
        } catch (Exception e) {
            log.error("Exception:" + e);
            result.put("error", true);
            result.put("msg", "查询员工信息失败");
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query Employeepage");
        }
        return result;
    }

    @RequestMapping(value = {"/employee/employeeservice/queryById"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryById(HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query Employeepage");
        }
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Long employeeId = genLongParameter("employeeId", request);
            if (employeeId == null) {
                log.error("非法查询,employeeId不能为空！");
                throw new Exception("非法查询！");
            }
            EmployeeInfo employee = employeeMgrService.queryEmployeeById(employeeId);
            result.put("error", false);
            result.put("data", employee);
        } catch (Exception e) {
            log.error("error found,", e);
            result.put("error", true);
            result.put("message", "查询员工信息失败");
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query Employeepage");
        }
        return result;
    }

    @RequestMapping(value = {"/employee/employeeservice"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployee(Long id) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query Employee");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> list = employeeMgrService.queryEmployee(id);
            resultMap.put("data", list);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query Employee");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/queryFieldKey/employeeservice"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryFieldKey() {
        if (log.isDebugEnabled()) {
            log.debug("begin to query fieldKey");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> list = employeeMgrService.queryFieldKey();
            resultMap.put("error", false);
            resultMap.put("data", list);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query fieldKey");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice"}, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addEmployee(@RequestBody EmployeeInfo employeeInfo, HttpServletRequest request,
                                    HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to addEmployee");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.setDefaultValue(request, employeeInfo, false);
            employeeMgrService.insertEmployee(employeeInfo);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to addEmployee");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployee(@RequestBody Map<String, Object> param, HttpServletRequest
            request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update Employee");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.updateEmployee(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update Employee");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployeeInfo(String searchInfo, HttpServletRequest
            request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query employeeInfo");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> result = employeeMgrService.queryEmployeeInfo(searchInfo);
            resultMap.put("error", false);
            resultMap.put("data", result);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query employeeInfo");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/edu"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployeeEdu(Long employee_id, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query EmployeeEdu");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<EmployeeEdu> result = employeeMgrService.queryEmployeeEdu(employee_id);
            resultMap.put("error", false);
            resultMap.put("data", result);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query EmployeeEdu");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/edu"}, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addEmployeeEdu(@RequestBody EmployeeEdu param, HttpServletRequest request,
                                       HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to add EmployeeEdu");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.addEmployeeEdu(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to add EmployeeEdu");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/edu"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployeeEdu(@RequestBody EmployeeEdu param, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update EmployeeEdu");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.updateEmployeeEdu(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update EmployeeEdu");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/edu"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteEmployeeEdu(Long id, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete EmployeeEdu");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.deleteEmployeeEdu(id);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete EmployeeEdu");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/exp"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployeeExp(Long employee_id, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query EmployeeExp");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<EmployeeExperience> result = employeeMgrService.queryEmployeeExp(employee_id);
            resultMap.put("error", false);
            resultMap.put("data", result);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query EmployeeExp");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/exp"}, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addEmployeeExp(@RequestBody EmployeeExperience param, HttpServletRequest request,
                                       HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to add EmployeeEdu");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.addEmployeeExp(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to add EmployeeExp");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/exp"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployeeExp(@RequestBody EmployeeExperience param, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update EmployeeExp");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.updateEmployeeExp(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update EmployeeExp");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/exp"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteEmployeeExp(Long id, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete EmployeeExp");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.deleteEmployeeExp(id);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete EmployeeExp");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/sum"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployeeSum(Long employee_id, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query EmployeeSum");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<EmployeeSummarize> result = employeeMgrService.queryEmployeeSum(employee_id);
            resultMap.put("error", false);
            resultMap.put("data", result);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query EmployeeSum");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/sum"}, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addEmployeeSum(@RequestBody EmployeeSummarize param, HttpServletRequest request,
                                       HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to add EmployeeSum");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.addEmployeeSum(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to add EmployeeSum");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/sum"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployeeSum(@RequestBody EmployeeSummarize param, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update EmployeeSum");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.updateEmployeeSum(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update EmployeeSum");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/sum"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteEmployeeSum(Long id, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete EmployeeSum");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.deleteEmployeeSum(id);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete EmployeeSum");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/rew"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryEmployeeRew(Long employee_id, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query EmployeeRew");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<EmployeeReward> result = employeeMgrService.queryEmployeeRew(employee_id);
            resultMap.put("error", false);
            resultMap.put("data", result);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query EmployeeRew");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/rew"}, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addEmployeeRew(@RequestBody EmployeeReward param, HttpServletRequest request,
                                       HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to add EmployeeRew");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.addEmployeeRew(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to add EmployeeRew");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/rew"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployeeRew(@RequestBody EmployeeReward param, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update EmployeeRew");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.updateEmployeeRew(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update EmployeeRew");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/rew"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> deleteEmployeeRew(Long id, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete EmployeeRew");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.deleteEmployeeRew(id);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete EmployeeRew");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/static"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployeeStatic(@RequestBody Map<String, Object> param, HttpServletRequest
            request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update EmployeeStatic");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.updateEmployeeStatic(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update EmployeeStatic");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/image"}, method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updateEmployeeImage(@RequestBody Map<String, Object> param, HttpServletRequest
            request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update EmployeeStatic");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            boolean suc = employeeMgrService.updateEmployeeImage(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update EmployeeStatic");
        }
        return resultMap;
    }


    /**
     * 图像上传重定向
     *
     * @return
     */
    @RequestMapping("/templates/hrm/uploadImage/headPic")
    public ModelAndView headPic(HttpServletRequest request) {
        try {
            Long employee_id = Long.valueOf(request.getParameter("selectedEmpId").toString());
            request.setAttribute("employee_id", employee_id);
        } catch (Exception e) {
            log.error(e);
        }

        return new ModelAndView("templates/hrm/uploadImage/HeadPic");
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/post"}, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> queryPostByEmpId(Long employee_id, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query EmployeeRew");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> result = employeeMgrService.queryPostByEmpId(employee_id);
            resultMap.put("error", false);
            resultMap.put("data", result);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query EmployeeRew");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/post"}, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addEmpPost(@RequestBody Map<String, Object> param, HttpServletRequest request,
                                   HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to add EmployeeRew");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取当前登陆的id
            Account account = (Account) request.getSession().getAttribute(
                    "USER_OBJ");
            param.put("create_user", account.getId());
            boolean suc = employeeMgrService.addEmpPost(param);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to add EmployeeRew");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/post"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> removeEmpPost(Long id, HttpServletRequest request,
                                      HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete EmpPost");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            employeeMgrService.removeEmpPost(id);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete EmpPost");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/status"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> setStatus(Long id, HttpServletRequest request,
                                  HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to set status");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            boolean suc = employeeMgrService.setStatus(id);
            resultMap.put("error", false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to set status");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/employee/employeeservice/employeeInfo/img"}, method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> modifyImg(@RequestBody Map<String, String> json, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            String modifyPhoto = employeeMgrService.modifyPhoto(json);
            resultMap.put("url", modifyPhoto);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

}
