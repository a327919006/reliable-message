package com.cn.rmq.api.cms.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>后台table对象</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Getter
@Setter
public class DataGrid implements Serializable {
    private static final List<Object> EMPTY_LIST = new ArrayList<>();
    private long total;
    private List rows = EMPTY_LIST;
}
