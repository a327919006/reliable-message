package com.cn.rmq.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Title:</p>
 * <p>Description:
 * 校验异常
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/2.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckException extends RuntimeException {
    private int code = 1;
    private String msg;

    public CheckException(String message) {
        super(message);
        this.msg = message;
    }
}
