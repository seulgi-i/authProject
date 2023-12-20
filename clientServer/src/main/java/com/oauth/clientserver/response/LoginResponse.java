package com.oauth.clientserver.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@Setter
@Getter
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
}
