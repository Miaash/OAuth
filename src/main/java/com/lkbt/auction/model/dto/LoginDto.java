package com.lkbt.auction.model.dto;

import com.lkbt.auction.configuration.security.user.UserInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인관련 DTO
 */

public class LoginDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class loadByUsernameParam {
        private String username;

        public loadByUsernameParam(String username) {
            this.username = username;
        }

    }

    /**
     * 로그인 인증용도의 DTO
     * 사용에 주의할 것. 민감한 정보 담겨있음.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class loadByUsername {

        @Schema(description = "최종등록자개인번호")
        private String firstAddusrId;

        @Schema(description = "최종등록일시")
        private String firstAddDtm;

        @Schema(description = "최종변경일시")
        private String lastUpdateDtm;

        @Schema(description = "최종변경자개인번호")
        private String lastUpdateusrId;

        @Schema(description = "삭제여부")
        private String delYn;

        @Schema(description = "휴대전화번호")
        private String phoneNum;

        @Schema(description = "사용자ID")
        private String usrId;

        @Schema(description = "성명")
        private String usrNm;

        @Schema(description = "카카오 ID")
        private String kakaoId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class loginParam {

        @Schema(description = "사용자 ID")
        private String usrId;

        @Schema(description = "사용자이름")
        private String usrNm;

        @Schema(description = "카카오 ID")
        private String kakaoId;

        @Schema(description = "휴대전화번호")
        private String phoneNum;

        public loginParam(String usrId, String usrNm, String kakaoId, String phoneNum) {
            this.usrId = usrId;
            this.usrNm = usrNm;
            this.kakaoId = kakaoId;
            this.phoneNum = phoneNum;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class login {

        @Schema(description = "엑세스토큰 - 카카오 리소스 서버 접근용")
        private String accessToken;
        @Schema(description = "토큰정보")
        private LoginDto.tokenInfo tokenInfo;

        public login(String accessToken, UserInfo userInfo, LoginDto.tokenInfo tokenInfo) {
            this.accessToken = accessToken;
            this.tokenInfo = new LoginDto.tokenInfo();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class tokenInfo {
        @Schema(description = "사용자 ID")
        private String usrId;
        @Schema(description = "사용자명")
        private String usrNm;
        @Schema(description = "전화번호")
        private String phoneNum;
        @Schema(description = "카카오 ID")
        private String kakaoId;
        @Schema(description = "만료시각")
        private Long expirationMilliSecond;

        public tokenInfo(UserInfo userInfo) {
            this.usrId = userInfo.getUsrId();
            this.usrNm = userInfo.getUsrNm();
            this.phoneNum = userInfo.getPhoneNum();
            this.kakaoId = userInfo.getKakaoId();
            this.expirationMilliSecond = userInfo.getExpirationMilliSecond();
        }

        public tokenInfo(String usrId, String usrNm, String phoneNum, String kakaoId, Long expirationMilliSecond) {

        }
    }
}
