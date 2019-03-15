package com.cn.rmq.api.model.dto.cms.message;

import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.model.dto.cms.PageReq;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/14.
 */
@Getter
@Setter
public class CmsMessageListDto extends PageReq {
    private String id;

    private String consumerQueue;

    private Byte alreadyDead;

    private Byte status;

    private String createStartTime; // 注册时间起

    private String createEndTime; // 注册时间止

    public String getCreateStartTime() {
        if (StringUtils.isBlank(createStartTime)) {
            return null;
        }
        return createStartTime;
    }

    public String getCreateEndTime() {
        if (StringUtils.isBlank(createEndTime)) {
            return null;
        }
        return createEndTime;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
