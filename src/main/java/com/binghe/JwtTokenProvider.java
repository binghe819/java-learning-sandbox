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
    private Long expirationTimeInMilliSeconds;

    public JwtTokenProvider(String secretKey, Long expirationTimeInMilliSeconds) {
        this.secretKey = secretKey;
        this.expirationTimeInMilliSeconds = expirationTimeInMilliSeconds;
    }

    public String createToken(Map<String, Object> payload) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationTimeInMilliSeconds);

        return Jwts.builder()
            .setClaims(payload)
            .setIssuedAt(now)
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createToken(Map<String, Object> header, Map<String, Object> payload) {
        header.put("exp", new Date().getTime() + expirationTimeInMilliSeconds);

        return Jwts.builder()
            .setHeader(header)
            .setClaims(payload)
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
            .compact();
    }

    public String getPayloadByKey(String token, String key) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(key, String.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }

    public boolean validateTokenExpirationTime(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
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
