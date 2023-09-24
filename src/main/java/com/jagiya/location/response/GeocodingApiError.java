package com.jagiya.location.response;

import lombok.Data;

@Data
public class GeocodingApiError {

    private String id;
    private String category;
    private String code;
    private String message;

}
