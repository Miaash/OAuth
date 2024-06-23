package com.lkbt.auction.configuration.security.dto.request;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * [AccessToken 규격]
 * UsernamePasswordAuthenticationToken클래스를 상속받아 엑세스 토큰 규격화.
 * UsernamePasswordAuthenticationToken: 사용자이름, 비밀번호로 인증정보 캡슐화.
 * params: principal -> 인증주체(사용자이름), credentials -> 자격증명(비번.)
 */
public class UserAccessToken extends UsernamePasswordAuthenticationToken {

    // UsernamePasswordAuthenticationToken의 생성자를 호출하여 인증주체, 자격증명 설정.
    public UserAccessToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    // 아래와 같이 사용.
    // UserAccessToken을 사용하여 인증되지 않은 토큰 생성
    // UserAccessToken token = new UserAccessToken("user", "password");

    // AuthenticationManager를 사용하여 인증 시도
    // Authentication authentication = authenticationManager.authenticate(token);
}
