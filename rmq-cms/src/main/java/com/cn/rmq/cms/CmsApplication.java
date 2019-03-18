package com.cn.rmq.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消息管理子系统启动类
 *
 * @author Chen Nan
 * @date 2019/3/15.
 */
@SpringBootApplication()
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
