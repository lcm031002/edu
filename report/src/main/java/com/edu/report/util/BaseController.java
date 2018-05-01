package com.edu.report.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class BaseController {
    private static final Logger log = Logger.getLogger(BaseController.class);

    protected Long genLongParameter(String name, HttpServletRequest request) {
        String param = request.getParameter(name);
        Long longParam = null;
        try {
            if (StringUtils.isNotBlank(param)) {
                longParam = Long.parseLong(param);
            }
        } catch (Exception e) {
            log.error("error found,see:", e);
        }
        return longParam;
    }

    protected Integer genIntParameter(String name, HttpServletRequest request) {
        String param = request.getParameter(name);
        Integer longParam = null;
        try {
            if (StringUtils.isNotBlank(param)) {
                longParam = Integer.parseInt(param);
            }
        } catch (Exception e) {
            log.error("error found,see:", e);
        }
        return longParam;
    }

    protected OrgModel getOrgModel(HttpServletRequest request) throws Exception {
        OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
        if (null == orgModel || orgModel.getBuId() == null) {
            throw new Exception("请选择校区或团队！");
        }
        return orgModel;
    }

    /**
     * 设置分页查询返回结果
     * 
     * @param request
     *            http请求对象，封装前台请求数据
     * @param page
     *            分页数据
     * @param resultMap
     *            返回结果
     */
    protected void setRespDataForPage(HttpServletRequest request, List list, Map<String, Object> resultMap) {
        PageInfo pageData = new PageInfo(list);
        resultMap.put("error", false);
        resultMap.put("data", pageData.getList());
        resultMap.put("total", pageData.getTotal());
        resultMap.put("totalPage", pageData.getPages());
        resultMap.put("currentPage", pageData.getPageNum());
        resultMap.put("pageSize", pageData.getPageSize());
    }

    /**
     * 设置查询分页信息
     * 
     * @param request
     *            http请求对象，封装前天请求数据
     */
    protected void setPageInfo(HttpServletRequest request) {
        Integer currentPage = genIntParameter("currentPage", request);
        currentPage = (currentPage == null) ? 1 : currentPage;
        Integer pageSize = genIntParameter("pageSize", request);
        pageSize = (pageSize == null) ? 10 : pageSize;
        PageHelper.startPage(currentPage, pageSize);
    }
}
