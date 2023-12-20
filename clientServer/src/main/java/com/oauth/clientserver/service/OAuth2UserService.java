package com.oauth.clientserver.service;


import com.oauth.clientserver.repository.UserRepository;
import com.oauth.clientserver.repository.entity.UserEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    final Log logger = LogFactory.getLog(getClass());


    private final  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public OAuth2UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttributes().get("email").toString();
        String name = oAuth2User.getAttributes().get("name").toString();
        String password = "1234";
        UserEntity userEntity = userRepository.findByEmail(email);

        logger.info("userEntity???===> "+userEntity);
        logger.info("password???===> "+password);

        // User info generate for DB
        if (userEntity == null) {
            // 사용자가 데이터베이스에 없으면 새로운 사용자를 생성.
            userEntity = new UserEntity();
            userEntity.setEmail(email);
            userEntity.setName(name);
            userEntity.setRole("ADMIN");
            userEntity.setPassword(passwordEncoder.encode(password));

            userRepository.save(userEntity);
        }

        // User Role generate for session
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ADMIN");

        // nameAttributeKey for Google ='sub'
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // DefaultOAuth2User 객체 생성 => 세션에 저장되어 사용자가 인증된 상태를 유지하는 데 사용.
        // authorities : userEntity.getRole()의 권한 목록
        // oAuth2User.getAttributes : OAuth2 제공자로부터 받은 사용자 속성 정보 ex) Google name, email
        // userNameAttributeName : 사용자의 고유 이름을 식별하는 키로, OAuth2 제공자가 제공하는 사용자 정보에서 중요한 역할을 함.
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
        // DefaultOAuth2User 객체는 스프링 시큐리티 컨텍스트에 저장되어,
        // 사용자가 인증된 상태를 나타내며, 애플리케이션 내에서 사용자의 권한을 확인하는 데 사용된다.
    }


}