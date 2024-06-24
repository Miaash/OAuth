package com.lkbt.auction.configuration.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.lkbt.auction.configuration.security.dto.request.UserAccessToken;
import com.lkbt.auction.configuration.security.dto.response.UserAuthToken;
import com.lkbt.auction.configuration.security.user.UserInfo;
import com.lkbt.auction.util.global.GlobalUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * [카카오로그인 인증 처리 Provider.]
 * AuthenticationProvider 인터페이스의 메서드 구현.
 * 사용자의 인증 정보를 검증하고, 사용자의 세부 정보를 로드하여 인증된 사용자 객체를 생성
 * 
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    @NonNull
    private final UserDetailServiceImpl userDetailServiceImpl;

    // AuthenticationProvider에 정의되어있는 메서드 override해야함.
    // Authentication authenticate(Authentication authentication) throws
    // AuthenticationException;
    // authenticate 메서드: 사용자의 액세스 토큰을 받아 인증을 수행.
    // 사용자 정보를 로드한 후, 사용자가 존재하고 활성화된 경우 UserAuthToken을 생성하여 반환.
    // 그렇지 않으면 새로운 사용자를 등록하고 인증 토큰을 생성하여 반환.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserAccessToken token = (UserAccessToken) authentication;
        final String username = token.getName();
        final UserInfo userInfo = (UserInfo) userDetailServiceImpl.loadUserByUsername(username);

        if (GlobalUtil.isNotNull(userInfo) && userInfo.isEnabled()) {
            // 사용자 정보가 있으면 해당 사용자정보로 인증토큰 생성.
            return new UserAuthToken(userInfo, userInfo.getAuthorities());
        } else {
            // 없으면 새로운 사용자 등록 후 새로운 사용자정보로 인증토큰 생성.
            userDetailServiceImpl.add(token.getAddParam());
            final UserInfo generatedUser = (UserInfo) userDetailServiceImpl.loadUserByUsername(username);
            return new UserAuthToken(generatedUser, generatedUser.getAuthorities());
        }
    }

    // suppors 메서드: UserAccessToken타입의 인증을 지원하는지에 대한 여부를 반환.
    @Override
    public boolean supports(Class<?> authentication) {
        return UserAccessToken.class.isAssignableFrom(authentication);
    }
}
