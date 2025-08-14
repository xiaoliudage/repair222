package com.project.repair.Filter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.repair.Entity.User;
import com.project.repair.Service.UserService;
import com.project.repair.Utils.JwtTokenUtil;
import com.project.repair.Utils.UserContext;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. 从请求头中获取Token
        String token = getJwtFromRequest(request);

        // 2. 验证Token
        if (token != null && jwtTokenUtil.validateToken(token)) {
            // 3. 从Token中获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);

            /**
             * 这里实现从数据库中查询用户信息,并存入localThread，进行数据共享
             * */
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, username);
            User user = userService.getOne(queryWrapper);

            UserContext.setUser(user);

            // 4. 创建认证对象
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, null);

            // 5. 将认证信息存入安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 6. 继续过滤器链
        chain.doFilter(request, response);
    }

    // 从请求头中提取Token
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}