package com.lkbt.auction.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lkbt.auction.exception.AdminLoginFailedException;
import com.lkbt.auction.model.global.GlobalExceptionModel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description : Global Exception 처리를 위한 Advice 클래스
 * 4. History
 * > 2023-05-08 : 최초 생성
 * 5. 참조
 */
// RestContrllerAdvice : @ControllerAdvice + @ResponseBody
// RestController로 선언된 클래스에서 발생하는 예외 처리
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class GlobalFailedExceptionHander {

  // @NonNull
  // private final AdminLoginMapper adminloginMapper;

  /**
   * Mybatis에서 반환하는 Exception에 대한 처리
   * Mybatis에서는 SqlException을 DataAccessException으로 re-throwing
   *
   * @param e
   * @return
   */
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<GlobalExceptionModel> handler(DataAccessException e) {
    log.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.OK).body(new GlobalExceptionModel(e));
  }

  // 인증 실패시에 대한 로그인 실패 회수 증가 처리
  // @ExceptionHandler(AdminLoginFailedException.class)
  // public ResponseEntity<GlobalExceptionModel> handler(AdminLoginFailedException
  // e) {

  // log.error("Handle Admin Login Failed : " + e.getMessage());
  // // 로그인 실패에 대한 카운트 업데이트 처리
  // adminloginMapper.updateLoginFailCnt(new
  // AdminLoginDto.updateLoginFailCntParam(e.getUsrid()));
  // // loginMapper.updateLoginFailCnt(new
  // // AdminLoginDto().updateLoginFailCntParam(e.getUsrid()));

  // return ResponseEntity.status(HttpStatus.OK).body(new
  // GlobalExceptionModel(e));
  // }

  /**
   * 모든 Exception에 대한 처리
   *
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<GlobalExceptionModel> handler(Exception e) {
    log.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.OK).body(new GlobalExceptionModel(e));
  }

}
