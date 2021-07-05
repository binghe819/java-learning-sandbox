package com.binghe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class JjwtTest {

    @Test
    void JWT_키_생성_및_검증() {
        // 비밀키
        String secretKey = "binghe";

        // 헤더
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // 페이로드
        Map<String, Object> payloads = new HashMap<>();
        Long expiredTime = Long.valueOf(1000 * 600); // 만료기간
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime); // 현재시간 + 만료기간
        payloads.put("exp", now);
        payloads.put("data", "Hello JwtWorld"); // 데이터

        // 토큰 생성
        String newToken = Jwts.builder()
            .setHeader(headers)
            .setClaims(payloads)
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
            .compact();
        // 토큰 출력
        System.out.println(newToken);

        // 토큰 검증
        Claims tokenClaims = Jwts
            .parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(newToken)
            .getBody();
        Date expiration = tokenClaims.get("exp", Date.class);
        System.out.println(expiration);

        // 데이터 출력
        String data = tokenClaims.get("data", String.class);
        assertEquals(data, "Hello JwtWorld");
    }
}
