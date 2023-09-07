package com.jagiya.main.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WeatherData {
    private List<WeatherItem> item;
}
