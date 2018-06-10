package com.edu.erp.dict.controller;

import com.edu.common.constants.Constants;
import com.edu.erp.dict.service.CoopOrgService;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.CoopOrg;
import com.edu.erp.model.CoopOrgRel;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/common/coopOrg"})
public class CoopOrgController extends BaseController {
    private static final Logger log = Logger.getLogger(CoopOrgController.class);

    @Resource(name = "coopOrgService")
    private CoopOrgService coopOrgService;

    @ResponseBody
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> querySchoolPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> queryParam = initParamMap(request, true);
            Page<CoopOrg> page = coopOrgService.selectForPage(queryParam);
            setRespDataForPage(request, page, resultMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET, headers = {"Accept=application/json"})
    public Map<String, Object> list(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> queryParam = initParamMap(request, true);
            List<CoopOrg> coopOrgList = coopOrgService.selectList(queryParam);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, coopOrgList);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, method = RequestMethod.POST, headers = {"Accept=application/json"})
    public Map<String, Object> addCoopOrg(@RequestBody CoopOrg coopOrg, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            setDefaultValue(request, coopOrg, false);
            coopOrgService.insert(coopOrg);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, method = RequestMethod.PUT, headers = {"Accept=application/json"})
    public Map<String, Object> updateCoopOrg(@RequestBody CoopOrg coopOrg, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            setDefaultValue(request, coopOrg, true);
            coopOrgService.update(coopOrg);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, method = RequestMethod.DELETE, headers = {"Accept=application/json"})
    public Map<String, Object> deleteById(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ids", id);
            paramMap.put("status", BaseObject.StatusEnum.DELETE.getCode());
            coopOrgService.changeStatus(paramMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/changeStatus"}, method = RequestMethod.PUT, headers = {"Accept=application/json"})
    public Map<String, Object> updateSchoolStatus(@RequestBody Map<String, Object> jsonMap, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            coopOrgService.changeStatus(jsonMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/addPercentage"}, method = RequestMethod.POST, headers = {"Accept=application/json"})
    public Map<String, Object> addPercentage(@RequestBody CoopOrgRel coopOrgRel, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            setDefaultValue(request, coopOrgRel, false);
            coopOrgService.insertPercentage(coopOrgRel);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/listPercentage"}, method = RequestMethod.GET, headers = {"Accept=application/json"})
    public Map<String, Object> listPercentage(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String coopOrgId = request.getParameter("coopOrgId");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("coopOrgId", coopOrgId);

            List<CoopOrgRel> coopOrgList = coopOrgService.selectPercentageList(paramMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, coopOrgList);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }
}
