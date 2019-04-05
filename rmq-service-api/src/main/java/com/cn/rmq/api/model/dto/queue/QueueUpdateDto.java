package com.cn.rmq.api.model.dto.queue;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class QueueUpdateDto implements Serializable {
    @NotBlank
    private String id;

    @NotBlank
    private String businessName;

    @NotBlank
    private String consumerQueue;

    @NotBlank
    private String checkUrl;

    @NotNull
    private Integer checkDuration;

    @NotNull
    private Short checkTimeout;

    private String updateUser;
}
