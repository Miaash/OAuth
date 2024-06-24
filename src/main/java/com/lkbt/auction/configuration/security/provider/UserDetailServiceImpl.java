package com.lkbt.auction.configuration.security.provider;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkbt.auction.configuration.security.user.UserInfo;
import com.lkbt.auction.mapper.LoginMapper;
import com.lkbt.auction.model.dto.LoginDto;
import com.lkbt.auction.util.global.GlobalUtil;

import lombok.RequiredArgsConstructor;

/**
 * [사용자 인증 서비스단]
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final LoginMapper loginMapper;

    // UserDetailsService에 정의되어있는 메서드 override해야함.
    // UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final LoginDto.loadByUsername user = loginMapper.loadUserByUsername(new LoginDto.loadByUsernameParam(username));
        if (GlobalUtil.isNotNull(user)) {
            // 유저 정보가 DB에 있다면 인증성공 생성자로 r기존 유저정보 담아서 유저정보 객체 생성 후, 반환
            return new UserInfo(user.getUsrNm(), user.getUsrId(), user.getUsrId(),
                    user.getUsrNm(), user.getPhoneNum(), user.getKakaoId(),
                    Arrays.asList(new SimpleGrantedAuthority("USER")));
        } else {
            return new UserInfo();
        }
    }

    public void add(LoginDto.loginParam param) {
        loginMapper.addUser(param);
    }

}
