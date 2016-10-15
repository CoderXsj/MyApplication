package com.cydai.cncx.network;

/**
 * Created by 薛世君
 * Date : 2016/10/10
 * Email : 497881309@qq.com
 */

public class DataResponse<T> {
    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
