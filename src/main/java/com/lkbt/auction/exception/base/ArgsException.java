package com.lkbt.auction.exception.base;

import com.lkbt.auction.handler.enums.ResponseCode;

public class ArgsException extends BaseException {

  public ArgsException(ResponseCode responseCode) {
    super(responseCode);
  }

  public ArgsException(ResponseCode responseCode, String message) {
    super(responseCode, message);
  }
  
}
