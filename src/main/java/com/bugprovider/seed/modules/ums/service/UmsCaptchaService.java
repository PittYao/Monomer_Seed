package com.bugprovider.seed.modules.ums.service;

import com.bugprovider.seed.modules.ums.dto.UmsAdminLoginParam;
import com.bugprovider.seed.security.config.captcha.CaptchaDTO;

/**
 * @author: bugProvider
 * @description: 验证码
 */
public interface UmsCaptchaService {
    // 缓存验证码到redis
    CaptchaDTO cacheCaptcha();
    // 校验验证码是否正确
    Boolean checkCaptcha(UmsAdminLoginParam umsAdminLoginParam);
}
