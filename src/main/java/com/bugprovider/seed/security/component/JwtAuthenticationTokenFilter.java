package com.bugprovider.seed.security.component;

import cn.hutool.core.util.StrUtil;
import com.bugprovider.seed.security.config.IgnoreUrlsConfig;
import com.bugprovider.seed.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWT登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 获取请求头中 Authorization 属性值
        String authHeader = request.getHeader(this.tokenHeader);

        // 放行 OPTIONS请求 和 白名单请求
        Boolean passIgnoreUrls = ignoreUrlsConfig.passIgnoreUrls(request, response, chain,false);
        if (passIgnoreUrls) {
            chain.doFilter(request, response);
            return;
        }

        // token 不能为空
        if (StrUtil.isBlank(authHeader)) {
            request.setAttribute("TokenError", "请求头中token不能为空");
            chain.doFilter(request, response);
            return;
        }

        // authToken 不以 Bearer 开头
        if (!authHeader.startsWith(this.tokenHead)) {
            request.setAttribute("TokenError", "请求头中token值未以" + this.tokenHead + "开头");
            chain.doFilter(request, response);
            return;
        }

        // 截取token值
        String authToken = authHeader.substring(this.tokenHead.length());
        // 校验token是否过期
        boolean tokenExpired = jwtTokenUtil.isTokenExpired(authToken);
        if (tokenExpired) {
            request.setAttribute("TokenError", "token已过期");
            chain.doFilter(request, response);
            return;
        }

        // 从token值获取用户名
        String username = jwtTokenUtil.getUserNameFromToken(authToken);
        LOGGER.info("checking username:{}", username);
        if (StrUtil.isBlank(username)) {
            request.setAttribute("TokenError", "token异常");
            chain.doFilter(request, response);
            return;
        }

        // 请求中已有用户信息
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        // 根据用户名查询用户信息
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (Objects.isNull(userDetails) || StrUtil.isBlank(userDetails.getUsername())) {
            request.setAttribute("TokenError", "token中用户不存在");
            chain.doFilter(request, response);
            return;
        }

        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            LOGGER.info("authenticated user:{}", username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}