package com.oauth.clientserver.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class LoginResponse extends CommonResponse {

    private String accessToken;
    private String refreshToken;
    final Log logger = LogFactory.getLog(getClass());

    public Map<String, String> loginSuccess (String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        return responseMap(accessToken, refreshToken);
    }

    public Map<String, String> responseMap (String accessToken, String refreshToken) {
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        logger.info("@@@@@ response @@@" + response);
        return response;
    }
}