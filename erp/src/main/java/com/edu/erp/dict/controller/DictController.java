package com.edu.erp.dict.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.erp.dict.service.GcDictService;
import com.edu.erp.util.BaseController;

/**
 * @Description: 数据字典基础服务
 * @author zenglw
 */
@Controller
@RequestMapping(value = { "/dictionary" })
public class DictController extends BaseController {

	@Autowired
	private GcDictService gcDictService;
	
	/**
	 * 根据字典类型查询字典数据
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public  Map<String, Object> queryDictsByDictTypeCode(String code) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			resultMap.put("data", gcDictService.selectByDictTypeCode(code));
		}catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	@ResponseBody
    @RequestMapping(value = { "/service/dictData" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
    public  Map<String, Object> queryDictData(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            Map<String, Object> paramMap = this.initParamMap(request, false, StringUtils.EMPTY);
            resultMap.put("data", gcDictService.selectDictData(paramMap));
        }catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
}
