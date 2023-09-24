package com.jagiya.location.response;

import lombok.Data;

import java.util.List;

@Data
public class LocationResult {
    private LocationCommon common;
    private List<LocationData> location;
}
