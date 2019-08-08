package com.example.demo.config.intercepors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import reactor.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: SpringBootDemo
 * @description: vue静态资源拦截器
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-07 15:49
 **/
@Component
public class VueStaticInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(VueStaticInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        uri = uri.replace("/api", "");
        request.getRequestDispatcher(uri).forward(request, response);
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
