package com.cn.rmq.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务层启动类
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@SpringBootApplication
@MapperScan("com.cn.rmq.dal.mapper")
@ComponentScan(basePackages = {"com.cn.rmq.dal.mapper"})
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
