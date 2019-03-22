package com.cn.rmq.schedule.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/22.
 */
@Configuration
public class ThreadPoolConfig {
    @Autowired
    private CheckTaskConfig config;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 为线程池起名
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("check-pool-%d").build();
        return new ThreadPoolExecutor(config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(config.getQueueCapacity()),
                namedThreadFactory);
    }
}
