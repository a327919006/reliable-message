package com.cn.rmq.cms.web;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.cn.rmq.cms.utils.CaptchaValidateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>验证码控制器</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Controller
public class CaptchaController {

    /**
     * <p>生成的图片验证码 保存在session中的validateCode中</p>
     */
    @RequestMapping(value = "login/captcha", method = RequestMethod.GET)
    public void captcha(HttpSession session,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        ICaptcha captcha = CaptchaUtil.createCircleCaptcha(110, 30, 4, 10);

        // 禁止图像缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        // 将四位数字的验证码保存到Session中。
        session.setAttribute(CaptchaValidateUtil.SESSION_KEY, captcha.getCode());
        session.setAttribute(CaptchaValidateUtil.SESSION_TIME_KEY, System.currentTimeMillis());
        outputStream.flush();
        outputStream.close();
    }
}
