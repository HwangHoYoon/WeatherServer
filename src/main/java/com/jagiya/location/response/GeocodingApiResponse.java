package com.jagiya.location.response;

import lombok.Data;

@Data
public class GeocodingApiResponse {

    private GeocodingApiData coordinateInfo;

    private GeocodingApiError error;
}
