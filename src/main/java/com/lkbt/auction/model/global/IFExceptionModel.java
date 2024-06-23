package com.lkbt.auction.model.global;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2023-05-09
 * 3. Description : API 에러 응답에 대한 모델 클래스
 * > GlobalFailedExceptionHander 에서 모든 Exception에 대해 규격화된 응답을 하기 위해 사용
 * 4. History
 * > 2023-05-09 : 최초 생성
 */
public class IFExceptionModel {

  @JsonProperty
  private String status;
  @JsonProperty
  private String message;
  @JsonProperty
  private String statusCode;

  /**
   * 인증 에러 처리용 customize 생성자
   */
  public IFExceptionModel(String status, String message, String statusCode) {
    this.status = status;
    this.message = message;
    this.statusCode = statusCode;
  }
}
