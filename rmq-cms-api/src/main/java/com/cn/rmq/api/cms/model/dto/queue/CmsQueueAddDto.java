package com.cn.rmq.api.cms.model.dto.queue;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/16.
 */
@Data
public class CmsQueueAddDto implements Serializable {
    @NotBlank
    private String businessName;

    @NotBlank
    private String consumerQueue;

    private String createUser;

    private String updateUser;
}
