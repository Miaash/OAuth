package com.lkbt.auction.model.global;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lkbt.auction.handler.enums.ResponseCode;

import lombok.Getter;
import lombok.Setter;

/**
 * 1. Created by   : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description  : Global Response Model 클래스 > API의 모든 응답은 이 클래스를 통해 응답한다.
 * 4. History
 *   > 2023-05-08 : 최초 생성
 * 5. 참조
 */
public class GlobalResponseModel {

  @Getter
  @Setter
  public abstract static class base {

    protected String statusCode = ResponseCode.OK.getCode().toString();
    protected Long total;           // 전체 데이터 수
    protected Long startRow;        // 조회 시작 Row
    protected Long endRow;          // 조회 종료 Row

    public base(Collection<?> data) {
      this.total = Long.valueOf(data.size());
    }

    public base(Object data) {
      this.total = data == null ? 0 : 1L;
    }

    public base(PageInfo<?> data) {
      this.total = data.getTotal();
      this.startRow = data.getStartRow();
      this.endRow = data.getEndRow();
    }
  }

  /**
   * 응답 데이터가 단일 객체인 경우
   */
  @Getter
  public static class object<T> extends GlobalResponseModel.base {
    private T results;

    public object(T data) {
      super(data);
      this.results = data;
    }
  }

  /**
   * 응답 데이터가 컬렉션인 경우
   */
  @Getter
  public static class collection<T> extends GlobalResponseModel.base {
    private List<T> results = new ArrayList<>();

    public collection(Collection<T> data) {
      super(data);
      this.results = new ArrayList<>(data);
    }
  }

  /**
   * 응답 개체가 PageHelper를 사용 후 반환된 형태인 경우 (PageInfo)
   * PageInfo의 getList() 메소드를 통해 컬렉션을 반환한다.
   */
  @Getter
  public static class pageInfo<T> extends GlobalResponseModel.base {
    private List<T> results = new ArrayList<>();

    public pageInfo(PageInfo<T> data) {
      super(data);
      this.results = new ArrayList<>(data.getList());
    }
  }

}
