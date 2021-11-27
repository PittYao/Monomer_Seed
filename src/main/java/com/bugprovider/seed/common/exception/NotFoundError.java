package com.bugprovider.seed.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author BugProvider
 * @date 2021/11/26
 * @apiNote 捕获 404 异常
 */
@Controller
public class NotFoundError implements ErrorController {

    @RequestMapping(value = {"/error"})
    @ResponseBody
    public void error() {
        Asserts.fail("没有该请求路由");
    }
}
