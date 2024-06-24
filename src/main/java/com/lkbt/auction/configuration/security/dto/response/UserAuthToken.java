package com.lkbt.auction.configuration.security.dto.response;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.lkbt.auction.configuration.security.user.UserInfo;

/**
 * [AuthToken 규격]
 * AbstractAuthenticationToken클래스를 상속받아 클라이언트에 보낼 인증토큰 규격화.
 * fields: Authorities -> 사용자에게 부여된 권한, Authenticated -> 인증여부, Details -> 인증요청
 * 세부정보
 * methods: getPrincipal() -> 인증된 사용자를 나타내는 객체 반환, getCredentials() -> 사용자비밀번호같은
 * 자격증명 반환.
 */
public class UserAuthToken extends AbstractAuthenticationToken {
    private UserInfo principal;

    public UserAuthToken(UserInfo principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setDetails(principal);
        super.setAuthenticated(principal.isEnabled());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
