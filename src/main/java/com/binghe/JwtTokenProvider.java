package com.binghe;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {

    private String secretKey;
    private Long expiredTime;

    public JwtTokenProvider(String secretKey, Long expiredTime) {
        this.secretKey = secretKey;
        this.expiredTime = expiredTime;
    }

    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiredTime);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createToken(Map<String, Object> header, Map<String, Object> payload) {
        header.put("exp", new Date().getTime() + expiredTime);

        return Jwts.builder()
            .setHeader(header)
            .setClaims(payload)
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
            .compact();
    }



    public String getPayload(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
