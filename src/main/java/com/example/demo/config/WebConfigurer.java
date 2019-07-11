package com.example.demo.config;

import com.example.demo.config.intercepors.LoginInterceptor;
import com.example.demo.config.intercepors.VueUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: SpringBootDemo
 * @description: 初始化项目配置
 * @author: Mr.ZhouHuaHu
 * @create: 2019-06-25 13:09
 **/
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private VueUserInterceptor vueUserInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/images/");
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String login = "/user/loadLogin";
        String register = "/user/loadRegister";
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**").excludePathPatterns(login, register);
        registry.addInterceptor(vueUserInterceptor).addPathPatterns("/api/user/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/user/loadIndex");
    }
}
