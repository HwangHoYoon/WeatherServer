package com.jagiya.juso.response;

import lombok.Data;

@Data
public class JusoCommon {
    private String errorMessage;
    private String countPerPage;
    private String totalCount;
    private String errorCode;
    private String currentPage;
}
