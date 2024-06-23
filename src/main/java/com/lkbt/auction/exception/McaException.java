package com.lkbt.auction.exception;

import com.lkbt.auction.exception.base.BaseException;
import com.lkbt.auction.handler.enums.ResponseCode;

import lombok.Getter;

public class McaException extends BaseException {

  @Getter
  protected final Object param;
  protected final String ctsprCd;

  public McaException(ResponseCode responseCode, String ctsprCd, Object param) {
    super(responseCode);
    this.param = param;
    this.ctsprCd = ctsprCd;
  }

  public McaException(ResponseCode responseCode, String message, String ctsprCd, Object param) {
    super(responseCode, message);
    this.param = param;
    this.ctsprCd = ctsprCd;
  }

  public McaException(ResponseCode responseCode, String message, Throwable cause, String ctsprCd, Object param) {
    super(responseCode, message, cause);
    this.param = param;
    this.ctsprCd = ctsprCd;
  }
}
