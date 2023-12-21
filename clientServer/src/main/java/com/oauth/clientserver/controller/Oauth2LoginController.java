package com.oauth.clientserver.controller;

import com.oauth.clientserver.controller.dto.LoginRequest;
import com.oauth.clientserver.repository.UserRepository;
import com.oauth.clientserver.repository.entity.UserEntity;
import com.oauth.clientserver.response.LoginResponse;
import com.oauth.clientserver.response.MapResponse;
import com.oauth.clientserver.service.ResponseService;
import com.oauth.clientserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@CrossOrigin(origins = "http://about:blank")
@RestController
@RequiredArgsConstructor
public class Oauth2LoginController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;

    final Log logger = LogFactory.getLog(getClass());


    @PostMapping("/user/login")
    @Operation(summary = "Login", description = "Login")
    public MapResponse<String> LoginAuth (@RequestBody LoginRequest userInfo, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String getEmail = userInfo.getEmail();
        String encodingPassword = userInfo.getPassword();

        UserEntity userEntity = userRepository.findByEmail(getEmail);
        String encodePw = userEntity.getPassword();

        if (passwordEncoder.matches(encodingPassword, encodePw)) {
            session.setAttribute("member", userInfo);
            String token = userService.login(userEntity.getEmail());

            String refreshToken = userService.refreshToken(userEntity.getName());

            LoginResponse loginResponse = new LoginResponse();
            Map<String, String> tokenMap = loginResponse.loginSuccess(token, refreshToken);

            return responseService.getMapResponse(tokenMap);
        }

        throw new Exception();

    }
}