package com.jagiya.location.response;

import lombok.Data;

@Data
public class GeocodingApiData {
    private String coordType;
    private String addressFlag;
    private String matchFlag;
    private String lat;
    private String lon;
    private String city_do;
    private String gu_gun;
    private String eup_myun;
    private String legalDong;
    private String legalDongCode;
    private String adminDong;
    private String adminDongCode;
    private String ri;
    private String bunji;
    private String newMatchFlag;
    private String newLat;
    private String newLon;
    private String newRoadName;
    private String newBuildingIndex;
    private String newBuildingName;
    private String newBuildingCateName;
    private String remainder;

}
