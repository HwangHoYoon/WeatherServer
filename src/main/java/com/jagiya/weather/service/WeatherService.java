package com.jagiya.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagiya.common.exception.CommonException;
import com.jagiya.location.entity.Location;
import com.jagiya.location.entity.LocationGroup;
import com.jagiya.location.repository.LocationCustomRepository;
import com.jagiya.location.repository.LocationRepository;
import com.jagiya.weather.entity.Weather;
import com.jagiya.weather.entity.WeatherEditor;
import com.jagiya.weather.enums.WeatherCategory;
import com.jagiya.weather.enums.WeatherResponseCode;
import com.jagiya.weather.repository.WeatherRepository;
import com.jagiya.weather.response.WeatherApiResponse;
import com.jagiya.weather.response.WeatherErrorResponse;
import com.jagiya.weather.response.WeatherItem;
import com.jagiya.weather.response.WeatherTestResponse;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    private final LocationRepository locationRepository;

    private final RestTemplate restTemplate;

    private final LocationCustomRepository locationCustomRepository;

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
        List<LocationGroup> locationGroupList = locationCustomRepository.selectLocationGroupByCityDo(cityDos);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("시작 : {}", LocalDateTime.now().format(formatter));
        for (LocationGroup locationGroup : locationGroupList) {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            insertWeather(weatherSrtUrl, serviceKey, baseDate, "0500", locationGroup, "1");
        }
        log.info("종료 : {}", LocalDateTime.now().format(formatter));
    }

    @Transactional
    private void insertWeather(String weatherUrl, String serviceKey, String baseDate, String baseTime, LocationGroup locationGroup, String refreshType) {
        retryCnt = 0;
        String nx = locationGroup.getLatX();
        String ny = locationGroup.getLonY();
        Long jusoGroupId = locationGroup.getLocationGroupId();

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
            log.info("findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime start");
            if (weathers.size() > 0) {
                for (Weather weather : weathers) {
                    String fcstDate = weather.getFcstDate();
                    String fcstTime = weather.getFcstTime();
                    Weather weatherRes = weatherRepository.findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime(jusoGroupId, fcstDate, fcstTime);
                    if (weatherRes != null) {
                        WeatherEditor.WeatherEditorBuilder editorBuilder = weatherRes.toEditor();
                        WeatherEditor weatherEditor;
                        if (StringUtils.equals(refreshType, "1")) {
                            weatherEditor = editorBuilder.pop(weather.getPop())
                                    .pty(weather.getPty())
                                    .tmx(weather.getTmx())
                                    .tmn(weather.getTmn())
                                    .tmp(weather.getTmp())
                                    .pcp(weather.getPcp())
                                    .sky(weather.getSky())
                                    .baseDate(weather.getBaseDate())
                                    .baseTime(weather.getBaseTime())
                                    .build();
                        } else {
                            weatherEditor = editorBuilder.pcp(weather.getPcp())
                                    .tmp(weather.getTmp())
                                    .sky(weather.getSky())
                                    .pty(weather.getPty())
                                    .baseDate(weather.getBaseDate())
                                    .baseTime(weather.getBaseTime())
                                    .build();
                        }
                        weatherRes.edit(weatherEditor);
                    } else {
                        weather.setLocationGroup(locationGroup);
                        weatherRepository.save(weather);
                    }
                }
                log.info("findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime end");
            } else {
                log.info("Call API 값이 없습니다.");
            }
        } else {
            log.info("Call API NULL");
        }
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
        Optional<Location> jusoOptional = locationRepository.findByRegionCd(regionCd);
        if (jusoOptional.isPresent()) {
            Location location = jusoOptional.get();
            Long jusoGroupId = location.getLocationGroup().getLocationGroupId();

            List<Weather> weatherList = weatherRepository.findByLocationGroupLocationGroupIdAndFcstDateOrderByFcstTimeAsc(jusoGroupId, fcstDate);
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

    @Transactional
    public Weather selectLocationAndTimeForWeather(LocationGroup locationGroup, String fcstDate, String fcstTime) {
        Long locationGroupId = locationGroup.getLocationGroupId();
        Weather weather = weatherRepository.findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime(locationGroupId, fcstDate, fcstTime);
        String refreshType = getRefreshType(fcstTime);
        LocalDateTime localDateTime = getSrtWeatherDate(refreshType);
        String baseDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm"));

        // 현재 시간보다 적은 기준날짜는 제외
        int fcstYear = Integer.parseInt(fcstDate.substring(0, 4));
        int fcstMonth = Integer.parseInt(fcstDate.substring(4, 6));
        int fcstDay = Integer.parseInt(fcstDate.substring(6, 8));

        int fcstHour = Integer.parseInt(fcstTime.substring(0, 2));
        int fcstMiute = Integer.parseInt(fcstTime.substring(2));

        LocalDateTime fcstDateTime = LocalDateTime.of(fcstYear, fcstMonth, fcstDay, fcstHour, fcstMiute);
        if (fcstDateTime.isAfter(localDateTime)) {
            String weatherUrl;
            if (refreshType.equals("0")) {
                weatherUrl = weatherUltraSrtUrl;
            } else {
                weatherUrl = weatherSrtUrl;
            }

            // 날씨 데이터가 없다면 API 호출
            if (weather == null) {
                // 대상 시간이 현재 시간보다 6시간 이전인지 이후인지 체크
                // 6시간 이전 초단기조회
                // 6시간 이후 단기조회
                // 현재시간 기준으로 초단기, 단기 기준시간 조회
                // API 등록
                // 이후 데이터 조회
                log.info("baseDate {}, baseTime {}", baseDate, baseTime);
                insertWeather(weatherUrl, serviceKey, baseDate, baseTime, locationGroup, refreshType);
                weather = weatherRepository.findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime(locationGroupId, fcstDate, fcstTime);
            } else { // DB 값이 현재 기준일보다 적을 경우 update

                int baseYear = Integer.parseInt(weather.getBaseDate().substring(0, 4));
                int baseMonth = Integer.parseInt(weather.getBaseDate().substring(4, 6));
                int baseDay = Integer.parseInt(weather.getBaseDate().substring(6, 8));

                int baseHour = Integer.parseInt(weather.getBaseTime().substring(0, 2));
                int baseMiute = Integer.parseInt(weather.getBaseTime().substring(2));

                LocalDateTime weatherDateTime = LocalDateTime.of(baseYear, baseMonth, baseDay, baseHour, baseMiute);
                if (localDateTime.isAfter(weatherDateTime)) {
                    log.info("localDateTime isAfter weatherDateTime localDateTime {}, weatherDateTime {}", localDateTime, weatherDateTime);

                    log.info("baseDate {}, baseTime {}", baseDate, baseTime);
                    insertWeather(weatherUrl, serviceKey, baseDate, baseTime, locationGroup, refreshType);
                    weather = weatherRepository.findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime(locationGroupId, fcstDate, fcstTime);
                }
            }
        }
        return weather;
    }

    private String getRefreshType(String fcstTime) {
        if (fcstTime.length() != 4) {
            throw new CommonException("시간형식이 올바르지 않습니다.", "666");
        }
        int fcstHour = Integer.parseInt(fcstTime.substring(0, 2));

        LocalTime currentLocalTime = LocalTime.now();
        int currentHour = currentLocalTime.getHour();
        int currentMinute = currentLocalTime.getMinute();

        if (currentMinute > 45) {
            currentHour = currentHour + 1;
        }

        int hout = Math.abs(fcstHour - currentHour);

        if (hout < 6) {
            return "0";
        } else {
            return "1";
        }
    }


    public List<WeatherTestResponse> refreshLocationForWeather(String regionCd, String refreshType) {
        Optional<Location> jusoOptional = locationRepository.findByRegionCd(regionCd);
        if (jusoOptional.isPresent()) {
            Location location = jusoOptional.get();
            LocationGroup locationGroup = location.getLocationGroup();
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

            insertWeather(weatherUrl, serviceKey, baseDate, baseTime, locationGroup, refreshType);
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
        localDateTime = localDateTime.withHour(closestTime.getHour()).withMinute(closestTime.getMinute()).withSecond(0).withNano(0);

        return localDateTime;
    }

    public void refreshWeather(LocationGroup locationGroup, String fcstDate, String fcstTime) {
        Long locationGroupId = locationGroup.getLocationGroupId();
        Weather weather = weatherRepository.findByLocationGroupLocationGroupIdAndFcstDateAndFcstTime(locationGroupId, fcstDate, fcstTime);
        String refreshType = getRefreshType(fcstTime);
        LocalDateTime localDateTime = getSrtWeatherDate(refreshType);
        String baseDate = localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm"));

        // 현재 시간보다 적은 기준날짜는 제외
        int fcstYear = Integer.parseInt(fcstDate.substring(0, 4));
        int fcstMonth = Integer.parseInt(fcstDate.substring(4, 6));
        int fcstDay = Integer.parseInt(fcstDate.substring(6, 8));

        int fcstHour = Integer.parseInt(fcstTime.substring(0, 2));
        int fcstMiute = Integer.parseInt(fcstTime.substring(2));

        LocalDateTime fcstDateTime = LocalDateTime.of(fcstYear, fcstMonth, fcstDay, fcstHour, fcstMiute);
        if (fcstDateTime.isAfter(localDateTime)) {
            String weatherUrl;
            if (refreshType.equals("0")) {
                weatherUrl = weatherUltraSrtUrl;
            } else {
                weatherUrl = weatherSrtUrl;
            }

            // 날씨 데이터가 없다면 API 호출
            if (weather == null) {
                // 대상 시간이 현재 시간보다 6시간 이전인지 이후인지 체크
                // 6시간 이전 초단기조회
                // 6시간 이후 단기조회
                // 현재시간 기준으로 초단기, 단기 기준시간 조회
                // API 등록
                // 이후 데이터 조회
                log.info("baseDate {}, baseTime {}", baseDate, baseTime);
                insertWeather(weatherUrl, serviceKey, baseDate, baseTime, locationGroup, refreshType);
            } else { // DB 값이 현재 기준일보다 적을 경우 update

                int baseYear = Integer.parseInt(weather.getBaseDate().substring(0, 4));
                int baseMonth = Integer.parseInt(weather.getBaseDate().substring(4, 6));
                int baseDay = Integer.parseInt(weather.getBaseDate().substring(6, 8));

                int baseHour = Integer.parseInt(weather.getBaseTime().substring(0, 2));
                int baseMiute = Integer.parseInt(weather.getBaseTime().substring(2));

                LocalDateTime weatherDateTime = LocalDateTime.of(baseYear, baseMonth, baseDay, baseHour, baseMiute);
                if (localDateTime.isAfter(weatherDateTime)) {
                    log.info("localDateTime isAfter weatherDateTime localDateTime {}, weatherDateTime {}", localDateTime, weatherDateTime);

                    log.info("baseDate {}, baseTime {}", baseDate, baseTime);
                    insertWeather(weatherUrl, serviceKey, baseDate, baseTime, locationGroup, refreshType);
                }
            }
        }
    }
}
