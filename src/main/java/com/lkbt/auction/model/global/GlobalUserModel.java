package com.lkbt.auction.model.global;

/**
 * 1. Created by : ycsong
 * 2. Created Date : 2024-06-18
 * 3. Description :
 * > GlobalUserModel - RegistModel, SearchModel에서 상속받아 사용자 정보를 관리하는 인터페이스
 * 4. History
 * > 2024-06-18 : 최초 생성
 */
public interface GlobalUserModel {
    void setUsrid(String usrid);

    void setFsrgmnEno(String fsrgmnEno);

    void setLsCmeno(String lsCmeno);

    String getUsrid();

    String getFsrgmnEno();

    String getLsCmeno();

}
