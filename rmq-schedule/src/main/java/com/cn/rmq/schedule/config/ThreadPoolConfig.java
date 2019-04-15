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
    private CheckTaskConfig checkTaskConfig;
    @Autowired
    private RecoverTaskConfig recoverTaskConfig;

    @Bean
    public ThreadPoolExecutor checkExecutor() {
        // 为线程池起名
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("check-pool-").build();
        return new ThreadPoolExecutor(checkTaskConfig.getCorePoolSize(),
                checkTaskConfig.getMaxPoolSize(),
                checkTaskConfig.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(checkTaskConfig.getQueueCapacity()),
                namedThreadFactory);
    }

    @Bean
    public ThreadPoolExecutor recoverExecutor() {
        // 为线程池起名
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("recover-pool-").build();
        return new ThreadPoolExecutor(recoverTaskConfig.getCorePoolSize(),
                recoverTaskConfig.getMaxPoolSize(),
                recoverTaskConfig.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(recoverTaskConfig.getQueueCapacity()),
                namedThreadFactory);
    }
}
