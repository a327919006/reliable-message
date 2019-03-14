package com.cn.rmq.api.model.enums;

/**
 * <p>Title:</p>
 * <p>Description:消息状态常量</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
public enum MessageStatusEnum {
    /**
     * 待确认
     */
    WAIT((byte) 0),

    /**
     * 发送中
     */
    SENDING((byte) 1);

    private byte value;

    MessageStatusEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}