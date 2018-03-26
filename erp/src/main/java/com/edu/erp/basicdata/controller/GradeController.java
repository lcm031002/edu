package com.edu.erp.basicdata.controller;

import com.ebusiness.common.constants.Constants;
import com.edu.erp.basicdata.service.GradeService;
import com.edu.erp.model.BasGrade;
import com.edu.erp.utils.BaseController;
import com.github.pagehelper.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = { "/dictionary/grade" })
public class GradeController extends BaseController {
    private static Logger log = Logger.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    @ResponseBody
    @RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
    public Map<String, Object> queryGradePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> queryParam = initParamMap(request, true);
            Page<BasGrade> page = gradeService.queryForPage(queryParam);
            setRespDataForPage(request, page, resultMap);
        } catch (Exception e) {
            log.error(e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }
}
