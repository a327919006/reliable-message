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
    private int pageNum = 1;
    private int pageSize = 10;
}
