package com.bugprovider.seed.security.config;

import com.bugprovider.seed.security.config.captcha.ValidateProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: bugProvider
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    // 是否开启权限校验
    private Boolean open;

    // 验证属性
    private ValidateProperties captcha = new ValidateProperties();

}
