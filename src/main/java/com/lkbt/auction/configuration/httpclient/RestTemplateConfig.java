package com.lkbt.auction.configuration.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.lkbt.auction.exception.base.BizException;
import com.lkbt.auction.handler.enums.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException {
        // SSL 유효성 검사를 우회하기 위한 TrustManager 생성
        TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null; // 모든 인증서를 신뢰
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

        // 모든 호스트 이름을 신뢰
        HostnameVerifier allHostsValid = (hostname, session) -> true;

        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .additionalInterceptors(new LoggingInterceptor())
                .errorHandler(new CustomErrorHandler())
                .additionalCustomizers(restTemplate -> {
                    restTemplate.setRequestFactory(
                            new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
                    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                })
                .build();
    }

    @Slf4j
    static class LoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution ex)
                throws IOException {
            final String sessionNumber = makeSessionNumber();
            printRequest(sessionNumber, req, body);
            ClientHttpResponse response = ex.execute(req, body);
            printResponse(sessionNumber, response);
            return response;
        }

        private String makeSessionNumber() {
            return Integer.toString((int) (Math.random() * 1000000));
        }

        private void printRequest(final String sessionNumber, final HttpRequest req, final byte[] body) {
            log.info("[{}] URI: {}, Method: {}, Headers:{}, Body:{} ",
                    sessionNumber, req.getURI(), req.getMethod(), req.getHeaders(),
                    new String(body, StandardCharsets.UTF_8));
        }

        private void printResponse(final String sessionNumber, final ClientHttpResponse res) throws IOException {

            BufferedReader bfr = new BufferedReader(new InputStreamReader(res.getBody(), StandardCharsets.UTF_8));

            String body = bfr.lines().collect(Collectors.joining("\n"));

            bfr.close();

            log.info("[{}] \nStatus: {}, \nHeaders:{}, \nBody:{} ",
                    sessionNumber, res.getStatusCode(), res.getHeaders(), body);
        }
    }

    static class CustomErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // 여기에서 에러 처리 로직을 작성하세요.
            // 예를 들어, 적절한 예외를 던질 수 있습니다.
            if (response.getStatusCode().is4xxClientError()) {
                // 4xx 에러 처리
                throw new BizException(ResponseCode.BAD_REQUEST);
            } else if (response.getStatusCode().is5xxServerError()) {
                // 5xx 에러 처리
                throw new BizException(ResponseCode.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
