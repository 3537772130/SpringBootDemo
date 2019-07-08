package com.example.demo.config;

import com.example.demo.config.argumentResolver.SessionScopeMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @program: SpringBootDemo
 * @description: 函数参数分解器
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-08 10:12
 **/
@Configuration
public class ApplicationConfigurer extends WebMvcConfigurationSupport {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new SessionScopeMethod());
    }

}
