package com.lkbt.auction.model.global;

import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lkbt.auction.exception.base.BaseException;
import com.lkbt.auction.handler.enums.ResponseCode;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2023-05-09
 * 3. Description : API 에러 응답에 대한 모델 클래스
 * > GlobalFailedExceptionHander 에서 모든 Exception에 대해 규격화된 응답을 하기 위해 사용
 * 4. History
 * > 2023-05-09 : 최초 생성
 */
public class GlobalExceptionModel {

  @JsonProperty
  private String message;
  @JsonProperty
  private String statusCode;
  @JsonProperty
  private LocalDateTime timestamp;

  /**
   * Mybatis에서 Exception이 발생한 경우 반환되는 모델
   * 
   * @param DataAccessException e
   */
  public GlobalExceptionModel(DataAccessException e) {
    this.message = ResponseCode.SQL_SYNTAX_ERROR.getReason();
    this.timestamp = LocalDateTime.now();
    this.statusCode = ResponseCode.SQL_SYNTAX_ERROR.getCode().toString();
  }

  /**
   * 기본 Exceptiom 반환 모델
   * 
   * @param Exception e
   */
  public GlobalExceptionModel(Exception e) {
    this.message = e.getMessage();
    this.timestamp = LocalDateTime.now();
    this.statusCode = e instanceof BaseException ? ((BaseException) e).getResponseCode().getCode().toString()
        : ResponseCode.ETC.getCode().toString();
  }

}
