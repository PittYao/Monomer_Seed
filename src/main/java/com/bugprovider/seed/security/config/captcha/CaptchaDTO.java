package com.bugprovider.seed.security.config.captcha;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @description: 验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaptchaDTO {
    /**
     * 验证码标识符
     */
    private String captchaKey;
    /**
     * 验证码过期时间
     */
    private Integer expire;
    /**
     * base64字符串
     */
    private String base64Img;
}
