package com.bugprovider.seed.security.config.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @description: 图形验证码 参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCodeProperties {
    private Integer width = 110;
    private Integer height = 40;
    private Integer length = 4;
    private Integer thickness = 4;
    private Integer lineCount = 2;
    // 60s 过期
    private Integer expired = 60;

}
