package com.cn.rmq.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 */
@Getter
@Setter
@ToString
public class RmqMessage implements Serializable {
    private String messageId;
    private String messageBody;
}
