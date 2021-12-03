package com.bugprovider.seed.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 用户登录参数
 */
@Getter
@Setter
public class UmsAdminParam {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "备注")
    private String note;
}
