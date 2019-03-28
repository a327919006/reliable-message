package com.cn.rmq.schedule.tasks;

import com.cn.rmq.api.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>Description:
 * 标记消息死亡定时任务
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/28.
 */
@Component
@Slf4j
public class DeadTask {

    @Reference
    private IMessageService messageService;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void task() {
        log.info("【DeadTask】start");


        log.info("【DeadTask】end");
    }
}
