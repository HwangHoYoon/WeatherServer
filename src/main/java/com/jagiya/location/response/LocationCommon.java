package com.jagiya.location.response;

import lombok.Data;

@Data
public class LocationCommon {
    private String errorMessage;
    private String countPerPage;
    private String totalCount;
    private String errorCode;
    private String currentPage;
}
