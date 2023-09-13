package com.jagiya.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagiya.juso.entity.JusoGroup;
import com.jagiya.weather.enums.WeatherCategory;
import com.jagiya.weather.enums.WeatherResponseCode;
import com.jagiya.juso.entity.Juso;
import com.jagiya.weather.entity.Weather;
import com.jagiya.juso.repository.JusoCustomRepository;
import com.jagiya.juso.repository.JusoRepository;
import com.jagiya.weather.repository.WeatherRepository;
import com.jagiya.weather.request.WeatherTestRequest;
import com.jagiya.weather.response.WeatherApiResponse;
import com.jagiya.weather.response.WeatherErrorResponse;
import com.jagiya.weather.response.WeatherItem;
import com.jagiya.weather.response.WeatherTestResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final JusoRepository jusoRepository;

    private final RestTemplate restTemplate;

    private final JusoCustomRepository jusoCustomRepository;

    private int retryCnt = 0;

    @Value("${weather.srtUrl}")
    private String weatherSrtUrl;

    @Value("${weather.ultraSrtUrl}")
    private String weatherUltraSrtUrl;

    @Value("${weather.serviceKey}")
    private String serviceKey;

    @Transactional
    public void insertWeather() throws Exception {
        List<String> cityDos = new ArrayList<>();
        cityDos.add("서울특별시");
        cityDos.add("경기도");
        List<JusoGroup> jusoGroupList = jusoCustomRepository.selectJusoGroupByCityDo(cityDos);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("시작 : {}", LocalDateTime.now().format(formatter));
        for (JusoGroup jusoGroup : jusoGroupList) {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            insertWeather(weatherSrtUrl, serviceKey, baseDate, "0500", jusoGroup, "1");
        }
        log.info("종료 : {}", LocalDateTime.now().format(formatter));
    }

    private void insertWeather(String weatherUrl, String serviceKey, String baseDate, String baseTime, JusoGroup jusoGroup, String refreshType) {
        retryCnt = 0;
        String nx = jusoGroup.getLatX();
        String ny = jusoGroup.getLonY();
        Long jusoGroupId = jusoGroup.getJusoGroupId();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(weatherUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "10000")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .build(true);

        URI apiUrl = uri.toUri();
        log.info("uri : " +  apiUrl);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        WeatherApiResponse response = callApi(apiUrl, entity);

        if (response != null) {
            List<WeatherItem> weatherItemList = response.getResponse().getBody().getItems().getItem();
            log.info("groupDataByDateAndTime");
            List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
            log.info("findByJusoGroupJusoGroupIdAndFcstDateAndFcstTime start");
            if (weathers.size() > 0) {
                for (Weather weather : weathers) {
                    String fcstDate = weather.getFcstDate();
                    String fcstTime = weather.getFcstTime();
                    Weather weatherRes = weatherRepository.findByJusoGroupJusoGroupIdAndFcstDateAndFcstTime(jusoGroupId, fcstDate, fcstTime);
                    if (weatherRes != null) {
                        if (StringUtils.equals(refreshType, "1")) {
                            if (StringUtils.isNotBlank(weather.getPop())) {
                                weatherRes.setPop(weather.getPop());
                            }
                            if (StringUtils.isNotBlank(weather.getPty())) {
                                weatherRes.setPty(weather.getPty());
                            }
                            if (StringUtils.isNotBlank(weather.getTmx())) {
                                weatherRes.setTmx(weather.getTmx());
                            }
                            if (StringUtils.isNotBlank(weather.getTmp())) {
                                weatherRes.setTmp(weather.getTmp());
                            }
                        }

                        if (StringUtils.isNotBlank(weather.getPcp())) {
                            weatherRes.setPcp(weather.getPcp());
                        }

                        if (StringUtils.isNotBlank(weather.getTmn())) {
                            weatherRes.setTmn(weather.getTmn());
                        }

                        if (StringUtils.isNotBlank(weather.getSky())) {
                            weatherRes.setSky(weather.getSky());
                        }

                        if (StringUtils.isNotBlank(weather.getBaseDate())) {
                            weatherRes.setBaseDate(weather.getBaseDate());
                        }

                        if (StringUtils.isNotBlank(weather.getBaseTime())) {
                            weatherRes.setBaseTime(weather.getBaseTime());
                        }
                        weatherRes.setModifyDate(new Date());
                        weatherRepository.save(weatherRes);
                    } else {
                        weather.setJusoGroup(jusoGroup);
                        weatherRepository.save(weather);
                    }
                }
                log.info("findByJusoGroupJusoGroupIdAndFcstDateAndFcstTime end");
            } else {
                log.info("Call API 값이 없습니다.");
            }
        } else {
            log.info("Call API NULL");
        }
    }

    @Transactional
    public void insertWeatherWithCode() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Optional<Juso> jusoOptional = jusoRepository.findByRegionCd("1111010600");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("시작 : {}", LocalDateTime.now().format(formatter));

        if (jusoOptional.isPresent()) {
            Juso juso = jusoOptional.get();

            String latX = juso.getLatX();
            String lonY = juso.getLonY();

            UriComponents uri = UriComponentsBuilder
                    .fromHttpUrl(weatherSrtUrl)
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
                        if (resultCode.equals(WeatherResponseCode.NORMAL_SERVICE.getCode())) {
                            log.info("API 성공");
                            return response;
                        } else {
                            log.error("API 통신 오류 : {}, {}", resultCode, WeatherResponseCode.getMessageByCode(resultCode));
                            if (WeatherResponseCode.getRetryCode(resultCode)) {
                                return retryApi(apiUrl, entity);
                            } else {
                                return null;
                            }
                        }
                    } catch (Exception e) {
                        log.error("JSON 변환 실패 XML 변환 : {}", e.getMessage());
                        WeatherErrorResponse weatherApiErrorResponse = xmlConvertToVo(responseAsString.getBody(), WeatherErrorResponse.class);
                        String returnReasonCode = weatherApiErrorResponse.getCmmMsgHeader().getReturnReasonCode();
                        log.error("callApi 실패 error returnReasonCode : {} {}", returnReasonCode, WeatherResponseCode.getMessageByCode(returnReasonCode));
                        if (WeatherResponseCode.getRetryCode(returnReasonCode)) {
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
            if (StringUtils.equals(fieldName, "rn1")) {
                fieldName = "pcp";
            }
            if (StringUtils.equals(fieldName, "t1h")) {
                fieldName = "tmp";
            }
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Class<?> targetClass = targetObject.getClass();
            Class<?> valueType = String.class;
            targetClass.getMethod(setterName, valueType).invoke(targetObject, value);
        } catch (Exception e) {
            log.error("callApi setValue error : " + e.getMessage());
        }
    }

    public List<WeatherTestResponse> selectLocationForWeather(String regionCd, String fcstDate) {
        Optional<Juso> jusoOptional = jusoRepository.findByRegionCd(regionCd);
        if (jusoOptional.isPresent()) {
            Juso juso = jusoOptional.get();
            Long jusoGroupId = juso.getJusoGroup().getJusoGroupId();

            List<Weather> weatherList = weatherRepository.findByJusoGroupJusoGroupIdAndFcstDateOrderByFcstTimeAsc(jusoGroupId, fcstDate);
            List<WeatherTestResponse> weatherTestResponseList = new ArrayList<>();
            for (Weather weather : weatherList) {
                WeatherTestResponse weatherTestResponse = WeatherTestResponse.builder()
                        .pop(weather.getPop())
                        .pty(weather.getPty())
                        .pcp(weather.getPcp())
                        .tmn(weather.getTmn())
                        .tmp(weather.getTmp())
                        .tmx(weather.getTmx())
                        .sky(weather.getSky())
                        .baseDate(weather.getBaseDate())
                        .baseTime(weather.getBaseTime())
                        .fcstDate(weather.getFcstDate())
                        .fcstTime(weather.getFcstTime())
                        .build();
                weatherTestResponseList.add(weatherTestResponse);
            }
            return weatherTestResponseList;
        } else {
            return null;
        }
    }

    public List<WeatherTestResponse> refreshLocationForWeather(String regionCd, String refreshType) {
        Optional<Juso> jusoOptional = jusoRepository.findByRegionCd(regionCd);
        if (jusoOptional.isPresent()) {
            Juso juso = jusoOptional.get();
            JusoGroup jusoGroup = juso.getJusoGroup();
            LocalDateTime localDateTime = getSrtWeatherDate(refreshType);
            String baseDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm"));

            log.info("baseDate {}, baseTime {}", baseDate, baseTime);

            String weatherUrl;
            if (refreshType.equals("0")) {
                weatherUrl = weatherUltraSrtUrl;
            } else {
                weatherUrl = weatherSrtUrl;
            }

            insertWeather(weatherUrl, serviceKey, baseDate, baseTime, jusoGroup, refreshType);
            return selectLocationForWeather(regionCd, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        } else {
            return null;
        }
    }

    private LocalDateTime getSrtWeatherDate(String refreshType) {
        LocalDateTime localDateTime = LocalDateTime.now();

        int minusMinute;
        List<LocalTime> targetTimes = new ArrayList<>();
        if (refreshType.equals("0")) {
            minusMinute = 15;
            for (int i = 0; i<=23; i++) {
                targetTimes.add(LocalTime.of(i, 45));
            }
        } else {
            minusMinute = 10;
            targetTimes.add(LocalTime.of(2, 10));
            targetTimes.add(LocalTime.of(5, 10));
            targetTimes.add(LocalTime.of(8, 10));
            targetTimes.add(LocalTime.of(11, 10));
            targetTimes.add(LocalTime.of(14, 10));
            targetTimes.add(LocalTime.of(17, 10));
            targetTimes.add(LocalTime.of(20, 10));
            targetTimes.add(LocalTime.of(23, 10));
        }

        LocalTime currentTime = localDateTime.toLocalTime();

        LocalTime closestTime = null;
        for (LocalTime targetTime : targetTimes) {
            if (currentTime.isAfter(targetTime)) {
                closestTime = targetTime;
            }
        }

        if (closestTime == null) {
            localDateTime = localDateTime.minusDays(1);
            closestTime = targetTimes.get(targetTimes.size() - 1);
        }

        closestTime = closestTime.minusMinutes(minusMinute);
        localDateTime = localDateTime.withHour(closestTime.getHour()).withMinute(closestTime.getMinute());

        return localDateTime;
    }

}
