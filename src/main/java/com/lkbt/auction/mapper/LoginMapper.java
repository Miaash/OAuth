package com.lkbt.auction.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lkbt.auction.model.dto.LoginDto;

@Mapper
public interface LoginMapper {

    /**
     * 사용자 이름 조회
     * 
     * @param param
     * @return
     */
    LoginDto.loadByUsername loadUserByUsername(LoginDto.loadByUsernameParam param);

    /**
     * 새로운 사용자 등록.
     * 
     * @param param
     */
    void addUser(LoginDto.loginParam param);

}
