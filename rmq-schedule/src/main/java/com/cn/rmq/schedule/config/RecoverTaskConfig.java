package com.cn.rmq.schedule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息恢复定时任务配置
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Component
@ConfigurationProperties(prefix = "schedule.recover")
@Data
public class RecoverTaskConfig {
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
    private Integer keepAliveTime = 60000;
    /**
     * 缓存队列大小
     */
    private Integer queueCapacity = 10;

    /**
     * 最大重发次数
     *
     * 举例：maxTimes设置为6次
     * 某条消息状态处于发送中，下游业务层一直没有确认消费该消息，则消息恢复子系统会重发该消息给下游业务层
     * 如果消息重发次数达到maxTimes，则不再重发该消息，并标记为死亡
     */
    private Integer maxTimes;
    /**
     * 等待所有线程执行完成的超时时间（毫秒），
     */
    private Integer waitCompleteTimeout = 10000;
}
