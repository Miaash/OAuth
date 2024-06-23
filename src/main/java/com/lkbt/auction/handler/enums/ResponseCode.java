package com.lkbt.auction.handler.enums;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description : API의 응답 코드를 정의하는 Enum 클래스
 * 4. History
 * > 2023-05-08 : 최초 생성
 *
 * TODO :: 비즈니스 로직에 따라 에러 응답 코드를 추가해야 함...
 */
@AllArgsConstructor
public enum ResponseCode {
  OK(0), // 성공
  EMPTY_PARAMETER(1_100), // 요청 파라미터 관련 실패
  ILLEGAL_PARAMETER(1_110), // 잘못된 파라미터 요청
  DUPLICATED_ID(1_200), // PK 중복
  SQL_SYNTAX_ERROR(2_100), // SQL 구문 오류
  BAD_REQUEST(4_100), // 실패3
  AUTHENTIFICATION_ERROR(4_200), // 인증오류 (보통 로그인 TOKEN이 만료된 경우 발생)
  INTERNAL_SERVER_ERROR(5_100), // 실패4
  USER_NOT_FOUND(6_100), // 관리자 검색 실패
  USER_DUPLICATED(6_110), // 관리자 중복됨
  ATTMNO_EXPIRED(6_120), // 인증번호 유효시간 만료
  ATTMNO_NOTEQUAL(6_130), // 인증번호 불일치
  USER_OUT_ALREADY(6_140), // 관리자 검색 실패
  BROKER_OUT_ALREADY(6_141), // 중도매인 사용불가
  TEMP_LOGIN_BLOCKED(6_150), // 잦은 로그인 시도로 인한 임시블락처리
  DIFF_NABZPLC_LOGIN(6_160), // 타 공판장 사람이 로그인 시도
  FAIL5(4_100), // 실패4
  FAIL6(4_100), // 실패4
  FAIL7(4_100), // 실패4
  FAIL8(4_100), // 실패4
  IF_FAILED_KEYMANAGEMENT(7_110),
  IF_FAILED_NOSUCH(7_120),
  IF_FAILED_SSL(7_130),
  IF_FAILED_RESULT(7_140),
  IF_FAILED_NOINSTANCE(7_210),
  SEND_MESSAGE_NOT_READY(8_100), // 메세지 미준비
  SEND_CONVERT_NOT_READY(8_110), // 메세지 미준비
  IF_FAILED_AUTH_INFO(3_301), // 인증 토큰 에러
  IF_FAILED_AUTH_TIMELIMIT(3_302), // 인증 토큰 유효시간 만기
  ETC(9_999); // 기타

  /**
   * 응답 코드
   */
  @Getter
  private Integer code;

  /**
   * 응답 코드에 해당하는 ResponseCode를 반환한다
   *
   * @param code
   * @return
   */
  @Nullable
  public static ResponseCode identityByCode(int code) {
    for (ResponseCode responseCode : values()) {
      if (responseCode.code == code)
        return responseCode;
    }
    return ResponseCode.ETC;
  }

  public String getReason() {
    final ResponseCode.codeGroup codeGroup = ResponseCode.codeGroup.identityByCode(this.code / 1_000);
    return codeGroup != null ? codeGroup.reason : "API 호출에 실패했습니다. 관리자에게 문의하세요.";
  }

  /**
   * ResponseCode의 응답 코드 그룹을 정의한다
   * 1. - 요청 PARAMETER 관련
   * 2. - SqlException - SQL 구문 오류
   */
  @AllArgsConstructor
  private enum codeGroup {
    OK(0, "success"),
    PARAMETER_EXCEPTION(1, "parameter exception"),
    SQL_EXCEPTION(2, "sql exception"),
    FAIL3(3, "fail type3"),
    FAIL4(4, "bad request"),
    FAIL5(5, "internal server error"),
    FAIL6(6, "fail type4"),
    FAIL7(7, "fail type4"),
    FAIL8(8, "fail type4"),
    ETC(9, "etc");

    private int group;
    private String reason;

    /**
     * 응답 코드에 해당하는 codeGroup을 반환한다
     *
     * @param group
     * @return
     */
    @Nullable
    private static ResponseCode.codeGroup identityByCode(int group) {
      for (ResponseCode.codeGroup codeGroup : values()) {
        if (codeGroup.group == group)
          return codeGroup;
      }
      return ResponseCode.codeGroup.ETC;
    }
  }

}
