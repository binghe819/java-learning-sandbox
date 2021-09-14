package com.binghe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JjwtTest {

    private JwtTokenProvider jwtTokenProvider;
    private String secretKey;

    @BeforeEach
    void setUp() {
        long expirationTime = TimeUnit.HOURS.toMillis(1);
        secretKey = "binghe819";
        jwtTokenProvider = new JwtTokenProvider(secretKey, expirationTime);
    }

    @Test
    void JWT_생성_간단버전() {
        // given
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", "mark");

        // when
        String token = jwtTokenProvider.createToken(payloads);

        // then
        assertThat(token).isNotBlank();
        System.out.println(token);
    }

    @Test
    void JWT_생성_상세버전() {
        // given
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", "mark");

        // when
        String token = jwtTokenProvider.createToken(headers, payloads);

        // then
        assertThat(token).isNotBlank();
        System.out.println(token);
    }

    @DisplayName("유효성 검증(성공 케이스) - 유효한 토큰인 경우 예외가 발생하지 않는다.")
    @Test
    void JWT_유효성_검증_성공() {
        // given
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", "mark");

        String token = jwtTokenProvider.createToken(payloads);

        // when, then
        assertThatCode(() -> {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        }).doesNotThrowAnyException();
    }

    @DisplayName("유효성 검증(실패 케이스) - 만료기간 초과시 ExpiredJwtException을 던진다.")
    @Test
    void JWT_유효성_검증_실패_유효기간() {
        // given
        JwtTokenProvider provider = new JwtTokenProvider(secretKey, 0L);

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", "mark");

        String token = provider.createToken(payloads);

        // when, then
        assertThatCode(() -> {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        }).isInstanceOf(ExpiredJwtException.class);
    }

    @DisplayName("유효성 검증(실패 케이스) - 유효하지 않은 토큰일 경우 SignatureException을 던진다.")
    @Test
    void JWT_유효성_검증_실패_유효하지_않는_토큰() {
        // given
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", "mark");

        // when
        String token = jwtTokenProvider.createToken(payloads) + "invalid";

        // when, then
        assertThatCode(() -> {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        }).isInstanceOf(SignatureException.class);
    }

    @DisplayName("유효성 검증(실패 케이스) - 빈 토큰을 검증하려고 하면 IllegalArgumentException을 던진다.")
    @Test
    void JWT_유효성_검증_실패_빈_토큰() {
        // given
        String token = "";

        // when, then
        assertThatCode(() -> {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("토큰 정보 읽기 - payload 정보 읽기")
    @Test
    void JWT_정보_읽기_payload() {
        // given
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("username", "mark");

        // when
        String token = jwtTokenProvider.createToken(payloads);
        Jws<Claims> claimsJws = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token);
        Claims body = claimsJws.getBody();

        // when
        assertThat(body.get("username", String.class ))
            .isEqualTo("mark");
    }
}
