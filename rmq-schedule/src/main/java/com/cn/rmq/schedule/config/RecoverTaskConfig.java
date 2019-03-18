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
    private Integer maxTimes;
}
