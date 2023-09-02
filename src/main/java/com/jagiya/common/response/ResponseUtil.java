package com.jagiya.common.response;

public class ResponseUtil {
    public static <T> CommonResponse<T> response(int code, T response, String message) {
        return new CommonResponse<>(code, response, message);
    }
}
