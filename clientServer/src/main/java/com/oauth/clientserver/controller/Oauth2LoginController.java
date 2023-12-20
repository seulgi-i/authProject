package com.oauth.clientserver.controller;

import com.oauth.clientserver.controller.dto.LoginRequest;
import com.oauth.clientserver.repository.UserRepository;
import com.oauth.clientserver.repository.entity.UserEntity;
import com.oauth.clientserver.response.LoginResponse;
import com.oauth.clientserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://about:blank")
@RestController
@RequiredArgsConstructor
public class Oauth2LoginController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    final Log logger = LogFactory.getLog(getClass());


    @PostMapping("/login/token")
    @Operation(summary = "LoginToken", description = "Login/Join token")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest userInfo, HttpServletResponse response) throws Exception {
        String getEmail = userInfo.getEmail();


        logger.info("getEmail" + getEmail);
        // DB에서 추가적인 사용자 정보 조회
        UserEntity userEntity = userRepository.findByEmail(getEmail);


        if (userEntity == null || !userEntity.getName().equals(getEmail)) {
            throw new Exception();
        }

        String token = userService.login(userEntity.getEmail());
        String refreshToken = userService.refreshToken(userEntity.getName());

        response.setHeader("Authorization", "Bearer " + token);
        response.addHeader("refreshToken", refreshToken);

        logger.info("@@@@@@@" + token);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setRefreshToken(refreshToken);

        return ResponseEntity.ok().body(loginResponse);
    }


    @PostMapping("/user/login")
    @Operation(summary = "Login", description = "Login")
    public String LoginAuth(@RequestBody LoginRequest userInfo, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String getEmail = userInfo.getEmail();
        String encodingPassword = userInfo.getPassword();

        UserEntity userEntity = userRepository.findByEmail(getEmail);
        String encodePw = userEntity.getPassword();



        if (passwordEncoder.matches(encodingPassword, encodePw)) {
            session.setAttribute("member", userInfo);

            return "로그인 성공!";

        }

        throw new Exception();

    }
}
