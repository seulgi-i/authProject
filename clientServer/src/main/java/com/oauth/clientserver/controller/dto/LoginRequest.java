package com.oauth.clientserver.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    @Schema(description = "사용자 이름", example = "seulgi choi")
    private String userName;
    @Schema(description = "이메일", example = "458chi@gmail.com")
    private String email;
}
