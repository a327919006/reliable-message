package com.cn.rmq.api.cms.model.vo.queue;

import com.cn.rmq.api.utils.json.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/16.
 */
@Getter
@Setter
public class CmsQueueVo implements Serializable {
    private String id;

    private String businessName;

    private String consumerQueue;

    private String checkUrl;

    private Integer checkDuration;

    private Integer checkTimeout;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createUser;

    private String updateUser;


}
