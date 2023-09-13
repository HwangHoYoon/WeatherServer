package com.jagiya.juso.response;

import lombok.Data;

@Data
public class GeocodingApiResponse {

    private GeocodingApiData coordinateInfo;

    private GeocodingApiError error;
}
