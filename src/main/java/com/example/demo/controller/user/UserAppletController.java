package com.example.demo.controller.user;

import com.example.demo.service.AppletService;
import com.example.demo.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SpringBootDemo
 * @description: 用户小程序控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-19 09:36
 **/
@RestController
@RequestMapping(value = "/api/user/applet/")
public class UserAppletController {
    private static final Logger log = LoggerFactory.getLogger(UserAppletController.class);
    @Autowired
    private AppletService appletService;
    @Autowired
    private ManagerService managerService;


}
