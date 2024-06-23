package com.lkbt.auction.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.lkbt.auction.handler.enums.ResponsePolicyEnum;

/**
 * 1. Created by   : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description  : Exception을 제외한 모든 응답에 대한 처리를 위한 Advice 클래스
 * 4. History
 *  > 2023-05-08 : 최초 생성
 * 5. 참조
 *  > http://www.javabyexamples.com/quick-guide-to-responsebodyadvice-in-spring-mvc
 */
@RestControllerAdvice(basePackages = {"com.lkbt.auction.controller"})
public class GlobalSuccessResponseHandler implements ResponseBodyAdvice<Object> {

  /**
   * supports : 해당 클래스가 적용되는지 여부를 판단한다.
   * @param returnType
   * @param converterType
   * @return true : 적용, false : 미적용
   * [TODO :: 현재는 모든 응답에 대해 적용되도록 설정되어 있으나, 필요에 따라 적용 범위를 제한할 수 있다.]
   */
  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  /**
   * beforeBodyWrite : 응답 데이터를 변환한다.
   * @param body
   * @param returnType
   * @param selectedContentType
   * @param selectedConverterType
   * @param request
   * @param response
   * @return
   */
  @Override
  @Nullable
  public Object beforeBodyWrite(
    @Nullable Object body,
    MethodParameter returnType,
    MediaType selectedContentType,
    Class<? extends HttpMessageConverter<?>> selectedConverterType,
    ServerHttpRequest request,
    ServerHttpResponse response
  ) {
      return ResponsePolicyEnum.getFilter()                                     // List<ResponsePolicyEnum>
                                .stream()
                                .filter(policy -> policy.check(body))           // ResponsePolicyEnum에 정의된 Predicate를 통해 체크
                                .findFirst()                                    // filter 조건에 해당하는 첫번째 요소를 반환 Optional<ResponsePolicyEnum>
                                .orElseGet(() -> ResponsePolicyEnum.IS_OBJECT)  // filter 조건에 해당하는 요소가 없는 경우, IS_OBJECT를 반환
                                .returnModel(body);
  }

}
