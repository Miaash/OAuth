package com.lkbt.auction.configuration.security.user;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

/**
 * [사용자 인증 결과 반환 규격]
 */
public class UserInfo extends User {

    @Getter
    private String usrId;
    @Getter
    private String usrNm;
    @Getter
    private String phoneNum;
    @Getter
    private String kakaoId;
    @Getter
    @Setter
    private Long expirationMilliSecond;

    /**
     * 사용자 인증 성공시 반환 생성자
     *
     * @param username
     * @param password
     * @param usrId
     * @param usrNm
     * @param naBzplc
     * @param authorities
     */
    public UserInfo(String username, String password, String usrId,
            String usrNm,
            String phoneNum,
            String kakaoId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.usrId = usrId;
        this.usrNm = usrNm;
        this.phoneNum = phoneNum;
        this.kakaoId = kakaoId;
    }

    /**
     * 토큰 컨버팅 대응 생성자
     *
     * @param username
     * @param password
     * @param authorities
     * @param usrId
     * @param usrNm
     * @param naBzplc
     * @param naBzplnm
     * @param expirationMilliSecond
     * @param nhBrc
     * @param nhUsrId
     * @param nhAuthYn
     * @param nhLedStsc
     * @param phoneNum
     * @param kakaoId
     */
    public UserInfo(String username, Collection<? extends GrantedAuthority> authorities,
            String usrId, String usrNm, Long expirationMilliSecond, String phoneNum, String kakaoId) {
        super(username, username, true, true, true, true, authorities);
        this.usrId = usrId;
        this.usrNm = usrNm;
        this.expirationMilliSecond = expirationMilliSecond;
        this.phoneNum = phoneNum;
        this.kakaoId = kakaoId;
    }

    /**
     * 인증 실패시 반환 생성자
     */
    public UserInfo() {
        super("anomymous", "", false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("GUEST")));
        this.usrId = "anomymous";
        this.expirationMilliSecond = 0L;
    }

}
