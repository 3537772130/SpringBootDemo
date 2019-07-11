package com.example.demo.controller;

import com.example.demo.service.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: SpringBootDemo
 * @description: socket控制层
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-11 15:10
 **/
@RestController
public class SocketController {
    private static final Logger log = LoggerFactory.getLogger(SocketController.class);
    @Resource
    private WebSocketServer webSocketServer;

    /**
     * 给指定用户推送消息
     *
     * @param userName 用户名
     * @param message  消息
     * @throws IOException
     */
    @RequestMapping(value = "/socket", method = RequestMethod.GET)
    public void testSocket1(@RequestParam String userName, @RequestParam String message) {
        webSocketServer.sendInfo(userName, message);
    }

    /**
     * 给所有用户推送消息
     *
     * @param message 消息
     * @throws IOException
     */
    @RequestMapping(value = "/socket/all", method = RequestMethod.GET)
    public void testSocket2(@RequestParam String message) {
        webSocketServer.sendInfo(null, message);
    }
}
