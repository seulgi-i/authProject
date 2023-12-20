package com.oauth.clientserver.response;

import com.oauth.clientserver.service.ResponseService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse  extends ResponseService {

    private String accessToken;
    private String refreshToken;


}
