package com.fsAdmin.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fsAdmin.config.security.dto.UserLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret:helloworld}")
    private String secret;

    @Value("${jwt.expiration:7}")
    private Integer expiration;

    public String createJwt(UserLoginInfo userLoginInfo) {
        Date expireDate = new Date(System.currentTimeMillis() + expiration * 1000 * 60 * 60 * 24);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("userId", userLoginInfo.getUserId())//userId
                .withClaim("username", userLoginInfo.getUsername())//用户名
                .withClaim("sessionId", userLoginInfo.getSessionId())//会话id
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(String.valueOf(secret))); //SECRET加密
        return token;
    }

    /**
     * 验证token是否有效
     *  有效返回true 无效返回false
     * @param token
     * @return
     */
    public boolean validToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取 token 过期时间
     */
    public Date getTokenExpiredTime(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @return
     */
    public UserLoginInfo getUserLoginInfoFromJwt(String token) {

        Map<String, Claim> map = getMapFromToken(token);
        if (map == null) {
            return null;
        }

        Long userId = map.get("userId").asLong();
        String username = map.get("username").asString();
        String sessionId = map.get("sessionId").asString();

        return new UserLoginInfo().setUserId(userId).setUsername(username).setSessionId(sessionId);
    }

    /**
     * 获取jwt中的所有参数
     *
     * @param token
     * @return
     */
    public Map<String, Claim> getMapFromToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("token解码异常");
            return null;
        }
    }

}
