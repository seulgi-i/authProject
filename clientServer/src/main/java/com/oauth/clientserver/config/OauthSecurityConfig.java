package com.oauth.clientserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.clientserver.service.OAuth2UserService;
import com.oauth.clientserver.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Tag(name = "login", description = "API Document")
public class OauthSecurityConfig {
    private final OAuth2UserService oAuth2UserService;


    final Log logger = LogFactory.getLog(getClass());
    @Bean
    @Order(1)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .sessionManagement(sessionManagement -> {
//                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login/token", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**","/swagger-ui/index.html#/oauth-2-login-controller/Login","http://localhost:3000").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Configurer -> oauth2Configurer
                .successHandler(successHandler())
                .userInfoEndpoint(userInfo ->
                    userInfo.userService(oAuth2UserService)));

        return http.build();

    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            long tokenPeriod = 1000L * 60L * 10L;
            long refreshTokenPeriod = 1000L * 60L * 60L * 24L * 7L; // 1 week
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String email = defaultOAuth2User.getAttributes().get("email").toString();


            String accessToken = JwtUtil.createJwt(email, tokenPeriod);
            String refreshToken = JwtUtil.createJwt(email, refreshTokenPeriod);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.addHeader("refreshToken", "Bearer " + refreshToken);
            response.setHeader("Authorization", "Bearer " + accessToken);



            // SecurityContext에 Authentication 객체를 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.sendRedirect("/login");

        });
    }

//    @Order(0)
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*")); // 허용할 오리진을 설정해주세요
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST")); // 허용할 메서드를 설정해주세요
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
