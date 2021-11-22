package com.bugprovider.seed.modules.ums.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台用户管理
 */
@Controller
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/subject")
public class UmsTestController {

    /**
     * test
     *
     * @return string
     */
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "ok";
    }
}
