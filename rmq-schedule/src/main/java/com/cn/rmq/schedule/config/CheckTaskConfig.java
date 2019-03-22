package com.cn.rmq.schedule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息确认定时任务配置
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Component
@ConfigurationProperties(prefix = "schedule.check")
@Data
public class CheckTaskConfig {
    /**
     * 线程池最小线程数
     */
    private Integer corePoolSize = 10;
    /**
     * 线程池最大线程数
     */
    private Integer maxPoolSize = 100;
    /**
     * 线程运行的空闲时间
     */
    private Integer keepAliveSeconds = 60;
    /**
     * 缓存队列大小
     */
    private Integer queueCapacity = 10;
}
