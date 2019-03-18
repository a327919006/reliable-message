package com.cn.rmq.check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 消息确认子系统启动类
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@SpringBootApplication
@EnableScheduling
public class CheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }
}
