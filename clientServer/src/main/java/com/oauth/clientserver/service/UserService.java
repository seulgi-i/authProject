package com.oauth.clientserver.service;

import com.oauth.clientserver.utils.JwtUtil;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    long tokenPeriod = 1000L * 60L * 10L;
    long refreshTokenPeriod = 1000L * 60L * 60L * 24L * 7L; // 1 week
    public String login(String userName) {
        return JwtUtil.createJwt(userName, tokenPeriod);
    }
    public String refreshToken(String userName) {
        return JwtUtil.createJwt(userName, refreshTokenPeriod);
    }

}
