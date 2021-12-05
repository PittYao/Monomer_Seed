package com.bugprovider.seed.config;

import com.bugprovider.seed.annotation.IgnoreResponseAdvice;
import com.bugprovider.seed.common.api.CommonResult;
import com.bugprovider.seed.common.api.ResultCode;
import com.bugprovider.seed.security.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * json统一格式响应
 */
@RestControllerAdvice
public class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 对添加注解 @IgnoreResponseAdvice 的不做json响应处理
        IgnoreResponseAdvice methodAnnotation = methodParameter.getMethodAnnotation(IgnoreResponseAdvice.class);
        return methodAnnotation== null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof CommonResult) {
            return body;
        }

        // 如果是knife4j的请求不能封装 否则无法访问knife4j
        if (ignoreUrlsConfig.isKnife4j(serverHttpRequest)) {
            return body;
        }

        // 确定请求方法的要返回的默认消息
        HttpMethod method = serverHttpRequest.getMethod();
        ResultCode resultCode = ResultCode.SUCCESS;
        if (method != null) {
            switch (method) {
                case GET:
                    resultCode = ResultCode.GET_SUCCESS;
                    break;
                case POST:
                    resultCode = ResultCode.POST_SUCCESS;
                    break;
                case PUT:
                    resultCode = ResultCode.PUT_SUCCESS;
                    break;
                case DELETE:
                    resultCode = ResultCode.DELETE_SUCCESS;
                    break;
                default:
                    break;
            }
        }

        return new CommonResult<>(resultCode, body);
    }


}
