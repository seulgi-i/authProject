package com.oauth.clientserver.config;


import com.oauth.clientserver.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

     final String secretKey = "my-token-secret-key";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.error("authorization?.");
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            logger.error("authorization 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }
        //Token 꺼내기
        String token = authorization.split(" ")[1];
        logger.info("@@@@ token @@@ : " + token);


        logger.info("@@@ secretKey @@@ :" + secretKey);


        // Token Expired되어있는지 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            logger.error("Token이 만료 되었습니다.");

            filterChain.doFilter(request, response);
            return;
        }

        //UserName Token에서 꺼내기
        String userName = JwtUtil.getUserName(token, secretKey);

        logger.info("userName");
        logger.info(userName);
        //권한부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));

        //Detail을 넣기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }


}
