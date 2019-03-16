package com.cn.rmq.api.cms.model.dto.queue;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class CmsQueueUpdateDto implements Serializable {
    @NotBlank
    private String id;

    @NotBlank
    private String businessName;

    @NotBlank
    private String consumerQueue;

    private String updateUser;
}
