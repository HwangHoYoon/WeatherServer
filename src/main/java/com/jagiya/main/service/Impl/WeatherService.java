package com.jagiya.main.service.Impl;

import com.jagiya.common.enums.WeatherCategory;
import com.jagiya.main.entity.Juso;
import com.jagiya.main.entity.Weather;
import com.jagiya.main.repository.JusoCustomRepository;
import com.jagiya.main.repository.WeatherRepository;
import com.jagiya.main.response.ApiResponse;
import com.jagiya.main.response.WeatherItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final JusoCustomRepository jusoCustomRepository;

    private final RestTemplate restTemplate;

    private int retryCnt = 0;

    @Value("${weather.url}")
    private String weatherUrl;

    @Value("${weather.serviceKey}")
    private String serviceKey;

    @Transactional
    public void insertWeather() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<Juso> jusoList = jusoCustomRepository.selectJusoGroupByLocation();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);

        System.out.println("시작 : " + formattedTime);
        log.info("시작 : " + formattedTime);
        for (Juso juso : jusoList) {
            String latX = juso.getLatX();
            String lonY = juso.getLonY();
            
            UriComponents uri = UriComponentsBuilder
                    .fromHttpUrl(weatherUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "1000")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", "20230905")
                    .queryParam("base_time", "0500")
                    .queryParam("nx", latX)
                    .queryParam("ny", lonY)
                    .build(true);

            URI apiUrl = uri.toUri();
            System.out.println("uri : " +  apiUrl);
            log.info("uri : " +  apiUrl);

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<ApiResponse> response = callApi(apiUrl, entity);

            if (response != null) {
                List<WeatherItem> weatherItemList = response.getBody().getResponse().getBody().getItems().getItem();

                List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
                System.out.println(weathers);
                if (weathers.size() > 0) {
                    //weatherRepository.saveAll(weathers);
                    //System.out.println("성공 : " + latX + " : " +lonY );
                } else {
                    System.out.println("Call API 값이 없습니다.");
                    log.info("Call API 값이 없습니다.");
                }
            } else {
                System.out.println("Call API NULL");
                log.info("Call API NULL");
            }
        }
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedTime = now.format(formatter);
        System.out.println("종료 : " + formattedTime);
        log.info("종료 : " + formattedTime);
    }

    @Transactional
    public void insertWeatherWithCode() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Juso juso = jusoCustomRepository.selectJusoWhereCode("1111010600");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);

        log.info("시작 : {}", formattedTime);
        String latX = juso.getLatX();
        String lonY = juso.getLonY();

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(weatherUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1000")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", "20230906")
                .queryParam("base_time", "0500")
                .queryParam("nx", latX)
                .queryParam("ny", lonY)
                .build(true);

        URI apiUrl = uri.toUri();
        log.info("uri : " +  apiUrl);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ApiResponse> response = callApi(apiUrl, entity);

        if (response != null) {
            List<WeatherItem> weatherItemList = response.getBody().getResponse().getBody().getItems().getItem();

            List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
            if (weathers.size() > 0) {
                //weatherRepository.saveAll(weathers);
                //System.out.println("성공 : " + latX + " : " +lonY );
            } else {
                log.info("Call API 값이 없습니다.");
            }
        } else {
            log.info("Call API NULL");
        }
        now = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedTime = now.format(formatter);
        log.info("종료 : {}", formattedTime);
    }

    private ResponseEntity<ApiResponse> callApi(URI apiUrl, HttpEntity<String> entity) {
        try {
            ResponseEntity<ApiResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ApiResponse.class);
            if (response == null) {
                return retryApi(apiUrl, entity);
            } else {
                return response;
            }
        } catch (Exception e) {
            System.out.println("callApi 실패 error : " + e.getMessage());
            log.error("callApi 실패 error : " + e.getMessage());
            return retryApi(apiUrl, entity);
        }
    }

    private ResponseEntity<ApiResponse> retryApi(URI apiUrl, HttpEntity<String> entity) {
        if (retryCnt <=5) {
            System.out.println("retryApi : " + retryCnt);
            log.info("retryApi : " + retryCnt);
            retryCnt++;
            return callApi(apiUrl, entity);
        } else {
            return null;
        }
    }

    private List<Weather> groupDataByDateAndTime(List<WeatherItem> weatherItemList) {
        Map<String, Weather> groupedData = new HashMap<>();

        for (WeatherItem dataItem : weatherItemList) {
            String dateAndTime = dataItem.getFcstDate() + dataItem.getFcstTime();

            Weather weather = groupedData.getOrDefault(dateAndTime, new Weather());
            String category = dataItem.getCategory();

            boolean containsValue = Arrays.asList(WeatherCategory.values()).stream().anyMatch(value -> value.name().equals(category));

            if (containsValue) {
                weather.setBaseDate(dataItem.getBaseDate());
                weather.setBaseTime(dataItem.getBaseTime());
                weather.setFcstDate(dataItem.getFcstDate());
                weather.setFcstTime(dataItem.getFcstTime());
                weather.setLatX(dataItem.getNx());
                weather.setLonY(dataItem.getNy());
                String value = dataItem.getFcstValue();
                setValue(weather, category.toLowerCase(), value);
            }
            groupedData.put(dateAndTime, weather);
        }

        return new ArrayList<>(groupedData.values());
    }

    private void setValue(Object targetObject, String fieldName, String value) {
        try {
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Class<?> targetClass = targetObject.getClass();
            Class<?> valueType = String.class;
            targetClass.getMethod(setterName, valueType).invoke(targetObject, value);
        } catch (Exception e) {
            System.out.println("callApi setValue error : " + e.getMessage());
            log.error("callApi setValue error : " + e.getMessage());
        }
    }
}
