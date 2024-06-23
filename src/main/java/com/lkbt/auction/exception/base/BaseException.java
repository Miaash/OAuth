package com.lkbt.auction.exception.base;

import com.lkbt.auction.handler.enums.ResponseCode;

import lombok.Getter;

public abstract class BaseException extends RuntimeException {

  @Getter
  protected final ResponseCode responseCode;

  public BaseException(ResponseCode responseCode) {
    super(responseCode.getReason());
    this.responseCode = responseCode;
  }

  public BaseException(ResponseCode responseCode, String message) {
    super(message);
    this.responseCode = responseCode;
  }

  public BaseException(ResponseCode responseCode, String message, Throwable cause) {
    super(message, cause);
    this.responseCode = responseCode;
  }
}
