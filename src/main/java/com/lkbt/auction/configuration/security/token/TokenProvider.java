package com.lkbt.auction.configuration.security.token;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lkbt.auction.configuration.security.dto.response.UserAuthToken;
import com.lkbt.auction.configuration.security.user.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * [인증된 토큰 검증 및 토큰 생성 클래스]
 * TokenProvider는 JWT를 생성하고, 검증하며, JWT를 통해 인증 객체를 생성
 * UserAuthenticationProvider는 사용자 인증정보 검증, 사용자 세부 정부 로그해서 인증된 사용자 객체를 생성.
 * [단일책임의 원칙(SRP)]: UserAuthenticationProvider는 사용자 인증처리, TokenProvider는 jwt토큰
 * 생성 및 검증.
 */
@Slf4j // SLF4J(Simple Logging Facade for Java) API를 사용하여 로그 메시지를 기록
public class TokenProvider {
    // role
    protected static final String AUTHORITIES_KEY = "auth";
    // 사용자 ID
    protected static final String USER_ID_KEY = "usrid";
    // 사용자 이름
    protected static final String USER_NM_KEY = "usrnm";
    // 만료시간 milli초단위
    protected static final String EXPIRATION_MILLI_SEC = "expmsec";
    // 사용자 카카오 ID
    protected static final String KAKAO_ID_KEY = "kakaoId";
    // 전화번호
    protected static final String PHONE_NUM_KEY = "phoneNum";

    // 비밀키, 토큰유지시간, 비밀키 기준으로 사용할 jwt암호화 키
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final Key key;

    /**
     * [jwt토큰의 서명을 위한 비밀키, 토큰 유효시간 설정 생성자]
     * 
     * @param secret
     * @param tokenValidityInMilliseconds
     */
    public TokenProvider(String secret, long tokenValidityInMilliseconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;

        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰 생성 데이터 반환 클래스
     */
    @Getter
    public static class createTokenResult {
        private String accessToken;
        private Long expirationMilliSecond;

        // 추후 refreshToken이나 등등 있을 시 사용
        public createTokenResult(String accessToken, Long expirationMilliSecond) {
            this.accessToken = accessToken;
            this.expirationMilliSecond = expirationMilliSecond;
        }
    }

    /**
     * AuthenticationProvider를 통해 인증된 정보로 토큰 생성.
     * 
     * @param authentication
     * @return
     */
    public TokenProvider.createTokenResult createToken(Authentication authentication) {
        // role string 형태로
        final String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        final Map<String, Object> claimProperties = new HashMap<>();
        claimProperties.put(AUTHORITIES_KEY, authorities);

        final long expired;
        final Date expireDate;

        // 만료시각 계산
        expired = LocalDate.now().plusDays(1L).atTime(0, 0, 0, 0).toEpochSecond(ZoneOffset.of("+09:00")) * 1_000L; // millisecond로
        expireDate = new Date(expired);

        final UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        claimProperties.put(USER_ID_KEY, userInfo.getUsrId());
        claimProperties.put(USER_NM_KEY, userInfo.getUsrNm());
        claimProperties.put(KAKAO_ID_KEY, userInfo.getKakaoId());
        claimProperties.put(PHONE_NUM_KEY, userInfo.getPhoneNum());

        // 만료시각
        claimProperties.put(EXPIRATION_MILLI_SEC, expired);

        // token 생성
        // 인증된 정보 + 만료시간 + 사용자이름으로 jwt토큰 생성.
        final String accessToken = Jwts.builder()
                .setClaims(claimProperties)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .setExpiration(expireDate)
                .setSubject(authentication.getName())
                .compact();

        return new TokenProvider.createTokenResult(accessToken, expired);
    }

    /**
     * 생성된 jwt 토큰 기준으로 UserInfo 객체 생성.
     * 
     * @param token
     * @return
     */
    public Authentication getAuthenticationFromAuthentication(String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        final Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        final String usrNm = Optional.ofNullable(claims.get(USER_NM_KEY)).orElseGet(() -> "").toString();
        final String phoneNum = Optional.ofNullable(claims.get(PHONE_NUM_KEY)).orElseGet(() -> "").toString();
        final String kakaoId = claims.get(KAKAO_ID_KEY).toString();
        final Long expMilliSec = (Long) claims.get(EXPIRATION_MILLI_SEC);
        final String usrId = claims.get(USER_ID_KEY).toString();
        final UserInfo principal = new UserInfo(usrNm, authorities, usrId, usrNm, expMilliSec, phoneNum,
                kakaoId);
        return new UserAuthToken(principal, authorities);
    }

    /**
     * 토큰 유효성 검증
     * 
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (MalformedJwtException e) {
            log.info("MalformedJwtException: 잘못된 서명입니다.");
        } catch (ExpiredJwtException e2) {
            log.info("ExpiredJwtException: 로그인 유지시간이 만료되었습니다.");
        } catch (UnsupportedJwtException e3) {
            log.info("UnsupportedJwtException: 잘못된 서명입니다.");
        } catch (IllegalArgumentException e4) {
            log.info("IllegalArgumentException: 잘못된 서명입니다.");
        }

        return false;
    }

}
