package com.cn.rmq.api.model;

/**
 * <p>Title: Constants</p>
 * <p>Description: 常量类</p>
 *
 * @author Chen Nan
 */
public class Constants {

    private Constants() {
        throw new RuntimeException("Constants.class can't be instantiated");
    }

    /* 通用应答码 URC-Universal Response Code */
    /**
     * 应答码：成功
     */
    public static final int CODE_SUCCESS = 0;
    /**
     * 应答码：失败
     */
    public static final int CODE_FAILURE = 1;

    /**
     * MSG
     */
    public static final String MSG_SUCCESS = "SUCCESS";
    public static final String MSG_READ_CONFIG_ERROR = "读取配置文件错误";
}
