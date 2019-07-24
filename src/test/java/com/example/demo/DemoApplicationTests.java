package com.example.demo;

import com.example.demo.util.NullUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        String ip = "192.168.5.52";
        //新浪查询失败查询阿里
        String sina = restTemplate.getForObject("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip={ip}", String.class, ip);
        if (NullUtil.isNotNullOrEmpty(sina)) {
            System.out.println(sina);
        } else {
            sina = restTemplate.getForObject("http://ip.taobao.com/service/getIpInfo.php?ip={ip}", String.class, ip);
            System.out.println(sina);
        }
    }

}
