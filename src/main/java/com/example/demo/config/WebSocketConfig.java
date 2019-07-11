package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: SpringBootDemo
 * @description: socket配置类, 往 spring 容器中注入ServerEndpointExporter实例
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-11 15:09
 **/
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
