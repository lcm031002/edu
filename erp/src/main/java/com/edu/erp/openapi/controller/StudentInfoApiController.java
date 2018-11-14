package com.edu.erp.openapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = { "/openapi/student" })
public class StudentInfoApiController {

    @ResponseBody
    @RequestMapping(value = { "/test" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
    public Map<String, Object> test(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        result.put("error", "false");
        result.put("data", "test");
        return result;
    }
}
