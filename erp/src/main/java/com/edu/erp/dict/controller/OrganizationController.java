package com.edu.erp.dict.controller;

import com.edu.common.constants.Constants;
import com.edu.common.util.NumberUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.dict.service.DictionaryService;
import com.edu.erp.dict.service.GcDictService;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.dict.service.SelectOptionService;
import com.edu.erp.model.OrganizationInfo;
import com.edu.erp.model.TPProductLine;
import com.edu.erp.model.TpDictData;
import com.edu.erp.util.BaseController;

/**
 * 组织机构
 * 
 * @author wCong
 * 
 */
@Controller
@RequestMapping(value = "/dictionary/organization")
public class OrganizationController extends BaseController {

    @Resource(name = "organizationService")
    private OrganizationService organizationService;

    @Resource(name = "selectOptionService")
    private SelectOptionService selectOptionService;
    
    @Resource(name = "dictionaryService")
    private DictionaryService dictionaryService;
    
    @Resource(name = "gcDictService")
    private GcDictService gcDictService;

    /**
     * 动态参数获取OrganizationInfoList
     * 
     * angular.ajax访问
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<OrganizationInfo> orgList = new ArrayList<OrganizationInfo>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            String org_name = request.getParameter("org_name");
            String org_type = request.getParameter("org_type");
            param.put("org_name", org_name);
            param.put("org_type", org_type);

            orgList = organizationService.list(param);

            result.put("error", false);
            result.put("data", orgList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", "查询失败！see:" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/teamList" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryTeamList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (orgModel.getCityId() == null) {
                result.put("error", true);
                result.put("message", "请选择校区或团队！");
                return result;
            }

            OrganizationInfo orgInfo = new OrganizationInfo();
            Long city_id = genLongParameter("city_id", request);
            city_id = (city_id == null) ? orgModel.getCityId() : city_id;
            orgInfo.setParent_id(city_id);
            List<OrganizationInfo> orgList = organizationService.queryOrganizationInfo(orgInfo);

            result.put("error", false);
            result.put("data", orgList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", "查询失败！see:" + e.getMessage());
        }
        return result;
    }

    /**
     * 动态参数获取OrganizationInfoList
     * 
     * angular.ajax访问
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/branchs" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryBuBranchs(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<OrganizationInfo> orgList = new ArrayList<OrganizationInfo>();
        try {
            Map<String, Object> paramMap = this.initParamMap(request, false);
            if (paramMap.get("buId") == null) {
                paramMap.put("buId", paramMap.get("bu_id"));
            }

            Long buId = NumberUtils.object2Long(paramMap.get("buId"));
            if (buId == null) {
                result.put("error", true);
                result.put("message", "请选择校区或团队！");
                return result;
            }


            Account account = WebContextUtils.genUser(request);
            if (WebContextUtils.isAdmin(request) || Constants.YES.equals(paramMap.get("queryAll"))) {
                orgList = organizationService.queryBuBranchs(buId);
            } else {
                orgList = organizationService.queryBuBranchs(account.getId(), buId);
            }

            result.put("error", false);
            result.put("data", orgList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", "查询失败！see:" + e.getMessage());
        }
        return result;
    }

    /**
     * 动态参数获取OrganizationInfoList
     * 
     * angular.ajax访问
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/allbranchs" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryBuAllBranchs(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<OrganizationInfo> orgList = new ArrayList<OrganizationInfo>();
        try {
            OrganizationInfo param = new OrganizationInfo();
            Long bu_id = genLongParameter("bu_id", request);
            if (bu_id == null) {
                OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
                if (orgModel.getBuId() == null) {
                    result.put("error", true);
                    result.put("message", "请选择校区或团队！");
                    return result;
                }
                param.setParent_id(orgModel.getBuId());
                if (orgModel.getOrgKind() != null) {
                    param.setOrgKind(orgModel.getOrgKind());
                }
            } else {
                param.setParent_id(bu_id);
            }
            orgList = organizationService.queryBuBranchs(param);

            result.put("error", false);
            result.put("data", orgList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", "查询失败！see:" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
    public Map<String, Object> insert(@RequestBody OrganizationInfo orgInfo, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            checkOrgInfo(request, orgInfo);
            this.organizationService.insert(orgInfo);
            result.put("error", false);
            result.put("data", "组织机构添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", "组织机构添加失败！" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
    public Map<String, Object> update(@RequestBody OrganizationInfo orgInfo, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            checkOrgInfo(request, orgInfo);
            this.organizationService.update(orgInfo);
            result.put("error", false);
            result.put("data", "组织机构修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", "组织机构修改失败！" + e.getMessage());
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = { "/productLine" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryProductLine(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            getOrgModel(request);
            List<TPProductLine> productLineList = this.dictionaryService.selectProductLine(null);
            result.put("error", false);
            result.put("data", productLineList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    /**
     * 查询数据字典组织机构配置
     * @param request
     * @param response
     * @return 组织机构信息
     */
    @ResponseBody
    @RequestMapping(value = { "/dictOrgList" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryDictOrgList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            getOrgModel(request);
            List<TpDictData> dictDataList = gcDictService.selectByDictTypeCode("org");
            result.put("error", false);
            result.put("data", dictDataList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    /**
     * 上传组织logo
     * @param jsonMap logo信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/uploadLogo" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
    public Map<String, Object> uploadLogo(@RequestBody Map<String, Object> jsonMap, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            getOrgModel(request);
            String logoUrl = this.organizationService.uploadLogo(jsonMap);
            result.put("error", false);
            result.put("data", logoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = { "/deleteLogo" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
    public Map<String, Object> deleteLogo(@RequestBody Map<String, Object> jsonMap, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            getOrgModel(request);
            this.organizationService.deleteLogo(jsonMap);
            result.put("error", false);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    private void checkOrgInfo(HttpServletRequest request, OrganizationInfo orgInfo) throws Exception {
        getOrgModel(request);
        if (orgInfo == null) {
            throw new Exception("没有组织机构信息，操作失败！");
        }
        
        if (StringUtils.isEmpty(orgInfo.getOrg_name())) {
            throw new Exception("请填写组织机构名称！");
        }
        
        if (orgInfo.getOrg_type() == null) {
            throw new Exception("请选择组织级别！");
        }
        
        if (orgInfo.getOrg() == null) {
            throw new Exception("请选择所属机构！");
        }
    }

}
