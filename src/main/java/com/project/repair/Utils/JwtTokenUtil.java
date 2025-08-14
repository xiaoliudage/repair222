package com.project.repair.Utils;

import com.project.repair.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final JwtProperties jwtProperties;


    /**
     * 生成token
     * */
    public String generateToken(String username){
        String secret= jwtProperties.getSecret();
        Long expiration= jwtProperties.getExpiration();

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration*1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 获取解析数据
     * */
    public Claims getAllClaimsFromToken(String token){
        String secret= jwtProperties.getSecret();

        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取用户名
     * */
    public String getUsernameFromToken(String token){
        Claims claim = getAllClaimsFromToken(token);
        return claim.getSubject();
    }

    /**
     * 检查token是否过期
     * */
    public Boolean isTokenExpired(String token){
        Claims claim = getAllClaimsFromToken(token);
        return claim.getExpiration().before(new Date());
    }

    /**
     * 验证Token
     * */
    public Boolean validateToken(String token){
        String username = getUsernameFromToken(token);
        Boolean isToken = isTokenExpired(token);
        return !isToken && username.equals(getUsernameFromToken(token));
    }



}
