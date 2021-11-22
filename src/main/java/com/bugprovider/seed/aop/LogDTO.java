package com.bugprovider.seed.aop;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

/**
 * @author BugProvider
 * @apiNote 日志
 */
@Data
@Builder
public class LogDTO {
    @JSONField
    private String requestURL;
    @JSONField(ordinal = 1)
    private String controllerMethod;
    @JSONField(ordinal = 2)
    private String requestMethod;
    @JSONField(ordinal = 3)
    private String requestParams;
    @JSONField(ordinal = 4)
    private String contentType;
    @JSONField(ordinal = 5)
    private Boolean isError;
    @JSONField(ordinal = 6)
    private String characterEncoding;
    @JSONField(ordinal = 7)
    private String executeTime;
    @JSONField(ordinal = 8)
    private String remoteAddress;
}
