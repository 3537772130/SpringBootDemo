package com.example.demo.controller.other;

import com.example.demo.config.annotation.SessionScope;
import com.example.demo.entity.ManagerInfo;
import com.example.demo.service.WebSocketService;
import com.example.demo.util.AjaxResponse;
import com.example.demo.util.Constants;
import jodd.datetime.JDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @program: SpringBootDemo
 * @description: socket控制层
 * @author: Mr.ZhouHuaHu
 * @create: 2019-07-11 15:10
 **/
@RestController
@RequestMapping(value = "/web/manager/socket")
public class SocketController {
    private static final Logger log = LoggerFactory.getLogger(SocketController.class);
    @Resource
    private WebSocketService webSocketService;

    /**
     * 给指定用户推送消息
     *
     * @param sId 用户编号
     * @param message  消息
     * @throws IOException
     */
    @RequestMapping(value = "/pushMessage", method = RequestMethod.GET)
    public void pushMessage(@RequestParam String sId, @RequestParam String message) {
        webSocketService.sendInfo(sId, message);
    }

    /**
     * 给所有用户推送消息
     *
     * @param message 消息
     * @throws IOException
     */
    @RequestMapping(value = "/pushMessageToAll", method = RequestMethod.GET)
    public void pushMessageToAll(@RequestParam String message) {
        webSocketService.sendInfo(null, message);
    }

    /**
     * 发送模拟系统消息
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/sendImitateSystemNews")
    @ResponseBody
    public Object sendImitateSystemNews(@SessionScope(Constants.WEB_MANAGER_INFO) ManagerInfo info) {
        try {
            JDateTime time = new JDateTime(new Date());
            webSocketService.sendInfo(info.getId().toString(), "模拟系统信息：" + time.toString());
            return AjaxResponse.success("发送成功");
        } catch (Exception e) {
            log.error("模拟系统推送信息出错{}", e);
            return AjaxResponse.error("发送失败");
        }
    }
}
