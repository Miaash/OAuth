package com.lkbt.auction.configuration.security.provider;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.lkbt.auction.configuration.security.dto.request.UserAccessToken;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * [카카오로그인 인증 처리 Provider.]
 * AuthenticationProvider 인터페이스
 */
@Component
@NoArgsConstructor
public class AutherizationProvider implements AuthenticationProvider {
    @NonNull
    public final UserDetailServiceImpl userDetailService;

    // AuthenticationProvider에 정의되어있는 메서드 override해야함.
    // Authentication authenticate(Authentication authentication) throws
    // AuthenticationException;
    @Override
    public Authentication authentication(Authentication authentication) throws AuthenticationException {
        final UserAccessToken token = (UserAccessToken) authentication;
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAccessToken.class.isAssignableFrom(authentication);
    }
}
