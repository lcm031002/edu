package com.edu.common.interceptor;

import com.edu.common.annotation.Token;
import com.edu.common.util.HttpRequestUtils;
import java.lang.reflect.Method;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * token拦截器，防止重复提交
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    request.getSession(false).removeAttribute("token");
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (StringUtils.isEmpty(serverToken)) {
            return true;
        }

        String bodyParam = HttpRequestUtils.getBodyString(request);
        if (StringUtils.isEmpty(bodyParam)) {
            return true;
        }
        String clientToken = request.getParameter("token");
        if (StringUtils.isEmpty(clientToken)) {
            JSONObject requestParams = JSONObject.fromObject(bodyParam);
            clientToken = requestParams.getString("token");
            if (StringUtils.isEmpty(clientToken)) {
                return true;
            }
        }

        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
    }

}
