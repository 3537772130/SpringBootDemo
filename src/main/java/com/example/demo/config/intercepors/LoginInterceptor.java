package com.example.demo.config.intercepors;

import com.example.demo.entity.ManagerInfo;
import com.example.demo.util.Constants;
import com.example.demo.util.RandomUtil;
import com.example.demo.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: SpringBootDemo
 * @description: 登录拦截器
 * @author: Mr.ZhouHuaHu
 * @create: 2019-06-25 13:12
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        ManagerInfo managerInfo = (ManagerInfo) SerializeUtil.unserialize((byte[]) session.getAttribute(Constants.WEB_MANAGER_INFO));
        if (managerInfo == null){
            request.getSession().setAttribute("loginStatus", true);
            request.getRequestDispatcher("/web/toIndex").forward(request, response);
            return false;
        }else {
            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        request.getSession().setAttribute("token", RandomUtil.getTimeStamp());
    }
}
