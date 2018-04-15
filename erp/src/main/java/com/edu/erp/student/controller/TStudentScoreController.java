package com.edu.erp.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.common.constants.Constants;
import com.edu.erp.model.TStudentScore;
import com.edu.erp.student.service.TStudentScoreService;
import com.edu.erp.util.BaseController;

@Controller
@RequestMapping(value = { "/studentservice" })
public class TStudentScoreController extends BaseController {
    private static final Logger log = Logger.getLogger(TStudentScoreController.class);

    @Resource(name = "tStudentScoreService")
    private TStudentScoreService tStudentScoreService;

    @ResponseBody
    @RequestMapping(value = { "/schedulingApply/stuScore" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryByApplyId(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.RespMapKey.ERROR, false);
        try {
            Long applyId = genLongParameter("applyId", request);
            if (applyId == null) {
                throw new Exception("没有申请单ID，查询失败！");
            }
            List<TStudentScore> stuScoreList = this.tStudentScoreService.queryByApplyId(applyId);
            resultMap.put(Constants.RespMapKey.DATA, stuScoreList);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = { "/schedulingApply/stuScore" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
    public Map<String, Object> save(HttpServletRequest request, HttpServletResponse response,
            @RequestBody TStudentScore stuScore) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.RespMapKey.ERROR, false);
        try {
            this.setDefaultValue(request, stuScore, false);
            this.tStudentScoreService.save(stuScore);
            resultMap.put(Constants.RespMapKey.DATA, stuScore);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = { "/schedulingApply/stuScore" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
    public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response,
            @RequestBody TStudentScore stuScore) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.RespMapKey.ERROR, false);
        try {
            this.setDefaultValue(request, stuScore, true);
            this.tStudentScoreService.update(stuScore);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = { "/schedulingApply/stuScore" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
    public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.RespMapKey.ERROR, false);
        try {
            Long id = genLongParameter("id", request);
            if (id == null) {
                throw new Exception("没有学生科目成绩ID，删除失败！");
            }
            this.tStudentScoreService.deleteById(id);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

}
