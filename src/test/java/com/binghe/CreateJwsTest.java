package com.binghe;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class CreateJwsTest {

    @Test
    void setHeaderParam() {
        String jws = Jwts.builder()
            .setHeaderParam("kid", "binghe")
            .setHeaderParam("type", "test")
            .setSubject("binghe819")
            .signWith(SignatureAlgorithm.HS256, "secret key")
            .compact();

        System.out.println(jws);
    }

    @Test
    void setHeader() {
        Header header = Jwts.header(); // Map<String, Object>처럼 사용 가능하다.

    }

    @Test
    void claim() {
        String jws = Jwts.builder()
            .claim("hello", "world")
            .claim("name", "binghe")
            .signWith(SignatureAlgorithm.HS256, "secret key")
            .compact();

        System.out.println(jws);
    }

    @Test
    void setClaims() {
        Claims claims = Jwts.claims();
        
    }
}
