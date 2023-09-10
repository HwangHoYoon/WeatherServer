package com.jagiya.weather.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherBody {
    private String dataType;
    private WeatherData items;

    private String pageNo;
    private String numOfRows;
    private String totalCount;
}
