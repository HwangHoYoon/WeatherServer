package com.jagiya.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonResponse<T> {
    private final int code;
    private final T data;
    private final String message;

}
