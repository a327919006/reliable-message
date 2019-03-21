package com.cn.rmq.schedule.tasks;

import com.cn.rmq.api.schedule.service.ICheckMessageService;
import com.cn.rmq.schedule.config.CheckTaskConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Component
@Slf4j
public class CheckTask {

    @Autowired
    private ICheckMessageService checkMessageService;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void check() {
        log.info("【CheckTask】start");

        checkMessageService.checkWaitingMessage();

        log.info("【CheckTask】end");
    }
}
