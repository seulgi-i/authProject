package com.oauth.clientserver.service;


import com.oauth.clientserver.response.CommonResponse;
import com.oauth.clientserver.response.ListResponse;
import com.oauth.clientserver.response.PostResponse;
import com.oauth.clientserver.response.SingleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        setSuccessResponse(singleResponse);
        return singleResponse;
    }

    public <T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse listResponse = new ListResponse();
        listResponse.dataList = dataList;
        setSuccessResponse(listResponse);
        return listResponse;
    }

    public PostResponse postResponse() {
        PostResponse postResponse = new PostResponse();
        postResponse.body = true;
        setSuccessResponse(postResponse);
        return postResponse;
    }

    public PostResponse failResponse() {
        PostResponse postResponse = new PostResponse();
        postResponse.body = false;
        setFailResponse(postResponse);
        return postResponse;
    }

    public void setSuccessResponse(CommonResponse response) {
        response.code = 200;
        response.success = true;
        response.message = "정상적으로 처리되었습니다.";
    }

    public void setFailResponse(CommonResponse response) {
        response.code = 400;
        response.message = "엥";
        response.success = false;
    }
}
