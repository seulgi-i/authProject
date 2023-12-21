package com.oauth.clientserver.response;

import java.util.Map;

public class MapResponse<T> extends CommonResponse {

    public Map<T, T> body;
}