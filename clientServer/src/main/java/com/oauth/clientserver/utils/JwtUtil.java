package com.oauth.clientserver.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static String secretKey = "my-token-secret-key";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public static String getUserName(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());

    }

    public static String createJwt(String userName, Long expiredMs) {

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("userName", userName).
                compact();

    }
}
