package com.cn.rmq.api.model.dto.queue;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/16.
 */
@Data
public class QueueAddDto implements Serializable {
    @NotBlank
    private String businessName;

    @NotBlank
    private String consumerQueue;

    @NotBlank
    private String checkUrl;

    @NotNull
    private Integer checkDuration;

    @NotNull
    private Integer checkTimeout;

    private String createUser;

    private String updateUser;
}
