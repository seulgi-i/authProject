package com.oauth.clientserver.response;

import java.util.List;

// 공통속성 + 엔티티 T의 단일 데이터
public class ListResponse<T> extends CommonResponse {
    public List<T> dataList;
}