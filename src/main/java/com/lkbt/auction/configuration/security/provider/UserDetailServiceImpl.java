package com.lkbt.auction.configuration.security.provider;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * [로그인 시 사용자 찾는 용도의 서비스]
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    // UserDetailsService에 정의되어있는 메서드 override해야함.
    // UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 찾는 로직 추가
        return null;

    }

}
