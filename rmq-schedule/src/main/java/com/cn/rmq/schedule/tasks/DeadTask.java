package com.cn.rmq.schedule.tasks;

import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.schedule.config.RecoverTaskConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RecoverTaskConfig config;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void task() {
        log.info("【DeadTask】start");

        short maxResendTimes = (short) config.getInterval().size();

        int updateCount = messageService.updateMessageDead(maxResendTimes);
        log.info("【DeadTask】maxResendTimes={}, updateCount={}", maxResendTimes, updateCount);

        log.info("【DeadTask】end");
    }
}
