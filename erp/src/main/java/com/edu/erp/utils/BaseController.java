package com.edu.erp.utils;

import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.model.OrgModel;
import com.ebusiness.cas.client.common.util.WebContextUtils;
import com.edu.common.constants.Constants;
import com.edu.erp.model.BaseObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
    private static final Logger log = Logger.getLogger(BaseController.class);

    /**
     * 获取页面参数Map
     *
     * @param request HttpServletRequest对象
     * @return Map<String   ,       Object> 页面参数Map
     * 注：使用此方法需注意页面的HTML元素的name以p_开头，例如：<input name=
     * "p_username"/>。最后生成的Map中的key为页面 HTML的元素的name去掉p_，即key为username。
     */
    protected Map<String, Object> getParamMap(HttpServletRequest request) {
        return WebUtils.getParametersStartingWith(request, "p_");
    }

    protected Map<String, Object> getParamMap(HttpServletRequest request, String prefix) {
        return WebUtils.getParametersStartingWith(request, prefix);
    }

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

    /**
     * 初始查询过滤条件
     *
     * @param request     http请求对象，封装前台请求数据
     * @param isPageQuery 是否分页查询
     * @return 查询条件
     */
    protected Map<String, Object> initParamMap(HttpServletRequest request, boolean isPageQuery) throws Exception {
        return initParamMap(request, isPageQuery, null);
    }

    protected Map<String, Object> initParamMap(HttpServletRequest request, boolean isPageQuery, String prefix)
            throws Exception {
        OrgModel orgModel = getOrgModel(request);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("city_id", orgModel.getCityId());
        paramMap.put("bu_id", orgModel.getBuId());
        paramMap.put("product_line", orgModel.getProductLine());

        if (prefix == null) {
            paramMap.putAll(this.getParamMap(request));
        } else {
            paramMap.putAll(this.getParamMap(request, prefix));
        }

        if (isPageQuery) {
            setPageInfo(request);
        }

        return paramMap;
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
     * @param request   http请求对象，封装前台请求数据
     * @param list      分页数据
     * @param resultMap 返回结果
     */
    protected void setRespDataForPage(HttpServletRequest request, List list, Map<String, Object> resultMap) {
        PageInfo pageData = new PageInfo(list);
        resultMap.put(Constants.RespMapKey.ERROR, false);
        resultMap.put(Constants.RespMapKey.DATA, pageData.getList());
        resultMap.put("total", pageData.getTotal());
        resultMap.put("totalPage", pageData.getPages());
        resultMap.put("currentPage", pageData.getPageNum());
        resultMap.put("pageSize", pageData.getPageSize());

        Object tokenObj = request.getSession(false).getAttribute("token");
        if (tokenObj != null) {
            resultMap.put("token", tokenObj);
        }
    }

    /**
     * 对象新增或者更新时，设置框架默认值；校验是否有选择校区 默认值：创建人、修改人、所属城市、状态等
     *
     * @param request  http请求对象，封装前台请求数据
     * @param po       保存或更新对象
     * @param isUpdate 是否更新 true-更新 false-保存
     * @throws Exception 异常信息
     */
    protected Long setDefaultValue(HttpServletRequest request, BaseObject po, boolean isUpdate) throws Exception {
        OrgModel orgModel = getOrgModel(request);
        Account account = WebContextUtils.genUser(request);
        if (isUpdate) {
            po.setUpdate_user(account.getId());
            po.setUpdate_time(new Date());
        } else {
            if (po.getStatus() == null) {
                po.setStatus(BaseObject.StatusEnum.VALID.getCode());
            }
            po.setCreate_user(account.getId());
            po.setCreate_time(new Date());
        }
        return orgModel.getBuId();
    }

    /**
     * 设置查询分页信息
     *
     * @param request http请求对象，封装前天请求数据
     */
    protected void setPageInfo(HttpServletRequest request) {
        Integer currentPage = genIntParameter("currentPage", request);
        currentPage = (currentPage == null) ? 1 : currentPage;
        Integer pageSize = genIntParameter("pageSize", request);
        pageSize = (pageSize == null) ? 10 : pageSize;
        PageHelper.startPage(currentPage, pageSize);
    }

}
