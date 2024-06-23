package com.lkbt.auction.model.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 1. Created by   : ycsong
 * 2. Created Date : 2023-05-09
 * 3. Description  : 페이지 처리를 위한 모델 클래스
 *  > 리스트 조회에 관련된 모든 class에서는 이 PageModel을 상속받아 사용해야 한다.
 * 4. History
 *  > 2023-05-09 : 최초 생성
 */
@Getter
@Setter
public class PageModel {

  @Schema(description = "페이지 번호 (기본값 : 1)", defaultValue = "1")
  protected int pageNum = 1;

  @Schema(description = "페이지 사이즈 (기본값 : 20)", defaultValue = "20")
  protected int pageSize = 20;

}
