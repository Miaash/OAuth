package com.lkbt.auction.handler.enums;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import com.github.pagehelper.PageInfo;
import com.lkbt.auction.model.global.GlobalResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. Created by   : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description  : Controller return Object 유형에 따라 응답을 다르게 처리하기 위한 정책을 위한 Enum 클래스
 * 4. History
 *  > 2023-05-08 : 최초 생성
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum ResponsePolicyEnum {
  IS_VOID(target -> target == null) {
    @Override
    public GlobalResponseModel.base returnModel(Object target) {
      log.info("IS_VOID");
      return new GlobalResponseModel.object<>(target);
    }
  },
  IS_RESPONSE_BODY_MODEL(target -> target instanceof GlobalResponseModel.base) {
    @Override
    public GlobalResponseModel.base returnModel(Object target) {
      log.info("IS_RESPONSE_BODY_MODEL");
      return (GlobalResponseModel.base) target;
    }
  },
  IS_COLLECTION(target -> target instanceof Collection<?>) {
    @Override
    public GlobalResponseModel.base returnModel(Object target) {
      log.info("IS_COLLECTION");
      return new GlobalResponseModel.collection<>((Collection<?>) target);
    }
  },
  IS_PAGEMODEL(target -> target instanceof PageInfo<?>) {
    @Override
    public GlobalResponseModel.base returnModel(Object target) {
      log.info("IS_PAGEMODEL");
      return new GlobalResponseModel.pageInfo<>((PageInfo<?>) target);
    }
  },
  IS_OBJECT(target -> true) {
    @Override
    public GlobalResponseModel.base returnModel(Object target) {
      log.info("IS_OBJECT");
      return new GlobalResponseModel.object<>(target);
    }
  };

  private Predicate<Object> predicate;

  /**
   * api 에서 반환된 데이터를 받아, GlobalResponseModel 로 반환.
   * @param target
   * @return
   */
  public abstract GlobalResponseModel.base returnModel(Object target);

  /**
   * 객체 타입 체크 후 반환
   * @param target
   * @return
   */
  public boolean check(Object target){
    return this.predicate.test(target);
  }

  /**
   * enum 리스트 반환
   * @return
   */
  public static List<ResponsePolicyEnum> getFilter(){
    return Arrays.asList(ResponsePolicyEnum.values());
  }
}
