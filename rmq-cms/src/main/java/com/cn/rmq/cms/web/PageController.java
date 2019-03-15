package com.cn.rmq.cms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2018/3/27.
 */
@Controller
@RequestMapping(value = "/page")
public class PageController {

    @RequestMapping(value = "/{model}/{name}", method = RequestMethod.GET)
    public String page(@PathVariable("model") String model, @PathVariable("name") String name) {
        return model + "/" + name;
    }
}
