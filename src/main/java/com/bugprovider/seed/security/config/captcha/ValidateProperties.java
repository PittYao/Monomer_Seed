package com.bugprovider.seed.security.config.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @description: 验证码抽象 用于扩展 各种验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateProperties {
    // 是否开启验证码功能
    private Boolean open = false;
    // 图形验证码
    ImageCodeProperties image = new ImageCodeProperties();
}
