package com.edu.erp.teacher_manager.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.constants.Constants;
import com.edu.common.util.ModelDataUtils;
import com.edu.erp.dict.service.GcDictService;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.dict.service.SubjectService;
import com.edu.erp.model.*;
import com.edu.erp.teacher_manager.service.TeacherInfoService;
import com.edu.erp.util.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.ReaderConfig;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = {"/teacherservice"})
public class TeacherInfoController extends BaseController {
    private static final Logger log = Logger
            .getLogger(TeacherInfoController.class);

    @Resource(name = "teacherInfoService")
    private TeacherInfoService teacherInfoService;

    @Resource(name = "organizationService")
    private OrganizationService organizationService;

    @Resource(name = "subjectService")
    private SubjectService subjectService;

    @Resource(name = "gcDictService")
    private GcDictService gcDictService;

    // add by zenglw start

    /**
     * 设置教师图像
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/photo"}, headers = {"Accept=application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadImg(@RequestBody Map<String, String> json, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            teacherInfoService.uploadAndSetPhoto(json);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 通过教师id获取教师关联的科目信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/subject"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> searchTeacherSubjects(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            Integer teacherId = genIntParameter("teacherId", request);
            List<Map<String, Object>> data = teacherInfoService.searchTeacherSubjects(teacherId);
            List<Map<String, Object>> lowerMapList = ModelDataUtils.lowerMapList(data);
            resultMap.put("error", false);
            resultMap.put("data", lowerMapList);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 批量删除
     *
     * @return
     */
    //@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.DELETE)
    //@ResponseBody
    public Map<String, Object> batchRemove(@RequestBody Map<String, String> json, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        String ids = json.get("teacherIds");
        try {
            if (StringUtils.isEmpty("ids")) {
                throw new RuntimeException("没有选中任何教师");
            }
            teacherInfoService.toChangeStatus(ids,
                    BaseObject.StatusEnum.DELETE.getCode());
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 切换教师状态
     *
     * @return
     */
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestParam String teacherIds, @RequestParam String status, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            if (StringUtils.isEmpty(teacherIds)) {
                throw new RuntimeException("没有选中任何教师");
            }
            if ("enable".equals(status)) {
                teacherInfoService.toChangeStatus(teacherIds, 1);
            } else if ("disable".equals(status)) {
                teacherInfoService.toChangeStatus(teacherIds, 6);
            } else {
                throw new RuntimeException("不支持的状态类型");
            }
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 老师查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> teacherSearch(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            Map<String, Object> param = this.initParamMap(request, true, StringUtils.EMPTY);
            if (param.get("org_id") != null) {
                param.put("bu_id", param.get("org_id"));
            }
            Page<Teacher> page = new Page<Teacher>();
            page = teacherInfoService.page(param);
            setRespDataForPage(request, page.getResult(), resultMap);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 查询老师信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/list"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> selectList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            Map<String, Object> param = this.initParamMap(request, false, StringUtils.EMPTY);
            List<Teacher> teacherList = teacherInfoService.selectList(param);
            resultMap.put("error", false);
            resultMap.put("data", teacherList);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/toManage"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> toManage(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String dictTypeCodes = Constants.DictTypeCode.TEACHER_PAGE_TYPE_CODES;
            Map<String, List<TpDictData>> dictDataMap = gcDictService
                    .selectByDictTypeCodes(dictTypeCodes);
            if (!CollectionUtils.isEmpty(dictDataMap)) {
                resultMap.putAll(dictDataMap);
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("org_type", Constants.OrgType.GROUP);
            List<OrganizationInfo> buList = organizationService.list(paramMap);
            if (!CollectionUtils.isEmpty(buList)) {
                resultMap.put("buList", buList);
            }

            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            Map<String, Object> paramMap2 = new HashMap<String, Object>();
            List<TPSubject> subjectList = subjectService.queryDataList(paramMap2);
            if (!CollectionUtils.isEmpty(subjectList)) {
                resultMap.put("subjectList", subjectList);
            }

            if (orgModel != null) {
                resultMap.put("bu_id", orgModel.getBuId());
            }

            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    /**
     * 新增
     *
     * @param teacher
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/service"}, method = RequestMethod.POST, headers = {"Accept=application/json"})
    public Map<String, Object> toAdd(@RequestBody() Teacher teacher,
                                     HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Account account = WebContextUtils.genUser(request);
            if (account != null) {
                teacher.setCreate_user(account.getId());
            }
            Map<String, Object> param = new HashMap<String, Object>();
            Set subjectNames = new HashSet();
            for (String team : teacher.getTeam().split("[,，]")) {
                param.clear();
                param.put("bu_id", team);
                List<TPSubject> tpSubjectList = subjectService.querySubjectListByBuID(param);
                for (TPSubject tpSubject : tpSubjectList) {
                    subjectNames.add(tpSubject.getName());
                }
            }
            for (String subject : teacher.getSubject().split("[,，]")) {
                String subjectName = subjectService.queryData(subject).getName();
                if (!subjectNames.contains(subjectName)) {
                    throw new Exception("所选团队没有" + subjectName + "科目，请重新选择！");
                }
            }
            List<TeacherSubject> teacherSubjectList = null;
            if (StringUtils.isNotEmpty(teacher.getSubject())) {
                teacherSubjectList = new ArrayList<TeacherSubject>();
                TeacherSubject teacherSubject = null;
                for (String team : teacher.getTeam().split("[,，]")) {
                    for (String subject : teacher.getSubject().split("[,，]")) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubject_id(Long.parseLong(subject));
                        teacherSubject.setBu_id(Long.parseLong(team));
                        teacherSubject.setStatus(BaseObject.StatusEnum.VALID.getCode());
                        teacherSubjectList.add(teacherSubject);
                    }
                }
            }
            List<TeacherTeamRel> teacherTeamRelList = null;
            if (StringUtils.isNotEmpty(teacher.getTeam())) {
                teacherTeamRelList = new ArrayList<TeacherTeamRel>();
                TeacherTeamRel teacherTeamRel = null;
                for (String team : teacher.getTeam().split("[,，]")) {
                    teacherTeamRel = new TeacherTeamRel();
                    teacherTeamRel.setBuId(Long.parseLong(team));
                    teacherTeamRelList.add(teacherTeamRel);
                }
            }
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            Assert.notNull(orgModel.getCityId(), "city_id不能为空");
            teacher.setCity_id(orgModel.getCityId());
            this.teacherInfoService.toAdd(teacher, teacherSubjectList, teacherTeamRelList);
            resultMap.put("error", false);
            resultMap.put("data", teacher);
        } catch (Exception e) {
            System.err.println(e);
            resultMap.put("error", true);
            resultMap.put("message", "保存失败！see:" + e.getMessage());
        }
        return resultMap;
    }

    /**
     * 修改
     *
     * @param teacher
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/service"}, method = RequestMethod.PUT, headers = {"Accept=application/json"})
    public Map<String, Object> toUpdate(
            @RequestBody() Teacher teacher, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {

            Assert.hasText(teacher.getEncoding(), "教师编码必填");
            Assert.hasText(teacher.getTeacher_name(), "教师姓名必填");
            Assert.notNull(teacher.getEmployee_id(), "关联员工必填");
            Assert.hasText(teacher.getPhone(), "手机号必填");
            Assert.notNull(teacher.getSex(), "性别必填");
            Map<String, Object> param = new HashMap<String, Object>();
            Set subjectNames = new HashSet();
            for (String team : teacher.getTeam().split("[,，]")) {
                param.clear();
                param.put("bu_id", team);
                List<TPSubject> tpSubjectList = subjectService.querySubjectListByBuID(param);
                for (TPSubject tpSubject : tpSubjectList) {
                    subjectNames.add(tpSubject.getName());
                }
            }
            for (String subject : teacher.getSubject().split("[,，]")) {
                String subjectName = subjectService.queryData(subject).getName();
                if (!subjectNames.contains(subjectName)) {
                    throw new Exception("所选团队没有" + subjectName + "科目，请重新选择！");
                }
            }
            List<TeacherSubject> teacherSubjectList = null;
            if (StringUtils.isNotEmpty(teacher.getSubject())) {
                teacherSubjectList = new ArrayList<TeacherSubject>();
                TeacherSubject teacherSubject = null;
                for (String team : teacher.getTeam().split("[,，]")) {
                    for (String subject : teacher.getSubject().split(",")) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setTeacher_id(teacher.getId());
                        teacherSubject.setSubject_id(Long.parseLong(subject));
                        teacherSubject.setBu_id(Long.parseLong(team));
                        teacherSubject.setStatus(BaseObject.StatusEnum.VALID.getCode());
                        teacherSubjectList.add(teacherSubject);
                    }
                }
            }
            List<TeacherTeamRel> teacherTeamRelList = null;
            if (StringUtils.isNotEmpty(teacher.getTeam())) {
                teacherTeamRelList = new ArrayList<TeacherTeamRel>();
                TeacherTeamRel teacherTeamRel = null;
                for (String team : teacher.getTeam().split("[,，]")) {
                    teacherTeamRel = new TeacherTeamRel();
                    teacherTeamRel.setBuId(Long.parseLong(team));
                    teacherTeamRelList.add(teacherTeamRel);
                }
            }
            Account account = WebContextUtils.genUser(request);
            teacher.setUpdate_user(account.getId());
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            teacher.setCity_id(orgModel.getCityId());
            teacherInfoService.toUpdate(teacher, teacherSubjectList, teacherTeamRelList);
            resultMap.put("error", false);
        } catch (Exception e) {
            System.err.println(e);
            resultMap.put("error", true);
            resultMap.put("message", "修改失败！see:" + e.getMessage());
        }
        return resultMap;
    }

    /**
     * 设置教师图像
     *
     * @param teacher_id
     * @param photo
     * @return
     */
    @RequestMapping(value = {"/toSetPhoto"}, method = RequestMethod.POST)
    public ModelAndView toSetPhoto(@RequestParam Long teacher_id,
                                   @RequestParam String photo) {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        try {
            teacherInfoService.toSetPhoto(teacher_id, photo);
            modelAndView.addObject("result", 1);
        } catch (Exception e) {
            System.err.println(e);
            modelAndView.addObject("result", 0);
        }
        return modelAndView;
    }

    /**
     * 查询教师关联团队
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/team"}, method = RequestMethod.GET, headers = {"Accept=application/json"})
    @ResponseBody
    public Map<String, Object> searchTeacherTeams(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            Integer teacherId = genIntParameter("teacherId", request);
            List<TeacherTeamRel> data = teacherInfoService.searchTeacherTeam(teacherId);
            resultMap.put("error", false);
            resultMap.put("data", data);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

}
