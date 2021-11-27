package com.bugprovider.seed.modules.ums.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.bugprovider.seed.common.exception.Asserts;
import com.bugprovider.seed.modules.ums.dto.UmsAdminLoginParam;
import com.bugprovider.seed.modules.ums.service.ICaptchaService;
import com.bugprovider.seed.security.config.SecurityProperties;
import com.bugprovider.seed.security.config.captcha.CaptchaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: bugProvider
 * @description: 验证码
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements ICaptchaService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public CaptchaDTO cacheCaptcha() {
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        Integer width = securityProperties.getCaptcha().getImage().getWidth();
        Integer height = securityProperties.getCaptcha().getImage().getHeight();
        Integer length = securityProperties.getCaptcha().getImage().getLength();
        Integer lineCount = securityProperties.getCaptcha().getImage().getLineCount();

        // 生成验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height, length, lineCount);
        captcha.setBackground(Color.PINK);

        // 验证码的内容： 例子： W5UK
        String captchaCode = captcha.getCode();

        // 获取 验证码 Base64编码
        String str = "data:image/jpeg;base64,";
        String base64Img = str + captcha.getImageBase64();

        // ------ 生成验证码响应实体 ------

        // 验证码唯一标识
        String captchaKey = IdUtil.fastSimpleUUID();
        // 验证码过期时间
        Integer expired = securityProperties.getCaptcha().getImage().getExpired();
        // 存入redis
        stringRedisTemplate.opsForValue().set(captchaKey, captchaCode, expired, TimeUnit.SECONDS);

        log.info("缓存验证码: {} 到redis,过期时间是:{}", captchaCode, expired);

        return CaptchaDTO.builder()
                .captchaKey(captchaKey)
                .base64Img(base64Img)
                .expire(expired)
                .build();
    }

    @Override
    public Boolean checkCaptcha(UmsAdminLoginParam umsAdminLoginParam) {
        if (securityProperties.getCaptcha().getOpen()) {
            String requestCode = umsAdminLoginParam.getCode();
            String captchaKey = umsAdminLoginParam.getCaptchaKey();

            // 校验请求中的 参数 合法性
            if (StrUtil.isBlank(requestCode)) {
                Asserts.fail("验证码不能为空");
            }
            if (StrUtil.isBlank(captchaKey)) {
                Asserts.fail("验证码唯一标识不能为空");
            }

            // 读取redis中的验证码
            String redisCode = stringRedisTemplate.opsForValue().get(captchaKey);

            // 验证码不存在 则 验证码已过期
            if (StrUtil.isBlank(redisCode)) {
                Asserts.fail("验证码已过期");
            }

            // 验证码存在 redis中的验证码 和 request中的验证码 不一样 则 验证码错误
            if (!redisCode.equals(requestCode)) {
                Asserts.fail("验证码不正确");
            }
        }
        return true;
    }

}
