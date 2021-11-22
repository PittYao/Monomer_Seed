package com.bugprovider.seed.security.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
@EnableConfigurationProperties(IgnoreUrlsConfig.class)
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();

    public Boolean passIgnoreUrls(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain, Boolean passChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);

        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            if (passChain) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return true;
            }
        }

        //白名单请求直接放行
        String requestURI = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : this.getUrls()) {
            if (pathMatcher.match(path, requestURI)) {
                if (passChain) {
                    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                    return true;
                }
            }
        }

        return false;
    }
}
