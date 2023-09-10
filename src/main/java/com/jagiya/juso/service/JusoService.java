package com.jagiya.juso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagiya.common.exception.CommonException;
import com.jagiya.juso.enums.JusoResponseCode;
import com.jagiya.juso.response.JusoApiResponse;
import com.jagiya.juso.response.JusoData;
import com.jagiya.juso.response.JusoTestResponse;
import com.jagiya.weather.entity.Weather;
import com.jagiya.weather.enums.WeatherCategory;
import com.jagiya.weather.enums.WeatherResponseCode;
import com.jagiya.weather.response.WeatherApiResponse;
import com.jagiya.weather.response.WeatherErrorResponse;
import com.jagiya.weather.response.WeatherItem;
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
import java.net.URLDecoder;
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

    private int retryCnt = 0;

    private final RestTemplate restTemplate;

    public List<JusoTestResponse> selectLocation(String keyword) throws UnsupportedEncodingException {
        retryCnt = 0;
        boolean isCheckSearchedWord = checkSearchedWord(keyword);
        if (!isCheckSearchedWord) {
            log.error("특수문자 또는 사용할수 없는 특정 문자가 들어갔습니다. {}", keyword);
            throw new CommonException("특수문자 또는 사용할수 없는 특정 문자가 들어갔습니다.", 888);
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
                .queryParam("countPerPage", "100")
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
                            log.error("API 통신 오류 : {}, {}", resultCode, JusoResponseCode.getMessageByCode(resultCode));
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
                String SggNm = dataItem.getSggNm();
                String emdNm = dataItem.getEmdNm();

                jusoTestResponse.setAdmCd(admCd);
                jusoTestResponse.setSiNm(siNm);
                jusoTestResponse.setSggNm(SggNm);
                jusoTestResponse.setEmdNm(emdNm);
                groupedData.put(admCd, jusoTestResponse);
            }
        }

        return new ArrayList<>(groupedData.values());
    }
}
