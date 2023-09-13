package com.jagiya.juso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagiya.common.exception.CommonException;
import com.jagiya.juso.enums.JusoResponseCode;
import com.jagiya.juso.request.GpsTransfer;
import com.jagiya.juso.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class JusoService {

    @Value("${juso.url}")
    private String jusoUrl;

    @Value("${juso.confmKey}")
    private String confmKey;

    @Value("${geocoding.url}")
    private String geocodingUrl;

    @Value("${geocoding.appKey}")
    private String geocodingAppKey;


    private int retryCnt = 0;

    private final RestTemplate restTemplate;

    public List<JusoTestResponse> selectLocation(String keyword) throws Exception {
        retryCnt = 0;
        if (StringUtils.isBlank(keyword)) {
            throw new CommonException("검색어를 입력해주세요 {}", "887");
        }

        boolean isCheckSearchedWord = checkSearchedWord(keyword);
        if (!isCheckSearchedWord) {
            log.error("특수문자 또는 사용할수 없는 특정 문자가 들어갔습니다. {}", keyword);
            throw new CommonException("특수문자 또는 사용할수 없는 특정 문자가 들어갔습니다.", "888");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String decodedConfmKey = URLEncoder.encode(confmKey, "UTF-8");
        String decodedKeyword = URLEncoder.encode(keyword, "UTF-8");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(jusoUrl)
                .queryParam("confmKey", decodedConfmKey)
                .queryParam("currentPage", "1")
                .queryParam("countPerPage", "500")
                .queryParam("keyword", decodedKeyword)
                .queryParam("resultType", "json")
                .build(true);
        URI apiUrl = uri.toUri();
        log.info("uri : " +  apiUrl);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        JusoApiResponse response = callApi(apiUrl, entity);

        if (response != null) {
            List<JusoData> jusoDataList = response.getResults().getJuso();
            List<JusoTestResponse> jusoResponseList = groupDataByLocation(jusoDataList);

            if (jusoResponseList.size() > 0) {
                return jusoResponseList;
            } else {
                log.info("Call API 값이 없습니다.");
            }
        } else {
            log.info("Call API NULL");
        }

        return null;
    }

    private JusoApiResponse callApi(URI apiUrl, HttpEntity<String> entity) {
        try {
            ResponseEntity<String> responseAsString = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (responseAsString == null) {
                log.info("API 결과 NULL");
            } else {
                if (responseAsString.getStatusCode() == HttpStatus.OK) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JusoApiResponse response = objectMapper.readValue(responseAsString.getBody(), JusoApiResponse.class);
                        String resultCode = response.getResults().getCommon().getErrorCode();

                        if (resultCode.equals(JusoResponseCode.NORMAL.getCode())) {
                            log.info("API 성공");
                            return response;
                        } else {
                            JusoResponseCode jusoResponseCode = JusoResponseCode.getJusoResponseCode(resultCode);
                            log.error("API 통신 오류 : {}, {}, {}", resultCode, jusoResponseCode.getMessage(), jusoResponseCode.getSolution());
                            throw new CommonException(jusoResponseCode.getMessage() + jusoResponseCode.getSolution(), resultCode);
                        }
                    } catch (Exception e) {
                        log.error("callApi 실패 error returnReasonCode : {} ", e.getMessage());
                    }
                } else {
                    log.error("API 통신 결과 실패 HttpStatus : {} ", responseAsString.getStatusCode());
                }
            }
        } catch (Exception e) {
            log.error("callApi 실패 error : {}", e.getMessage());
        }
        return null;
    }

    private boolean checkSearchedWord(String keyword) {
        Pattern pattern = Pattern.compile("[%=><]");
        Matcher matcher = pattern.matcher(keyword);

        if (matcher.find()) {
            return false;
        }

        List<String> sqlArray = new ArrayList<>();
        sqlArray.add("OR");
        sqlArray.add("SELECT");
        sqlArray.add("INSERT");
        sqlArray.add("DELETE");
        sqlArray.add("UPDATE");
        sqlArray.add("CREATE");
        sqlArray.add("DROP");
        sqlArray.add("EXEC");
        sqlArray.add("UNION");
        sqlArray.add("FETCH");
        sqlArray.add("DECLARE");
        sqlArray.add("TRUNCATE");

        for (String sqlText : sqlArray) {
            Pattern patternSql = Pattern.compile(sqlText, Pattern.CASE_INSENSITIVE);
            Matcher matcherSql = patternSql.matcher(keyword);
            if (matcherSql.find()) {
                return false;
            }
        }

        return true;
    }

    private List<JusoTestResponse> groupDataByLocation(List<JusoData> jusoDataList) {
        Map<String, JusoTestResponse> groupedData = new HashMap<>();

        for (JusoData dataItem : jusoDataList) {
            String admCd = dataItem.getAdmCd();

            JusoTestResponse jusoTestResponse = groupedData.getOrDefault(admCd, new JusoTestResponse());

            if (StringUtils.isBlank(jusoTestResponse.getAdmCd())) {
                String siNm = dataItem.getSiNm();
                String sggNm = dataItem.getSggNm();
                String emdNm = dataItem.getEmdNm();

                jusoTestResponse.setAdmCd(admCd);
                jusoTestResponse.setSiNm(siNm);
                jusoTestResponse.setSggNm(sggNm);
                jusoTestResponse.setEmdNm(emdNm);

                GeocodingApiData apiData = selectGeocoding(siNm, sggNm, emdNm);

                if (apiData != null) {
                    String lat = apiData.getLat();
                    String lon = apiData.getLon();
                    try {
                        if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lon)) {
                            GpsTransfer gpsTransfer = new GpsTransfer(Double.parseDouble(lat), Double.parseDouble(lon));
                            gpsTransfer.transfer(gpsTransfer, 0);
                            String latX = String.valueOf(gpsTransfer.getxLat());
                            String lonY = String.valueOf(gpsTransfer.getyLon());

                            jusoTestResponse.setLat(lat);
                            jusoTestResponse.setLon(lon);
                            jusoTestResponse.setLatX(latX);
                            jusoTestResponse.setLonY(lonY);
                        } else {
                            log.error("위경도 null : {} {}", lat, lon);
                        }
                    } catch (Exception e) {
                        log.error("위경도 좌표 변환 실패 : {} {}", lat, lon);
                    }
                }
                groupedData.put(admCd, jusoTestResponse);
            }
        }

        return new ArrayList<>(groupedData.values());
    }

    private GeocodingApiData selectGeocoding(String cityDo, String guGun, String dong) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("appKey", geocodingAppKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            String decodedCityDo = URLEncoder.encode(cityDo, "UTF-8");
            String decodedGuGun = URLEncoder.encode(guGun, "UTF-8");
            String decodedDong = URLEncoder.encode(dong, "UTF-8");
            UriComponents uri = UriComponentsBuilder
                    .fromHttpUrl(geocodingUrl)
                    .queryParam("version", "1")
                    .queryParam("city_do", decodedCityDo)
                    .queryParam("gu_gun", decodedGuGun)
                    .queryParam("dong", decodedDong)
                    .queryParam("addressFlag", "F00")
                    .queryParam("coordType", "WGS84GEO")
                    .build(true);
            URI apiUrl = uri.toUri();
            log.info("uri : " +  apiUrl);

            ResponseEntity<GeocodingApiResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, GeocodingApiResponse.class);

            if (response == null) {
                log.info("API 결과 NULL");
            } else {
                if (response.getStatusCode() == HttpStatus.OK) {
                    GeocodingApiData apiData = response.getBody().getCoordinateInfo();
                    return apiData;
                } else {
                    log.error("API 통신 결과 실패 HttpStatus : {} ", response.getStatusCode());
                    log.error("API 통신 결과 실패 error : {} ", response.getBody().getError());
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.info("Geocoding 주소 인코딩 실패 {}, {}, {}", cityDo, guGun, dong);
        }

        return null;
    }
}
