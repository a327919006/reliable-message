package com.cn.rmq.api.cms.model.vo.message;

import com.cn.rmq.api.enums.AlreadyDeadEnum;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.utils.json.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/15.
 */
@Getter
@Setter
public class CmsMessageVo implements Serializable {
    private String id;

    private String consumerQueue;

    private Short resendTimes;

    private Byte alreadyDead;
    private String alreadyDeadName;

    private Byte status;
    private String statusName;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date updateTime;

    private String messageBody;

    public String getAlreadyDeadName() {
        return AlreadyDeadEnum.format(alreadyDead);
    }

    public String getStatusName() {
        return MessageStatusEnum.format(status);
    }
}
