package com.lkbt.auction.model.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2024-06-18
 * 3. Description : DB insert, update시 사용자(usrid), 작성자(fsrgmnEno), 수정자(lsCmeno)
 * 처리를 위한 모델
 * 4. History
 * > 2024-06-18 : 최초 생성
 * > 2024-06-18 : interface -> class로 변경, GlobalUserModel 인터페이스 상속
 */
@Getter
@Setter
public class RegistModel implements GlobalUserModel {
    @Schema(description = "사용자 아이디")
    protected String usrid;

    @Schema(description = "최초등록자개인번호")
    protected String fsrgmnEno;

    @Schema(description = "최종변경자개인번호")
    protected String lsCmeno;
}
