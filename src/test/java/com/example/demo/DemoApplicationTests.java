package com.example.demo;

import com.example.demo.mapper.UserLoginLogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.example.demo.mapper.*")
public class DemoApplicationTests {
    private static Integer index = 0;
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;


    @Test
    public void contextLoads() {

    }

}
