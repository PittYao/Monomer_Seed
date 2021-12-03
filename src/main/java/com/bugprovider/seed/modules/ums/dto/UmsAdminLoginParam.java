package com.bugprovider.seed.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    // 验证码
    private String code;
    // 验证码唯一编号
    private String captchaKey;
}
