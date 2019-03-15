package com.cn.rmq.api.model.dto.cms;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/14.
 */
@Getter
@Setter
public class PageReq implements Serializable {
    private int page = 1;
    private int rows = 10;
}
