package com.example.demo.controller.manager;

import com.example.demo.service.AppletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SpringBootDemo
 * @description: 管理小程序控制类
 * @author: Mr.ZhouHuaHu
 * @create: 2019-08-19 13:26
 **/
@RestController
@RequestMapping(value = "/api/manage")
public class ManageAppletController {
    private static final Logger log = LoggerFactory.getLogger(ManageAppletController.class);
    @Autowired
    private AppletService appletService;
}
