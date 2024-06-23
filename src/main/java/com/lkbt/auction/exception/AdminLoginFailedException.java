package com.lkbt.auction.exception;

import com.lkbt.auction.handler.enums.ResponseCode;

import com.lkbt.auction.exception.base.BaseException;

import lombok.Getter;

/**
 * 2023.11.07
 * 로그인 실패시 카운트 증가를 위한 전용 Exception
 */
public class AdminLoginFailedException extends BaseException {

    @Getter
    private final String usrid;

    public AdminLoginFailedException(ResponseCode responseCode, String usrid) {
        super(responseCode);
        this.usrid = usrid;
    }

    public AdminLoginFailedException(ResponseCode responseCode, String usrid, String message) {
        super(responseCode, message);
        this.usrid = usrid;
    }

    public AdminLoginFailedException(ResponseCode responseCode, String usrid, String message, Throwable cause) {
        super(responseCode, message, cause);
        this.usrid = usrid;
    }

}
