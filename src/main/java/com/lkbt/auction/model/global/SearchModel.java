package com.lkbt.auction.model.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchModel implements GlobalUserModel {
    @Schema(description = "사용자 아이디")
    protected String usrid;

    @Schema(description = "최초등록자개인번호")
    protected String fsrgmnEno;

    @Schema(description = "최종변경자개인번호")
    protected String lsCmeno;
}
