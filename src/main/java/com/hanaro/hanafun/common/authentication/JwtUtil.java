package com.hanaro.hanafun.common.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access-expired-ms}")
    private Long accessExpiredMS;

    @Value("${jwt.claims.auth-key}")
    private String authKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, Long userId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(authKey, userId);

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessExpiredMS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //Jwt 파싱하여 만료여부를 확인한다.
    public Boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return true;  // 만료된 것으로 간주
        }
    }

    //Jwt 파싱하여 인증 정보를 반환한다.
    public long getAuthValue(String token) {
        try {
            return getClaims(token).get(authKey, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;  // 기본값 반환
        }
    }

    //클래임 건지기
    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    //Jwt 인증 정보를 토큰으로 반환
    public Authentication getAuthFromJwt(String token) {
        Long userId = getAuthValue(token);
        return new UsernamePasswordAuthenticationToken(userId, null,
                Collections.singleton(new SimpleGrantedAuthority("USER")));
    }
}
