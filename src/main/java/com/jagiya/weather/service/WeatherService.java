package com.jagiya.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagiya.common.enums.WeatherCategory;
import com.jagiya.common.enums.WeatherCode;
import com.jagiya.weather.entity.Juso;
import com.jagiya.weather.entity.Weather;
import com.jagiya.weather.repository.JusoCustomRepository;
import com.jagiya.weather.repository.JusoRepository;
import com.jagiya.weather.repository.WeatherRepository;
import com.jagiya.weather.request.WeatherTestRequest;
import com.jagiya.weather.response.WeatherApiResponse;
import com.jagiya.weather.response.WeatherErrorResponse;
import com.jagiya.weather.response.WeatherItem;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final JusoCustomRepository jusoCustomRepository;

    private final JusoRepository jusoRepository;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("시작 : {}", LocalDateTime.now().format(formatter));
        for (Juso juso : jusoList) {
            String latX = juso.getLatX();
            String lonY = juso.getLonY();
            
            UriComponents uri = UriComponentsBuilder
                    .fromHttpUrl(weatherUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "1000")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", "20230908")
                    .queryParam("base_time", "0500")
                    .queryParam("nx", latX)
                    .queryParam("ny", lonY)
                    .build(true);

            URI apiUrl = uri.toUri();
            log.info("uri : " +  apiUrl);

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            WeatherApiResponse response = callApi(apiUrl, entity);

            if (response != null) {
                List<WeatherItem> weatherItemList = response.getResponse().getBody().getItems().getItem();

                List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
                System.out.println(weathers);
                if (weathers.size() > 0) {
                    //weatherRepository.saveAll(weathers);
                    //System.out.println("성공 : " + latX + " : " +lonY );
                } else {
                    log.info("Call API 값이 없습니다.");
                }
            } else {
                log.info("Call API NULL");
            }
        }
        log.info("종료 : {}", LocalDateTime.now().format(formatter));
    }

    @Transactional
    public void insertWeatherWithCode() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Optional<Juso> jusoOptional = jusoRepository.findByCode("1111010600");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("시작 : {}", LocalDateTime.now().format(formatter));

        if (jusoOptional.isPresent()) {
            Juso juso = jusoOptional.get();

            String latX = juso.getLatX();
            String lonY = juso.getLonY();

            UriComponents uri = UriComponentsBuilder
                    .fromHttpUrl(weatherUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "1000")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", "20230907")
                    .queryParam("base_time", "0500")
                    .queryParam("nx", latX)
                    .queryParam("ny", lonY)
                    .build(true);

            URI apiUrl = uri.toUri();
            log.info("uri : " +  apiUrl);

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            WeatherApiResponse response = callApi(apiUrl, entity);

            if (response != null) {
                List<WeatherItem> weatherItemList = response.getResponse().getBody().getItems().getItem();
                List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
                if (weathers.size() > 0) {
                    log.info("성공");
                    //weatherRepository.saveAll(weathers);
                    //System.out.println("성공 : " + latX + " : " +lonY );
                } else {
                    log.error("Call API 값이 없습니다.");
                }
            } else {
                log.error("Call API NULL");
            }
        }

        log.info("종료 : {}", LocalDateTime.now().format(formatter));
    }

    private WeatherApiResponse callApi(URI apiUrl, HttpEntity<String> entity) {
        try {
            ResponseEntity<String> responseAsString = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (responseAsString == null) {
                log.info("API 결과 NULL");
                return retryApi(apiUrl, entity);
            } else {
                if (responseAsString.getStatusCode() == HttpStatus.OK) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        WeatherApiResponse response = objectMapper.readValue(responseAsString.getBody(), WeatherApiResponse.class);
                        String resultCode = response.getResponse().getHeader().getResultCode();
                        if (resultCode.equals(WeatherCode.NORMAL_SERVICE.getCode())) {
                            log.info("API 성공");
                            return response;
                        } else {
                            log.error("API 통신 오류 : {}, {}", resultCode, WeatherCode.getMessageByCode(resultCode));
                            if (WeatherCode.getRetryCode(resultCode)) {
                                return retryApi(apiUrl, entity);
                            } else {
                                return null;
                            }
                        }
                    } catch (Exception e) {
                        log.error("JSON 변환 실패 XML 변환 : {}", e.getMessage());
                        WeatherErrorResponse weatherApiErrorResponse = xmlConvertToVo(responseAsString.getBody(), WeatherErrorResponse.class);
                        String returnReasonCode = weatherApiErrorResponse.getCmmMsgHeader().getReturnReasonCode();
                        log.error("callApi 실패 error returnReasonCode : {} {}", returnReasonCode, WeatherCode.getMessageByCode(returnReasonCode));
                        if (WeatherCode.getRetryCode(returnReasonCode)) {
                            return retryApi(apiUrl, entity);
                        } else {
                            return null;
                        }
                    }
                } else {
                    log.error("API 통신 결과 실패 HttpStatus : {} ", responseAsString.getStatusCode());
                    return retryApi(apiUrl, entity);
                }
            }
        } catch (Exception e) {
            log.error("callApi 실패 error : {}", e.getMessage());
            return retryApi(apiUrl, entity);
        }
    }

    private <T> T xmlConvertToVo(String xml, Class<T> voClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(voClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StringReader reader = new StringReader(xml);
        return (T)unmarshaller.unmarshal(reader);
    }

    private WeatherApiResponse retryApi(URI apiUrl, HttpEntity<String> entity) {
        if (retryCnt <=3) {
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
            log.error("callApi setValue error : " + e.getMessage());
        }
    }

    public List<Weather> selectLocationForWeather(WeatherTestRequest weatherRequest) {
        String code = weatherRequest.getCode();

        Optional<Juso> jusoOptional = jusoRepository.findByCode(code);
        if (jusoOptional.isPresent()) {
            Juso juso = jusoOptional.get();
            String latX = juso.getLatX();
            String lonY = juso.getLonY();
            String fcstDate = weatherRequest.getFcstDate();

            List<Weather> weatherList = weatherRepository.findByLatXAndLonYAndFcstDateOrderByFcstTimeAsc(latX, lonY, fcstDate);
            return weatherList;
        } else {
            return null;
        }
    }

}
