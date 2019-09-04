package com.example.demo.config.intercepors;

import com.example.demo.config.annotation.CancelAuthentication;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.entity.ViewRoleMenuThird;
import com.example.demo.service.MenuService;
import com.example.demo.util.Constants;
import com.example.demo.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @program: demo
 * @description: web管理员登陆信息拦截器
 * @author: zhouhuahu
 * @create: 2019-08-17 16:22
 **/
@Component
public class ManagerInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ManagerInterceptor.class);
    @Autowired
    private MenuService menuService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handleMethod = (HandlerMethod) handler;
            CancelAuthentication cancel = handleMethod.getMethodAnnotation(CancelAuthentication.class);
            if (cancel != null) {
                return true;
            }
        }
        HttpSession session = request.getSession();
        ManagerInfo managerInfo = (ManagerInfo) SerializeUtil.unserialize((byte[]) session.getAttribute(Constants.WEB_MANAGER_INFO));
        if (managerInfo == null) {
            log.info("===> 登录过期");
            request.getRequestDispatcher("/api/error").forward(request, response);
            return false;
        } else {
            boolean bool = true;
            try {
                if (menuService == null) {//解决service为null无法注入问题
                    System.out.println("menuService is null!!!");
                    BeanFactory factory = WebApplicationContextUtils
                            .getRequiredWebApplicationContext(request.getServletContext());
                    menuService = (MenuService) factory.getBean("menuService");
                }

                String uri = request.getRequestURI();
                int lastIndex = uri.lastIndexOf("/");
                String logo = uri.substring((lastIndex + 1), uri.length());
                List<ViewRoleMenuThird> list = menuService.selectRoleMenuInfo(logo);
                for (ViewRoleMenuThird record : list) {
                    bool = false;
                    if (record.getRoleId().intValue() == managerInfo.getRoleId().intValue()) {
                        bool = true;
                        break;
                    }
                }
                if (!bool) {
                    request.getRequestDispatcher("/api/auth").forward(request, response);
                }
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bool;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
