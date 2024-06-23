package com.lkbt.auction.model.dto.external.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2024-06-18
 * 3. Description : 카카오 로그인 정보 class
 * 4. History
 * > 2023-11-17 : 최초 생성
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "kko.login")
public class KakaoLoginProperties {

    private Redirect Redirect;
    private Client client;

    @Getter
    @Setter
    @ToString
    private static class Redirect {
        private String url;
    }

    @Getter
    @Setter
    @ToString
    private static class Client {

        private Id id;
        private String scope;
        private String state;

        @Getter
        @Setter
        @ToString
        private static class Id {
            private String javascript;
        }
    }
}
