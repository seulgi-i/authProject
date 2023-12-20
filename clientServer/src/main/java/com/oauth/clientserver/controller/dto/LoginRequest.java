package com.oauth.clientserver.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    @Schema(description = "사용자 이메일", example = "458chi@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "1234")
    private String password;
}
