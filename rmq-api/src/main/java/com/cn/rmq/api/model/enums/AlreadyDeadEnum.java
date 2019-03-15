package com.cn.rmq.api.model.enums;

/**
 * <p>Title:</p>
 * <p>Description:消息状态常量</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
public enum AlreadyDeadEnum {
    /**
     * 未死亡
     */
    NO((byte) 0),

    /**
     * 已死亡
     */
    YES((byte) 1);

    private byte value;

    AlreadyDeadEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static String format(Byte value) {
        switch (value) {
            case 0:
                return "未死亡";
            case 1:
                return "已死亡";
            default:
                return "";
        }
    }
}