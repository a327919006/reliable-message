package com.cn.rmq.schedule.service.impl;

import com.cn.rmq.api.schedule.service.IRecoverMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Slf4j
@Service
public class RecoverMessageServiceImpl implements IRecoverMessageService {

    @Override
    public void recoverSendingMessage() {

    }
}
