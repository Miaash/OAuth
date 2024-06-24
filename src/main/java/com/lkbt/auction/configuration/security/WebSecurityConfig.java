package com.lkbt.auction.configuration.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

/**
 * [Spring Security관련 설정]
 * 
 * 
 */
@RequiredArgsConstructor
@EnableWebSecurity // 상속없이 사용가능.
@Configurable
public class WebSecurityConfig {

    /**
     * 요청을 SecurityFilterChain이 가로채서 설정된 인증, 인가 부분을 체크하고 난 후 다음 흐름으로 이어짐.
     * 
     * @param http
     * @return
     * @throws Exception
     * @description
     *              설정할 내용
     *              1. CORS 설정 .cors(withDefaults())
     *              2. 세션 관리 정책 설정 .sessionManagement(management ->
     *              management.sessionCreationPolicy(SessionCreationPolicy.NEVER))
     *              3. 인증 체크를 하지 않을 URL 설정
     *              4. 예외 처리 핸들러 설정
     *              5. 필터 체인 생성
     *              6. 캐시 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CORS 설정
        // http.cors(withDefaults())
        // 2. 세션 관리 정책 설정
        // .sessionManagement(management ->
        // management.sessionCreationPolicy(SessionCreationPolicy.NEVER))
        // 3. 인증 체크 하지 않을 URL 설정.
        // .authorizeHttpRequests(
        // requests ->
        // requests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        // .antMatchers(noAuthCheckUrl).permitAll()
        // .anyRequest().permitAll())
        // 4. 예외 처리 핸들러 설정
        // .exceptionHandling(handler -> handler.authenticationEntryPoint(null))
        // 5. 필터체인 생성
        // .addFilterBefore(adminRequestFilter,
        // UsernamePasswordAuthenticationFilter.class);
        // 6. 캐시 설정
        // http.headers(headers -> headers.cacheControl(cache -> cache.disable()));

        // 요청되는 모든 URL을 허용
        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll());
        return http.build();
    }
}
