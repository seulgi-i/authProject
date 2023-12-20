package com.oauth.clientserver.response;

// 공통속성 + 엔티티 T의 단일 데이터

public class SingleResponse<T> extends CommonResponse {
    public T data;

}
