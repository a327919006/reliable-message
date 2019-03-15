package com.cn.rmq.api.model.enums;

/**
 * <p>系统资源类型枚举</p>
 */
public enum SysResourceTypeEnum {
    // 菜单
    MENU((byte)0),
    // 按钮
    BUTTON((byte) 1);

    private byte code;
    public byte getValue() {
        return code;
    }

    SysResourceTypeEnum(byte code) {
        this.code = code;
    }

    /**
     * <p>根据code获取对应枚举</p>
     *
     * @param code 枚举值
     * @return 对应枚举
     */
    public static SysResourceTypeEnum valueOf(byte code) {
        for (SysResourceTypeEnum item : values()) {
            if (item.getValue() == code) {
                return item;
            }
        }
        return null;
    }
}
