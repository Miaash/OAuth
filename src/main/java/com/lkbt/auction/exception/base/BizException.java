package com.lkbt.auction.exception.base;

import com.lkbt.auction.handler.enums.ResponseCode;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description : 비즈니스 예외 처리를 위한 클래스
 * 4. History
 * > 2023-05-08 : 최초 생성
 */
public class BizException extends BaseException {

  public BizException(ResponseCode responseCode) {
    super(responseCode);
  }

  public BizException(ResponseCode responseCode, String message) {
    super(responseCode, message);
  }

  public BizException(ResponseCode responseCode, String message, Throwable cause) {
    super(responseCode, message, cause);
  }

}
